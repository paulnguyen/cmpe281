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
package org.jboss.test.aop.jdk15annotateddeclare;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Wraps calls to System.out.println(String), and stores all Strings beginning 
 * with "WARNING:"
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 75505 $
 */
@SuppressWarnings({"unchecked"})
public class SystemOutDecorator extends PrintStream
{
   static PrintStream sysout = null;
   ArrayList warnings = new ArrayList();
   public SystemOutDecorator(OutputStream out)
   {
      super(out);
      sysout = System.out;
   }
   
   public ArrayList getWarnings()
   {
      return warnings;
   }
   
   public void println(String msg)
   {
      super.println(msg);
      if (msg.startsWith("WARNING:"))
      {
         System.out.println(">>>>>");
         super.println(msg);
         System.out.println("<<<<<");
         warnings.add(msg);
      }
   }
   
   public static SystemOutDecorator initialise()
   {
      SystemOutDecorator sys = new SystemOutDecorator(System.out);
      System.setOut(sys);
      return sys;
   }
   
   public void kill()
   {
      System.setOut(sysout);
   }

   String getRidOfAllWhiteSpace(String msg)
   {
      StringBuffer sb = new StringBuffer();
      
      for (int i = 0 ; i < msg.length() ; i++)
      {
         char ch = msg.charAt(i);
         if (ch != '\n' && ch != '\t' && ch != ' ' && ch != '\r')
         {
            sb.append(ch);
         }
      }
      
      return sb.toString();
   }
}
