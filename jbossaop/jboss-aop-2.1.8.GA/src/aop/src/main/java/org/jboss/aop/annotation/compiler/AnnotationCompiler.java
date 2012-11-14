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
package org.jboss.aop.annotation.compiler;



/**
 * Convert doclet tags to JBoss AOP annotation XML
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 *
 **/
public class AnnotationCompiler
{
   public static void main(String[] args) throws Exception
   {
      AnnotationCompiler c = new AnnotationCompiler();
      c.compile(args);
   }

   public void usage()
   {
      System.err.println("Usage: annotationc [-bytecode] [-xml -o <output xml file> ] <files>+");
   }

   public void compile(String[] args) throws Exception
   {
      if (args.length == 0)
      {
         usage();
         System.exit(1);
         return;
      }
      for (int i = 0; i < args.length; i++)
      {
         if (args[i].equals("-bytecode"))
         {
            ByteCodeAnnotationCompiler compiler = new ByteCodeAnnotationCompiler();
            try
            {
               compiler.compile(args);
            }
            catch (Exception e)
            {
               e.printStackTrace(System.out);
               throw e;
            }
            return;
         }
         if (args[i].equals("-xml"))
         {
            XmlAnnotationCompiler compiler = new XmlAnnotationCompiler();
            compiler.compile(args);
            return;
         }
      }
      usage();
      System.exit(1);
      return;
   }
}
