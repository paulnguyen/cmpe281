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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javassist.runtime.Inner;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.ClassifiedBindingAndPointcutCollection;
import org.jboss.aop.metadata.ClassMetaDataBinding;
import org.jboss.aop.metadata.ClassMetaDataLoader;
import org.jboss.aop.util.Advisable;
import org.jboss.aop.util.BindingClassifier;
import org.jboss.aop.util.ConstructorComparator;
import org.jboss.aop.util.FieldComparator;
import org.jboss.aop.util.MethodHashing;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 87692 $
 */
public class ClassContainer extends Advisor
{
   private static final AOPLogger logger = AOPLogger.getLogger(ClassContainer.class);
   
   private boolean chainOverridingForInheritedMethods;
   
   public ClassContainer(String name, AspectManager manager)
   {
      super(name, manager);
   }

   @SuppressWarnings("deprecation")
   public void initializeClassContainer(Class<?> clazz)
   {
      setClass(clazz);
      initializeClassContainer();
   }
   
   @Deprecated
   public void initializeClassContainer()
   {
      initializeMetadata();
      rebuildInterceptors();
   }

   @Deprecated
   public void setClass(Class<?> clazz)
   {
      this.clazz = clazz;
   }

   public void initializeMetadata()
   {
      createMethodMap();
      createConstructorTables();
      createFieldTable();
      getManager().attachMetaData(this, clazz);
      rebindClassMetaData();
      deployAnnotationOverrides();
   }
   
   protected Field[] advisedFields;

   private void populateFieldTable(ArrayList<Field> fields, final Class<?> superclass)
   {
      if (superclass == null) return;
      if (superclass.equals(Object.class)) return;

      populateFieldTable(fields, superclass.getSuperclass());

      // if (!isAdvised(superclass)) return;

      ArrayList<Field> temp = new ArrayList<Field>();
      Field[] declaredFields = AccessController.doPrivileged(new PrivilegedAction<Field[]>()
      {
         public Field[] run()
         {
            return superclass.getDeclaredFields();
         }
      });
      for (int i = 0; i < declaredFields.length; i++)
      {
         if (Advisable.isAdvisable(declaredFields[i]))
         {
            temp.add(declaredFields[i]);
         }
      }
      Collections.sort(temp, FieldComparator.INSTANCE);
      fields.addAll(temp);
   }

   /**
    * Gets advised methods.
    */
   protected void createFieldTable()
   {
      ArrayList<Field> fields = new ArrayList<Field>();
      populateFieldTable(fields, clazz);
      advisedFields = fields.toArray(new Field[fields.size()]);

   }

   @Override
   protected void rebuildInterceptors()
   {
      adviceBindings.clear();
      if (this.constructorInfos == null)
      {
         createInterceptorChains();
      }
      else
      {
         updateInterceptorChains();
      }
   }
   
   @Override
   protected void rebuildInterceptorsForAddedBinding(AdviceBinding binding)
   {
      if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("iterate binding " + binding.getName());
      resetChainKeepInterceptors(methodInfos);
      resetChainKeepInterceptors(constructorInfos);

      resolveMethodPointcut(binding);
      resolveConstructorPointcut(binding);
      
      finalizeChain(constructorInfos);
      finalizeMethodChain();
      
      populateInterceptorsFromInfos();
      doesHaveAspects = adviceBindings.size() > 0;
   }
   
   @Override
   protected void rebuildInterceptorsForRemovedBinding(AdviceBinding binding)
   {
      
      if (BindingClassifier.isExecution(binding))
      {
         updateMethodPointcutAfterRemove(binding);
      }
      if (BindingClassifier.isConstructorExecution(binding))
      {
         updateConstructorPointcutAfterRemove(binding);
      }
      
      if (BindingClassifier.isExecution(binding))
      {
         finalizeMethodChain();
      }
      if (BindingClassifier.isConstructorExecution(binding))
      {
         finalizeChain(constructorInfos);
      }
      
      doesHaveAspects = adviceBindings.size() > 0;
   }

   @Override
   public void addClassMetaData(ClassMetaDataBinding data)
   {
      initClassMetaDataBindingsList();
      classMetaDataBindings.add(data);
      if (this.clazz == null) return;  // don't bind till later.

      bindClassMetaData(data);
      // Recalculate interceptorPointcuts because of MetaDataInterceptorPointcuts
      adviceBindings.clear();
      doesHaveAspects = false;
      rebuildInterceptors();

   }



   @Override
   public void removeClassMetaData(ClassMetaDataBinding data)
   {
      if (classMetaDataBindings.remove(data))
      {
         if (this.clazz == null) return; // not bound yet
         rebindClassMetaData();
         // Recalculate interceptorPointcuts because of MetaDataInterceptorPointcuts
         adviceBindings.clear();
         doesHaveAspects = false;
         rebuildInterceptors();
      }
   }

