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

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 74807 $
 */
public class PointcutMethodMatch
{
   private boolean matches;
   private int matchLevel;
   private Class<?> matchedClass;
   private boolean isInstanceOf;

   public PointcutMethodMatch(boolean matches, Class<?> matchedClass, int matchLevel, boolean isInstanceOf)
   {
      this.matches = matches;
      this.matchLevel = matchLevel;
      this.matchedClass = matchedClass;
      this.isInstanceOf = isInstanceOf;
   }

   public Class<?> getMatchedClass()
   {
      return matchedClass;
   }

   public boolean isMatch()
   {
      return matches;
   }

   public int getMatchLevel()
   {
      return matchLevel;
   }

   public boolean isInstanceOf()
   {
      return isInstanceOf;
   }
   
   @Override
   public boolean equals(Object o)
   {
      if(o != null && o instanceof PointcutMethodMatch &&
            this.getMatchLevel() == ((PointcutMethodMatch) o).getMatchLevel() &&
            this.getMatchedClass() != null && ((PointcutMethodMatch) o).getMatchedClass() != null &&
            this.getMatchedClass().getName().equals(((PointcutMethodMatch) o).getMatchedClass().getName()) &&
            this.isInstanceOf() == ((PointcutMethodMatch) o).isInstanceOf())
         return true;
      else
         return false;
   }

   @Override
   public int hashCode()
   {
      return matchedClass.getName().hashCode();
   }

}
