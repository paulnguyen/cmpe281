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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.ClassInstanceAdvisor;
import org.jboss.aop.GeneratedClassAdvisor;
import org.jboss.aop.GeneratedInstanceAdvisorMixin;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.joinpoint.ConstructionInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByConstructorJoinpoint;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodInvocation;
import org.jboss.aop.joinpoint.ConstructorCalledByMethodJoinpoint;
import org.jboss.aop.joinpoint.ConstructorInvocation;
import org.jboss.aop.joinpoint.ConstructorJoinpoint;
import org.jboss.aop.joinpoint.FieldInvocation;
import org.jboss.aop.joinpoint.FieldJoinpoint;
import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.joinpoint.MethodCalledByConstructorInvocation;
import org.jboss.aop.joinpoint.MethodCalledByConstructorJoinpoint;
import org.jboss.aop.joinpoint.MethodCalledByMethodInvocation;
import org.jboss.aop.joinpoint.MethodCalledByMethodJoinpoint;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aop.joinpoint.MethodJoinpoint;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Special interceptor wrapping the interceptor factory, so that generated advisors have 
 * all the information they need about the contained advices for generating the invocation
 * methods.
 * If we are invoked upon dymamically we use vanilla invocations, i.e. the generated invocation code 
 * does not step in, so we generate the interceptor class as and when needed in our invoke() method.
 *
 * Old skool class advisors do not use this class
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 78996 $
 */
public class GeneratedAdvisorInterceptor implements Interceptor
{
   private static final AOPLogger logger = AOPLogger.getLogger(GeneratedAdvisorInterceptor.class);
   // important to indicate that the factory has already been called, but a null
   // interceptor was returned
   private static final Interceptor EMPTY_INTERCEPTOR = new Interceptor()
   {
      public Object invoke(Invocation invocation) throws Throwable
      {
         return invocation.invokeNext();
      }

      public String getName()
      {
         return "NULL";
      }
   };
   private InterceptorFactory factory;
   private volatile Object instance; 
   private String cflowString;
   private ASTCFlowExpression cflowExpression;
   
   /**
    * The interceptor that is used if and only if we invoke dynamically
    */
   volatile Interceptor lazyInterceptor;
   
   public GeneratedAdvisorInterceptor(
         InterceptorFactory factory, 
         GeneratedClassAdvisor advisor, 
         Joinpoint joinpoint, 
         String cflowString, 
         ASTCFlowExpression cflowExpr)
   {
      this(factory, advisor, joinpoint);
      this.cflowString = cflowString;
      this.cflowExpression = cflowExpr;
   }
   
   public GeneratedAdvisorInterceptor(InterceptorFactory factory, GeneratedClassAdvisor advisor, Joinpoint joinpoint)
   {
      this.factory = factory;
      
      if (!(factory instanceof GenericInterceptorFactory))
      {
         if (getScope() == Scope.PER_INSTANCE)
         {
            if (!advisor.getPerInstanceAspectDefinitions().contains(factory.getAspect()))
            {
               advisor.addPerInstanceAspect(factory.getAspect());
            }
         }
         else if (getScope() == Scope.PER_JOINPOINT)
         {
            advisor.addPerInstanceJoinpointAspect(joinpoint, factory.getAspect());
         }
         else if (getScope() == Scope.PER_CLASS_JOINPOINT)
         {
            if (advisor.getPerClassJoinpointAspect(factory.getAspect(), joinpoint) == null)
            {
               advisor.addPerClassJoinpointAspect(factory.getAspect(), joinpoint);
            }
         }
      }
   }

   public Interceptor create(Advisor advisor, Joinpoint joinpoint)
   {
      return factory.create(advisor, joinpoint);
   }

   /**
    * Used to obtain aspects from the generated code at runtime for joinpoints/aspects requiring an instance advisor
    */
   public Object getAspect(Advisor advisor, Joinpoint joinpoint)
   {
      return getAspect(advisor, joinpoint, false);
   }
   
