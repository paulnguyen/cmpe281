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

import java.io.IOException;
import java.net.URL;

import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 79661 $
 */
public class AOPTestWithSetup extends AbstractTestCaseWithSetup
{
   public static final String DISABLE_SECURITY_KEY = "jboss.aop.disable.security";
   
   public AOPTestWithSetup(String arg0)
   {
      super(arg0);
   }

   /**
    * Default setup with security manager enabled
    * 
    * @param clazz the class
    * @return the delegate
    * @throws Exception for any error
    */
   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      AOPTestDelegate delegate = new AOPTestDelegate(clazz);
      boolean disableSecurity = Boolean.valueOf(System.getProperty(DISABLE_SECURITY_KEY, "false"));
      if (!disableSecurity)
      {
         delegate.enableSecurity = true;
      }
      return delegate;
   }
   
   protected URL getURLRelativeToProjectRoot(String relativePath) throws IOException
   {
      URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
      String location = url.toString();
      int index = location.indexOf("/target/");
      location = location.substring(0, index);
      
      location = location + relativePath;
      return new URL(location);
   }
}
