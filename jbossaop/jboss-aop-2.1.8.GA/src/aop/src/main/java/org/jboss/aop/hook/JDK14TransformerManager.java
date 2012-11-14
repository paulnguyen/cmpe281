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
package org.jboss.aop.hook;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70481 $
 */
public class JDK14TransformerManager
{
   public static JDK14Transformer transformer;

   public static byte[] transform(ClassLoader loader, String className, byte[] classBytes)
   {
      if (transformer == null) return null;
      if (isNonAdvisableClassName(className)) return null;

      return transformer.transform(loader, className, classBytes);
   }

   public static boolean isNonAdvisableClassName(String classname)
   {
      return (classname.startsWith("org.jboss.aop.") ||
      classname.endsWith("$aop") ||
      classname.startsWith("javassist") ||
      classname.startsWith("org.jboss.util.") ||
      classname.startsWith("gnu.trove.") ||
      classname.startsWith("EDU.oswego.cs.dl.util.concurrent.") ||
      // System classes
      classname.startsWith("org.apache.crimson") ||
      classname.startsWith("org.apache.xalan") ||
      classname.startsWith("org.apache.xml") ||
      classname.startsWith("org.apache.xpath") ||
      classname.startsWith("org.ietf.") ||
      classname.startsWith("org.omg.") ||
      classname.startsWith("org.w3c.") ||
      classname.startsWith("org.xml.sax.") ||
      classname.startsWith("sunw.") ||
      classname.startsWith("sun.") ||
      classname.startsWith("java.") ||
      classname.startsWith("javax.") ||
      classname.startsWith("com.sun.") ||
      classname.startsWith("junit") || 
      classname.startsWith("jrockit.") ||
      classname.startsWith("com.bea.vm.")  ||
      classname.startsWith("$Proxy")
      );
   }
}
