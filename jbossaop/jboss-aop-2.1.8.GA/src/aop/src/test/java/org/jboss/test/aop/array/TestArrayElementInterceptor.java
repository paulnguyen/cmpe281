/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.array;

import java.util.List;

import junit.framework.Assert;

import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.array.ArrayElementReadInvocation;
import org.jboss.aop.array.ArrayElementWriteInvocation;
import org.jboss.aop.array.ArrayReference;
import org.jboss.aop.array.ArrayRegistry;
import org.jboss.aop.array.BooleanArrayElementWriteInvocation;
import org.jboss.aop.array.ByteArrayElementWriteInvocation;
import org.jboss.aop.array.CharArrayElementWriteInvocation;
import org.jboss.aop.array.DoubleArrayElementWriteInvocation;
import org.jboss.aop.array.FloatArrayElementWriteInvocation;
import org.jboss.aop.array.IntArrayElementWriteInvocation;
import org.jboss.aop.array.LongArrayElementWriteInvocation;
import org.jboss.aop.array.ObjectArrayElementWriteInvocation;
import org.jboss.aop.array.ShortArrayElementWriteInvocation;
import org.jboss.aop.joinpoint.Invocation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class TestArrayElementInterceptor implements Interceptor
{
   public static int index;
   public static Object value;
   public static List<ArrayReference> owners;
   
   public static void clear()
   {
      index = -1;
      value = null;
      owners = null;
   }
   
   public String getName()
   {
      return this.getClass().getName();
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      if (invocation instanceof ArrayElementReadInvocation)
      {
         index = ((ArrayElementReadInvocation)invocation).getIndex();
         value = null;
      }
      else if (invocation instanceof ArrayElementWriteInvocation)
      {
         index = ((ArrayElementWriteInvocation)invocation).getIndex();
         value = ((ArrayElementWriteInvocation)invocation).getValue();
         checkType((ArrayElementWriteInvocation)invocation);
      } 
      owners = ArrayRegistry.getInstance().getArrayOwners(invocation.getTargetObject());
      invocation.getMetaData().addMetaData("test", "invoked", "TestArrayElementInterceptor");
      return invocation.invokeNext();
   }

   public static class Value
   {
      public Object value;
   }
   
   private void checkType(ArrayElementWriteInvocation invocation)
   {
      if (invocation instanceof ObjectArrayElementWriteInvocation)
      {
         
      }
      else if (invocation instanceof ByteArrayElementWriteInvocation)
      {
         byte b = ((ByteArrayElementWriteInvocation)invocation).getByteValue();
         Assert.assertEquals(b, ((Byte)invocation.getValue()).byteValue());
      }
      else if (invocation instanceof BooleanArrayElementWriteInvocation)
      {
         boolean b = ((BooleanArrayElementWriteInvocation)invocation).getBooleanValue();
         Assert.assertEquals(b, ((Boolean)invocation.getValue()).booleanValue());
      }
      else if (invocation instanceof CharArrayElementWriteInvocation)
      {
         char c = ((CharArrayElementWriteInvocation)invocation).getCharValue();
         Assert.assertEquals(c, ((Character)invocation.getValue()).charValue());
      }
      else if (invocation instanceof DoubleArrayElementWriteInvocation)
      {
         double d = ((DoubleArrayElementWriteInvocation)invocation).getDoubleValue();
         Assert.assertEquals(d, ((Double)invocation.getValue()).doubleValue());         
      }
      else if (invocation instanceof FloatArrayElementWriteInvocation)
      {
         float f = ((FloatArrayElementWriteInvocation)invocation).getFloatValue();
         Assert.assertEquals(f, ((Float)invocation.getValue()).floatValue());         
      }
      else if (invocation instanceof IntArrayElementWriteInvocation)
      {
         int i = ((IntArrayElementWriteInvocation)invocation).getIntValue();
         Assert.assertEquals(i, ((Integer)invocation.getValue()).intValue());         
      }
      else if (invocation instanceof LongArrayElementWriteInvocation)
      {
         long l = ((LongArrayElementWriteInvocation)invocation).getLongValue();
         Assert.assertEquals(l, ((Long)invocation.getValue()).longValue());         
      }
      else if (invocation instanceof ShortArrayElementWriteInvocation)
      {
         short s = ((ShortArrayElementWriteInvocation)invocation).getShortValue();
         Assert.assertEquals(s, ((Short)invocation.getValue()).shortValue());         
      }
      else
      {
         throw new RuntimeException("Dodgy invocation type " + invocation.getClass().getName());
      }
   }
}
