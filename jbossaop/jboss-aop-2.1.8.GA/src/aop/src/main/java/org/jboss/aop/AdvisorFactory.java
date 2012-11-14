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

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import javassist.CtClass;

import org.jboss.aop.instrument.GeneratedAdvisorInstrumentor;
import org.jboss.aop.instrument.InstrumentorEnum;
import org.jboss.aop.instrument.InstrumentorFactory;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * 
 *
 * @author <a href="mailto:stalep@conduct.no">Staale W. Pedersen</a>
 * @version $Revision
 */
public class AdvisorFactory
{
   private static final AOPLogger logger = AOPLogger.getLogger(AdvisorFactory.class);

   protected static final int CLASS = 1;
   protected static final int OTHER_ADVISOR = 1000; //The jrockit aop advisor is in another jar which we should not depend on
   protected static int advisor = 0;
   
   protected static Constructor<?> otherAdvisorConstructor;
   
   private static final Class<?>[] NO_ARGS = new Class<?>[0];
   private static final Class<?>[] CONSTRUCTOR_SIG = new Class<?>[] {String.class, AspectManager.class};
   
   
   public static void initialise(String property)
   {
      if (AspectManager.verbose && logger.isDebugEnabled())
      {
         logger.debug("Passed in advisor: " + property);
      }

      if(property != null)
      {
         if (property.equals(ClassAdvisor.class.getName()))
         {
            advisor = CLASS;
         }
         else
         {         
            try
            {
               //Context classloader is fine in this case since this is only called when initialising the main AspectManager
               Class<?> otherAdvisorClass = SecurityActions.getContextClassLoader().loadClass(property);
               otherAdvisorConstructor = otherAdvisorClass.getConstructor(CONSTRUCTOR_SIG);
            }
            catch (ClassNotFoundException e)
            {
               throw new RuntimeException("Invalid advisor " + property + " was used");
            }
            catch(NoSuchMethodException e)
            {
               throw new RuntimeException(property + " does not have a constructor with the expected signature");
            }
         }   
      }
      else
      {
         if (AspectManager.verbose  && logger.isDebugEnabled())
         {
            logger.debug("[debug] Defaulting advisor to: " + ClassAdvisor.class.getName());
         }
         advisor = CLASS;
      }
   }

   public static ClassAdvisor getClassAdvisor(Class<?> clazz, AspectManager am)
   {
      ClassAdvisor classAdvisor = getClassAdvisor(clazz.getName(), am, clazz);
      classAdvisor.setClazz(clazz);
      return classAdvisor;
   }
   
   public static ClassAdvisor getClassAdvisor(CtClass clazz, AspectManager am)
   {
      ClassAdvisor classAdvisor = getClassAdvisor(clazz.getName(), am, null);
      classAdvisor.setClassLoader(clazz.getClassPool().getClassLoader());
      return classAdvisor;
   }
   
   private static ClassAdvisor getClassAdvisor(String className, AspectManager am, Class<?> loadedClass)
   {
      if(advisor == CLASS)
      {
         if (loadedClass != null)
         {
            if (InstrumentorFactory.getInstrumentor() == InstrumentorEnum.GENERATED_ADVISOR)
            {
               //Generated advisors need to be initialised via the class itself
               try
               {
                  final Method getAdvisor = loadedClass.getMethod(GeneratedAdvisorInstrumentor.GET_CLASS_ADVISOR, NO_ARGS);
                  if (!getAdvisor.isAccessible())
                  {
                     if (System.getSecurityManager() == null)
                     {
                        getAdvisor.setAccessible(true);
                     }
                     else
                     {
                        AccessController.doPrivileged(
                              new PrivilegedExceptionAction<Object>()
                        {
                           public Object run() throws Exception
                           {
                              getAdvisor.setAccessible(true);
                              return null;
                           }
                        });
                     }
                  }
                  ClassAdvisor advisor = (ClassAdvisor)getAdvisor.invoke(null, null);
                  if (advisor != null && advisor.getClazz() == loadedClass)
                  {
                     //Sub classes may not be instrumented, in which case we have the super class advisor here, which we don't want
                     return advisor;
                  }
               }
               catch (NoSuchMethodException e)
               {
                  //This is an interface or the class is not advised, fall back to creating a normalClassAdvisor
               }
               catch (Exception e)
               {
                  throw new RuntimeException(e);
               }
            }
         }

         return new ClassAdvisor(className, am);
      }
      else if(otherAdvisorConstructor != null)
      {
         try
         {
            return (ClassAdvisor) otherAdvisorConstructor.newInstance(new Object[] {className, am});
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      }
      else
      {
         throw new RuntimeException("Advisor is not set");
      }         
   }
   
}
