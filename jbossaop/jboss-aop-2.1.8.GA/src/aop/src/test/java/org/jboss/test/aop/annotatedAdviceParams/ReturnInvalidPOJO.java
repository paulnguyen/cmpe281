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

import java.util.List;

/**
 * Plain old java object used both on @Return parameter tests with invalid advices(
 * its joinpoint executions will throw a NoMatchingAdviceException, due to the fact
 * that there is no advice that matches the joinpoint).
 * <br>
 * This class is a "copy" of {@link ArgsPOJO} class. Each joinpoint of the original
 * class is duplicated here to be matched by each invalid advice, in order to assert
 * the invalidity of advices in an individual manner.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * 
 * @see ReturnInvalidTestCase
 */
public class ReturnInvalidPOJO
{
   /* String field1 */
   
   public String field1Around2;
   
   /* String field2 */
   
   public String field2Before;
   
   /* String field4 */
   
   public String field4Finally5;
   
   /* String field5 */
   
   public String field5Around6;
   
   /* SubValue field7 */
   
   public SubValue field7Before;
   
   /* SubValue field8 */
   
   public SubValue field8Around9;
   public SubValue field8After9;
   public SubValue field8Finally9;
   
   /* SuperValue field9 */
   
   public SuperValue field9Before;
   public SuperValue field9After10;
   
   /* List<SuperValue> fieldGenerics */
   
   public List<SuperValue> fieldGenericsAround1;
   public List<SuperValue> fieldGenericsAround2;
   public List<SuperValue> fieldGenericsAround4;
   public List<SuperValue> fieldGenericsAround5;
   public List<SuperValue> fieldGenericsAround7;
   public List<SuperValue> fieldGenericsAround8;
   public List<SuperValue> fieldGenericsAfter5;
   public List<SuperValue> fieldGenericsAfter7;
   public List<SuperValue> fieldGenericsAfter8;
   public List<SuperValue> fieldGenericsFinally5;
   public List<SuperValue> fieldGenericsFinally7;
   public List<SuperValue> fieldGenericsFinally8;
   public List<SuperValue> fieldGenericsFinally9;
   public List<SuperValue> fieldGenericsFinally10;
   
   /* method1() */
   
   public void method1Before() {}

   /* method2() */
   
   public String method2Around2()
   {
      return "method2";
   }
   
   /* method3() */
   
   public String method3Before()
   {
      return "method3";
   }
   
   /* method5() */
   
   public String method5Finally5()
   {
      return "method5";
   }
   
   /* method6Around6() */
   
   public String method6Around6()
   {
      return "method6";
   }
   
   /* method8() */
   
   public SubValue method8Before()
   {
      return new SubValue(8);
   }
   
   /* method9() */
   
   public SubValue method9Around9()
   {
      return new SubValue(9);
   }
   
   public SubValue method9After9()
   {
      return new SubValue(9);
   }
   
   public SubValue method9Finally9()
   {
      return new SubValue(9);
   }
   
   /* method10() */
   
   public SuperValue method10Before()
   {
      return new SuperValue(10);
   }
   
   public SuperValue method10After10()
   {
      return new SuperValue(10);
   }
   
   /* methodGenericsExecution() */
   
   public List<SuperValue> methodGenericsExecutionAround1()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAround2()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAround4()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAround5()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAround7()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAround8()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAfter5()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAfter7()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionAfter8()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionFinally5()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionFinally7()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionFinally8()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionFinally9()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsExecutionFinally10()
   {
      return null;
   }
   
   /* methodGenericsCall() */
   
   public List<SuperValue> methodGenericsCallAround1()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAround2()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAround4()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAround5()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAround7()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAround8()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAfter5()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAfter7()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallAfter8()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallFinally5()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallFinally7()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallFinally8()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallFinally9()
   {
      return null;
   }
   
   public List<SuperValue> methodGenericsCallFinally10()
   {
      return null;
   }
}