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
package org.jboss.test.aop.schema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jboss.aop.AspectXmlLoader;
import org.jboss.test.aop.AOPTestWithSetup;
import org.jboss.util.xml.DOMWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class SchemaTestCase extends AOPTestWithSetup
{
   
   public SchemaTestCase(String arg0)
   {
      super(arg0);
   }

   public void testSchema() throws Exception
   {
      URL schemaUrl = AspectXmlLoader.class.getResource("/jboss-aop_2_0.xsd");
      assertNotNull(schemaUrl);
      Schema schema = getSchema(schemaUrl);
      List<File> files = getAopXmlFiles();
      for (File file : files)
      {
         System.out.println("Validating " + file);
         Reader rdr = massageFile(file, schemaUrl);
         parseAndValidateXmlFile(rdr, schema);
      }
   }
   
   private void parseAndValidateXmlFile(Reader reader, Schema schema) throws Exception
   {
      MyErrorHandler handler = new MyErrorHandler();
      try
      {
         DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
         docBuilderFactory.setNamespaceAware(true);
         docBuilderFactory.setValidating(true);
         docBuilderFactory.setAttribute("http://apache.org/xml/features/validation/schema", true);
         docBuilderFactory.setSchema(schema);
         InputSource source = new InputSource(reader);

         DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
         docBuilder.setErrorHandler(handler);
         docBuilder.parse(source);
      }
      catch(Exception e)
      {
         throw new Exception("Error reading file", e);
      }
      finally
      {
         reader.close();
      }
      
      if (handler.getType() != null && handler.getException() != null)
      {
         throw new Exception(handler.getType(), handler.getException());
      }
   }
   
   private Reader massageFile(File file, URL schemaUrl) throws Exception
   {
      Reader is = new BufferedReader(new FileReader(file));
      try
      {
         DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
         InputSource source = new InputSource(is);
   
         DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
         Document doc = docBuilder.parse(source);
         
         Element root = doc.getDocumentElement();
         NamedNodeMap map = root.getAttributes();

         if (map.getLength() == 0)
         {
            StringWriter writer = new StringWriter();
            
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:noNamespaceSchemaLocation", schemaUrl.toString());

            DOMWriter domWriter = new DOMWriter(new BufferedWriter(writer));
            domWriter.setWriteXMLDeclaration(true);
            domWriter.print(doc);
            
            return new StringReader(writer.getBuffer().toString());
         }
      }
      finally
      {
         is.close();
      }
      
      return new BufferedReader(new FileReader(file));
   }
   
   private Schema getSchema(URL url) throws SAXException
   {
      SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = factory.newSchema(url);
      return schema;
   }
   
   private List<File> getAopXmlFiles() throws IOException, URISyntaxException
   {
      URL url = getURLRelativeToProjectRoot("/src/resources/test/");
      File root = new File(url.toURI());
      List<File> files = new ArrayList<File>();
      getAopXmlFiles(files, root);
      return files;
   }
   
   private void getAopXmlFiles(List<File> files, File directory)
   {
      File[] dirFiles = directory.listFiles();
      for (File file : dirFiles)
      {
         if (file.getName().equals(".svn"))
         {
            continue;
         }
         if (file.isDirectory())
         {
            getAopXmlFiles(files, file);
            continue;
         }
         
         if (file.getPath().endsWith("-aop.xml"))
         {
            files.add(file);
         }
      }
   }
   
   private static class MyErrorHandler implements ErrorHandler
   {
      String type;
      SAXParseException exception;
      
      public void error(SAXParseException exception) throws SAXException
      {
         this.exception = exception;
         type = "Parser Error";
      }

      public void fatalError(SAXParseException exception) throws SAXException
      {
         this.exception = exception;
         type = "Parser Fatal Error";
      }

      public void warning(SAXParseException exception) throws SAXException
      {
         this.exception = exception;
         type = "Parser Warning";
      }

      protected String getType()
      {
         return type;
      }

      protected SAXParseException getException()
      {
         return exception;
      }
   }     
}
