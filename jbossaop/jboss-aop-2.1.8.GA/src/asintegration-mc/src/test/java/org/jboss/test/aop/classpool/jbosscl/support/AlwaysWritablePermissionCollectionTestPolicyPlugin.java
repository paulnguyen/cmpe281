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
package org.jboss.test.aop.classpool.jbosscl.support;

import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.SecurityPermission;
import java.util.Enumeration;
import java.util.PropertyPermission;

import org.jboss.test.security.TestsPolicyPlugin;

/**
 * The original TestPolicyPlugin reuses the underlying PermissionCollection. This causes problems
 * for tests creating several ProtectionDomains, since the ProtectionDomain's constructor 
 * marks the PolicyCollection as read-only so we get errors
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class AlwaysWritablePermissionCollectionTestPolicyPlugin extends TestsPolicyPlugin
{
   public AlwaysWritablePermissionCollectionTestPolicyPlugin(Class<?> clazz)
   {
      super(clazz);
   }

   public synchronized PermissionCollection getPermissions(CodeSource codesource)
   {
      return new AlwaysWritablePermissionCollection(super.getPermissions(codesource));
   }
   
   public static void initialisePlugin()
   {
      System.setProperty("org.jboss.test.security.PolicyPlugin", AlwaysWritablePermissionCollectionTestPolicyPlugin.class.getName());
   }
   
   public static void cleanupPlugin()
   {
      System.clearProperty("org.jboss.test.security.PolicyPlugin");
   }
   
   private static class AlwaysWritablePermissionCollection extends PermissionCollection
   {
      private static final long serialVersionUID = 1L;
      
      volatile PermissionCollection lastDelegate;
      volatile PermissionCollection delegate;
      
      final static Permission GET_POLICY = new SecurityPermission("getPolicy");
      final static Permission PARENT_PKGS = new PropertyPermission("jboss.test.parent.pkgs", "read");
      
      private AlwaysWritablePermissionCollection(PermissionCollection delegate)
      {
         this.delegate = delegate;
      }

      public void add(Permission permission)
      {
         delegate.add(permission);
      }

      public Enumeration<Permission> elements()
      {
         return delegate.elements();
      }

      public boolean equals(Object obj)
      {
         return delegate.equals(obj);
      }

      public int hashCode()
      {
         return delegate.hashCode();
      }

      public boolean implies(Permission permission)
      {
         return delegate.implies(permission);
      }

      public boolean isReadOnly()
      {
         return delegate.isReadOnly();
      }

      public void setReadOnly()
      {
         //ignore
      }

      public String toString()
      {
         return delegate.toString();
      }
   }
}
