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
package org.jboss.aophelper.util.xml;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jboss.aophelper.core.AopBaseSettings;
import org.jboss.aophelper.core.AopHandler;
import org.jboss.aophelper.core.AopRun;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A AntBuildCreator.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class AntBuildCreator
{
   
   private static Document createAntDOM()
   {
      try 
      {
         
      AopRun aopCompile =  AopHandler.instance().getRun();
      
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = builder.newDocument();
      Element project = doc.createElement("project");
      project.setAttribute("default", "usage");
      project.setAttribute("name", "JBoss AOP build");
      
      doc.appendChild(project);
      createPrepareElement(aopCompile, doc, project);
      createUsageElement(doc, project);
      createCompileElement(aopCompile, doc, project);
      createAopCompileElement(aopCompile, doc, project);
      createRunElement(AopHandler.instance().getRun(), doc, project);
      
      return doc;
      }
      catch (ParserConfigurationException e) 
      {
        System.out.println("ERROR in createAntDOM \n"+e.toString());
        e.printStackTrace();
      }
      return null;
   }
   
   private static void createUsageElement(Document doc, Element project)
   {
      Element usageTarget = doc.createElement("target");
      usageTarget.setAttribute("name", "usage");
      
      Element echo1 = doc.createElement("echo");
      echo1.appendChild(doc.createTextNode("Use one of the following targets:"));
      usageTarget.appendChild(echo1);
      
      Element echo2 = doc.createElement("echo");
      echo2.appendChild(doc.createTextNode("ant aoprun        Run with compile-time weaving."));
      usageTarget.appendChild(echo2);
      
      project.appendChild(usageTarget);
   }
   
   private static void createPrepareElement(AopBaseSettings aopBase, Document doc, Element project)
   {
      Element prepareTarget = doc.createElement("target");
      prepareTarget.setAttribute("name", "prepare");
      
      Element classPath = doc.createElement("path");
      classPath.setAttribute("id", "classpath");
  
      for(String path : aopBase.getClasspath())
      {
         Element pathElement = doc.createElement("pathelement");
         pathElement.setAttribute("path", path);
         classPath.appendChild(pathElement);
      }
      
      Element pathElement = doc.createElement("pathelement");
      pathElement.setAttribute("path", aopBase.getWorkingdir());
      classPath.appendChild(pathElement);
      
      prepareTarget.appendChild(classPath);
      project.appendChild(prepareTarget);
   }
   
   private static void createCompileElement(AopRun aopBase, Document doc, Element project)
   {
      Element compileTarget = doc.createElement("target");
      compileTarget.setAttribute("name", "compile");
      compileTarget.setAttribute("depends", "prepare");
       
      Element javac = doc.createElement("javac");
      if(aopBase.getSrcPath() == null || aopBase.getSrcPath().length() < 1)
         javac.setAttribute("srcdir", aopBase.getWorkingdir());
      else
         javac.setAttribute("srcdir", aopBase.getSrcPath());
      javac.setAttribute("destdir", aopBase.getWorkingdir());
      javac.setAttribute("debug", "on");
      javac.setAttribute("deprecation", "on");
      javac.setAttribute("optimize", "off");
      javac.setAttribute("includes", "**");
      
      Element cp = doc.createElement("classpath");
      cp.setAttribute("refid", "classpath");
      
      javac.appendChild(cp);
      compileTarget.appendChild(javac);
      
      project.appendChild(compileTarget);
   }
   
   public static void createAopCompileElement(AopRun aopBase, Document doc, Element project)
   {
      Element aopCompile = doc.createElement("target");
      aopCompile.setAttribute("name", "aopcompile");
      aopCompile.setAttribute("depends", "compile");
      
      Element taskdef = doc.createElement("taskdef");
      taskdef.setAttribute("name", "aopc");
      taskdef.setAttribute("classname", "org.jboss.aop.ant.AopC");
      taskdef.setAttribute("classpathref", "classpath");
      aopCompile.appendChild(taskdef);
      
      Element aopc = doc.createElement("aopc");
      aopc.setAttribute("compilerclasspathref", "classpath");
      aopc.setAttribute("classpathref","classpath");
      
      Element sysprop = doc.createElement("sysproperty");
      sysprop.setAttribute("key", "jboss.aop.path");
      sysprop.setAttribute("value", aopBase.getAopXmlAsString());
      aopc.appendChild(sysprop);
      
      Element classpath = doc.createElement("classpath");
      classpath.setAttribute("path", "classpath"); //TODO
      aopc.appendChild(classpath);
      
      Element src = doc.createElement("src");
      if(aopBase.getSrcPath() == null || aopBase.getSrcPath().length() < 1)
         src.setAttribute("path",  aopBase.getWorkingdir());
      else
         src.setAttribute("path",  aopBase.getSrcPath());
      aopc.appendChild(src);
      
      Element aoppath = doc.createElement("aoppath");
      aoppath.setAttribute("path", aopBase.getAopXmlAsString());
      aopc.appendChild(aoppath);
      
      aopCompile.appendChild(aopc);
      project.appendChild(aopCompile);
      
   }
   
   public static void createRunElement(AopRun run, Document doc, Element project)
   {
      Element aopRun = doc.createElement("target");
      aopRun.setAttribute("name", "aoprun");
      aopRun.setAttribute("depends", "aopcompile");
      
      Element java = doc.createElement("java");
      java.setAttribute("fork", "yes");
      java.setAttribute("failOnError", "true");
      java.setAttribute("className", run.calculateCorrectExecutionClass()); //TODO
      
      Element sysprop = doc.createElement("sysproperty");
      sysprop.setAttribute("key", "jboss.aop.path");
      sysprop.setAttribute("value", run.getAopXmlAsString());  //TODO
      java.appendChild(sysprop);
      
      Element classpath = doc.createElement("classpath");
      classpath.setAttribute("refid", "classpath");
      java.appendChild(classpath);
      
      aopRun.appendChild(java);
      project.appendChild(aopRun);
      
   }
   
   public static String getAntSettingsAsString() 
   {
      try 
      {
         Document doc = createAntDOM();
        Source source = new DOMSource(doc);

        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);

        // Write the DOM document to the writer
        Transformer xformer = TransformerFactory.newInstance().newTransformer();
        xformer.transform(source, result);

        return writer.toString();
      } 
      catch (TransformerConfigurationException e) 
      {
        e.printStackTrace();
      } 
      catch (TransformerException e) 
      {
        e.printStackTrace();
      }
      return null;
    }
   
   public static void main(String[] args)
   {
      System.out.println(AntBuildCreator.getAntSettingsAsString());
   }

}
