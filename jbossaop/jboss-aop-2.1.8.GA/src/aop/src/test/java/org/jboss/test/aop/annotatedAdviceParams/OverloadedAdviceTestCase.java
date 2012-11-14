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

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.aop.AOPTestWithSetup;

/**
 * Tests the selection of advice methods when these are overloaded.
 * 
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
@SuppressWarnings({"unused"})
public class OverloadedAdviceTestCase extends AOPTestWithSetup
{
   private OverloadedAdvicePOJO pojo;
   
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("OverloadedAdviceTestCase");
      suite.addTestSuite(OverloadedAdviceTestCase.class);
      return suite;
   }
   
   public OverloadedAdviceTestCase(String name)
   {
      super(name);
   }
   
   public void setUp() throws Exception
   {
      super.setUp();
      this.pojo = new OverloadedAdvicePOJO();
   }

   public void testBefore()
   {
      // clear all relevant aspect fields
      OverloadedBeforeAspect.clear();
      // execute the join point
      pojo.text = "test2";
      // check aspect fields
      assertEquals("FieldAccess,String", OverloadedBeforeAspect.before1);
      assertEquals("FieldAccess,Object", OverloadedBeforeAspect.before2);
      assertEquals("JoinPointBean,String", OverloadedBeforeAspect.before3);
      assertEquals("JoinPointBean,Object", OverloadedBeforeAspect.before4);
      assertEquals("Object,String", OverloadedBeforeAspect.before5);
      assertEquals("Object,Object", OverloadedBeforeAspect.before6);
      assertEquals("FieldAccess,Object[]", OverloadedBeforeAspect.before7);
      assertEquals("JoinPointBean,Object[]", OverloadedBeforeAspect.before8);
      assertEquals("Object,Object[]", OverloadedBeforeAspect.before9);
      assertEquals("FieldAccess", OverloadedBeforeAspect.before10);
      assertEquals("JoinPointBean", OverloadedBeforeAspect.before11);
      assertEquals("Object", OverloadedBeforeAspect.before12);
      assertEquals("String", OverloadedBeforeAspect.before13);
      assertEquals("Object", OverloadedBeforeAspect.before14);
      assertEquals("Object[]", OverloadedBeforeAspect.before15);
      assertEquals("", OverloadedBeforeAspect.before16);
      assertEquals("FieldAccess,String", OverloadedBeforeAspect.before17);
      OverloadedBeforeAspect.clear();
      String myText = pojo.text;
      assertEquals("FieldAccess", OverloadedBeforeAspect.before17);
      
   }
   
   public void testAround()
   {
      // clear all relevant aspect fields
      OverloadedAroundAspect.clear();
      // execute the join point
      pojo.method1(10, 15);
      // check aspect fields
      assertEquals("defaultSignature", OverloadedAroundAspect.around1);
      assertEquals("MethodInvocation,int,long", OverloadedAroundAspect.around2);
      assertEquals("Invocation,int,long", OverloadedAroundAspect.around3);
      assertEquals("Object,int,long", OverloadedAroundAspect.around4);
      assertTrue(OverloadedAroundAspect.around5.startsWith("MethodInvocation,"));
      assertTrue(OverloadedAroundAspect.around5.equals("MethodInvocation,int") ||
            OverloadedAroundAspect.around5.equals("MethodInvocation,long"));
      assertTrue(OverloadedAroundAspect.around6.startsWith("Invocation,"));
      assertTrue(OverloadedAroundAspect.around6.equals("Invocation,int") ||
            OverloadedAroundAspect.around6.equals("Invocation,long"));
      assertTrue(OverloadedAroundAspect.around7.startsWith("Object,"));
      assertTrue(OverloadedAroundAspect.around7.equals("Object,int") ||
            OverloadedAroundAspect.around7.equals("Object,long"));
      assertEquals("MethodInvocation,Object[]", OverloadedAroundAspect.around8);
      assertEquals("Invocation,Object[]", OverloadedAroundAspect.around9);
      assertEquals("Object,Object[]", OverloadedAroundAspect.around10);
      assertEquals("MethodInvocation", OverloadedAroundAspect.around11);
      assertEquals("Object", OverloadedAroundAspect.around12);
      assertEquals("int,long", OverloadedAroundAspect.around13);
      assertTrue(OverloadedAroundAspect.around14.equals("int") ||
            OverloadedAroundAspect.around14.equals("long"));
      assertEquals("Object[]", OverloadedAroundAspect.around15);
      assertEquals("", OverloadedAroundAspect.around16);
   }
   
   public void testAfter()
   {
      // clear all relevant aspect fields
      OverloadedAfterAspect.clear();
      // execute the join point
      pojo.method2(0, null);
      // check aspect fields
      assertEquals("Object,MethodExecution,SuperClass,float,SubValue",
            OverloadedAfterAspect.after1);
      assertEquals("Object,MethodExecution,SuperClass,float,SuperValue",
            OverloadedAfterAspect.after2);
      assertEquals("Object,MethodExecution,SuperClass,float,Object",
            OverloadedAfterAspect.after3);
      assertEquals("Object,MethodExecution,Object,float,SubValue",
            OverloadedAfterAspect.after4);
      assertEquals("Object,MethodExecution,Object,float,SuperValue",
            OverloadedAfterAspect.after5);
      assertEquals("Object,MethodExecution,Object,float,Object",
            OverloadedAfterAspect.after6);
      assertEquals("Object,JoinPointBean,SuperClass,float,SubValue",
            OverloadedAfterAspect.after7);
      assertEquals("SuperClass,MethodExecution,SuperClass,SubValue",
            OverloadedAfterAspect.after8);
      assertEquals("Object,MethodExecution,SuperClass,float",
            OverloadedAfterAspect.after9);
      assertEquals("Object,MethodExecution,SuperClass,SuperValue",
            OverloadedAfterAspect.after10);
      assertEquals("SuperClass,MethodExecution,SuperClass,Object",
            OverloadedAfterAspect.after11);
      assertEquals("SuperClass,MethodExecution,Object,SubValue",
            OverloadedAfterAspect.after12);
      assertEquals("Object,MethodExecution,Object,float",
            OverloadedAfterAspect.after13);
      assertEquals("SubClass,MethodExecution,Object,SuperValue",
            OverloadedAfterAspect.after14);
      assertEquals("SubClass,MethodExecution,Object,Object",
            OverloadedAfterAspect.after15);
      assertEquals("SuperClass,JoinPointBean,SuperClass,SubValue",
            OverloadedAfterAspect.after16);
      assertEquals("SubClass,JoinPointBean,SuperClass,float",
            OverloadedAfterAspect.after17);
      assertEquals("SubClass,JoinPointBean,SuperClass,SuperValue",
            OverloadedAfterAspect.after18);
      assertEquals("SuperClass,JoinPointBean,SuperClass,Object",
            OverloadedAfterAspect.after19);
      assertEquals("SubClass,Object,SuperClass,SubValue",
            OverloadedAfterAspect.after20);
      assertEquals("Object,Object,SuperClass,float", OverloadedAfterAspect.after21);
      assertEquals("SubClass,Object,SuperClass,SuperValue",
            OverloadedAfterAspect.after22);
      assertEquals("Object,Object,SuperClass,Object", OverloadedAfterAspect.after23);
      assertEquals("Object,MethodExecution,SuperClass,Object[]",
            OverloadedAfterAspect.after24);
      assertEquals("Object,MethodExecution,Object,Object[]",
            OverloadedAfterAspect.after25);
      assertEquals("Object,JoinPointBean,SuperClass,Object[]",
            OverloadedAfterAspect.after26);
      assertEquals("Object,JoinPointBean,Object,Object[]",
            OverloadedAfterAspect.after27);
      assertEquals("Object,Object,SuperClass,Object[]",
            OverloadedAfterAspect.after28);
      assertEquals("Object,Object,Object,Object[]", OverloadedAfterAspect.after29);
      assertEquals("Object,MethodExecution,SuperClass", OverloadedAfterAspect.after30);
      assertEquals("Object,MethodExecution,Object", OverloadedAfterAspect.after31);
      assertEquals("Object,JoinPointBean,SuperClass", OverloadedAfterAspect.after32);
      assertEquals("Object,MethodExecution,float,SubValue",
            OverloadedAfterAspect.after33);
      assertEquals("Object,MethodExecution,float,SuperValue",
            OverloadedAfterAspect.after34);
      assertEquals("Object,MethodExecution,float,Object", OverloadedAfterAspect.after35);
      assertEquals("Object,JoinPointBean,float,SubValue",
            OverloadedAfterAspect.after36);
      assertEquals("SuperClass,MethodExecution,SubValue", OverloadedAfterAspect.after37);
      assertEquals("Object,MethodExecution,float", OverloadedAfterAspect.after38);
      assertEquals("Object,MethodExecution,SuperValue", OverloadedAfterAspect.after39);
      assertEquals("SuperClass,JoinPointBean,SubValue",
            OverloadedAfterAspect.after40);
      assertEquals("SubClass,JoinPointBean,float", OverloadedAfterAspect.after41);
      assertEquals("SubClass,JoinPointBean,SuperValue",
            OverloadedAfterAspect.after42);
      assertEquals("SubClass,Object,SubValue", OverloadedAfterAspect.after43);
      assertEquals("Object,Object,float", OverloadedAfterAspect.after44);
      assertEquals("SubClass,Object,SuperValue", OverloadedAfterAspect.after45);
      assertEquals("SubClass,MethodExecution,Object[]", OverloadedAfterAspect.after46);
      assertEquals("SubClass,JoinPointBean,Object[]", OverloadedAfterAspect.after47);
      assertEquals("Object,MethodExecution", OverloadedAfterAspect.after48);
      assertEquals("Object,JoinPointBean", OverloadedAfterAspect.after49);
      assertEquals("Object,SuperClass,float,SubValue",
            OverloadedAfterAspect.after50);
      assertEquals("Object,SuperClass,float,SuperValue",
            OverloadedAfterAspect.after51);
      assertEquals("Object,SuperClass,float,Object", OverloadedAfterAspect.after52);
      assertEquals("SuperClass,SuperClass,float", OverloadedAfterAspect.after53);
      assertEquals("Object,SuperClass,SubValue", OverloadedAfterAspect.after54);
      assertEquals("Object,SuperClass,SuperValue", OverloadedAfterAspect.after55);
      assertEquals("SuperClass,SuperClass,Object", OverloadedAfterAspect.after56);
      assertEquals("Object,SuperClass,Object[]", OverloadedAfterAspect.after57);
      assertEquals("Object,Object,Object[]", OverloadedAfterAspect.after58);
      assertEquals("Object,SuperClass", OverloadedAfterAspect.after59);
      assertEquals("Object,Object", OverloadedAfterAspect.after60);
      assertEquals("void,float,SubValue", OverloadedAfterAspect.after61);
      assertEquals("Object,float", OverloadedAfterAspect.after62);
      assertEquals("void,SubValue", OverloadedAfterAspect.after63);
      assertEquals("void,SuperValue", OverloadedAfterAspect.after64);
      assertEquals("void,Object[]", OverloadedAfterAspect.after65);
      assertEquals("void", OverloadedAfterAspect.after66);
   }
   
   public void testAfterThrowing()
   {
      // clear all relevant aspect fields
      OverloadedThrowingAspect.clear();
      // execute the join point
      try
      {
         new OverloadedAdvicePOJO(null, null);
      } catch (POJOException pe) {}
      
      // check aspect fields
      
      assertEquals("JoinPointBean,Throwable,SubInterface,Implementor",
            OverloadedThrowingAspect.throwing1);
      
      assertTrue(OverloadedThrowingAspect.throwing2.startsWith(
            "JoinPointBean,Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing2.equals(
            "JoinPointBean,Throwable,Interface,Implementor") ||
            OverloadedThrowingAspect.throwing2.equals(
            "JoinPointBean,Throwable,SubInterface,SubInterface") ||
            OverloadedThrowingAspect.throwing2.equals(
            "JoinPointBean,Throwable,SubInterface,Object"));
      
      assertTrue(OverloadedThrowingAspect.throwing3.startsWith(
            "JoinPointBean,Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing3.equals(  
            "JoinPointBean,Throwable,SuperInterface,Implementor") ||
            OverloadedThrowingAspect.throwing3.equals(
            "JoinPointBean,Throwable,SubInterface,Interface") ||
            OverloadedThrowingAspect.throwing3.equals(
            "JoinPointBean,Throwable,Interface,SubInterface"));
      
      assertTrue(OverloadedThrowingAspect.throwing4.startsWith(
            "JoinPointBean,Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing4.equals(
            "JoinPointBean,Throwable,SuperInterface,SubInterface") ||
            OverloadedThrowingAspect.throwing4.equals(
            "JoinPointBean,Throwable,SuperInterface,Object") ||
            OverloadedThrowingAspect.throwing4.equals(
            "JoinPointBean,Throwable,Interface,Interface") ||
            OverloadedThrowingAspect.throwing4.equals(
            "JoinPointBean,Throwable,SubInterface,SuperInterface"));
      
      assertTrue(OverloadedThrowingAspect.throwing5.startsWith(
            "JoinPointBean,Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing5.equals(
            "JoinPointBean,Throwable,SuperInterface,Interface") ||
            OverloadedThrowingAspect.throwing5.equals(
            "JoinPointBean,Throwable,Interface,SuperInterface"));
      
      assertEquals("JoinPointBean,Throwable,SuperInterface,SuperInterface",
            OverloadedThrowingAspect.throwing6);
      
      assertEquals("JoinPointBean,Object,SubInterface,Implementor",
            OverloadedThrowingAspect.throwing7);
      
      assertTrue(OverloadedThrowingAspect.throwing8.startsWith(
            "JoinPointBean,Object,"));
      assertTrue(OverloadedThrowingAspect.throwing8.equals(
            "JoinPointBean,Object,Interface,Implementor") ||
            OverloadedThrowingAspect.throwing8.equals(
            "JoinPointBean,Object,SubInterface,SubInterface") ||
            OverloadedThrowingAspect.throwing8.equals(
            "JoinPointBean,Object,SubInterface,Object"));

      assertTrue(OverloadedThrowingAspect.throwing9.startsWith(
            "JoinPointBean,Object,"));
      assertTrue(OverloadedThrowingAspect.throwing9.equals(  
            "JoinPointBean,Object,SuperInterface,Implementor") ||
            OverloadedThrowingAspect.throwing9.equals(
            "JoinPointBean,Object,SubInterface,Interface") ||
            OverloadedThrowingAspect.throwing9.equals(
            "JoinPointBean,Object,Interface,SubInterface"));

      assertTrue(OverloadedThrowingAspect.throwing10.startsWith(
            "JoinPointBean,Object,"));
      assertTrue(OverloadedThrowingAspect.throwing10.equals(
            "JoinPointBean,Object,SuperInterface,SubInterface") ||
            OverloadedThrowingAspect.throwing10.equals(
            "JoinPointBean,Object,SuperInterface,Object") ||
            OverloadedThrowingAspect.throwing10.equals(
            "JoinPointBean,Object,Interface,Interface") ||
            OverloadedThrowingAspect.throwing10.equals(
            "JoinPointBean,Object,SubInterface,SuperInterface"));
      
      assertTrue(OverloadedThrowingAspect.throwing11.startsWith(
            "JoinPointBean,Object,"));
      assertTrue(OverloadedThrowingAspect.throwing11.equals(
            "JoinPointBean,Object,SuperInterface,Interface") ||
            OverloadedThrowingAspect.throwing11.equals(
            "JoinPointBean,Object,Interface,SuperInterface"));

      assertEquals("JoinPointBean,Object,SuperInterface,SuperInterface",
               OverloadedThrowingAspect.throwing12);
      
      assertTrue(OverloadedThrowingAspect.throwing13.startsWith(
            "JoinPointBean,Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing13.equals(
            "JoinPointBean,Throwable,SubInterface") ||
            OverloadedThrowingAspect.throwing13.equals(
            "JoinPointBean,Throwable,Implementor"));
      
      assertTrue(OverloadedThrowingAspect.throwing14.startsWith(
            "JoinPointBean,Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing14.equals(
            "JoinPointBean,Throwable,Interface") ||
            OverloadedThrowingAspect.throwing14.equals(
            "JoinPointBean,Throwable,Object"));
      
      assertEquals("JoinPointBean,Throwable,SuperInterface",
            OverloadedThrowingAspect.throwing15);
      
      assertEquals("JoinPointBean,Throwable,Object[]",
            OverloadedThrowingAspect.throwing16);
      
      assertEquals("JoinPointBean,Object,Object[]",
            OverloadedThrowingAspect.throwing17);
      
      assertEquals("Throwable,SubInterface,Implementor",
            OverloadedThrowingAspect.throwing18);
      
      assertTrue(OverloadedThrowingAspect.throwing19.startsWith("Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing19.equals(
            "Throwable,Interface,Implementor") ||
            OverloadedThrowingAspect.throwing19.equals(
            "Throwable,SubInterface,SubInterface") ||
            OverloadedThrowingAspect.throwing19.equals(
            "Throwable,SubInterface,Object"));
      
      assertTrue(OverloadedThrowingAspect.throwing20.startsWith("Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing20.equals(  
            "Throwable,SuperInterface,Implementor") ||
            OverloadedThrowingAspect.throwing20.equals(
            "Throwable,SubInterface,Interface") ||
            OverloadedThrowingAspect.throwing20.equals(
            "Throwable,Interface,SubInterface"));
      
      assertTrue(OverloadedThrowingAspect.throwing21.startsWith("Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing21.equals(
            "Throwable,SuperInterface,SubInterface") ||
            OverloadedThrowingAspect.throwing21.equals(
            "Throwable,SuperInterface,Object") ||
            OverloadedThrowingAspect.throwing21.equals(
            "Throwable,Interface,Interface") ||
            OverloadedThrowingAspect.throwing21.equals(
            "Throwable,SubInterface,SuperInterface"));

      assertTrue(OverloadedThrowingAspect.throwing22.startsWith("Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing22.equals(
            "Throwable,SuperInterface,Interface") ||
            OverloadedThrowingAspect.throwing22.equals(
            "Throwable,Interface,SuperInterface"));

      assertEquals("Throwable,SuperInterface,SuperInterface",
            OverloadedThrowingAspect.throwing23);

      assertEquals("Object,SubInterface,Implementor",
            OverloadedThrowingAspect.throwing24);
      
      assertTrue(OverloadedThrowingAspect.throwing25.startsWith("Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing25.equals("Throwable,SubInterface")
            || OverloadedThrowingAspect.throwing25.equals("Throwable,Implementor"));

      assertTrue(OverloadedThrowingAspect.throwing26.startsWith("Throwable,"));
      assertTrue(OverloadedThrowingAspect.throwing26.equals("Throwable,Interface") ||
         OverloadedThrowingAspect.throwing26.equals("Throwable,Object"));

      assertEquals("Throwable,SuperInterface", OverloadedThrowingAspect.throwing27);
      
      assertTrue(OverloadedThrowingAspect.throwing28.startsWith("Object,"));
      assertTrue(OverloadedThrowingAspect.throwing28.equals("Object,SubInterface")
            || OverloadedThrowingAspect.throwing28.equals("Object,Implementor"));

      assertTrue(OverloadedThrowingAspect.throwing29.startsWith("Object,"));
      assertTrue(OverloadedThrowingAspect.throwing29.equals("Object,Interface") ||
         OverloadedThrowingAspect.throwing29.equals("Object,Object"));

      assertEquals("Object,SuperInterface", OverloadedThrowingAspect.throwing30);
      
      assertEquals("Throwable,Object[]", OverloadedThrowingAspect.throwing31);
      
      assertEquals("Object,Object[]", OverloadedThrowingAspect.throwing32);
      
      assertEquals("Throwable", OverloadedThrowingAspect.throwing33);
      
      assertEquals("Object", OverloadedThrowingAspect.throwing34);
   }
   
   public void testAfterThrowing2()
   {
      // clear all relevant aspect fields
      OverloadedThrowingAspect.clear();
      // execute the join point
      try
      {
         new OverloadedAdvicePOJO(null, null, 0);
      } catch (POJOException pe) {}
      
      // check aspect fields
      
      assertEquals("Throwable,SuperInterface", OverloadedThrowingAspect.throwing35);
      assertEquals("Throwable,Object", OverloadedThrowingAspect.throwing36);
      assertNotNull(OverloadedThrowingAspect.throwing37); // it can choose any advice
      assertEquals("Throwable,Object", OverloadedThrowingAspect.throwing38);
   }
   
   public void testFinally() throws Exception
   {
      // clear all relevant aspect fields
      OverloadedFinallyAspect.clear();
      
      // execute the join point
      assertEquals("finally69", pojo.method5(0, 1));
      
      // check aspect fields
      assertEquals("void,MethodExecution,String,Throwable,int,long",
            OverloadedFinallyAspect.finally1);
      assertEquals("void,MethodExecution,String,Serializable,int,long",
            OverloadedFinallyAspect.finally2);
      assertEquals("void,MethodExecution,CharSequence,Throwable,int,long",
            OverloadedFinallyAspect.finally3);
      assertEquals("String,MethodExecution,String,Throwable,int",
            OverloadedFinallyAspect.finally4);
      assertEquals("void,MethodExecution,String,Throwable,long",
            OverloadedFinallyAspect.finally5);
      assertEquals("String,MethodExecution,String,Serializable,long",
            OverloadedFinallyAspect.finally6);
      assertEquals("void,MethodExecution,String,Serializable,int",
            OverloadedFinallyAspect.finally7);
      assertEquals("Object,MethodExecution,CharSequence,Throwable,int",
            OverloadedFinallyAspect.finally8);
      assertEquals("void,MethodExecution,CharSequence,Throwable,long",
            OverloadedFinallyAspect.finally9);
      assertEquals("void,MethodExecution,String,Throwable,Object[]",
            OverloadedFinallyAspect.finally10);
      assertEquals("void,MethodExecution,String,Throwable,Object",
            OverloadedFinallyAspect.finally11);
      assertEquals("void,MethodExecution,String,Serializable,Object[]",
            OverloadedFinallyAspect.finally12);
      assertEquals("void,MethodExecution,String,Serializable,Object",
            OverloadedFinallyAspect.finally13);
      assertEquals("void,MethodExecution,CharSequence,Throwable,Object[]",
            OverloadedFinallyAspect.finally14);
      assertEquals("void,MethodExecution,CharSequence,Throwable,Object",
            OverloadedFinallyAspect.finally15);
      assertEquals("void,MethodExecution,String,Throwable",
            OverloadedFinallyAspect.finally16);
      assertEquals("void,MethodExecution,String,Serializable",
            OverloadedFinallyAspect.finally17);
      assertEquals("void,MethodExecution,CharSequence,Throwable",
            OverloadedFinallyAspect.finally18);
      assertEquals("void,MethodExecution,Throwable,int,long",
            OverloadedFinallyAspect.finally19);
      assertEquals("void,MethodExecution,Serializable,int,long",
            OverloadedFinallyAspect.finally20);
      assertEquals("Object,MethodExecution,Throwable,long",
            OverloadedFinallyAspect.finally21);
      assertEquals("void,MethodExecution,Throwable,int",
            OverloadedFinallyAspect.finally22);
      assertEquals("String,MethodExecution,Serializable,int",
            OverloadedFinallyAspect.finally23);
      assertEquals("Object,MethodExecution,Serializable,long",
            OverloadedFinallyAspect.finally24);
      assertEquals("void,MethodExecution,Throwable,Object[]",
            OverloadedFinallyAspect.finally25);
      assertEquals("void,MethodExecution,Throwable,Object",
            OverloadedFinallyAspect.finally26);
      assertEquals("void,MethodExecution,Serializable,Object[]",
            OverloadedFinallyAspect.finally27);
      assertEquals("void,MethodExecution,Serializable,Object",
            OverloadedFinallyAspect.finally28);
      assertEquals("void,MethodExecution,Throwable", OverloadedFinallyAspect.finally29);
      assertEquals("void,MethodExecution,Serializable",
            OverloadedFinallyAspect.finally30);
      assertEquals("void,MethodExecution,int,long", OverloadedFinallyAspect.finally31);
      assertEquals("String,MethodExecution,long", OverloadedFinallyAspect.finally32);
      assertEquals("Object,MethodExecution,int", OverloadedFinallyAspect.finally33);
      assertEquals("void,MethodExecution,Object[]", OverloadedFinallyAspect.finally34);
      assertEquals("void,MethodExecution,Object", OverloadedFinallyAspect.finally35);
      assertEquals("void,MethodExecution", OverloadedFinallyAspect.finally36);
      assertEquals("void,String,Throwable,int,long",
            OverloadedFinallyAspect.finally37);
      assertEquals("void,String,Serializable,int,long",
            OverloadedFinallyAspect.finally38);
      assertEquals("void,CharSequence,Throwable,int,long",
            OverloadedFinallyAspect.finally39);
      assertEquals("String,String,Throwable,int", OverloadedFinallyAspect.finally40);
      assertEquals("void,String,Throwable,long", OverloadedFinallyAspect.finally41);
      assertEquals("String,String,Serializable,long",
            OverloadedFinallyAspect.finally42);
      assertEquals("void,String,Serializable,int",
            OverloadedFinallyAspect.finally43);
      assertEquals("Object,CharSequence,Throwable,int",
            OverloadedFinallyAspect.finally44);
      assertEquals("void,CharSequence,Throwable,long",
            OverloadedFinallyAspect.finally45);
      assertEquals("void,String,Throwable,Object[]",
            OverloadedFinallyAspect.finally46);
      assertEquals("void,String,Throwable,Object",
            OverloadedFinallyAspect.finally47);
      assertEquals("void,String,Serializable,Object[]",
            OverloadedFinallyAspect.finally48);
      assertEquals("void,String,Serializable,Object",
            OverloadedFinallyAspect.finally49);
      assertEquals("void,CharSequence,Throwable,Object[]",
            OverloadedFinallyAspect.finally50);
      assertEquals("void,CharSequence,Throwable,Object",
            OverloadedFinallyAspect.finally51);
      assertEquals("void,String,Throwable", OverloadedFinallyAspect.finally52);
      assertEquals("void,String,Serializable", OverloadedFinallyAspect.finally53);
      assertEquals("void,CharSequence,Throwable", OverloadedFinallyAspect.finally54);
      assertEquals("void,Throwable,int,long", OverloadedFinallyAspect.finally55);
      assertEquals("void,Serializable,int,long", OverloadedFinallyAspect.finally56);
      assertEquals("Object,Throwable,long", OverloadedFinallyAspect.finally57);
      assertEquals("void,Throwable,int", OverloadedFinallyAspect.finally58);
      assertEquals("String,Serializable,int", OverloadedFinallyAspect.finally59);
      assertEquals("Object,Serializable,long", OverloadedFinallyAspect.finally60);
      assertEquals("void,Throwable,Object[]", OverloadedFinallyAspect.finally61);
      assertEquals("void,Throwable,Object", OverloadedFinallyAspect.finally62);
      assertEquals("void,Serializable,Object[]", OverloadedFinallyAspect.finally63);
      assertEquals("void,Serializable,Object", OverloadedFinallyAspect.finally64);
      assertEquals("void,Throwable", OverloadedFinallyAspect.finally65);
      assertEquals("void,Serializable", OverloadedFinallyAspect.finally66);
      assertEquals("void,int,long", OverloadedFinallyAspect.finally67);
      assertEquals("String,long", OverloadedFinallyAspect.finally68);
      assertEquals("Object,int", OverloadedFinallyAspect.finally69);
      assertEquals("void,Object[]", OverloadedFinallyAspect.finally70);
      assertEquals("void,Object", OverloadedFinallyAspect.finally71);
   }
   
   public void testBeforeCall()
   {
      // clear all relevant aspect fields
      OverloadedBeforeCallAspect.clear();
      // execute the join point
      (new OverloadedAdvicePOJOCaller()).callMethod3(pojo);
      // check aspect fields
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedBeforeCallAspect.before1);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,int",
            OverloadedBeforeCallAspect.before2);
      assertEquals("MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,int",
            OverloadedBeforeCallAspect.before3);
      assertEquals("MethodCallByMethod,Object,SuperClass,int",
            OverloadedBeforeCallAspect.before4);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedBeforeCallAspect.before5);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,Object[]",
            OverloadedBeforeCallAspect.before6);
      assertEquals("MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedBeforeCallAspect.before7);
      assertEquals("MethodCallByMethod,Object,SuperClass,Object[]",
            OverloadedBeforeCallAspect.before8);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller",
            OverloadedBeforeCallAspect.before9);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,SuperClass",
            OverloadedBeforeCallAspect.before10);
      assertEquals("MethodCallByMethod,Object,OverloadedAdvicePOJOCaller",
            OverloadedBeforeCallAspect.before11);
      assertEquals("MethodCallByMethod,Object,SuperClass",
            OverloadedBeforeCallAspect.before12);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,int",
            OverloadedBeforeCallAspect.before13);
      assertEquals("MethodCallByMethod,Object,int",
            OverloadedBeforeCallAspect.before14);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,Object[]",
            OverloadedBeforeCallAspect.before15);
      assertEquals("MethodCallByMethod,Object,Object[]",
            OverloadedBeforeCallAspect.before16);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,int",
            OverloadedBeforeCallAspect.before17);
      assertEquals("MethodCallByMethod,SuperClass,int",
            OverloadedBeforeCallAspect.before18);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedBeforeCallAspect.before19);
      assertEquals("MethodCallByMethod,SuperClass,Object[]",
            OverloadedBeforeCallAspect.before20);
      assertEquals("MethodCallByMethod,int", OverloadedBeforeCallAspect.before21);
      assertEquals("MethodCallByMethod,Object[]",
            OverloadedBeforeCallAspect.before22);
      assertEquals("MethodCallByMethod", OverloadedBeforeCallAspect.before23);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedBeforeCallAspect.before24);
      assertEquals("OverloadedAdvicePOJO,SuperClass,int",
            OverloadedBeforeCallAspect.before25);
      assertEquals("Object,OverloadedAdvicePOJOCaller,int",
            OverloadedBeforeCallAspect.before26);
      assertEquals("Object,SuperClass,int", OverloadedBeforeCallAspect.before27);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedBeforeCallAspect.before28);
      assertEquals("OverloadedAdvicePOJO,SuperClass,Object[]",
            OverloadedBeforeCallAspect.before29);
      assertEquals("Object,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedBeforeCallAspect.before30);
      assertEquals("Object,SuperClass,Object[]",
            OverloadedBeforeCallAspect.before31);
      assertEquals("OverloadedAdvicePOJO,int", OverloadedBeforeCallAspect.before32);
      assertEquals("Object,int", OverloadedBeforeCallAspect.before33);
      assertEquals("OverloadedAdvicePOJO,Object[]",
            OverloadedBeforeCallAspect.before34);
      assertEquals("Object,Object[]", OverloadedBeforeCallAspect.before35);
      assertEquals("OverloadedAdvicePOJO", OverloadedBeforeCallAspect.before36);
      assertEquals("Object", OverloadedBeforeCallAspect.before37);
      assertEquals("OverloadedAdvicePOJOCaller,int",
            OverloadedBeforeCallAspect.before38);
      assertEquals("SuperClass,int", OverloadedBeforeCallAspect.before39);
      assertEquals("OverloadedAdvicePOJOCaller,Object[]",
            OverloadedBeforeCallAspect.before40);
      assertEquals("SuperClass,Object[]", OverloadedBeforeCallAspect.before41);
      assertEquals("OverloadedAdvicePOJOCaller",
            OverloadedBeforeCallAspect.before42);
      assertEquals("SuperClass", OverloadedBeforeCallAspect.before43);
      assertEquals("int", OverloadedBeforeCallAspect.before44);
   }
   
   public void testAroundCall()
   {
      // clear all relevant aspect fields
      OverloadedAroundCallAspect.clear();
      // execute the join point
      (new OverloadedAdvicePOJOCaller()).callMethod3(pojo);
      // check aspect fields
      assertEquals("defaultSignature", OverloadedAroundCallAspect.around1);
      assertEquals(
            "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedAroundCallAspect.around2);
      assertEquals(
            "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,int",
            OverloadedAroundCallAspect.around3);
      assertEquals(
            "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,int",
            OverloadedAroundCallAspect.around4);
      assertEquals("MethodCalledByMethodInvocation,Object,SuperClass,int",
            OverloadedAroundCallAspect.around5);
      assertEquals(
            "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedAroundCallAspect.around6);
      assertEquals(
            "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass,Object[]",
            OverloadedAroundCallAspect.around7);
      assertEquals(
            "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedAroundCallAspect.around8);
      assertEquals("MethodCalledByMethodInvocation,Object,SuperClass,Object[]",
            OverloadedAroundCallAspect.around9);
      assertEquals(
            "MethodCalledByMethodInvocation,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller",
            OverloadedAroundCallAspect.around10);
      assertEquals("MethodCalledByMethodInvocation,OverloadedAdvicePOJO,SuperClass",
            OverloadedAroundCallAspect.around11);
      assertEquals(
            "MethodCalledByMethodInvocation,Object,OverloadedAdvicePOJOCaller",
            OverloadedAroundCallAspect.around12);
      assertEquals("MethodCalledByMethodInvocation,Object,SuperClass",
            OverloadedAroundCallAspect.around13);
      assertEquals("MethodCalledByMethodInvocation,OverloadedAdvicePOJO,int",
            OverloadedAroundCallAspect.around14);
      assertEquals("MethodCalledByMethodInvocation,Object,int",
            OverloadedAroundCallAspect.around15);
      assertEquals("MethodCalledByMethodInvocation,OverloadedAdvicePOJO,Object[]",
            OverloadedAroundCallAspect.around16);
      assertEquals("MethodCalledByMethodInvocation,Object,Object[]",
            OverloadedAroundCallAspect.around17);
      assertEquals("MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,int",
            OverloadedAroundCallAspect.around18);
      assertEquals("MethodCalledByMethodInvocation,SuperClass,int",
            OverloadedAroundCallAspect.around19);
      assertEquals(
            "MethodCalledByMethodInvocation,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedAroundCallAspect.around20);
      assertEquals("MethodCalledByMethodInvocation,SuperClass,Object[]",
            OverloadedAroundCallAspect.around21);
      assertEquals("MethodCalledByMethodInvocation,int",
            OverloadedAroundCallAspect.around22);
      assertEquals("MethodCalledByMethodInvocation,Object[]",
            OverloadedAroundCallAspect.around23);
      assertEquals("MethodCalledByMethodInvocation",
            OverloadedAroundCallAspect.around24);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedAroundCallAspect.around25);
      assertEquals("OverloadedAdvicePOJO,SuperClass,int",
            OverloadedAroundCallAspect.around26);
      assertEquals("Object,OverloadedAdvicePOJOCaller,int",
            OverloadedAroundCallAspect.around27);
      assertEquals("Object,SuperClass,int", OverloadedAroundCallAspect.around28);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedAroundCallAspect.around29);
      assertEquals("OverloadedAdvicePOJO,SuperClass,Object[]",
            OverloadedAroundCallAspect.around30);
      assertEquals("Object,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedAroundCallAspect.around31);
      assertEquals("Object,SuperClass,Object[]",
            OverloadedAroundCallAspect.around32);
      assertEquals("OverloadedAdvicePOJO,int", OverloadedAroundCallAspect.around33);
      assertEquals("Object,int", OverloadedAroundCallAspect.around34);
      assertEquals("OverloadedAdvicePOJO,Object[]",
            OverloadedAroundCallAspect.around35);
      assertEquals("Object,Object[]", OverloadedAroundCallAspect.around36);
      assertEquals("OverloadedAdvicePOJO", OverloadedAroundCallAspect.around37);
      assertEquals("Object", OverloadedAroundCallAspect.around38);
      assertEquals("OverloadedAdvicePOJOCaller,int",
            OverloadedAroundCallAspect.around39);
      assertEquals("SuperClass,int", OverloadedAroundCallAspect.around40);
      assertEquals("OverloadedAdvicePOJOCaller,Object[]",
            OverloadedAroundCallAspect.around41);
      assertEquals("SuperClass,Object[]", OverloadedAroundCallAspect.around42);
      assertEquals("OverloadedAdvicePOJOCaller",
            OverloadedAroundCallAspect.around43);
      assertEquals("SuperClass", OverloadedAroundCallAspect.around44);
      assertEquals("int", OverloadedAroundCallAspect.around45);
   }
   
   public void testAfterCall()
   {
      // clear all relevant aspect fields
      OverloadedAfterCallAspect.clear();
      // execute the join point
      (new OverloadedAdvicePOJOCaller()).callMethod3(pojo);
      // check aspect fields
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int",
            OverloadedAfterCallAspect.after1);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]",
            OverloadedAfterCallAspect.after2);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long",
            OverloadedAfterCallAspect.after3);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedAfterCallAspect.after4);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller",
            OverloadedAfterCallAspect.after5);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,long,int",
            OverloadedAfterCallAspect.after6);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,long,Object[]",
            OverloadedAfterCallAspect.after7);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,long",
            OverloadedAfterCallAspect.after8);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,int",
            OverloadedAfterCallAspect.after9);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO",
            OverloadedAfterCallAspect.after10);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,long,int",
            OverloadedAfterCallAspect.after11);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Object[]",
            OverloadedAfterCallAspect.after12);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,long",
            OverloadedAfterCallAspect.after13);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,int",
            OverloadedAfterCallAspect.after14);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller",
            OverloadedAfterCallAspect.after15);
      assertEquals("MethodCallByMethod,long,int", OverloadedAfterCallAspect.after16);
      assertEquals("MethodCallByMethod,long,Object[]",
            OverloadedAfterCallAspect.after17);
      assertEquals("MethodCallByMethod,long", OverloadedAfterCallAspect.after18);
      assertEquals("MethodCallByMethod,int", OverloadedAfterCallAspect.after19);
      assertEquals("MethodCallByMethod", OverloadedAfterCallAspect.after20);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,int",
            OverloadedAfterCallAspect.after21);
      assertEquals("OverloadedAdvicePOJO,SuperClass,long,int",
            OverloadedAfterCallAspect.after22);
      assertEquals("Object,OverloadedAdvicePOJOCaller,long,int",
            OverloadedAfterCallAspect.after23);
      assertEquals("Object,SuperClass,long,int", OverloadedAfterCallAspect.after24);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Object[]",
            OverloadedAfterCallAspect.after25);
      assertEquals("OverloadedAdvicePOJO,SuperClass,long,Object[]",
            OverloadedAfterCallAspect.after26);
      assertEquals("Object,OverloadedAdvicePOJOCaller,long,Object[]",
            OverloadedAfterCallAspect.after27);
      assertEquals("Object,SuperClass,long,Object[]",
            OverloadedAfterCallAspect.after28);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long",
            OverloadedAfterCallAspect.after29);
      assertEquals("OverloadedAdvicePOJO,SuperClass,long",
            OverloadedAfterCallAspect.after30);
      assertEquals("Object,OverloadedAdvicePOJOCaller,long",
            OverloadedAfterCallAspect.after31);
      assertEquals("Object,SuperClass,long", OverloadedAfterCallAspect.after32);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedAfterCallAspect.after33);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller",
            OverloadedAfterCallAspect.after34);
      assertEquals("OverloadedAdvicePOJO,long,int",
            OverloadedAfterCallAspect.after35);
      assertEquals("Object,long,int", OverloadedAfterCallAspect.after36);
      assertEquals("OverloadedAdvicePOJO,long,Object[]",
            OverloadedAfterCallAspect.after37);
      assertEquals("Object,long,Object[]", OverloadedAfterCallAspect.after38);
      assertEquals("OverloadedAdvicePOJO,long", OverloadedAfterCallAspect.after39);
      assertEquals("Object,long", OverloadedAfterCallAspect.after40);
      assertEquals("OverloadedAdvicePOJO,int", OverloadedAfterCallAspect.after41);
      assertEquals("OverloadedAdvicePOJO", OverloadedAfterCallAspect.after42);
      assertEquals("OverloadedAdvicePOJOCaller,long,int",
            OverloadedAfterCallAspect.after43);
      assertEquals("SuperClass,long,int", OverloadedAfterCallAspect.after44);
      assertEquals("OverloadedAdvicePOJOCaller,long,Object[]",
            OverloadedAfterCallAspect.after45);
      assertEquals("SuperClass,long,Object[]", OverloadedAfterCallAspect.after46);
      assertEquals("OverloadedAdvicePOJOCaller,long",
            OverloadedAfterCallAspect.after47);
      assertEquals("SuperClass,long", OverloadedAfterCallAspect.after48);
      assertEquals("OverloadedAdvicePOJOCaller,int",
            OverloadedAfterCallAspect.after49);
      assertEquals("OverloadedAdvicePOJOCaller", OverloadedAfterCallAspect.after50);
   }
   
   public void testAfterCallThrowing()
   {
      // clear all relevant aspect fields
      OverloadedThrowingCallAspect.clear();
      // execute the join point
      (new OverloadedAdvicePOJOCaller()).callMethod4(pojo);
      // check aspect fields
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing1);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing2);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable",
            OverloadedThrowingCallAspect.throwing3);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing4);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing5);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,Throwable",
            OverloadedThrowingCallAspect.throwing6);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing7);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing8);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable",
            OverloadedThrowingCallAspect.throwing9);
      assertEquals("MethodCallByMethod,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing10);
      assertEquals("MethodCallByMethod,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing11);
      assertEquals("MethodCallByMethod,Throwable",
            OverloadedThrowingCallAspect.throwing12);
      assertEquals(
            "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing13);
      assertEquals("OverloadedAdvicePOJO,SuperClass,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing14);
      assertEquals("Object,OverloadedAdvicePOJOCaller,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing15);
      assertEquals("Object,SuperClass,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing16);
      assertEquals(
            "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing17);
      assertEquals("OverloadedAdvicePOJO,SuperClass,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing18);
      assertEquals("Object,OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing19);
      assertEquals("Object,SuperClass,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing20);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable",
            OverloadedThrowingCallAspect.throwing21);
      assertEquals("OverloadedAdvicePOJO,SuperClass,Throwable",
            OverloadedThrowingCallAspect.throwing22);
      assertEquals("Object,OverloadedAdvicePOJOCaller,Throwable",
            OverloadedThrowingCallAspect.throwing23);
      assertEquals("Object,SuperClass,Throwable",
            OverloadedThrowingCallAspect.throwing24);
      assertEquals("OverloadedAdvicePOJO,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing25);
      assertEquals("Object,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing26);
      assertEquals("OverloadedAdvicePOJO,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing27);
      assertEquals("Object,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing28);
      assertEquals("OverloadedAdvicePOJO,Throwable",
            OverloadedThrowingCallAspect.throwing29);
      assertEquals("Object,Throwable", OverloadedThrowingCallAspect.throwing30);
      assertEquals("OverloadedAdvicePOJOCaller,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing31);
      assertEquals("SuperClass,Throwable,boolean",
            OverloadedThrowingCallAspect.throwing32);
      assertEquals("OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing33);
      assertEquals("SuperClass,Throwable,Object[]",
            OverloadedThrowingCallAspect.throwing34);
      assertEquals("OverloadedAdvicePOJOCaller,Throwable",
            OverloadedThrowingCallAspect.throwing35);
      assertEquals("SuperClass,Throwable", OverloadedThrowingCallAspect.throwing36);
      assertEquals("Throwable,boolean", OverloadedThrowingCallAspect.throwing37);
   }
   
   public void testFinallyCall()
   {
      // clear all relevant aspect fields
      OverloadedFinallyCallAspect.clear();
      
      // execute the join point
      (new OverloadedAdvicePOJOCaller()).callMethod3(pojo);
      
      // check aspect fields
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int",
            OverloadedFinallyCallAspect.finally1);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,int",
            OverloadedFinallyCallAspect.finally2);
      assertEquals(
            "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,int",
            OverloadedFinallyCallAspect.finally3);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally4);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally5);
      assertEquals(
            "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally6);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable",
            OverloadedFinallyCallAspect.finally7);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,SuperClass,long,Throwable",
            OverloadedFinallyCallAspect.finally8);
      assertEquals(
            "MethodCallByMethod,Object,OverloadedAdvicePOJOCaller,long,Throwable",
            OverloadedFinallyCallAspect.finally9);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int",
            OverloadedFinallyCallAspect.finally10);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally11);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable",
            OverloadedFinallyCallAspect.finally12);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedFinallyCallAspect.finally13);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedFinallyCallAspect.finally14);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller",
            OverloadedFinallyCallAspect.finally15);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,int",
            OverloadedFinallyCallAspect.finally16);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally17);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,long,Throwable",
            OverloadedFinallyCallAspect.finally18);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,Throwable,int",
            OverloadedFinallyCallAspect.finally19);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally20);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,Throwable",
            OverloadedFinallyCallAspect.finally21);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,int",
            OverloadedFinallyCallAspect.finally22);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO,Object[]",
            OverloadedFinallyCallAspect.finally23);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJO",
            OverloadedFinallyCallAspect.finally24);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,int",
            OverloadedFinallyCallAspect.finally25);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally26);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,long,Throwable",
            OverloadedFinallyCallAspect.finally27);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,int",
            OverloadedFinallyCallAspect.finally28);
      assertEquals(
            "MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally29);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,Throwable",
            OverloadedFinallyCallAspect.finally30);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,int",
            OverloadedFinallyCallAspect.finally31);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedFinallyCallAspect.finally32);
      assertEquals("MethodCallByMethod,OverloadedAdvicePOJOCaller",
            OverloadedFinallyCallAspect.finally33);
      assertEquals("MethodCallByMethod,long,Throwable,int",
            OverloadedFinallyCallAspect.finally34);
      assertEquals("MethodCallByMethod", OverloadedFinallyCallAspect.finally35);
      assertEquals(
            "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,int",
            OverloadedFinallyCallAspect.finally36);
      assertEquals(
            "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally37);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,long,Throwable",
            OverloadedFinallyCallAspect.finally38);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,int",
            OverloadedFinallyCallAspect.finally39);
      assertEquals(
            "OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally40);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Throwable",
            OverloadedFinallyCallAspect.finally41);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,int",
            OverloadedFinallyCallAspect.finally42);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller,Object[]",
            OverloadedFinallyCallAspect.finally43);
      assertEquals("OverloadedAdvicePOJO,OverloadedAdvicePOJOCaller",
            OverloadedFinallyCallAspect.finally44);
      assertEquals("OverloadedAdvicePOJO,long,Throwable,int",
            OverloadedFinallyCallAspect.finally45);
      assertEquals("OverloadedAdvicePOJO,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally46);
      assertEquals("OverloadedAdvicePOJO,long,Throwable",
            OverloadedFinallyCallAspect.finally47);
      assertEquals("OverloadedAdvicePOJO,Throwable,int",
            OverloadedFinallyCallAspect.finally48);
      assertEquals("OverloadedAdvicePOJO,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally49);
      assertEquals("OverloadedAdvicePOJO,Throwable",
            OverloadedFinallyCallAspect.finally50);
      assertEquals("OverloadedAdvicePOJO,int",
            OverloadedFinallyCallAspect.finally51);
      assertEquals("OverloadedAdvicePOJO,Object[]",
            OverloadedFinallyCallAspect.finally52);
      assertEquals("OverloadedAdvicePOJO", OverloadedFinallyCallAspect.finally53);
      assertEquals("OverloadedAdvicePOJOCaller,long,Throwable,int",
            OverloadedFinallyCallAspect.finally54);
      assertEquals("OverloadedAdvicePOJOCaller,long,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally55);
      assertEquals("OverloadedAdvicePOJOCaller,long,Throwable",
            OverloadedFinallyCallAspect.finally56);
      assertEquals("OverloadedAdvicePOJOCaller,Throwable,int",
            OverloadedFinallyCallAspect.finally57);
      assertEquals("OverloadedAdvicePOJOCaller,Throwable,Object[]",
            OverloadedFinallyCallAspect.finally58);
      assertEquals("OverloadedAdvicePOJOCaller,Throwable",
            OverloadedFinallyCallAspect.finally59);
      assertEquals("OverloadedAdvicePOJOCaller,int",
            OverloadedFinallyCallAspect.finally60);
      assertEquals("OverloadedAdvicePOJOCaller,Object[]",
            OverloadedFinallyCallAspect.finally61);
      assertEquals("OverloadedAdvicePOJOCaller",
            OverloadedFinallyCallAspect.finally62);
   }
}