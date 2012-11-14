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
package org.jboss.test.aop;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

import org.jboss.aop.AspectXmlLoader;
import org.jboss.aop.eclipsesupport.EclipseTestTransformer;
import org.jboss.test.AbstractTestDelegate;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 70829 $
 */
public class AOPTestDelegate extends AbstractTestDelegate
{
   Properties systemProps;
   
   public AOPTestDelegate(Class<?> clazz)
   {
      // FIXME AOPTestDelegate constructor
      super(clazz);
      
      systemProps = AccessController.doPrivileged(new PrivilegedAction<Properties>() 
      {
         public Properties run()
         {
            return (Properties)System.getProperties().clone();
         }
      });
   }

   public Properties getSystemProperties()
   {
      return systemProps;
   }
   
    /**
     * Undeployment any test specific aop descriptor deployed in setUp.
     */
    public void tearDown() throws Exception
    {
       //TODO Figure out cause of security exception when making this call
//       super.tearDown();
       String deployedByClassLoader = (String)systemProps.get(EclipseTestTransformer.CLASSLOADER_DEPLOYED_XML); 
       if (deployedByClassLoader != null)
       {
          URL url = Thread.currentThread().getContextClassLoader().getResource(deployedByClassLoader);
          AspectXmlLoader.undeployXML(url);
       }
    }
    
}
