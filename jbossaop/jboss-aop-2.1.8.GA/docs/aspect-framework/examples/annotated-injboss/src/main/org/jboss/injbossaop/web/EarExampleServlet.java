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
package org.jboss.injbossaop.web;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.injbossaop.ejb.ExampleSession;
import org.jboss.injbossaop.ejb.ExampleSessionHome;
import org.jboss.injbossaop.lib.ExampleValue;

/** 
 *  
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 *
 */
public class EarExampleServlet extends HttpServlet {

   public void service(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      try 
      {
         String s = request.getParameter("field1");
         System.out.println("**** EarExampleServlet.service()");
         
         InitialContext ctx = new InitialContext();
         Object obj = ctx.lookup("ExampleSession");
         ExampleSessionHome home = (ExampleSessionHome)PortableRemoteObject.narrow(obj, ExampleSessionHome.class);
         ExampleSession exSess = home.create();
         ExampleValue value = exSess.getValue(s); 
         
         request.getSession().setAttribute("exampleValue", new ExampleValue(s));
         request.getRequestDispatcher("/index.jsp").forward(request, response);
      } 
      catch (Exception e) 
      {
         e.printStackTrace();
         throw new ServletException(e);
      } 
   }
}
