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
package org.jboss.test.aop.callerscope;

import org.jboss.aop.instrument.Untransformable;

/** Main intent of this test is to check that differently scoped interceptors/aspects
 * will work with caller pointcuts.
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
public class POJO implements Untransformable
{
   public POJO()
   {
      System.out.println("*** PER_VM advised POJO constructor");
   }

   public POJO(int i)
   {
      System.out.println("*** PER_CLASS advised POJO constructor");
   }

   public POJO(boolean b)
   {
      System.out.println("*** PER_CLASS_JOINPOINT advised POJO constructor");
   }
   
   public void perVmMethod()
   {
      System.out.println("*** PER_VM POJO method");
   }
   
   public void perClassMethod()
   {
      System.out.println("*** PER_CLASS POJO method");
   }
   
   public void perInstanceMethod()
   {
      System.out.println("*** PER_INSTANCE POJO method");
   }
   
   public void perJoinpointMethod()
   {
      System.out.println("*** PER_JOINPOINT POJO method");
   }
   
   public void perClassJoinpointMethod()
   {
      System.out.println("*** PER_CLASS_JOINPOINT POJO method");
   }
   

}
