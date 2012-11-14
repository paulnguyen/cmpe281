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
package org.jboss.aspects.jmx;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Introspects a class to provide the JMX management interface.
 * The class does not have to follow any pattern.
 * <p/>
 * public get/set methods will create mbean attributes, all other public
 * methods will be operations.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 74264 $
 */
public class JmxIntrospectingMixin implements DynamicMBean
{
   public class OpsKey
   {
      private final String methodName;
      private final String[] signature;
      private final int hash;

      public OpsKey(String methodName, String[] signature)
      {
         this.methodName = methodName;
         this.signature = signature;
         int tmp = methodName.hashCode();
         for (int i = 0; i < signature.length; i++)
         {
            tmp += signature[i].hashCode();
         }
         hash = tmp;
      }

      public int hashCode()
      {
         return hash;
      };
      public String getMethodName()
      {
         return methodName;
      }

      public String[] getSignature()
      {
         return signature;
      }

      public boolean equals(Object obj)
      {
         if (obj == this) return true;
         OpsKey key = (OpsKey) obj;
         if (key.hash != hash) return false;
         if (!key.methodName.equals(methodName)) return false;
         if (key.signature.length != signature.length) return false;
         for (int i = 0; i < signature.length; i++)
         {
            if (!signature[i].equals(key.signature[i])) return false;
         }
         return true;
      }
   }

   private final Object target;
   private final HashMap<OpsKey, Method> ops = new HashMap<OpsKey, Method>();
   private final HashMap<String, Method> gets = new HashMap<String, Method>();
   private final HashMap<String, Method> sets = new HashMap<String, Method>();
   private MBeanInfo mbeanInfo;


   public JmxIntrospectingMixin(Object target)
   {
      this.target = target;
   }

   public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException
   {
      Method get = gets.get(attribute);
      if (get == null) throw new AttributeNotFoundException(attribute);
      try
      {
         return get.invoke(target, null);
      }
      catch (IllegalAccessException e)
      {
         throw new ReflectionException(e);
      }
      catch (InvocationTargetException e)
      {
         if (e.getTargetException() instanceof Exception) throw new MBeanException((Exception) e.getTargetException());
         throw new MBeanException(new Exception(e.getTargetException().getMessage()));
      }
   }

   public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
   {
      Method set = sets.get(attribute.getName());
      if (set == null) throw new AttributeNotFoundException(attribute.getName());
      try
      {
         Object[] args = {attribute.getValue()};
         set.invoke(target, args);
      }
      catch (IllegalAccessException e)
      {
         throw new ReflectionException(e);
      }
      catch (InvocationTargetException e)
      {
         if (e.getTargetException() instanceof Exception) throw new MBeanException((Exception) e.getTargetException());
         throw new MBeanException(new Exception(e.getTargetException().getMessage()));
      }
   }

   public AttributeList getAttributes(String[] attributes)
   {
      AttributeList list = new AttributeList();
      for (int i = 0; i < attributes.length; i++)
      {
         try
         {
            list.add(0, new Attribute(attributes[i], getAttribute(attributes[i])));
         }
         catch (AttributeNotFoundException e)
         {
            throw new RuntimeException(e);
         }
         catch (MBeanException e)
         {
            throw new RuntimeException(e);
         }
         catch (ReflectionException e)
         {
            throw new RuntimeException(e);
         }
      }
      return list;
   }

   public AttributeList setAttributes(AttributeList attributes)
   {
      for (int i = 0; i < attributes.size(); i++)
      {
         try
         {
            Attribute attr = (Attribute) attributes.get(i);
            setAttribute(attr);
         }
         catch (InvalidAttributeValueException inv)
         {
            throw new RuntimeException(inv);
         }
         catch (AttributeNotFoundException e)
         {
            throw new RuntimeException(e);
         }
         catch (MBeanException e)
         {
            throw new RuntimeException(e);
         }
         catch (ReflectionException e)
         {
            throw new RuntimeException(e);
         }
      }
      return attributes;
   }

   public Object invoke(String actionName, Object params[], String signature[]) throws MBeanException, ReflectionException
   {
      OpsKey key = new OpsKey(actionName, signature);
      Method m = ops.get(key);
      if (m == null) throw new NoSuchMethodError(actionName);
      try
      {
         return m.invoke(target, params);
      }
      catch (IllegalAccessException e)
      {
         throw new ReflectionException(e);
      }
      catch (InvocationTargetException e)
      {
         Throwable cause = e.getTargetException();
         if (cause instanceof Exception) throw new MBeanException((Exception) cause);
         throw new MBeanException(new Exception(e.getMessage()));
      }
   }

