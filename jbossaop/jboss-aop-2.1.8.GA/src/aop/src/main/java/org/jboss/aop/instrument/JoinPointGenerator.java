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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.CallerConstructorInfo;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.JoinPointInfo;
import org.jboss.aop.advice.AdviceMethodProperties;
import org.jboss.aop.advice.AdviceType;
import org.jboss.aop.advice.GeneratedAdvisorInterceptor;
import org.jboss.aop.advice.InvalidAdviceException;
import org.jboss.aop.advice.NoMatchingAdviceException;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.JoinPointBean;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.util.GetDeclaringClassUtil;
import org.jboss.aop.util.JavassistUtils;
import org.jboss.aop.util.ReflectToJavassist;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Creates the Joinpoint invocation replacement classes used with Generated advisors
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author adrian@jboss.org
 * @version $Revision$
 */
public abstract class JoinPointGenerator
{
   private static final AOPLogger logger = AOPLogger.getLogger(JoinPointGenerator.class);
   
   public static final String INFO_FIELD = "info";
   public static final String INITIALISED_LIGHTWEIGHT_INSTANCE_ASPECTS = "initialisedLightweightInstanceAspects";
   public static final String INITIALISE_LIGHTWEIGHT_INSTANCE_ASPECTS = "initialisePerInstanceAspects";
   public static final String IS_FOR_INSTANCE_ADVISOR = "isForInstanceAdvisor";
   public static final String INVOKE_JOINPOINT = "invokeJoinpoint";
   public static final String INVOKE_TARGET = "invokeTarget";
   public static final String DISPATCH = "dispatch";
   protected static final String TARGET_FIELD = "targetObject";
   protected static final String TYPED_TARGET_FIELD = "typedTargetObject";
   protected static final String CALLER_FIELD = "callingObject";
   protected static final String TYPED_CALLER_FIELD = "typedCallingObject";
   protected static final String GENERATED_CLASS_ADVISOR = GeneratedClassAdvisor.class.getName();
   public static final String GENERATE_JOINPOINT_CLASS = "generateJoinPointClass";
   private static final String CURRENT_ADVICE = "super.currentInterceptor";
   public static final String JOINPOINT_FIELD_PREFIX = "joinpoint_";
   public static final String JOINPOINT_CLASS_PREFIX = "JoinPoint_";
   private static final String RETURN_VALUE = "ret";
   private static final String THROWABLE = "t";
   protected static final String ARGUMENTS= "arguments";
   private static final String GET_ARGUMENTS= OptimizedBehaviourInvocations.GET_ARGUMENTS + "()";
   protected static final CtClass[] EMPTY_CTCLASS_ARRAY = new CtClass[0];
   protected static final CtClass[] THROWS_THROWABLE;
   static
   {
         try
         {
            THROWS_THROWABLE = new CtClass[]{
                  AspectManager.instance().findClassPool(
                        SecurityActions.getContextClassLoader()).
                        get("java.lang.Throwable")};
         } catch (NotFoundException e)
         {
            throw new RuntimeException(e);
         }
      
   }
   
   private final ArrayList<Integer> joinPointArguments;
   private final boolean nullArgsArray;
   
   private JoinPointParameters parameters;
   private static int increment;
   private Class<?> advisorClass;
   private String baseJoinPointClassName;
   protected String joinpointClassName;
   protected String joinpointFieldName;
   private String joinpointFqn;
   private Field joinpointField;
   
   private ThreadLocal<Set<Integer>> inconsistentTypeArgs;
   
   /**
    * A cache of the generated joinpoint classes indexed by the interceptor chains for the info to 
    * avoid having to generate a new class on every single rebind
    */
   private HashMap<String, GeneratedClassInfo> generatedJoinPointClassCache =
      new HashMap<String, GeneratedClassInfo>();
   
   /**
    * Constructor.
    * 
    * @param advisor        the advisor associated to this generator
    * @param info           information regarding the joinpoint to be intercepted
    * @param parameters     indicates the parameters available to the interception
    *                       of this joinpoint
    * @param argumentsSize  number of joinpoint arguments
    * @param nullArgsArray  <code>true</code> to indicate that the arguments array
    *                       should be null when there is not argument available
    */
   protected JoinPointGenerator(GeneratedClassAdvisor advisor, JoinPointInfo info,
         JoinPointParameters parameters, int argumentsSize, boolean nullArgsArray)
   {
      // this.info = info;
      this.parameters = parameters;
      // this.advisor = advisor;
      this.advisorClass = advisor.getClass();
      this.nullArgsArray = nullArgsArray;
      Class<?>[] interfaces = advisorClass.getInterfaces();
      
      for (int i = 0 ; i < interfaces.length ; i++)
      {
         if (interfaces[i].equals(InstanceAdvisor.class))
         {
            //The InstanceAdvisor extends the Advisor, which is what contains the JoinPoint field
            advisorClass = advisorClass.getSuperclass();
            break;
         }
      }
      // helper collection
      joinPointArguments = new ArrayList<Integer>(argumentsSize);
      // iterate once so we don't need to iterate always (use this array instead)
      for (int i = 0; i < argumentsSize; i++)
      {
         joinPointArguments.add(i);
      }
      // joinpoint generation helper field, will contain list of typed args that are
      // inconsistent with arguments array
      // is ThreadLocal so we can support more than one Thread
      // generating the a joinpoint class
      inconsistentTypeArgs = new ThreadLocal<Set<Integer>>()
      {
         protected synchronized Set<Integer> initialValue() {
            return new HashSet<Integer>();
         }
      };
    
      initialiseJoinPointNames(info);
      findAdvisedField(info);
      initBaseJoinPointClassName(advisor);
   }
   
   private void initBaseJoinPointClassName(GeneratedClassAdvisor advisor)
   {
      Package pkg = advisor.getClass().getPackage();
      
      StringBuffer className = new StringBuffer();
      if (pkg != null)
      {
         className.append(pkg.getName());
         className.append(".");
      }
      className.append(joinpointClassName);
      className.append("_");
      baseJoinPointClassName = className.toString();
   }
   
   public void rebindJoinpoint(JoinPointInfo newInfo)
   {
      try
      {
         if (joinpointField == null) return;
         joinpointField.set(newInfo.getAdvisor(), null);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         StringBuffer sb = new StringBuffer();
         debugClass(sb, newInfo.getAdvisor().getClass());
         System.out.println("==================== Error");
         System.out.println("Field: " + joinpointField);
         System.out.println("Field: " + joinpointField.getDeclaringClass() + " " + SecurityActions.getClassLoader(joinpointField.getDeclaringClass()));
         System.out.println("Value: " + newInfo.getAdvisor().getClass() + " " + SecurityActions.getClassLoader(newInfo.getAdvisor().getClass()));
         System.out.println(sb.toString());
         
         throw new RuntimeException(e);
      }
   }
      
   /**
    * Called by the joinpoint if a interceptors were regenereated
    * Here for backwards compatiblity with AOP 1.5.0
    */
   public void generateJoinPointClass()
   {
      generateJoinPointClass(null, null);
   }
   
   /**
    * Called by the joinpoint if a interceptors were regenereated
    */
   public synchronized Object generateJoinPointClass(ClassLoader classloader, JoinPointInfo info)
   {
      if (info == null)
      {
         throw new RuntimeException("GeneratedAdvisor weaving in AOP 2.0.0.aplha5 and later is not compatible with that of previous versions");
      }
      
      if (System.getSecurityManager() == null)
      {
         return GenerateJoinPointClassAction.NON_PRIVILEGED.generateJoinPointClass(classloader, this, info);
      }
      else
      {
         return GenerateJoinPointClassAction.PRIVILEGED.generateJoinPointClass(classloader, this, info);
      }
   }
    
   /**
    * Does the work for generateJoinPointClass()
    * @see JoinPointGenerator#generateJoinPointClass()
    */
   private Object doGenerateJoinPointClass(ClassLoader classloader, JoinPointInfo info)
   {
      try
      {
         if (classloader  == null)
         {
            logger.warn("No classloader specified " + info.getAdviceString());
            classloader = SecurityActions.getContextClassLoader();
         }

         //Attempt to get the cached information so we don't have to recreate the class every time we rebind the joinpoint
         String infoAdviceString = info.getAdviceString();
         GeneratedClassInfo generatedClass = null;
         if (generatedJoinPointClassCache.containsKey(infoAdviceString))
         {
            generatedClass= generatedJoinPointClassCache.get(infoAdviceString);
         }  
         else
         {
            //We need to do all the work again
            AspectManager manager = AspectManager.instance();
            ClassPool pool = manager.findClassPool(classloader);
            ProtectionDomain pd = advisorClass.getProtectionDomain();
            generatedClass = generateJoinpointClass(pool, info, classloader, pd);
            generatedJoinPointClassCache.put(infoAdviceString, generatedClass);
         }
         Object obj = generatedClass.createJoinPointInstance(classloader, info);
         
         joinpointField.set(info.getAdvisor(), obj);
         return obj;
      }
      catch (NoMatchingAdviceException e)
      {
         throw e;
      }
      catch (InvalidAdviceException e)
      {
         throw e;
      }
      catch (Throwable e)
      {
//         for (int i = 0 ; i < info.getInterceptors().length ; i++)
//         {
//            GeneratedAdvisorInterceptor icptr = (GeneratedAdvisorInterceptor)info.getInterceptors()[i]; 
//            System.out.println(" - - - - - - - - - - - - - ");
//            System.out.println(icptr.getName());
//         }
         e.printStackTrace();
         throw new RuntimeException("Error generating joinpoint class for joinpoint " + info, e);
      }
   }

