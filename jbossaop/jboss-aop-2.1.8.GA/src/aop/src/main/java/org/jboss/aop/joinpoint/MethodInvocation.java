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
package org.jboss.aop.joinpoint;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.MarshalledObject;
import java.util.ArrayList;

import org.jboss.aop.Advisor;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.metadata.SimpleMetaData;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70849 $
 */
public class MethodInvocation extends InvocationBase implements java.io.Externalizable
{
   static final long serialVersionUID = -1313717554016611763L;
   /**
    * arguments of the method call expressed as an array of objects
    */
   protected java.lang.Object[] arguments;
   protected long methodHash; // needs to be member variable as MethodInfo is not serializable across the wire.
   protected MarshalledObject marshalledArguments;
   protected Method advisedMethod;
   protected Method unadvisedMethod;

   public String toString()
   {
      StringBuffer sb = new StringBuffer(100);
      sb.append("[");
      sb.append("advisedMethod=").append(advisedMethod);
      sb.append(", unadvisedMethod=").append(unadvisedMethod);
      sb.append(", metadata=").append(metadata);
      sb.append(", targetObject=").append(targetObject);
      sb.append(", arguments=").append(arguments);
      sb.append("]");
      return sb.toString();
   }


   public MethodInvocation(MethodInfo info, org.jboss.aop.advice.Interceptor[] interceptors)
   {
      this(interceptors, info.getHash(), info.getMethod(), info.getUnadvisedMethod(), info.getAdvisor());
   }

   public MethodInvocation(Interceptor[] interceptors, long methodHash, Method advisedMethod, Method unadvisedMethod, Advisor advisor)
   {
      super(interceptors);
      this.advisor = advisor;
      this.methodHash = methodHash;
      this.advisedMethod = advisedMethod;
      this.unadvisedMethod = unadvisedMethod;
   }

   protected MethodInvocation(org.jboss.aop.advice.Interceptor[] interceptors)
   {
      super(interceptors);
   }

   public MethodInvocation()
   {
   }

   /**
    * Invoke on the next interceptor in the chain.  If this is already
    * the end of the chain, reflection will call the constructor, field, or
    * method you are invoking on.
    */
   public Object invokeNext() throws Throwable
   {
      if (interceptors != null && currentInterceptor < interceptors.length)
      {
         try
         {
            return interceptors[currentInterceptor++].invoke(this);
         }
         finally
         {
            // so that interceptors like clustering can reinvoke down the chain
            currentInterceptor--;
         }
      }

      return invokeTarget();
   }
   
   /**
    * Invokes the target joinpoint for this invocation skipping any subsequent
    * interceptors in the chain.
    */
   public Object invokeTarget() throws Throwable
   {
      try
      {
         return getActualMethod().invoke(getTargetObject(), arguments);
      }
      catch (Throwable t)
      {
         throw handleErrors(getTargetObject(), getMethod(), arguments, t);
      }
   }


   /**
    * Handle invocation errors
    *
    * @param target the target
    * @param method the method
    * @param arguments the arguments
    * @param t the error
    * @return never
    * @throws Throwable the throwable
    */
   public static Throwable handleErrors(Object target, Method method, Object[] arguments, Throwable t) throws Throwable
   {
      if (t instanceof IllegalArgumentException)
      {
         if (target == null)

            throw new IllegalArgumentException("Null target for method " + method);
         Class<?> methodClass = method.getClass();
         Class<?> targetClass = target.getClass();
         if (methodClass.isAssignableFrom(targetClass) == false)
            throw new IllegalArgumentException("Wrong target. " + targetClass + " for " + method);
         ArrayList<String> expected = new ArrayList<String>();
         Class<?>[] parameterTypes = method.getParameterTypes();
         for (int i = 0; i < parameterTypes.length; ++i)
            expected.add(parameterTypes[i].getName());
         ArrayList<String> actual = new ArrayList<String>();
         if (arguments != null)
         {
            for (int i = 0; i < arguments.length; ++i)
            {
               if (arguments[i] == null)
                  actual.add(null);
               else
                  actual.add(arguments[i].getClass().getName());
            }
         }
         throw new IllegalArgumentException("Wrong arguments. " + method.getName() + " expected=" + expected + " actual=" + actual);
      }
      else if (t instanceof InvocationTargetException)
      {
         throw ((InvocationTargetException) t).getTargetException();
      }
      throw t;
   }

   /**
    * This method resolves an annotation based on the context of the invocation.
    */
   public Object resolveAnnotation(Class<? extends Annotation> annotation)
   {
      return resolveTypedAnnotation(annotation);
   }

   public <T extends Annotation> T resolveTypedAnnotation(Class<T> annotation)
   {
      T val = super.resolveTypedAnnotation(annotation);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().resolveTypedAnnotation(getMethodHash(), getMethod(), annotation);
         if (val != null) return val;
      }

