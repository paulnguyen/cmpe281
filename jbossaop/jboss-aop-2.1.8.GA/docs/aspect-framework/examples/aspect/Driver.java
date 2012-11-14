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
import org.jboss.aop.AspectManager;
import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.InstanceAdvisor;

/**
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class Driver
{
   public static void main(String[] args)
   {
      System.out.println("---- POJO ---");
      POJO pojo = new POJO();
      pojo.field++;
      pojo.someMethod();
      System.out.println("---- POJO2 ---");
      POJO2 pojo2 = new POJO2();
      pojo2.field++;
      pojo2.someMethod();

      System.out.println("-- get stats --");
      AspectPerVM vm = (AspectPerVM)AspectManager.instance().getPerVMAspect("AspectPerVM");
      System.out.println("perVM stats: " + vm.constructorCalled + " " + vm.methodCalled + " " + vm.fieldRead + " " + vm.fieldWrite);

      Advisor advisor = ((Advised)pojo)._getAdvisor();
      AspectPerClass perClass = (AspectPerClass)advisor.getPerClassAspect("AspectPerClass");
      System.out.println("POJO perClass stats: " + perClass.constructorCalled + " " + perClass.methodCalled + " " + perClass.fieldRead + " " + perClass.fieldWrite);

      advisor = ((Advised)pojo2)._getAdvisor();
      perClass = (AspectPerClass)advisor.getPerClassAspect("AspectPerClass");
      System.out.println("POJO2 perClass stats: " + perClass.constructorCalled + " " + perClass.methodCalled + " " + perClass.fieldRead + " " + perClass.fieldWrite);

      InstanceAdvisor ia = ((Advised)pojo)._getInstanceAdvisor();
      AspectPerInstance perInstance = (AspectPerInstance)ia.getPerInstanceAspect("AspectPerInstance");
      System.out.println("pojo perInstance stats: " + perInstance.methodCalled + " " + perInstance.fieldRead + " " + perInstance.fieldWrite);
      
      ia = ((Advised)pojo2)._getInstanceAdvisor();
      perInstance = (AspectPerInstance)ia.getPerInstanceAspect("AspectPerInstance");
      System.out.println("pojo2 perInstance stats: " + perInstance.methodCalled + " " + perInstance.fieldRead + " " + perInstance.fieldWrite);
      
      
      
   }
}
