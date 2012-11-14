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
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceStack;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.AspectFactoryWithClassLoader;
import org.jboss.aop.advice.ClassifiedBindingAndPointcutCollection;
import org.jboss.aop.advice.DynamicCFlowDefinition;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.PrecedenceDef;
import org.jboss.aop.advice.PrecedenceDefEntry;
import org.jboss.aop.advice.PrecedenceSorter;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.array.ArrayAdvisor;
import org.jboss.aop.array.ArrayBinding;
import org.jboss.aop.array.ArrayReplacement;
import org.jboss.aop.classpool.AOPClassLoaderScopingPolicy;
import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.aop.instrument.GeneratedAdvisorInstrumentor;
import org.jboss.aop.instrument.Instrumentor;
import org.jboss.aop.instrument.InstrumentorFactory;
import org.jboss.aop.instrument.JoinPointGenerator;
import org.jboss.aop.instrument.TransformerCommon;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.metadata.ClassMetaDataBinding;
import org.jboss.aop.metadata.ClassMetaDataLoader;
import org.jboss.aop.metadata.SimpleClassMetaDataLoader;
import org.jboss.aop.microcontainer.lifecycle.LifecycleCallbackBinding;
import org.jboss.aop.microcontainer.lifecycle.LifecycleManager;
import org.jboss.aop.pointcut.CFlowStack;
import org.jboss.aop.pointcut.DeclareDef;
import org.jboss.aop.pointcut.DynamicCFlow;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.pointcut.PointcutInfo;
import org.jboss.aop.pointcut.PointcutStats;
import org.jboss.aop.pointcut.Typedef;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.util.AOPLock;
import org.jboss.aop.util.UnmodifiableEmptyCollections;
import org.jboss.aop.util.logging.AOPLogger;
import org.jboss.util.collection.WeakValueHashMap;
import org.jboss.util.loading.Translator;

/**
 * This singleton class manages all pointcuts and metadata.
 * Coders can access it via the AspectManager.instance() method.
 * <p/>
 * It is also the middle man between the ClassLoader and
 * the actual class instrumentation as well.
 * <p/>
 * App Developers that want to create and apply, interceptors,
 * pointcuts, or metadata at runtime can also use this object
 * to do that.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author adrian@jboss.org
 * @version $Revision: 87258 $
 */
