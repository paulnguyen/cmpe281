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
package org.jboss.aop.advice;

/**
 * Exception thrown when no advice suitable to intercept a specific joinpoint was
 * found.
 * 
 * This does not mean, however, that an advice is invalid or breaks a signature
 * rule.
 * <p>
 * If your application is throwing this exception, you can either:
 * <ul>
 * <li> add a valid advice method if there is no advice method with the specified
 * name;
 * <li> change an existing advice method in order to make it suitable for the
 * problematic joinpoint;</li>
 * <li> add an overloaded advice method that is suitable for the problematic
 * joinpoint.</li>
 * </ul>
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class NoMatchingAdviceException extends RuntimeException
{
   private static final long serialVersionUID = 2170206416883695862L;

   /**
    * Constructor.
    * 
    * @param adviceProperties properties of the queried advice
    * @param adviceType       the type of the queried advice
    * @param message          message explaining why no matching advice was found
    */
   public NoMatchingAdviceException(AdviceMethodProperties adviceProperties,
         AdviceType adviceType, String message)
   {
      super("No matching " + adviceType + " advice called '" +
            adviceProperties.getAdviceName() + "' could be found in " +
            adviceProperties.getAspectClass().getName() + " for joinpoint " +
            adviceProperties.getJoinPoint() + message);
   }
   
   /**
    * Constructor.
    * 
    * @param adviceProperties properties of the queried advice
    * @param adviceType       the type of the queried advice
    * @param message          message explaining why no matching advice was found
    */
   public NoMatchingAdviceException(Class<?> aspectClass, String adviceName)
   {
      super("No matching advice called '" + adviceName + "' could be found in " +
            aspectClass.getName() + ": method was not found");
   }
}