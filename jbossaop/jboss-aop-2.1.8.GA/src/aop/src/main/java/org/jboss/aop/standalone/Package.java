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
package org.jboss.aop.standalone;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71279 $
 */
public class Package implements java.io.Serializable
{
   static final long serialVersionUID = 6188655039267365373L;
   public HashMap<String, Advisor> advisors = new HashMap<String, Advisor>();
   public HashMap<String, Package> packages = new HashMap<String, Package>();
   public String name;

   public Package(String name)
   {
      this.name = name;
   }

   static void parse(Class<?> clazz, Package root)
   {
      Advisor advisor = AspectManager.instance().findAdvisor(clazz);
      StringTokenizer tokenizer = new StringTokenizer(clazz.getName(), ".");
      while (tokenizer.hasMoreTokens())
      {
         String pkgName = tokenizer.nextToken();
         if (tokenizer.hasMoreTokens())
         {
            Package subpkg = root.packages.get(pkgName);
            if (subpkg == null)
            {
               subpkg = new Package(pkgName);
               root.packages.put(pkgName, subpkg);
            }
            root = subpkg;
         }
         else
         {
            root.advisors.put(pkgName, advisor);
         }
      }
   }

   public static Package aopClassMap()
   {
      Map<Class<?>, WeakReference<Advisor>> advisors = AspectManager.instance().getAdvisors();
      Iterator<Class<?>> it = advisors.keySet().iterator();
      Package root = new Package("classes");
      while (it.hasNext())
      {
         Class<?> clazz = it.next();
         parse(clazz, root);
      }
      return root;
   }
}
