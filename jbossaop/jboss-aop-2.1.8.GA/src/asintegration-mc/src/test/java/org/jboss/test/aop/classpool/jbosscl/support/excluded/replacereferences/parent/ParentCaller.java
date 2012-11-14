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
package org.jboss.test.aop.classpool.jbosscl.support.excluded.replacereferences.parent;

import org.jboss.test.aop.classpool.jbosscl.support.excluded.replacereferences.Invoked;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ParentCaller
{
   public static void test()
   {
      Invoked.invoked = false;
      Parent parent = new Parent();
      assertCond(Invoked.invoked, "Invoked not called for Parent constructor");
      
      Invoked.invoked = false;
      parent.parent = 5;
      assertCond(Invoked.invoked, "Invoked not called for Parent.parent write");
      
      Invoked.invoked = false;
      int i = parent.parent;
      assertCond(Invoked.invoked, "Invoked not called for Parent.parent read");
      assertCond(i == 5, "Expected 5 for Parent.parent, was " + i);
   }
   
   private static void assertCond(boolean b, String msg)
   {
      if (!b)
      {
         throw new RuntimeException(msg);
      }
   }
}
