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
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Classic weaving.
 *
 * @author <a href="stalep@conduct.no">Stale W. Pedersen</a>
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision:
 */
public class ClassicWeavingStrategy extends WeavingStrategySupport
{
   private static final AOPLogger logger = AOPLogger.getLogger(ClassicWeavingStrategy.class);
   
	private boolean verbose = AspectManager.verbose;
    /**
     * This is the translate version that was always there
     */
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
          CtClass clazz = null;
          try
          {
             clazz = pool.getLocally(className);
          }
          catch (NotFoundException e)
          {
             // todo Bill Burke: this scares the shit out of me, but it must be done
             // I think it will screw up hotdeployment at some time.  Then again, maybe not ;)
             ByteArrayClassPath cp = new ByteArrayClassPath(className, classfileBuffer);
             pool.insertClassPath(cp);
             clazz = pool.getLocally(className);
          }
          if (clazz.isArray())
          {
             if (verbose && logger.isDebugEnabled()) logger.debug("cannot compile, isArray: " + className);
             pool.flushClass(className);
             return null;
          }
          if (clazz.isInterface())
          {
             if (verbose && logger.isDebugEnabled()) logger.debug("cannot compile, isInterface: " + className);
             pool.flushClass(className);
             return null;
          }
          if (clazz.isFrozen())
          {
             if (verbose && logger.isDebugEnabled()) logger.debug("warning, isFrozen: " + className);
             clazz.defrost();
          }

          ClassAdvisor advisor = AdvisorFactory.getClassAdvisor(clazz, manager);
          Instrumentor instrumentor = InstrumentorFactory.getInstrumentor(
                pool,
                manager,
                manager.dynamicStrategy.getJoinpointClassifier(),
                manager.dynamicStrategy.getDynamicTransformationObserver(clazz));

          if (!Instrumentor.isTransformable(clazz))
          {
             if (verbose && logger.isDebugEnabled()) logger.debug("[cannot compile] implements Untransformable: " + className);
             pool.flushClass(className);
             return null;
          }

          manager.attachMetaData(advisor, clazz, true);
          manager.applyInterfaceIntroductions(advisor, clazz);
          boolean transformed = instrumentor.transform(clazz, advisor);
          if (transformed)
          {
             pool.lockInCache(clazz);
             if (AspectManager.debugClasses)
             {
                SecurityActions.debugWriteFile(clazz);
             }

             byte[] rtn = clazz.toBytecode();
             if (AspectManager.getPrune()) clazz.prune();
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
                logger.error("[error] " + ex.getMessage() + ".. Do verbose mode if you want full stack trace.");

          }
          throw ex;
       }
       finally
       {
    	   clearReEntry();
       }
    }
 }