   protected void bindClassMetaData(ClassMetaDataBinding data)
   {
      try
      {
         ClassMetaDataLoader loader = data.getLoader();
         Object[] objs = advisedMethods.getValues();
         Method[] methods = new Method[objs.length];
         for (int i = 0; i < objs.length; i++) methods[i] = (Method) objs[i];
         loader.bind(this, data, methods, advisedFields, clazz.getDeclaredConstructors());
      }
      catch (Exception ex)
      {
         // REVISIT:  Need to know how errors affects deployment
         ex.printStackTrace();
      }
   }

   protected void rebindClassMetaData()
   {
      defaultMetaData.clear();
      methodMetaData.clear();
      fieldMetaData.clear();
      constructorMetaData.clear();
      classMetaData.clear();

      for (int i = 0; i < classMetaDataBindings.size(); i++)
      {
         try
         {
            ClassMetaDataBinding data = classMetaDataBindings.get(i);
            bindClassMetaData(data);
         }
         catch (ClassCastException e)
         {
            //This is for EJB3. It is putting the SerializedConcurrentAccess into the list 
            logger.warn("Invalid object found in classMetaDataBindings map. The type was " + e.getMessage());
         }
      }
   }

   protected void createMethodMap()
   {
      initAdvisedMethodsMap();
      try
      {
         Method[] declaredMethods = clazz.getMethods();
         for (int i = 0; i < declaredMethods.length; i++)
         {
            if (ClassAdvisor.isAdvisable(declaredMethods[i]))
            {
               long hash = MethodHashing.methodHash(declaredMethods[i]);
               advisedMethods.put(hash, declaredMethods[i]);
            }
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   protected void initializeMethodChain()
   {
      long[] keys = advisedMethods.keys();
      for (int i = 0; i < keys.length; i++)
      {
         MethodInfo info = new MethodInfo();
         Method amethod = (Method) advisedMethods.get(keys[i]);
         info.setAdvisedMethod(amethod);
         info.setUnadvisedMethod(amethod);
         info.setHash(keys[i]);
         info.setAdvisor(this);
         methodInfos.put(keys[i], info);
      }
   }

   protected void createConstructorTables()
   {
      constructors = SecurityActions.getDeclaredConstructors(clazz);
      // remove javassist generated constructor for inner class
      if (clazz.isMemberClass())
      {
         for (int i = 0; i < constructors.length; i++)
         {
            if (constructors[i].getParameterTypes().length == 1 &&
               constructors[i].getParameterTypes()[0].getName().
               equals(Inner.class.getName()))
            {
               Constructor<?>[] newConstructors = new Constructor<?>[constructors.length - 1];
               System.arraycopy(constructors, 0, newConstructors, 0, i);
               System.arraycopy(constructors, i+1, newConstructors, i, constructors.length -i - 1);
               constructors = newConstructors;
               break;
            }
         }
      }
      if (constructors.length > 0)
      {
         for (int i = 0; i < constructors.length; i++)
         {
            SecurityActions.setAccessible(constructors[i]);
         }
         Arrays.sort(constructors, ConstructorComparator.INSTANCE);
      }
   }

   protected void createInterceptorChains()
   {
      initializeMethodChain();
      initializeConstructorChain();
      makeInterceptorChains();
   }
   
   protected void updateInterceptorChains()
   {
      resetChain(this.methodInfos);
      resetChain(this.constructorInfos);
      makeInterceptorChains();
   }

   private void makeInterceptorChains()
   {
      ClassifiedBindingAndPointcutCollection bindingCol = manager.getBindingCollection();
      AspectManager.lock.lockRead();
      try
      {
         Collection<AdviceBinding> bindings = bindingCol.getConstructorExecutionBindings(); 
         for (AdviceBinding binding : bindings)
         {
            if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("iterate binding " + binding.getName());
            resolveConstructorPointcut(binding);
         }
         bindings = bindingCol.getMethodExecutionBindings(); 
         for (AdviceBinding binding : bindings)
         {
            if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("iterate binding " + binding.getName());
            resolveMethodPointcut(binding);
         }
      }
      finally
      {
         AspectManager.lock.unlockRead();
      }
      finalizeChain(constructorInfos);
      finalizeMethodChain();
      populateInterceptorsFromInfos();

      doesHaveAspects = adviceBindings.size() > 0;
   }
   
   /**
    * @return the value of chainOverridingForInheritedMethods
    * @see Advisor#chainOverridingForInheritedMethods()
    */
   @Override
   public boolean chainOverridingForInheritedMethods()
   {
      return chainOverridingForInheritedMethods;
   }

   /**
    * @param overriding the new value of chainOverridingForInheritedMethods
    * @see Advisor#chainOverridingForInheritedMethods()
    */
   protected void setChainOverridingForInheritedMethods(boolean overriding)
   {
      this.chainOverridingForInheritedMethods = overriding;
   }
}
