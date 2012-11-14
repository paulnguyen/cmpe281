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

import java.util.Collection;
import java.util.List;

/**
 * Plain old java object used on @Arg and @Args-annotated parameter tests with
 * invalid advices (its joinpoint executions will throw a NoMatchingAdviceException,
 * due to the fact that there is no advice that matches the joinpoint).
 * <br>
 * This class is a "copy" of {@link ArgsPOJO} class. Each joinpoint of the original
 * class is duplicated here to be matched by each invalid advice, in order to assert
 * the invalidity of advices in an individual manner.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * 
 * @see ArgInvalidTestCase
 * @see ArgsInvalidTestCase
 */
@SuppressWarnings({"cast", "unchecked"})
public class ArgsInvalidPOJO
{
   /* Object field1 */
   public Object field1Before4;
   public Object field1Before5;
   
   /* String field2 */
   public String field2NonexistentAdvice;
   public String field2Finally2;
   
   /* int field3 */
   public int field3Before3;
   public int field3After4;
   public int field3After5;

   /* boolean field4 */
   public static boolean field4Before4;
   public static boolean field4Before5;
   public static boolean field4After4;
   public static boolean field4After5;
   public static boolean field4Finally2;
   
   /* List<SuperValue> field5 */
   public List<SuperValue> field5Before7;
   public List<SuperValue> field5Before8;
   public List<SuperValue> field5Around5;
   public List<SuperValue> field5Around7;
   public List<SuperValue> field5Around8;
   public List<SuperValue> field5After5;
   public List<SuperValue> field5After7;
   public List<SuperValue> field5After8;
   public List<SuperValue> field5Throwing2;
   public List<SuperValue> field5Throwing7;
   public List<SuperValue> field5Throwing8;
   public List<SuperValue> field5Finally5;
   public List<SuperValue> field5Finally7;
   public List<SuperValue> field5Finally8;
   
   public ArgsInvalidPOJO() {}
   
   /* Constructor Exceution */
   
   // intercepted by advice ArgAspectGenerics.before7
   public ArgsInvalidPOJO(List<SuperValue> arg1, boolean arg2){}

   // intercepted by advice ArgAspectGenerics.before8
   public ArgsInvalidPOJO(List<SuperValue> arg1, char arg2){}
   
   // intercepted by advice ArgAspectGenerics.around5_
   public ArgsInvalidPOJO(List<SuperValue> arg1, byte arg2){}
   
   // intercepted by advice ArgAspectGenerics.around7_
   public ArgsInvalidPOJO(List<SuperValue> arg1, short arg2){}
   
   // intercepted by advice ArgAspectGenerics.around8_
   public ArgsInvalidPOJO(List<SuperValue> arg1, int arg2){}
      
   // intercepted by advice ArgAspectGenerics.after5
   public ArgsInvalidPOJO(List<SuperValue> arg1, long arg2){}
   
   // intercepted by advice ArgAspectGenerics.after7
   public ArgsInvalidPOJO(List<SuperValue> arg1, float arg2){}
   
   // intercepted by advice ArgAspectGenerics.after8
   public ArgsInvalidPOJO(List<SuperValue> arg1, double arg2){}
   
   // intercepted by advice ArgAspectGenerics.throwing2
   public ArgsInvalidPOJO(List<SuperValue> arg1, boolean arg2, boolean arg3){}
   
   // intercepted by advice ArgAspectGenerics.throwing7
   public ArgsInvalidPOJO(List<SuperValue> arg1, boolean arg2, char arg3){}
   
   // intercepted by advice ArgAspectGenerics.throwing8
   public ArgsInvalidPOJO(List<SuperValue> arg1, boolean arg2, byte arg3){}
   
   // intercepted by advice ArgAspectGenerics.finally5
   public ArgsInvalidPOJO(List<SuperValue> arg1, boolean arg2, short arg3){}
   
