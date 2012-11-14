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
package org.jboss.aop.util;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteStub;

/**
 * An ObjectOutputStream subclass used by the MarshalledValue class to
 * ensure the classes and proxies are loaded using the thread context
 * class loader. Currently this does not do anything as neither class or
 * proxy annotations are used.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 71276 $
 */
public class MarshalledValueOutputStream
   extends ObjectOutputStream
{
   /** Creates a new instance of MarshalledValueOutputStream
    If there is a security manager installed, this method requires a
    SerializablePermission("enableSubstitution") permission to ensure it's
    ok to enable the stream to do replacement of objects in the stream.
    */
   public MarshalledValueOutputStream(OutputStream os) throws IOException
   {
      super(os);
      enableReplaceObject(true);
   }

   /**
    * @throws java.io.IOException   Any exception thrown by the underlying OutputStream.
    */
   protected void annotateClass(Class<?> cl) throws IOException
   {
      super.annotateClass(cl);
   }
   
   /**
    * @throws java.io.IOException   Any exception thrown by the underlying OutputStream.
    */
   protected void annotateProxyClass(Class<?> cl) throws IOException
   {
      super.annotateProxyClass(cl);
   }

   /** Override replaceObject to check for Remote objects that are
    not RemoteStubs.
   */
   protected Object replaceObject(Object obj) throws IOException
   {
      if( (obj instanceof Remote) && !(obj instanceof RemoteStub) )
      {
         Remote remote = (Remote) obj;
         try
         {
            obj = RemoteObject.toStub(remote);
         }
         catch(IOException ignore)
         {
            // Let the Serialization layer try with the orignal obj
         }
      }
      return obj;
   }
}
