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

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A BaseConfigParser.
 * NOTE: this class is a ugly bugly xml parser/creator. will rewrite when time permits....
 * 
 * <aophelper>
  <compile>
    <classpaths>
      <classpath></classpath>
    </classpath>
    <aopxmls>
      <aopxml></aopxml>
    </aopxmls>
    <options>
      <verbose>true</verbose>
      <suppress>true</suppress>
      <report>true</report>
      <noopt>true</noopt>
    </options>
    <workingdir></workingdir>
  </compile>

  <run>
    <classpaths>
      <classpath></classpath>
    </classpath>
    <aopxmls>
      <aopxml></aopxml>
    </aopxmls>
    <options>
      <verbose>true</verbose>
      <suppress>true</suppress>
      <report>true</report>
      <noopt>true</noopt>
      <loadtime>true</loadtime>
    </options>
    <workingdir></workingdir>
    <execlass></execlass>
  </run>

<aophelper>
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class BaseConfigParser
{
   
   public static void parse(File file)
   {
   
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db;
         try
         {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            Node aophelper = findElementNode("aophelper", doc);
            Node compile = findElementNode("compile", aophelper);
            Node run = findElementNode("run", aophelper);
            
            AopHandler.instance().setRun( parseCompile(compile));
            AopHandler.instance().setRun(parseRun(run));

         }
         catch (ParserConfigurationException e)
         {
            e.printStackTrace();
         }
         catch (SAXException e)
         {
            e.printStackTrace();
         }
         catch (IOException e)
         {
            e.printStackTrace();
         }
   }
   
   
   private static AopRun parseCompile(Node compile)
   {
      AopRun compileSettings = new AopRun();
      parseBaseSettings(compile, compileSettings);
      
      return compileSettings;
   }
   
   private static void parseBaseSettings(Node root, AopBaseSettings base)
   {
      Node classpaths = findElementNode("classpaths", root);
      base.setClasspath(getNodesText(classpaths.getChildNodes()));
      
      Node aopxmls = findElementNode("aopxmls", root);
      base.setAopXml(getNodesText(aopxmls.getChildNodes()));
      
      Node options = findElementNode("options", root);
      Node verbose = findElementNode("verbose", options);
      base.setVerbose(getNodeText(verbose).equals("true"));
      
      Node suppress = findElementNode("suppress", options);
      base.setSuppress(getNodeText(suppress).equals("true"));
      Node noopt = findElementNode("noopt", options);
      base.setNoopt(getNodeText(noopt).equals("true"));
      Node report = findElementNode("report", options);
      base.setReport(getNodeText(report).equals("true"));
      
      
      Node workingdir =  findElementNode("workingdir", root);
      base.setWorkingdir(getNodeText(workingdir));
   }
   private static AopRun parseRun(Node run)
   {
      AopRun aopRun = new AopRun();
      parseBaseSettings(run, aopRun);
      
      Node loadtime = findElementNode("loadtime", run);
      aopRun.setLoadtime(getNodeText(loadtime).equals("true"));
      Node exeClass = findElementNode("execlass", run);
      aopRun.setExecutionClass(getNodeText(exeClass));
      
      return aopRun;
   }
   
   private static Node findElementNode(String elementName, Node root){

      Node matchingNode = null;
      //Check to see if root is the desired element. If so return root.
      String nodeName = root.getNodeName();

      if((nodeName != null) & (nodeName.equals(elementName))) 
        return root;

      //Check to see if root has any children if not return null
      if(!(root.hasChildNodes()))
        return null;

      //Root has children, so continue searching for them
      NodeList childNodes = root.getChildNodes();
      int noChildren = childNodes.getLength();
      for(int i = 0; i < noChildren; i++)
      {
         if(matchingNode == null)
         {
            Node child = childNodes.item(i);
            matchingNode = findElementNode(elementName,child);
         }
         else break;

      }

      return matchingNode;
    }


   private static String getNodeText(Node node) {
      if (node.getNodeType() == Node.ELEMENT_NODE)   {
         for (Node child = node.getFirstChild();
         child != null;  
         child = child.getNextSibling()) {

            if (child.getNodeType() == Node.TEXT_NODE)  {
               return child.getNodeValue();
            }
         }
      }
      else if (node.getNodeType() == Node.TEXT_NODE)  {
        return node.getNodeValue();
      }
      return null;
    }
   
   private static List<String> getNodesText(NodeList list)
   {
      List<String> text = new ArrayList<String>();
      for(int i=0; i < list.getLength(); i++)
      {
         String t = getNodeText(list.item(i)).trim();
         if(t != null && t.length() > 0)
            text.add(t);
      }
      
      return text;
   }
  
//   public static void main(String[] args)
//   {
//      BaseConfigParser.parse(new File("/home/stalep/jboss/svn/jboss-aop/aophelper/test_config.xml"));
//      
//      System.out.println("OUT: \n"+BaseConfigParser.getDOMAsString(createDOM()));
//   }
   
   public static String getSettingsAsString() 
   {
      try 
      {
         Document doc = createDOM();
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
   
   private static Document createDOM() 
   {
      try 
      {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement("aophelper");
        Element compile = doc.createElement("compile");
        Element run = doc.createElement("run");
       populateCompile(compile, doc);
       populateRun(run, doc);
       root.appendChild(compile);
       root.appendChild(run);
       doc.appendChild(root);
        
       return doc;
      } 
      catch (ParserConfigurationException e) 
      {
        System.out.println("ERROR in CreateDOM \n"+e.toString());
        e.printStackTrace();
      }
      return null;
    }
   
   private static void populateCompile(Element compile, Document doc)
   {
      populateBase(compile, doc, AopHandler.instance().getRun());
   }
   
   private static void populateRun(Element run, Document doc)
   {
      populateBase(run, doc, AopHandler.instance().getRun());
      Element loadtime = doc.createElement("loadtime");
      loadtime.appendChild( doc.createTextNode(Boolean.toString(AopHandler.instance().getRun().isLoadtime())));
      run.appendChild(loadtime);
      Element exeClass = doc.createElement("execlass");
      if(AopHandler.instance().getRun().getExecutionClass() != null)
         exeClass.appendChild( doc.createTextNode(AopHandler.instance().getRun().getExecutionClass()));
      run.appendChild(exeClass);
      
   }
   
   private static void populateBase(Element base, Document doc, AopBaseSettings settings)
   {
      Element classpaths = doc.createElement("classpaths");
      for(String cp : settings.getClasspath())
      {
         Element classpath = doc.createElement("classpath");
         classpath.appendChild( doc.createTextNode(cp));
         classpaths.appendChild(classpath);
      }
      base.appendChild(classpaths);
      
      Element aopxmls = doc.createElement("aopxmls");
      for(String xml : settings.getAopXml())
      {
         Element xmlaop = doc.createElement("aopxml");
         xmlaop.appendChild( doc.createTextNode(xml));
         aopxmls.appendChild(xmlaop);
      }
      base.appendChild(aopxmls);
      
      Element options = doc.createElement("options");
      Element verbose = doc.createElement("verbose");
      verbose.appendChild( doc.createTextNode(Boolean.toString(settings.isVerbose())));
      options.appendChild(verbose);
      Element suppress = doc.createElement("suppress");
      suppress.appendChild( doc.createTextNode(Boolean.toString(settings.isSuppress())));
      options.appendChild(suppress);
      Element noopt = doc.createElement("noopt");
      noopt.appendChild( doc.createTextNode(Boolean.toString(settings.isNoopt())));
      options.appendChild(noopt);
      Element report = doc.createElement("report");
      report.appendChild( doc.createTextNode(Boolean.toString(settings.isReport())));
      options.appendChild(report);
      
      base.appendChild(options);
      
      Element workingdir = doc.createElement("workingdir");
      if(settings.getWorkingdir() != null)
         workingdir.appendChild( doc.createTextNode(settings.getWorkingdir()));
      base.appendChild(workingdir);
   }
   



}
