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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.pointcut.Pointcut;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 78458 $
 */
public abstract class ConstructionTransformer
{
   final static String CONSTRUCTION_INFO_CLASS_NAME = "org.jboss.aop.ConstructionInfo";
   protected Instrumentor instrumentor;

   protected ConstructionTransformer(Instrumentor instrumentor)
   {
      this.instrumentor = instrumentor;
   }

   /**
    * Adds a ConstructionInfo field to the passed in class
    */
   protected String addConstructionInfoField(int modifiers, CtClass addTo, CtConstructor ctor, int index) throws NotFoundException, CannotCompileException
   {
      return addConstructionInfoField(modifiers, addTo, ctor, index, null);
   }
   
   /**
    * Adds a ConstructionInfo field to the passed in class
    */
   protected String addConstructionInfoField(int modifiers, CtClass addTo, CtConstructor ctor, int index, CtField.Initializer init) throws NotFoundException, CannotCompileException
   {
      String name = getConstructionInfoFieldName(ctor.getDeclaringClass().getSimpleName(), index);
      
      //Instrumentor claspool will be null during hotswap, in which case we
      //already will have created the field
      if (instrumentor.getClassPool() != null)
      {
         try
         {
            addTo.getDeclaredField(name);
            return name;
         }
         catch(NotFoundException e)
         {
         }
         TransformerCommon.addInfoField(instrumentor, CONSTRUCTION_INFO_CLASS_NAME, name, modifiers, addTo, addInfoAsWeakReference(), init, markInfoAsSynthetic());
         
      }
      return name;
   }

   protected boolean addInfoAsWeakReference()
   {
      return true;
   }
   
   protected boolean markInfoAsSynthetic()
   {
      return true;
   }

   public static String getConstructionInfoFieldName(String classname, int index)
   {
      if (classname.indexOf(".") >= 0)
      {
         throw new RuntimeException("Use simple class name for construction info field name: " + classname);
      }
      return "aop$constructionInfo_" + classname.replace('.','$') + "_" + index;
   }
      
   protected static String constructionInfoFromWeakReference(String localName, String ctorInfoName)
   {
      return TransformerCommon.infoFromWeakReference(CONSTRUCTION_INFO_CLASS_NAME, localName, ctorInfoName);
   }

   // generateWrapper + prepareForWrapping
   public boolean insertConstructionInterception(CtClass clazz, ClassAdvisor advisor)
   throws Exception
   {
      if (!advisor.getManager().isConstruction()) return false;

      boolean oneMatch = false;
      List<CtConstructor> constructors = instrumentor.getConstructors(clazz);
      
      Iterator<CtConstructor> it = constructors.iterator();
      for (int index = 0; it.hasNext(); index++)
      {
         // generate wrapper
         CtConstructor constructor = it.next();
         if (constructor.isClassInitializer() || !isAdvisableConstructor(constructor,  advisor))
         {
            if (oneMatch)
            {
               //Should this be done for class initialisers?
               generateNotMatchedConstructionInfoField(constructor, index);
            }
            continue;
         }
         
         if (!oneMatch)
         {
            oneMatch = true;
            instrumentor.setupBasics(clazz);
            
            for (int j = 0 ; j < index ; j++)
            {
               generateNotMatchedConstructionInfoField(constructors.get(j), j);
            }
         }

         generateConstructionInfoField(constructor, index);
         insertInterception(constructor, index);
      }
      return oneMatch;
   }

   protected abstract void insertInterception(CtConstructor constructor, int index) throws Exception;

   // currently used by CallerTransformer
   public static boolean isAdvisableConstructor(CtConstructor con, ClassAdvisor advisor) throws NotFoundException
   {
      
      Collection<Pointcut> pointcuts = advisor.getManager().getBindingCollection().getConstructionPointcuts();
      for (Pointcut pointcut : pointcuts)
      {
         if (pointcut.matchesConstruction(advisor, con))
         {
            return true;
         }
      }
      return false;
   }

   protected void generateConstructionInfoField(CtConstructor constructor, int index) throws NotFoundException, CannotCompileException
   {
      addConstructionInfoField(Modifier.STATIC | Modifier.PRIVATE, 
            constructor.getDeclaringClass(),
            constructor,
            index);
   }
   
   protected void generateNotMatchedConstructionInfoField(CtConstructor constructor, int index) throws NotFoundException, CannotCompileException
   {
      
   }
   
}