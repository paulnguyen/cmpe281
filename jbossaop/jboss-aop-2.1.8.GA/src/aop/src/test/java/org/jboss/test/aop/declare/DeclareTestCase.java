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
package org.jboss.test.aop.declare;

import java.util.ArrayList;
import java.util.Iterator;

import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.test.aop.declare.businesslayer.BusinessObject;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unchecked"})
public class DeclareTestCase extends AOPTestWithSetup
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("DotInPointcutNameTester");
      suite.addTestSuite(DeclareTestCase.class);
      return suite;
   }

   public DeclareTestCase(String name)
   {
      super(name);
   }

   public void testLoadtimeDeclare()throws Exception
   {
      System.out.println("*** testLoadTimeDeclare");
      SystemOutDecorator out = null;
      try
      {
         out = SystemOutDecorator.initialise();
         BusinessObject bo = new BusinessObject();
         bo.createVehicles();
         ArrayList expected = getExpectedWarnings();
         ArrayList actual = out.getWarnings();
         compareWarnings(expected, actual);
      } 
      finally
      {
         out.kill();         
      }
   }
   
   private ArrayList getExpectedWarnings()
   {
      //Get rid of all whitespace within strings to make them easier to compare
      ArrayList list = new ArrayList();
		list.add("WARNING:declare-warningcondition'(call(*org.jboss.test.aop.declare.businesslayer.*->*(..))ORcall(org.jboss.test.aop.declare.businesslayer.*->new(..)))ANDwithin(org.jboss.test.aop.declare.datalayer.*)'wasbrokenforconstructorcall:org.jboss.test.aop.declare.datalayer.Truck.new(Ljava/lang/String;)Vcallsorg.jboss.test.aop.declare.businesslayer.BusinessObject.new()VThisisanexpectedwarning:Cannotcallbusinesslayerfromdatalayer.");
		list.add("WARNING:declare-warningcondition'(call(*org.jboss.test.aop.declare.businesslayer.*->*(..))ORcall(org.jboss.test.aop.declare.businesslayer.*->new(..)))ANDwithin(org.jboss.test.aop.declare.datalayer.*)'wasbrokenformethodcall:org.jboss.test.aop.declare.datalayer.Truck.new(Ljava/lang/String;)Vcallsorg.jboss.test.aop.declare.businesslayer.BusinessObject.someMethod()VThisisanexpectedwarning:Cannotcallbusinesslayerfromdatalayer.");
      list.add("WARNING:declare-warningcondition'(call(*org.jboss.test.aop.declare.businesslayer.*->*(..))ORcall(org.jboss.test.aop.declare.businesslayer.*->new(..)))ANDwithin(org.jboss.test.aop.declare.datalayer.*)'wasbrokenforconstructorcall:org.jboss.test.aop.declare.datalayer.Car.badMethod()Vcallsorg.jboss.test.aop.declare.businesslayer.BusinessObject.new()VThisisanexpectedwarning:Cannotcallbusinesslayerfromdatalayer.");
		list.add("WARNING:declare-warningcondition'(call(*org.jboss.test.aop.declare.businesslayer.*->*(..))ORcall(org.jboss.test.aop.declare.businesslayer.*->new(..)))ANDwithin(org.jboss.test.aop.declare.datalayer.*)'wasbrokenformethodcall:org.jboss.test.aop.declare.datalayer.Car.badMethod()Vcallsorg.jboss.test.aop.declare.businesslayer.BusinessObject.someMethod()VThisisanexpectedwarning:Cannotcallbusinesslayerfromdatalayer.");
		
      list.add("WARNING:declare-warningcondition'class($instanceof{org.jboss.test.aop.declare.datalayer.Vehicle})AND!has(*->new(java.lang.String))'wasbrokenforclassorg.jboss.test.aop.declare.datalayer.CarThisisanexpectedwarning:CARshouldbementionedinmsg");
		list.add("WARNING:declare-warningcondition'!class(org.jboss.test.aop.declare.datalayer.Vehicle)ANDclass($instanceof{org.jboss.test.aop.declare.datalayer.Vehicle})AND!hasfield(org.jboss.test.aop.declare.Logger*->logger)'wasbrokenforclassorg.jboss.test.aop.declare.datalayer.FourWheelerThisisanexpectedwarning:FOURWHEELERshouldbementionedinmsg");
		list.add("WARNING:declare-warningcondition'class($instanceof{org.jboss.test.aop.declare.datalayer.Vehicle})AND!has(publicboolean*->accept(org.jboss.test.aop.declare.datalayer.MyVisitor))'wasbrokenforclassorg.jboss.test.aop.declare.datalayer.TruckThisisanexpectedwarning:TRUCKshouldbementionedinmsg");
		
		list.add("WARNING:declare-warningcondition'call(*org.jboss.test.aop.declare.businesslayer.*->*(..))ANDwithincode(*org.jboss.test.aop.declare.datalayer.Car->badMethod())'wasbrokenformethodcall:org.jboss.test.aop.declare.datalayer.Car.badMethod()Vcallsorg.jboss.test.aop.declare.businesslayer.BusinessObject.someMethod()VThisisanexpectedwarning:CannotcallbusinesslayerfromCar.badMethod()");
      
      return list;
   }

   private void compareWarnings(ArrayList expected, ArrayList actual)
   {
      for (Iterator ex = expected.iterator() ; ex.hasNext() ; )
      {
         String want = (String)ex.next();
         for (Iterator ac = actual.iterator() ; ac.hasNext() ; )
         {
            String have = (String)ac.next();
            if (have.equals(want))
            {
               ex.remove();
               ac.remove();
            }
         }
      }
      
      if (actual.size() > 0 || expected.size() > 0)
      {
         StringBuffer sb = new StringBuffer();
         if (actual.size() > 0)
         {
            sb.append("These warnings were raised but not expected\n");
            for (Iterator it = actual.iterator() ; it.hasNext() ; )
            {
               sb.append((String)it.next() + "\n");
            }
         }
         if (expected.size() > 0)
         {
            sb.append("These warnings were expected but not raised\n");
            for (Iterator it = expected.iterator() ; it.hasNext() ; )
            {
               sb.append((String)it.next() + "\n");
            }
         }
         
         throw new RuntimeException(sb.toString());
      }
      
   }
}