   private Class<?> toClass(ClassLoader classLoader, CtClass ctclass, ProtectionDomain pd) throws NotFoundException, CannotCompileException, ClassNotFoundException
   {
      return TransformerCommon.toClass(ctclass, classLoader, pd);
   }
   
   private StringBuffer debugClass(StringBuffer sb, Class<?> clazz)
   {
      sb.append("\n\t\t" + Modifier.toString(clazz.getModifiers()) + " " + clazz.getName() + " " + SecurityActions.getClassLoader(clazz)); 
      Field[] fields = clazz.getDeclaredFields();
      for (int i = 0 ; i < fields.length ; i++)
      {
         sb.append("\n\t\t\t" + Modifier.toString(fields[i].getModifiers()) + " " + fields[i].getType().getName() + " " + fields[i].getName() + " " + SecurityActions.getClassLoader(fields[i].getType()));
      }
     
     Class<?> superClass = clazz.getSuperclass();
     if (superClass != null && superClass != Object.class)
     {
        sb.append("\n\t\t\textends\n");
        debugClass(sb, superClass);
     }
     return sb;
   }
   
   
   private static synchronized int getIncrement()
   {
      return ++increment;
   }
   
   /**
    * This method returns the code that access the joinpoint arg number
    * <code>index</code> (for invocations).
    * 
    * @param index the index of the desired arg field
    */
   protected String getJoinPointArg(int index)
   {
      return "this.arg" + index;
   }
   
   protected abstract void initialiseJoinPointNames(JoinPointInfo info);

   private GeneratedClassInfo generateJoinpointClass(ClassPool pool, JoinPointInfo newInfo, ClassLoader classloader, ProtectionDomain pd) throws NotFoundException,
   CannotCompileException, ClassNotFoundException
   {
      CtClass superClass = pool.get(joinpointFqn);
      String className = getJoinpointClassName();
      try
      {
         CtClass clazz = TransformerCommon.makeClass(pool, className);
         clazz.setSuperclass(superClass);
         addUntransformableInterface(pool, clazz);
  
         AdviceSetups setups = initialiseAdviceInfosAndAddFields(pool, clazz, newInfo);
         createInitialisePerInstanceAspectsMethod(clazz, setups, newInfo.getClazz());
         
         createConstructors(pool, superClass, clazz, setups);
         createJoinPointInvokeMethod(
               superClass, 
               clazz, 
               isVoid(),
               setups,
               newInfo);
  
         createInvokeNextMethod(clazz, isVoid(), setups, newInfo);
  
         overrideDispatchMethods(superClass, clazz, newInfo);
         Class<?> loadedClass= toClass(classloader, clazz, pd);
         return new GeneratedClassInfo(loadedClass, setups);
      }
      catch (NotFoundException e)
      {
         logger.error("Exception generating " + className + ": " + e.getMessage(), e);
         throw e;
      }
      catch (CannotCompileException e)
      {
         logger.error("Exception generating " + className + ": " + e.getMessage(), e);
         throw e;
      }
      catch (ClassNotFoundException e)
      {
         logger.error("Exception generating " + className + ": " + e.getMessage(), e);
         throw e;
      }
   }

   private String getJoinpointClassName()
   {
      return baseJoinPointClassName + getIncrement();
   }

   private void createInitialisePerInstanceAspectsMethod(CtClass clazz, AdviceSetups setups, Class<?> advisedClass) throws CannotCompileException, NotFoundException
   {
      if (setups.hasLightweightAdvicesRequiringInstanceAdvisor())
      {
         StringBuffer code = new StringBuffer("{");
         for (AdviceSetup setup : setups.getLightweightAdvicesRequiringInstanceAdvisor())
         {
            AdviceType type = setup.getType();
            if (type != AdviceType.AROUND && setup.requiresInstanceAdvisor())
            {
               code.append(getPerInstanceAspectCode("$1", setup, false));
            }
            
         }
         code.append("}");

         String name = advisedClass.getName();
         CtClass ctTarget = clazz.getClassPool().get(name);
         
         CtMethod method = CtNewMethod.make(
               Modifier.PRIVATE, 
               CtClass.voidType,
               INITIALISE_LIGHTWEIGHT_INSTANCE_ASPECTS,
               new CtClass[] {ctTarget},//target
               EMPTY_CTCLASS_ARRAY,
               code.toString(),
               clazz);
         clazz.addMethod(method);
         
      }
   }
   
   protected abstract boolean isVoid();
   protected abstract Class<?> getReturnClassType(); 
   protected abstract AdviceMethodProperties getAdviceMethodProperties(JoinPointBean info, AdviceSetup setup);
   
   protected boolean isCaller()
   {
      return false;
   }
   
   protected boolean hasCallingObject()
   {
      return false;
   }
   
   protected abstract boolean hasTargetObject();
   
   private boolean isStaticCall()
   {
      if (isCaller())
      {
         return !hasCallingObject();
      }
      else
      {
         return !hasTargetObject();
      }
   }
   
   private void findAdvisedField(JoinPointInfo info)
   {
      if (info.getClazz() == null)
      {
         return;
      }
      Class<?> advisorClass = null;
      if (info.getAdvisor().getClazz().equals(info.getClazz()))
      {
         // short cut, avoid cost of else block
         advisorClass = info.getAdvisor().getClass();
      }
      else
      {
         AspectManager manager = info.getAdvisor().getManager();
         try
         {
            advisorClass = manager.getAdvisor(info.getClazz()).getClass();
         }
         catch(ClassCastException e)
         {
            Advisor advisor = manager.findAdvisor(info.getClazz());
            if (advisor != null && !( advisor instanceof ClassAdvisor))
            {
               //The advisor is a ClassContainer or something like that, so ignore this joinpoint
               return;
            }
         }
      }
      
      try
      {
         joinpointField = advisorClass.getField(joinpointFieldName);
         SecurityActions.setAccessible(joinpointField);
         joinpointFqn = GetDeclaringClassUtil.getDeclaringClass(advisorClass).getName() + "$" + joinpointClassName;
      }
      catch (NoSuchFieldException e)
      {
         throw new RuntimeException(e);
      }
      catch (NoClassDefFoundError e)
      {
         throw e;
      }
   }

   private AdviceSetups initialiseAdviceInfosAndAddFields(ClassPool pool, CtClass clazz, JoinPointInfo info) throws ClassNotFoundException, NotFoundException, CannotCompileException
   {
      HashMap<String, Integer> cflows = new HashMap<String, Integer>();
      Collection<AdviceSetup> setups = new ArrayList<AdviceSetup>(info.getInterceptors().length);
      
      ClassLoader classLoader = pool.getClassLoader();
      if (classLoader == null)
      {
         logger.warn("No classloader specified " + clazz.getName(), new Throwable("STACKTRACE"));
         classLoader = SecurityActions.getContextClassLoader();
      }

      for (int i = 0 ; i < info.getInterceptors().length ; i++)
      {
         AdviceSetup setup = new AdviceSetup(i, (GeneratedAdvisorInterceptor)info.getInterceptors()[i], info, classLoader);
         if (!setup.shouldInvokeAspect())
         {
            continue;
         }
         setups.add(setup);
         addAspectFieldAndGetter(pool, clazz, setup);
         addCFlowFieldsAndGetters(pool, setup, clazz, cflows);
      }
      addLightweightInstanceAspectsTrackerFields(clazz);
      AdviceSetup[] setupArray = setups.toArray(new AdviceSetup[setups.size()]);
      return new AdviceSetups(info, setupArray);
   }
   
   /**
    * 
    * @param pool
    * @param clazz
    * @param setup represents an advice that should be invoked (i.e.,
    *              {@code setup.shouldBeInvoked()} is {@code true})
    * @throws NotFoundException
    * @throws CannotCompileException
    */
   private void addAspectFieldAndGetter(ClassPool pool, CtClass clazz, AdviceSetup setup) throws NotFoundException, CannotCompileException
   {
      CtClass aspectClass = setup.getAspectCtClass();
      
      CtField field = new CtField(aspectClass, setup.getAspectFieldName(), clazz);
      field.setModifiers(Modifier.PRIVATE | Modifier.TRANSIENT);
      clazz.addField(field);
      
      String body = getAspectFieldGetterBody(setup); 
      CtMethod method = CtNewMethod.make(
            aspectClass, 
            setup.getAspectInitialiserName(), 
            new CtClass[0], 
            new CtClass[0], 
            body, 
            clazz);
      method.setModifiers(Modifier.PRIVATE);
      clazz.addMethod(method);
   }

   private String getAspectFieldGetterBody(AdviceSetup setup)
   {
      if (setup.requiresInstanceAdvisor())
      {
         String instanceAdvisor = (isCaller()) ? CALLER_FIELD : TARGET_FIELD;
                        
         return getPerInstanceAspectCode(instanceAdvisor, setup, true);
      }
      else
      {
         return
            "{" +
            "   if (" + setup.getAspectFieldName() + " != null)" +
            "   {" +
            "      return " + setup.getAspectFieldName() + ";" +
            "   }" +
            "   org.jboss.aop.advice.GeneratedAdvisorInterceptor fw = (org.jboss.aop.advice.GeneratedAdvisorInterceptor)info.getInterceptors()[" + setup.getIndex() + "];" +
            "   Object o = fw.getAspect(info.getAdvisor(), info.getJoinpoint());" +
            "   return (" + setup.getAspectClass().getName() + ")o;" +
            "}";
      }
   }
   
