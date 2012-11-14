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
package org.jboss.test.aop.regression.jbaop110;

import org.jboss.test.aop.regression.jbaop110.Type.NormalType;
import org.jboss.test.aop.regression.jbaop110.Type.StaticType;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
@SuppressWarnings("unused")
public class POJO
{
   NormalType nt;
   StaticType st;

   public POJO()
   {
      
   }
   
   public POJO(NormalType ntype, StaticType stype)
   {
      
   }
   
   public NormalType method(StaticType stype, NormalType ntype)
   {
      return ntype;
   }
   
   public void executeNormal()
   {
      NormalClass instance = new NormalClass(Type.type.normalType, Type.type.staticType);
      
      instance.nnt = Type.type.normalType;
      NormalType nt = instance.nnt;
      
      instance.nst = Type.type.staticType;
      StaticType st = instance.nst;
      
      instance.method(Type.type.staticType, Type.type.normalType);
   }
   
   public void executeStatic()
   {
      StaticClass instance = new StaticClass(Type.type.normalType, Type.type.staticType);
      
      instance.snt = Type.type.normalType;
      NormalType nt = instance.snt;
      
      instance.sst = Type.type.staticType;
      StaticType st = instance.sst;
      
      instance.method(Type.type.staticType, Type.type.normalType);
   }
   
   public class NormalClass
   {
      NormalType nnt;
      StaticType nst;
      
      public NormalClass(NormalType ntype, StaticType stype)
      {
         
      }
      
      public NormalType method(StaticType stype, NormalType ntype)
      {
         return ntype;
      }
   }
   
   private static class StaticClass
   {
      Type.NormalType snt;
      Type.StaticType sst;
      
      public StaticClass(NormalType ntype, StaticType stype)
      {
         
      }
      
      public NormalType method(StaticType stype, NormalType ntype)
      {
         return ntype;
      }
   }

}
