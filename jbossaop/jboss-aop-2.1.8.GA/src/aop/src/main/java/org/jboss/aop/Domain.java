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

import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceStack;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.ClassifiedBindingAndPointcutCollection;
import org.jboss.aop.advice.DynamicCFlowDefinition;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.PrecedenceDef;
import org.jboss.aop.array.ArrayReplacement;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.metadata.ClassMetaDataBinding;
import org.jboss.aop.metadata.ClassMetaDataLoader;
import org.jboss.aop.microcontainer.lifecycle.LifecycleCallbackBinding;
import org.jboss.aop.pointcut.CFlowStack;
import org.jboss.aop.pointcut.DynamicCFlow;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.pointcut.PointcutInfo;
import org.jboss.aop.pointcut.Typedef;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.util.UnmodifiableEmptyCollections;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author adrian@jboss.org
 * @version $Revision: 87258 $
 */
public class Domain extends AspectManager
{
   String name;
   protected AspectManager parent;
   protected boolean parentFirst;
   protected boolean inheritsDeclarations = true;
   protected boolean inheritsBindings = false;

   //Calculating the size of the collections containing this information is timeconsuming, we
   //only want to do this when adding/removing stuff
   protected boolean hasOwnPointcuts;
   protected boolean hasOwnBindings;
   protected boolean hasOwnAnnotationIntroductions;
   protected boolean hasOwnAnnotationOverrides;
   protected boolean hasOwnInterfaceIntroductions;
   protected boolean hasOwnTypedefs;
   protected boolean hasOwnPrecedenceDefs;
   protected boolean hasOwnClassMetaData;
   private static int sequenceNumber;

   public Domain(AspectManager manager, String name, boolean parentFirst)
   {
      this.parent = manager;
      this.parentFirst = parentFirst;
      this.name = name;
      manager.addSubDomainByName(this);
   }

   /**
    * Creates the binding collection to be used as the collection by this domain
    * @return a {@link DomainClassifiedBindingAndPointcutCollection}
    */
   @Override
   protected ClassifiedBindingAndPointcutCollection createBindingCollection()
   {
      return new DomainClassifiedBindingAndPointcutCollection();
   }

   // FIXME: JBAOP-107 REMOVE THIS HACK
   public boolean isValid()
   {
      return true;
   }
   
   public String getDomainName()
   {
      return name;
   }

   public String getManagerFQN()
   {
      return parent.getManagerFQN() + name + "/";
   }

   public static String getDomainName(final Class<?> clazz, final boolean forInstance)
   {
      String name = AccessController.doPrivileged(new PrivilegedAction<String>() {

         public String run()
         {
            StringBuffer sb = new StringBuffer();
            sb.append(clazz.getName());
            sb.append("_");
            sb.append(System.identityHashCode(SecurityActions.getClassLoader(clazz)));

            if (forInstance)
            {
               sb.append("_");
               sb.append(getNextSequenceNumber());
            }
            return sb.toString();
         }
      });
      return name;
   }

   private synchronized static int getNextSequenceNumber()
   {
      return sequenceNumber++;
   }
   /**
    * Inherits interceptor, aspect, advice stack definitions
    *
    * @param inheritsDeclarations
    */
   public void setInheritsDeclarations(boolean inheritsDeclarations)
   {
      this.inheritsDeclarations = inheritsDeclarations;
   }

   public void setInheritsBindings(boolean inheritBindings)
   {
      this.inheritsBindings = inheritBindings;
      if (inheritsBindings)
      {
         parent.subscribeSubDomain(this);
      }
      else
      {
         parent.unsubscribeSubDomain(this);
      }
   }
   