public class AspectManager
        implements Translator
{
   private static final AOPLogger logger = AOPLogger.getLogger(AspectManager.class);
   
   /** Indicates that a call to the factory has been made, but it returned null. */
   private static final Object NULL_ASPECT = new Object();

   /** Lock to be used when lazy creating the collections */
   Object lazyCollectionLock = new Object();

   /** Advisors registered with this manager/domain */
   protected final WeakHashMap<Class<?>, WeakReference<Advisor>> advisors = new WeakHashMap<Class<?>, WeakReference<Advisor>>();

   /** A map of domains by class, maintaned by the top level AspectManager */
   protected volatile WeakHashMap<Class<?>, WeakReference<Domain>> subDomainsPerClass = UnmodifiableEmptyCollections.EMPTY_WEAK_HASHMAP;

   /** A map of domains by name */
   protected volatile WeakValueHashMap<String, Domain> subDomainsByName = UnmodifiableEmptyCollections.EMPTY_WEAK_VALUE_HASHMAP;

   /** Each domain may have sub domains interested in changes happening in this manager/domain */
   protected volatile WeakHashMap<Domain, Object> subscribedSubDomains = UnmodifiableEmptyCollections.EMPTY_WEAK_HASHMAP;

   /** A queue for adding new subscribed subdomains to */
   protected volatile WeakHashMap<Domain, Object> subscribedSubDomainsQueue = UnmodifiableEmptyCollections.EMPTY_WEAK_HASHMAP;
   protected int subscribedDomainQueueRef;

   protected volatile LinkedHashMap<String, InterfaceIntroduction> interfaceIntroductions = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   protected volatile LinkedHashMap<String, ArrayReplacement> arrayReplacements = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   protected volatile LinkedHashMap<String, ArrayBinding> arrayBindings = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   protected volatile LinkedHashMap<String, AnnotationIntroduction> annotationIntroductions =UnmodifiableEmptyCollections. EMPTY_LINKED_HASHMAP;
   protected volatile LinkedHashMap<String, AnnotationIntroduction> annotationOverrides = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   @Deprecated
   protected volatile LinkedHashMap<String, AdviceBinding> bindings = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   protected final ClassifiedBindingAndPointcutCollection bindingCollection;
   protected volatile LinkedHashMap<String, Typedef> typedefs = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   protected volatile HashMap<String, InterceptorFactory> interceptorFactories = UnmodifiableEmptyCollections.EMPTY_HASHMAP;
   protected volatile HashMap<String,ClassMetaDataLoader> classMetaDataLoaders = UnmodifiableEmptyCollections.EMPTY_HASHMAP;
   protected volatile HashMap<String, AdviceStack> interceptorStacks = UnmodifiableEmptyCollections.EMPTY_HASHMAP;
   protected volatile HashMap<String, DeclareDef> declares = UnmodifiableEmptyCollections.EMPTY_HASHMAP;
   protected volatile ConcurrentHashMap<String, CFlowStack> cflowStacks = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   protected volatile ConcurrentHashMap<String, DynamicCFlowDefinition> dynamicCFlows = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   protected volatile ConcurrentHashMap<String, AspectDefinition> aspectDefinitions = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   protected volatile ConcurrentHashMap<String, Object> perVMAspects = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;

   /** class name prefixes to explicitly exclude unless contained in include. Maintained by top-level AspectManager */
   protected volatile ArrayList<String> exclude = UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;

   /** class name prefixes to explicitly include, this overrides whatever was set in exclude. Maintained by top-level AspectManager */
   protected volatile ArrayList<String> include = UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;

   /** A set of wildcard enabled classnames that will be ignored no matter if they have been included. Maintained by top-level AspectManager */
   protected volatile ArrayList<String> ignore = UnmodifiableEmptyCollections.EMPTY_ARRAYLIST;

   /** A set of annotation names that will be included even though they are invisible. */
   protected List<String> includeInvisibleAnnotations = Collections .emptyList();

   /** ClassExpressions built from ignore. Maintained by top-level AspectManager */
   protected ClassExpression[] ignoreExpressions = new ClassExpression[0];

   /**
    * This lock synchronizes weaving process with operations that change collections
    * read during weaving.
    * For weaving operations, use lock.lockRead(). For operations that change the
    * collections used during weaving, use lock.lockWrite().
    */
   protected static AOPLock lock = new AOPLock();

   protected volatile LinkedHashMap<String, ClassMetaDataBinding> classMetaData = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   protected volatile HashMap<String, DomainDefinition> containers = UnmodifiableEmptyCollections.EMPTY_HASHMAP;
   protected volatile LinkedHashMap<String, PrecedenceDef> precedenceDefs = UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP;
   protected PrecedenceDefEntry[] sortedPrecedenceDefEntries;
   protected WeavingStrategy weavingStrategy;

   protected DynamicAOPStrategy dynamicStrategy = new LoadInterceptedClassesStrategy();

   /** The classloader scoping policy */
   // This shouldn't really be static (artifact of singleton and self-bootstrap design)
   private static AOPClassLoaderScopingPolicy classLoaderScopingPolicy;

   //Keeps track of if we need to convert references etc for a given class. Domains for scoped classloaders will have their own version of this
   protected static Map<ClassLoader, InterceptionMarkers> interceptionMarkers = new WeakHashMap<ClassLoader, InterceptionMarkers>();
   private final static ClassLoader NULL_CLASSLOADER = new URLClassLoader(new URL[0]);
   
   // Static -------------------------------------------------------

   protected static AspectManager manager;
   public static boolean optimize = true;
   public static boolean debugClasses;//If true, the generated advisor instrumentor will output the generated classes
   public static ClassLoaderValidation classLoaderValidator;

   //Keep track of the microcontainer lifecycle callbacks
   public LifecycleManager lifecycleManager = new LifecycleManager(this);

   /**
    * logging switch.  We don't use log4j to avoid another heavy library
    */
   public static boolean verbose = false;

   /**
    * Whether or not we should maintain the deprecated Advisor.methodInterceptors field
    * This is required in jboss 4.x for backwards compatibility with EJB 3
    * See JBAOP-517
    */
   public static boolean maintainAdvisorMethodInterceptors;

   /**
    * Get the top level aspect manager
    *
    * @return the top level aspect manager
    */
   public static synchronized AspectManager getTopLevelAspectManager()
   {
      if (classLoaderScopingPolicy == null)
      {
         //We are not running in jboss
         return instance();
      }

      AspectManager result = initManager();
      Domain scopedDomain = classLoaderScopingPolicy.getTopLevelDomain(result);
      if (scopedDomain != null)
         result = scopedDomain;
      return result;
   }

   public static synchronized AspectManager instance()
   {
      return instance(SecurityActions.getContextClassLoader());
   }

   /**
    * Get the aspect manager for a classloader
    *
    * @param loadingClassLoader the classloader
    * @return the aspect manager
    */
   public static synchronized AspectManager instance(ClassLoader loadingClassLoader)
   {
      AspectManager result = initManager();
      if (classLoaderScopingPolicy != null)
      {
         Domain scopedDomain = classLoaderScopingPolicy.getDomain(loadingClassLoader, result);
         if (scopedDomain != null)
            result = scopedDomain;
      }
      return result;
   }

   /**
    * Initialise the manager if not already dones so<p>
    *
    * This method should be invoked in a synchronized block
    *
    * @return the manager
    */
   private static AspectManager initManager()
   {
      if (manager == null)
      {
         AccessController.doPrivileged(new PrivilegedAction<AspectManager>()
         {
            public AspectManager run()
            {
               String optimized = System.getProperty("jboss.aop.optimized", null);
               if (optimized != null)
               {
                  optimize = (new Boolean(optimized)).booleanValue();
               }
               String pruneit = System.getProperty("jboss.aop.prune", null);
               if (pruneit != null)
               {
                  AOPClassPoolRepository.getInstance().setPrune((new Boolean(pruneit)).booleanValue());
               }
               manager = new AspectManager();
               //Initialise frequently used fields needed by the top-level manager
               manager.subDomainsPerClass = new WeakHashMap<Class<?>, WeakReference<Domain>>();
               manager.exclude = new ArrayList<String>();
               manager.include = new ArrayList<String>();
               manager.ignore = new ArrayList<String>();
               manager.includeInvisibleAnnotations = new ArrayList<String>();
               

               AOPClassPoolRepository.getInstance().setAspectManager(manager);

               if (!verbose)
               {
                  verbose = (new Boolean(System.getProperty("jboss.aop.verbose", "false"))).booleanValue();
               }
               String exclude = System.getProperty("jboss.aop.exclude", null);
               if (exclude != null)
               {
                  ArrayList<String> list = splitString(exclude, ",");
                  manager.setExclude(list);
               }
               String include = System.getProperty("jboss.aop.include", null);
               if (include != null)
               {
                  ArrayList<String> list = splitString(include, ",");
                  manager.setInclude(list);
               }
               String ignore = System.getProperty("jboss.aop.ignore", null);
               if (ignore != null)
               {
                  ArrayList<String> list = splitString(ignore, ",");
                  manager.setIgnore(list);
               }
               String invisibleAnnotations = System.getProperty("jboss.aop.invisible.annotations", null);
               if(invisibleAnnotations != null)
               {
                  ArrayList<String> list = splitString(invisibleAnnotations, ",");
                  manager.setIncludedInvisibleAnnotations(list);
               }
               
               String instrument = System.getProperty("jboss.aop.instrumentor", null);
               InstrumentorFactory.initialise(instrument);

               String advisorName = System.getProperty("jboss.aop.advisor", null);
               AdvisorFactory.initialise(advisorName);

               String debugClass = System.getProperty("jboss.aop.debug.classes", null);
               if (debugClass != null)
               {
                  debugClasses = (new Boolean(debugClass)).booleanValue();
               }

               String methodInterceptors = System.getProperty("jboss.aop.advisor.methodInterceptors", null);
               if (methodInterceptors != null)
               {
                  maintainAdvisorMethodInterceptors = (new Boolean(methodInterceptors)).booleanValue();
               }


               Deployment.deploy();
               return null;
            }
         });
      }
      return manager;
   }

   private static ArrayList<String> splitString(String string, String delim)
   {
      if (string != null)
      {
         ArrayList<String> list = new ArrayList<String>();
         for(String token : string.split(delim))
         {
            list.add(token.trim());
         }
         return list;
      }
      return null;
   }
   
   /**
    * Get the classLoaderScopingPolicy.
    *
    * @return the classLoaderScopingPolicy.
    */
   public static AOPClassLoaderScopingPolicy getClassLoaderScopingPolicy()
   {
      return classLoaderScopingPolicy;
   }

   /**
    * Set the classLoaderScopingPolicy.
    *
    * TODO does it make sense for this to be modified once it has been set?
    * @param classLoaderScopingPolicy the classLoaderScopingPolicy.
    */
   public static void setClassLoaderScopingPolicy(AOPClassLoaderScopingPolicy classLoaderScopingPolicy)
   {
      AspectManager.classLoaderScopingPolicy = classLoaderScopingPolicy;
   }

   public InterceptionMarkers getInterceptionMarkers(ClassLoader loader)
   {
      if (loader == null)
      {
         loader = NULL_CLASSLOADER;
      }
      synchronized(interceptionMarkers)
      {
         InterceptionMarkers markers = interceptionMarkers.get(loader);
         if (markers == null)
         {
            markers = new InterceptionMarkers();
            interceptionMarkers.put(loader, markers);
         }
         return markers;
      }
   }

   public LinkedHashMap<String, Pointcut> getPointcuts()
   {
      return bindingCollection.getPointcuts();
   }

   public LinkedHashMap<String, PointcutInfo> getPointcutInfos()
   {
      return bindingCollection.getPointcutInfos();
   }

   public CFlowStack getCFlowStack(String name)
   {
      return cflowStacks.get(name);
   }

   public void addCFlowStack(CFlowStack stack)
   {
      initCflowStacksMap();
      cflowStacks.put(stack.getName(), stack);
   }

   public void removeCFlowStack(String name)
   {
      cflowStacks.remove(name);
   }

   @Deprecated
   public DynamicCFlow getDynamicCFlow(String name)
   {
      return getDynamicCFlow(name, SecurityActions.getContextClassLoader());
   }
   
   public DynamicCFlow getDynamicCFlow(String name, ClassLoader cl)
   {
      DynamicCFlowDefinition def = dynamicCFlows.get(name);

      if (def != null)
      {
         return def.create(cl);
      }
      return null;
   }


   /* (non-Javadoc)
    * @see org.jboss.aop.Manager#addDynamicCFlow(java.lang.String, org.jboss.aop.advice.DynamicCFlowDefinition)
    */
   public void addDynamicCFlow(String name, DynamicCFlowDefinition cflow)
   {
      initDynamicCflowsMap();
      dynamicCFlows.put(name, cflow);
   }

   public void removeDynamicCFlow(String name)
   {
      dynamicCFlows.remove(name);
   }

   /**
    * This is a callback object that receives events about
    * new pointcuts, interceptors and metadata.
    * The Aspect Management console hooks into this listener
    */
   public static AspectNotificationHandler notificationHandler = null;

   public static boolean suppressTransformationErrors = false;


   /**
    * Suppress when a class cannot be found that a class references
    * This may happen if code in a class references something and the
    * class is not in the classpath.
    */
   public static boolean suppressReferenceErrors = true;

   // Constructors -------------------------------------------------

   /**
    * Called by subclasses
    */
   public AspectManager()
   {
      bindingCollection = createBindingCollection();
   }
   
   /**
    * Creates the binding collection
    * @return a {@link ClassifiedBindingAndPointcutCollection}
    * @see Domain#ini 
    */
   protected ClassifiedBindingAndPointcutCollection createBindingCollection()
   {
      return new ClassifiedBindingAndPointcutCollection();
   }
   
   /**
    * Every &lt;class-metadata&gt; tag corresponds to
    * a ClassMetaDataLoader.  The ClassMetaDataLoader knows how to take
    * arbitrary XML and apply it to SimpleMetaData.
    * <p/>
    * Given a group, return the loader for that group
    */
   public ClassMetaDataLoader findClassMetaDataLoader(String group)
   {
      ClassMetaDataLoader loader = classMetaDataLoaders.get(group);
      if (loader == null) loader = SimpleClassMetaDataLoader.singleton;
      return loader;
   }

   /**
    * Every &lt;class-metadata&gt; tag corresponds to
    * a ClassMetaDataLoader.  The ClassMetaDataLoader knows how to take
    * arbitrary XML and apply it to SimpleMetaData.
    * <p/>
    * Add a loader for a given group
    */
   public void addClassMetaDataLoader(String group, ClassMetaDataLoader loader)
   {
      initClassMetaDataLoadersMap();
      classMetaDataLoaders.put(group, loader);
   }

   /**
    * Remove a loader for a given group
    */
   public void removeClassMetaDataLoader(String group)
   {
      classMetaDataLoaders.remove(group);
   }

   public Map<Class<?>, WeakReference<Advisor>> getAdvisors()
   {
      WeakHashMap<Class<?>, WeakReference<Advisor>> tmpAdvisors = new WeakHashMap<Class<?>, WeakReference<Advisor>>(advisors);
      Map<Class<?>, WeakReference<Domain>> domainsMap = getSubDomainsPerClass();
      if(domainsMap.size() > 0)
      {
         Set<Class<?>> keys = domainsMap.keySet();
         for(Class<?> clazz : keys)
         {
            WeakReference<Domain> ref = domainsMap.get(clazz);
            if (ref != null)
            {
               Domain subDomain = ref.get();
               if(subDomain != null)
               {
                  WeakReference<Advisor> advisorRef = subDomain.advisors.get(clazz);
                  tmpAdvisors.put(clazz, advisorRef);
               }
            }
         }
      }
      return tmpAdvisors;
   }

   public Advisor getAdvisor(String name)
   {
      /*
      synchronized (advisors)
      {
         return (Advisor) advisors.get(name);
      }
      */
      throw new RuntimeException("OPERATION NOT SUPPORTED ANYMORE");
   }

   /**
    * Returns the binding map.
    * <p>
    * The returned map must be used for read purposes only. To edit the
    * binding collection, call {@link #addBinding(AdviceBinding)} and
    * {@link #removeBinding(String)} instead.
    * 
    * @return the map containing all advice bindings indexed by their names
    */
   public LinkedHashMap<String, AdviceBinding> getBindings()
   {
      return bindingCollection.getBindings();
   }
   
   /**
    * Returns the classified binding collection.
    * <p>
    * <b>Attention:</b> this collection is not supposed to be edited. Hence, only
    * get methods can be called by clients.
    * 
    * @return the classified binding collection
    */
   public ClassifiedBindingAndPointcutCollection getBindingCollection()
   {
      return bindingCollection;
   }

   protected Map<Class<?>, WeakReference<Domain>> getSubDomainsPerClass()
   {
      return subDomainsPerClass;
   }

   public Advisor findAdvisor(Class<?> clazz)
   {
      if (getSubDomainsPerClass().size() > 0)
      {
         //For generated advisors each advisor has its own domain
         //This is primarily needed for the reflection aspect
         Domain subDomain = null;
         synchronized (getSubDomainsPerClass())
         {
            WeakReference<Domain> ref = getSubDomainsPerClass().get(clazz);
            if (ref != null)
            {
               subDomain = ref.get();
            }
         }

         if (subDomain != null && subDomain != this)
         {
            Advisor advisor = subDomain.findAdvisor(clazz);
            if (advisor != null)
            {
               return advisor;
            }
         }
      }

      synchronized (advisors)
      {
         WeakReference<Advisor> ref = advisors.get(clazz);
         if (ref == null) return null;
         return ref.get();
      }
   }

   /**
    * Takes a string of the form /sub1/sub2/sub3 of subdomains by name, where the leading "/" is the main AspectManager.
    * The main user of the naming of domains is (un)serialization of advisors/containers
    *
    * @param The FQN of the domain
    * @return the domain referenced by the FQN or null if it does not exist
    */
   public AspectManager findManagerByName(String fqn)
   {
      String[] nameparts = fqn.split("/");
      return findManagerByName(nameparts);
   }

   private AspectManager findManagerByName(String[] nameparts)
   {
      AspectManager found = this;
      for (int i = 0 ; i < nameparts.length ; i++)
      {
         if (nameparts[i].trim().length() == 0)
         {
            continue;
         }
         found = found.findManagerByNameInternal(nameparts[i]);
         if (found == null)
         {
            break;
         }
      }
      return found;
   }

   private AspectManager findManagerByNameInternal(String name)
   {
      return subDomainsByName.get(name);
   }

   protected void addSubDomainByName(Domain domain)
   {
      initSubDomainsByNameMap();
      subDomainsByName.put(domain.getDomainName(), domain);
   }

   public String getManagerFQN()
   {
      return "/";
   }

   public ClassAdvisor getAdvisorIfAdvised(Class<?> clazz)
   {
      return (ClassAdvisor)getAnyAdvisorIfAdvised(clazz);
   }

   /**
    * Take into account that an advisor may be a container
    */
   public Advisor getAnyAdvisorIfAdvised(Class<?> clazz)
   {
      try
      {
         Advisor advisor;
         advisor = findAdvisor(clazz);
         if (advisor == null)
         {
            return null;
         }
         if (advisor.getClazz() == null && advisor instanceof ClassAdvisor)
         {
            ((ClassAdvisor)advisor).attachClass(clazz);
            if (notificationHandler != null)
            {
               notificationHandler.attachClass(clazz.getName());
            }
         }
         return advisor;
      }
      catch (RuntimeException ex)
      {
         ex.printStackTrace();
         throw ex;
      }
   }

   /**
    * This method is called by the aspectized class when it is loaded
    * This causes all initialization of interceptors for ClassAdvisor
    *
    * @param clazz
    * @return
    */
   public synchronized ClassAdvisor getAdvisor(Class<?> clazz)
   {
      ClassAdvisor advisor = null;
      // See if one already exists
      advisor = (ClassAdvisor)findAdvisor(clazz);
      // if one does not
      if (advisor == null)
      {
         advisor = AdvisorFactory.getClassAdvisor(clazz, this);
         initialiseClassAdvisor(clazz, advisor);
      }
      return advisor;
   }

   public void initialiseClassAdvisor(Class<?> clazz, ClassAdvisor advisor)
   {
      // avoiding deadlock. Other threads first get the bindignCollection lock
      // and then the advisors
      // as we know that the bindingCollection lock will be needed during the 
      // Advisor.attachClass method execution, we get the lock at this point
      // making sure we are avoiding the deadlock.
      lock.lockRead();
      try
      {
         synchronized (advisors)
         {
            advisors.put(clazz, new WeakReference<Advisor>(advisor));
            registerClass(clazz);
            advisor.attachClass(clazz);
            InterceptorChainObserver observer = dynamicStrategy.getInterceptorChainObserver(clazz);
            advisor.setInterceptorChainObserver(observer);
            if (notificationHandler != null)
            {
               notificationHandler.attachClass(clazz.getName());
            }
         }
      }
      finally
      {
         lock.unlockRead();
      }
   }

   // Public -------------------------------------------------------

   public static Map<ClassLoader, ClassPool> getRegisteredCLs()
   {
      return AOPClassPoolRepository.getInstance().getRegisteredCLs();
   }

   /**
    * This method will check to see if a register classloader has been undeployed (as in JBoss)
    */
   public static void clearUnregisteredClassLoaders()
   {
      AOPClassPoolRepository.getInstance().clearUnregisteredClassLoaders();
   }

   /**
    * Checks to see if an Advisor represents a class that should have been undeployed.
    *
    * @param advisor
    * @return
    */
   public boolean isAdvisorRegistered(Advisor advisor)
   {
      synchronized (getRegisteredCLs())
      {
         if (!advisors.containsKey(advisor.getClazz())) return false;
         if (classLoaderValidator != null)
         {
            if (classLoaderValidator.isValidClassLoader(advisor.getClassLoader()))
            {
               return true;
            }
            else
            {
               unregisterClassLoader(advisor.getClassLoader());
               return false;
            }
         }
         else
         {
            ScopedClassPool pool = (ScopedClassPool) getRegisteredClassPool(advisor.getClassLoader());
            if (pool == null) return false;
            if (pool.isUnloadedClassLoader())
            {
               unregisterClassLoader(advisor.getClassLoader());
               return false;
            }
            else
            {
               return true;
            }
         }
      }
   }

   public ClassPool findClassPool(ClassLoader cl)
   {
      if (cl == null)
      {
         //!(cl instanceof Translatable)
         // findClassPool has problems with boot and system classes, because
         // they may be null
         return registerClassLoader(SecurityActions.getContextClassLoader());
      }
      return registerClassLoader(cl);
   }
   
   public ClassPool findClassPool(Class<?> clazz)
   {
      return findClassPool(SecurityActions.getClassLoader(clazz));
   }

   protected ClassPool getRegisteredClassPool(ClassLoader cl)
   {
      return getRegisteredCLs().get(cl);
   }

   public ClassPool registerClassLoader(ClassLoader ucl)
   {
      return AOPClassPoolRepository.getInstance().registerClassLoader(ucl);
   }

   protected void registerClass(Class<?> clazz)
   {
      AOPClassPoolRepository.getInstance().registerClass(clazz);
   }

   public void unregisterClassLoader(ClassLoader cl)
   {
      AOPClassPoolRepository.getInstance().unregisterClassLoader(cl);
   }

   public ArrayList<String> getExclude()
   {
      return exclude;
   }

   public void setExclude(ArrayList<String> exclude)
   {
      this.exclude.clear();
      this.exclude.addAll(exclude);
   }

   public ArrayList<String> getInclude()
   {
      return include;
   }

   public void setInclude(ArrayList<String> include)
   {
      this.include.clear();
      this.include.addAll(include);
   }

   public ArrayList<String> getIgnore()
   {
      return ignore;
   }
   
   
   public List<String> getIncludedInvisibleAnnotations()
   {
      return includeInvisibleAnnotations;
   }
   
   public void setIncludedInvisibleAnnotations(List<String> ia)
   {
      includeInvisibleAnnotations.clear();
      includeInvisibleAnnotations.addAll(ia);
   }

   public ClassExpression[] getIgnoreExpressions()
   {
      return ignoreExpressions;
   }

   public void setIgnore(ArrayList<String> ignore)
   {
      this.ignore.clear();
      this.ignore.addAll(ignore);
      ignoreExpressions = new ClassExpression[ignore.size()];
      for (int i = 0 ; i < ignore.size() ; i++)
      {
        String ex = ignore.get(i);
        ignoreExpressions[i] = new ClassExpression(ex);
      }
   }

   public boolean ignoreClass(String classname)
   {
      ArrayList<String> ignore = getIgnore();
      if (ignore == null) return false;
      ClassExpression[] ignoreExprs = getIgnoreExpressions();
      for (int i = 0; i < ignoreExprs.length; i++)
      {
         if(ignoreExprs[i].matches(classname)) return true;
      }
      return false;
   }

   public boolean includeClass(String classname)
   {
      ArrayList<String> include = getInclude();
      if (include == null) return false;
      for (int i = 0; i < include.size(); i++)
      {
         String str = include.get(i);
         if (classname.startsWith(str)) return true;
      }
      return false;
   }

   public boolean excludeClass(String classname)
   {
      ArrayList<String> exclude = getExclude();
      if (exclude == null) return false;
      for (int i = 0; i < exclude.size(); i++)
      {
         String str = exclude.get(i);
         if (str.equals("*")) return true;
         if (classname.startsWith(str)) return true;
      }
      return false;
   }

   public static boolean getPrune()
   {
      return AOPClassPoolRepository.getInstance().isPrune();
   }

   public static void setPrune(boolean prune)
   {
      AOPClassPoolRepository.getInstance().setPrune(prune);
   }

   public static void setClassPoolFactory(ScopedClassPoolFactory factory)
   {
      AOPClassPoolRepository.getInstance().setClassPoolFactory(factory);
   }

   public static ScopedClassPoolFactory getClassPoolFactory()
   {
      return AOPClassPoolRepository.getInstance().getClassPoolFactory();
   }

   public boolean isNonAdvisableClassName(String classname)
   {
      if (ignoreClass(classname)) return true;
      if (includeClass(classname)) return false;
      if (excludeClass(classname)) return true;
      return (classname.startsWith("org.jboss.aop.") ||
              classname.endsWith("$aop") ||
              classname.startsWith("javassist") ||
              classname.startsWith("org.jboss.util.") ||
              classname.startsWith("gnu.trove.") ||
              classname.startsWith("EDU.oswego.cs.dl.util.concurrent.") ||
              classname.contains('.' + JoinPointGenerator.JOINPOINT_CLASS_PREFIX) ||
      // System classes
              classname.startsWith("org.apache.tools.ant") ||
              classname.startsWith("org.apache.crimson") ||
              classname.startsWith("org.apache.xalan") ||
              classname.startsWith("org.apache.xml") ||
              classname.startsWith("org.apache.xpath") ||
              classname.startsWith("org.ietf.") ||
              classname.startsWith("org.omg.") ||
              classname.startsWith("org.w3c.") ||
              classname.startsWith("org.xml.sax.") ||
              classname.startsWith("sunw.") ||
              classname.startsWith("sun.") ||
              classname.startsWith("java.") ||
              classname.startsWith("javax.") ||
              classname.startsWith("com.sun.") ||
              classname.startsWith("junit") ||
              classname.startsWith("jrockit.") ||
              classname.startsWith("com.bea.vm.") ||
              classname.startsWith("$Proxy")
             );
   }

   /**
    * This is the hook for ClassLoaders that want to instrument their classes with AOP
    * <p/>
    * This would be called during a findClass or loadClass call.  The return value
    * is used by defineClass to create the class from bytecode
    */
   public byte[] transform(ClassLoader loader,
                           String className,
                           @SuppressWarnings(value= {"all"}) Class classBeingRedefined,
                           ProtectionDomain protectionDomain,
                           byte[] classfileBuffer)
           throws Exception
   {
      byte[] b = translate(className, loader, classfileBuffer);
      return b;
   }


   /**
    * This is to be backward compatible with JBoss 3.2.3 Translator interface
    *
    * @param className
    * @param loader
    * @return
    * @throws Exception
    */
   public byte[] translate(String className, ClassLoader loader) throws Exception
   {
      return translate(className, loader, null);
   }

   /**
    * This is to be backward compatible with JBoss 3.2.3 Translator interface
    * TODO: stalep, added a synchronized block for the entire method to prevent
    *  a deadlock. its not optimal and should be further reviewed.
    *  (commented out sync block inside the method)
    *
    * @param className
    * @param loader
    * @return
    * @throws Exception
    */
   public byte[] translate(String className, ClassLoader loader, byte[] classfileBuffer) throws Exception
   {
      try
      {
         if (isNonAdvisableClassName(className))
         {
            return null;
         }
         lock.lockRead();
         try
         {
            if (weavingStrategy == null)
            {
               if (TransformerCommon.isCompileTime())
               {
                  weavingStrategy = new ClassicWeavingStrategy();
               }
               else if(InstrumentorFactory.getInstrumentor(this,dynamicStrategy.getJoinpointClassifier())
                       instanceof GeneratedAdvisorInstrumentor)
               {
                  weavingStrategy = new SuperClassesFirstWeavingStrategy();
               }
               else
               {
                  weavingStrategy = new ClassicWeavingStrategy();
               }
            }
            return weavingStrategy.translate(this, className, loader, classfileBuffer);
         }
         finally
         {
            lock.unlockRead();
         }
      }
      catch (Exception e)
      {
         // AutoGenerated
         throw new RuntimeException(e);
      }
   }

   /**
    * Add an interceptor factory that can be referenced by name.
    */
   public void addInterceptorFactory(String name, InterceptorFactory factory)
   {
      initInterceptorFactoriesMap();
      synchronized (interceptorFactories)
      {
         interceptorFactories.put(name, factory);
      }
   }

   /**
    * Remove an interceptor factory that can be referenced by name.
    */
   public void removeInterceptorFactory(String name)
   {
      synchronized (interceptorFactories)
      {
         interceptorFactories.remove(name);
      }
   }

   public Map<String, InterceptorFactory> getInterceptorFactories()
   {
      return interceptorFactories;
   }

   /**
    * Find the interceptor factory that can be referenced by name.
    */
   public InterceptorFactory getInterceptorFactory(String name)
   {
      synchronized (interceptorFactories)
      {
         return interceptorFactories.get(name);
      }
   }


   public void addPrecedence(PrecedenceDef precedenceDef)
   {
      initPrecedenceDefsMap();
      synchronized (precedenceDefs)
      {
         precedenceDefs.put(precedenceDef.getName(), precedenceDef);
      }
      forceResortPrecedenceDefs();
   }

   public void removePrecedence(String name)
   {
      synchronized (precedenceDefs)
      {
         precedenceDefs.remove(name);
      }

      forceResortPrecedenceDefs();
   }

   protected void forceResortPrecedenceDefs()
   {
      synchronized (precedenceDefs)
      {
         sortedPrecedenceDefEntries = null;
      }
      synchronized (subscribedSubDomains)
      {
         copySubDomainsFromQueue(true);
         boolean newSubscribers = true;
         while (newSubscribers)
         {
            for (Domain domain : subscribedSubDomains.keySet())
            {
               domain.forceResortPrecedenceDefs();
            }
            newSubscribers = copySubDomainsFromQueue(false);
         }
      }
   }

   public LinkedHashMap<String, PrecedenceDef> getPrecedenceDefs()
   {
      return precedenceDefs;
   }

   public PrecedenceDefEntry[] getSortedPrecedenceDefEntries()
   {
      if (sortedPrecedenceDefEntries == null)
      {
         synchronized (precedenceDefs)
         {
            if (sortedPrecedenceDefEntries == null)
            {
               sortedPrecedenceDefEntries = PrecedenceSorter.createOverallPrecedence(this);
            }
         }
      }
      return sortedPrecedenceDefEntries;
   }

   /**
    * Add a referencable InterceptorStack( &lt;stack&gt; )
    */
   public void addAdviceStack(AdviceStack stack)
   {
      initInerceptorStacksMap();
      synchronized (interceptorStacks)
      {
         interceptorStacks.put(stack.getName(), stack);
      }
   }

   /**
    * Remove a referencable InterceptorStack( &lt;stack&gt; )
    */
   public void removeInterceptorStack(String name)
   {
      synchronized (interceptorStacks)
      {
         interceptorStacks.remove(name);
      }
   }

   /**
    * Find an interceptor stack referenced by name ( &lt;stack&gt; )
    */
   public AdviceStack getAdviceStack(String name)
   {
      synchronized (interceptorStacks)
      {
         return interceptorStacks.get(name);
      }
   }


   protected boolean attachMetaData(ClassAdvisor advisor, CtClass clazz, boolean addAdvisor) throws Exception
   {
      boolean attached = false;
      synchronized (classMetaData)
      {
         for (ClassMetaDataBinding data : classMetaData.values())
         {
            if (data.matches(advisor, clazz))
            {
               attached = true;
               if (addAdvisor) data.addAdvisor(advisor);
               ClassMetaDataLoader loader = data.getLoader();
               loader.bind(advisor, data, clazz.getDeclaredMethods(), clazz.getDeclaredFields(), clazz.getDeclaredConstructors());
            }
         }
      }
      return attached;
   }

   protected void attachMetaData(Advisor advisor, Class<?> clazz)
   {
      synchronized (classMetaData)
      {
         for (ClassMetaDataBinding data : classMetaData.values())
         {
            addAdvisorToClassMetaDataBinding(data, clazz, advisor, clazz);
         }
      }
   }

   public ClassAdvisor getTempClassAdvisor(CtClass clazz) throws Exception
   {
      ClassAdvisor advisor = AdvisorFactory.getClassAdvisor(clazz, this);
      attachMetaData(advisor, clazz, false);
      applyInterfaceIntroductions(advisor, clazz);
      return advisor;
   }

   public Advisor getTempClassAdvisorIfNotExist(Class<?> clazz)
   {
      Advisor advisor = findAdvisor(clazz);
      if (advisor != null) return advisor;
      if (Advised.class.isAssignableFrom(clazz))
      {

         Class<?> superClass = clazz;
         try
         {
            while (superClass != null)
            {
               try
               {
                  Field field = superClass.getDeclaredField(Instrumentor.HELPER_FIELD_NAME);
                  SecurityActions.setAccessible(field);
                  advisor = (ClassAdvisor) field.get(null);
                  if (advisor != null)
                  {
                     return advisor;
                  }
                  else
                  {
                     break;
                  }
               }
               catch (NoSuchFieldException e)
               {
                  superClass = superClass.getSuperclass();
               }
            }
         }
         catch (IllegalAccessException e)
         {
            throw new RuntimeException(e);
         }
      }

      advisor = AdvisorFactory.getClassAdvisor(clazz, this);
      advisor.setClazz(clazz);
      return advisor;
   }


   public DomainDefinition getContainer(String name)
   {
      return containers.get(name);
   }

   public void addContainer(DomainDefinition def)
   {
      initContainersMap();
      containers.put(def.getName(), def);
   }

   public void removeContainer(String name)
   {
      containers.remove(name);
   }

   /**
    * Find a pointcut of with a given name
    */
   public Pointcut getPointcut(String name)
   {
      return bindingCollection.getPointcut(name);
   }

   /**
    * Remove an interceptor pointcut with a given name
    */
   public void removePointcut(String name)
   {
      lock.lockWrite();
      try
      {
         bindingCollection.removePointcut(name);
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   /**
    * Add an interceptor pointcut with a given name
    */
   public void addPointcut(Pointcut pointcut)
   {
      lock.lockWrite();
      try
      {
         bindingCollection.add(pointcut, this);
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public boolean isExecution()
   {
      return bindingCollection.isExecution();
   }

   public boolean isConstruction()
   {
      return bindingCollection.isConstruction();
   }

   public boolean isCall()
   {
      return bindingCollection.isCall();
   }

   public boolean isWithin()
   {
      return bindingCollection.isWithin();
   }

   public boolean isWithincode()
   {
      return bindingCollection.isWithincode();
   }

   public boolean isGet()
   {
      return bindingCollection.isGet();
   }

   public boolean isSet()
   {
      return bindingCollection.isSet();
   }

   /**
    * Remove an interceptor pointcut with a given name
    */
   public void removeBinding(String name)
   {
      lock.lockWrite();
      try
      {
         AdviceBinding binding = internalRemoveBinding(name);
         if (binding != null)
         {
            binding.clearAdvisors();
            dynamicStrategy.interceptorChainsUpdated();
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public void removeBindings(ArrayList<String> binds)
   {
      clearUnregisteredClassLoaders();

      HashSet<Advisor> bindingAdvisors = new HashSet<Advisor>();
      ArrayList<AdviceBinding> removedBindings = null;
      
      lock.lockWrite();
      try
      {
         removedBindings = this.bindingCollection.removeBindings(binds);
         for (AdviceBinding removedBinding: removedBindings)
         {
            ArrayList<Advisor> ads = removedBinding.getAdvisors();
            bindingAdvisors.addAll(ads);
            Pointcut pointcut = removedBinding.getPointcut();
            this.removePointcut(pointcut.getName());
         }
      }
      finally
      {
         lock.unlockWrite();
      }
      
      Iterator<Advisor> it = bindingAdvisors.iterator();
      while (it.hasNext())
      {
         Advisor advisor = it.next();
         if (!isAdvisorRegistered(advisor))
         {
            //Check sub domains in case of generated advisors

            WeakReference<Domain> ref = getSubDomainsPerClass().get(advisor.getClazz());
            Domain domain = null;
            if (ref != null) domain = ref.get();
            if (domain != null)
            {
               if (subscribedSubDomains.containsKey(domain) || subscribedSubDomainsQueue.containsKey(domain))
               {
                  if (!domain.isAdvisorRegistered(advisor))continue;
               }
               else
               {
                  continue;//If advisor does not belong to a subscribed subdomain, we should not rebuild
               }
            }
         }
         advisor.removeAdviceBindings(removedBindings);
      }
      dynamicStrategy.interceptorChainsUpdated();
   }

   /**
    * Add an interceptor pointcut with a given name
    */
   public void addBinding(AdviceBinding binding)
   {
      Set<Advisor> affectedAdvisors = null;
      AdviceBinding removedBinding = null;
      lock.lockWrite();
      try
      {
         removedBinding = internalRemoveBinding(binding.getName());
         affectedAdvisors = removedBinding == null ? null : new HashSet<Advisor>(removedBinding.getAdvisors());         
         bindingCollection.add(binding, this);
         synchronized (advisors)
         {
            Set<Advisor> handledAdvisors = new HashSet<Advisor>();
            updateAdvisorsForAddedBinding(binding, handledAdvisors);

            if (affectedAdvisors != null && affectedAdvisors.size() > 0)
            {
               for (Advisor advisor : affectedAdvisors)
               {
                  if (isAdvisorRegistered(advisor))
                     advisor.removeAdviceBinding(removedBinding);
               }
            }
         }
         this.dynamicStrategy.interceptorChainsUpdated();
      }
      finally
      {
         lock.unlockWrite();
      }
   }


   protected void updateAdvisorsForAddedBinding(AdviceBinding binding, Set<Advisor> handledAdvisors)
   {
      synchronized (advisors)
      {
         Collection<Class<?>> keys = advisors.keySet();
         if (keys.size() > 0)
         {
            Iterator<Class<?>> it = keys.iterator();
            while (it.hasNext())
            {
               Advisor advisor = getAdvisorFromAdvisorsKeySetIterator(it);
               if (advisor == null) continue;
               if (handledAdvisors.contains(advisor)) continue;
               handledAdvisors.add(advisor);
               
               if (binding.getPointcut().softMatch(advisor))
               {
                  if (AspectManager.verbose && logger.isDebugEnabled())
                     logger.debug("softmatch succeeded for : " + advisor.getName() + " " + binding + " " + binding.getPointcut().getExpr());
                  advisor.newBindingAdded(binding);
                  //affectedAdvisors.remove(advisor);
               }
               else
               {
                  if (AspectManager.verbose && logger.isDebugEnabled())
                     logger.debug("softmatch failed for : " + advisor.getName() + " " + binding + " " + binding.getPointcut().getExpr());
               }
            }
         }
      }
      synchronized (subscribedSubDomains)
      {
         copySubDomainsFromQueue(true);
         boolean newSubscribers = true;
         while (newSubscribers)
         {
            Collection<Domain> domains =  subscribedSubDomains.keySet();
            if (domains.size() > 0)
            {
               //When interceptors are installed as beans in the microcontainer, creating the interceptor instances
               for (Domain domain : domains)
               {
                  domain.updateAdvisorsForAddedBinding(binding, handledAdvisors);
               }
            }
            newSubscribers = copySubDomainsFromQueue(false);
         }
      }
   }

   public void removeClassMetaData(String name)
   {
      internalRemoveClassMetaData(name);
   }

   public void internalRemoveClassMetaData(String name)
   {
      synchronized (classMetaData)
      {
         ClassMetaDataBinding meta = classMetaData.remove(name);
         if (meta == null) return;
         meta.clearAdvisors();
      }
   }

   public void addClassMetaData(ClassMetaDataBinding meta)
   {
      internalRemoveClassMetaData(meta.getName());

      //Add the metadata before we update the advisors. Important for the generated instance advisors
      initClassMetaDataMap();
      synchronized (classMetaData)
      {
         classMetaData.put(meta.getName(), meta);
      }

      updateAdvisorsForAddedClassMetaData(meta);
   }

   protected void updateAdvisorsForAddedClassMetaData(ClassMetaDataBinding meta)
   {
      synchronized (advisors)
      {
         Iterator<Class<?>> it = advisors.keySet().iterator();

         while (it.hasNext())
         {
            Advisor advisor = getAdvisorFromAdvisorsKeySetIterator(it);
            if (advisor == null) continue;

            Class<?> clazz = advisor.getClazz();
            addAdvisorToClassMetaDataBinding(meta, clazz, advisor, clazz);
         }
      }

      synchronized (subscribedSubDomains)
      {
         copySubDomainsFromQueue(true);
         boolean newSubscribers = true;
         while (newSubscribers)
         {
            if (subscribedSubDomains.size() > 0)
            {
               for (Domain domain : subscribedSubDomains.keySet())
               {
                  domain.updateAdvisorsForAddedClassMetaData(meta);
               }
            }
            newSubscribers = copySubDomainsFromQueue(false);
         }
      }
   }

   protected void addAdvisorToClassMetaDataBinding(ClassMetaDataBinding meta, Class<?> clazz, Advisor advisor, Class<?> advisedClass)
   {
      if (meta.matches(advisor, clazz))
      {
            meta.addAdvisor(advisor);
      }
      else if (advisor.chainOverridingForInheritedMethods())
      {
         //If advisor class does not match class metadata directly, try the superclasses so that methods can inherit
         //old skool weaving doesn't support metadata overriding for inherited methods, so only do this extra work for generated advisors
         Class<?> superClass = clazz.getSuperclass();
         if (superClass != null && superClass != Object.class)
         {
            addAdvisorToClassMetaDataBinding(meta, superClass, advisor, advisedClass);
         }
      }
   }

   //--- Introductions

   /**
    * Retrieve an introduction pointcut of a certain name
    */
   public InterfaceIntroduction getInterfaceIntroduction(String name)
   {
      synchronized (interfaceIntroductions)
      {
         return interfaceIntroductions.get(name);
      }
   }

   /**
    * Register an introduction pointcut
    */
   public void addInterfaceIntroduction(InterfaceIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         removeInterfaceIntroduction(pointcut.getName());
         initInterfaceIntroductionsMap();
         synchronized (interfaceIntroductions)
         {
            interfaceIntroductions.put(pointcut.getName(), pointcut);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   /**
    * remove an introduction pointcut of a certain name
    */
   public void removeInterfaceIntroduction(String name)
   {
      lock.lockWrite();
      try
      {
         synchronized (interfaceIntroductions)
         {
            InterfaceIntroduction pointcut = interfaceIntroductions.remove(name);
            if (pointcut == null) return;
            pointcut.clearAdvisors();
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   /**
    * Retrieve an introduction pointcut of a certain name
    */
   public ArrayReplacement getArrayReplacement(String name)
   {
      synchronized (arrayReplacements)
      {
         return arrayReplacements.get(name);
      }
   }

   /**
    * Register an introduction pointcut
    */
   public void addArrayReplacement(ArrayReplacement pointcut)
   {
      lock.lockWrite();
      try
      {
         removeArrayReplacement(pointcut.getName());
         initArrayReplacementMap();
         synchronized (arrayReplacements)
         {
            arrayReplacements.put(pointcut.getName(), pointcut);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   /**
    * remove an introduction pointcut of a certain name
    */
   public void removeArrayReplacement(String name)
   {
      lock.lockWrite();
      try
      {
         synchronized (arrayReplacements)
         {
            arrayReplacements.remove(name);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   /**
    * Retrieve an introduction pointcut of a certain name
    */
   public ArrayBinding getArrayBinding(String name)
   {
      synchronized (arrayBindings)
      {
         return arrayBindings.get(name);
      }
   }

   /**
    * Register an introduction pointcut
    */
   public void addArrayBinding(ArrayBinding binding)
   {
      lock.lockWrite();
      try
      {
         removeArrayBinding(binding.getName());
         initArrayBindingMap();
         synchronized (arrayBindings)
         {
            arrayBindings.put(binding.getName(), binding);
            ArrayAdvisor.addBinding(binding);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   /**
    * remove an introduction pointcut of a certain name
    */
   public void removeArrayBinding(String name)
   {
      lock.lockWrite();
      try
      {
         synchronized (arrayBindings)
         {
            ArrayBinding pointcut = arrayBindings.remove(name);
            if (pointcut == null) return;
            ArrayAdvisor.removeBinding(pointcut);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }


   /**
    * Register an annotation introduction
    */
   public void addAnnotationIntroduction(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         String name = pointcut.getOriginalAnnotationExpr() + pointcut.getOriginalExpression();
         removeAnnotationIntroduction(pointcut);
         initAnnotationIntroductionsMap();
         synchronized (annotationIntroductions)
         {
            annotationIntroductions.put(name, pointcut);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   /**
    * remove an annotation pointcut
    */
   public void removeAnnotationIntroduction(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         String name = pointcut.getOriginalAnnotationExpr() + pointcut.getOriginalExpression();
         synchronized (annotationIntroductions)
         {
            annotationIntroductions.remove(name);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public List<AnnotationIntroduction> getAnnotationIntroductions()
   {
      synchronized (annotationIntroductions)
      {
         return new ArrayList<AnnotationIntroduction>(annotationIntroductions.values());
      }
   }

   public void addDeclare(DeclareDef declare)
   {
      lock.lockWrite();
      try
      {
         removeDeclare(declare.getName());
         initDeclaresMap();
         synchronized (declares)
         {
            declares.put(declare.getName(), declare);
         }
         if (declare.isPointcut())
         {
            PointcutStats stats;
            stats = new PointcutStats(declare.getAst(), manager);
            stats.matches();
            bindingCollection.updateStats(stats);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public void removeDeclare(String name)
   {
      lock.lockWrite();
      try
      {
         synchronized (declares)
         {
            declares.remove(name);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public Iterator<DeclareDef> getDeclares()
   {
      return declares.values().iterator();
   }

   protected void applyInterfaceIntroductions(Advisor advisor, Class<?> clazz)
   {
      Map<String, InterfaceIntroduction> interfaceIntroductions = getInterfaceIntroductions();
      if (interfaceIntroductions != null && interfaceIntroductions.size() > 0)
      {
         for (InterfaceIntroduction pointcut : interfaceIntroductions.values())
         {
            if (pointcut.matches(advisor, clazz))
            {
               pointcut.addAdvisor(advisor);
            }
         }
      }
   }

   protected void applyInterfaceIntroductions(ClassAdvisor advisor, CtClass clazz) throws Exception
   {
      Map<String, InterfaceIntroduction> interfaceIntroductions = getInterfaceIntroductions();
      if (interfaceIntroductions != null && interfaceIntroductions.size() > 0)
      {
         for (InterfaceIntroduction pointcut : interfaceIntroductions.values())
         {
            if (pointcut.matches(advisor, clazz))
            {
               pointcut.addAdvisor(advisor);
            }
         }
      }
   }


   /**
    * Register an annotation introduction
    */
   public void addAnnotationOverride(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         String name = pointcut.getOriginalAnnotationExpr() + pointcut.getOriginalExpression();
         initAnnotationOverridesMap();
         synchronized (annotationOverrides)
         {
            annotationOverrides.put(name, pointcut);
         }
         updateAdvisorsForAddedAnnotationOverride(pointcut);
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public void updateAdvisorsForAddedAnnotationOverride(AnnotationIntroduction introduction)
   {
      synchronized (advisors)
      {
         Iterator<Class<?>> it = advisors.keySet().iterator();
         while (it.hasNext())
         {
            Advisor advisor = getAdvisorFromAdvisorsKeySetIterator(it);
            if (advisor == null) continue;

            advisor.deployAnnotationOverride(introduction);
         }
      }
      synchronized (subscribedSubDomains)
      {
         copySubDomainsFromQueue(true);
         boolean newSubscribers = true;
         while (newSubscribers)
         {
            for (Domain domain : subscribedSubDomains.keySet())
            {
               domain.updateAdvisorsForAddedAnnotationOverride(introduction);
            }
            newSubscribers = copySubDomainsFromQueue(false);
         }
      }
   }


   /**
    * remove an annotation pointcut
    */
   public void removeAnnotationOverride(AnnotationIntroduction pointcut)
   {
      lock.lockWrite();
      try
      {
         String name = pointcut.getOriginalAnnotationExpr() + pointcut.getOriginalExpression();
         synchronized (annotationOverrides)
         {
            annotationOverrides.remove(name);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public List<AnnotationIntroduction> getAnnotationOverrides()
   {
      synchronized (annotationOverrides)
      {
         return new ArrayList<AnnotationIntroduction>(annotationOverrides.values());
      }
   }

   public Object getPerVMAspect(AspectDefinition def)
   {
      return getPerVMAspect(def.getName());
   }

   public Object getPerVMAspect(String def)
   {
      Object aspect = perVMAspects.get(def);
      if (aspect == null)
      {
         AspectDefinition adef = aspectDefinitions.get(def);
         if (adef != null && adef.getScope() == Scope.PER_VM)
         {
            synchronized (adef)
            {
               // double check but, now, in a sync block
               aspect = perVMAspects.get(def);
               if (aspect == null)
               {
                  aspect = createPerVmAspect(def, adef, null);
               }
            }
         }
      }
      if (aspect == NULL_ASPECT)
      {
         return null;
      }
      return aspect;
   }

   protected Object createPerVmAspect(String def, AspectDefinition adef, ClassLoader scopedClassLoader)
   {
      Object instance = null;
      synchronized (adef)
      {
         try
         {
            if (scopedClassLoader != null && adef.getFactory() instanceof AspectFactoryWithClassLoader)
            {
               //If a scoped classloader with no parent delegation redefines the class, we need to make sure that that class is pushed on the stack
               ((AspectFactoryWithClassLoader)adef.getFactory()).pushScopedClassLoader(scopedClassLoader);
            }
            instance = adef.getFactory().createPerVM();
            initPerVMAspectsMap();
            if (instance == null)
            {
            	// indicates that the factory must not be called again
            	// the factory has already been called and returned null
               perVMAspects.put(def, NULL_ASPECT);
            }
            else
            {
               perVMAspects.put(def, instance);
            }
         }
         finally
         {
            if (scopedClassLoader != null && adef.getFactory() instanceof AspectFactoryWithClassLoader)
            {
               ((AspectFactoryWithClassLoader)adef.getFactory()).popScopedClassLoader();
            }
         }
      }
      return instance;
   }

   public void addAspectDefinition(AspectDefinition def)
   {
      removeAspectDefinition(def.getName());
      initAspectDefintitionsMap();
      aspectDefinitions.put(def.getName(), def);
   }

   public void removeAspectDefinition(String name)
   {
      internalRemoveAspectDefintion(name);
   }

   protected AspectDefinition internalRemoveAspectDefintion(String name)
   {
      AspectDefinition def = aspectDefinitions.remove(name);
      if (def != null)
      {
         def.undeploy();
         if (def.getScope() == Scope.PER_VM) perVMAspects.remove(def.getName());
      }
      return def;
   }

   public Map<String, AspectDefinition> getAspectDefinitions()
   {
      return aspectDefinitions;
   }

   public AspectDefinition getAspectDefinition(String name)
   {
      return aspectDefinitions.get(name);
   }
   
   public void addTypedef(Typedef def) throws Exception
   {
      lock.lockWrite();
      try
      {
         removeTypedef(def.getName());
         initTypedefsMap();
         synchronized (typedefs)
         {
            typedefs.put(def.getName(), def);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public void removeTypedef(String name)
   {
      lock.lockWrite();
      try
      {
         synchronized (typedefs)
         {
            typedefs.remove(name);
         }
      }
      finally
      {
         lock.unlockWrite();
      }
   }

   public Typedef getTypedef(String name)
   {
      synchronized (typedefs)
      {
         return typedefs.get(name);
      }
   }

   public Map<String, InterfaceIntroduction> getInterfaceIntroductions()
   {
      return interfaceIntroductions;
   }

   public Map<String, ArrayReplacement> getArrayReplacements()
   {
      return arrayReplacements;
   }

   public Map<String, Typedef> getTypedefs()
   {
      return typedefs;
   }

   public Map<String, AdviceStack> getInterceptorStacks()
   {
      return interceptorStacks;
   }

   public Map<String, ClassMetaDataLoader> getClassMetaDataLoaders()
   {
      return classMetaDataLoaders;
   }

   public Map<String, CFlowStack> getCflowStacks()
   {
      return cflowStacks;
   }

   public Map<String, DynamicCFlowDefinition> getDynamicCFlows()
   {
      return dynamicCFlows;
   }

   public Map<String, Object> getPerVMAspects()
   {
      return perVMAspects;
   }

   public Map<String, ClassMetaDataBinding> getClassMetaData()
   {
      return classMetaData;
   }

   /**
    * Returns the dynamic aop strategy to be used.
    */
   public DynamicAOPStrategy getDynamicAOPStrategy()
   {
      return this.dynamicStrategy;
   }

   /**
    * Sets the dynamic aop strategy to be used.
    * Should be called only before any class is transformed.
    * @param strategy the new dynamic aop strategy.
    */
   public void setDynamicAOPStrategy(DynamicAOPStrategy strategy)
   {
      // avoid users calling this method in run time
      if (hasTransformationStarted())
      {
         throw new RuntimeException("Dynamic AOP Strategy Update not allowed in run time");
      }
      this.dynamicStrategy = strategy;
   }

   /**
    * Removes an AdviceBinding without notifying dynamic aop strategy.
    * 
    * Needs to be called from inside a synchronized lock with the AspectManager.lock.
    * 
    * @param name the binding to be removed.
    */
   private AdviceBinding internalRemoveBinding(String name)
   {
      AdviceBinding binding = bindingCollection.removeBinding(name);
      if (binding == null)
      {
         return null;
      }
      Pointcut pointcut = binding.getPointcut();
      this.removePointcut(pointcut.getName());
      return binding;
   }

//   public void setBindings(LinkedHashMap bindings)
//   {
//      initBindingsMap();
//      this.bindings.clear();
//      this.bindings.putAll(bindings);
//   }

   public void addSubDomainPerClass(Class<?> clazz, Domain domain)
   {
      synchronized (getSubDomainsPerClass())
      {
         getSubDomainsPerClass().put(clazz, new WeakReference<Domain>(domain));
      }
   }

   /**
    * Add subscriber to queue
    * @see AspectManager#copySubDomainsFromQueue(boolean)
    */
   public void subscribeSubDomain(Domain domain)
   {
      initSubscribedSubDomainsMap();
      initSubscribedSubDomainsQueueMap();
      synchronized (subscribedSubDomains)
      {
         subscribedSubDomainsQueue.put(domain, "Contents do not matter");
      }
   }

   public void unsubscribeSubDomain(Domain domain)
   {
      synchronized (subscribedSubDomains)
      {
         subscribedSubDomains.remove(domain);
      }
   }

   public Map<Domain, Object> getSubscribedSubDomains()
   {
      return subscribedSubDomains;
   }

   private Advisor getAdvisorFromAdvisorsKeySetIterator(Iterator<Class<?>> it)
   {
      Class<?> clazz = it.next();
      if (classLoaderValidator != null && !classLoaderValidator.isValidClassLoader(SecurityActions.getClassLoader(clazz)))
      {
         it.remove();
         return null;
      }
      WeakReference<Advisor> ref = advisors.get(clazz);
      if (ref == null) return null;
      Advisor advisor = ref.get();
      if (advisor == null)
      {
         it.remove();
         return null;
      }
      return advisor;
   }

   /**
    * When running in the microcontainer with aspects installed as beans, a ClassProxyContainer will be created per bean
    * to check if this bean needs interceptors, each container creates a sunscribed domain for matching. This subscribed
    * domain is added to a queue, which is checked when we need to iterate over the subscribed domains.
    */
   private boolean copySubDomainsFromQueue(boolean increment)
   {
      boolean copied = false;
      initSubscribedSubDomainsMap();
      synchronized (subscribedSubDomains)
      {
         if (!increment && subscribedDomainQueueRef > 0) subscribedDomainQueueRef--;

         if (subscribedDomainQueueRef == 0 && subscribedSubDomainsQueue.size() > 0){
            subscribedSubDomains.putAll(subscribedSubDomainsQueue);
            subscribedSubDomainsQueue.clear();
            copied = true;
         }

         if (increment) subscribedDomainQueueRef++;
      }
      return copied;
   }

   public void addLifecycleDefinition(AspectDefinition def)
   {
      lifecycleManager.addLifecycleDefinition(def);
   }

   public void removeLifecycleDefinition(String name)
   {
      lifecycleManager.removeLifecycleDefinition(name);
   }

   public void addLifecycleBinding(LifecycleCallbackBinding lifecycleBinding)
   {
      lifecycleManager.addLifecycleBinding(lifecycleBinding);
   }

   public Map<String, LifecycleCallbackBinding> getLifecycleBindings()
   {
      return lifecycleManager.getLifecycleBindings();
   }

   public void removeLifecycleBinding(String name)
   {
      lifecycleManager.removeLifecycleBinding(name);
   }

   public static boolean hasTransformationStarted()
   {
      return WeavingStrategySupport.transformationStarted();
   }

//   public void dumpSubDomainsAndAdvisors(int indent)
//   {
//      indent(indent);
//      System.out.println("Manager: " + this);
//      indent++;
//      indent(indent);
//      System.out.println("<Advisors>");
//      //indent(indent);
//
//      for (Iterator it = advisors.keySet().iterator() ; it.hasNext() ; )
//      {
//         Class clazz = (Class) it.next();
//         Advisor advisor = null;
//         WeakReference ref = (WeakReference) advisors.get(clazz);
//         if (ref != null) advisor = (Advisor) ref.get();
//         indent(indent);
//         System.out.println(System.identityHashCode(advisor) + " " + advisor);
//         indent(indent);
//      }
//      indent--;
//      indent(indent);
//      System.out.println("</Advisors>");
//
//      indent(indent);
//      System.out.println("<Sub domains>");
//      indent++;
//      for (Iterator it = subscribedSubDomains.keySet().iterator(); it.hasNext() ; )
//      {
//         AspectManager manager = (AspectManager)it.next();
//         manager.dumpSubDomainsAndAdvisors(indent);
//      }
//      indent--;
//      indent(indent);
//      System.out.println("</Sub domains>");
//      indent--;
//
//   }
//
//   private void indent(int indent)
//   {
//      for (int i = 0 ; i < indent ; i++) System.out.print(" ");
//   }

   protected void initSubDomainsByNameMap()
   {
      if (subDomainsByName == UnmodifiableEmptyCollections.EMPTY_WEAK_VALUE_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (subDomainsByName == UnmodifiableEmptyCollections.EMPTY_WEAK_VALUE_HASHMAP)
            {
               subDomainsByName = new WeakValueHashMap<String, Domain>();
            }
         }
      }
   }

   protected void initSubscribedSubDomainsMap()
   {
      if (subscribedSubDomains == UnmodifiableEmptyCollections.EMPTY_WEAK_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (subscribedSubDomains == UnmodifiableEmptyCollections.EMPTY_WEAK_HASHMAP)
            {
               subscribedSubDomains = new WeakHashMap<Domain, Object>();
            }
         }
      }
   }

   protected void initSubscribedSubDomainsQueueMap()
   {
      if (subscribedSubDomainsQueue == UnmodifiableEmptyCollections.EMPTY_WEAK_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (subscribedSubDomainsQueue == UnmodifiableEmptyCollections.EMPTY_WEAK_HASHMAP)
            {
               subscribedSubDomainsQueue = new WeakHashMap<Domain, Object>();
            }
         }
      }
   }

   protected void initInterfaceIntroductionsMap()
   {
      if (interfaceIntroductions == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (interfaceIntroductions == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               interfaceIntroductions = new LinkedHashMap<String, InterfaceIntroduction>();
            }
         }
      }
   }

   protected void initArrayReplacementMap()
   {
      if (arrayReplacements == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (arrayReplacements == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               arrayReplacements = new LinkedHashMap<String, ArrayReplacement>();
            }
         }
      }
   }

   protected void initArrayBindingMap()
   {
      if (arrayBindings == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (arrayBindings == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               arrayBindings = new LinkedHashMap<String, ArrayBinding>();
            }
         }
      }
   }


   protected void initAnnotationIntroductionsMap()
   {
      if (annotationIntroductions == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (annotationIntroductions == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               annotationIntroductions = new LinkedHashMap<String, AnnotationIntroduction>();
            }
         }
      }
   }

   protected void initAnnotationOverridesMap()
   {
      if (annotationOverrides == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (annotationOverrides == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               annotationOverrides = new LinkedHashMap<String, AnnotationIntroduction>();
            }
         }
      }
   }

   protected void initTypedefsMap()
   {
      if (typedefs == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (typedefs == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               typedefs = new LinkedHashMap<String, Typedef>();
            }
         }
      }
   }

   protected void initInterceptorFactoriesMap()
   {
      if (interceptorFactories == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (interceptorFactories == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
            {
               interceptorFactories = new HashMap<String, InterceptorFactory>();
            }
         }
      }
   }

   protected void initClassMetaDataLoadersMap()
   {
      if (classMetaDataLoaders == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (classMetaDataLoaders == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
            {
               classMetaDataLoaders = new HashMap<String, ClassMetaDataLoader>();
            }
         }
      }
   }

   protected void initInerceptorStacksMap()
   {
      if (interceptorStacks == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (interceptorStacks == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
            {
               interceptorStacks = new HashMap<String, AdviceStack>();
            }
         }
      }
   }


   protected void initDeclaresMap()
   {
      if (declares == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (declares == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
            {
               declares = new HashMap<String, DeclareDef>();
            }
         }
      }
   }

   protected void initCflowStacksMap()
   {
      if (cflowStacks == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (cflowStacks == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               cflowStacks = new ConcurrentHashMap<String, CFlowStack>();
            }
         }
      }
   }

   protected void initDynamicCflowsMap()
   {
      if (dynamicCFlows == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (dynamicCFlows == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               dynamicCFlows = new ConcurrentHashMap<String, DynamicCFlowDefinition>();
            }
         }
      }
   }

   protected void initAspectDefintitionsMap()
   {
      if (aspectDefinitions == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (aspectDefinitions == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               aspectDefinitions = new ConcurrentHashMap<String, AspectDefinition>();
            }
         }
      }
   }

   protected void initPerVMAspectsMap()
   {
      if (perVMAspects == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (perVMAspects == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               perVMAspects = new ConcurrentHashMap<String, Object>();
            }
         }
      }
   }

   protected void initClassMetaDataMap()
   {
      if (classMetaData == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (classMetaData == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               classMetaData = new LinkedHashMap<String, ClassMetaDataBinding>();
            }
         }
      }
   }
   protected void initContainersMap()
   {
      if (containers == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (containers == UnmodifiableEmptyCollections.EMPTY_HASHMAP)
            {
               containers = new HashMap<String, DomainDefinition>();
            }
         }
      }
   }

   protected void initPrecedenceDefsMap()
   {
      if (precedenceDefs == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (precedenceDefs == UnmodifiableEmptyCollections.EMPTY_LINKED_HASHMAP)
            {
               precedenceDefs = new LinkedHashMap<String, PrecedenceDef>();
            }
         }
      }
   }
}