   /**
    * Also used as a convenience method to create aspect instances for the JoinPointGenerator in order to figure 
    * out what the class of the aspect should be when making the call from the generated joinpoint class.
    * PER_INSTANCE or PER_JOINPOINT (for non-static fields) aspects cannot be created "properly" 
    * until at runtime, since that requires access to the instance advisor. If forCodeGeneration
    * is true we create a temporary InstanceAdvisor to avoid NPEs in case we are calling an
    * AspectFactory needing access to the instance advisor
    */
   public Object getAspect(Advisor advisor, Joinpoint joinpoint, boolean forCodeGeneration)
   {
      if (factory instanceof GenericInterceptorFactory)
      {
         if (instance == null)
         {
            instance = ((GenericInterceptorFactory)factory).create(advisor, joinpoint);
         }
         return instance;
      }
      else if (factory instanceof GeneratedInstanceAdvisorMixin.InstanceInterceptorFactory)
      {
         return ((GeneratedInstanceAdvisorMixin.InstanceInterceptorFactory)factory).create(advisor, joinpoint);
      }
      else if (factory instanceof ScopedInterceptorFactory || factory instanceof AdviceFactory)
      {
         ClassInstanceAdvisor temp = (forCodeGeneration) ? new ClassInstanceAdvisor(advisor) : null;
         return getAspectInstance(factory.getAspect(), advisor, joinpoint, temp);
      }
      
      return null;
   }
   
   /**
    * Used to obtain aspects from the generated code at runtime for joinpoints/aspects requiring an instance advisor
    */
   public Object getPerInstanceAspect(Advisor advisor, Joinpoint joinpoint, InstanceAdvisor ia)
   {
      if (factory instanceof GenericInterceptorFactory)
      {
         if (instance == null)
         {
            instance = ((GenericInterceptorFactory)factory).create(advisor, joinpoint);
         }
         return instance;
      }
      else if (factory instanceof ScopedInterceptorFactory || factory instanceof AdviceFactory)
      {
         return getAspectInstance(factory.getAspect(), advisor, joinpoint, ia);
      }
      
      return null;
   }
   
   private Object getAspectInstance(AspectDefinition def, Advisor advisor, Joinpoint joinpoint, InstanceAdvisor ia)
   {
      final Scope scope = def.getScope();
      if (scope == Scope.PER_VM)
      {
         if (instance == null)
         {
            instance = advisor.getPerVMAspect(def);
         }
         if (instance == null || instance == EMPTY_INTERCEPTOR)
         {
            instance = EMPTY_INTERCEPTOR;
            return null;
         }
         return instance;
      }
      else if (scope == Scope.PER_CLASS)
      {
         if (instance == null)
         {
            instance = advisor.getPerClassAspect(def);
            if (instance != null)
            {
               return instance;
            }
            advisor.addPerClassAspect(def);
            instance = advisor.getPerClassAspect(def);
         }
         if (instance == null || instance == EMPTY_INTERCEPTOR)
         {
            // indicates that the instance has already been retrieved
            instance = EMPTY_INTERCEPTOR;
            return null;
         }
         return instance;
      }
      else if (scope == Scope.PER_INSTANCE)
      {
         return getPerInstanceAspect(def, advisor, joinpoint, ia);
      }
      else if (scope == Scope.PER_JOINPOINT)
      {
         return getPerJoinPointAspect(def, advisor, joinpoint, ia);
      }
      else if (scope == Scope.PER_CLASS_JOINPOINT)
      {
         if (instance == null)
         {
            instance = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(def, joinpoint);
            if (instance != null)
            {
               return instance;
            }
            
            ((GeneratedClassAdvisor)advisor).addPerClassJoinpointAspect(def, joinpoint);
            instance = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(def, joinpoint);
            if (instance == null)
            {
               instance = EMPTY_INTERCEPTOR;
            }
         }
         if (instance == EMPTY_INTERCEPTOR)
         {
            return null;
         }
         return instance;
      }
      else
      {
         //if (aspect.getScope() == null) System.err.println("scope is null: " + aspect.getName() + "." + advice);
      }
      return null;
   }

