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
package org.jboss.aop.instrument;

import java.lang.reflect.Constructor;

import org.jboss.aop.AspectManager;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class InstrumentorFactory 
{
   private static final AOPLogger logger = AOPLogger.getLogger(InstrumentorFactory.class);
   
   protected static InstrumentorEnum instrumentor; 
   
   protected static Constructor<?> otherInstrumentorConstructor;
   
   private static final Class<?>[] CONSTRUCTOR_SIG = new Class[] {AOPClassPool.class, AspectManager.class, JoinpointClassifier.class, DynamicTransformationObserver.class};
   
   public static void initialise(String property)
   {
      if (AspectManager.verbose && logger.isDebugEnabled())
      {
         logger.debug("Passed in instrumentor: " + property);
      }
      
      if (property != null)
      {
         if (property.equals(ClassicInstrumentor.class.getName()))
         {
            instrumentor = InstrumentorEnum.CLASSIC;
         }
         else if (property.equals(GeneratedAdvisorInstrumentor.class.getName()))
         {
            instrumentor = InstrumentorEnum.GENERATED_ADVISOR;
         }
         else
         {
            
            try
            {
               //Context classloader is fine in this case since this is only called when initialising the main AspectManager
               Class<?> otherInstrumentorClass = SecurityActions.getContextClassLoader().loadClass(property);
               otherInstrumentorConstructor = otherInstrumentorClass.getConstructor(CONSTRUCTOR_SIG);
               instrumentor = InstrumentorEnum.OTHER_INSTRUMENTOR;
            }
            catch (ClassNotFoundException e)
            {
               throw new RuntimeException("Invalid instrumentor " + property + " was used");
            }
            catch(NoSuchMethodException e)
            {
               throw new RuntimeException(property + " does not have a constructor with the expected signature");
            }
         }
      }
      else
      {
         instrumentor = InstrumentorEnum.GENERATED_ADVISOR;
         if (AspectManager.verbose && logger.isDebugEnabled())
         {
            logger.debug("Defaulting instrumentor to: " + getInstrumentorName());
         }
      }
   }
   
   public static InstrumentorEnum getInstrumentor()
   {
      return instrumentor;
   }
   
   public static Instrumentor getInstrumentor(AOPClassPool pool, AspectManager manager, JoinpointClassifier joinpointClassifier, DynamicTransformationObserver observer)
   {
      if (instrumentor == InstrumentorEnum.CLASSIC)
      {
         return new ClassicInstrumentor(pool, manager, joinpointClassifier, observer);         
      }
      else if (instrumentor == InstrumentorEnum.GENERATED_ADVISOR)
      {
         return new GeneratedAdvisorInstrumentor(pool, manager, joinpointClassifier, observer);
      }
      else if (otherInstrumentorConstructor != null)
      {
         try
         {
            return (Instrumentor) otherInstrumentorConstructor.newInstance(new Object[] {pool, manager, joinpointClassifier, observer});
         }
         catch (Exception e)
         {
            throw new RuntimeException(e);
         }
      }
      else
      {
         throw new RuntimeException("Instrumentor is not set");
      }
   }
   

   public static Instrumentor getInstrumentor(AspectManager manager, JoinpointClassifier joinpointClassifier)
   {
      if (instrumentor == InstrumentorEnum.CLASSIC)
      {
         return new ClassicInstrumentor(manager, joinpointClassifier);
      }
      else if (instrumentor == InstrumentorEnum.GENERATED_ADVISOR)
      {
         return new GeneratedAdvisorInstrumentor(manager, joinpointClassifier);
      }
      else
      {
         throw new RuntimeException("Instrumentor is not set");
      }
   }
   
   public static String getInstrumentorName()
   {
      if (instrumentor == InstrumentorEnum.CLASSIC)
      {
         return ClassicInstrumentor.class.getName();
      }
      else if (instrumentor == InstrumentorEnum.GENERATED_ADVISOR)
      {
         return GeneratedAdvisorInstrumentor.class.getName();
      }
      else if (instrumentor == InstrumentorEnum.OTHER_INSTRUMENTOR)
      {
         return otherInstrumentorConstructor.getName();
      }
      else
      {
         throw new RuntimeException("Instrumentor is not set");
      }
   }
   
}