   // intercepted by advice ArgAspectGenerics.finally7
   public ArgsInvalidPOJO(List<SuperValue> arg1, boolean arg2, int arg3){}
   
   // intercepted by advice ArgAspectGenerics.finally8
   public ArgsInvalidPOJO(List<SuperValue> arg1, boolean arg2, long arg3){}
   
   /* Construction */
   
   // intercepted by advice ArgAspectGenerics.before7
   public ArgsInvalidPOJO(boolean arg1, List<SuperValue> arg2){}

   // intercepted by advice ArgAspectGenerics.before8
   public ArgsInvalidPOJO(char arg1, List<SuperValue> arg2){}
   
   // intercepted by advice ArgAspectGenerics.around5_
   public ArgsInvalidPOJO(byte arg1, List<SuperValue> arg2){}
   
   // intercepted by advice ArgAspectGenerics.around7_
   public ArgsInvalidPOJO(short arg1, List<SuperValue> arg2){}
   
   // intercepted by advice ArgAspectGenerics.around8_
   public ArgsInvalidPOJO(int arg1, List<SuperValue> arg2){}
      
   // intercepted by advice ArgAspectGenerics.after5
   public ArgsInvalidPOJO(long arg1, List<SuperValue> arg2){}
   
   // intercepted by advice ArgAspectGenerics.after7
   public ArgsInvalidPOJO(float arg1, List<SuperValue> arg2){}
   
   // intercepted by advice ArgAspectGenerics.after8
   public ArgsInvalidPOJO(double arg1, List<SuperValue> arg2){}
   
   // intercepted by advice ArgAspectGenerics.throwing2
   public ArgsInvalidPOJO(boolean arg1, boolean arg2, List<SuperValue> arg3){}
   
   // intercepted by advice ArgAspectGenerics.throwing7
   public ArgsInvalidPOJO(boolean arg1, char arg2, List<SuperValue> arg3){}
   
   // intercepted by advice ArgAspectGenerics.throwing8
   public ArgsInvalidPOJO(boolean arg1, byte arg2, List<SuperValue> arg3){}
   
   // intercepted by advice ArgAspectGenerics.finally5
   public ArgsInvalidPOJO(boolean arg1, short arg2, List<SuperValue> arg3){}
   
   // intercepted by advice ArgAspectGenerics.finally7
   public ArgsInvalidPOJO(boolean arg1, int arg2, List<SuperValue> arg3){}
   
   // intercepted by advice ArgAspectGenerics.finally8
   public ArgsInvalidPOJO(boolean arg1, long arg2, List<SuperValue> arg3){}
   
   
   /* bunch1(int, double, float, String, int) */
   
   public int bunch1Around1(int x, double y, float z, String str, int q)
   {
      return x + (int) y + (int) z + q;
   }
   
   public int bunch1Around2(int x, double y, float z, String str, int q)
   {
      return x + (int) y + (int) z + q;
   }
   
   public int bunch1Around3(int x, double y, float z, String str, int q)
   {
      return x + (int) y + (int) z + q;
   }
   
   public int bunch1After2(int x, double y, float z, String str, int q)
   {
      return x + (int) y + (int) z + q;
   }
   
   public int bunch1After3(int x, double y, float z, String str, int q)
   {
      return x + (int) y + (int) z + q;
   }
   
   public int bunch1After5(int x, double y, float z, String str, int q)
   {
      return x + (int) y + (int) z + q;
   }
   
   /* bunch2(int, double, float, int) */
   
   public int bunch2Before3(int x, double y, float z, int q)
   {
      return x + (int) y * 2 + (int) z + q;
   }
   
   public int bunch2After1(int x, double y, float z, int q)
   {
      return x + (int) y * 2 + (int) z + q;
   }
   
   public int bunch2After2(int x, double y, float z, int q)
   {
      return x + (int) y * 2 + (int) z + q;
   }
   
   public int bunch2After3(int x, double y, float z, int q)
   {
      return x + (int) y * 2 + (int) z + q;
   }
   