   @Override
   public LinkedHashMap<String, AdviceBinding> getBindings()
   {
      if (inheritsBindings)
      {
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            LinkedHashMap<String, AdviceBinding> map = new LinkedHashMap<String, AdviceBinding>(parent.getBindings());
            map.putAll(this.bindingCollection.getBindings());
            return map;
         }
         else
         {
            LinkedHashMap<String, AdviceBinding> map = new LinkedHashMap<String, AdviceBinding>(this.bindingCollection.getBindings());
            map.putAll(parent.getBindings());
            return map;
         }
      }
      return super.getBindings();
   }

   public boolean hasOwnBindings()
   {
      return hasOwnBindings;
   }

   @Override
   public void addBinding(AdviceBinding binding)
   {
      lock.lockWrite();
      try
      {
         hasOwnPointcuts = true;
         hasOwnBindings = true;
         super.addBinding(binding);
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public void removeBinding(String name)
   {
      lock.lockWrite();
      try
      {
         super.removeBinding(name);
         hasOwnBindings = !bindingCollection.isEmpty();
         hasOwnPointcuts = !bindingCollection.hasPointcuts();
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public void removeBindings(ArrayList<String> binds)
   {
      lock.lockWrite();
      try
      {
         super.removeBindings(binds);
         hasOwnBindings = !bindingCollection.isEmpty();
         hasOwnPointcuts = !bindingCollection.hasPointcuts();
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public LinkedHashMap<String, Pointcut> getPointcuts()
   {
      return bindingCollection.getPointcuts(); 
   }

   public boolean hasOwnPointcuts()
   {
      return hasOwnPointcuts;
   }
     
   @Override
   public void addPointcut(Pointcut pointcut)
   {
      lock.lockWrite();
      try
      {
         hasOwnPointcuts = true;
         super.addPointcut(pointcut);
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   @Override
   public void removePointcut(String name)
   {
      lock.lockWrite();
      try
      {
         super.removePointcut(name);
         hasOwnPointcuts = bindingCollection.hasPointcuts();
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   @Override
   public LinkedHashMap<String, PointcutInfo> getPointcutInfos()
   {
      return bindingCollection.getPointcutInfos();
   }

   @Override
   public List<AnnotationIntroduction> getAnnotationIntroductions()
   {

      if (inheritsBindings)
      {
         List<AnnotationIntroduction> result = new ArrayList<AnnotationIntroduction>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            result.addAll(parent.getAnnotationIntroductions());
            synchronized (annotationIntroductions)
            {
               result = new ArrayList<AnnotationIntroduction>(annotationIntroductions.values());
            }
            return result;
         }
         else
         {
            synchronized (annotationIntroductions)
            {
               result = new ArrayList<AnnotationIntroduction>(annotationIntroductions.values());
            }
            result.addAll(parent.getAnnotationIntroductions());
            return result;
         }
      }

      return super.getAnnotationIntroductions();
   }
   
   @Override
   public Map<String, ArrayReplacement> getArrayReplacements()
   {
      if (inheritsBindings)
      {
         HashMap<String, ArrayReplacement> map = new HashMap<String, ArrayReplacement>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getArrayReplacements());
            synchronized (arrayReplacements)
            {
               map.putAll(arrayReplacements);
            }
            return map;
         }
         else
         {
            synchronized (arrayReplacements)
            {
               map.putAll(arrayReplacements);
            }
            map.putAll(parent.getArrayReplacements());
            return map;
         }
      }
      return super.getArrayReplacements();
   }


   public boolean hasOwnAnnotationIntroductions()
   {
      return hasOwnAnnotationIntroductions;
   }

   @Override
   public void addAnnotationIntroduction(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         hasOwnAnnotationIntroductions = true;
         super.addAnnotationIntroduction(pointcut);
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public void removeAnnotationIntroduction(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         super.removeAnnotationIntroduction(pointcut);
         hasOwnAnnotationIntroductions = annotationIntroductions.size() > 0;
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public List<AnnotationIntroduction> getAnnotationOverrides()
   {
      if (inheritsBindings)
      {
         ArrayList<AnnotationIntroduction> list = new ArrayList<AnnotationIntroduction>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            list.addAll(parent.getAnnotationOverrides());
            synchronized (annotationOverrides)
            {
               list.addAll(annotationOverrides.values());
            }
            return list;
         }
         else
         {
            synchronized (annotationOverrides)
            {
               list.addAll(annotationOverrides.values());
            }
            list.addAll(parent.getAnnotationOverrides());
            return list;
         }
      }
      return super.getAnnotationOverrides();
   }

   public boolean hasOwnAnnotationOverrides()
   {
      return hasOwnAnnotationOverrides;
   }

   @Override
   public void addAnnotationOverride(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         hasOwnAnnotationOverrides = true;
         super.addAnnotationOverride(pointcut);
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   @Override
   public void removeAnnotationOverride(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         super.removeAnnotationOverride(pointcut);
         hasOwnAnnotationOverrides = annotationOverrides.size() > 0;
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public Map<String, InterfaceIntroduction> getInterfaceIntroductions()
   {
      if (inheritsBindings)
      {
         HashMap<String, InterfaceIntroduction> map = new HashMap<String, InterfaceIntroduction>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getInterfaceIntroductions());
            synchronized (interfaceIntroductions)
            {
               map.putAll(interfaceIntroductions);
            }
            return map;
         }
         else
         {
            synchronized (interfaceIntroductions)
            {
               map.putAll(interfaceIntroductions);
            }
            map.putAll(parent.getInterfaceIntroductions());
            return map;
         }
      }
      return super.getInterfaceIntroductions();
   }

   public boolean hasOwnInterfaceIntroductions()
   {
      return hasOwnInterfaceIntroductions;
   }

   @Override
   public void addInterfaceIntroduction(InterfaceIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         hasOwnInterfaceIntroductions = true;
         super.addInterfaceIntroduction(pointcut);
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public void removeInterfaceIntroduction(String name)
   {
      lock.lockWrite();
      try
      {
         super.removeInterfaceIntroduction(name);
         hasOwnInterfaceIntroductions = interfaceIntroductions.size() > 0;
      }
      finally
      {
         lock.unlockWrite();
      }
   }
   
   @Override
   public Map<String, Typedef> getTypedefs()
   {
      if (inheritsBindings)
      {
         HashMap<String, Typedef> map = new HashMap<String, Typedef>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getTypedefs());
            synchronized (typedefs)
            {
               map.putAll(typedefs);
            }
            return map;
         }
         else
         {
            synchronized (typedefs)
            {
               map.putAll(typedefs);
            }
            map.putAll(parent.getTypedefs());
            return map;
         }
      }
      return super.getTypedefs();
   }


   public boolean hasOwnTypedefs()
   {
      return hasOwnTypedefs;
   }

   @Override
   public void addTypedef(Typedef def) throws Exception
   {
      lock.lockWrite();
      try
      {
         hasOwnTypedefs = true;
         super.addTypedef(def);
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   @Override
   public void removeTypedef(String name)
   {
      lock.lockWrite();
      try
      {
         super.removeTypedef(name);
         hasOwnTypedefs = typedefs.size() > 0;
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public Map<String, AdviceStack> getInterceptorStacks()
   {
      if (inheritsBindings)
      {
         HashMap<String, AdviceStack> map = new HashMap<String, AdviceStack>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getInterceptorStacks());
            synchronized (interceptorStacks)
            {
               map.putAll(interceptorStacks);
            }
            return map;
         }
         else
         {
            synchronized (interceptorStacks)
            {
               map.putAll(interceptorStacks);
            }
            map.putAll(parent.getInterceptorStacks());
            return map;
         }
      }
      return super.getInterceptorStacks();
   }

   @Override
   public Map<String, ClassMetaDataLoader> getClassMetaDataLoaders()
   {
      if (inheritsBindings)
      {
         HashMap<String, ClassMetaDataLoader> map = new HashMap<String, ClassMetaDataLoader>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getClassMetaDataLoaders());
            synchronized (classMetaDataLoaders)
            {
               map.putAll(classMetaDataLoaders);
            }
            return map;
         }
         else
         {
            synchronized (classMetaDataLoaders)
            {
               map.putAll(classMetaDataLoaders);
            }
            map.putAll(parent.getClassMetaDataLoaders());
            return map;
         }
      }
      return super.getClassMetaDataLoaders();
   }

   @Override
   public Map<String, CFlowStack> getCflowStacks()
   {
      if (inheritsBindings)
      {
         HashMap<String, CFlowStack> map = new HashMap<String, CFlowStack>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getCflowStacks());
            synchronized (cflowStacks)
            {
               map.putAll(cflowStacks);
            }
            return map;
         }
         else
         {
            synchronized (cflowStacks)
            {
               map.putAll(cflowStacks);
            }
            map.putAll(parent.getCflowStacks());
            return map;
         }
      }
      return super.getCflowStacks();
   }

   @Override
   public Map<String, DynamicCFlowDefinition> getDynamicCFlows()
   {
      if (inheritsBindings)
      {
         HashMap<String, DynamicCFlowDefinition> map = new HashMap<String, DynamicCFlowDefinition>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getDynamicCFlows());
            synchronized (dynamicCFlows)
            {
               map.putAll(dynamicCFlows);
            }
            return map;
         }
         else
         {
            synchronized (dynamicCFlows)
            {
               map.putAll(dynamicCFlows);
            }
            map.putAll(parent.getDynamicCFlows());
            return map;
         }
      }
      return super.getDynamicCFlows();
   }

   @Override
   public Map<String, Object> getPerVMAspects()
   {
      if (inheritsBindings)
      {
         HashMap<String, Object> map = new HashMap<String, Object>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getPerVMAspects());
            synchronized (perVMAspects)
            {
               map.putAll(perVMAspects);
            }
            return map;
         }
         else
         {
            synchronized (perVMAspects)
            {
               map.putAll(perVMAspects);
            }
            map.putAll(parent.getPerVMAspects());
            return map;
         }
      }
      return super.getPerVMAspects();
   }

   @Override
   public LinkedHashMap<String, PrecedenceDef> getPrecedenceDefs()
   {
      if (inheritsDeclarations)
      {
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            LinkedHashMap<String, PrecedenceDef> map = new LinkedHashMap<String, PrecedenceDef>(parent.getPrecedenceDefs());
            map.putAll(this.precedenceDefs);
            return map;
         }
         else
         {
            LinkedHashMap<String, PrecedenceDef> map = new LinkedHashMap<String, PrecedenceDef>(this.precedenceDefs);
            map.putAll(parent.getPrecedenceDefs());
            return map;
         }
      }
      return super.getPrecedenceDefs();
   }

   public boolean hasOwnPrecedenceDefs()
   {
      return hasOwnPrecedenceDefs;
   }

   @Override
   public void addPrecedence(PrecedenceDef precedenceDef)
   {
      hasOwnPrecedenceDefs = true;
      super.addPrecedence(precedenceDef);
   }

   @Override
   public void removePrecedence(String name)
   {
      super.removePrecedence(name);
      hasOwnPrecedenceDefs = precedenceDefs.size() > 0;
   }

   @Override
   public Map<String, ClassMetaDataBinding> getClassMetaData()
   {
      if (inheritsBindings)
      {
         HashMap<String, ClassMetaDataBinding> map = new HashMap<String, ClassMetaDataBinding>();
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            map.putAll(parent.getClassMetaData());
            synchronized (classMetaData)
            {
               map.putAll(classMetaData);
            }
            return map;
         }
         else
         {
            synchronized (classMetaData)
            {
               map.putAll(classMetaData);
            }
            map.putAll(parent.getClassMetaData());
            return map;
         }
      }
      return super.getClassMetaData();
   }

   public boolean hasOwnClassMetaData()
   {
      return hasOwnClassMetaData;
   }

   @Override
   public void removeClassMetaData(String name)
   {
      super.removeClassMetaData(name);
      hasOwnClassMetaData = classMetaData.size() > 0;
   }

   @Override
   public void addClassMetaData(ClassMetaDataBinding meta)
   {
      hasOwnClassMetaData = true;
      super.addClassMetaData(meta);
   }

   public boolean hasOwnDataWithEffectOnAdvices()
   {
      return hasOwnBindings ||
      hasOwnPointcuts ||
      hasOwnAnnotationIntroductions ||
      hasOwnAnnotationOverrides ||
      hasOwnInterfaceIntroductions ||
      hasOwnTypedefs ||
      hasOwnPrecedenceDefs ||
      hasOwnClassMetaData;
   }

   @Override
   public InterceptorFactory getInterceptorFactory(String name)
   {
      InterceptorFactory factory = null;
      if (parentFirst)
      {
         factory = parent.getInterceptorFactory(name);
         if (factory != null) return factory;
      }
      factory = super.getInterceptorFactory(name);

      if (factory != null) return factory;
      return parent.getInterceptorFactory(name);
   }

   @Override
   public AdviceStack getAdviceStack(String name)
   {
      AdviceStack factory = null;
      if (parentFirst)
      {
         factory = parent.getAdviceStack(name);
         if (factory != null) return factory;
      }
      factory = super.getAdviceStack(name);

      if (factory != null) return factory;
      return parent.getAdviceStack(name);
   }

   @Override
   public Object getPerVMAspect(AspectDefinition def)
   {
      return getPerVMAspect(def.getName());
   }

   @Override
   public Object getPerVMAspect(String def)
   {
      Object factory = null;
      if (parentFirst)
      {
         factory = parent.getPerVMAspect(def);
         if (factory != null) return factory;
      }
      factory = super.getPerVMAspect(def);

      if (factory != null) return factory;
      return parent.getPerVMAspect(def);
   }

   @Override
   public AspectDefinition getAspectDefinition(String name)
   {
      AspectDefinition factory = null;
      if (parentFirst)
      {
         factory = parent.getAspectDefinition(name);
         if (factory != null) return factory;
      }
      factory = super.getAspectDefinition(name);

      if (factory != null) return factory;
      return parent.getAspectDefinition(name);
   }

   @Override
   public Typedef getTypedef(String name)
   {
      Typedef factory = null;
      if (parentFirst)
      {
         factory = parent.getTypedef(name);
         if (factory != null) return factory;
      }
      factory = super.getTypedef(name);

      if (factory != null) return factory;
      return parent.getTypedef(name);
   }

   @Override
   public DomainDefinition getContainer(String name)
   {
      DomainDefinition container = null;
      if (parentFirst)
      {
         container = parent.getContainer(name);
         if (container != null) return container;
      }
      container = super.getContainer(name);
      if (container != null) return container;
      return parent.getContainer(name);
   }

   /**
    * Find a pointcut of with a given name
    */
   @Override
   public Pointcut getPointcut(String name)
   {
      Pointcut pointcut = null;

      if (parentFirst)
      {
         pointcut = parent.getPointcut(name);
         if (pointcut != null) return pointcut;
      }
      pointcut = super.getPointcut(name);
      if (pointcut != null) return pointcut;
      return parent.getPointcut(name);
   }

   @Override
   public void attachMetaData(Advisor advisor, Class<?> clazz)
   {
      if (inheritsBindings)
      {
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            parent.attachMetaData(advisor, clazz);
            super.attachMetaData(advisor, clazz);
         }
         else
         {
            super.attachMetaData(advisor, clazz);
            parent.attachMetaData(advisor, clazz);
         }
      }
      else
      {
         super.attachMetaData(advisor, clazz);
      }
   }

   @Override
   public CFlowStack getCFlowStack(String name)
   {
      if (inheritsDeclarations)
      {
         if (!parentFirst)
         {
            CFlowStack cflow = super.getCFlowStack(name);
            if (cflow == null)
            {
               cflow = parent.getCFlowStack(name);
            }
            return cflow;
         }
         else
         {
            CFlowStack cflow = parent.getCFlowStack(name);
            if (cflow == null)
            {
               cflow = super.getCFlowStack(name);
            }
            return cflow;
         }
      }
      else
      {
         return super.getCFlowStack(name);
      }
   }


   @Override
   public DynamicCFlow getDynamicCFlow(String name, ClassLoader cl)
   {
      if (inheritsBindings)
      {
         if (!parentFirst)
         {
            DynamicCFlow cflow = super.getDynamicCFlow(name, cl);
            if (cflow == null)
            {
               cflow = parent.getDynamicCFlow(name, cl);
            }
            return cflow;
         }
         else
         {
            DynamicCFlow cflow = parent.getDynamicCFlow(name, cl);
            if (cflow == null)
            {
               cflow = super.getDynamicCFlow(name, cl);
            }
            return cflow;
         }
      }
      else
      {
         return super.getDynamicCFlow(name, cl);
      }
   }

   @Override
   public ClassMetaDataLoader findClassMetaDataLoader(String group)
   {
      if (inheritsDeclarations)
      {
         if (!parentFirst)
         {
            ClassMetaDataLoader loader = super.findClassMetaDataLoader(group);
            if (loader == null)
            {
               loader = parent.findClassMetaDataLoader(group);
            }
            return loader;
         }
         else
         {
            ClassMetaDataLoader loader = parent.findClassMetaDataLoader(group);
            if (loader == null)
            {
               loader = super.findClassMetaDataLoader(group);
            }
            return loader;
         }
      }

      return super.findClassMetaDataLoader(group);
   }

   @Override
   public Map<String, LifecycleCallbackBinding> getLifecycleBindings()
   {
      if (inheritsBindings)
      {
         if (!parentFirst)
         {
            // when child first, parent bindings go in first so that they can be overridden by child.
            LinkedHashMap<String, LifecycleCallbackBinding> map = new LinkedHashMap<String, LifecycleCallbackBinding>(parent.getLifecycleBindings());
            map.putAll(super.getLifecycleBindings());
            return map;
         }
         else
         {
            LinkedHashMap<String, LifecycleCallbackBinding> map = new LinkedHashMap<String, LifecycleCallbackBinding>(super.getLifecycleBindings());
            map.putAll(parent.getLifecycleBindings());
            return map;
         }
      }
      return super.getLifecycleBindings();
   }



   //////////////////////////////////////////////////////////////////////////
   //Methods that should delegate to the top AspectManager

   @Override
   public InterceptionMarkers getInterceptionMarkers(ClassLoader loader)
   {
      return parent.getInterceptionMarkers(loader);
   }

   /** Managed by the top-level aspect manager */
   @Override
   protected Map<Class<?>, WeakReference<Domain>> getSubDomainsPerClass()
   {
      return parent.getSubDomainsPerClass();
   }

   /** Only set on a per vm basis */
   @Override
   public ArrayList<String> getExclude()
   {
      return parent.getExclude();
   }

   /** Only set on a per vm basis */
   @Override
   public ArrayList<String> getInclude()
   {
      return parent.getInclude();
   }

   /** Only set on a per vm basis */
   @Override
   public ArrayList<String> getIgnore()
   {
      return parent.getIgnore();
   }

   /** Only set on a per vm basis */
   @Override
   public ClassExpression[] getIgnoreExpressions()
   {
      return parent.getIgnoreExpressions();
   }
   
   /** Only set on a per vm basis */
   @Override
   public List<String> getIncludedInvisibleAnnotations()
   {
      return parent.getIncludedInvisibleAnnotations();
   }
   
   @Override
   public DynamicAOPStrategy getDynamicAOPStrategy()
   {
      return parent.getDynamicAOPStrategy();
   }

   @Override
   public void setDynamicAOPStrategy(DynamicAOPStrategy strategy)
   {
      parent.setDynamicAOPStrategy(strategy);
   }

   private class DomainClassifiedBindingAndPointcutCollection extends ClassifiedBindingAndPointcutCollection
   {

      @Override
      public LinkedHashMap<String, AdviceBinding> getBindings()
      {
         LinkedHashMap<String, AdviceBinding> result = super.getBindingsInternal();
         LinkedHashMap<String, AdviceBinding> parentResult = 
            inheritsBindings ? parent.getBindingCollection().getBindingsInternal() :
               UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
         LinkedHashMap<String, AdviceBinding> overall = unifyMaps(result, parentResult, parentFirst);
         if (overall == result || overall == parentResult)
         {
            return new LinkedHashMap<String, AdviceBinding>(overall);
         }
         return overall;
      }  

      @Override
      public LinkedHashMap<String, Pointcut> getPointcuts()
      {
         LinkedHashMap<String, Pointcut> result = super.getPointcutsInternal();
         LinkedHashMap<String, Pointcut> parentResult = 
               inheritsBindings ? 
                     parent.getBindingCollection().getPointcutsInternal() : UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
         LinkedHashMap<String, Pointcut> overall = unifyMaps(result, parentResult, parentFirst);
         if (overall == result || overall == parentResult)
         {
            return new LinkedHashMap<String, Pointcut>(overall);
         }
         return overall;
      }

      @Override
      public LinkedHashMap<String, PointcutInfo> getPointcutInfos()
      {
         LinkedHashMap<String, PointcutInfo> result = super.getPointcutInfosInternal();
         LinkedHashMap<String, PointcutInfo> parentResult = 
            inheritsBindings ?
                  parent.getBindingCollection().getPointcutInfosInternal() : UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
         LinkedHashMap<String, PointcutInfo> overall = unifyMaps(result, parentResult, parentFirst);
         if (overall == result || overall == parentResult)
         {
            return new LinkedHashMap<String, PointcutInfo>(overall);
         }
         return overall;
      }


      @Override
      public Collection<AdviceBinding> getFieldReadBindings()
      {
         Collection<AdviceBinding> result = super.getFieldReadBindings();
         Collection<AdviceBinding> parentResult = 
               inheritsBindings ?
                     parent.getBindingCollection().getFieldReadBindings() : UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      @Override
      public Collection<AdviceBinding> getFieldWriteBindings()
      {
         Collection<AdviceBinding> result = super.getFieldWriteBindings();
         Collection<AdviceBinding> parentResult = 
            inheritsBindings ? parent.getBindingCollection().getFieldWriteBindings():
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      @Override
      public Collection<AdviceBinding> getConstructionBindings()
      {
         Collection<AdviceBinding> result = super.getConstructionBindings();
         Collection<AdviceBinding> parentResult = 
            inheritsBindings ? parent.getBindingCollection().getConstructionBindings() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      @Override
      public Collection<AdviceBinding> getConstructorExecutionBindings()
      {
         Collection<AdviceBinding> result = super.getConstructorExecutionBindings();
            Collection<AdviceBinding> parentResult = 
               inheritsBindings ?
                     parent.getBindingCollection().getConstructorExecutionBindings() : UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
            return unifyCollections(result, parentResult, parentFirst);
      }
      
      @Override
      public Collection<AdviceBinding> getMethodExecutionBindings()
      {
         Collection<AdviceBinding> result = super.getMethodExecutionBindings();
         Collection<AdviceBinding> parentResult = 
            inheritsBindings ?
            parent.getBindingCollection().getMethodExecutionBindings() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      @Override
      public Collection<AdviceBinding> getConstructorCallBindings()
      {
         Collection<AdviceBinding> result = super.getConstructorCallBindings();
         Collection<AdviceBinding> parentResult = 
            inheritsBindings ?
            parent.getBindingCollection().getConstructorCallBindings() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      @Override
      public Collection<AdviceBinding> getMethodCallBindings()
      {
         Collection<AdviceBinding> result = super.getMethodCallBindings();
         Collection<AdviceBinding> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getMethodCallBindings() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }

      public Collection<Pointcut> getFieldReadPointcuts()
      {
         Collection<Pointcut> result = super.getFieldReadPointcuts();
         Collection<Pointcut> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getFieldReadPointcuts() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<Pointcut> getFieldWritePointcuts()
      {
         Collection<Pointcut> result = super.getFieldWritePointcuts();
         Collection<Pointcut> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getFieldWritePointcuts() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<Pointcut> getConstructionPointcuts()
      {
         Collection<Pointcut> result = super.getConstructionPointcuts();
         Collection<Pointcut> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getConstructionPointcuts() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<Pointcut> getConstructorExecutionPointcuts()
      {
         Collection<Pointcut> result = super.getConstructorExecutionPointcuts();
         Collection<Pointcut> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getConstructorExecutionPointcuts() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<Pointcut> getMethodExecutionPointcuts()
      {
         Collection<Pointcut> result = super.getMethodExecutionPointcuts();
         Collection<Pointcut> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getMethodExecutionPointcuts() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<Pointcut> getConstructorCallPointcuts()
      {
         Collection<Pointcut> result = super.getConstructorCallPointcuts();
         Collection<Pointcut> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getConstructorCallPointcuts() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<Pointcut> getMethodCallPointcuts()
      {
         Collection<Pointcut> result = super.getMethodCallPointcuts();
         Collection<Pointcut> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getMethodCallPointcuts() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<PointcutInfo> getFieldReadPointcutInfos()
      {
         Collection<PointcutInfo> result = super.getFieldReadPointcutInfos();
         Collection<PointcutInfo> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getFieldReadPointcutInfos() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<PointcutInfo> getFieldWritePointcutInfos()
      {
         Collection<PointcutInfo> result = super.getFieldWritePointcutInfos();
         Collection<PointcutInfo> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getFieldWritePointcutInfos() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<PointcutInfo> getConstructionPointcutInfos()
      {
         Collection<PointcutInfo> result = super.getConstructionPointcutInfos();
         Collection<PointcutInfo> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getConstructionPointcutInfos() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<PointcutInfo> getConstructorExecutionPointcutInfos()
      {
         Collection<PointcutInfo> result = super.getConstructorExecutionPointcutInfos();
         Collection<PointcutInfo> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getConstructorExecutionPointcutInfos() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<PointcutInfo> getMethodExecutionPointcutInfos()
      {
         Collection<PointcutInfo> result = super.getMethodExecutionPointcutInfos();
         Collection<PointcutInfo> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getMethodExecutionPointcutInfos() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<PointcutInfo> getConstructorCallPointcutInfos()
      {
         Collection<PointcutInfo> result = super.getConstructorCallPointcutInfos();
         Collection<PointcutInfo> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getConstructorCallPointcutInfos() :
            UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }
      
      public Collection<PointcutInfo> getMethodCallPointcutInfos()
      {
         Collection<PointcutInfo> result = super.getMethodCallPointcutInfos();
         Collection<PointcutInfo> parentResult =
            inheritsBindings ?
            parent.getBindingCollection().getMethodCallPointcutInfos() : UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;
         return unifyCollections(result, parentResult, parentFirst);
      }

      @Override
      public boolean isExecution()
      {
         if (super.isExecution())
         {
            return true;
         }
         if (inheritsBindings)
         {
            return parent.isExecution();
         }
         return false;
      }

      @Override
      public boolean isConstruction()
      {
         if (super.isConstruction())
         {
            return true;
         }
         if (inheritsBindings)
         {
            return parent.isConstruction();
         }
         return false;
      }

      @Override
      public boolean isCall()
      {
         if (super.isCall())
         {
            return true;
         }
         if (inheritsBindings)
         {
            return parent.isCall();
         }
         return false;
      }

      @Override
      public boolean isWithin()
      {
         if (super.isWithin())
         {
            return true;
         }
         if (inheritsBindings)
         {
            return parent.isWithin();
         }
         return false;
      }

      @Override
      public boolean isWithincode()
      {
         if (super.isWithincode())
         {
            return true;
         }
         if (inheritsBindings)
         {
            return parent.isWithincode();
         }
         return false;
      }

      @Override
      public boolean isGet()
      {
         if (super.isGet())
         {
            return true;
         }
         if (inheritsBindings)
         {
            return parent.isGet();
         }
         return false;
      }

      @Override
      public boolean isSet()
      {
         if (super.isSet())
         {
            return true;
         }
         if (inheritsBindings)
         {
            return parent.isSet();
         }
         return false;
      }
      
      private <T> Collection<T> unifyCollections(Collection<T> collection1,
            Collection<T> collection2, boolean prioritizeFirst)
      {
         if (collection1.isEmpty())
         {
            return collection2;
         }
         if (collection2.isEmpty())
         {
            return collection1;
         }
         if (prioritizeFirst)
         {
            collection1 = new LinkedHashSet<T>(collection1);
            collection1.addAll(collection2);
         }
         else
         {
            Collection<T> temp = collection1;
            collection1 = new LinkedHashSet<T>(collection2);
            collection1.addAll(temp);
         }
         return collection1;
      }

      private <T, K> LinkedHashMap<T, K> unifyMaps(LinkedHashMap<T, K> map1,
            LinkedHashMap<T, K> map2, boolean prioritizeFirst)
      {
         if (map1.isEmpty())
         {
            return map2;
         }
         if (map2.isEmpty())
         {
            return map1;
         }
         if (prioritizeFirst)
         {
            map1 = new LinkedHashMap<T, K>(map1);
            map1.putAll(map2);
         }
         else
         {
            LinkedHashMap<T, K> temp = map1;
            map1 = new LinkedHashMap<T, K>(map2);
            map1.putAll(temp);
         }
         return map1;
      }
   }
}