   private String getPerInstanceAspectCode(String objectWithInstanceAdvisor, AdviceSetup setup, boolean shouldReturn)
   {
      String instanceAdvisor = "org.jboss.aop.InstanceAdvisor ia = ((org.jboss.aop.Advised)" + objectWithInstanceAdvisor + ")._getInstanceAdvisor();";
      String assignOrReturn = (shouldReturn) ? "return " : setup.getAspectFieldName() + " = ";
      return
      "{" +
      "   " + instanceAdvisor +
      "   org.jboss.aop.advice.GeneratedAdvisorInterceptor fw = (org.jboss.aop.advice.GeneratedAdvisorInterceptor)info.getInterceptors()[" + setup.getIndex() + "];" +
      "   Object o = fw.getPerInstanceAspect(info.getAdvisor(), info.getJoinpoint(), ia);" +
      "   " + assignOrReturn + "(" + setup.getAspectClass().getName() + ")o;" +
      "}";
   }
   
   private void addCFlowFieldsAndGetters(ClassPool pool, AdviceSetup setup,
         CtClass clazz, HashMap<String, Integer> cflows)
         throws NotFoundException, CannotCompileException
   {
      if (setup.getCFlowString() != null)
      {
         Integer useCFlowIndex = cflows.get(setup.getCFlowString());
         if (useCFlowIndex == null)
         {
            useCFlowIndex = new Integer(setup.getIndex());
            cflows.put(setup.getCFlowString(), useCFlowIndex);
            
            CtField cflowX = new CtField(
                  pool.get(ASTCFlowExpression.class.getName()), 
                  "cflow" + useCFlowIndex, 
                  clazz);
            clazz.addField(cflowX);
            
            CtField matchesCFlowX = new CtField(
                  CtClass.booleanType, 
                  "matchesCflow" + useCFlowIndex, 
                  clazz);
            clazz.addField(matchesCFlowX);
            
            String initCFlowXBody = 
               "{" +
               "   org.jboss.aop.pointcut.CFlowMatcher matcher = new org.jboss.aop.pointcut.CFlowMatcher();" +
               "   return matcher.matches(" + cflowX.getName() + ", this);" +
               "}";
            CtMethod initCFlowX = CtNewMethod.make(
                  CtClass.booleanType,
                  "getCFlow" + useCFlowIndex,
                  new CtClass[0],
                  new CtClass[0],
                  initCFlowXBody,
                  clazz);
            clazz.addMethod(initCFlowX);
         }
         setup.setUseCFlowFrom(useCFlowIndex.intValue());
      }
   }
   
   private void addLightweightInstanceAspectsTrackerFields(CtClass clazz) throws CannotCompileException
   {
      CtField initialised = new CtField(CtClass.booleanType, INITIALISED_LIGHTWEIGHT_INSTANCE_ASPECTS, clazz);
      clazz.addField(initialised);
      
      CtField forInstance = new CtField(CtClass.booleanType, IS_FOR_INSTANCE_ADVISOR, clazz);
      clazz.addField(forInstance);
   }
   
   private void createJoinPointInvokeMethod(CtClass superClass, CtClass clazz, boolean isVoid, AdviceSetups setups, JoinPointInfo info) throws CannotCompileException, NotFoundException
   {
      CtMethod superInvoke = superClass.getDeclaredMethod(INVOKE_JOINPOINT);
      String code = null;
      try
      {
         code = createJoinpointInvokeBody(
               superClass,
               clazz, 
               setups,
               superInvoke.getExceptionTypes(), superInvoke.getParameterTypes(), info);
         CtMethod invoke = CtNewMethod.make(
               superInvoke.getReturnType(), 
               superInvoke.getName(), 
               superInvoke.getParameterTypes(), 
               superInvoke.getExceptionTypes(), 
               code, 
               clazz);
         clazz.addMethod(invoke);
      }
      catch (CannotCompileException e)
      {
         throw new RuntimeException("Error compiling code for Joinpoint (" + info.getJoinpoint() +"): " + code + "\n - " + e + "\n - " + getMethodString(clazz, superInvoke.getName(), superInvoke.getParameterTypes()) + "\n - " + clazz.getName(), e);
      }
   }
   
   private String createJoinpointInvokeBody(CtClass joinpointSuperClass, CtClass joinpointClass,
         AdviceSetups setups, CtClass[] declaredExceptions, CtClass[] parameterTypes, JoinPointInfo info)throws NotFoundException
   {
      AdviceCallStrategy defaultCall = DefaultAdviceCallStrategy.getInstance();
      AdviceCallStrategy afterThrowingCall = AfterThrowingAdviceCallStrategy.getInstance();
      AdviceCallStrategy afterCall = AfterAdviceCallStrategy.getInstance();
      
      StringBuffer code = new StringBuffer();
      code.append("{");

      if (!isVoid())
      {
         String ret = null;
         Class<?> retType = getReturnClassType();
         if (retType.isPrimitive())
         {
            if (retType.equals(Boolean.TYPE)) ret = "false";
            else if (retType.equals(Character.TYPE)) ret = "'\\0'";
            else if (retType.equals(Byte.TYPE)) ret = "(byte)0";
            else if (retType.equals(Short.TYPE)) ret = "(short)0";
            else if (retType.equals(Integer.TYPE)) ret = "(int)0";
            else if (retType.equals(Long.TYPE)) ret = "0L";
            else if (retType.equals(Float.TYPE)) ret = "0.0f";
            else if (retType.equals(Double.TYPE)) ret =  "0.0d";
         }
         code.append("   " + ClassExpression.simpleType(getReturnClassType()) + "  " + RETURN_VALUE + " = " + ret + ";");
      }
      
      // declare the throwable in an outer variable (this is needed for finally)
      code.append("Throwable ").append(THROWABLE).append(" = null;");
      
      
      addInitialiseLightweightPerInstanceAdvicesCode(joinpointSuperClass, info, code, setups);

      code.append("   try");
      code.append("   {");
      boolean argsFoundBefore = defaultCall.
         addInvokeCode(this, setups.getByType(AdviceType.BEFORE), code, info);
      
      // add around according to whether @Args were found before
      boolean joinPointCreated = addAroundInvokeCode(code, setups, joinpointClass,
            argsFoundBefore, parameterTypes);
      
      // generate after code
      StringBuffer afterCode = new StringBuffer();
      boolean argsFoundAfter = afterCall.addInvokeCode(this,
            setups.getByType(AdviceType.AFTER), afterCode, info);
      afterCode.append("   }");
      afterCode.append("   catch(java.lang.Throwable throwable)");
      afterCode.append("   {");
      // store throwable in THROWABLE variable
      afterCode.append(THROWABLE).append(" = ").append("throwable;");
      argsFoundAfter = afterThrowingCall.addInvokeCode(this,
            setups.getByType(AdviceType.THROWING), afterCode, info) || argsFoundAfter;
      afterCode.append("throw t;");
      //addHandleExceptionCode(afterCode, declaredExceptions);
      afterCode.append("   }");
      
      AdviceSetup[] finallySetups = setups.getByType(AdviceType.FINALLY);
      if (finallySetups!= null && finallySetups.length > 0)
      {
         afterCode.append("   finally {");
         argsFoundAfter = afterCall.addInvokeCode(this, finallySetups, afterCode,
               info) || argsFoundAfter;
         afterCode.append("}");
      }
      
      // if joinpoint has been created for around,
      // need to update arguments variable when this variable is used,
      // which happens in one of both cases
      // 1.an @Args parameter is found on after code
      // 2.an @Arg parameter is found on after code (in this case, we need
      //   to update the variable value according to what is contained in joinpoint)
      if (joinPointCreated &&  (argsFoundAfter ||
            inconsistentTypeArgs.get().size() < joinPointArguments.size()))
      {
         code.append(ARGUMENTS);
         code.append(" = jp.").append(GET_ARGUMENTS).append(";");
         argsFoundAfter = true; // force creation of arguments variable
      }
      
      // add after code
      code.append(afterCode.toString());
      
      if (!isVoid())
      {
         code.append("   return " + RETURN_VALUE + ";");
      }

      code.append("}");

      // declare arguments array if necessary
      if (argsFoundBefore || argsFoundAfter)
      {
         code.insert(1, parameters.declareArgsArray(parameterTypes.length, nullArgsArray));
      }

      return code.toString();
   }

