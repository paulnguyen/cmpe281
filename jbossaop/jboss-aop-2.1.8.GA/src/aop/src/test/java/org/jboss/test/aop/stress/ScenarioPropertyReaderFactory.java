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
package org.jboss.test.aop.stress;

import java.net.URL;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("unchecked")
public class ScenarioPropertyReaderFactory
{
   Class testCaseClass;
   String packageName;
   
   String baseDirectory;
   ScenarioPropertyReader reader;
   
   ScenarioPropertyReaderFactory(Class testCaseClass)
   {
      this.testCaseClass = testCaseClass;
      packageName = testCaseClass.getName().substring("org.jboss.test.aop.stress.".length(), testCaseClass.getName().lastIndexOf(".")); 
      packageName.replace('.', '/');
      
      URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
      System.out.println("class url: " + url);
      String location = url.toString();
      int index = location.indexOf("/target/");
      location = location.substring(0, index);
      
      baseDirectory = location + "/src/resources/test/stress/";     
   }
   
   public ScenarioPropertyReader getPropertyReader(String testName)
   {
      if (reader == null)
      {
         //System properties override everything
         this.reader = new SystemScenarioPropertyReader();
         ScenarioPropertyReader cur = this.reader;
         
         //We can have a file with the same name as the testcase
         ScenarioPropertyReader next = new MutableFileScenatioPropertyLoader(getConfigFileForTest(testName), false);
         cur.setNext(next);
         cur = next;
         
         //We can have a file with the same name as the test case
         next = new FileScenarioPropertyReader(baseDirectory + packageName + "/" + testCaseClass.getSimpleName() + ".properties", false);
         cur.setNext(next);
         cur = next;
         
         //A global configuration file is necessary
         next = new FileScenarioPropertyReader(baseDirectory + "config.properties", true);
         cur.setNext(next);
         cur = next;
         
         //A global configuration file is necessary
         next = new DefaultScenarioPropertyReader();
         cur.setNext(next);
      }
      else
      {
         ScenarioPropertyReader cur = reader;
         while (cur != null)
         {
            if (cur instanceof MutableFileScenatioPropertyLoader)
            {
               ((MutableFileScenatioPropertyLoader)cur).updateProperties(getConfigFileForTest(testName));
            }
            cur = cur.getNext();
         }
      }
      return this.reader;
   }
   
   private String getConfigFileForTest(String testName)
   {
      return baseDirectory + packageName + "/" + testCaseClass.getSimpleName() + "_" + testName + ".properties";
   }
}
