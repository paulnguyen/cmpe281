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
package org.jboss.test.aop.annotatedAdviceParams;

import junit.framework.Assert;

import org.jboss.aop.advice.annotation.Arg;
import org.jboss.aop.advice.annotation.Args;
import org.jboss.aop.advice.annotation.JoinPoint;
import org.jboss.aop.advice.annotation.Thrown;
import org.jboss.aop.joinpoint.JoinPointBean;

/**
 * Aspect used on overloaded throwing advice tests (for JoinPoint, Thrown, Arg and
 * Args tests).
 * 
 * This class includes both class and interface hierarchy on multiple Arg parameters.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class OverloadedThrowingAspect
{
   static String throwing1 = null;
   static String throwing2 = null;
   static String throwing3 = null;
   static String throwing4 = null;
   static String throwing5 = null;
   static String throwing6 = null;
   static String throwing7 = null;
   static String throwing8 = null;
   static String throwing9 = null;
   static String throwing10 = null;
   static String throwing11 = null;
   static String throwing12 = null;
   static String throwing13 = null;
   static String throwing14 = null;
   static String throwing15 = null;
   static String throwing16 = null;
   static String throwing17 = null;
   static String throwing18 = null;
   static String throwing19 = null;
   static String throwing20 = null;
   static String throwing21 = null;
   static String throwing22 = null;
   static String throwing23 = null;
   static String throwing24 = null;
   static String throwing25 = null;
   static String throwing26 = null;
   static String throwing27 = null;
   static String throwing28 = null;
   static String throwing29 = null;
   static String throwing30 = null;
   static String throwing31 = null;
   static String throwing32 = null;
   static String throwing33 = null;
   static String throwing34 = null;
   static String throwing35 = null;
   static String throwing36 = null;
   static String throwing37 = null;
   static String throwing38 = null;
   
   static void clear()
   {
      throwing1 = null;
      throwing2 = null;
      throwing3 = null;
      throwing4 = null;
      throwing5 = null;
      throwing6 = null;
      throwing7 = null;
      throwing8 = null;
      throwing9 = null;
      throwing10 = null;
      throwing11 = null;
      throwing12 = null;
      throwing13 = null;
      throwing14 = null;
      throwing15 = null;
      throwing16 = null;
      throwing17 = null;
      throwing18 = null;
      throwing19 = null;
      throwing20 = null;
      throwing21 = null;
      throwing22 = null;
      throwing23 = null;
      throwing24 = null;
      throwing25 = null;
      throwing26 = null;
      throwing27 = null;
      throwing28 = null;
      throwing29 = null;
      throwing30 = null;
      throwing31 = null;
      throwing32 = null;
      throwing33 = null;
      throwing34 = null;
      throwing35 = null;
      throwing36 = null;
      throwing37 = null;
      throwing38 = null;
   }

   /* THROWING1 ADVICE */
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SubInterface,Implementor";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing1 = "JoinPointBean,Throwable,Interface,Implementor";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SubInterface,SubInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SubInterface,Object";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing1 = "JoinPointBean,Throwable,SuperInterface,Implementor";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SubInterface,Interface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,Interface,SubInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SuperInterface,SubInterface";
   }

   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SuperInterface,Object";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,Interface,Interface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SuperInterface,Interface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,Interface,SuperInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing1 = "JoinPointBean,Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing1 = "JoinPointBean,Object,SubInterface,Implementor";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing1 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing1 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing1 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing1 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing1 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing1 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing1 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing1 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing1 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing1 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing1 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing1 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing1 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing1 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing1(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing1 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing1 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing1(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing1 = "JoinPointBean,Throwable,Object";
   }
   public void throwing1(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing1 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing1 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing1 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing1 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing1 = "Throwable,SubInterface,Object";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing1 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing1 = "Throwable,Interface,Implementor";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing1 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing1 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing1 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing1 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing1 = "Throwable,Interface,Interface";
   }

   public void throwing1(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing1 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing1 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing1 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing1 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing1 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing1(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing1 = "Object,SubInterface,Implementor";
   }

   public void throwing1(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing1 = "Throwable,Implementor";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing1 = "Throwable,SubInterface";
   }

   public void throwing1(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing1 = "Throwable,Object";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing1 = "Throwable,Interface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing1 = "Throwable,SuperInterface";
   }

   public void throwing1(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing1 = "Object,Implementor";
   }
      
   public void throwing1(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing1 = "Object,SubInterface";
   }

   public void throwing1(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing1 = "Object,Object";
   }
   
   public void throwing1(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing1 = "Object,Interface";
   }

   public void throwing1(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing1 = "Object,SuperInterface";
   }
   
   public void throwing1(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing1 = "Throwable,Object[]";
   }
   
   public void throwing1(@Thrown Object thrown, @Args Object[] args)
   {
      throwing1 = "Object,Object[]";
   }
   
   public void throwing1(@Thrown Throwable thrown)
   {
      throwing1 = "Throwable";
   }
   
   public void throwing1(@Thrown Object thrown)
   {
      throwing1 = "Object";
   }
   
   public void throwing1(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING2 ADVICE */
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing2 = "JoinPointBean,Throwable,Interface,Implementor";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SubInterface,SubInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SubInterface,Object";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing2 = "JoinPointBean,Throwable,SuperInterface,Implementor";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SubInterface,Interface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,Interface,SubInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SuperInterface,SubInterface";
   }

   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SuperInterface,Object";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,Interface,Interface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SuperInterface,Interface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,Interface,SuperInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing2 = "JoinPointBean,Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing2 = "JoinPointBean,Object,SubInterface,Implementor";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing2 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing2 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing2 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing2 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing2 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing2 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing2 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing2 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing2 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing2 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing2 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing2 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing2 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing2 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing2(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing2 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing2 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing2(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing2 = "JoinPointBean,Throwable,Object";
   }
   public void throwing2(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing2 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing2 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing2 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing2 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing2 = "Throwable,SubInterface,Object";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing2 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing2 = "Throwable,Interface,Implementor";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing2 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing2 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing2 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing2 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing2 = "Throwable,Interface,Interface";
   }

   public void throwing2(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing2 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing2 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing2 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing2 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing2 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing2(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing2 = "Object,SubInterface,Implementor";
   }

   public void throwing2(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing2 = "Throwable,Implementor";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing2 = "Throwable,SubInterface";
   }

   public void throwing2(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing2 = "Throwable,Object";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing2 = "Throwable,Interface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing2 = "Throwable,SuperInterface";
   }

   public void throwing2(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing2 = "Object,Implementor";
   }
      
   public void throwing2(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing2 = "Object,SubInterface";
   }

   public void throwing2(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing2 = "Object,Object";
   }
   
   public void throwing2(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing2 = "Object,Interface";
   }

   public void throwing2(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing2 = "Object,SuperInterface";
   }
   
   public void throwing2(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing2 = "Throwable,Object[]";
   }
   
   public void throwing2(@Thrown Object thrown, @Args Object[] args)
   {
      throwing2 = "Object,Object[]";
   }
   
   public void throwing2(@Thrown Throwable thrown)
   {
      throwing2 = "Throwable";
   }
   
   public void throwing2(@Thrown Object thrown)
   {
      throwing2 = "Object";
   }
   
   public void throwing2(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING3 ADVICE */
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing3 = "JoinPointBean,Throwable,SuperInterface,Implementor";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,SubInterface,Interface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,Interface,SubInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,SuperInterface,SubInterface";
   }

   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing3 = "JoinPointBean,Throwable,SuperInterface,Object";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,Interface,Interface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,SuperInterface,Interface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,Interface,SuperInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing3 = "JoinPointBean,Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing3 = "JoinPointBean,Object,SubInterface,Implementor";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing3 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing3 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing3 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing3 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing3 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing3 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing3 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing3 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing3 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing3 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing3 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing3 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing3 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing3 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing3(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing3 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing3 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing3(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing3 = "JoinPointBean,Throwable,Object";
   }
   public void throwing3(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing3 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing3 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing3 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing3 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing3 = "Throwable,SubInterface,Object";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing3 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing3 = "Throwable,Interface,Implementor";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing3 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing3 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing3 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing3 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing3 = "Throwable,Interface,Interface";
   }

   public void throwing3(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing3 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing3 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing3 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing3 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing3 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing3(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing3 = "Object,SubInterface,Implementor";
   }

   public void throwing3(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing3 = "Throwable,Implementor";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing3 = "Throwable,SubInterface";
   }

   public void throwing3(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing3 = "Throwable,Object";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing3 = "Throwable,Interface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing3 = "Throwable,SuperInterface";
   }

   public void throwing3(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing3 = "Object,Implementor";
   }
      
   public void throwing3(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing3 = "Object,SubInterface";
   }

   public void throwing3(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing3 = "Object,Object";
   }
   
   public void throwing3(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing3 = "Object,Interface";
   }

   public void throwing3(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing3 = "Object,SuperInterface";
   }
   
   public void throwing3(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing3 = "Throwable,Object[]";
   }
   
   public void throwing3(@Thrown Object thrown, @Args Object[] args)
   {
      throwing3 = "Object,Object[]";
   }
   
   public void throwing3(@Thrown Throwable thrown)
   {
      throwing3 = "Throwable";
   }
   
   public void throwing3(@Thrown Object thrown)
   {
      throwing3 = "Object";
   }
   
   public void throwing3(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING4 ADVICE */
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing4 = "JoinPointBean,Throwable,SuperInterface,SubInterface";
   }

   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing4 = "JoinPointBean,Throwable,SuperInterface,Object";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing4 = "JoinPointBean,Throwable,Interface,Interface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing4 = "JoinPointBean,Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing4 = "JoinPointBean,Throwable,SuperInterface,Interface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing4 = "JoinPointBean,Throwable,Interface,SuperInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing4 = "JoinPointBean,Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing4 = "JoinPointBean,Object,SubInterface,Implementor";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing4 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing4 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing4 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing4 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing4 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing4 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing4 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing4 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing4 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing4 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing4 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing4 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing4 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing4 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing4(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing4 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing4 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing4(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing4 = "JoinPointBean,Throwable,Object";
   }
   public void throwing4(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing4 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing4 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing4 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing4 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing4 = "Throwable,SubInterface,Object";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing4 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing4 = "Throwable,Interface,Implementor";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing4 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing4 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing4 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing4 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing4 = "Throwable,Interface,Interface";
   }

   public void throwing4(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing4 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing4 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing4 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing4 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing4 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing4(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing4 = "Object,SubInterface,Implementor";
   }

   public void throwing4(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing4 = "Throwable,Implementor";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing4 = "Throwable,SubInterface";
   }

   public void throwing4(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing4 = "Throwable,Object";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing4 = "Throwable,Interface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing4 = "Throwable,SuperInterface";
   }

   public void throwing4(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing4 = "Object,Implementor";
   }
      
   public void throwing4(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing4 = "Object,SubInterface";
   }

   public void throwing4(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing4 = "Object,Object";
   }
   
   public void throwing4(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing4 = "Object,Interface";
   }

   public void throwing4(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing4 = "Object,SuperInterface";
   }
   
   public void throwing4(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing4 = "Throwable,Object[]";
   }
   
   public void throwing4(@Thrown Object thrown, @Args Object[] args)
   {
      throwing4 = "Object,Object[]";
   }
   
   public void throwing4(@Thrown Throwable thrown)
   {
      throwing4 = "Throwable";
   }
   
   public void throwing4(@Thrown Object thrown)
   {
      throwing4 = "Object";
   }
   
   public void throwing4(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING5 ADVICE */
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing5 = "JoinPointBean,Throwable,SuperInterface,Interface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing5 = "JoinPointBean,Throwable,Interface,SuperInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing5 = "JoinPointBean,Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing5 = "JoinPointBean,Object,SubInterface,Implementor";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing5 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing5 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing5 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing5 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing5 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing5 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing5 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing5 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing5 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing5 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing5 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing5 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing5 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing5 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing5(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing5 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing5 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing5(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing5 = "JoinPointBean,Throwable,Object";
   }
   public void throwing5(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing5 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing5 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing5 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing5 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing5 = "Throwable,SubInterface,Object";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing5 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing5 = "Throwable,Interface,Implementor";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing5 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing5 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing5 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing5 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing5 = "Throwable,Interface,Interface";
   }

   public void throwing5(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing5 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing5 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing5 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing5 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing5 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing5(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing5 = "Object,SubInterface,Implementor";
   }

   public void throwing5(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing5 = "Throwable,Implementor";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing5 = "Throwable,SubInterface";
   }

   public void throwing5(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing5 = "Throwable,Object";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing5 = "Throwable,Interface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing5 = "Throwable,SuperInterface";
   }

   public void throwing5(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing5 = "Object,Implementor";
   }
      
   public void throwing5(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing5 = "Object,SubInterface";
   }

   public void throwing5(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing5 = "Object,Object";
   }
   
   public void throwing5(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing5 = "Object,Interface";
   }

   public void throwing5(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing5 = "Object,SuperInterface";
   }
   
   public void throwing5(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing5 = "Throwable,Object[]";
   }
   
   public void throwing5(@Thrown Object thrown, @Args Object[] args)
   {
      throwing5 = "Object,Object[]";
   }
   
   public void throwing5(@Thrown Throwable thrown)
   {
      throwing5 = "Throwable";
   }
   
   public void throwing5(@Thrown Object thrown)
   {
      throwing5 = "Object";
   }
   
   public void throwing5(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING6 ADVICE */
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing6 = "JoinPointBean,Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing6 = "JoinPointBean,Object,SubInterface,Implementor";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing6 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing6 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing6 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing6 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing6 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing6 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing6 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing6 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing6 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing6 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing6 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing6 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing6 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing6 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing6(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing6 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing6 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing6(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing6 = "JoinPointBean,Throwable,Object";
   }
   public void throwing6(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing6 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing6 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing6 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing6 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing6 = "Throwable,SubInterface,Object";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing6 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing6 = "Throwable,Interface,Implementor";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing6 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing6 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing6 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing6 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing6 = "Throwable,Interface,Interface";
   }

   public void throwing6(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing6 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing6 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing6 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing6 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing6 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing6(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing6 = "Object,SubInterface,Implementor";
   }

   public void throwing6(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing6 = "Throwable,Implementor";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing6 = "Throwable,SubInterface";
   }

   public void throwing6(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing6 = "Throwable,Object";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing6 = "Throwable,Interface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing6 = "Throwable,SuperInterface";
   }

   public void throwing6(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing6 = "Object,Implementor";
   }
      
   public void throwing6(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing6 = "Object,SubInterface";
   }

   public void throwing6(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing6 = "Object,Object";
   }
   
   public void throwing6(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing6 = "Object,Interface";
   }

   public void throwing6(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing6 = "Object,SuperInterface";
   }
   
   public void throwing6(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing6 = "Throwable,Object[]";
   }
   
   public void throwing6(@Thrown Object thrown, @Args Object[] args)
   {
      throwing6 = "Object,Object[]";
   }
   
   public void throwing6(@Thrown Throwable thrown)
   {
      throwing6 = "Throwable";
   }
   
   public void throwing6(@Thrown Object thrown)
   {
      throwing6 = "Object";
   }
   
   public void throwing6(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING7 ADVICE */
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Implementor arg2)
   {
      throwing7 = "JoinPointBean,Object,SubInterface,Implementor";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing7 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing7 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing7 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing7 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing7 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing7 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing7 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing7 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing7 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing7 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing7 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing7 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing7 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing7 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing7(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing7 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing7 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing7(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing7 = "JoinPointBean,Throwable,Object";
   }
   public void throwing7(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing7 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing7 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing7 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing7 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing7 = "Throwable,SubInterface,Object";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing7 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing7 = "Throwable,Interface,Implementor";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing7 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing7 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing7 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing7 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing7 = "Throwable,Interface,Interface";
   }

   public void throwing7(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing7 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing7 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing7 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing7 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing7 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing7(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing7 = "Object,SubInterface,Implementor";
   }

   public void throwing7(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing7 = "Throwable,Implementor";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing7 = "Throwable,SubInterface";
   }

   public void throwing7(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing7 = "Throwable,Object";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing7 = "Throwable,Interface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing7 = "Throwable,SuperInterface";
   }

   public void throwing7(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing7 = "Object,Implementor";
   }
      
   public void throwing7(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing7 = "Object,SubInterface";
   }

   public void throwing7(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing7 = "Object,Object";
   }
   
   public void throwing7(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing7 = "Object,Interface";
   }

   public void throwing7(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing7 = "Object,SuperInterface";
   }
   
   public void throwing7(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing7 = "Throwable,Object[]";
   }
   
   public void throwing7(@Thrown Object thrown, @Args Object[] args)
   {
      throwing7 = "Object,Object[]";
   }
   
   public void throwing7(@Thrown Throwable thrown)
   {
      throwing7 = "Throwable";
   }
   
   public void throwing7(@Thrown Object thrown)
   {
      throwing7 = "Object";
   }
   
   public void throwing7(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING8 ADVICE */
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Implementor arg2)
   {
      throwing8 = "JoinPointBean,Object,Interface,Implementor";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg SubInterface arg2)
   {
      throwing8 = "JoinPointBean,Object,SubInterface,SubInterface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing8 = "JoinPointBean,Object,SubInterface,Object";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing8 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing8 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing8 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing8 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing8 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing8 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing8 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing8 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing8 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing8 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing8 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing8(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing8 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing8 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing8(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing8 = "JoinPointBean,Throwable,Object";
   }
   public void throwing8(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing8 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing8 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing8 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing8 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing8 = "Throwable,SubInterface,Object";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing8 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing8 = "Throwable,Interface,Implementor";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing8 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing8 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing8 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing8 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing8 = "Throwable,Interface,Interface";
   }

   public void throwing8(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing8 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing8 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing8 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing8 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing8 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing8(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing8 = "Object,SubInterface,Implementor";
   }

   public void throwing8(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing8 = "Throwable,Implementor";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing8 = "Throwable,SubInterface";
   }

   public void throwing8(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing8 = "Throwable,Object";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing8 = "Throwable,Interface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing8 = "Throwable,SuperInterface";
   }

   public void throwing8(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing8 = "Object,Implementor";
   }
      
   public void throwing8(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing8 = "Object,SubInterface";
   }

   public void throwing8(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing8 = "Object,Object";
   }
   
   public void throwing8(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing8 = "Object,Interface";
   }

   public void throwing8(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing8 = "Object,SuperInterface";
   }
   
   public void throwing8(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing8 = "Throwable,Object[]";
   }
   
   public void throwing8(@Thrown Object thrown, @Args Object[] args)
   {
      throwing8 = "Object,Object[]";
   }
   
   public void throwing8(@Thrown Throwable thrown)
   {
      throwing8 = "Throwable";
   }
   
   public void throwing8(@Thrown Object thrown)
   {
      throwing8 = "Object";
   }
   
   public void throwing8(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING9 ADVICE */
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing9 = "JoinPointBean,Object,SuperInterface,Implementor";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, @Arg Interface arg2)
   {
      throwing9 = "JoinPointBean,Object,SubInterface,Interface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SubInterface arg2)
   {
      throwing9 = "JoinPointBean,Object,Interface,SubInterface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing9 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing9 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing9 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing9 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing9 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing9 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing9 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing9 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing9(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing9 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing9 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing9(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing9 = "JoinPointBean,Throwable,Object";
   }
   public void throwing9(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing9 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing9 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing9 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing9 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing9 = "Throwable,SubInterface,Object";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing9 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing9 = "Throwable,Interface,Implementor";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing9 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing9 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing9 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing9 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing9 = "Throwable,Interface,Interface";
   }

   public void throwing9(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing9 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing9 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing9 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing9 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing9 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing9(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing9 = "Object,SubInterface,Implementor";
   }

   public void throwing9(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing9 = "Throwable,Implementor";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing9 = "Throwable,SubInterface";
   }

   public void throwing9(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing9 = "Throwable,Object";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing9 = "Throwable,Interface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing9 = "Throwable,SuperInterface";
   }

   public void throwing9(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing9 = "Object,Implementor";
   }
      
   public void throwing9(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing9 = "Object,SubInterface";
   }

   public void throwing9(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing9 = "Object,Object";
   }
   
   public void throwing9(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing9 = "Object,Interface";
   }

   public void throwing9(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing9 = "Object,SuperInterface";
   }
   
   public void throwing9(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing9 = "Throwable,Object[]";
   }
   
   public void throwing9(@Thrown Object thrown, @Args Object[] args)
   {
      throwing9 = "Object,Object[]";
   }
   
   public void throwing9(@Thrown Throwable thrown)
   {
      throwing9 = "Throwable";
   }
   
   public void throwing9(@Thrown Object thrown)
   {
      throwing9 = "Object";
   }
   
   public void throwing9(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING10 ADVICE */
   
   public void throwing10(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing10 = "JoinPointBean,Object,SuperInterface,SubInterface";
   }

   public void throwing10(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg(index=1) Object arg2)
   {
      throwing10 = "JoinPointBean,Object,SuperInterface,Object";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg Interface arg2)
   {
      throwing10 = "JoinPointBean,Object,Interface,Interface";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing10 = "JoinPointBean,Object,SubInterface,SuperInterface";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing10 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing10 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing10 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing10 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing10(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing10 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing10 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing10(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing10 = "JoinPointBean,Throwable,Object";
   }
   public void throwing10(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing10 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing10 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing10 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing10 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing10 = "Throwable,SubInterface,Object";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing10 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing10 = "Throwable,Interface,Implementor";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing10 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing10 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing10 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing10 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing10 = "Throwable,Interface,Interface";
   }

   public void throwing10(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing10 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing10 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing10 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing10 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing10 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing10(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing10 = "Object,SubInterface,Implementor";
   }

   public void throwing10(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing10 = "Throwable,Implementor";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing10 = "Throwable,SubInterface";
   }

   public void throwing10(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing10 = "Throwable,Object";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing10 = "Throwable,Interface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing10 = "Throwable,SuperInterface";
   }

   public void throwing10(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing10 = "Object,Implementor";
   }
      
   public void throwing10(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing10 = "Object,SubInterface";
   }

   public void throwing10(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing10 = "Object,Object";
   }
   
   public void throwing10(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing10 = "Object,Interface";
   }

   public void throwing10(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing10 = "Object,SuperInterface";
   }
   
   public void throwing10(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing10 = "Throwable,Object[]";
   }
   
   public void throwing10(@Thrown Object thrown, @Args Object[] args)
   {
      throwing10 = "Object,Object[]";
   }
   
   public void throwing10(@Thrown Throwable thrown)
   {
      throwing10 = "Throwable";
   }
   
   public void throwing10(@Thrown Object thrown)
   {
      throwing10 = "Object";
   }
   
   public void throwing10(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING11 ADVICE */
   
   public void throwing11(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, @Arg Interface arg2)
   {
      throwing11 = "JoinPointBean,Object,SuperInterface,Interface";
   }
   
   public void throwing11(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg Interface arg1, @Arg SuperInterface arg2)
   {
      throwing11 = "JoinPointBean,Object,Interface,SuperInterface";
   }
   
   public void throwing11(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing11 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing11(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing11 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing11(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing11 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing11(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing11 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing11(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing11 = "JoinPointBean,Throwable,Object";
   }
   public void throwing11(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing11 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing11(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing11 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing11(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing11 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing11 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing11 = "Throwable,SubInterface,Object";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing11 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing11 = "Throwable,Interface,Implementor";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing11 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing11 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing11 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing11 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing11 = "Throwable,Interface,Interface";
   }

   public void throwing11(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing11 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing11 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing11 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing11 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing11 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing11(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing11 = "Object,SubInterface,Implementor";
   }

   public void throwing11(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing11 = "Throwable,Implementor";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing11 = "Throwable,SubInterface";
   }

   public void throwing11(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing11 = "Throwable,Object";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing11 = "Throwable,Interface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing11 = "Throwable,SuperInterface";
   }

   public void throwing11(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing11 = "Object,Implementor";
   }
      
   public void throwing11(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing11 = "Object,SubInterface";
   }

   public void throwing11(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing11 = "Object,Object";
   }
   
   public void throwing11(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing11 = "Object,Interface";
   }

   public void throwing11(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing11 = "Object,SuperInterface";
   }
   
   public void throwing11(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing11 = "Throwable,Object[]";
   }
   
   public void throwing11(@Thrown Object thrown, @Args Object[] args)
   {
      throwing11 = "Object,Object[]";
   }
   
   public void throwing11(@Thrown Throwable thrown)
   {
      throwing11 = "Throwable";
   }
   
   public void throwing11(@Thrown Object thrown)
   {
      throwing11 = "Object";
   }
   
   public void throwing11(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING12 ADVICE */
   
   public void throwing12(@JoinPoint JoinPointBean joinpoint,
         @Thrown Object thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing12 = "JoinPointBean,Object,SuperInterface,SuperInterface";
   }
   
   public void throwing12(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing12 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing12(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing12 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing12(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing12 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing12(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing12 = "JoinPointBean,Throwable,Object";
   }
   public void throwing12(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing12 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing12(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing12 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing12(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing12 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing12 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing12 = "Throwable,SubInterface,Object";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing12 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing12 = "Throwable,Interface,Implementor";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing12 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing12 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing12 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing12 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing12 = "Throwable,Interface,Interface";
   }

   public void throwing12(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing12 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing12 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing12 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing12 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing12 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing12(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing12 = "Object,SubInterface,Implementor";
   }

   public void throwing12(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing12 = "Throwable,Implementor";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing12 = "Throwable,SubInterface";
   }

   public void throwing12(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing12 = "Throwable,Object";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing12 = "Throwable,Interface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing12 = "Throwable,SuperInterface";
   }

   public void throwing12(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing12 = "Object,Implementor";
   }
      
   public void throwing12(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing12 = "Object,SubInterface";
   }

   public void throwing12(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing12 = "Object,Object";
   }
   
   public void throwing12(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing12 = "Object,Interface";
   }

   public void throwing12(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing12 = "Object,SuperInterface";
   }
   
   public void throwing12(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing12 = "Throwable,Object[]";
   }
   
   public void throwing12(@Thrown Object thrown, @Args Object[] args)
   {
      throwing12 = "Object,Object[]";
   }
   
   public void throwing12(@Thrown Throwable thrown)
   {
      throwing12 = "Throwable";
   }
   
   public void throwing12(@Thrown Object thrown)
   {
      throwing12 = "Object";
   }
   
   public void throwing12(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING13 ADVICE */
   
   public void throwing13(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing13 = "JoinPointBean,Throwable,SubInterface";
   }

   public void throwing13(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing13 = "JoinPointBean,Throwable,Implementor";
   }
   
   public void throwing13(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing13 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing13(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing13 = "JoinPointBean,Throwable,Object";
   }
   public void throwing13(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing13 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing13(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing13 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing13(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing13 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing13 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing13 = "Throwable,SubInterface,Object";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing13 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing13 = "Throwable,Interface,Implementor";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing13 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing13 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing13 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing13 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing13 = "Throwable,Interface,Interface";
   }

   public void throwing13(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing13 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing13 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing13 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing13 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing13 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing13(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing13 = "Object,SubInterface,Implementor";
   }

   public void throwing13(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing13 = "Throwable,Implementor";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing13 = "Throwable,SubInterface";
   }

   public void throwing13(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing13 = "Throwable,Object";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing13 = "Throwable,Interface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing13 = "Throwable,SuperInterface";
   }

   public void throwing13(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing13 = "Object,Implementor";
   }
      
   public void throwing13(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing13 = "Object,SubInterface";
   }

   public void throwing13(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing13 = "Object,Object";
   }
   
   public void throwing13(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing13 = "Object,Interface";
   }

   public void throwing13(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing13 = "Object,SuperInterface";
   }
   
   public void throwing13(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing13 = "Throwable,Object[]";
   }
   
   public void throwing13(@Thrown Object thrown, @Args Object[] args)
   {
      throwing13 = "Object,Object[]";
   }
   
   public void throwing13(@Thrown Throwable thrown)
   {
      throwing13 = "Throwable";
   }
   
   public void throwing13(@Thrown Object thrown)
   {
      throwing13 = "Object";
   }
   
   public void throwing13(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING14 ADVICE */
   
   public void throwing14(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing14 = "JoinPointBean,Throwable,Interface";
   }

   public void throwing14(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing14 = "JoinPointBean,Throwable,Object";
   }
   public void throwing14(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing14 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing14(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing14 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing14(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing14 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing14 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing14 = "Throwable,SubInterface,Object";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing14 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing14 = "Throwable,Interface,Implementor";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing14 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing14 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing14 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing14 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing14 = "Throwable,Interface,Interface";
   }

   public void throwing14(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing14 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing14 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing14 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing14 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing14 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing14(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing14 = "Object,SubInterface,Implementor";
   }

   public void throwing14(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing14 = "Throwable,Implementor";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing14 = "Throwable,SubInterface";
   }

   public void throwing14(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing14 = "Throwable,Object";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing14 = "Throwable,Interface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing14 = "Throwable,SuperInterface";
   }

   public void throwing14(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing14 = "Object,Implementor";
   }
      
   public void throwing14(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing14 = "Object,SubInterface";
   }

   public void throwing14(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing14 = "Object,Object";
   }
   
   public void throwing14(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing14 = "Object,Interface";
   }

   public void throwing14(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing14 = "Object,SuperInterface";
   }
   
   public void throwing14(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing14 = "Throwable,Object[]";
   }
   
   public void throwing14(@Thrown Object thrown, @Args Object[] args)
   {
      throwing14 = "Object,Object[]";
   }
   
   public void throwing14(@Thrown Throwable thrown)
   {
      throwing14 = "Throwable";
   }
   
   public void throwing14(@Thrown Object thrown)
   {
      throwing14 = "Object";
   }
   
   public void throwing14(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING15 ADVICE */
   
   public void throwing15(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing15 = "JoinPointBean,Throwable,SuperInterface";
   }
   
   public void throwing15(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing15 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing15(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing15 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing15 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing15 = "Throwable,SubInterface,Object";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing15 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing15 = "Throwable,Interface,Implementor";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing15 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing15 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing15 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing15 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing15 = "Throwable,Interface,Interface";
   }

   public void throwing15(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing15 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing15 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing15 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing15 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing15 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing15(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing15 = "Object,SubInterface,Implementor";
   }

   public void throwing15(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing15 = "Throwable,Implementor";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing15 = "Throwable,SubInterface";
   }

   public void throwing15(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing15 = "Throwable,Object";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing15 = "Throwable,Interface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing15 = "Throwable,SuperInterface";
   }

   public void throwing15(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing15 = "Object,Implementor";
   }
      
   public void throwing15(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing15 = "Object,SubInterface";
   }

   public void throwing15(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing15 = "Object,Object";
   }
   
   public void throwing15(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing15 = "Object,Interface";
   }

   public void throwing15(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing15 = "Object,SuperInterface";
   }
   
   public void throwing15(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing15 = "Throwable,Object[]";
   }
   
   public void throwing15(@Thrown Object thrown, @Args Object[] args)
   {
      throwing15 = "Object,Object[]";
   }
   
   public void throwing15(@Thrown Throwable thrown)
   {
      throwing15 = "Throwable";
   }
   
   public void throwing15(@Thrown Object thrown)
   {
      throwing15 = "Object";
   }
   
   public void throwing15(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING16 ADVICE */
   
   public void throwing16(@JoinPoint JoinPointBean joinPoint,
         @Thrown Throwable thrown, @Args Object[] args)
   {
      throwing16 = "JoinPointBean,Throwable,Object[]";
   }
   
   public void throwing16(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing16 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing16 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing16 = "Throwable,SubInterface,Object";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing16 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing16 = "Throwable,Interface,Implementor";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing16 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing16 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing16 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing16 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing16 = "Throwable,Interface,Interface";
   }

   public void throwing16(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing16 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing16 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing16 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing16 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing16 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing16(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing16 = "Object,SubInterface,Implementor";
   }

   public void throwing16(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing16 = "Throwable,Implementor";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing16 = "Throwable,SubInterface";
   }

   public void throwing16(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing16 = "Throwable,Object";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing16 = "Throwable,Interface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing16 = "Throwable,SuperInterface";
   }

   public void throwing16(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing16 = "Object,Implementor";
   }
      
   public void throwing16(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing16 = "Object,SubInterface";
   }

   public void throwing16(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing16 = "Object,Object";
   }
   
   public void throwing16(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing16 = "Object,Interface";
   }

   public void throwing16(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing16 = "Object,SuperInterface";
   }
   
   public void throwing16(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing16 = "Throwable,Object[]";
   }
   
   public void throwing16(@Thrown Object thrown, @Args Object[] args)
   {
      throwing16 = "Object,Object[]";
   }
   
   public void throwing16(@Thrown Throwable thrown)
   {
      throwing16 = "Throwable";
   }
   
   public void throwing16(@Thrown Object thrown)
   {
      throwing16 = "Object";
   }
   
   public void throwing16(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING17 ADVICE */
   
   public void throwing17(@JoinPoint JoinPointBean joinPoint,
         @Thrown Object thrown, @Args Object[] args)
   {
      throwing17 = "JoinPointBean,Object,Object[]";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing17 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing17 = "Throwable,SubInterface,Object";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing17 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing17 = "Throwable,Interface,Implementor";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing17 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing17 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing17 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing17 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing17 = "Throwable,Interface,Interface";
   }

   public void throwing17(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing17 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing17 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing17 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing17 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing17 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing17(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing17 = "Object,SubInterface,Implementor";
   }

   public void throwing17(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing17 = "Throwable,Implementor";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing17 = "Throwable,SubInterface";
   }

   public void throwing17(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing17 = "Throwable,Object";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing17 = "Throwable,Interface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing17 = "Throwable,SuperInterface";
   }

   public void throwing17(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing17 = "Object,Implementor";
   }
      
   public void throwing17(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing17 = "Object,SubInterface";
   }

   public void throwing17(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing17 = "Object,Object";
   }
   
   public void throwing17(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing17 = "Object,Interface";
   }

   public void throwing17(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing17 = "Object,SuperInterface";
   }
   
   public void throwing17(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing17 = "Throwable,Object[]";
   }
   
   public void throwing17(@Thrown Object thrown, @Args Object[] args)
   {
      throwing17 = "Object,Object[]";
   }
   
   public void throwing17(@Thrown Throwable thrown)
   {
      throwing17 = "Throwable";
   }
   
   public void throwing17(@Thrown Object thrown)
   {
      throwing17 = "Object";
   }
   
   public void throwing17(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING18 ADVICE */
   
   public void throwing18(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing18 = "Throwable,SubInterface,Implementor";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing18 = "Throwable,SubInterface,Object";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing18 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing18 = "Throwable,Interface,Implementor";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing18 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing18 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing18 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing18 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing18 = "Throwable,Interface,Interface";
   }

   public void throwing18(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing18 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing18 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing18 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing18 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing18 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing18(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing18 = "Object,SubInterface,Implementor";
   }

   public void throwing18(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing18 = "Throwable,Implementor";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing18 = "Throwable,SubInterface";
   }

   public void throwing18(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing18 = "Throwable,Object";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing18 = "Throwable,Interface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing18 = "Throwable,SuperInterface";
   }

   public void throwing18(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing18 = "Object,Implementor";
   }
      
   public void throwing18(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing18 = "Object,SubInterface";
   }

   public void throwing18(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing18 = "Object,Object";
   }
   
   public void throwing18(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing18 = "Object,Interface";
   }

   public void throwing18(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing18 = "Object,SuperInterface";
   }
   
   public void throwing18(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing18 = "Throwable,Object[]";
   }
   
   public void throwing18(@Thrown Object thrown, @Args Object[] args)
   {
      throwing18 = "Object,Object[]";
   }
   
   public void throwing18(@Thrown Throwable thrown)
   {
      throwing18 = "Throwable";
   }
   
   public void throwing18(@Thrown Object thrown)
   {
      throwing18 = "Object";
   }
   
   public void throwing18(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING19 ADVICE */
   
   public void throwing19(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing19 = "Throwable,SubInterface,Object";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing19 = "Throwable,SubInterface,SubInterface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Implementor arg2)
   {
      throwing19 = "Throwable,Interface,Implementor";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing19 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing19 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing19 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing19 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing19 = "Throwable,Interface,Interface";
   }

   public void throwing19(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing19 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing19 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing19 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing19 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing19 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing19(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing19 = "Object,SubInterface,Implementor";
   }

   public void throwing19(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing19 = "Throwable,Implementor";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing19 = "Throwable,SubInterface";
   }

   public void throwing19(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing19 = "Throwable,Object";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing19 = "Throwable,Interface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing19 = "Throwable,SuperInterface";
   }

   public void throwing19(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing19 = "Object,Implementor";
   }
      
   public void throwing19(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing19 = "Object,SubInterface";
   }

   public void throwing19(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing19 = "Object,Object";
   }
   
   public void throwing19(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing19 = "Object,Interface";
   }

   public void throwing19(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing19 = "Object,SuperInterface";
   }
   
   public void throwing19(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing19 = "Throwable,Object[]";
   }
   
   public void throwing19(@Thrown Object thrown, @Args Object[] args)
   {
      throwing19 = "Object,Object[]";
   }
   
   public void throwing19(@Thrown Throwable thrown)
   {
      throwing19 = "Throwable";
   }
   
   public void throwing19(@Thrown Object thrown)
   {
      throwing19 = "Object";
   }
   
   public void throwing19(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING20 ADVICE */
   
   public void throwing20(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SubInterface arg2)
   {
      throwing20 = "Throwable,Interface,SubInterface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SubInterface arg1,
         @Arg Interface arg2)
   {
      throwing20 = "Throwable,SubInterface,Interface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SuperInterface arg,
         @Arg Implementor implementor)
   {
      throwing20 = "Throwable,SuperInterface,Implementor";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing20 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing20 = "Throwable,Interface,Interface";
   }

   public void throwing20(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing20 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing20 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing20 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing20 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing20 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing20(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing20 = "Object,SubInterface,Implementor";
   }

   public void throwing20(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing20 = "Throwable,Implementor";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing20 = "Throwable,SubInterface";
   }

   public void throwing20(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing20 = "Throwable,Object";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing20 = "Throwable,Interface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing20 = "Throwable,SuperInterface";
   }

   public void throwing20(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing20 = "Object,Implementor";
   }
      
   public void throwing20(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing20 = "Object,SubInterface";
   }

   public void throwing20(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing20 = "Object,Object";
   }
   
   public void throwing20(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing20 = "Object,Interface";
   }

   public void throwing20(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing20 = "Object,SuperInterface";
   }
   
   public void throwing20(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing20 = "Throwable,Object[]";
   }
   
   public void throwing20(@Thrown Object thrown, @Args Object[] args)
   {
      throwing20 = "Object,Object[]";
   }
   
   public void throwing20(@Thrown Throwable thrown)
   {
      throwing20 = "Throwable";
   }
   
   public void throwing20(@Thrown Object thrown)
   {
      throwing20 = "Object";
   }
   
   public void throwing20(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING21 ADVICE */
   
   public void throwing21(@Thrown Throwable thrown, @Arg SubInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing21 = "Throwable,SubInterface,SuperInterface";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg Interface arg2)
   {
      throwing21 = "Throwable,Interface,Interface";
   }

   public void throwing21(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg(index=1) Object arg2)
   {
      throwing21 = "Throwable,SuperInterface,Object";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg SubInterface arg2)
   {
      throwing21 = "Throwable,SuperInterface,SubInterface";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing21 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing21 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing21 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing21(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing21 = "Object,SubInterface,Implementor";
   }

   public void throwing21(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing21 = "Throwable,Implementor";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing21 = "Throwable,SubInterface";
   }

   public void throwing21(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing21 = "Throwable,Object";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing21 = "Throwable,Interface";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing21 = "Throwable,SuperInterface";
   }

   public void throwing21(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing21 = "Object,Implementor";
   }
      
   public void throwing21(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing21 = "Object,SubInterface";
   }

   public void throwing21(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing21 = "Object,Object";
   }
   
   public void throwing21(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing21 = "Object,Interface";
   }

   public void throwing21(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing21 = "Object,SuperInterface";
   }
   
   public void throwing21(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing21 = "Throwable,Object[]";
   }
   
   public void throwing21(@Thrown Object thrown, @Args Object[] args)
   {
      throwing21 = "Object,Object[]";
   }
   
   public void throwing21(@Thrown Throwable thrown)
   {
      throwing21 = "Throwable";
   }
   
   public void throwing21(@Thrown Object thrown)
   {
      throwing21 = "Object";
   }
   
   public void throwing21(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING22 ADVICE */
   
   public void throwing22(@Thrown Throwable thrown, @Arg Interface arg1,
         @Arg SuperInterface arg2)
   {
      throwing22 = "Throwable,Interface,SuperInterface";
   }
   
   public void throwing22(@Thrown Throwable thrown, @Arg SuperInterface arg1,
         @Arg Interface arg2)
   {
      throwing22 = "Throwable,SuperInterface,Interface";
   }
   
   public void throwing22(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing22 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing22(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing22 = "Object,SubInterface,Implementor";
   }

   public void throwing22(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing22 = "Throwable,Implementor";
   }
   
   public void throwing22(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing22 = "Throwable,SubInterface";
   }

   public void throwing22(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing22 = "Throwable,Object";
   }
   
   public void throwing22(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing22 = "Throwable,Interface";
   }
   
   public void throwing22(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing22 = "Throwable,SuperInterface";
   }

   public void throwing22(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing22 = "Object,Implementor";
   }
      
   public void throwing22(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing22 = "Object,SubInterface";
   }

   public void throwing22(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing22 = "Object,Object";
   }
   
   public void throwing22(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing22 = "Object,Interface";
   }

   public void throwing22(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing22 = "Object,SuperInterface";
   }
   
   public void throwing22(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing22 = "Throwable,Object[]";
   }
   
   public void throwing22(@Thrown Object thrown, @Args Object[] args)
   {
      throwing22 = "Object,Object[]";
   }
   
   public void throwing22(@Thrown Throwable thrown)
   {
      throwing22 = "Throwable";
   }
   
   public void throwing22(@Thrown Object thrown)
   {
      throwing22 = "Object";
   }
   
   public void throwing22(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING23 ADVICE */
   
   public void throwing23(@Thrown Throwable thrown, @Arg SuperInterface arg1, 
         @Arg SuperInterface arg2)
   {
      throwing23 = "Throwable,SuperInterface,SuperInterface";
   }
   
   public void throwing23(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing23 = "Object,SubInterface,Implementor";
   }

   public void throwing23(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing23 = "Throwable,Implementor";
   }
   
   public void throwing23(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing23 = "Throwable,SubInterface";
   }

   public void throwing23(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing23 = "Throwable,Object";
   }
   
   public void throwing23(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing23 = "Throwable,Interface";
   }
   
   public void throwing23(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing23 = "Throwable,SuperInterface";
   }

   public void throwing23(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing23 = "Object,Implementor";
   }
      
   public void throwing23(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing23 = "Object,SubInterface";
   }

   public void throwing23(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing23 = "Object,Object";
   }
   
   public void throwing23(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing23 = "Object,Interface";
   }

   public void throwing23(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing23 = "Object,SuperInterface";
   }
   
   public void throwing23(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing23 = "Throwable,Object[]";
   }
   
   public void throwing23(@Thrown Object thrown, @Args Object[] args)
   {
      throwing23 = "Object,Object[]";
   }
   
   public void throwing23(@Thrown Throwable thrown)
   {
      throwing23 = "Throwable";
   }
   
   public void throwing23(@Thrown Object thrown)
   {
      throwing23 = "Object";
   }
   
   public void throwing23(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING24 ADVICE */
   
   public void throwing24(@Thrown Object thrown, @Arg SubInterface arg1,
         @Arg Implementor arg2)
   {
      throwing24 = "Object,SubInterface,Implementor";
   }

   public void throwing24(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing24 = "Throwable,Implementor";
   }
   
   public void throwing24(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing24 = "Throwable,SubInterface";
   }

   public void throwing24(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing24 = "Throwable,Object";
   }
   
   public void throwing24(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing24 = "Throwable,Interface";
   }
   
   public void throwing24(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing24 = "Throwable,SuperInterface";
   }

   public void throwing24(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing24 = "Object,Implementor";
   }
      
   public void throwing24(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing24 = "Object,SubInterface";
   }

   public void throwing24(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing24 = "Object,Object";
   }
   
   public void throwing24(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing24 = "Object,Interface";
   }

   public void throwing24(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing24 = "Object,SuperInterface";
   }
   
   public void throwing24(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing24 = "Throwable,Object[]";
   }
   
   public void throwing24(@Thrown Object thrown, @Args Object[] args)
   {
      throwing24 = "Object,Object[]";
   }
   
   public void throwing24(@Thrown Throwable thrown)
   {
      throwing24 = "Throwable";
   }
   
   public void throwing24(@Thrown Object thrown)
   {
      throwing24 = "Object";
   }
   
   public void throwing24(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING25 ADVICE */
   
   public void throwing25(@Thrown Throwable thrown, @Arg Implementor arg2)
   {
      throwing25 = "Throwable,Implementor";
   }
   
   public void throwing25(@Thrown Throwable thrown, @Arg SubInterface arg1)
   {
      throwing25 = "Throwable,SubInterface";
   }

   public void throwing25(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing25 = "Throwable,Object";
   }
   
   public void throwing25(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing25 = "Throwable,Interface";
   }
   
   public void throwing25(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing25 = "Throwable,SuperInterface";
   }

   public void throwing25(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing25 = "Object,Implementor";
   }
      
   public void throwing25(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing25 = "Object,SubInterface";
   }

   public void throwing25(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing25 = "Object,Object";
   }
   
   public void throwing25(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing25 = "Object,Interface";
   }

   public void throwing25(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing25 = "Object,SuperInterface";
   }
   
   public void throwing25(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing25 = "Throwable,Object[]";
   }
   
   public void throwing25(@Thrown Object thrown, @Args Object[] args)
   {
      throwing25 = "Object,Object[]";
   }
   
   public void throwing25(@Thrown Throwable thrown)
   {
      throwing25 = "Throwable";
   }
   
   public void throwing25(@Thrown Object thrown)
   {
      throwing25 = "Object";
   }
   
   public void throwing25(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING26 ADVICE */
   
   public void throwing26(@Thrown Throwable thrown, @Arg(index=1) Object arg2)
   {
      throwing26 = "Throwable,Object";
   }
   
   public void throwing26(@Thrown Throwable thrown, @Arg Interface arg1)
   {
      throwing26 = "Throwable,Interface";
   }
   
   public void throwing26(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing26 = "Throwable,SuperInterface";
   }

   public void throwing26(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing26 = "Object,Implementor";
   }
      
   public void throwing26(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing26 = "Object,SubInterface";
   }

   public void throwing26(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing26 = "Object,Object";
   }
   
   public void throwing26(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing26 = "Object,Interface";
   }

   public void throwing26(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing26 = "Object,SuperInterface";
   }
   
   public void throwing26(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing26 = "Throwable,Object[]";
   }
   
   public void throwing26(@Thrown Object thrown, @Args Object[] args)
   {
      throwing26 = "Object,Object[]";
   }
   
   public void throwing26(@Thrown Throwable thrown)
   {
      throwing26 = "Throwable";
   }
   
   public void throwing26(@Thrown Object thrown)
   {
      throwing26 = "Object";
   }
   
   public void throwing26(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING27 ADVICE */
   
   public void throwing27(@Thrown Throwable thrown, @Arg SuperInterface arg1)
   {
      throwing27 = "Throwable,SuperInterface";
   }

   public void throwing27(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing27 = "Object,Implementor";
   }
      
   public void throwing27(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing27 = "Object,SubInterface";
   }

   public void throwing27(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing27 = "Object,Object";
   }
   
   public void throwing27(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing27 = "Object,Interface";
   }

   public void throwing27(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing27 = "Object,SuperInterface";
   }
   
   public void throwing27(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing27 = "Throwable,Object[]";
   }
   
   public void throwing27(@Thrown Object thrown, @Args Object[] args)
   {
      throwing27 = "Object,Object[]";
   }
   
   public void throwing27(@Thrown Throwable thrown)
   {
      throwing27 = "Throwable";
   }
   
   public void throwing27(@Thrown Object thrown)
   {
      throwing27 = "Object";
   }
   
   public void throwing27(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING28 ADVICE */
   
   public void throwing28(@Thrown Object thrown, @Arg Implementor arg2)
   {
      throwing28 = "Object,Implementor";
   }
      
   public void throwing28(@Thrown Object thrown, @Arg SubInterface arg1)
   {
      throwing28 = "Object,SubInterface";
   }

   public void throwing28(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing28 = "Object,Object";
   }
   
   public void throwing28(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing28 = "Object,Interface";
   }

   public void throwing28(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing28 = "Object,SuperInterface";
   }
   
   public void throwing28(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing28 = "Throwable,Object[]";
   }
   
   public void throwing28(@Thrown Object thrown, @Args Object[] args)
   {
      throwing28 = "Object,Object[]";
   }
   
   public void throwing28(@Thrown Throwable thrown)
   {
      throwing28 = "Throwable";
   }
   
   public void throwing28(@Thrown Object thrown)
   {
      throwing28 = "Object";
   }
   
   public void throwing28(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING29 ADVICE */
   
   public void throwing29(@Thrown Object thrown, @Arg(index=1) Object arg2)
   {
      throwing29 = "Object,Object";
   }
   
   public void throwing29(@Thrown Object thrown, @Arg Interface arg1)
   {
      throwing29 = "Object,Interface";
   }

   public void throwing29(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing29 = "Object,SuperInterface";
   }
   
   public void throwing29(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing29 = "Throwable,Object[]";
   }
   
   public void throwing29(@Thrown Object thrown, @Args Object[] args)
   {
      throwing29 = "Object,Object[]";
   }
   
   public void throwing29(@Thrown Throwable thrown)
   {
      throwing29 = "Throwable";
   }
   
   public void throwing29(@Thrown Object thrown)
   {
      throwing29 = "Object";
   }
   
   public void throwing29(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING30 ADVICE */
   
   public void throwing30(@Thrown Object thrown, @Arg SuperInterface arg1)
   {
      throwing30 = "Object,SuperInterface";
   }
   
   public void throwing30(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing30 = "Throwable,Object[]";
   }
   
   public void throwing30(@Thrown Object thrown, @Args Object[] args)
   {
      throwing30 = "Object,Object[]";
   }
   
   public void throwing30(@Thrown Throwable thrown)
   {
      throwing30 = "Throwable";
   }
   
   public void throwing30(@Thrown Object thrown)
   {
      throwing30 = "Object";
   }
   
   public void throwing30(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING31 ADVICE */
   
   public void throwing31(@Thrown Throwable thrown, @Args Object[] args)
   {
      throwing31 = "Throwable,Object[]";
   }
   
   public void throwing31(@Thrown Object thrown, @Args Object[] args)
   {
      throwing31 = "Object,Object[]";
   }
   
   public void throwing31(@Thrown Throwable thrown)
   {
      throwing31 = "Throwable";
   }
   
   public void throwing31(@Thrown Object thrown)
   {
      throwing31 = "Object";
   }
   
   public void throwing31(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING32 ADVICE */
   
   public void throwing32(@Thrown Object thrown, @Args Object[] args)
   {
      throwing32 = "Object,Object[]";
   }
   
   public void throwing32(@Thrown Throwable thrown)
   {
      throwing32 = "Throwable";
   }
   
   public void throwing32(@Thrown Object thrown)
   {
      throwing32 = "Object";
   }
   
   public void throwing32(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING33 ADVICE */
   
   public void throwing33(@Thrown Throwable thrown)
   {
      throwing33 = "Throwable";
   }
   
   public void throwing33(@Thrown Object thrown)
   {
      throwing33 = "Object";
   }
   
   public void throwing33(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING34 ADVICE */
   
   public void throwing34(@Thrown Object thrown)
   {
      throwing34 = "Object";
   }
   
   public void throwing34(@JoinPoint JoinPointBean joinpoint)
   {
      Assert.fail("This advice should never be executed");
   }
   
   /* THROWING35 ADVICE */
   
   public void throwing35(@Thrown Throwable thrown, @Arg Object arg1)
   {
      throwing35 = "Throwable,Object";
   }
   
   public void throwing35(@Thrown Throwable thrown,
         @Arg SuperInterface arg2)
   {
      throwing35 = "Throwable,SuperInterface";  
   }
   
   /* THROWING36 ADVICE */
   
   public void throwing36(@Thrown Throwable thrown, @Arg Object arg1)
   {
      throwing36 = "Throwable,Object";
   }
   
   public void throwing36(@Thrown Throwable thrown,
         @Arg(index=1) SuperInterface arg2)
   {
      throwing36 = "Throwable,SuperInterface";  
   }
   
   /* THROWING37 ADVICE */
   
   public void throwing37(@Thrown Throwable thrown, @Arg(index=1) Object arg1)
   {
      throwing37 = "Throwable,Object";
   }
   
   public void throwing37(@Thrown Throwable thrown,
         @Arg SuperInterface arg2)
   {
      throwing37 = "Throwable,SuperInterface";  
   }
   
   /* THROWING38 ADVICE */
   
   public void throwing38(@Thrown Throwable thrown, @Arg(index=1) Object arg1)
   {
      throwing38 = "Throwable,Object";
   }
   
   public void throwing38(@Thrown Throwable thrown,
         @Arg(index=1) SuperInterface arg2)
   {
      throwing38 = "Throwable,SuperInterface";  
   }
}