   private void addInitialiseLightweightPerInstanceAdvicesCode(CtClass joinpointSuperClass, JoinPointInfo info, StringBuffer code, AdviceSetups setups)
   {
      if (setups.hasLightweightAdvicesRequiringInstanceAdvisor())
      {
         if (!hasCallingObject() && !hasTargetObject())
         {
            //Without either a calling object or a target object we can not create an instance advisor
            return;
         }

         String instanceAdvisor = "$" + parameters.getContextIndex();
         
         code.append("if (!" + INITIALISED_LIGHTWEIGHT_INSTANCE_ASPECTS + "){");
         code.append("   if(" + IS_FOR_INSTANCE_ADVISOR + "){");
         code.append("      " + INITIALISE_LIGHTWEIGHT_INSTANCE_ASPECTS + "(" + instanceAdvisor + ");");
         code.append(INITIALISED_LIGHTWEIGHT_INSTANCE_ASPECTS + " = true;");
         code.append("   }else{");
         code.append("      org.jboss.aop.GeneratedClassAdvisor instanceAdvisor = (org.jboss.aop.GeneratedClassAdvisor)((org.jboss.aop.Advised)" + instanceAdvisor + ")._getInstanceAdvisor();");
         code.append("      Object objJp = instanceAdvisor.createAndRebindJoinPointForInstance((org.jboss.aop.JoinPointInfo)super.info);");//Needs to be info for the instance advisor, convert?
         code.append("      " + joinpointSuperClass.getName() + " jp = (" + joinpointSuperClass.getName() + ")objJp;");
         if (isVoid())
         {
            code.append("      jp." + INVOKE_JOINPOINT + "($$);");
            code.append("return;");
         }
         else
         {
            code.append("      return jp." + INVOKE_JOINPOINT + "($$);");
         }
         code.append("   }");
         code.append("}");
      }
   }
   
   private boolean addAroundInvokeCode(StringBuffer code, AdviceSetups setups,
         CtClass joinpointClass, boolean argsFoundBefore, CtClass[] parameterTypes)
   throws NotFoundException
   {
      if (setups.getByType(AdviceType.AROUND) != null)
      {
         StringBuffer aspects = new StringBuffer();
         StringBuffer cflows = new StringBuffer();
         
         AdviceSetup[] asetups = setups.getAllSetups(); 
         for (int i = 0 ; i < asetups.length ; i++)
         {
            if (asetups[i].requiresInstanceAdvisor())
            {
            }
            else
            {
               aspects.append(", ");
               aspects.append(asetups[i].getAspectFieldName());
            }
         
            if (asetups[i].isNewCFlow())
            {
               cflows.append(", cflow" + asetups[i].getIndex());
            }
         }
         code.append(joinpointFqn).append(" jp = new " + joinpointClass.getName() + "(this");
         if (argsFoundBefore)
         {
            parameters.appendParameterListWithoutArgs(code);
            
         }
         else
         {
            code.append(", $$");
         }
         
         code.append(aspects.toString() + cflows.toString() + ");");
         
         if (argsFoundBefore)
         {
            code.append("   jp.setArguments(");
            code.append(ARGUMENTS);
            code.append(");");
         }
         
         if (setups.getHasArgsAroundAdvices())
         {
            code.append("try{");
            code.append("   org.jboss.aop.joinpoint.CurrentInvocation.push(jp); ");
         }
         
         
         if (!isVoid())
         {
            code.append("          " + RETURN_VALUE + " = ($r)");
         }
         code.append("jp.invokeNext();");
         
         if (setups.getHasArgsAroundAdvices())
         {
            code.append("}finally{");
            code.append("   org.jboss.aop.joinpoint.CurrentInvocation.pop(); ");
            code.append("}");
         }
         
         // 'after' code will find all args inconsistent, since we have to update
         // arguments array according to invocation values
         inconsistentTypeArgs.get().addAll(joinPointArguments);
         return true;
      }
      else
      {
         addDispatchCode(code, parameterTypes, argsFoundBefore);
         return false;
      }
   }

   private final void addDispatchCode(StringBuffer code, CtClass[] parameterTypes,
         boolean argsFound)
   {
      if (! isVoid())
      {
         code.append("          " + RETURN_VALUE + " = ($r)");
      }
      code.append("super.dispatch(");
      if (argsFound)
      {
         parameters.appendParameterList(code, parameterTypes);
      }
      else
      {
         code.append("$$");
      }
      code.append(");");
   }
   
//   private void addHandleExceptionCode(StringBuffer code, CtClass[] declaredExceptions)
//   {
//      for (int i = 0 ; i < declaredExceptions.length ; i++)
//      {
//         code.append("if (t instanceof " + declaredExceptions[i].getName() + ")");
//         code.append("   throw (" + declaredExceptions[i].getName() + ")t;");
//      }
//      
//      code.append("if (t instanceof java.lang.RuntimeException)");
//      code.append(   "throw t;");
//      
//      code.append("throw new java.lang.RuntimeException(t);");
//   }

   private void createInvokeNextMethod(CtClass jp, boolean isVoid, AdviceSetups setups, JoinPointInfo info) throws NotFoundException, CannotCompileException
   {
      AdviceSetup[] aroundSetups = setups.getByType(AdviceType.AROUND);
      if (aroundSetups == null) return;
      
      CtMethod method = jp.getSuperclass().getSuperclass().getDeclaredMethod("invokeNext");
      CtMethod invokeNext = CtNewMethod.copy(method, jp, null);
      
      String code = createInvokeNextMethodBody(jp, isVoid, aroundSetups, info);
      
      try
      {
         invokeNext.setBody(code);
      }
      catch (CannotCompileException e)
      {
         throw new RuntimeException("Error creating invokeNext method: " + code, e);
      }
      
      jp.addMethod(invokeNext);
   }

   private String createInvokeNextMethodBody(CtClass jp, boolean isVoid, AdviceSetup[] aroundSetups, JoinPointInfo info) throws NotFoundException
   {
      final String returnStr = (isVoid) ? "" : "return ($w)";

      StringBuffer body = new StringBuffer();
      body.append("{");
      body.append("   try{");
      body.append("      switch(++" + CURRENT_ADVICE + "){");
      new AroundAdviceCallStrategy().addInvokeCode(this, aroundSetups, body, info);
      body.append("      default:");
      body.append("         " + returnStr + "this.dispatch();");
      body.append("      }");
      body.append("   }finally{");
      body.append("      --" + CURRENT_ADVICE + ";");
      body.append("   }");
      body.append("   return null;");
      body.append("}");
      
      return body.toString();
   }
   
   private void createConstructors(ClassPool pool, CtClass superClass, CtClass clazz, AdviceSetups setups) throws NotFoundException, CannotCompileException
   {
      CtConstructor[] superCtors = superClass.getDeclaredConstructors();
      if (superCtors.length != 3 && superCtors.length != 2 && !this.getClass().equals(MethodJoinPointGenerator.class)
            && !FieldJoinPointGenerator.class.isAssignableFrom(this.getClass()))
      {
         throw new RuntimeException("JoinPoints should have 2 or 3 constructors, not " + superCtors.length);
      }
      else if (superCtors.length != 4 && superCtors.length != 3 && this.getClass().equals(MethodJoinPointGenerator.class))
      {
         throw new RuntimeException("Method JoinPoints should have 3 or 4 constructors, not " + superCtors.length);
      }
      
      int publicIndex = -1;
      int protectedIndex1 = -1;
      int protectedIndex2 = -1;
      int defaultIndex = -1;
      
      for (int i = 0 ; i < superCtors.length ; i++)
      {
         int modifier = superCtors[i].getModifiers();
         if (Modifier.isPublic(modifier))
         {
            if (superCtors[i].getParameterTypes().length == 0) defaultIndex = i;
            else publicIndex = i;
         }
         else if (Modifier.isProtected(modifier))
         {
            if (protectedIndex1 == -1)
            {
               protectedIndex1 = i;
            }
            else
            {
               protectedIndex2 = i;
            }
         }
      }
      
      if (publicIndex < 0 || protectedIndex1 < 0)
      {
         throw new RuntimeException("One of the JoinPoint constructors should be public, and at least one of them should be protected");
      }
      
      if (defaultIndex >= 0)
      {
         createDefaultConstructor(superCtors[defaultIndex], clazz);
      }
      
      createPublicConstructor(pool, superCtors[publicIndex], clazz, setups);
      if (protectedIndex2 == -1)
      {
         createProtectedConstructors(pool, superCtors[protectedIndex1], null, clazz, setups);
      }
      else
      {
         createProtectedConstructors(pool, superCtors[protectedIndex1], superCtors[protectedIndex2], clazz, setups);
      }
      createCopyConstructorAndMethod(pool, clazz);
   }

   /**
    * Currently only method joinpoints need serialization and thus a default ctor
    */
   private void createDefaultConstructor(CtConstructor superCtor, CtClass clazz) throws CannotCompileException
   {
      CtConstructor ctor = CtNewConstructor.defaultConstructor(clazz);
      clazz.addConstructor(ctor);
   }
   
