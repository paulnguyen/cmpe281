/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.aop;


import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.instrument.Instrumentor;
import org.jboss.aop.instrument.InstrumentorFactory;
import org.jboss.aop.util.logging.AOPLogger;

import javassist.ByteArrayClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Generated advisors need to load all the superclasses
 * before we load the actual class.
 *
 * @author <a href="stalep@conduct.no">Stale W. Pedersen</a>
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision:
 */
public class SuperClassesFirstWeavingStrategy extends WeavingStrategySupport {

   private static final AOPLogger logger = AOPLogger.getLogger(SuperClassesFirstWeavingStrategy.class);
   
   private boolean verbose = AspectManager.verbose;
   public static final String AOP_PACKAGE = Advised.class.getPackage().getName();

   public byte[] translate(AspectManager manager, String className, ClassLoader loader, byte[] classfileBuffer) throws Exception
   {
      if (isReEntry())
      {
         return null;
      }
      setReEntry();
      super.setTransformationStarted();
      try
      {
         if (manager.isNonAdvisableClassName(className))
         {
            return null;
         }

         AOPClassPool pool = (AOPClassPool) manager.registerClassLoader(loader);

         CtClass clazz = obtainCtClassInfo(pool, className, classfileBuffer);
         
         CtClass woven = instrumentClass(manager, pool, clazz, true);
         if (woven != null)
         {
            pool.lockInCache(woven);
            if (AspectManager.debugClasses)
            {
               SecurityActions.debugWriteFile(clazz);
            }
            byte[] rtn = woven.toBytecode();
            if (AspectManager.getPrune()) woven.prune();
            return rtn;
         }
         else
         {
            pool.soften(clazz);
         }
         return null;
      }
      catch (Exception ex)
      {
         if (!(ex instanceof NotFoundException))
         {
            if (verbose)
               logger.error(ex);
            else
               logger.error(ex.getMessage()+".. Do verbose mode if you want full stack trace.");
         }
         throw ex;
      }
      finally
      {
         clearReEntry();
      }
   }

   private CtClass obtainCtClassInfo(AOPClassPool pool, String className, byte[] classfileBuffer) throws NotFoundException
   {
      try
      {
         return pool.getLocally(className);
      }
      catch (NotFoundException e)
      {
         // todo Bill Burke: this scares the shit out of me, but it must be done
         // I think it will screw up hotdeployment at some time.  Then again, maybe not ;)
         ByteArrayClassPath cp = new ByteArrayClassPath(className, classfileBuffer);
         pool.insertClassPath(cp);
         return pool.getLocally(className);
      }
      catch(Error e)
      {
         return null;
      }
   }

   private CtClass instrumentClass(AspectManager manager, AOPClassPool pool, CtClass clazz, boolean isLoadedClass) throws NotFoundException, Exception
   {
      if (pool.isClassLoadedButNotWoven(clazz.getName()))
      {
         return null;
      }
      try
      {
         CtClass superClass = clazz.getSuperclass();
         if (superClass != null && !Instrumentor.implementsAdvised(clazz))
         {
            ClassPool superPool = superClass.getClassPool();
            if (superPool instanceof AOPClassPool)
            {
               AspectManager aspectManager = manager;
               if (manager instanceof Domain && superPool != pool)
               {
                  //We are in a scoped classloader and the superclass is not
                  aspectManager = AspectManager.instance(superPool.getClassLoader());
               }
               instrumentClass(aspectManager, (AOPClassPool)superPool, superClass, false);
            }
         }

         if (manager.isNonAdvisableClassName(clazz.getName()))
         {
            return null;
         }

         if (clazz.isArray())
         {
            if (verbose && logger.isDebugEnabled()) logger.debug("cannot compile, isArray: " + clazz.getName());
            pool.flushClass(clazz.getName());
            return null;
         }
         if (clazz.isInterface())
         {
            if (verbose && logger.isDebugEnabled()) logger.debug("cannot compile, isInterface: " + clazz.getName());
            //pool.flushClass(info.getClassName());
            clazz.prune();
            return null;
         }
         if (clazz.isFrozen())
         {
            if(isAdvised(pool, clazz))
               return null;
            if (verbose && logger.isDebugEnabled()) logger.debug("warning, isFrozen: " + clazz.getName() + " " + clazz.getClassPool());
            if (!isLoadedClass)
            {
               //What's the point of this?
               clazz = obtainCtClassInfo(pool, clazz.getName(), null);
            }
            else
               return null;
            //info.getClazz().defrost();
         }

         boolean transformed = clazz.isModified();
         if (!transformed)
         {
            ClassAdvisor advisor =
                   AdvisorFactory.getClassAdvisor(clazz, manager);
            Instrumentor instrumentor = InstrumentorFactory.getInstrumentor(
                  pool,
                  manager,
                  manager.dynamicStrategy.getJoinpointClassifier(),
                  manager.dynamicStrategy.getDynamicTransformationObserver(clazz));

            if (!Instrumentor.isTransformable(clazz))
            {
               if (verbose && logger.isDebugEnabled()) logger.debug("cannot compile, implements Untransformable: " + clazz.getName());
               //Flushing the generated invocation classes breaks things further down the line
               //pool.flushClass(info.getClassName());
               return null;
            }

            manager.attachMetaData(advisor, clazz, true);
            manager.applyInterfaceIntroductions(advisor, clazz);
            transformed = instrumentor.transform(clazz, advisor);
         }
         if (transformed)
         {
            return clazz;
         }
         
         if (isLoadedClass)
         {
            pool.setClassLoadedButNotWoven(clazz.getName());
         }
         
         return null;
      }
      catch(Exception e)
      {
         throw new RuntimeException("Error converting class ", e);
      }
      finally
      {
      }
   }

   public boolean isAdvised(ClassPool pool, CtClass clazz) throws NotFoundException
   {
      CtClass[] interfaces = clazz.getInterfaces();
      CtClass advised = pool.get(AOP_PACKAGE + ".Advised");
      for (int i = 0; i < interfaces.length; i++)
      {
         if (interfaces[i].equals(advised)) return true;
         if (interfaces[i].getName().equals(AOP_PACKAGE + ".Advised")) return true;
      }
      return false;
   }
}
