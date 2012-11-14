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
package org.jboss.test.aop.classpool.jbosscl.test;

import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Collection;

import javassist.ClassPool;
import javassist.CodeConverter;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import junit.framework.Test;

import org.jboss.aop.AspectManager;
import org.jboss.aop.classpool.AOPClassPool;
import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.classloading.spi.DomainClassLoader;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.util.loading.Translator;

/**
 * Test that field/constructor wrapper replacement works since there were some problems in AS5
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class ClassPoolWithReplaceReferencesTestCase extends JBossClClassPoolTest
{
   final static String STRING = String.class.getName();

   public final static URL JAR_PARENT = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-replacereferences-parent.jar");

   public final static URL JAR_CHILD = getURLRelativeToProjectRoot("target/jboss-aop-asintegration-mc-test-classpool-replacereferences-child.jar");

   public final static String PACKAGE_REPLACEMENT = PACKAGE_ROOT + ".replacereferences";

   public final static String PACKAGE_REPLACEMENT_CHILD = PACKAGE_REPLACEMENT + ".child";

   public final static String PACKAGE_REPLACEMENT_PARENT = PACKAGE_REPLACEMENT + ".parent";

   public final static String CLASS_INVOKED = PACKAGE_REPLACEMENT + ".Invoked";

   public final static String CLASS_PARENT_CALLER = PACKAGE_REPLACEMENT_PARENT + ".ParentCaller";

   public final static String CLASS_CHILD_CALLER = PACKAGE_REPLACEMENT_CHILD + ".ChildCaller";

   public final String x = "org.jboss.test.aop.classpool.jbosscl.support.excluded";

   private final static Translator TRANSLATOR = new MyTranslator();

   public ClassPoolWithReplaceReferencesTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(ClassPoolWithReplaceReferencesTestCase.class);
   }

   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      return new AlwaysWritablePermissionCollectionTestPolicyPluginTestDelegate(clazz);
   }
   
   public void testParentLoadedParentDelegation() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool scopedPool = null;
      try
      {
         getSystem().setTranslator(TRANSLATOR);
         globalPool = createClassPool("GLOBAL", true, JAR_PARENT);
         scopedPool = createChildDomainParentFirstClassPool("SCOPED", "SCOPED", true, JAR_CHILD);

         loadClassAndRunTest(CLASS_PARENT_CALLER, globalPool.getClassLoader());
         loadClassAndRunTest(CLASS_CHILD_CALLER, scopedPool.getClassLoader());
      }
      finally
      {
         unregisterClassPool(globalPool);
         unregisterClassPool(scopedPool);
         unregisterDomain(scopedPool);
         getSystem().setTranslator(null);
      }
   }

   public void testParentLoadedNoParentDelegation() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool scopedPool = null;
      try
      {
         getSystem().setTranslator(TRANSLATOR);
         globalPool = createClassPool("GLOBAL", true, JAR_PARENT);
         scopedPool = createChildDomainParentLastClassPool("SCOPED", "SCOPED", true, JAR_CHILD);

         loadClassAndRunTest(CLASS_PARENT_CALLER, globalPool.getClassLoader());
         loadClassAndRunTest(CLASS_CHILD_CALLER, scopedPool.getClassLoader());
      }
      finally
      {
         unregisterClassPool(globalPool);
         unregisterClassPool(scopedPool);
         unregisterDomain(scopedPool);
         getSystem().setTranslator(null);
      }
   }

   public void testParentNotLoadedParentDelegation() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool scopedPool = null;
      try
      {
         getSystem().setTranslator(TRANSLATOR);
         globalPool = createClassPool("GLOBAL", true, JAR_PARENT);
         scopedPool = createChildDomainParentFirstClassPool("SCOPED", "SCOPED", true, JAR_CHILD);

         loadClassAndRunTest(CLASS_CHILD_CALLER, scopedPool.getClassLoader());
      }
      finally
      {
         unregisterClassPool(globalPool);
         unregisterClassPool(scopedPool);
         unregisterDomain(scopedPool);
         getSystem().setTranslator(null);
      }
   }

   public void testParentNotLoadedNoParentDelegation() throws Exception
   {
      ClassPool globalPool = null;
      ClassPool scopedPool = null;
      try
      {
         getSystem().setTranslator(TRANSLATOR);
         globalPool = createClassPool("GLOBAL", true, JAR_PARENT);
         scopedPool = createChildDomainParentLastClassPool("SCOPED", "SCOPED", true, JAR_CHILD);

         loadClassAndRunTest(CLASS_CHILD_CALLER, scopedPool.getClassLoader());
      }
      finally
      {
         unregisterClassPool(globalPool);
         unregisterClassPool(scopedPool);
         unregisterDomain(scopedPool);
         getSystem().setTranslator(null);
      }
   }

   private void loadClassAndRunTest(String classname, ClassLoader loader) throws Exception
   {
      Class<?> caller = loader.loadClass(classname);
      Method m = caller.getMethod("test");
      m.invoke(null, new Object[]
      {});
   }

   final static class MyTranslator implements Translator
   {
      public void unregisterClassLoader(DomainClassLoader loader)
      {
      }

      public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws Exception
      {
         if (!className.startsWith(PACKAGE_REPLACEMENT))
         {
            return null;
         }
         if (className.endsWith("Invoked"))
         {
            return null;
         }

         ClassPool pool = AspectManager.instance().registerClassLoader(loader);
         CtClass clazz = pool.get(className);
         if (className.endsWith("Caller"))
         {
            instrumentReplaceReferences(clazz);
         }
         else
         {
            instrumentCreateWrappers(clazz);
         }

         if (clazz.isModified())
         {
            return clazz.toBytecode();
         }
         return null;
      }

      private CtClass instrumentCreateWrappers(CtClass clazz)
      {
         try
         {
            if (!clazz.getName().startsWith(PACKAGE_REPLACEMENT))
            {
               return clazz;
            }
            addWrappersToClass(clazz);
         }
         catch (Exception e)
         {
            System.err.println("Exception creating wrappers for " + clazz);
            e.printStackTrace();
         }
         return clazz;
      }

      private CtClass instrumentReplaceReferences(CtClass clazz) throws Exception
      {
         if (!clazz.getName().startsWith(PACKAGE_REPLACEMENT))
         {
            return clazz;
         }
         replaceReferences(clazz);
         return clazz;
      }

      public void unregisterClassLoader(ClassLoader loader)
      {
      }

      private void addWrappersToClass(CtClass clazz) throws Exception
      {
         addFieldWrappers(clazz);
         addConstructorWrappers(clazz);
      }

      private void addFieldWrappers(CtClass clazz) throws Exception
      {
         CtClass objectCt = clazz.getClassPool().getCtClass(Object.class.getName());

         for (CtField field : clazz.getFields())
         {
            if (field.getDeclaringClass() == objectCt)
            {
               continue;
            }
            CtMethod rmethod = 
               CtNewMethod.make(
                     Modifier.PUBLIC | Modifier.STATIC,
                     CtClass.intType, 
                     getFieldReadWrapperName(field.getName()),
                     new CtClass[]{objectCt}, 
                     null, 
                     "{" + CLASS_INVOKED + ".invoked = true; return ((" + clazz.getName() + ")$1)." + field.getName() + ";}", 
                     clazz);
            clazz.addMethod(rmethod);


            CtMethod wmethod = 
               CtNewMethod.make(
                     Modifier.PUBLIC | Modifier.STATIC,
                     CtClass.voidType, 
                     getFieldWriteWrapperName(field.getName()),
                     new CtClass[]{objectCt, field.getType()}, 
                     null,
                     "{" + CLASS_INVOKED + ".invoked = true; ((" + clazz.getName() + ")$1)." + field.getName() + "=(int)$2;}", 
                     clazz);
            clazz.addMethod(wmethod);
         }
      }

      private void addConstructorWrappers(CtClass clazz) throws Exception
      {
         for (CtConstructor ctor : clazz.getConstructors())
         {
            CtMethod wrapper = CtNewMethod.make(clazz, getConstructorName(clazz), ctor.getParameterTypes(), null,
                  "{" + CLASS_INVOKED + ".invoked = true; return new " + clazz.getName() + "($$);}", clazz);
            wrapper.setModifiers(Modifier.PUBLIC | Modifier.STATIC);
            clazz.addMethod(wrapper);
         }
      }

      private void replaceReferences(final CtClass clazz) throws Exception
      {
         CodeConverter conv = new CodeConverter();
         AOPClassPool pool = AOPClassPool
               .createAOPClassPool(clazz.getClassPool(), AOPClassPoolRepository.getInstance());
         Collection<String> refs = AccessController.doPrivileged(new PrivilegedAction<Collection<String>>() {

            public Collection<String> run()
            {
               return clazz.getRefClasses();
            }});
         
         for (String ref : refs)
         {
            if (ref.startsWith(PACKAGE_REPLACEMENT_CHILD) || ref.startsWith(PACKAGE_REPLACEMENT_PARENT))
            {
               if (ref.endsWith("Caller"))
               {
                  continue;
               }
               CtClass ctRef = pool.get(ref);
               CtField[] fields = ctRef.getDeclaredFields();
               for (CtField fld : fields)
               {
                  conv.replaceFieldRead(fld, ctRef, getFieldReadWrapperName(fld.getName()));
                  conv.replaceFieldWrite(fld, ctRef, getFieldWriteWrapperName(fld.getName()));
               }
               conv.replaceNew(ctRef, ctRef, getConstructorName(ctRef));
            }
         }
         clazz.instrument(conv);
      }

      private String getFieldReadWrapperName(String fieldName)
      {
         return fieldName + "_read";
      }

      private String getFieldWriteWrapperName(String fieldName)
      {
         return fieldName + "_write";
      }

      private String getConstructorName(CtClass clazz)
      {
         return clazz.getSimpleName() + "_new";
      }
   }
}