   /**
    * This is the constructor that will be called by the GeneratedClassAdvisor, make sure it
    * initialises all the non-per-instance aspects
    */
   private void createPublicConstructor(ClassPool pool, CtConstructor superCtor, CtClass clazz, AdviceSetups setups)throws CannotCompileException, NotFoundException
   {
      StringBuffer body = new StringBuffer();
      try
      {
         AdviceSetup[] aroundSetups = setups.getByType(AdviceType.AROUND);
         int size = 0;
         int firstIndex = superCtor.getParameterTypes().length;
         if (aroundSetups != null)
         {
            for (int i = 0; i < aroundSetups.length; i++)
            {
               if (aroundSetups[i].isNewCFlow())
               {
                  size++;
                  body.append("cflow").append(aroundSetups[i].useCFlowFrom());
                  body.append(" = $").append(++firstIndex).append(";");
               }
            }
         }
         
         CtClass[] paramTypes = null;
         if (size > 0)
         {
            // set paramTypes
            paramTypes = new CtClass[firstIndex];
            CtClass[] superCtorParamTypes = superCtor.getParameterTypes();
            System.arraycopy(superCtorParamTypes, 0, paramTypes, 0, superCtorParamTypes.length);
            CtClass astCFlowExpression = pool.get(ASTCFlowExpression.class.getName());
            for (int i = superCtorParamTypes.length; i < firstIndex; i++)
            {
               paramTypes[i] = astCFlowExpression;
            }
            
            // reset buffer
            String cflowInitialization = body.toString();
            body.setLength(0);
            body.append("{super(");
            // it is supposed to contain at least the info object as first parameter
            //if (superCtorParamTypes.length > 0)
            body.append("$1");
            for (int i = 1; i < superCtorParamTypes.length; i++)
            {
               body.append(",$" + (i + 1));
            }
            body.append(");");
            body.append(cflowInitialization);
         }
         else
         {
            body.append("{super($$);");
            paramTypes = superCtor.getParameterTypes();
         }

         body.append(IS_FOR_INSTANCE_ADVISOR).append("=($1.getAdvisor() instanceof ");
         body.append(InstanceAdvisor.class.getName()).append(");");
         
         //Initialise all the aspects not scoped per_instance or per_joinpoint
         AdviceSetup[] allSetups = setups.getAllSetups();
         for (int i = 0 ; i < allSetups.length ; i++)
         {
            if (!allSetups[i].requiresInstanceAdvisor())
            {
               body.append(allSetups[i].getAspectFieldName() + " = " + allSetups[i].getAspectInitialiserName() + "();");
            }
         }
                  
         body.append("}");

         CtConstructor ctor = CtNewConstructor.make(paramTypes, superCtor.getExceptionTypes(), body.toString(), clazz);
         ctor.setModifiers(superCtor.getModifiers());
         clazz.addConstructor(ctor);
      }
      catch (CannotCompileException e)
      {
         // AutoGenerated
         throw new CannotCompileException("Error compiling. Code \n" + body.toString(), e);
      }
   }
   
   /**
    * These are the constructors that will be called by the invokeJoinPoint() method,
    * make sure it copies across all the non-per-instance aspects
    */
   private void createProtectedConstructors(ClassPool pool, CtConstructor superCtor1,
         CtConstructor superCtor2, CtClass clazz, AdviceSetups setups)
         throws CannotCompileException, NotFoundException
   {
      
      ArrayList<AdviceSetup> aspects = new ArrayList<AdviceSetup>(); 
      ArrayList<Integer> cflows = new ArrayList<Integer>();
      StringBuffer adviceInit  = new StringBuffer(); 
      
      AdviceSetup[] allSetups = setups.getAllSetups();
      for (int i = 0 ; i < allSetups.length ; i++)
      {
         if (allSetups[i].requiresInstanceAdvisor())
         {
            adviceInit.append(allSetups[i].getAspectFieldName());
            adviceInit.append(" = ");
            adviceInit.append(allSetups[i].getAspectInitialiserName());
            adviceInit.append("();");
         }
         else
         {
            aspects.add(allSetups[i]);
         }
         
         if (allSetups[i].isNewCFlow())
         {
            cflows.add(allSetups[i].useCFlowFrom());
         }
      }
      createProtectedConstructor(pool, clazz, superCtor1, allSetups, aspects, cflows,
            adviceInit.toString());
      if (superCtor2 != null)
      {
         createProtectedConstructor(pool, clazz, superCtor2, allSetups, aspects, cflows,
            adviceInit.toString());
      }
   }
   
   private void createProtectedConstructor(ClassPool pool, CtClass clazz,
         CtConstructor superCtor, AdviceSetup[] allSetups,
         ArrayList<AdviceSetup> aspects, ArrayList<Integer> cflows,
         String aspectInitialization)
      throws NotFoundException, CannotCompileException
   {
      // Set up the parameters
      CtClass[] superParams = superCtor.getParameterTypes();
      CtClass[] params = new CtClass[superParams.length + aspects.size() + cflows.size()];
      System.arraycopy(superParams, 0, params, 0, superParams.length);

      StringBuffer init = new StringBuffer();
      for (int i = 0 ; i < aspects.size() ; i++)
      {
         AdviceSetup setup = aspects.get(i);
         params[i + superParams.length] = setup.getAspectCtClass();
         init.append("this." + setup.getAspectFieldName() + " = $" + (i + superParams.length + 1) + ";");
      }
      final int aspectsLength = superParams.length + aspects.size();
      if (cflows.size() > 0 )
      {
         CtClass astCFlowExpr = pool.get(ASTCFlowExpression.class.getName());
         for (int i = 0 ; i < cflows.size() ; i++)
         {
            params[i + aspectsLength] = astCFlowExpr;
            init.append("cflow" + cflows.get(i) + "= $" + (i + aspectsLength + 1) + ";");
            init.append("matchesCflow" + cflows.get(i) + " = getCFlow" + allSetups[cflows.get(i)].useCFlowFrom() + "();");
         }
      }
      
      StringBuffer body = new StringBuffer("{super(");
      for (int i = 0 ; i < superParams.length ; i++)
      {
         if (i > 0)
         {
            body.append(", ");
         }
         body.append("$" + (i + 1));
      
      }
      body.append(");");
      body.append(aspectInitialization);
      body.append(init.toString());
      
      body.append("}");   
      CtConstructor ctor = CtNewConstructor.make(
          params, 
          superCtor.getExceptionTypes(),
          body.toString(),
          clazz);
         ctor.setModifiers(superCtor.getModifiers());
         clazz.addConstructor(ctor);
   }


   private void createCopyConstructorAndMethod(ClassPool pool, CtClass clazz) throws NotFoundException, CannotCompileException
   {
      //Add all fields from this class and all superclasses
      StringBuffer body = new StringBuffer();
      body.append("{");
      body.append("   super($1.info);");

      CtClass superClass = clazz;
      while (superClass != null && !superClass.getName().equals("java.lang.Object"))
      {
         CtField[] fields = superClass.getDeclaredFields();
         for (int i = 0 ; i < fields.length ; i++)
         {
            if (Modifier.isPrivate(fields[i].getModifiers()) && fields[i].getDeclaringClass() != clazz)
            {
               continue;
            }
            
            if (Modifier.isFinal(fields[i].getModifiers()) || Modifier.isStatic(fields[i].getModifiers()) )
            {
               continue;
            }
            
            body.append("   this." + fields[i].getName() + " = $1." + fields[i].getName() + ";");
         }
         superClass = superClass.getSuperclass();
      }
      body.append("}");
      
      CtConstructor copyCtor = CtNewConstructor.make(new CtClass[] {clazz}, new CtClass[0], body.toString(), clazz);
      copyCtor.setModifiers(Modifier.PRIVATE);
      clazz.addConstructor(copyCtor);
      
      CtMethod superCopy = pool.get(Invocation.class.getName()).getDeclaredMethod("copy");
      String copyBody = 
         "{" +
         "   return new " + clazz.getName() + "(this);" +
         "}";
      CtMethod copy = CtNewMethod.make(
            superCopy.getReturnType(), 
            superCopy.getName(), 
            new CtClass[0], 
            new CtClass[0], 
            copyBody, 
            clazz);
      clazz.setModifiers(Modifier.PUBLIC);
      clazz.addMethod(copy);
   }

   /**
    * Normal people don't want to override the dispatch method
    */
   protected void overrideDispatchMethods(CtClass superClass, CtClass clazz, JoinPointInfo newInfo) throws CannotCompileException, NotFoundException
   {     
   }
   
   /**
    * For ConByXXXX,  If target constructor is execution advised, replace it with a call to the constructor wrapper
    */
   protected void overrideDispatchMethods(CtClass superClass, CtClass clazz, CallerConstructorInfo cinfo) throws NotFoundException, CannotCompileException
   {
      if (cinfo.getWrappingMethod() == null)
      {
         return;
      }
      
      CtMethod[] superDispatches = JavassistUtils.getDeclaredMethodsWithName(superClass, DISPATCH);
      
      if (superDispatches.length > 2)
      {
         if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("Too many dispatch() methods found in " + superClass.getName());
      }
      
      for (int i = 0 ; i < superDispatches.length ; i++)
      {
         CtMethod wrapperMethod = ReflectToJavassist.methodToJavassist(cinfo.getWrappingMethod());
         CtClass[] params = wrapperMethod.getParameterTypes(); 
         
         StringBuffer parameters = new StringBuffer("(");
         if (superDispatches[i].getParameterTypes().length == 0)
         {
            //This is the no params version called by invokeNext() for around advices
            for (int j = 0 ; j < params.length ; j++)
            {
               if (j > 0)parameters.append(", ");
               parameters.append("arg" + j);
            }
         }
         else
         {
            //This is the no parameterized version called by invokeJoinPoint() when there are no around advices
            int offset = (hasCallingObject()) ? 1 : 0; 
            for (int j = 0 ; j < params.length ; j++)
            {
               if (j > 0)parameters.append(", ");
               parameters.append("$" + (j + offset + 1));
            }
         }
         parameters.append(")");

         String body = 
            "{ return " + cinfo.getConstructor().getDeclaringClass().getName() + "." + cinfo.getWrappingMethod().getName() +  parameters + ";}";
   
         try
         {
            CtMethod dispatch = CtNewMethod.make(
                  superDispatches[i].getReturnType(), 
                  superDispatches[i].getName(), 
                  superDispatches[i].getParameterTypes(), 
                  superDispatches[i].getExceptionTypes(), 
                  body, 
                  clazz);
            dispatch.setModifiers(superDispatches[i].getModifiers());
            clazz.addMethod(dispatch);
         }
         catch (CannotCompileException e)
         {
            throw new RuntimeException("Could not compile code " + body + " for method " + getMethodString(clazz, superDispatches[i].getName(), superDispatches[i].getParameterTypes()), e);
         }
         
      }
   }
   
