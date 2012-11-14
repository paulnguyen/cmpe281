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
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.ArrayList;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;

import org.jboss.aop.AspectManager;
import org.jboss.aop.instrument.TransformerCommon;
import org.jboss.aop.joinpoint.Joinpoint;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 81450 $
 */
public class PerVmAdvice
{
   public static synchronized Interceptor generateOptimized(Joinpoint joinpoint, AspectManager manager, String adviceName, AspectDefinition a) throws Exception
   {
      Object aspect = manager.getPerVMAspect(a);
      if (aspect == null)
      {
         return null;
      }
      return generateInterceptor(joinpoint, aspect, adviceName);

   }

   public static Interceptor generateInterceptor(Joinpoint joinpoint, Object aspect, String adviceName) throws Exception
   {
      if (aspect == null)
      {
         return null;
      }
      ClassLoader cl = SecurityActions.getClassLoader(aspect.getClass());
      String name = "org.jboss.aop.advice." + aspect.getClass().getName() + "_z_" + adviceName + "_" + System.identityHashCode(cl);
      Class<?> iclass = null;

      if (cl == null)
      {
         //The classloader will be null if loader by the booststrap classloader
         cl = SecurityActions.getContextClassLoader();
      }
      synchronized (PerVmAdvice.class)
      {
         try
         {
            iclass = cl.loadClass(name);
         }
         catch(Exception e)
         {
         }

         ClassPool pool = AspectManager.instance().findClassPool(cl);
         
         if (iclass == null)
         {
            //Aspects deployed in the lib folder, such as the AOP/MC integration aspects like the JMXIntroduction
            //will be deployed in the root classloader, but created in a pool for a child ucl due to the nature of AspectManager.findClassPool()
            try
            {
               ClassLoader pcl = pool.getClassLoader();
               iclass = pcl.loadClass(name);
            }
            catch(Exception e)
            {
            }
         }

         if (iclass == null)
         {
            Method[] methods = aspect.getClass().getMethods();
            ArrayList<Method> matches = new ArrayList<Method>();
            for (int i = 0; i < methods.length; i++)
            {
               if (methods[i].getName().equals(adviceName)) matches.add(methods[i]);
            }
            if(matches.size() == 0)
               throw new NoMatchingAdviceException(aspect.getClass(), adviceName);

            for(int i=0; i < matches.size(); i++)
            {
               Method method = matches.get(i);
               if(method.getParameterTypes().length != 1)
               {
                  throw new InvalidAdviceException(
                        "Only one argument of type invocations is supported. Method '"
                        + method.toString() + "' (aspect class " + 
                        aspect.getClass().getName() + ") does not comply");
               }
               
               // we only support params that implements org.jboss.aop.joinpoint.Invocation
               Class<?> paramClass = method.getParameterTypes()[0];
               boolean foundInterface = false;
               if(paramClass.isInterface() && paramClass.getName().equals("org.jboss.aop.joinpoint.Invocation"))
                  foundInterface = true;
               else
               {
                  Class<?> superParamClass = findSuperClass(paramClass);
                  for(Class<?> iClass : superParamClass.getInterfaces())
                  {
                     if(iClass.getName().equals("org.jboss.aop.joinpoint.Invocation"))
                        foundInterface = true;
                  }
               }
               if(!foundInterface)
                  throw new InvalidAdviceException(
                        "Aspect method must have a parameter that implements Invocation. "
                        + "Parameter of advice '" + method.getName()+
                        "' (aspect class" + aspect.getClass().getName() +
                        ") does not comply: " + paramClass.getCanonicalName());
            }

            CtClass clazz = TransformerCommon.makeClass(pool, name);

            // We need to know whether this Interceptor is actually advice.
            CtClass interceptorInterface = pool.get("org.jboss.aop.advice.Interceptor");
            CtClass abstractAdviceClass = pool.get("org.jboss.aop.advice.AbstractAdvice");
            clazz.setSuperclass(abstractAdviceClass);

            // aspect field
            CtClass aspectClass = pool.get(aspect.getClass().getName());
            CtField field = new CtField(aspectClass, "aspectField", clazz);
            field.setModifiers(javassist.Modifier.PUBLIC);
            clazz.addField(field);
            
            CtMethod getAspectFld = CtNewMethod.make(pool.get(Object.class.getName()), "getAspectInstance", new CtClass[0], new CtClass[0], "{return aspectField;}", clazz);
            getAspectFld.setModifiers(javassist.Modifier.PUBLIC);
            clazz.addMethod(getAspectFld);
            
            // getName()
            CtMethod getNameTemplate = interceptorInterface.getDeclaredMethod("getName");
            String getNameBody =
            "{ " +
            "   return \"" + aspect.getClass().getName() + "." + adviceName + "\"; " +
            "}";
            CtMethod getName = CtNewMethod.make(getNameTemplate.getReturnType(), "getName", getNameTemplate.getParameterTypes(), getNameTemplate.getExceptionTypes(), getNameBody, clazz);
            getName.setModifiers(javassist.Modifier.PUBLIC);
            clazz.addMethod(getName);

            // invoke
            CtMethod invokeTemplate = interceptorInterface.getDeclaredMethod("invoke");
            StringBuffer invokeBody = new StringBuffer();
            invokeBody.append("{  ");
            if (matches.size() > 1)
            {
               boolean noArg = false;
               for (int i = 0; i < matches.size(); i++)
               {
                  Method advice = matches.get(i);
                  if (advice.getParameterTypes().length > 0)
                  {
                     String param = advice.getParameterTypes()[0].getName();
                     invokeBody.append("   if ($1 instanceof " + param + ") return aspectField." + adviceName + "((" + param + ")$1); ");
                  }
                  else
                  {
                     noArg = true;
                     invokeBody.append("   return aspectField." + adviceName + "(); ");
                     break;
                  }
               }
               if (!noArg)
               invokeBody.append("   return (org.jboss.aop.joinpoint.Invocation)null; ");
            }
            else
            {
               Method advice = matches.get(0);
               String param = advice.getParameterTypes()[0].getName();
               invokeBody.append("return aspectField." + adviceName + "((" + param + ")$1); ");
            }
            invokeBody.append("}");
            CtMethod invoke = CtNewMethod.make(invokeTemplate.getReturnType(), "invoke", invokeTemplate.getParameterTypes(), invokeTemplate.getExceptionTypes(), invokeBody.toString(), clazz);
            invoke.setModifiers(javassist.Modifier.PUBLIC);
            clazz.addMethod(invoke);

            CtConstructor ctor = CtNewConstructor.defaultConstructor(clazz);
            ctor.setBody("{super.adviceName = \"" + adviceName + "\";}");
            clazz.addConstructor(ctor);
            
            ProtectionDomain pd = aspect.getClass().getProtectionDomain();
            iclass = TransformerCommon.toClass(clazz, cl, pd);
         }
      }
      Interceptor rtn = (Interceptor) iclass.newInstance();
      Field f = iclass.getField("aspectField");
      f.set(rtn, aspect);
      return rtn;
   }
   
   private static Class<?> findSuperClass(Class<?> clazz)
   {
      if(clazz.getSuperclass() == null || clazz.getSuperclass().getName().equals("java.lang.Object"))
         return clazz;
      else
         return findSuperClass(clazz.getSuperclass());

   }
      

}