   public MBeanInfo getMBeanInfo()
   {
      //System.out.println("******************* MBEAN INFO **********************");
      if (mbeanInfo != null)
      {
         //System.out.println("****mbeanInfo already exists***");
         return mbeanInfo;
      }


      try
      {
         Method[] methods = target.getClass().getMethods();

         //System.out.println("**** introspect attributes ****");
         for (int i = 0; i < methods.length; i++)
         {
            if (methods[i].getName().equals("_getInstanceAdvisor")) continue;
            if (methods[i].getName().equals("_setInstanceAdvisor")) continue;
            if (methods[i].getName().equals("_getAdvisor")) continue;
            if (methods[i].getName().equals("setAttribute")) continue;
            if (methods[i].getName().equals("getMBeanInfo")) continue;
            if (methods[i].getName().equals("getAttribute")) continue;
            if (methods[i].getName().equals("setAttributes")) continue;
            if (methods[i].getName().equals("getAttributes")) continue;
            if (methods[i].getName().equals("invoke")) continue;
            if (methods[i].getName().indexOf("$aop") != -1) continue;
            if (methods[i].getDeclaringClass().equals(java.lang.Object.class)) continue;
            if (methods[i].getName().startsWith("get") && methods[i].getParameterTypes().length == 0)
            {
               //System.out.println("adding get attribute: " + methods[i].getName().substring(3));
               gets.put(methods[i].getName().substring(3), methods[i]);
            }
            else if (methods[i].getName().startsWith("is") && methods[i].getParameterTypes().length == 0 &&
                    (methods[i].getReturnType().equals(Boolean.class) || methods[i].getReturnType().equals(boolean.class)))
            {
               //System.out.println("adding is attribute: " + methods[i].getName().substring(2));
               gets.put(methods[i].getName().substring(2), methods[i]);
            }
            else if (methods[i].getName().startsWith("set") && methods[i].getParameterTypes().length == 1)
            {
               //System.out.println("adding set attribute: " + methods[i].getName().substring(3));
               sets.put(methods[i].getName().substring(3), methods[i]);
            }
            else
            {
               //System.out.println("adding operation: " + methods[i].getName());
               String[] signature = new String[methods[i].getParameterTypes().length];
               for (int j = 0; j < methods[i].getParameterTypes().length; j++)
               {
                  signature[j] = methods[i].getParameterTypes()[j].getName();
               }
               ops.put(new OpsKey(methods[i].getName(), signature), methods[i]);
            }
         }

         HashMap<String, MBeanAttributeInfo> attributes = new HashMap<String, MBeanAttributeInfo>();
         for (String attribute : gets.keySet())
         {
            Method m = gets.get(attribute);
            //System.out.println("*** creating get: " + attribute);
            boolean isWritable = sets.containsKey(attribute);
            boolean isIs = m.getName().startsWith("is");
            MBeanAttributeInfo info = new MBeanAttributeInfo(attribute, m.getReturnType().getName(), target.getClass().getName() + "." + attribute, true, isWritable, isIs);
            attributes.put(attribute, info);
         }
         for (String attribute : sets.keySet())
         {
            if (gets.containsKey(attribute)) continue;
            //System.out.println("*** creating set: " + attribute);
            Method m = sets.get(attribute);
            MBeanAttributeInfo info = new MBeanAttributeInfo(attribute, m.getReturnType().getName(), target.getClass().getName() + "." + attribute, false, true, false);
            attributes.put(attribute, info);
         }

         MBeanOperationInfo[] operations = new MBeanOperationInfo[ops.size()];
         int i = 0;
         for (Method m : ops.values())
         {
            operations[i++] = new MBeanOperationInfo(m.toString(), m);
         }
         MBeanAttributeInfo[] attrs = attributes.values().toArray(new MBeanAttributeInfo[attributes.size()]);

         mbeanInfo = new MBeanInfo(target.getClass().getName(), target.getClass().getName(),
                 attrs, null, operations, null);
         //System.out.println("***returning MBeanInfo****");
         return mbeanInfo;
      }

      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }
}