   protected static void addUntransformableInterface(Instrumentor instrumentor, CtClass clazz) throws NotFoundException
   {
      addUntransformableInterface(instrumentor.getClassPool(), clazz);
   }

   protected static void addUntransformableInterface(ClassPool pool, CtClass clazz) throws NotFoundException
   {
      CtClass untransformable = pool.get(Untransformable.class.getName());
      clazz.addInterface(untransformable);
   }

   protected static String getMethodString(CtClass joinpoint, String method, CtClass[] params)
   {
      StringBuffer sb = new StringBuffer();
      sb.append(joinpoint);
      sb.append(".");
      sb.append("name");
      sb.append("(");
      for (int i = 0 ; i < params.length ; i++)
      {
         if (i > 0) sb.append(", ");
         sb.append(params[i].getName());
      }
      sb.append(")");
      
      return sb.toString();
   }
   
   protected class AdviceSetup
   {
      int index;
      Class<?> aspectClass;
      CtClass aspectCtClass;
      String adviceName;
      Class<?> thrownType;
      Scope scope;
      String registeredName;
      String cflowString;
      ASTCFlowExpression cflowExpr;
      int cflowIndex;
      AdviceType type;
      
      AdviceMethodProperties adviceMethodProperties;
      
      AdviceSetup(int index, GeneratedAdvisorInterceptor ifw, JoinPointInfo info, ClassLoader classLoader) throws ClassNotFoundException, NotFoundException
      {
         this.index = index;
         scope = ifw.getScope();
         adviceName = ifw.getAdviceName();
         registeredName = ifw.getRegisteredName();
         cflowString = ifw.getCFlowString();
         cflowExpr = ifw.getCflowExpression();
         if (ifw.isAspectFactory())
         {
            Object aspectInstance = ((GeneratedAdvisorInterceptor)info.getInterceptors()[index]).getAspect(info.getAdvisor(), info.getJoinpoint(), true);
            if (aspectInstance != null)
            {
               aspectClass = aspectInstance.getClass();
            }
         }
         else
         {
            aspectClass = classLoader.loadClass(ifw.getAspectClassName());
         }
         if (aspectClass != null)
         {
            aspectCtClass = ReflectToJavassist.classToJavassist(aspectClass);
         }
         type = ifw.getType();
      }
      
      String getAdviceName()
      {
         return adviceName;
      }
      
      
      Class<?> getAspectClass()
      {
         return aspectClass;
      }
      
      CtClass getAspectCtClass()
      {
         return aspectCtClass;
      }
      
      Scope getScope()
      {
         return scope;
      }
      
      int getIndex()
      {
         return index;
      }
      
      String getRegisteredName()
      {
         return registeredName;
      }
      
      String getAspectFieldName()
      {
         StringBuffer name = new StringBuffer();
         name.append(type.getName());
         name.append(index + 1);
         return name.toString();
      }
      
      String getAspectInitialiserName()
      {
         StringBuffer name = new StringBuffer();
         name.append(type.getAccessor());
         name.append(index + 1);
         return name.toString();
      }
      
      boolean isPerInstance()
      {
         return scope == Scope.PER_INSTANCE;
      }
      
      boolean isPerJoinpoint()
      {
         return scope == Scope.PER_JOINPOINT;
      }
      
      boolean shouldInvokeAspect()
      {
         return !((isPerInstance() && isStaticCall()) || aspectClass == null);
      }
      
      boolean requiresInstanceAdvisor()
      {
         return (isPerInstance() || (isPerJoinpoint() && !isStaticCall()));
      }
      
      String getCFlowString()
      {
         return cflowString;
      }
      
      ASTCFlowExpression getCFlow()
      {
         return cflowExpr;
      }
      
      void setUseCFlowFrom(int index)
      {
         cflowIndex = index;
      }
      
      int useCFlowFrom()
      {
         return cflowIndex;
      }
      
      boolean isNewCFlow()
      {
         return (getCFlowString() != null && index == cflowIndex);
      }

      public AdviceType getType()
      {
         return this.type;
      }

      public AdviceMethodProperties getAdviceMethodProperties()
      {
         return adviceMethodProperties;
      }

      public void setAdviceMethodProperties(AdviceMethodProperties adviceMethodProperties)
      {
         this.adviceMethodProperties = adviceMethodProperties;
      }
   }
   
   private class GeneratedClassInfo
   {
      Object[] ctorParams;
      Constructor<?> publicConstructor;
      
      GeneratedClassInfo(Class<?> generated, AdviceSetups setups)
      {
         AdviceSetup[] aroundSetups = setups.getByType(AdviceType.AROUND);
         int size = 0;
         if (aroundSetups != null)
         {
            for (int i = 0; i < aroundSetups.length; i++)
            {
               if (aroundSetups[i].isNewCFlow())
               {
                  size++;
               }
            }
         }
         ctorParams = new Object[size + 1];
         if (size > 0)
         {
            int j = 1;
            for (int i = 0; i < aroundSetups.length; i++)
            {
               if (aroundSetups[i].isNewCFlow())
               {
                  ctorParams[j++] = aroundSetups[i].getCFlow();
               }
            }
         }
         for (Constructor<?> currentConstructor: generated.getDeclaredConstructors())
         {
            if (currentConstructor.getParameterTypes().length > 0 &&
                Modifier.isPublic(currentConstructor.getModifiers()))
            {
               this.publicConstructor = currentConstructor;
            }
         }
      }
      
      Object createJoinPointInstance(ClassLoader classloader, JoinPointInfo info)
      {
         try
         {
            ctorParams[0] = info;
            return publicConstructor.newInstance(ctorParams);
         }
         catch (Exception e)
         {
            StringBuffer sb = new StringBuffer();
            throw new RuntimeException( debugClass(sb,
                  publicConstructor.getDeclaringClass()).toString(), e);
         }
      }
   }
   
   private class AdviceSetups
   {
      AdviceSetup[] allSetups;
      AdviceSetup[][] setups;
      List<AdviceSetup> lightweightAdvicesRequiringInstanceAdvisor;
      boolean hasAroundAdvices;
      boolean hasArgsAroundAdvices;

      /**
       * 
       * @param info
       * @param allSetups contains only setups that represent advices to be invoked
       *                  (i.e., allSetups[i].shouldInvoke() equals true for
       *                  0 <= i <= allSetups.length)
       */
      AdviceSetups(JoinPointInfo info, AdviceSetup[] allSetups)
      {
         this.allSetups = allSetups;
         int length = AdviceType.values().length;
         ArrayList<AdviceSetup>[] aspects = (ArrayList<AdviceSetup>[]) new ArrayList<?>[length];
         
         for (int i = 0 ; i < allSetups.length ; i++)
         {
            AdviceMethodProperties properties = getAdviceMethodProperties(info, allSetups[i]);
            AdviceType type = allSetups[i].getType();
            int index = type.ordinal();
            if (aspects[index] == null)
            {
               aspects[index] = new ArrayList<AdviceSetup>();
            }
            
            
            properties = type.getFactory().findAdviceMethod(properties);
            allSetups[i].setAdviceMethodProperties(properties);
            aspects[index].add(allSetups[i]);
               
            if (AdviceType.AROUND == type)
            {
               hasAroundAdvices = true;
               if (!hasArgsAroundAdvices)
               {
                  if (!hasInvocation(properties))
                  {
                     hasArgsAroundAdvices = true;
                  }
               }

            }
            else if (allSetups[i].requiresInstanceAdvisor())
            {
               //We are not an around advice and we require an instance advisor
               if (lightweightAdvicesRequiringInstanceAdvisor == null)
               {
                  lightweightAdvicesRequiringInstanceAdvisor = new ArrayList<AdviceSetup>();
               }
               lightweightAdvicesRequiringInstanceAdvisor.add(allSetups[i]);
            }
         }
         
         this.setups = new AdviceSetup[length][];
         for (int i = 0; i < length; i++)
         {
            this.setups[i] = (aspects[i] == null)? null: (AdviceSetup[])
                  aspects[i].toArray(new AdviceSetup[aspects[i].size()]);
         }
      }

      private boolean hasInvocation(AdviceMethodProperties properties)
      {
         int[] args = properties.getArgs();
         for (int z = 0; z < args.length ; z++)
         {
            if (args[z] == AdviceMethodProperties.INVOCATION_ARG)
            {
               return true;
            }
         }
         return false;
      }
      
      /**
       * Returns the list of all advice setups, regardless of the advice type.
       */
      public AdviceSetup[] getAllSetups()
      {
         return allSetups;
      }
      
      /**
       * Returns the setups list corresponding to the advice type.
       * 
       * @param type the advice type
       * 
       * @return an array of <code>AdviceSetup</code> instances.
       */
      public AdviceSetup[] getByType(AdviceType type)
      {
         return this.setups[type.ordinal()];
      }

      public boolean hasLightweightAdvicesRequiringInstanceAdvisor()
      {
         return lightweightAdvicesRequiringInstanceAdvisor != null && lightweightAdvicesRequiringInstanceAdvisor.size() > 0;  
      }
      
