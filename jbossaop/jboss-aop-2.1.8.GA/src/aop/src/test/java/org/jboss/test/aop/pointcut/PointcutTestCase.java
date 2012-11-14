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
package org.jboss.test.aop.pointcut;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.jboss.aop.AspectManager;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.pointcut.PointcutExpression;
import org.jboss.aop.pointcut.PointcutStats;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.PointcutExpressionParser;
import org.jboss.aop.pointcut.ast.PointcutExpressionParserVisitor;
import org.jboss.aop.util.BindingClassifier;
import org.jboss.test.aop.AOPTestWithSetup;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests an annotated introduction
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 45977 $
 */
@SuppressWarnings({"unchecked"})
public class PointcutTestCase extends AOPTestWithSetup
{
   public static Test suite()
   {
      TestSuite suite = new TestSuite("PointcutTestCase");
      suite.addTestSuite(PointcutTestCase.class);
      return suite;
   }

   public PointcutTestCase(String name)
   {
      super(name);
   }

   public void testGoodMethodPointcuts() throws Exception
   {
      Executor e = new Executor();
      e.parseGoodPointcut("execution(* *->*())");
      e.parseGoodPointcut("execution(public * *->*())");
      e.parseGoodPointcut("execution(public static * *->*())");
      e.parseGoodPointcut("execution(protected static * *->*())");
      e.parseGoodPointcut("execution(private static * *->*())");
      e.parseGoodPointcut("execution(!public !static * *->*())");
      e.parseGoodPointcut("execution(!protected !static * *->*())");
      e.parseGoodPointcut("execution(!private !static * *->*())");

      e.parseGoodPointcut("execution(@Ann @Ann->@Ann())");
      e.parseGoodPointcut("execution(@org.acme.Ann @org.acme.Ann->@org.acme.Ann(int))");
      e.parseGoodPointcut("execution(* @org.acme.Ann->@org.acme.Ann(int,..,long))");
      e.parseGoodPointcut("execution(* @org.acme.Ann->@org.acme.Ann(..))");

      e.parseGoodPointcut("execution(* *->*(org.acme.Clazz))");
      e.parseGoodPointcut("execution(* *->*(Clazz))");
      e.parseGoodPointcut("execution(* *->*(org.acme.Clazz, Clazz))");
      e.parseGoodPointcut("execution(* *->*(org.acme.Clazz, int, Clazz))");
      e.parseGoodPointcut("execution(* *->*(org.acme.Clazz, .., Clazz))");

      e.parseGoodPointcut("execution(* *->*(@org.acme.Ann))");
      e.parseGoodPointcut("execution(* *->*(@Ann))");
      e.parseGoodPointcut("execution(* *->*(@org.acme.Ann, @Ann))");
      e.parseGoodPointcut("execution(* *->*(@org.acme.Ann, int, @Ann))");
      e.parseGoodPointcut("execution(* *->*(@org.acme.Ann, .., @Ann))");

      e.parseGoodPointcut("execution(* *->*(org.acme.*))");
      e.parseGoodPointcut("execution(* *->*(*))");
      e.parseGoodPointcut("execution(* *->*(org.acme.*, Clazz))");
      e.parseGoodPointcut("execution(* *->*(org.acme.Clazz, int, Clazz))");
      e.parseGoodPointcut("execution(* *->*(org.acme.Clazz, .., Clazz))");

      e.parseGoodPointcut("execution(* *->*(@org.acme.*))");
      e.parseGoodPointcut("execution(* *->*(@*))");
      e.parseGoodPointcut("execution(* *->*(@org.acme.*, @*))");
      e.parseGoodPointcut("execution(* *->*(@org.acme.*, int, @*))");
      e.parseGoodPointcut("execution(* *->*(@org.acme.*, .., @*))");

      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.Ann}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@Ann}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.Ann}, $instanceof{@Ann}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.Ann}, int, $instanceof{@Ann}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.Ann}, .., $instanceof{@Ann}))");

      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.*}, $instanceof{@*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.*}, int, $instanceof{@*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@org.acme.*}, .., $instanceof{@*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@org.*.Clazz}, .., $instanceof{@*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{@*.acme.Clazz}, .., $instanceof{@*}))");

      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.Clazz}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{Clazz}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.Clazz}, $instanceof{Clazz}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.Clazz}, int, $instanceof{Clazz}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.Clazz}, .., $instanceof{Clazz}))");

      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.*}, $instanceof{*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.*}, int, $instanceof{*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{org.acme.*}, .., $instanceof{*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{org.*.Clazz}, .., $instanceof{*}))");
      e.parseGoodPointcut("execution(* *->*($instanceof{*.acme.Clazz}, .., $instanceof{*}))");

      e.parseGoodPointcut("execution($instanceof{a} $instanceof{Clazz}->$implements{Clazz}())");
      e.parseGoodPointcut("execution($instanceof{a.b} $instanceof{org.acme.Clazz}->$implements{org.acme.Clazz}())");
      e.parseGoodPointcut("execution($instanceof{@a.Ann} $instanceof{Clazz}->$implements{org.acme.Clazz}())");
      e.parseGoodPointcut("execution($instanceof{@Ann} $instanceof{org.acme.Clazz}->$implements{Clazz}())");
      
      e.parseGoodPointcut("execution($instanceof{a} $instanceof{Clazz}->$implementing{Clazz}())");
      e.parseGoodPointcut("execution($instanceof{a.b} $instanceof{org.acme.Clazz}->$implementing{org.acme.Clazz}())");
      e.parseGoodPointcut("execution($instanceof{@a.Ann} $instanceof{Clazz}->$implementing{org.acme.Clazz}())");
      e.parseGoodPointcut("execution($instanceof{@Ann} $instanceof{org.acme.Clazz}->$implementing{Clazz}())");
      
      e.parseGoodPointcut("execution(* org..->*())");
      e.parseGoodPointcut("execution(* org.acme..->*())");
      e.parseGoodPointcut("execution(* org.*..->*())");
      e.parseGoodPointcut("execution(* *.acme..->*())");
      
      checkFailures(e.failures);
   }
   
   public void testGoodConstructorPointcuts() throws Exception
   {
      Executor e = new Executor();

      e.parseGoodPointcut("execution(*->new())");
      e.parseGoodPointcut("execution(public *->new())");
      e.parseGoodPointcut("execution(public *->new())");
      e.parseGoodPointcut("execution(protected *->new())");
      e.parseGoodPointcut("execution(private *->new())");

      //These should work
//      e.parseGoodPointcut("execution(!public *->new())");
//      e.parseGoodPointcut("execution(!protected *->new())");
//      e.parseGoodPointcut("execution(!private *->new())");

      e.parseGoodPointcut("execution(@Ann->new())");
      e.parseGoodPointcut("execution(@org.acme.Ann->new(int))");
      e.parseGoodPointcut("execution(@org.acme.Ann->@org.acme.Ann(int,..,long))");
      e.parseGoodPointcut("execution(@org.acme.Ann->@Ann(..))");

      e.parseGoodPointcut("execution(*->new(org.acme.Clazz))");
      e.parseGoodPointcut("execution(*->new(Clazz))");
      e.parseGoodPointcut("execution(*->new(org.acme.Clazz, Clazz))");
      e.parseGoodPointcut("execution(*->new(org.acme.Clazz, int, Clazz))");
      e.parseGoodPointcut("execution(*->new(org.acme.Clazz, .., Clazz))");

      e.parseGoodPointcut("execution(*->new(@org.acme.Ann))");
      e.parseGoodPointcut("execution(*->new(@Ann))");
      e.parseGoodPointcut("execution(*->new(@org.acme.Ann, @Ann))");
      e.parseGoodPointcut("execution(*->new(@org.acme.Ann, int, @Ann))");
      e.parseGoodPointcut("execution(*->new(@org.acme.Ann, .., @Ann))");

      e.parseGoodPointcut("execution(*->new(org.acme.*))");
      e.parseGoodPointcut("execution(*->new(*))");
      e.parseGoodPointcut("execution(*->new(org.acme.*, Clazz))");
      e.parseGoodPointcut("execution(*->new(org.acme.Clazz, int, Clazz))");
      e.parseGoodPointcut("execution(*->new(org.acme.Clazz, .., Clazz))");

      e.parseGoodPointcut("execution(*->new(@org.acme.*))");
      e.parseGoodPointcut("execution(*->new(@*))");
      e.parseGoodPointcut("execution(*->new(@org.acme.*, @*))");
      e.parseGoodPointcut("execution(*->new(@org.acme.*, int, @*))");
      e.parseGoodPointcut("execution(*->new(@org.acme.*, .., @*))");

      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.Ann}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@Ann}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.Ann}, $instanceof{@Ann}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.Ann}, int, $instanceof{@Ann}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.Ann}, .., $instanceof{@Ann}))");

      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.*}, $instanceof{@*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.*}, int, $instanceof{@*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@org.acme.*}, .., $instanceof{@*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@org.*.Clazz}, .., $instanceof{@*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{@*.acme.Clazz}, .., $instanceof{@*}))");

      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.Clazz}))");
      e.parseGoodPointcut("execution(*->new($instanceof{Clazz}))");
      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.Clazz}, $instanceof{Clazz}))");
      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.Clazz}, int, $instanceof{Clazz}))");
      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.Clazz}, .., $instanceof{Clazz}))");

      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.*}, $instanceof{*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.*}, int, $instanceof{*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{org.acme.*}, .., $instanceof{*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{org.*.Clazz}, .., $instanceof{*}))");
      e.parseGoodPointcut("execution(*->new($instanceof{*.acme.Clazz}, .., $instanceof{*}))");
     
      e.parseGoodPointcut("execution(org..->new())");
      e.parseGoodPointcut("execution(org.acme..->new())");
      e.parseGoodPointcut("execution(org.*..->new())");
      e.parseGoodPointcut("execution(*.acme..->new())");
      
      checkFailures(e.failures);
   }
   
   public void testGoodConstructionPointcuts() throws Exception
   {
      Executor e = new Executor();

      e.parseGoodPointcut("construction(*->new())");
      e.parseGoodPointcut("construction(public *->new())");
      e.parseGoodPointcut("construction(public *->new())");
      e.parseGoodPointcut("construction(protected *->new())");
      e.parseGoodPointcut("construction(private *->new())");

      //These should work
//      e.parseGoodPointcut("construction(!public *->new())");
//      e.parseGoodPointcut("construction(!protected *->new())");
//      e.parseGoodPointcut("construction(!private *->new())");

      e.parseGoodPointcut("construction(@Ann->new())");
      e.parseGoodPointcut("construction(@org.acme.Ann->new(int))");
      e.parseGoodPointcut("construction(@org.acme.Ann->@org.acme.Ann(int,..,long))");
      e.parseGoodPointcut("construction(@org.acme.Ann->@Ann(..))");

      e.parseGoodPointcut("construction(*->new(org.acme.Clazz))");
      e.parseGoodPointcut("construction(*->new(Clazz))");
      e.parseGoodPointcut("construction(*->new(org.acme.Clazz, Clazz))");
      e.parseGoodPointcut("construction(*->new(org.acme.Clazz, int, Clazz))");
      e.parseGoodPointcut("construction(*->new(org.acme.Clazz, .., Clazz))");

      e.parseGoodPointcut("construction(*->new(@org.acme.Ann))");
      e.parseGoodPointcut("construction(*->new(@Ann))");
      e.parseGoodPointcut("construction(*->new(@org.acme.Ann, @Ann))");
      e.parseGoodPointcut("construction(*->new(@org.acme.Ann, int, @Ann))");
      e.parseGoodPointcut("construction(*->new(@org.acme.Ann, .., @Ann))");

      e.parseGoodPointcut("construction(*->new(org.acme.*))");
      e.parseGoodPointcut("construction(*->new(*))");
      e.parseGoodPointcut("construction(*->new(org.acme.*, Clazz))");
      e.parseGoodPointcut("construction(*->new(org.acme.Clazz, int, Clazz))");
      e.parseGoodPointcut("construction(*->new(org.acme.Clazz, .., Clazz))");

      e.parseGoodPointcut("construction(*->new(@org.acme.*))");
      e.parseGoodPointcut("construction(*->new(@*))");
      e.parseGoodPointcut("construction(*->new(@org.acme.*, @*))");
      e.parseGoodPointcut("construction(*->new(@org.acme.*, int, @*))");
      e.parseGoodPointcut("construction(*->new(@org.acme.*, .., @*))");

      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.Ann}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@Ann}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.Ann}, $instanceof{@Ann}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.Ann}, int, $instanceof{@Ann}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.Ann}, .., $instanceof{@Ann}))");

      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.*}, $instanceof{@*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.*}, int, $instanceof{@*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@org.acme.*}, .., $instanceof{@*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@org.*.Clazz}, .., $instanceof{@*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{@*.acme.Clazz}, .., $instanceof{@*}))");

      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.Clazz}))");
      e.parseGoodPointcut("construction(*->new($instanceof{Clazz}))");
      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.Clazz}, $instanceof{Clazz}))");
      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.Clazz}, int, $instanceof{Clazz}))");
      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.Clazz}, .., $instanceof{Clazz}))");

      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.*}, $instanceof{*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.*}, int, $instanceof{*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{org.acme.*}, .., $instanceof{*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{org.*.Clazz}, .., $instanceof{*}))");
      e.parseGoodPointcut("construction(*->new($instanceof{*.acme.Clazz}, .., $instanceof{*}))");
     
      e.parseGoodPointcut("construction(org..->new())");
      e.parseGoodPointcut("construction(org.acme..->new())");
      e.parseGoodPointcut("construction(org.*..->new())");
      e.parseGoodPointcut("construction(*.acme..->new())");
      
      checkFailures(e.failures);
   }
   
   public void testGoodFieldPointcuts() throws Exception
   {
      Executor e = new Executor();
      e.parseGoodPointcut("field(* *->*)");
      e.parseGoodPointcut("field(public * *->*)");
      e.parseGoodPointcut("field(public static * *->*)");
      e.parseGoodPointcut("field(protected static * *->*)");
      e.parseGoodPointcut("field(private static * *->*)");
      e.parseGoodPointcut("field(!public !static * *->*)");
      e.parseGoodPointcut("field(!protected !static * *->*)");
      e.parseGoodPointcut("field(!private !static * *->*)");

      e.parseGoodPointcut("field(@Ann @Ann->@Ann)");
      e.parseGoodPointcut("field(@org.acme.Ann @org.acme.Ann->@org.acme.Ann)");

      e.parseGoodPointcut("field($instanceof{a} $instanceof{Clazz}->x)");
      e.parseGoodPointcut("field($instanceof{a.b} $instanceof{org.acme.Clazz}->y)");
      e.parseGoodPointcut("field($instanceof{@a.Ann} $instanceof{Clazz}->x)");
      e.parseGoodPointcut("field($instanceof{@Ann} $instanceof{org.acme.Clazz}->x)");
          
      e.parseGoodPointcut("field(* org..->*)");
      e.parseGoodPointcut("field(* org.acme..->*)");
      e.parseGoodPointcut("field(* org.*..->*)");
      e.parseGoodPointcut("field(* *.acme..->*)");
      
      checkFailures(e.failures);
   }

   public void testGoodCompsitePointcuts() throws Exception
   {
      Executor e = new Executor();

      e.parseGoodPointcut("call(* *->*()) AND within(*)");
      
      e.parseGoodPointcut("call(org.acme.Clazz->new()) AND within(org.acme.Clazz)");
      e.parseGoodPointcut("call(Clazz->new()) AND within(org.acme.Clazz)");
      e.parseGoodPointcut("call(Clazz->new()) AND within(org.acme..)");
      e.parseGoodPointcut("call(org.acme..->new()) AND within(*.acme..)");
      
      e.parseGoodPointcut("call(int org.acme.Clazz->method()) AND within(org.acme.Clazz)");
      e.parseGoodPointcut("call(int Clazz->method()) AND within(Clazz)");
      e.parseGoodPointcut("call(int Clazz->method()) AND within(*.acme..)");
      e.parseGoodPointcut("call(int org.acme..->method()) AND within(org.acme..)");
      
      e.parseGoodPointcut("call(org.acme.Clazz->new()) AND withincode(org.acme.Clazz->new(int, java.lang.String[]))");
      e.parseGoodPointcut("call(Clazz->new()) AND withincode(Clazz->new(.., int))");
      e.parseGoodPointcut("call(org.acme..->new()) AND withincode(org.acme..->new(int, java.lang.String[]))");
      e.parseGoodPointcut("call(Clazz->new()) AND withincode(*.acme..->new(.., int))");
      
      
      e.parseGoodPointcut("call(int org.acme.Clazz->method()) AND withincode(int[] org.acme.Clazz->method(..))");
      e.parseGoodPointcut("call(int Clazz->method()) AND withincode(* Clazz->method(long))");
      e.parseGoodPointcut("call(int org.acme..->method()) AND withincode(int[] org.acme..->method(..))");
      e.parseGoodPointcut("call(int Clazz->method()) AND withincode(* *acme..->method(long))");
      
      checkFailures(e.failures);
   }
   
   public void testBadPointcuts() throws Exception
   {
      Executor e = new Executor();

      //Should maybe be implemented?
      e.parseBadPointcut("execution(org.acme.. *->*())");
      e.parseBadPointcut("execution(* *->*(org.acme..))");
      e.parseBadPointcut("execution(* *->*(org.acme..))");

      //Should maybe be implemented?
      e.parseBadPointcut("execution(*->new(org.acme..))");

      checkFailures(e.failures);
   }
   
   public void testPointcutClassification() throws Exception
   {
      createAndCheckPointcut("execution(void org.acme.Class->method())", true, false, false, false, false, false, false, false);
      createAndCheckPointcut("execution(org.acme.Class->new())", false, true, false, false, false, false, false, false);
      createAndCheckPointcut("field(int org.acme.Class->fld)", false, false, true, true, false, false, false, false);
      createAndCheckPointcut("get(int org.acme.Class->fld)", false, false, true, false, false, false, false, false);
      createAndCheckPointcut("set(int org.acme.Class->fld)", false, false, false, true, false, false, false, false);
      createAndCheckPointcut("construction(org.acme.Class->new())", false, false, false, false, true, false, false, false);
      createAndCheckPointcut("call(int org.acme.Class->method())", false, false, false, false, false, true, false, false);
      createAndCheckPointcut("call(org.acme.Class->new())", false, false, false, false, false, false, true, false);
      createAndCheckPointcut("call(org.acme.Class->new())", false, false, false, false, false, false, true, false);
      createAndCheckPointcut("all(org.acme.Class)", true, true, true, true, false, false, false, false);
      createAndCheckPointcut("call(org.acme.Class->new()) AND withincode(* org.acme.Class->method())", false, false, false, false, false, false, true, true);
      createAndCheckPointcut("execution(org.acme.Class->new()) OR field(* org.acme.Class->fld)", false, true, true, true, false, false, false, false);
      
      //Check that named pointcuts work
      AspectManager manager = AspectManager.instance();
      PointcutExpression pointcut1 = new PointcutExpression("METHOD", "execution(void org.acme.Class->method())");
      manager.addPointcut(pointcut1);
      PointcutExpression pointcut2 = new PointcutExpression("CONSTRUCTOR", "execution(org.acme.Class->new())");
      manager.addPointcut(pointcut2);
      PointcutExpression pointcut3 = new PointcutExpression("COMPOSITION", "METHOD OR CONSTRUCTOR");
      manager.addPointcut(pointcut3);
      PointcutStats stats = pointcut3.getStats();
      assertTrue(stats.isMethodExecution());
      assertTrue(stats.isConstructorExecution());
      assertFalse(stats.isGet());
      assertFalse(stats.isSet());
      assertFalse(stats.isMethodCall());
      assertFalse(stats.isConstructorCall());
      assertFalse(stats.isConstruction());
      assertFalse(stats.isWithincode());

   }
   
   private void createAndCheckPointcut(
         String expression, 
         boolean methodExecution, 
         boolean constructorExecution, 
         boolean get,
         boolean set,
         boolean construction,
         boolean methodCall,
         boolean constructorCall,
         boolean withincode) throws Exception
   {
      AspectManager manager = AspectManager.instance();
      PointcutExpression pointcut = new PointcutExpression("TEST", expression);
      manager.addPointcut(pointcut);
      PointcutStats stats = pointcut.getStats(); 
      AdviceBinding binding = new AdviceBinding(expression, null);
      manager.addBinding(binding);
      
      assertEquals(methodExecution, stats.isMethodExecution());
      assertEquals(methodExecution, BindingClassifier.isMethodExecution(binding));
      assertEquals(methodExecution, BindingClassifier.isMethodExecution(pointcut));
      
      
      assertEquals(constructorExecution, stats.isConstructorExecution());
      assertEquals(constructorExecution, BindingClassifier.isConstructorExecution(binding));
      assertEquals(constructorExecution, BindingClassifier.isConstructorExecution(pointcut));
      
      assertEquals(get, stats.isGet());
      assertEquals(get, BindingClassifier.isGet(binding));
      assertEquals(get, BindingClassifier.isGet(pointcut));
      
      assertEquals(set, stats.isSet());
      assertEquals(set, BindingClassifier.isSet(binding));
      assertEquals(set, BindingClassifier.isSet(pointcut));
      
      assertEquals(construction, stats.isConstruction());
      assertEquals(construction, BindingClassifier.isConstruction(binding));
      assertEquals(construction, BindingClassifier.isConstruction(pointcut));
      
      assertEquals(methodCall, stats.isMethodCall());
      assertEquals(methodCall, BindingClassifier.isMethodCall(binding));
      assertEquals(methodCall, BindingClassifier.isMethodCall(pointcut));
      
      assertEquals(constructorCall, stats.isConstructorCall());
      assertEquals(constructorCall, BindingClassifier.isConstructorCall(binding));
      assertEquals(constructorCall, BindingClassifier.isConstructorCall(pointcut));
      
      assertEquals(withincode, stats.isWithincode());
      assertEquals(withincode, BindingClassifier.isWithincode(binding));
      assertEquals(withincode, BindingClassifier.isWithincode(pointcut));
      
      if (methodExecution || constructorExecution)
      {
         assertTrue(stats.isExecution());
      }
      else
      {
         assertFalse(stats.isExecution());
      }
      
      if (methodCall || constructorCall)
      {
         assertTrue(stats.isCall());
      }
      else
      {
         assertFalse(stats.isCall());
      }
      manager.removePointcut("TEST");
   }
   
   
   private void checkFailures(ArrayList failures)
   {
      StringBuffer buf = new StringBuffer();
      if (failures.size() > 0)
      {
         buf.append("======= Did not pass validation ===========\n\n");
         for (Iterator it = failures.iterator() ; it.hasNext() ; )
         {
            buf.append((String)it.next());
            buf.append("\n");
         }
         
         fail(buf.toString());
      }
   }
   
   private static class Executor
   {
      ArrayList failures = new ArrayList();

      private void parseGoodPointcut(String pointcut) throws Exception
      {
         parsePointcut(pointcut, false);
      }

      private void parseBadPointcut(String pointcut) throws Exception
      {
         parsePointcut(pointcut, true);
      }

      private void parsePointcut(String pointcut, boolean expectFailure) throws Exception
      {
         StringReader reader = new StringReader(pointcut);
         PointcutExpressionParser t = new PointcutExpressionParser(reader);
   
         try
         {
            ASTStart n = t.Start();
            PointcutExpressionParserVisitor v = new EmptyPointcutVisitor();
            n.jjtAccept(v, null);
            if (expectFailure)
            {
               failures.add("- Should not have passed: " + pointcut);
            }
         }
         catch (Exception e)
         {
            if (!expectFailure)
            {
               failures.add("+ Should have passed: " + pointcut);
               System.out.println(pointcut);
               e.printStackTrace(System.out);
            }
         }
      }
   }
}

