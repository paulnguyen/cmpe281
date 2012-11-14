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

import gnu.trove.TLongObjectHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.GeneratedAdvisorInterceptor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.PrecedenceSorter;
import org.jboss.aop.instrument.ConByConJoinPointGenerator;
import org.jboss.aop.instrument.ConByMethodJoinPointGenerator;
import org.jboss.aop.instrument.ConstructionJoinPointGenerator;
import org.jboss.aop.instrument.ConstructorJoinPointGenerator;
import org.jboss.aop.instrument.FieldJoinPointGenerator;
import org.jboss.aop.instrument.JoinPointGenerator;
import org.jboss.aop.instrument.MethodByConJoinPointGenerator;
import org.jboss.aop.instrument.MethodByMethodJoinPointGenerator;
import org.jboss.aop.instrument.MethodJoinPointGenerator;
import org.jboss.aop.joinpoint.FieldJoinpoint;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.joinpoint.MethodJoinpoint;
import org.jboss.aop.pointcut.PointcutMethodMatch;
import org.jboss.aop.util.UnmodifiableEmptyCollections;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Comment
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class GeneratedClassAdvisor extends ClassAdvisor
{
   public static final String ADD_METHOD_INFO = "addMethodInfo";
   public static final String ADD_CONSTRUCTOR_INFO = "addConstructorInfo";
   public static final String ADD_CONSTRUCTION_INFO = "addConstructionInfo";
   public static final String ADD_FIELD_READ_INFO = "addFieldReadInfo";
   public static final String ADD_FIELD_WRITE_INFO = "addFieldWriteInfo";
   public static final String GET_PARENT_ADVISOR = "getParentAdvisor";

   private static final AOPLogger logger = AOPLogger.getLogger(GeneratedClassAdvisor.class);
   
// TODO Flavia   
//   ArrayList<ConstructorInfo> constructorInfos = new ArrayList();
//   ArrayList<ConstructionInfo> constructionInfos = new ArrayList();
//   ArrayList<FieldInfo> fieldReadInfos = new ArrayList();
//   ArrayList<FieldInfo> fieldWriteInfos = new ArrayList();
   /** Super class methods that have been overrridden - these need special handling in this weaving mode */
   ArrayList<MethodInfo> overriddenMethods = new ArrayList<MethodInfo>(); 

   //TODO These are only needed for the class advisor really
   //All joinpoint generators apart from field reads, constructions and MethodCalledByXXX go in here
   private volatile ConcurrentHashMap<Joinpoint, JoinPointGenerator> joinPointGenerators = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   //Needs its own map to avoid crashing with the field write generators
   private volatile ConcurrentHashMap<Joinpoint, FieldJoinPointGenerator> fieldReadJoinPoinGenerators = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   //Needs its own map to avoid crashing with the constructor generators
   private volatile ConcurrentHashMap<Joinpoint, ConstructorJoinPointGenerator> constructionJoinPointGenerators = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   //An extra level of indirection is needed for MethodCalledByCon and MethodCalledByMethod as compared to the main joinPointGenerators map
   private volatile ConcurrentHashMap<Joinpoint, ConcurrentHashMap<Class<?>, JoinPointGenerator>> methodCalledByXXXJoinPointGenerators = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   
   ConcurrentHashMap<Joinpoint, Interceptor[]> oldInfos = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   ConcurrentHashMap<Joinpoint, Interceptor[]> oldFieldReadInfos = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   ConcurrentHashMap<Joinpoint, Interceptor[]> oldConstructionInfos = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;

   boolean initialisedSuperClasses; 

   private int version;
   
   /**
    * Different behaviour depending on if we are a class advisor or instance advisor
    */
   AdvisorStrategy advisorStrategy;

   protected GeneratedClassAdvisor(String classname)
   {
      //Generated advisors will not pass in an aspectmanager
      //This will be passed in via the initialise() method
      super(classname, null);
      advisorStrategy = new ClassAdvisorStrategy(); 
   }
   
   protected GeneratedClassAdvisor(String classname, GeneratedClassAdvisor parent)
   {
      super(classname, null);
      advisorStrategy = new InstanceAdvisorStrategy(parent);
   }

   @Override
   public void cleanup()
   {
      super.cleanup();
      methodInfos = null;
      advisorStrategy = null;
      
      Map<Domain, Object> subscribedSubDomains = getManager().getSubscribedSubDomains();
      synchronized (subscribedSubDomains)
      {
         for (Iterator<Domain> it = subscribedSubDomains.keySet().iterator() ; it.hasNext() ; )
         {
            //TODO Not really sure what was attempted here
            //GeneratedAdvisorDomain manager = (GeneratedAdvisorDomain)it.next();
            //Map advisors = manager.getAdvisors();
            if (it.next() != null)
            {
               it.remove();
            }
         }
      }
   }

   protected void initialise(Class<?> clazz, AspectManager manager)
   {
      advisorStrategy.initialise(clazz, manager);
   }

   /**
    * Generated class advisor sub class will override
    */
   protected void initialiseCallers()
   {
   }

   /**
    * Generated instance advisor sub class will override
    */
   protected void initialiseInfosForInstance()
   {
      

   }
   
   /**
    * To be called by initialiseInfosForInstance() in the generated instance advisors
    */
   protected MethodInfo copyInfoFromClassAdvisor(MethodInfo info)
   {
      MethodInfo copy = (MethodInfo)info.copy();
      copy.setAdvisor(this);
      addMethodInfo(copy);
      return copy;
   }
   
   /**
    * To be called by initialiseInfosForInstance() in the generated instance advisors
    */
   protected FieldInfo copyInfoFromClassAdvisor(FieldInfo info)
   {
      FieldInfo copy = (FieldInfo)info.copy();
      copy.setAdvisor(this);
// TODO Flavia
//      if (copy.isRead())
//      {
//         addFieldReadInfo(copy);
//      }
//      else
//      {
//         addFieldWriteInfo(copy);
//      }
      return copy;
   }

   /**
    * To be called by initialiseInfosForInstance() in the generated instance advisors
    */
   protected ConByConInfo copyInfoFromClassAdvisor(ConByConInfo info)
   {
      ConByConInfo copy = (ConByConInfo)info.copy();
      copy.setAdvisor(this);
      return copy;
   }

   /**
    * To be called by initialiseInfosForInstance() in the generated instance advisors
    */
   protected MethodByConInfo copyInfoFromClassAdvisor(MethodByConInfo info)
   {
      MethodByConInfo copy = (MethodByConInfo)info.copy();
      copy.setAdvisor(this);
      return copy;
   }

   /**
    * To be called by initialiseInfosForInstance() in the generated instance advisors
    */
   protected ConByMethodInfo copyInfoFromClassAdvisor(ConByMethodInfo info)
   {
      ConByMethodInfo copy = (ConByMethodInfo)info.copy();
      copy.setAdvisor(this);
      return copy;
   }
   
   /**
    * To be called by initialiseInfosForInstance() in the generated instance advisors
    */
   protected MethodByMethodInfo copyInfoFromClassAdvisor(MethodByMethodInfo info)
   {
      MethodByMethodInfo copy = (MethodByMethodInfo)info.copy();
      copy.setAdvisor(this);
      return copy;
   }
   
   
   @Override
   protected void rebuildInterceptors()
   {
      version++;
      advisorStrategy.rebuildInterceptors();
   }
   
   @Override
   protected void rebuildInterceptorsForAddedBinding(AdviceBinding binding)
   {
      version++;
      advisorStrategy.rebuildInterceptorsForAddedBinding(binding);
   }
   
   @Override
   protected void rebuildInterceptorsForRemovedBinding(AdviceBinding binding)
   {
      version++;
      advisorStrategy.rebuildInterceptorsForRemovedBinding(binding);
   }
   
   /**
    * Callback for instance advisors to rebuild their interceptors when their
    * version number is out of sync
    */
   protected synchronized void internalRebuildInterceptors()
   {
      super.rebuildInterceptors();
   }

   /**
    * Callback for generated instance advisors to check if the version has been updated 
    */
   protected void checkVersion()
   {
      advisorStrategy.checkVersion();
   }

   /**
    * Will be overridden by generated instanceadvisor classes and perform a rebuild
    */
   protected void doRebuildForInstance()
   {
      
   }

   protected void handleOverriddenMethods(AdviceBinding binding)
   {
      if (overriddenMethods != null && overriddenMethods.size() > 0)
      {
         for(MethodInfo info : overriddenMethods)
         {
            Method method = info.getMethod();
            PointcutMethodMatch match = binding.getPointcut().matchesExecution(this, method);
            
            if (match != null && match.isMatch())
            {
               adviceBindings.add(binding);
               if (AspectManager.verbose)
               {
                  logger.error("method matched binding " + binding.getPointcut().getExpr() + " " + method.toString());     
               }
               binding.addAdvisor(this);
               pointcutResolved(info, binding, new MethodJoinpoint(method));
            }
         }
      }      
   }

   @Override
   protected void resolveMethodPointcut(AdviceBinding binding)
   {
      GeneratedClassAdvisor classAdvisor = getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
      if (classAdvisor == null)
      {
         //We are either the class advisor or an instanceadvisor with own data so we need to do all the work
         super.resolveMethodPointcut(binding);
         handleOverriddenMethods(binding);
      }
   }

   @Override
   protected void resolveFieldPointcut(FieldInfo[] newFieldInfos, Interceptor[][] fieldInterceptors, AdviceBinding binding, boolean write)
   {
      GeneratedClassAdvisor classAdvisor = getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
      if (classAdvisor == null)
      {
         //We are either the class advisor or an instanceadvisor with own data so we need to do all the work
         super.resolveFieldPointcut(newFieldInfos, fieldInterceptors, binding, write);
      }
   }

   @Override
   protected void resolveConstructorPointcut( AdviceBinding binding)
   {
      advisorStrategy.resolveConstructorPointcut(binding);
   }

   @Override
   protected void resolveConstructionPointcut(AdviceBinding binding)
   {
      advisorStrategy.resolveConstructionPointcut(binding);
   }
   
   /**
    * Generated class advisor sub class will override
    */
   protected void initialiseMethods()
   {
   }
   
   /**
    * Called by initialiseMethods() in generated advisor sub classes
    */
   protected void addMethodInfo(MethodInfo mi)
   {
      MethodInfo old = methodInfos.getMethodInfo(mi.getHash());
      if (old != null)
      {
         overriddenMethods.add(old);
      }
      methodInfos.put(mi.getHash(), mi);
      advisorStrategy.makeAccessibleMethod(mi);
   }

   @Override
   protected void initializeMethodChain()
   {
      //We have all the advised methods here, need to get all the others here too

      long[] keys = advisedMethods.keys();
      for (int i = 0; i < keys.length; i++)
      {
         MethodMatchInfo matchInfo = methodInfos.getMatchInfo(keys[i]);

         if (super.initialized && matchInfo != null)
         {
            matchInfo.clear();
         }

         if (matchInfo == null)
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
   }


   /**
    * Generated class advisor sub class will override
    */
   protected void initialiseConstructors(Collection<ConstructorInfo> constructorInfos)
   {
   }

   /**
    * Called by initialiseConstructors() in generated advisor sub classes
    */
   protected void addConstructorInfo(ConstructorInfo ci, Collection<ConstructorInfo> constructorInfos)
   {
      constructorInfos.add(ci);
      //If we do dynamic invokes the constructor will need to be accessible via reflection
      SecurityActions.setAccessible(ci.getConstructor());
   }

   @Override
   protected void createInterceptorChains() throws Exception
   {
      advisorStrategy.createInterceptorChains();
   }
   
// TODO Flavia
//   @Override
//   protected ArrayList initializeConstructorChain()
//   {
//      if (super.initialized)
//      {
//         for (Iterator it = constructorInfos.iterator() ; it.hasNext() ; )
//         {
//            ((ConstructorInfo)it.next()).clear();
//         }
//      }
//      return constructorInfos;
//   }

   /**
    * Generated class advisor sub class will override
    */
   protected void initialiseConstructions(Collection<ConstructionInfo> constructorInfos)
   {
   }

   /**
    * Called by initialiseConstructions() in generated advisor sub classes
    */
   protected void addConstructionInfo(ConstructionInfo ci, Collection<ConstructionInfo> constructionInfos)
   {
      constructionInfos.add(ci);
   }

// TODO Flavia
//   @Override
//   protected ArrayList initializeConstructionChain()
//   {
//      if (super.initialized)
//      {
//         for (Iterator it = constructionInfos.iterator() ; it.hasNext() ; )
//         {
//            ((ConstructionInfo)it.next()).clear();
//         }
//      }
//      return constructionInfos;
//   }

   /**
    * Generated class advisor sub class will override
    */
   protected void initialiseFieldReads(Collection<FieldInfo> fieldReadInfos)
   {
   }

   /**
    * Called by initialiseFieldReads() in generated advisor sub classes
    */
   protected void addFieldReadInfo(FieldInfo fi, Collection<FieldInfo> fieldReadInfos)
   {
      fieldReadInfos.add(fi);
      //If we do dynamic invokes the field will need to be accessible via reflection
      advisorStrategy.makeAccessibleField(fi);
   }

   // TODO Flavia remove this once the process is complete
   @Override
   protected void initializeConstructorChain()
   {
      // TODO remove this
      if (this.constructorInfos == null)
      {
         super.initializeConstructorChain();
      }
   }
   
   // TODO remove this once the process is complete
   @Override
   protected void initializeConstructionChain()
   {
      // TODO remove this
      if (this.constructionInfos == null)
      {
         super.initializeConstructionChain();
      }
   }
   
   @Override
   protected void initializeFieldReadChain()
   {
      this.fieldReadInfos = mergeFieldInfos(fieldReadInfos, true);
   }

   /**
    * Generated class advisor sub class will override
    */
   protected void initialiseFieldWrites(Collection<FieldInfo> fieldWriteInfos)
   {
   }

   /**
    * Called by initialiseFieldWrites() in generated advisor sub classes
    */
   protected void addFieldWriteInfo(FieldInfo fi, Collection<FieldInfo> fieldWriteInfos)
   {
      fieldWriteInfos.add(fi);
      //If we do dynamic invokes the field will need to be accessible via reflection
      advisorStrategy.makeAccessibleField(fi);
   }

   @Override
   protected void initializeFieldWriteChain()
   {
      this.fieldWriteInfos = mergeFieldInfos(fieldWriteInfos, false);
   }

   /* Creates a full list of field infos for all fields, using the ones added by
    * generated advisor for advised fields.
    */
   private FieldInfo[] mergeFieldInfos(FieldInfo[] advisedInfos, boolean read)
   {
      FieldInfo[] newInfos = new FieldInfo[advisedFields.length];

      int nextIndex = 0;
      int advisedInfosLength = advisedInfos == null? 0: advisedInfos.length;

      for (int i = 0 ; i < advisedFields.length ; i++)
      {
         if (nextIndex < advisedInfosLength && advisedInfos[nextIndex].getIndex() == i)
         {
            if (super.initialized)
            {
               advisedInfos[nextIndex].clear();
            }

            newInfos[i] = advisedInfos[nextIndex];
            nextIndex++;
         }
         else
         {
            FieldInfo info = new FieldInfo(this, read);
            info.setAdvisedField(advisedFields[i]);
            info.setIndex(i);
            newInfos[i] = info;
         }
      }

      return newInfos;
   }

   @Override
   protected void finalizeChains()
   {
      ClassAdvisor classAdvisor = getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
      if (classAdvisor != null)
      {
         //We are an instance advisor who has not resolved their own pointcuts, make sure that we set the bindings that are referenced
         //so that they can be removed properly
         //Make sure that all the adviceBindings for the class advisor are referenced from us
         synchronized(this.adviceBindings)
         {
            this.adviceBindings.addAll(classAdvisor.adviceBindings);
            if (adviceBindings.size() > 0)
            {
               for (AdviceBinding binding : this.adviceBindings)
               {
                  binding.addAdvisor(this);
               }
            }
         }
      }
      
      finalizeMethodChain();
      finalizeFieldReadChain();
      finalizeFieldWriteChain();
      advisorStrategy.finalizeConstructorChain(constructorInfos);
      advisorStrategy.finalizeConstructionChain(constructionInfos);
   }

   @Override
   protected void finalizeMethodChain()
   {
      ClassAdvisor classAdvisor = getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
      if (classAdvisor != null)
      {
         //We are an instance advisor with no own data influencing the chains, copy these from the parent advisor
         easyFinalizeMethodChainForInstance(classAdvisor, methodInfos);
      }
      else
      {
         //We are either the class advisor or an instanceadvisor with own data so we need to do all the work
         fullWorkFinalizeMethodChain(methodInfos);
      }
   }

   private void easyFinalizeMethodChainForInstance(ClassAdvisor classAdvisor, MethodInterceptors newMethodInterceptors)
   {
      long[] keys = newMethodInterceptors.keys();
      for (int i = 0; i < keys.length; i++)
      {
         MethodInfo classMethodInfo = classAdvisor.getMethodInfo(keys[i]);
         MethodMatchInfo matchInfo = newMethodInterceptors.getMatchInfo(keys[i]);
         MethodInfo myMethodInfo = matchInfo.getInfo();
         myMethodInfo.cloneChains(classMethodInfo);
         
         if (updateOldInfo(oldInfos, myMethodInfo, OldInfoMaps.INFOS))
         {
            MethodJoinPointGenerator generator = getJoinPointGenerator(myMethodInfo);
            generator.rebindJoinpoint(myMethodInfo);
         }
      }
   }
   
   private void fullWorkFinalizeMethodChain(MethodInterceptors newMethodInterceptors)
   {
      //We are either the class advisor or an instanceadvisor with own data so we need to do all the work
      TLongObjectHashMap newMethodInfos = new TLongObjectHashMap();

      long[] keys = newMethodInterceptors.keys();
      for (int i = 0; i < keys.length; i++)
      {
         MethodMatchInfo matchInfo = newMethodInterceptors.getMatchInfo(keys[i]);
         matchInfo.populateBindings();

         MethodInfo info = matchInfo.getInfo();
         newMethodInfos.put(keys[i], info);

         MethodJoinPointGenerator generator = getJoinPointGenerator(info);
         finalizeChainAndRebindJoinPoint(oldInfos, info, generator, OldInfoMaps.INFOS);
      }
      if (AspectManager.maintainAdvisorMethodInterceptors)
      {
         methodInterceptors = newMethodInfos;
      }
      
      //Handle the overridden methods
      if (overriddenMethods != null && overriddenMethods.size() > 0)
      {
         for (MethodInfo info : overriddenMethods)
         {
            MethodJoinPointGenerator generator = getJoinPointGenerator(info);
            finalizeChainAndRebindJoinPoint(oldInfos, info, generator, OldInfoMaps.INFOS);
         }
      }      
   }
   
   private void finalizeFieldReadChain()
   {
      ClassAdvisor classAdvisor = getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
      if (classAdvisor != null)
      {
         //We are an instance advisor with no own data influencing the chains, copy these from the parent advisor
         easyFinalizeFieldChainForInstance(oldFieldReadInfos, classAdvisor.getFieldReadInfos(), fieldReadInfos, OldInfoMaps.FIELD_READ_INFOS);
      }
      else
      {
         //We are either the class advisor or an instanceadvisor with own data so we need to do all the work
         fullWorkFinalizeFieldChain(oldFieldReadInfos, fieldReadInfos, OldInfoMaps.FIELD_READ_INFOS);
      }
   }

   protected void finalizeFieldWriteChain()
   {
      ClassAdvisor classAdvisor = getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
      if (classAdvisor != null)
      {
         //We are an instance advisor with no own data influencing the chains, copy these from the parent advisor
         easyFinalizeFieldChainForInstance(oldInfos, classAdvisor.getFieldWriteInfos(), fieldWriteInfos, OldInfoMaps.INFOS);
      }
      else
      {
         //We are either the class advisor or an instanceadvisor with own data so we need to do all the work
         fullWorkFinalizeFieldChain(oldInfos, fieldWriteInfos, OldInfoMaps.INFOS);
      }
   }

   private void easyFinalizeFieldChainForInstance(Map<Joinpoint, Interceptor[]> oldFieldInfos, FieldInfo[] classFieldInfos, FieldInfo[] newFieldInfos, OldInfoMaps oldInfoMapInstance)
   {
      //We are an instance advisor with no own data influencing the chains, copy these from the parent advisor
      for (int i = 0; i < newFieldInfos.length; i++)
      {
         FieldInfo myInfo = newFieldInfos[i];
         myInfo.cloneChains(classFieldInfos[i]);

         if (updateOldInfo(oldFieldInfos, myInfo, oldInfoMapInstance))
         {
            FieldJoinPointGenerator generator = getJoinPointGenerator(myInfo);
            generator.rebindJoinpoint(myInfo);
         }
      }
   }
   
   private void fullWorkFinalizeFieldChain(Map<Joinpoint, Interceptor[]> oldFieldInfos, FieldInfo[] newFieldInfos, OldInfoMaps oldInfoMapInstance)
   {
      //We are either the class advisor or an instanceadvisor with own data so we need to do all the work
      for (int i = 0; i < newFieldInfos.length; i++)
      {
         FieldInfo info = newFieldInfos[i];
         FieldJoinPointGenerator generator = getJoinPointGenerator(info);
         finalizeChainAndRebindJoinPoint(oldFieldInfos, info, generator, oldInfoMapInstance);
      }
   }

   @Override
   protected void finalizeMethodCalledByMethodInterceptorChain(MethodByMethodInfo info)
   {
      MethodByMethodJoinPointGenerator generator = getJoinPointGenerator(info);
      finalizeChainAndRebindJoinPoint(oldInfos, info, generator, OldInfoMaps.INFOS);
   }

   @Override
   protected void finalizeConCalledByMethodInterceptorChain(ConByMethodInfo info)
   {
      ConByMethodJoinPointGenerator generator = getJoinPointGenerator(info);
      finalizeChainAndRebindJoinPoint(oldInfos, info, generator, OldInfoMaps.INFOS);
   }

   @Override
   protected void finalizeConCalledByConInterceptorChain(ConByConInfo info)
   {
      ConByConJoinPointGenerator generator = getJoinPointGenerator(info);
      finalizeChainAndRebindJoinPoint(oldInfos, info, generator, OldInfoMaps.INFOS);
   }

   @Override
   protected void finalizeMethodCalledByConInterceptorChain(MethodByConInfo info)
   {
      MethodByConJoinPointGenerator generator = getJoinPointGenerator(info);
      finalizeChainAndRebindJoinPoint(oldInfos, info, generator, OldInfoMaps.INFOS);
   }

   private JoinPointGenerator getJoinPointGenerator(JoinPointInfo info)
   {
      if (info instanceof MethodInfo)
      {
         return getJoinPointGenerator((MethodInfo)info);
      }
      else if (info instanceof FieldInfo)
      {
         return getJoinPointGenerator((FieldInfo)info);
      }
      else if (info instanceof ConstructionInfo)
      {
         return getJoinPointGenerator((ConstructionInfo)info);
      }
      else if (info instanceof ConstructorInfo)
      {
         return getJoinPointGenerator((ConstructorInfo)info);
      }
      else if (info instanceof ConByConInfo)
      {
         return getJoinPointGenerator((ConByConInfo)info);
      }
      else if (info instanceof ConByMethodInfo)
      {
         return getJoinPointGenerator((ConByMethodInfo)info);
      }
      else if (info instanceof MethodByMethodInfo)
      {
         return getJoinPointGenerator((MethodByMethodInfo)info);
      }
      else if (info instanceof MethodByConInfo)
      {
         return getJoinPointGenerator((MethodByConInfo)info);
      }
      else
      {
         throw new RuntimeException("Invalid JoinPointInfo passed in: " + info.getClass().getName());
      }
   }

   protected MethodJoinPointGenerator getJoinPointGenerator(MethodInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }

   protected FieldJoinPointGenerator getJoinPointGenerator(FieldInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }
   
   protected ConstructorJoinPointGenerator getJoinPointGenerator(ConstructorInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }

   protected ConstructionJoinPointGenerator getJoinPointGenerator(ConstructionInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }

   protected MethodByMethodJoinPointGenerator getJoinPointGenerator(MethodByMethodInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }

   protected ConByMethodJoinPointGenerator getJoinPointGenerator(ConByMethodInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }

   protected ConByConJoinPointGenerator getJoinPointGenerator(ConByConInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }

   protected MethodByConJoinPointGenerator getJoinPointGenerator(MethodByConInfo info)
   {
      return advisorStrategy.getJoinPointGenerator(info);
   }

   /**
    * Override default behaviour of when a pointcut is matched, populate the factories since this
    * is what is needed for generating the optimized invocation method
    */
   @Override
   protected void pointcutResolved(JoinPointInfo info, AdviceBinding binding, Joinpoint joinpoint)
   {
      ArrayList<Interceptor> curr = info.getInterceptorChain();
      if (binding.getCFlow() != null)
      {
         InterceptorFactory[] factories = binding.getInterceptorFactories();
         for (int i = 0 ; i < factories.length ; i++)
         {
            curr.add(new GeneratedAdvisorInterceptor(factories[i], this, joinpoint, binding.getCFlowString(), binding.getCFlow()));
         }
      }
      else
      {
         InterceptorFactory[] factories = binding.getInterceptorFactories();
         for (int i = 0 ; i < factories.length ; i++)
         {
            curr.add(new GeneratedAdvisorInterceptor(factories[i], this, joinpoint));
         }
      }
   }

   private void finalizeChainAndRebindJoinPoint(Map<Joinpoint, Interceptor[]> oldInfos, JoinPointInfo info, JoinPointGenerator generator, OldInfoMaps oldInfoMapInstance)
   {
      adjustInfoForAddedBinding(info);
      List<Interceptor> list = info.getInterceptorChain();
      GeneratedAdvisorInterceptor[] factories = null;
      if (list.size() > 0)
      {
         factories = applyPrecedence(list.toArray(new GeneratedAdvisorInterceptor[list.size()]));
      }
      info.setInterceptors(factories);

      if (updateOldInfo(oldInfos, info, oldInfoMapInstance))
      {
         generator.rebindJoinpoint(info);
      }
   }

   @Override
   public String toString()
   {
      Class<?> clazz = this.getClass();
      StringBuffer sb = new StringBuffer("CLASS: " + clazz.getName());

      Field[] fields = clazz.getFields();
      for (int i = 0 ; i < fields.length ; i++)
      {
         sb.append("\n\t" + fields[i]);
      }
      return sb.toString();
   }

   GeneratedAdvisorInterceptor[] applyPrecedence(GeneratedAdvisorInterceptor[] interceptors)
   {
      return PrecedenceSorter.applyPrecedence(interceptors, manager);
   }

   /**
    * If this is an instance advisor, will check with parent class advisor if the aspect
    * is already registered. If so, we should use the one from the parent advisor
    */
   @Override
   public Object getPerClassAspect(AspectDefinition def)
   {
      return advisorStrategy.getPerClassAspect(def);
   }

   /**
    * Generated ClassAdvisors and InstanceAdvisors will be different instances,
    * so keep track of what per_class_joinpoint aspects have been added where
    */
   ConcurrentHashMap<AspectDefinition, Map<Joinpoint, Object>> perClassJoinpointAspectDefinitions =
         new ConcurrentHashMap<AspectDefinition, Map<Joinpoint, Object>>();


   public Object getPerClassJoinpointAspect(AspectDefinition def, Joinpoint joinpoint)
   {
      return advisorStrategy.getPerClassJoinpointAspect(def, joinpoint);
   }

   public synchronized void addPerClassJoinpointAspect(AspectDefinition def, Joinpoint joinpoint)
   {
      Map<Joinpoint, Object> joinpoints = perClassJoinpointAspectDefinitions.get(def);
      if (joinpoints == null)
      {
         joinpoints = new ConcurrentHashMap<Joinpoint, Object>();
         perClassJoinpointAspectDefinitions.put(def, joinpoints);
      }

      if (!joinpoints.containsKey(joinpoint))
      {
         Object aspect = def.getFactory().createPerJoinpoint(this, joinpoint);
         if (aspect == null)
         {
            joinpoints.put(joinpoint, NULL_ASPECT);
         }
         else
         {
            joinpoints.put(joinpoint, aspect);
         }
      }
      def.registerAdvisor(this);
   }

   public synchronized void removePerClassJoinpointAspect(AspectDefinition def)
   {
      perClassJoinpointAspectDefinitions.remove(def);
   }
   
   /**
    * @see Advisor#chainOverridingForInheritedMethods()
    */
   @Override
   public boolean chainOverridingForInheritedMethods()
   {
      return true;
   }

   @Override
   public Object getFieldAspect(FieldJoinpoint joinpoint, AspectDefinition def)
   {
      Object instance = getPerClassJoinpointAspect(def, joinpoint);
      if (instance == null)
      {
         addPerClassJoinpointAspect(def, joinpoint);
         instance = getPerClassJoinpointAspect(def, joinpoint);
      }
      return instance;
   }

   private GeneratedClassAdvisor getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices()
   {
      return advisorStrategy.getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
   }
   
   /**
    * Optimization so that when we create instance advisors we don't have to create the method tables again,
    * they were already created for the class advisor 
    */
   @Override
   protected void createMethodTables() throws Exception
   {
      advisorStrategy.createMethodTables();
   }
   
   /**
    * Optimization so that when we create instance advisors we don't have to create the field tables again,
    * they were already created for the class advisor 
    */
   @Override
   protected void createFieldTable() throws Exception
   {
      advisorStrategy.createFieldTable();
   }
   
   /**
    * Optimization so that when we create instance advisors we don't have to create the constructor tables again,
    * they were already created for the class advisor 
    */
   @Override
   protected void createConstructorTables() throws Exception
   {
      advisorStrategy.createConstructorTables();
   }

   @Override
   public Set<AspectDefinition> getPerInstanceAspectDefinitions()
   {
      return advisorStrategy.getPerInstanceAspectDefinitions();
   }

   @Override
   public Map<AspectDefinition, Set<Joinpoint>> getPerInstanceJoinpointAspectDefinitions()
   {
      return advisorStrategy.getPerInstanceJoinpointAspectDefinitions();
   }

   /**
    * Caches the old info and checks if the chains have been updated
    */
   private boolean updateOldInfo(Map<Joinpoint, Interceptor[]> oldInfos, JoinPointInfo newInfo, OldInfoMaps oldInfoMapInstance)
   {
      Interceptor[] oldChain = oldInfos.get(newInfo.getJoinpoint());
      if (oldChain != null)
      {
         //We are not changing any of the bindings
         if (newInfo.equalChains(oldChain))
         {
            return false;
         }
      }
      Interceptor[] currentOldChain = newInfo.getInterceptors();
      
      if (oldInfoMapInstance == OldInfoMaps.INFOS) 
      {
         oldInfos = initOldInfosMap();
      }
      else if (oldInfoMapInstance == OldInfoMaps.FIELD_READ_INFOS)
      {
         oldInfos = initOldFieldReadInfosMap();
      }
      else if (oldInfoMapInstance == OldInfoMaps.CONSTRUCTION_INFOS)
      {
         oldInfos = initOldConstructionInfosMap();
      }
      else
      {
         throw new RuntimeException("Unrecognised map");
      }
      if (currentOldChain != null)
      {
         oldInfos.put(newInfo.getJoinpoint(), currentOldChain);
      }
      else if (oldChain != null)
      {
         oldInfos.remove(newInfo.getJoinpoint());
      }
      return true;
   }

   protected void generateJoinPointClass(MethodInfo info)
   {
      MethodJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }

   protected void generateJoinPointClass(FieldInfo info)
   {
      FieldJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }

   protected void generateJoinPointClass(ConstructorInfo info)
   {
      ConstructorJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }

   protected void generateJoinPointClass(ConstructionInfo info)
   {
      ConstructionJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }

   protected void generateJoinPointClass(MethodByMethodInfo info)
   {
      MethodByMethodJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }

   protected void generateJoinPointClass(ConByMethodInfo info)
   {
      ConByMethodJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }

   protected void generateJoinPointClass(ConByConInfo info)
   {
      ConByConJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }

   protected void generateJoinPointClass(MethodByConInfo info)
   {
      MethodByConJoinPointGenerator generator = getJoinPointGenerator(info);
      generator.generateJoinPointClass(getClassLoader(), info);
   }
   
   protected Object rebindJoinPointWithInstanceInformation(JoinPointInfo info)
   {
      info.getInterceptorChainReadWriteLock().readLock().lock();
      try
      {
         JoinPointGenerator generator = getJoinPointGenerator(info);
         generator.rebindJoinpoint(info);
         return generator.generateJoinPointClass(getClassLoader(), info);
      }
      finally
      {
         info.getInterceptorChainReadWriteLock().readLock().unlock();
      }
   }
   
   /**
    * Called back from generated code
    */
   public Object createAndRebindJoinPointForInstance(JoinPointInfo info)
   {
      JoinPointInfo newinfo = info.copy();
      newinfo.setAdvisor(this);
      return rebindJoinPointWithInstanceInformation(newinfo);
   }
   
   
   protected void initJoinPointGeneratorsMap()
   {
      if (joinPointGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (joinPointGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               joinPointGenerators = new ConcurrentHashMap<Joinpoint, JoinPointGenerator>();
            }
         }
      }
   }
   
   protected void initFieldReadJoinPointGeneratorsMap()
   {
      if (fieldReadJoinPoinGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (fieldReadJoinPoinGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               fieldReadJoinPoinGenerators = new ConcurrentHashMap<Joinpoint, FieldJoinPointGenerator>();
            }
         }
      }
   }
   
   protected void initConstructionJoinPointGeneratorsMap()
   {
      if (constructionJoinPointGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (constructionJoinPointGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               constructionJoinPointGenerators = new ConcurrentHashMap<Joinpoint, ConstructorJoinPointGenerator>();
            }
         }
      }
   }
   
   protected void initMethodCalledByConJoinPointGeneratorsMap()
   {
      if (methodCalledByXXXJoinPointGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (methodCalledByXXXJoinPointGenerators == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               methodCalledByXXXJoinPointGenerators = new ConcurrentHashMap<Joinpoint, ConcurrentHashMap<Class<?>, JoinPointGenerator>>();
            }
         }
      }
   }
   
   protected ConcurrentHashMap<Joinpoint, Interceptor[]> initOldInfosMap()
   {
      if (oldInfos == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (oldInfos == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               oldInfos = new ConcurrentHashMap<Joinpoint, Interceptor[]>();
            }
         }
      }
      return oldInfos;
   }
   
   protected ConcurrentHashMap<Joinpoint, Interceptor[]> initOldFieldReadInfosMap()
   {
      if (oldFieldReadInfos == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (oldFieldReadInfos == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               oldFieldReadInfos = new ConcurrentHashMap<Joinpoint, Interceptor[]>();
            }
         }
      }
      return oldFieldReadInfos;
   }
   
   protected ConcurrentHashMap<Joinpoint, Interceptor[]> initOldConstructionInfosMap()
   {
      if (oldConstructionInfos == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (oldConstructionInfos == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               oldConstructionInfos = new ConcurrentHashMap<Joinpoint, Interceptor[]>();
            }
         }
      }
      return oldConstructionInfos;
   }
   
   /**
    * Encapsulates different behaviours depending on if this is an instance or class advisor
    */
   private interface AdvisorStrategy
   {
      void initialise(Class<?> clazz, AspectManager manager);
      void checkVersion();
      void createInterceptorChains() throws Exception;
      MethodJoinPointGenerator getJoinPointGenerator(MethodInfo info);
      FieldJoinPointGenerator getJoinPointGenerator(FieldInfo info);
      ConstructorJoinPointGenerator getJoinPointGenerator(ConstructorInfo info);
      ConstructionJoinPointGenerator getJoinPointGenerator(ConstructionInfo info);
      MethodByMethodJoinPointGenerator getJoinPointGenerator(MethodByMethodInfo info);
      ConByMethodJoinPointGenerator getJoinPointGenerator(ConByMethodInfo info);
      ConByConJoinPointGenerator getJoinPointGenerator(ConByConInfo info);
      MethodByConJoinPointGenerator getJoinPointGenerator(MethodByConInfo info);
      Object getPerClassAspect(AspectDefinition def);
      Object getPerClassJoinpointAspect(AspectDefinition def, Joinpoint joinpoint);
      GeneratedClassAdvisor getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices();
      void createMethodTables() throws Exception;
      void createFieldTable() throws Exception;
      void createConstructorTables() throws Exception;
      Set<AspectDefinition> getPerInstanceAspectDefinitions();
      Map<AspectDefinition, Set<Joinpoint>> getPerInstanceJoinpointAspectDefinitions();
      void rebuildInterceptors();
      void rebuildInterceptorsForAddedBinding(AdviceBinding binding);
      void rebuildInterceptorsForRemovedBinding(AdviceBinding binding);
      void resolveConstructorPointcut(AdviceBinding binding);
      void resolveConstructionPointcut(AdviceBinding binding);
      void finalizeConstructorChain(ConstructorInfo[] newConstructorInfos);
      void finalizeConstructionChain(ConstructionInfo[] newConstructionInfos);
      void makeAccessibleField(FieldInfo fi);
      void makeAccessibleMethod(MethodInfo mi);
   }
   
   private class ClassAdvisorStrategy implements AdvisorStrategy
   {
      GeneratedClassAdvisor parent;

      public void initialise(Class<?> clazz, AspectManager manager)
      {
         methodInfos = new MethodInterceptors(GeneratedClassAdvisor.this);
         initialiseMethods();
         // initialise constructor info array
         Collection<ConstructorInfo> constructorInfoCol = new ArrayList<ConstructorInfo>();
         initialiseConstructors(constructorInfoCol);
         constructorInfos = constructorInfoCol.toArray(new ConstructorInfo[constructorInfoCol.size()]);
         // initialise construction info array
         Collection<ConstructionInfo> constructionInfoCol = new ArrayList<ConstructionInfo>();
         initialiseConstructions(constructionInfoCol);
         constructionInfos = constructionInfoCol.toArray(new ConstructionInfo[constructionInfoCol.size()]);
         // initalise field read info array
         Collection<FieldInfo> fieldReadInfoCol = new ArrayList<FieldInfo>();
         initialiseFieldReads(fieldReadInfoCol);
         fieldReadInfos = fieldReadInfoCol.toArray(new FieldInfo[fieldReadInfoCol.size()]);
         // initalise field write info array
         Collection<FieldInfo> fieldWriteInfoCol = new ArrayList<FieldInfo>();
         initialiseFieldWrites(fieldWriteInfoCol);
         fieldWriteInfos = fieldWriteInfoCol.toArray(new FieldInfo[fieldWriteInfoCol.size()]);
         
         GeneratedClassAdvisor.super.setManager(manager);

         //Make sure that we copy across per class and per joinpoint aspects from the old advisor if it exists
         //Generated advisors get created when the class is first accessed (not loaded), meaning that there could be an exisiting advisor
         //used for mathcing already when setting up the microcontainer.
         Advisor existing = AspectManager.instance().getAnyAdvisorIfAdvised(clazz);
         if (existing != null)
         {
            GeneratedClassAdvisor.this.aspects = existing.aspects;
            if (existing instanceof GeneratedClassAdvisor)
            {
               GeneratedClassAdvisor.this.perClassJoinpointAspectDefinitions = ((GeneratedClassAdvisor)existing).perClassJoinpointAspectDefinitions;
            }
         }
         
         manager.initialiseClassAdvisor(clazz, GeneratedClassAdvisor.this);
         
         initialiseCallers();
      }

      public void checkVersion()
      {
         //The version is only has any significance for instance advisors
      }

      public void createInterceptorChains() throws Exception
      {
         GeneratedClassAdvisor.super.createInterceptorChains();
      }
      
      public MethodJoinPointGenerator getJoinPointGenerator(MethodInfo info)
      {
         MethodJoinPointGenerator generator = (MethodJoinPointGenerator)joinPointGenerators.get(info.getJoinpoint());
         if (generator == null)
         {
            generator = new MethodJoinPointGenerator(GeneratedClassAdvisor.this, info);
            initJoinPointGeneratorsMap();
            MethodJoinPointGenerator existing = (MethodJoinPointGenerator)joinPointGenerators.putIfAbsent(info.getJoinpoint(), generator);
            if (existing != null)
            {
               generator = existing;
            }
         }
         return generator;
      }

      public FieldJoinPointGenerator getJoinPointGenerator(FieldInfo info)
      {
         if (info.isRead())
         {
            FieldJoinPointGenerator generator = fieldReadJoinPoinGenerators.get(info.getJoinpoint());
            if (generator == null)
            {
               generator = new FieldJoinPointGenerator(GeneratedClassAdvisor.this, info);
               initFieldReadJoinPointGeneratorsMap();
               FieldJoinPointGenerator existing = fieldReadJoinPoinGenerators.putIfAbsent(info.getJoinpoint(), generator);
               if (existing != null)
               {
                  generator = existing;
               }
            }
            return generator;
         }
         else
         {
            FieldJoinPointGenerator generator = (FieldJoinPointGenerator)joinPointGenerators.get(info.getJoinpoint());
            if (generator == null)
            {
               generator = new FieldJoinPointGenerator(GeneratedClassAdvisor.this, info);
               initJoinPointGeneratorsMap();
               FieldJoinPointGenerator existing = (FieldJoinPointGenerator)joinPointGenerators.putIfAbsent(info.getJoinpoint(), generator);
               if (existing != null)
               {
                  generator = existing;
               }
            }
            return generator;
         }
      }
      
      public ConstructorJoinPointGenerator getJoinPointGenerator(ConstructorInfo info)
      {
         //We are the class advisor
         ConstructorJoinPointGenerator generator = constructionJoinPointGenerators.get(info.getJoinpoint());
         if (generator == null)
         {
            generator = new ConstructorJoinPointGenerator(GeneratedClassAdvisor.this, info);
            initConstructionJoinPointGeneratorsMap();
            ConstructorJoinPointGenerator existing = constructionJoinPointGenerators.putIfAbsent(info.getJoinpoint(), generator);
            if (existing != null)
            {
               generator = existing;
            }
         }
         return generator;
      }

      public ConstructionJoinPointGenerator getJoinPointGenerator(ConstructionInfo info)
      {
         ConstructionJoinPointGenerator generator = (ConstructionJoinPointGenerator)joinPointGenerators.get(info.getJoinpoint());
         if (generator == null)
         {
            generator = new ConstructionJoinPointGenerator(GeneratedClassAdvisor.this, info);
            initJoinPointGeneratorsMap();
            ConstructionJoinPointGenerator existing = (ConstructionJoinPointGenerator)joinPointGenerators.putIfAbsent(info.getJoinpoint(), generator);
            if (existing != null)
            {
               generator = existing;
            }
         }
         return generator;
      }

      public MethodByMethodJoinPointGenerator getJoinPointGenerator(MethodByMethodInfo info)
      {
         //An extra level of indirection since we distinguish between callers of method depending on
         //where the called method is defined (sub/super interfaces)
         ConcurrentHashMap<Class<?>, JoinPointGenerator> map = methodCalledByXXXJoinPointGenerators.get(info.getJoinpoint());
         if (map == null)
         {
            map = new ConcurrentHashMap<Class<?>, JoinPointGenerator>();
            initMethodCalledByConJoinPointGeneratorsMap();
            ConcurrentHashMap<Class<?>, JoinPointGenerator> existing = methodCalledByXXXJoinPointGenerators.putIfAbsent(info.getJoinpoint(), map);
            if (existing != null)
            {
               map = existing;
            }
         }

         MethodByMethodJoinPointGenerator generator = (MethodByMethodJoinPointGenerator)map.get(info.getCalledClass());
         if (generator == null)
         {
            generator = new MethodByMethodJoinPointGenerator(GeneratedClassAdvisor.this, info);
            MethodByMethodJoinPointGenerator existing = (MethodByMethodJoinPointGenerator)map.putIfAbsent(info.getCalledClass(), generator);
            if (existing != null)
            {
               generator = existing;
            }
         }
         return generator;
      }

      public ConByMethodJoinPointGenerator getJoinPointGenerator(ConByMethodInfo info)
      {
         ConByMethodJoinPointGenerator generator = (ConByMethodJoinPointGenerator)joinPointGenerators.get(info.getJoinpoint());
         if (generator == null)
         {
            generator = new ConByMethodJoinPointGenerator(GeneratedClassAdvisor.this, info);
            initJoinPointGeneratorsMap();
            ConByMethodJoinPointGenerator existing = (ConByMethodJoinPointGenerator)joinPointGenerators.putIfAbsent(info.getJoinpoint(), generator);
            if (existing != null)
            {
               generator = existing;
            }
         }
         return generator;
      }

      public ConByConJoinPointGenerator getJoinPointGenerator(ConByConInfo info)
      {
         //We are the class advisor
         ConByConJoinPointGenerator generator = (ConByConJoinPointGenerator)joinPointGenerators.get(info.getJoinpoint());
         if (generator == null)
         {
            generator = new ConByConJoinPointGenerator(GeneratedClassAdvisor.this, info);
            initJoinPointGeneratorsMap();
            ConByConJoinPointGenerator existing = (ConByConJoinPointGenerator)joinPointGenerators.putIfAbsent(info.getJoinpoint(), generator);
            if (existing != null)
            {
               generator = existing;
            }
         }
         return generator;
      }

      public MethodByConJoinPointGenerator getJoinPointGenerator(MethodByConInfo info)
      {
         //An extra level of indirection since we distinguish between callers of method depending on
         //where the called method is defined (sub/super interfaces)
         ConcurrentHashMap<Class<?>, JoinPointGenerator> map = methodCalledByXXXJoinPointGenerators.get(info.getJoinpoint());
         if (map == null)
         {
            map = new ConcurrentHashMap<Class<?>, JoinPointGenerator>();
            initMethodCalledByConJoinPointGeneratorsMap();
            ConcurrentHashMap<Class<?>, JoinPointGenerator> exisiting = methodCalledByXXXJoinPointGenerators.putIfAbsent(info.getJoinpoint(), map);
            if (exisiting != null)
            {
               map = exisiting;
            }
         }

         MethodByConJoinPointGenerator generator = (MethodByConJoinPointGenerator)map.get(info.getCalledClass());
         if (generator == null)
         {
            generator = new MethodByConJoinPointGenerator(GeneratedClassAdvisor.this, info);
            MethodByConJoinPointGenerator existing = (MethodByConJoinPointGenerator)map.putIfAbsent(info.getCalledClass(), generator);
            if (existing != null)
            {
               generator = existing;
            }
         }
         return generator;
      }

      public Object getPerClassAspect(AspectDefinition def)
      {
         return GeneratedClassAdvisor.super.getPerClassAspect(def);
      }

      public Object getPerClassJoinpointAspect(AspectDefinition def, Joinpoint joinpoint)
      {
         Map<Joinpoint, Object> joinpoints = perClassJoinpointAspectDefinitions.get(def);
         if (joinpoints != null)
         {
            Object aspect = joinpoints.get(joinpoint);
            if (aspect != NULL_ASPECT)
            {
               return aspect;
            }
         }
         return null;
      }
      
      public GeneratedClassAdvisor getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices()
      {
         return null;
      }
      
      public void createMethodTables() throws Exception
      {
         GeneratedClassAdvisor.super.createMethodTables();
      }
      
      public void createFieldTable() throws Exception
      {
         GeneratedClassAdvisor.super.createFieldTable();
      }

      public void createConstructorTables() throws Exception
      {
         GeneratedClassAdvisor.super.createConstructorTables();
      }
      
      public Set<AspectDefinition> getPerInstanceAspectDefinitions()
      {
         return GeneratedClassAdvisor.super.getPerInstanceAspectDefinitions();
      }

      public Map<AspectDefinition, Set<Joinpoint>> getPerInstanceJoinpointAspectDefinitions()
      {
         return GeneratedClassAdvisor.super.getPerInstanceJoinpointAspectDefinitions();
      }
      
      public void rebuildInterceptors()
      {
         version++;
         GeneratedClassAdvisor.super.rebuildInterceptors();
      }
      
      public void rebuildInterceptorsForAddedBinding(AdviceBinding binding)
      {
         version++;
         GeneratedClassAdvisor.super.rebuildInterceptorsForAddedBinding(binding);
      }
      
      public void rebuildInterceptorsForRemovedBinding(AdviceBinding binding)
      {
         version++;
         GeneratedClassAdvisor.super.rebuildInterceptorsForRemovedBinding(binding);
      }

      public void resolveConstructorPointcut(AdviceBinding binding)
      {
         GeneratedClassAdvisor.super.resolveConstructorPointcut(binding);
      }

      public void resolveConstructionPointcut(AdviceBinding binding)
      {
         GeneratedClassAdvisor.super.resolveConstructionPointcut(binding);
      }

      public void finalizeConstructorChain(ConstructorInfo[] newConstructorInfos)
      {
         for (int i = 0; i < newConstructorInfos.length; i++)
         {
            ConstructorInfo info = newConstructorInfos[i];
            ConstructorJoinPointGenerator generator = getJoinPointGenerator(info);
            Class<?> clazz = info.getClazz();
            if (clazz != null)
            finalizeChainAndRebindJoinPoint(oldInfos, info, generator, OldInfoMaps.INFOS);
         }
      }

      public void finalizeConstructionChain(ConstructionInfo[] newConstructionInfos)
      {
         for (int i = 0; i < newConstructionInfos.length; i++)
         {
            ConstructionInfo info = newConstructionInfos[i];
            ConstructionJoinPointGenerator generator = getJoinPointGenerator(info);
            finalizeChainAndRebindJoinPoint(oldConstructionInfos, info, generator, OldInfoMaps.CONSTRUCTION_INFOS);
         }
      }

      public void makeAccessibleField(FieldInfo fi)
      {
         //If we do dynamic invokes the field will need to be accessible via reflection
         SecurityActions.setAccessible(fi.getField());
      }
      
      public void makeAccessibleMethod(MethodInfo mi)
      {
         //If we do dynamic invokes the method will need to be accessible via reflection if private/protected
         SecurityActions.setAccessible(mi.getMethod());
      }
   }
   
   private class InstanceAdvisorStrategy implements AdvisorStrategy 
   {
      GeneratedClassAdvisor parent;
      boolean needsRebuild = true;
      
      public InstanceAdvisorStrategy(GeneratedClassAdvisor parent)
      {
         this.parent = parent;
         GeneratedClassAdvisor.this.version = parent.version;         
      }
      
      public void initialise(Class<?> clazz, AspectManager manager)
      {
         initialiseInfosForInstance();
         
         GeneratedClassAdvisor.super.setManager(manager);

         manager.initialiseClassAdvisor(clazz, GeneratedClassAdvisor.this);
      }

      public void checkVersion()
      {
         if (needsRebuild || parent.version != GeneratedClassAdvisor.this.version)
         {
            doRebuildForInstance();
            needsRebuild = false;
         }
      }
      
      public void createInterceptorChains() throws Exception
      {
         if (GeneratedClassAdvisor.super.initialized)
         {
            GeneratedClassAdvisor.super.createInterceptorChains();
         }
         else
         {
            //Instance advisor copies the chains from the class advisor during its initialise stage 
            GeneratedClassAdvisor.super.initialized = true;
         }
      }

      public MethodJoinPointGenerator getJoinPointGenerator(MethodInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }

      public FieldJoinPointGenerator getJoinPointGenerator(FieldInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }
      
      public ConstructorJoinPointGenerator getJoinPointGenerator(ConstructorInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }

      public ConstructionJoinPointGenerator getJoinPointGenerator(ConstructionInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }

      public MethodByMethodJoinPointGenerator getJoinPointGenerator(MethodByMethodInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }

      public ConByMethodJoinPointGenerator getJoinPointGenerator(ConByMethodInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }

      public ConByConJoinPointGenerator getJoinPointGenerator(ConByConInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }

      public MethodByConJoinPointGenerator getJoinPointGenerator(MethodByConInfo info)
      {
         //We are an instance advisor, get the generator from the class advisor
         return parent.getJoinPointGenerator(info);
      }

      /**
       * This is an instance advisor, so we will check with parent class advisor if the aspect
       * is already registered. If so, we should use the one from the parent advisor
       */
      public Object getPerClassAspect(AspectDefinition def)
      {
         Object aspect = parent.getPerClassAspect(def);
         if (aspect != null) return aspect;
         return GeneratedClassAdvisor.super.getPerClassAspect(def);
      }

      /**
       * This is an instance advisor, so we will check with parent class advisor if the aspect
       * is already registered. If so, we should use the one from the parent advisor
       */
      public Object getPerClassJoinpointAspect(AspectDefinition def, Joinpoint joinpoint)
      {
         Object aspect = parent.getPerClassJoinpointAspect(def, joinpoint);
         if (aspect != null)return aspect;
         return parent.getPerClassJoinpointAspect(def, joinpoint);
      }

      public GeneratedClassAdvisor getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices()
      {
         if (((Domain)getManager()).hasOwnDataWithEffectOnAdvices())
         {
            return null;
         }
         return parent;
      }
      
      public void createMethodTables() throws Exception
      {
         GeneratedClassAdvisor.this.unadvisedMethods = parent.unadvisedMethods;
         GeneratedClassAdvisor.this.advisedMethods = parent.advisedMethods;
      }

      public void createFieldTable() throws Exception
      {
         GeneratedClassAdvisor.this.advisedFields = parent.advisedFields;
      }

      public void createConstructorTables() throws Exception
      {
         GeneratedClassAdvisor.this.constructors = parent.constructors;
         
         methodCalledByConBindings = new HashMap[constructors.length];
         methodCalledByConInterceptors = new HashMap[constructors.length];

         conCalledByConBindings = new HashMap[constructors.length];
         conCalledByConInterceptors = new HashMap[constructors.length];
      }

      public Set<AspectDefinition> getPerInstanceAspectDefinitions()
      {
         return parent.getPerInstanceAspectDefinitions();
      }

      public Map<AspectDefinition, Set<Joinpoint>> getPerInstanceJoinpointAspectDefinitions()
      {
         return parent.getPerInstanceJoinpointAspectDefinitions();
      }

      
      public synchronized void rebuildInterceptors()
      {
         if (getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices() != null && GeneratedClassAdvisor.this.version != parent.version)
         {
            adviceBindings.clear();
            needsRebuild = true;
         }
         else
         {
            // check if it is initialized
            if (GeneratedClassAdvisor.this.fieldReadInfos == null)
            {
               try
               {
                  GeneratedClassAdvisor.this.createInterceptorChains();
               }
               catch (Exception ex)
               {
                  if (ex instanceof RuntimeException)
                  {
                     throw (RuntimeException) ex;
                  }
                  throw new RuntimeException(ex);
               }
            }
            else
            {
               GeneratedClassAdvisor.super.rebuildInterceptors();
            }
         }
      }

      public void rebuildInterceptorsForAddedBinding(AdviceBinding binding)
      {
         if (getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices() != null && GeneratedClassAdvisor.this.version != parent.version)
         {
            adviceBindings.clear();
            needsRebuild = true;
         }
         else
         {
            // check if it is initialized
            if (!GeneratedClassAdvisor.this.initialized)
            {
               try
               {
                  GeneratedClassAdvisor.this.createInterceptorChains();
               }
               catch (Exception ex)
               {
                  if (ex instanceof RuntimeException)
                  {
                     throw (RuntimeException) ex;
                  }
                  throw new RuntimeException(ex);
               }
            }
            else
            {
               GeneratedClassAdvisor.super.rebuildInterceptorsForAddedBinding(binding);
            }
         }
      }
      
      public void rebuildInterceptorsForRemovedBinding(AdviceBinding binding)
      {
         if (getClassAdvisorIfInstanceAdvisorWithNoOwnDataWithEffectOnAdvices() != null && GeneratedClassAdvisor.this.version != parent.version)
         {
            adviceBindings.clear();
            needsRebuild = true;
         }
         else
         {
            // check if it is initialized
            if (!GeneratedClassAdvisor.this.initialized)
            {
               try
               {
                  GeneratedClassAdvisor.this.createInterceptorChains();
               }
               catch (Exception ex)
               {
                  if (ex instanceof RuntimeException)
                  {
                     throw (RuntimeException) ex;
                  }
                  throw new RuntimeException(ex);
               }
            }
            else
            {
               GeneratedClassAdvisor.super.rebuildInterceptorsForRemovedBinding(binding);
            }
         }
      }


      public void resolveConstructorPointcut(AdviceBinding binding)
      {
         //Since the instance already exists it makes no sense to have bindings for constructors
      }

      public void resolveConstructionPointcut(AdviceBinding binding)
      {
         //Since the instance already exists it makes no sense to have bindings for constructors         
      }

      public void finalizeConstructorChain(ConstructorInfo[] newConstructorInfos)
      {
         //Since the instance already exists it makes no sense to have bindings for constructors
      }

      public void finalizeConstructionChain(ConstructionInfo[] newConstructionInfos)
      {
         //Since the instance already exists it makes no sense to have bindings for constructors
      }

      public void makeAccessibleField(FieldInfo fi)
      {
         //Do nothing, field was already made accessible in class advisor
      }

      public void makeAccessibleMethod(MethodInfo mi)
      {
         //Do nothing, field was already made accessible in class advisor
      }
   }
   
   enum OldInfoMaps{
      INFOS, FIELD_READ_INFOS, CONSTRUCTION_INFOS;
   }

}
