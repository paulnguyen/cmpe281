/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.aop.util.reference;

import java.lang.reflect.Constructor;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ConstructorPersistentReference extends ArgumentPersistentReference
{
   public ConstructorPersistentReference (Constructor<?> constructor, int referenceType)
   {
      super(constructor !=null ? constructor.getDeclaringClass() : null, constructor, referenceType);
      if (constructor != null)
      {
         setArguments(constructor.getParameterTypes());
      }
   }
   

   public synchronized Object rebuildReference() throws Exception
   {
      // A reference to guarantee the value is not being GCed during while the value is being rebuilt
      Object returnValue = null;
      if ((returnValue = internalGet())!=null) return returnValue;
      
      Constructor<?> constructor = getMappedClass().getDeclaredConstructor(getArguments());
      buildReference(constructor);
      return constructor;
   }

   public Constructor<?> getConstructor()
   {
      return (Constructor<?>) get();
   }
}
