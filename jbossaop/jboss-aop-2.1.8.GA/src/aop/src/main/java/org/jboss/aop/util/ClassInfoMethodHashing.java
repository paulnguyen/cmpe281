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
package org.jboss.aop.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;
import java.util.HashMap;

import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Experimental version of method hashing for the container Class-/Method-/ConstructorInfos
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 71279 $
 */
public class ClassInfoMethodHashing
{
   public static long methodHash(MethodInfo methodInfo)
   {
      try
      {
         TypeInfo[] parameterTypes = methodInfo.getParameterTypes();
         String methodDesc = methodInfo.getName()+"(";
         for(int j = 0; j < parameterTypes.length; j++)
         {
            methodDesc += getTypeString(parameterTypes[j]);
         }
         methodDesc += ")"+getTypeString(methodInfo.getReturnType());
         
         long hash = 0;
         ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(512);
         MessageDigest messagedigest = MessageDigest.getInstance("SHA");
         DataOutputStream dataoutputstream = new DataOutputStream(new DigestOutputStream(bytearrayoutputstream, messagedigest));
         dataoutputstream.writeUTF(methodDesc);
         dataoutputstream.flush();
         byte abyte0[] = messagedigest.digest();
         for(int j = 0; j < Math.min(8, abyte0.length); j++)
            hash += (long)(abyte0[j] & 0xff) << j * 8;
         return hash;
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   public static long constructorHash(ConstructorInfo constructorInfo)
   {
      try
      {
         TypeInfo[] parameterTypes = constructorInfo.getParameterTypes();
         String methodDesc = constructorInfo.getDeclaringClass().getName()+"(";
         for(int j = 0; j < parameterTypes.length; j++)
         {
            methodDesc += getTypeString(parameterTypes[j]);
         }
         methodDesc += ")";

         long hash = 0;
         ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(512);
         MessageDigest messagedigest = MessageDigest.getInstance("SHA");
         DataOutputStream dataoutputstream = new DataOutputStream(new DigestOutputStream(bytearrayoutputstream, messagedigest));
         dataoutputstream.writeUTF(methodDesc);
         dataoutputstream.flush();
         byte abyte0[] = messagedigest.digest();
         for(int j = 0; j < Math.min(8, abyte0.length); j++)
            hash += (long)(abyte0[j] & 0xff) << j * 8;
         return hash;
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   static String getTypeString(TypeInfo cl)
      throws Exception
   {
      if (cl.getClass().equals(PrimitiveInfo.class))
      {
         if (cl.equals(PrimitiveInfo.BYTE))
         {
            return "B";
         }
         else if (cl.equals(PrimitiveInfo.CHAR))
         {
            return "C";
         } 
         else if (cl.equals(PrimitiveInfo.DOUBLE))
         {
            return "D";
         } 
         else if (cl.equals(PrimitiveInfo.FLOAT))
         {
            return "F";
         } 
         else if (cl.equals(PrimitiveInfo.INT))
         {
            return "I";
         } 
         else if (cl.equals(PrimitiveInfo.LONG))
         {
            return "J";
         } 
         else if (cl.equals(PrimitiveInfo.SHORT))
         {
            return "S";
         } 
         else if (cl.equals(PrimitiveInfo.BOOLEAN))
         {
            return "Z";
         } 
         else if (cl.equals(PrimitiveInfo.VOID))
         {
            return "V";
         }
         else
         {
            throw new RuntimeException("Invalid primitive info " + cl);
         }
      } 
      else if (cl.isArray())
      {
         TypeInfo arrayType = ((ArrayInfo)cl).getComponentType();
         return "["+getTypeString(arrayType);
      } 
      else
      {
         return "L"+cl.getName().replace('.','/')+";";
      }
   }
   
   private static void addDeclaredMethods(HashMap<Long, MethodInfo> advised, ClassInfo superclass) throws Exception
   {
      MethodInfo[] declaredMethods = superclass.getDeclaredMethods();
      if (declaredMethods != null)
      {
         for (int i = 0; i < declaredMethods.length; i++)
         {
            if (superclass.isInterface() || Advisable.isAdvisableMethod(declaredMethods[i].getModifiers(), declaredMethods[i].getName()))
            {
               long hash = methodHash(declaredMethods[i]);
               advised.put(new Long(hash), declaredMethods[i]);
            }
         }
      }
   }
   private static void populateMethodTables(HashMap<Long, MethodInfo> advised, ClassInfo superclass)
      throws Exception
   {
      if (superclass == null) return;
      if (superclass.getName().equals("java.lang.Object")) return;

      populateMethodTables(advised, superclass.getSuperclass());
      addDeclaredMethods(advised, superclass);
   }

   public static HashMap<Long, MethodInfo> getMethodMap(ClassInfo clazz) throws Exception
   {
      HashMap<Long, MethodInfo> map = new HashMap<Long, MethodInfo>();
      populateMethodTables(map, clazz);
      return map;
   }

   public static HashMap<Long, MethodInfo> getDeclaredMethodMap(ClassInfo clazz) throws Exception
   {
      HashMap<Long, MethodInfo> map = new HashMap<Long, MethodInfo>();
      addDeclaredMethods(map, clazz);
      return map;
   }

}
