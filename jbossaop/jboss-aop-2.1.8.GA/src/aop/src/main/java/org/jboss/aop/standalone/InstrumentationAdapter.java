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
package org.jboss.aop.standalone;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.ArrayList;
import java.util.Collection;

import org.jboss.aop.instrument.HotSwapper;

/**
 * This class is an adapter to <code>java.lang.instrument.Instrumentation
 * </code> and is used to enable hot swap in JBoss AOP through Java 5 agents. 
 * 
 * @author Flavia Rainone
 */
class InstrumentationAdapter implements HotSwapper
{
   
   private Instrumentation instrumentation; // delegate
   private Collection<ClassDefinition> classDefinitions;
   
   /**
    * Constructor.
    * @param instrumentation
    */
   public InstrumentationAdapter(Instrumentation instrumentation)
   {
      this.instrumentation = instrumentation;
      this.classDefinitions = new ArrayList<ClassDefinition>();
   }

   /**
    * Register class' byte codes redefinitions.
    * @param clazz the clazz to be redefined.
    * @param classCode the new byte code implementation of <code>clazz</code>.
    */
   public synchronized void registerChange(Class<?> clazz, byte[] classCode)
   {
      ClassDefinition classDef = new ClassDefinition(clazz, classCode);
      this.classDefinitions.add(classDef);
   }

   /**
    * Hot swaps previously registered changes.
    * All classes' byte codes registered through <code>register</code>
    * are redefined in the system only when <code>hotSwap</code> is invoked.
    */
   public synchronized void hotSwap()
   {
      ClassDefinition[] definitions = new ClassDefinition[this.classDefinitions.size()];
      definitions = this.classDefinitions.toArray(definitions);
      try
      {
         instrumentation.redefineClasses(definitions);
      }
      catch(ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      catch(UnmodifiableClassException e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         this.classDefinitions.clear();
      }
   }
}