   public int bunch2After5(int x, double y, float z, int q)
   {
      return x + (int) y * 2 + (int) z + q;
   }
   
   /* bunch3(int, double, float, int) */
   
   public int bunch3Before3(int x, double y, float z, int q)
   {
      return x + (int) y * 3 + (int) z + q;
   }
   
   public int bunch3After1(int x, double y, float z, int q)
   {
      return x + (int) y * 3 + (int) z + q;
   }
   
   public int bunch3After2(int x, double y, float z, int q)
   {
      return x + (int) y * 3 + (int) z + q;
   }
   
   public int bunch3After3(int x, double y, float z, int q)
   {
      return x + (int) y * 3 + (int) z + q;
   }
   
   public int bunch3After5(int x, double y, float z, int q)
   {
      return x + (int) y * 3 + (int) z + q;
   }
   
   /* bunch4(int, double, float, int) */
   
   public int bunch4Before3(int x, double y, float z, int q)
   {
      return x + (int) y * 4 + (int) z + q;
   }
   
   /* bunch5(int, double, float, int) */
   
   public int bunch5Before3(int x, double y, float z, int q)
   {
      return x + (int) y * 5 + (int) z + q;
   }
   
   /* bunch6(int, double, float, int) */
   
   public int bunch6After1(int x, double y, float z, int q)
   {
      return x + (int) y * 6 + (int) z + q;
   }
   
   public int bunch6After2(int x, double y, float z, int q)
   {
      return x + (int) y * 6 + (int) z + q;
   }
   
   public int bunch6After3(int x, double y, float z, int q)
   {
      return x + (int) y * 6 + (int) z + q;
   }
   
   public int bunch6After5(int x, double y, float z, int q)
   {
      return x + (int) y * 6 + (int) z + q;
   }
   
   /* bunch7(int, double, float, int) */
   
   public int bunch7After1(int x, double y, float z, int q)
   {
      return x + (int) y * 7 + (int) z + q;
   }
   
   public int bunch7After2(int x, double y, float z, int q)
   {
      return x + (int) y * 7 + (int) z + q;
   }
   
   public int bunch7After3(int x, double y, float z, int q)
   {
      return x + (int) y * 7 + (int) z + q;
   }
   
   public int bunch7After5(int x, double y, float z, int q)
   {
      return x + (int) y * 7 + (int) z + q;
   }
   
   /* bunch8(int, double, float, int) */
   
   public int bunch8After1(int x, double y, float z, int q)
   {
      return x + (int) y * 8 + (int) z + q;
   }
   
   public int bunch8After2(int x, double y, float z, int q)
   {
      return x + (int) y * 8 + (int) z + q;
   }
   
   public int bunch8After3(int x, double y, float z, int q)
   {
      return x + (int) y * 8 + (int) z + q;
   }
   
   public int bunch8After5(int x, double y, float z, int q)
   {
      return x + (int) y * 8 + (int) z + q;
   }
   
   /* bunch9(int, double, float, int) */
   
   public int bunch9Before6(int x, double y, float z, int q)
   {
      return x + (int) y * 9 + (int) z + q;
   }
   
   public int bunch9Around7(int x, double y, float z, int q)
   {
      return x + (int) y * 9 + (int) z + q;
   }
   
   public int bunch9After1(int x, double y, float z, int q)
   {
      return x + (int) y * 9 + (int) z + q;
   }
   
   public int bunch9After2(int x, double y, float z, int q)
   {
      return x + (int) y * 9 + (int) z + q;
   }
   
   public int bunch9After3(int x, double y, float z, int q)
   {
      return x + (int) y * 9 + (int) z + q;
   }
   
   public int bunch9After5(int x, double y, float z, int q)
   {
      return x + (int) y * 9 + (int) z + q;
   }
   
   /* method2(String, int, boolean ArgsPOJO[]) */
   