      public List<AdviceSetup> getLightweightAdvicesRequiringInstanceAdvisor()
      {
         return lightweightAdvicesRequiringInstanceAdvisor;
      }

      public boolean getHasArgsAroundAdvices()
      {
         return hasArgsAroundAdvices;
      }
   }

   private interface GenerateJoinPointClassAction
   {
      Object generateJoinPointClass(ClassLoader classloader, JoinPointGenerator joinPointGenerator, JoinPointInfo info);
      
      GenerateJoinPointClassAction PRIVILEGED = new GenerateJoinPointClassAction()
      {
         public Object generateJoinPointClass(final ClassLoader classloader, final JoinPointGenerator joinPointGenerator, final JoinPointInfo info) 
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<Object>()
               {
                  public Object run() throws Exception
                  {
                     return joinPointGenerator.doGenerateJoinPointClass(classloader, info);
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               Exception actual = e.getException();
               if (actual instanceof RuntimeException)
               {
                  throw (RuntimeException)actual;
               }
               throw new RuntimeException(actual);
            }
         }
      };

      GenerateJoinPointClassAction NON_PRIVILEGED = new GenerateJoinPointClassAction()
      {
         public Object generateJoinPointClass(ClassLoader classloader, JoinPointGenerator joinPointGenerator, JoinPointInfo info)
         {
            return joinPointGenerator.doGenerateJoinPointClass(classloader, info);
         }
      };
   }


   private static abstract class AdviceCallStrategy
   {
      public boolean addInvokeCode(JoinPointGenerator generator,
            AdviceSetup[] setups, StringBuffer code, JoinPointInfo info) throws NotFoundException
      {
         StringBuffer call = new StringBuffer();
         if (setups == null || setups.length == 0)
         {
            return false;
         }
         boolean argsFound = false;
         String key = generateKey(generator);
         for (int i = 0 ; i < setups.length ; i++)
         {
            call.setLength(0);
            argsFound = appendAdviceCall(setups[i], key, code, call, generator, info)
               || argsFound;
            code.append(call);
            call.setLength(0);
         }
         return argsFound;
      }
      
      protected abstract String generateKey(JoinPointGenerator generator);
      
      /**
       * 
       * @param setup represents an advice that should be invoked (i.e.,
       *              {@code setup.shouldBeInvoked()} is {@code true})
       * @param key
       * @param beforeCall
       * @param call
       * @param generator
       * @param info
       * @return
       * @throws NotFoundException
       */
      protected abstract boolean appendAdviceCall(AdviceSetup setup, String key,
           StringBuffer beforeCall, StringBuffer call, JoinPointGenerator generator, JoinPointInfo info) throws NotFoundException;
      
      private final boolean appendAdviceCall(AdviceSetup setup,
            StringBuffer beforeCall, StringBuffer call, JoinPointGenerator generator) 
      {
         AdviceMethodProperties properties = setup.getAdviceMethodProperties();
         if (properties == null)
         {
            return false;
         }
         call.append(setup.getAspectFieldName());
         call.append(".");
         call.append(setup.getAdviceName());
         call.append("(");

         final int[] args = properties.getArgs();
         boolean argsFound = false;
         if (args.length > 0)
         {
            final Class<?>[] adviceParams = properties.getAdviceMethod().getParameterTypes();
            if (properties.isAdviceOverloaded())
            {
               appendCast(call, adviceParams[0]);
            }
            argsFound = appendParameter(beforeCall, call, args[0], adviceParams[0], properties,
                  generator);
            for (int i = 1 ; i < args.length ; i++)
            {
               call.append(", ");
               if (properties.isAdviceOverloaded())
               {
                  appendCast(call, adviceParams[i]);
               }
               argsFound = appendParameter(beforeCall, call, args[i], adviceParams[i],
                     properties, generator) || argsFound;
            }
         }
         
         call.append(");\n");
         return argsFound;
      }
      
      protected boolean appendParameter(StringBuffer beforeCall, StringBuffer call,
            final int arg, final Class<?> adviceParam, AdviceMethodProperties properties,
            JoinPointGenerator generator)
      {
         switch(arg)
         {
         case AdviceMethodProperties.INVOCATION_ARG:
            call.append("this");
            break;
         case AdviceMethodProperties.JOINPOINT_ARG:
            call.append(INFO_FIELD);
            break;
         case AdviceMethodProperties.RETURN_ARG:
            call.append(RETURN_VALUE);
            break;
         case AdviceMethodProperties.THROWABLE_ARG:
            call.append('(').append(adviceParam.getName()).append(')');
            call.append(THROWABLE);
            break;
         case AdviceMethodProperties.TARGET_ARG:
            if (!generator.parameters.hasTarget())
            {
               call.append("null");
            }
            else
            {
               call.append('$');
               call.append(generator.parameters.getTargetIndex());
            }
            break;
         case AdviceMethodProperties.CALLER_ARG:
            if (!generator.parameters.hasCaller())
            {
               call.append("null");
            }
            else
            {
               call.append('$');
               call.append(generator.parameters.getCallerIndex());
            }
            break;
         case AdviceMethodProperties.ARGS_ARG:
            call.append(ARGUMENTS);
            // all typed args may become inconsistent with arguments array after
            // advice call
            generator.inconsistentTypeArgs.get().addAll(generator.joinPointArguments);
            // return true when args has been found; false otherwise
            return true;
         default:
            
            // make typed argument consistent, if that is the case
            Set<Integer> inconsistentTypeArgs = generator.inconsistentTypeArgs.get();
            if (inconsistentTypeArgs.contains(arg))
            {
               int argIndex = arg + generator.parameters.getFirstArgIndex();
               beforeCall.append("$").append(argIndex).append(" = ");
               beforeCall.append(ReflectToJavassist.castInvocationValueToTypeString(
               properties.getJoinpointParameterClassTypes()[arg], ARGUMENTS + '[' + arg + ']'));
               beforeCall.append(';');
               inconsistentTypeArgs.remove(arg);
            }
            //The parameter array is 1-based, and the invokeJoinPoint method may also take the target and caller objects
            call.append("$");
            call.append(arg + generator.parameters.getFirstArgIndex());
         }
         return false;
      }
      
      protected void appendCast(StringBuffer call, Class<?> adviceParam)
      {
         call.append("(");
         call.append(ClassExpression.simpleType(adviceParam));
         call.append(")");
      }
   }
   
   private static class AroundAdviceCallStrategy extends AdviceCallStrategy
   {
      private int addedAdvice = 0;
      private boolean consistencyEnforced = false;
      
      public String generateKey(JoinPointGenerator generator)
      {
         addedAdvice = 0;
         if (generator.isVoid())
         {
            return "";
         }
         return "return ($w)";
      }

      public boolean appendAdviceCall(AdviceSetup setup, String key,
            StringBuffer beforeCall, StringBuffer call, JoinPointGenerator generator, JoinPointInfo info)
      {
         boolean result = false;
         
         AdviceMethodProperties properties = setup.getAdviceMethodProperties();
         if (properties == null || properties.getAdviceMethod() == null)
         {
            // throw new RuntimeException("DEBUG ONLY Properties was null " + 
            // aroundSetups[i].getAspectClass().getName() + "." + aroundSetups[i].getAdviceName());
            return false;
         }
         
         beforeCall.append("      case ");
         beforeCall.append(++addedAdvice);
         beforeCall.append(":");
         
         if (setup.getCFlowString() != null)
         {
            beforeCall.append("         if (matchesCflow" + setup.useCFlowFrom() + ")");
            beforeCall.append("         {");
            result = appendAroundCallString(beforeCall, call, key, setup, properties, generator);
            call.append("         }");
            call.append("         else");
            call.append("         {");
            call.append("            ");
            call.append(key);
            call.append(" invokeNext();");
            call.append("         }");
         }
         else
         {
            result = appendAroundCallString(beforeCall, call, key, setup, properties, generator);
         }
         
         call.append("      break;");
         return result;
      }
      
      
      public boolean appendAroundCallString(StringBuffer beforeCall,
            StringBuffer call, String returnStr, AdviceSetup setup,
            AdviceMethodProperties properties,
            JoinPointGenerator generator)
      {
         // method that avoids more than one repeated call to ENFORCE_ARGS_CONSISTENCY
         this.consistencyEnforced = false;
         
         call.append("   ");
         call.append(returnStr);
         call.append(" ");
         boolean result = super.appendAdviceCall(setup, beforeCall, call, generator);
         
         return result;
      }
      
      protected boolean appendParameter(StringBuffer beforeCall, StringBuffer call,
            final int arg, final Class<?> adviceParam,
            AdviceMethodProperties properties, JoinPointGenerator generator)
      {
         switch(arg)
         {
         case AdviceMethodProperties.TARGET_ARG:
            if (generator.parameters.hasTarget())
            {
               call.append(TYPED_TARGET_FIELD);
               return false;
            }
            break;
         case AdviceMethodProperties.CALLER_ARG:
            if (generator.parameters.hasCaller())
            {
               call.append(TYPED_CALLER_FIELD);
               return false;
            }
            break;
         case AdviceMethodProperties.ARGS_ARG:
            call.append(GET_ARGUMENTS);
            // return true when args has been found; false otherwise
            return true;
         }
         if (arg >= 0)
         {
            if (!consistencyEnforced)
            {
               beforeCall.append(OptimizedBehaviourInvocations.ENFORCE_ARGS_CONSISTENCY);
               beforeCall.append("();");
               consistencyEnforced = true;
            }
            call.append(generator.getJoinPointArg(arg));
            return false;
         }
         return super.appendParameter(beforeCall, call, arg, adviceParam, properties, generator);
      } 
   }
   
