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
package org.jboss.aop.instrument;

import java.util.ArrayList;
import java.util.Collection;

import javassist.CannotCompileException;
import javassist.CtMethod;

/**
 * Register pending codes, so that they can be added
 * to a class bytecode in a later moment.
 * @author Flavia Rainone
 */
public class Codifier
{

   private Collection<PendingCode> pendingCodes;
   
   /**
    * Constructor.
    */
   public Codifier()
   {
      this.pendingCodes = new ArrayList<PendingCode>();
   }
   
   /**
    * Register the body of <code>method</code> as a pending code.
    * @param method the method whose body will be <code>coded</code> in a later moment.
    * @param body the future body of <code>method</code>.
    */
   public synchronized void addPendingCode(CtMethod method, String body)
   {
      PendingCode pendingCode = new PendingCode(method, body);
      this.pendingCodes.add(pendingCode);
   }
   
   /**
    * Deploys pending code. In other words: changes the body of methods to their
    * pending bodies, which must have been registered through <code>addPendingCode</code>
    * method.
    * @throws CannotCompileException thrown if javassist cannot compile a pending code,
    * registered through <code>addPendingCode</code> method.
    */
   public synchronized void codifyPending() throws CannotCompileException
   {
      for (PendingCode pendingCode : pendingCodes)
      {
         pendingCode.method.setBody(pendingCode.body);
      }
      pendingCodes.clear();
   }
   
   /**
    * Represents a pending code, to be applied to a method body in the appropriate moment.
    */
   private class PendingCode
   {
      CtMethod method;
      String body;
      
      public PendingCode(CtMethod method, String body)
      {
         this.method = method;
         this.body = body;
      }
   }
}