   private Object getPerJoinPointAspect(AspectDefinition def, Advisor advisor, Joinpoint joinpoint, InstanceAdvisor ia)
   {
      if (ia == null)
      {
         if (instance == null)
         {
            //Used by JoinPointGenerator at code generation time
            if (AspectManager.verbose && logger.isDebugEnabled())
            {
               logger.debug("Calling create on PER_JOINPOINT scoped AspectFactory with no InstanceAdvisor as part of setup");
            }
            
            if (joinpoint instanceof FieldJoinpoint)
            {
               Field field = ((FieldJoinpoint)joinpoint).getField();
               if (Modifier.isStatic(field.getModifiers()))
               {
                  instance = ((ClassAdvisor)advisor).getFieldAspect((FieldJoinpoint)joinpoint, def);
               }
            }

            if (instance == null)
            {
               instance = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(def, joinpoint);
               if (instance != null)
               {
                  return instance;
               }
               
               ((GeneratedClassAdvisor)advisor).addPerClassJoinpointAspect(def, joinpoint);
               instance = ((GeneratedClassAdvisor)advisor).getPerClassJoinpointAspect(def, joinpoint);
            }
         }
         if (instance == null || instance == EMPTY_INTERCEPTOR)
         {
            instance = EMPTY_INTERCEPTOR;
            return null;
         }
         return instance;
      }
      else
      {
         //Used by code generated by JoinPointGenerator at runtime
         return ia.getPerInstanceJoinpointAspect(joinpoint, def);
      }
   }
   
   private Object getPerInstanceAspect(AspectDefinition def, Advisor advisor, Joinpoint joinpoint, InstanceAdvisor ia)
   {
      if (ia == null)
      {
         //Used by JoinPointGenerator at code generation time
         if (AspectManager.verbose && logger.isDebugEnabled())
         {
            logger.debug("Calling create on PER_INSTANCE scoped AspectFactory with no InstanceAdvisor as part of setup");
         }
         return def.getFactory().createPerInstance(advisor, ia);
      }
      else
      {
         return ia.getPerInstanceAspect(def);
      }
   }
   
   public boolean isAspectFactory()
   {
      if (factory instanceof GenericInterceptorFactory || factory instanceof GeneratedInstanceAdvisorMixin.InstanceInterceptorFactory)
      {
         return false;
      }
      else 
      {
         return !(factory.getAspect().getFactory() instanceof GenericAspectFactory);
      }
   }

   public InterceptorFactory getDelegate()
   {
      return factory;
   }
   
   public AspectDefinition getAspect()
   {
      return factory.getAspect();
   }

   public String getName()
   {
      return factory.getName();
   }
   
   public String getAspectClassName()
   {
      if (factory instanceof GenericInterceptorFactory)
      {
         //Dynamically added interceptors
         return ((GenericInterceptorFactory)factory).getClassName();
      }
      else if (factory instanceof GeneratedInstanceAdvisorMixin.InstanceInterceptorFactory)
      {
         return ((GeneratedInstanceAdvisorMixin.InstanceInterceptorFactory)factory).getClassName();
      }
      else 
      {
         AspectFactory af = factory.getAspect().getFactory();
         return af.getName();
      }
   }
   
   public AdviceType getType()
   {
      return factory.getType();
   }
   
   public boolean isInterceptor()
   {
      if (factory instanceof AdviceFactory)
      {
         return false;
      }
      return true;
   }
   
   public String getAdviceName()
   {
      if (factory instanceof AdviceFactory)
      {
         return ((AdviceFactory)factory).getAdvice();
      }
      
      return "invoke";
   }
   
   public Scope getScope()
   {
      if (factory instanceof GenericInterceptorFactory || factory instanceof GeneratedInstanceAdvisorMixin.InstanceInterceptorFactory)
      {
         return null;
      }
      else
      {
         return factory.getAspect().getScope();
      }
   }
   
   public String getRegisteredName()
   {
      if (factory instanceof GenericInterceptorFactory || factory instanceof GeneratedInstanceAdvisorMixin.InstanceInterceptorFactory)
      {
         return null;
      }
      else 
      {
         return factory.getAspect().getName();
      }
   }

   public ASTCFlowExpression getCflowExpression()
   {
      return cflowExpression;
   }

