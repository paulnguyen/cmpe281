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
package org.jboss.aop.pointcut;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.proxy.container.ClassProxyContainer;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Strategy to allow for different handling for the pointcut matchers for different types of advisor 
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 77480 $
 */
public abstract class MatcherStrategy
{
   private static final AOPLogger logger = AOPLogger.getLogger(MatcherStrategy.class);
   
   private final static MatcherStrategy ADVISOR_MATCHER_STRATEGY = new AdvisorMatcherStrategy();
   private final static MatcherStrategy PROXY_MATCHER_STRATEGY = new ProxyMatcherStrategy();
   
   public static MatcherStrategy getMatcher(Advisor advisor)
   {
      if (advisor instanceof ClassProxyContainer)
      {
         return PROXY_MATCHER_STRATEGY;
      }
      
      return ADVISOR_MATCHER_STRATEGY;
   }

   public boolean subtypeOf(Class<?> clazz, ClassExpression instanceOf, Advisor advisor)
   {
      
      if (clazz == null) return false;
      if (instanceOf.isInstanceOfAnnotated())
      {
         String sub = instanceOf.getInstanceOfAnnotation().substring(1);
         try
         {
            ClassLoader cl = advisor.getClassLoader();
            if (cl == null)
            {
               cl = SecurityActions.getContextClassLoader();
            }
            Class<?> annotation = cl.loadClass(sub);
            if (Annotation.class.isAssignableFrom(annotation))
            {
               if (AnnotationElement.getAnyAnnotation(clazz, (Class<? extends Annotation>)annotation) != null)
               {
                  return true;
               }
            }
         }
         catch (ClassNotFoundException e)
         {
            if (AspectManager.verbose)
            {
               logger.warn("The annotation @" + sub + " referenced in one of your pointcut expressions can not be found");
            }
            return false;
         }
      }
      else if (instanceOf.matches(clazz.getName()))
      {
         return true;
      }

      Class<?>[] interfaces = clazz.getInterfaces();
      for (int i = 0; i < interfaces.length; i++)
      {
         if (subtypeOf(interfaces[i], instanceOf, advisor)) return true;
      }
      if (clazz.isInterface()) return false; // we are done

      if (checkIntroductions(clazz, instanceOf, advisor))
      {
         return true;
      }
      
      return subtypeOf(clazz.getSuperclass(), instanceOf, advisor);
   }

   protected abstract boolean checkIntroductions(Class<?> clazz, ClassExpression instanceOf, Advisor advisor);
   
   public abstract Class<?> getDeclaringClass(Advisor advisor, Method m);
}