      return null;
   }

   /**
    * This method resolves an annotation based on the context of the invocation.
    */
   public Object resolveAnnotation(Class<? extends Annotation>[] annotations)
   {
      Object val = super.resolveAnnotation(annotations);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().resolveAnnotation(getMethod(), annotations);
         if (val != null) return val;
      }
      
      return null;
   }

   public <T extends Annotation> T resolveTypedAnnotation(Class<T>[] annotations)
   {
      T val = super.resolveTypedAnnotation(annotations);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().resolveTypedAnnotation(getMethod(), annotations);
         if (val != null) return val;
      }

      return null;
   }
   
   /**
    * This method resolves metadata based on the context of the invocation.
    * It iterates through its list of MetaDataResolvers to find out the
    * value of the metadata desired.
    * <p/>
    * This list usually is ThreadMetaData, InstanceAdvisor.getMetaData
    * ClassAdvisor.getMethodMetaData (or field, or constructor)
    * ClassAdvisor.getDefaultMetaData
    */
   public Object getMetaData(Object group, Object attr)
   {
      Object val = super.getMetaData(group, attr);
      if (val != null) return val;

      if (getAdvisor() != null)
      {
         val = getAdvisor().getMethodMetaData().resolve(this, group, attr);
         if (val != null) return val;
      }

      if (getAdvisor() != null)
      {
         val = getAdvisor().getDefaultMetaData().resolve(this, group, attr);
         if (val != null) return val;
      }

      return null;
   }

   /**
    * Returns a wrapper invocation object that can insert a new chain of interceptors
    * at runtime to the invocation flow.  CFlow makes use of this.
    * When the wrapper object finishes its invocation chain it delegates back to
    * the wrapped invocation.
    *
    * @param newchain chain of interceptors to be inserted on invocation
    * @return an invocation wrapper
    */
   public Invocation getWrapper(Interceptor[] newchain)
   {
      MethodInvocationWrapper wrapper = new MethodInvocationWrapper(this, newchain);
      return wrapper;
   }

   /**
    * Copies complete state of Invocation object.
    *
    * @return a copy of this instance
    */
   public Invocation copy()
   {
      MethodInvocation wrapper = new MethodInvocation(interceptors, methodHash, advisedMethod, unadvisedMethod, advisor);
      wrapper.metadata = this.metadata;
      wrapper.currentInterceptor = this.currentInterceptor;
      wrapper.instanceResolver = this.instanceResolver;
      wrapper.setTargetObject(this.getTargetObject());
      wrapper.setArguments(this.getArguments());
      return wrapper;
   }

   /**
    * Returns a non-null array containing all method arguments.
    * <p>
    * The returned array can be changed by the advice or interceptor accordingly. All
    * changes are reflected on joinpoint execution, and are noticed as well by
    * other advices and interceptors that are executed after the current one.
    * <br>
    * However, changes to this array are limited to the scope of current advice
    * execution, and must be performed before execution of {@link #invokeNext()},
    * {@link #invokeNext(Interceptor[])}, or {@link #invokeTarget()} method.
    * Otherwise, inconsistency on joinpoint argument values may be noticed.
    *
    * @return the method arguments
    */
   public Object[] getArguments()
   {
      if (arguments == null && marshalledArguments != null)
      {
         try
         {
            arguments = (Object[]) marshalledArguments.get();
            marshalledArguments = null;
         }
         catch (IOException e)
         {
            throw new RuntimeException(e);
         }
         catch (ClassNotFoundException e)
         {
            throw new RuntimeException(e);
         }
      }
      return arguments;
   }

   /**
    * Replaces method argument values by the ones contained in <code>
    * arguments</code>.
    * <p>
    * Advices and interceptors must be aware that, for performance reasons,
    * this array does not get copied across; its reference is directly used instead.
    * Hence, changes to <code>arguments</code> array after this method being called
    * are forbidden. Otherwise, inconsistency on joinpoint argument values may be
    * noticed. 
    *  
    * @param arguments a non-null array containing the new values of method arguments.
    *                  The size of this array must be the same as the one of 
    *                  {@link #getArguments()}, as well as the element types.
    */
   public void setArguments(Object[] arguments)
   {
      this.arguments = arguments;
   }

   public Method getMethod()
   {
      return advisedMethod;
   }

   public Method getActualMethod()
   {
      return unadvisedMethod;
   }

   public long getMethodHash()
   {
      return methodHash;
   }

   public Advisor getAdvisor()
   {
      return advisor;
   }


   public void writeExternal(ObjectOutput out) throws IOException
   {
      out.writeLong(methodHash);
      if (getArguments() == null)
      {
         out.writeObject(null);
      }
      else
      {
         MarshalledObject mo = new MarshalledObject(getArguments());
         out.writeObject(mo);
      }
      out.writeObject(metadata);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
   {
      methodHash = in.readLong();
      marshalledArguments = (MarshalledObject) in.readObject();
      metadata = (SimpleMetaData) in.readObject();
   }

}