   public String getCFlowString()
   {
      return cflowString;
   }

   public boolean equals(Object obj)
   {
      if (!(obj instanceof GeneratedAdvisorInterceptor)) return false;
      return this.factory.equals(((GeneratedAdvisorInterceptor)obj).getDelegate());
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      if (lazyInterceptor == null)
      {
         synchronized (this)
         {
            if (lazyInterceptor == null)
            {
               if (factory.getType().isGeneratedOnly())
               {
                  lazyInterceptor = new GeneratedOnlyInterceptor(factory.getName(), factory); 
               }
               else
               {
                  lazyInterceptor = create(invocation.getAdvisor(), getJoinpoint(invocation));
                  if (lazyInterceptor == null)
                  {
                     lazyInterceptor = EMPTY_INTERCEPTOR;
                  }
               }
            }
         }
      }
      return lazyInterceptor.invoke(invocation);
   }

   private Joinpoint getJoinpoint(Invocation invocation)
   {
      if (invocation instanceof MethodInvocation)
      {
         return new MethodJoinpoint(((MethodInvocation)invocation).getMethod());
      }
      if (invocation instanceof FieldInvocation)
      {
         return new FieldJoinpoint(((FieldInvocation)invocation).getField());
      }
      if (invocation instanceof ConstructorInvocation)
      {
         return new ConstructorJoinpoint(((ConstructorInvocation)invocation).getConstructor());
      }
      if (invocation instanceof ConstructionInvocation)
      {
         return new ConstructorJoinpoint(((ConstructionInvocation)invocation).getConstructor());
      }
      if (invocation instanceof MethodCalledByMethodInvocation)
      {
         return new MethodCalledByMethodJoinpoint(((MethodCalledByMethodInvocation)invocation).getCallingMethod(), ((MethodCalledByMethodInvocation)invocation).getCalledMethod());
      }
      if (invocation instanceof MethodCalledByConstructorInvocation)
      {
         return new MethodCalledByConstructorJoinpoint(((MethodCalledByConstructorInvocation)invocation).getCalling(), ((MethodCalledByConstructorInvocation)invocation).getCalledMethod());
      }
      if (invocation instanceof ConstructorCalledByMethodInvocation)
      {
         return new ConstructorCalledByMethodJoinpoint(((ConstructorCalledByMethodInvocation)invocation).getCallingMethod(), ((ConstructorCalledByMethodInvocation)invocation).getCalledConstructor());
      }
      if (invocation instanceof ConstructorCalledByConstructorInvocation)
      {
         return new ConstructorCalledByConstructorJoinpoint(((ConstructorCalledByConstructorInvocation)invocation).getCallingConstructor(), ((ConstructorCalledByConstructorInvocation)invocation).getCalledConstructor());
      }
      
      throw new RuntimeException("Invocation type not handled " + invocation);
   }
   
   volatile String adviceString;
   public String getAdviceString()
   {
      if (adviceString == null)
      {
         StringBuffer buf = new StringBuffer();
         switch(getType())
         {
            case AROUND:
               buf.append("R");
               break;
            case BEFORE:
               buf.append("B");
               break;
            case AFTER:
               buf.append("A");
               break;
            case THROWING:
               buf.append("T");
               break;
            case FINALLY:
               buf.append("F");
               break;
            default:
               throw new RuntimeException("No such interceptor");
         }
         
         buf.append(this.getScope());
         buf.append("~#$%");
         buf.append(getAspectClassName());
         buf.append("->");
         buf.append(getAdviceName());
         adviceString = buf.toString();
         
      }
      return adviceString;
   }
   
   private class GeneratedOnlyInterceptor implements Interceptor
   {
      String name;
      
      GeneratedOnlyInterceptor(String name, InterceptorFactory factory)
      {
         this.name = name;
         logger.warn(factory.getType().getName() +
               " interceptor:s'" + name +
               "' is ignored for dynamic invocation. Adding null GeneratedOnlyInterceptor in its place");
      }
      
      public String getName()
      {
         return name;
      }

      public Object invoke(Invocation invocation) throws Throwable
      {
         return invocation.invokeNext();
      }
   }
}
