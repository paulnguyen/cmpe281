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

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import org.jboss.aop.AspectManager;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.standalone.Compiler;

/**
 *  A few handy methods and common things used by the other Transformers
 * @author <a href="mailto:kabirkhan@bigfoot.com">Kabir Khan</a>
 * @version $Revision: 83504 $
 *
 */
public class TransformerCommon {

   final static URL[] NO_URLS = new URL[0];
   final static CtClass[] EMPTY_CTCLASS_ARRAY = new CtClass[0];
   final static String WEAK_REFERENCE = WeakReference.class.getName();

   public static boolean isCompileTime()
   {
      return Compiler.loader != null;
   }

   public static void compileOrLoadClass(CtClass classForPackage, CtClass newClass)
   {
      compileOrLoadClass(classForPackage, newClass, isCompileTime());
   }

   /** Compiles the class to file or adds it to the class pool
    *
    * @param classForPackage The class to be used to determine the directory to place the class in
    * @param invocation The class to be comiled/added to class pool
    * @throws Exception
    */
   public static void compileOrLoadClass(CtClass classForPackage, CtClass newClass, boolean compile)
   {
      try
      {
         // If compile time
         if (compile)
         {
            File file;
            URLClassLoader loader = Compiler.loader;
            if (loader == null)
            {
               loader = new URLClassLoader(NO_URLS, SecurityActions.getContextClassLoader());
            }
            URL url = loader.getResource(
                  classForPackage.getName().replace('.', '/') + ".class");
            String path = url.toString();
            path = path.substring(0, path.lastIndexOf('/') + 1);
            path = path + newClass.getSimpleName() + ".class";
            URI newUrl = new URI(path);
            file = new File(newUrl);
            FileOutputStream fp = new FileOutputStream(file);
            fp.write(newClass.toBytecode());
            fp.close();
         }
         else
         {
            // if load time
            if (System.getSecurityManager() == null)
            {
               ToClassAction.NON_PRIVILEGED.toClass(newClass, null, null);
            }
            else
            {
               ToClassAction.PRIVILEGED.toClass(newClass, null, null);
            }
         }

         if (AspectManager.debugClasses)
         {
            debugWriteFile(newClass);
         }
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }

   public static Class<?> toClass(CtClass newClass, ProtectionDomain pd) throws CannotCompileException
   {
      if (System.getSecurityManager() == null)
      {
         return ToClassAction.NON_PRIVILEGED.toClass(newClass, null, pd);
      }
      else
      {
         return ToClassAction.PRIVILEGED.toClass(newClass, null, pd);
      }
   }

   public static Class<?> toClass(CtClass newClass, ClassLoader loader, ProtectionDomain pd)
      throws CannotCompileException
   {
      if (System.getSecurityManager() == null)
      {
         return ToClassAction.NON_PRIVILEGED.toClass(newClass, loader, pd);
      }
      else
      {
         return ToClassAction.PRIVILEGED.toClass(newClass, loader, pd);
      }
   }
   
   public static void debugWriteFile(CtClass clazz)
   {
      if (System.getSecurityManager() == null)
      {
         DebugWriteFileAction.NON_PRIVILEGED.debugWriteFile(clazz);
      }
      else
      {
         DebugWriteFileAction.PRIVILEGED.debugWriteFile(clazz);
      }
   }

   protected static void addInfoField(Instrumentor instrumentor, String infoClassName, String infoName,
         int modifiers, CtClass addTo, boolean weak, CtField.Initializer init, boolean synthetic) throws NotFoundException, CannotCompileException
   {
      if (weak)
      {
         addWeakReferenceInfoField(instrumentor, infoClassName, infoName, modifiers, addTo, init, synthetic);
      }
      else
      {
         addStrongReferenceInfoField(instrumentor, infoClassName, infoName, modifiers, addTo, init, synthetic);
      }
   }

   private static void addWeakReferenceInfoField(Instrumentor instrumentor, String infoClassName, String infoName,
         int modifiers, CtClass addTo, CtField.Initializer init, boolean synthetic) throws NotFoundException, CannotCompileException
   {
      CtClass type = instrumentor.forName(WEAK_REFERENCE);
      CtField field = new CtField(type, infoName, addTo);
      field.setModifiers(modifiers);
      if (synthetic)
      {
         Instrumentor.addSyntheticAttribute(field);
      }
      addTo.addField(field, init);
   }

   private static void addStrongReferenceInfoField(Instrumentor instrumentor, String infoClassName, String infoName,
         int modifiers, CtClass addTo, CtField.Initializer init, boolean synthetic) throws NotFoundException, CannotCompileException
   {
      CtClass type = instrumentor.forName(infoClassName);
      CtField field = new CtField(type, infoName, addTo);
      field.setModifiers(modifiers);
      if (synthetic)
      {
         Instrumentor.addSyntheticAttribute(field);
      }
      addTo.addField(field, init);
   }

   protected static String infoFromWeakReference(String infoClassName, String localName, String infoName)
   {
         return infoClassName + " " + localName + " = (" + infoClassName + ")" + infoName + ".get();";
   }

   /**
    * Utility method to make a new class in a pool. It makes sure that the class is registered with the pool, so others can find it.
    */
   public static CtClass makeNestedClass(CtClass outer, String name, boolean isStatic, int modifiers, CtClass superClass) throws CannotCompileException
   {
      CtClass inner = makeNestedClass(outer, name, true);
      inner.setModifiers(modifiers);
      inner.setSuperclass(superClass);
      return inner;
   }

   /**
    * Utility method to make a new class in a pool. It makes sure that the class is registered with the pool, so others can find it.
    */
   public static CtClass makeNestedClass(CtClass outer, String name, boolean isStatic) throws CannotCompileException
   {
      final String classname = outer.getName() + "$" + name;
      try
      {
         registerGeneratedClass(outer.getClassPool(), classname);
         CtClass inner = outer.makeNestedClass(name, true);
         return inner;
      }
      catch (RuntimeException e)
      {
         unregisterGeneratedClass(outer.getClassPool(), classname);
         throw e;
      }
   }

   /**
    * Utility method to make a new class in a pool. It makes sure that the class is registered with the pool, so others can find it.
    */
   public static CtClass makeClass(ClassPool pool, String name)
   {
      registerGeneratedClass(pool, name);
      return pool.makeClass(name);
   }

   /**
    * Utility method to make a new class in a pool. It makes sure that the class is registered with the pool, so others can find it.
    */
   public static CtClass makeClass(ClassPool pool, String name, CtClass superClass)
   {
      registerGeneratedClass(pool, name);
      try
      {
         return pool.makeClass(name, superClass);
      }
      catch(RuntimeException e)
      {
         unregisterGeneratedClass(pool, name);
         throw e;
      }
   }

   private static void registerGeneratedClass(ClassPool pool, String name)
   {
      AOPClassPool aopPool = getAOPClassPool(pool);
      if (aopPool != null)
      {
         aopPool.registerGeneratedClass(name);
      }
   }
   
   private static void unregisterGeneratedClass(ClassPool pool, String name)
   {
      AOPClassPool aopPool = getAOPClassPool(pool);
      if (aopPool != null)
      {
         aopPool.doneGeneratingClass(name);
      }
   }
   
   private static AOPClassPool getAOPClassPool(ClassPool pool)
   {
      if (pool instanceof AOPClassPool)
      {
         return (AOPClassPool)pool;
      }
      return null;
   }

   private interface ToClassAction
   {
      Class<?> toClass(CtClass clazz, ClassLoader loader, ProtectionDomain pd)
         throws CannotCompileException;

      ToClassAction PRIVILEGED = new ToClassAction()
      {
         public Class<?> toClass(final CtClass clazz, final ClassLoader loader, final ProtectionDomain pd)
            throws CannotCompileException
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<Class<?>>()
               {
                  public Class<?> run() throws Exception
                  {
                     if (AspectManager.debugClasses)
                     {
                        clazz.debugWriteFile();
                     }
                     return clazz.toClass(loader, pd);
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               Exception actual = e.getException();
               if (actual instanceof CannotCompileException)
               {
                  throw (CannotCompileException)actual;
               }
               throw new RuntimeException(actual);
            }
         }
      };

      ToClassAction NON_PRIVILEGED = new ToClassAction()
      {
         public Class<?> toClass(CtClass clazz, ClassLoader loader, ProtectionDomain pd)
            throws CannotCompileException
         {
            if (AspectManager.debugClasses)
            {
               clazz.debugWriteFile();
            }
            return clazz.toClass(loader, pd);
         }
      };
   }

   private interface DebugWriteFileAction
   {
      void debugWriteFile(CtClass clazz);

      DebugWriteFileAction PRIVILEGED = new DebugWriteFileAction()
      {
         public void debugWriteFile(final CtClass clazz)
         {
            AccessController.doPrivileged(new PrivilegedAction<Object>()
            {
               public Object run()
               {
                  clazz.debugWriteFile();
                  return null;
               }
            });
         }
      };

      DebugWriteFileAction NON_PRIVILEGED = new DebugWriteFileAction()
      {
         public void debugWriteFile(CtClass clazz)
         {
            clazz.debugWriteFile();
         }
      };
   }
}