   public void method2Finally2(String param1, int param2, boolean param3, ArgsPOJO[] param4)
   {}
   
   /* method4(String, int, boolean, ArgsPOJO[]) */
   
   public void method4Finally2(String param1, int param2, boolean param3, ArgsPOJO[] param4)
   {}
   
   /* method6(short, long) */
   
   public void method6Finally2(short param1, long param2) throws POJOException
   {
      throw new POJOException();
   }
   
   /* method8() */
   
   public void method8Finally2() throws POJOException
   {
      this.method6Finally2((short) -4, (long) 4);
   }
   
   /* method9(Interface) */
   
   public void method9Before4(Interface param) throws POJOException {}
   
   public void method9Before5(Interface param) throws POJOException {}
   
   public void method9Around4(Interface param) throws POJOException {}
   
   public void method9Around5(Interface param) throws POJOException {}
   
   public void method9After4(Interface param) throws POJOException {}
   
   public void method9After5(Interface param) throws POJOException {}
   
   public void method9Throwing4(Interface param) throws POJOException {}
   
   public void method9Throwing5(Interface param) throws POJOException {}
   
   public void method9Finally4(Interface param) throws POJOException {}
   
   public void method9Finally5(Interface param) throws POJOException {}
   
   /* method10(Interface) */
   
   public void method10Before4(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10Before5(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10Around4(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10Around5(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10After4(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10After5(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10Throwing4(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10Throwing5(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10Finally4(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method10Finally5(Interface param) throws POJOException
   {
      throw new POJOException();
   }
   
   /* method11(String, Collection) */
   
   public void method11Before2(String param1, Collection param2) {}
   
   public void method11Around2(String param1, Collection param2) {}
   
   public void method11After2(String param1, Collection param2) {}
   
   public void method11Throwing2(String param1, Collection param2) {}
   
   public void method11Finally2(String param1, Collection param2) {}
   
   /* method12(String, Collection) */
   
   public void method12Before2(String param1, Collection param2) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method12Around2(String param1, Collection param2) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method12After2(String param1, Collection param2) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method12Throwing2(String param1, Collection param2) throws POJOException
   {
      throw new POJOException();
   }
   
   public void method12Finally2(String param1, Collection param2) throws POJOException
   {
      throw new POJOException();
   }
   
   /* methodGenericsExecution1(List<SuperValue>) */
   
   public void methodGenericsExecutionBefore7(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionBefore8(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionAround5(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionAround7(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionAround8(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionAfter5(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionAfter7(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionAfter8(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionThrowing2(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionThrowing7(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionThrowing8(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionFinally5(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionFinally7(List<SuperValue> arg) {}
   
   public void methodGenericsExecutionFinally8(List<SuperValue> arg) {}
   
   /* methodGenericsCall1(List<SuperValue>) */
   
   public void methodGenericsCallBefore7(List<SuperValue> arg) {}
   
   public void methodGenericsCallBefore8(List<SuperValue> arg) {}
   
   public void methodGenericsCallAround5(List<SuperValue> arg) {}
   
   public void methodGenericsCallAround7(List<SuperValue> arg) {}
   
   public void methodGenericsCallAround8(List<SuperValue> arg) {}
   
   public void methodGenericsCallAfter5(List<SuperValue> arg) {}
   
   public void methodGenericsCallAfter7(List<SuperValue> arg) {}
   
   public void methodGenericsCallAfter8(List<SuperValue> arg) {}
   
   public void methodGenericsCallThrowing2(List<SuperValue> arg) {}
   
   public void methodGenericsCallThrowing7(List<SuperValue> arg) {}
   
   public void methodGenericsCallThrowing8(List<SuperValue> arg) {}
   
   public void methodGenericsCallFinally5(List<SuperValue> arg) {}
   
   public void methodGenericsCallFinally7(List<SuperValue> arg) {}
   
   public void methodGenericsCallFinally8(List<SuperValue> arg) {}
}