   private static class DefaultAdviceCallStrategy extends AdviceCallStrategy
   {
      private static DefaultAdviceCallStrategy INSTANCE = new DefaultAdviceCallStrategy();
      
      public static final DefaultAdviceCallStrategy getInstance()
      {
         return INSTANCE;
      }
      
      private DefaultAdviceCallStrategy() {}

      public String generateKey(JoinPointGenerator generator)
      {
         return null;
      }
      
      public boolean appendAdviceCall(AdviceSetup setup, String key,
            StringBuffer beforeCall, StringBuffer call, JoinPointGenerator generator, JoinPointInfo info)
      {
         return super.appendAdviceCall(setup, beforeCall, call, generator);
      }
   }
   
   private static class AfterAdviceCallStrategy extends AdviceCallStrategy
   {
      private static AfterAdviceCallStrategy INSTANCE = new AfterAdviceCallStrategy();
      
      public static final AfterAdviceCallStrategy getInstance()
      {
         return INSTANCE;
      }
      
      private AfterAdviceCallStrategy() {}

      public String generateKey(JoinPointGenerator generator)
      {
         if (generator.isVoid())
         {
            return "";
         }
         return "          " + RETURN_VALUE + " = (" +
         generator.getReturnClassType().getName() + ")";
      }

      public boolean appendAdviceCall(AdviceSetup setup, String key,
            StringBuffer beforeCall, StringBuffer call, JoinPointGenerator generator, JoinPointInfo info) throws NotFoundException
      {
         AdviceMethodProperties properties = setup.getAdviceMethodProperties();
         if (properties != null && !properties.isAdviceVoid())
         {
            call.append(key);
         }
         return super.appendAdviceCall(setup, beforeCall, call, generator);
      }
   }
   
   private static class AfterThrowingAdviceCallStrategy extends AdviceCallStrategy
   {
      private static AfterThrowingAdviceCallStrategy INSTANCE = new AfterThrowingAdviceCallStrategy();
      
      public static final AfterThrowingAdviceCallStrategy getInstance()
      {
         return INSTANCE;
      }
      
      public boolean appendAdviceCall(AdviceSetup setup, String key,
            StringBuffer beforeCall, StringBuffer call, JoinPointGenerator generator, JoinPointInfo info)
      {
         int[] args = setup.getAdviceMethodProperties().getArgs();
         int throwableIndex = -1;
         for (int i = 0; i < args.length; i++)
         {
            if (args[i] == AdviceMethodProperties.THROWABLE_ARG)
            {
               throwableIndex = i;
            }
         }
         if (throwableIndex != -1)
         {
            Class<?> throwableType = setup.getAdviceMethodProperties().
               getAdviceMethod().getParameterTypes()[throwableIndex];
            if (throwableType != Throwable.class)
            {
               call.append("if (").append(THROWABLE).append(" instanceof ");
               call.append(throwableType.getName()).append(") {");
               boolean result = super.appendAdviceCall(setup, beforeCall, call, generator);
               call.append("}");
               return result;
            }
         }
         return super.appendAdviceCall(setup, beforeCall, call, generator);
      }

      @Override
      protected String generateKey(JoinPointGenerator generator)
      {
         return null;
      }
   }

   // TODO replace by enum
   protected static class JoinPointParameters {
      public static final JoinPointParameters ONLY_ARGS = new JoinPointParameters(false, -1, false, -1, 0, null);
      public static final JoinPointParameters TARGET_ARGS = new JoinPointParameters(true, 1, false, -1, 1, "$1");
      public static final JoinPointParameters CALLER_ARGS = new JoinPointParameters(false, -1, true, 1, 1, "$1");
      public static final JoinPointParameters TARGET_CALLER_ARGS = new JoinPointParameters(true, 1, true, 2, 2, "$1, $2");
      
      private boolean target;
      private boolean caller;
      
      private int targetIndex;
      private int callerIndex;
      private int firstArgIndex;
      private String targetCallerList;
      
      private JoinPointParameters(boolean target, int targetIndex, boolean caller, int callerIndex, int firstArgIndex, String targetCallerList)
      {
         this.target = target;
         this.targetIndex = targetIndex;
         this.caller = caller;
         this.callerIndex = callerIndex;
         this.firstArgIndex = firstArgIndex + 1;
         this.targetCallerList = targetCallerList;
      }
      
      public final boolean hasTarget()
      {
         return target;
      }
      
      public final int getTargetIndex()
      {
         return targetIndex;
      }
      
      public final boolean hasCaller()
      {
         return caller;
      }
      
      public final int getCallerIndex()
      {
         return callerIndex;
      }
      
      /**
       * Returns the index of the joinpoint context (the context where the
       * intercepted joinpoint is executed). For caller joinpoints, the
       * context is the caller; for other joinpoints, the context of the joinpoint is
       * the target.
       * 
       * This method must be called only for per instance situations, i.e, situations
       * where there is a context available.
       * 
       * @return the index of the joinpoint context
       */
      public final int getContextIndex()
      {
         return caller? callerIndex: targetIndex;
      }
      
      public final int getFirstArgIndex()
      {
         return firstArgIndex;
      }
      
      /**
       * Returns an statement declaring the arguments array.
       * 
       * @param totalParameters the total number of joinpoint parameters (including
       *                        caller and target when available)
       * @param nullArgsArray   <code>true</code> to indicate that the array should
       *                        be <code>null</code> where there is no argument;
       *                        <code>false</code> to indicate it should be an empty
       *                        array
       * @return an statement declaring (and initializing) the arguments array
       */
      public final String declareArgsArray(int totalParameters, boolean nullArgsArray)
      {
         StringBuffer buffer = new StringBuffer("Object[] ");
         buffer.append(ARGUMENTS);
         if (++totalParameters == firstArgIndex)
         {
            if (nullArgsArray)
            {
               buffer.append(" = null;");
            }
            else
            {
               buffer.append(" = new Object[0];");
            }
         }
         else
         {
            buffer.append(" = new Object[]{($w)$");
            buffer.append(firstArgIndex);
            for (int i = firstArgIndex + 1; i < totalParameters; i++)
            {
               buffer.append(", ($w)$");
               buffer.append(i);
            }
            buffer.append("};");
         }
         return buffer.toString();
      }
      
      public final void appendParameterList(StringBuffer code, CtClass[] parameterTypes)
      {

         int j = firstArgIndex - 1;
         int totalParameters = parameterTypes.length - j;
         if (targetCallerList != null)
         {
            code.append(targetCallerList);
         }
         if (totalParameters == 0)
         {
            return;
         }
         if (targetCallerList != null)
         {
            code.append(", ");
         }

         castArgument(code, parameterTypes[j++], 0);

         for (int i = 1; i < totalParameters; i++, j++)
         {
            code.append(", ");
            castArgument(code, parameterTypes[j], i);
         }
      }
      
      public final void appendParameterListWithoutArgs(StringBuffer code)
      {
         if (targetCallerList != null)
         {
            code.append(',');
            code.append(targetCallerList);
         }
      }
      
      
      private static final String[][] primitiveExtractions;

      static{
         primitiveExtractions = new String[][]{
               {"((Boolean) " + ARGUMENTS + " [", "]).booleanValue()"},
               {"((Integer) " + ARGUMENTS + "[", "]).intValue()"},
               {"((Double) " + ARGUMENTS + "[", "]).doubleValue()"},
               {"((Float) " + ARGUMENTS + "[", "]).floatValue()"},
               {"((Character) " + ARGUMENTS + "[", "]).charValue()"},
               {"((Byte) " + ARGUMENTS + " [", "]).byteValue()"},
               {"((Long) " + ARGUMENTS + "[", "]).longValue()"},
               {"((Short) " + ARGUMENTS + "[", "]).shortValue()"}
         };
      }
      
      public final void castArgument(StringBuffer code, CtClass expectedType, int i)
      {

         if (expectedType.isPrimitive())
         {
            String[] extraction = null;
            if (expectedType == CtClass.booleanType)
            {
               extraction = primitiveExtractions[0];
            }
            else if (expectedType == CtClass.intType)
            {
               extraction = primitiveExtractions[1];
            }
            else if (expectedType == CtClass.doubleType)
            {
               extraction = primitiveExtractions[2];
            }
            else if (expectedType == CtClass.floatType)
            {
               extraction = primitiveExtractions[3];
            }
            else if (expectedType == CtClass.charType)
            {
               extraction = primitiveExtractions[4];
            }
            else if (expectedType == CtClass.byteType)
            {
               extraction = primitiveExtractions[5];
            }
            else if (expectedType == CtClass.longType)
            {
               extraction = primitiveExtractions[6];
            }
            else if (expectedType == CtClass.shortType)
            {
               extraction = primitiveExtractions[7];
            }
            code.append(extraction[0]);
            code.append(i);
            code.append(extraction[1]);
         }
         else
         {
            code.append("(");
            code.append(expectedType.getName());
            code.append(") ");
            code.append(ARGUMENTS);
            code.append("[");
            code.append(i);
            code.append("]");
         }
      }
   }  
   
   protected Field getJoinpointField()
   {
      return joinpointField;
   }  
}
