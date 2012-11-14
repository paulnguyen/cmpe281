/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.proxyfactory.support;

import java.io.PrintWriter;
import java.util.Set;

import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.security.auth.Subject;

/**
 * TestManagedConnectionFactory.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 84374 $
 */
public class TestManagedConnectionFactory implements ManagedConnectionFactory
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -7340980450728732028L;

   public Object createConnectionFactory() throws ResourceException
   {
      throw new org.jboss.util.NotImplementedException("createConnectionFactory");
   }

   public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException
   {
      return cxManager;
   }

   public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException
   {
      throw new org.jboss.util.NotImplementedException("createManagedConnection");
   }

   public PrintWriter getLogWriter() throws ResourceException
   {
      throw new org.jboss.util.NotImplementedException("getLogWriter");
   }

   public ManagedConnection matchManagedConnections(@SuppressWarnings("unchecked")Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException
   {
      throw new org.jboss.util.NotImplementedException("matchManagedConnections");
   }

   public void setLogWriter(PrintWriter out) throws ResourceException
   {
      throw new org.jboss.util.NotImplementedException("setLogWriter");
   }

}
