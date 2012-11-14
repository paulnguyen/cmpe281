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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.jboss.aop.AspectManager;
import org.jboss.logging.Logger;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class FileScenarioPropertyReader extends ScenarioPropertyReader
{
   final static Logger log = Logger.getLogger(FileScenarioPropertyReader.class); 
   String location;
   boolean strict;
   
   FileScenarioPropertyReader(String location, boolean strict)
   {
      this.location = location;
      this.strict = strict;
   }
   
   @Override
   Properties loadProperties()
   {
      try
      {
         URL url = new URL(location);
         File file = new File(url.getFile());
         
         if (!file.exists())
         {
            if (strict)
            {
               throw new RuntimeException("Could not find config file " + file);
            }
               
            if (AspectManager.verbose)
            {
               System.out.println("Could not find test/scenario config file " + file + ". Creating passthrough reader");
            }
            return null;
         }
         else
         {
            InputStream in = new FileInputStream(url.getFile());
            Properties properties = new Properties();
            properties.load(in);
            return properties;
         }
      }
      catch (MalformedURLException e)
      {
         throw new RuntimeException(e);
      }
      catch (FileNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      catch (IOException e)
      {
         throw new RuntimeException(e);
      }
   }
}
