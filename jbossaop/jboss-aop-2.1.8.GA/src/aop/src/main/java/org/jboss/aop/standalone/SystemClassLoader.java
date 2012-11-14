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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * A classloader that can be installed as the system classloader
 * to enable aop translation based on files in META-INF/jboss-aop.xml
 * in the classpath.<p>
 *
 * You will need the following jars in your classpath (log4j.jar is optional):<p>
 * <ul>
 * <li>jboss-aop.jar</li>
 * <li>javassist.jar</li>
 * <li>jboss-common.jar</li>
 * </ul>
 *
 * Start the virtual machine with something like the following command:
 *
 * <pre>
 * java -Djava.system.class.loader=org.jboss.aop.standalone.SystemClassLoader my.Main
 * </pre>
 *
 * Implementation Detail: All access to non-jre classes must be done through reflection the
 * classes must be loaded using the loadLocally method.
 *
 * @todo The delegation for jre classes is important. Need to
 *       figure out how to do this generically
 * @todo java security model, protection domain, etc.
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 * @author <a href="mailto:juha@jboss.org">Juha Lindfors</a>
 * @version $Revision: 71279 $
 */
public class SystemClassLoader
   extends ClassLoader
{
   /**
    * Not yet installed as the system classloader
    */
   private static final int NOT_INSTALLED = 0;

   /**
    * Installed as the system classloader
    */
   private static final int INSTALLED = 1;

   /**
    * Installing the aspect manager
    */
   private static final int INITIALIZING = 2;

   /**
    * Aspect manager install
    */
   private static final int INITIALIZED = 3;

   /**
    * The current state
    */
   private int state = NOT_INSTALLED;

   /**
    * Our parent, the default system classloader
    */
   ClassLoader parent = null;


   /**
    * org.jboss.aop.AspectXmlDeployer.deployXML(java.util.URL url);
    */
   Method deployXML;

   /** Translator.transform(ClassLoader loader, String name,
    *  Class classBeingRedefined, ProtectionDomain pd,
    *  byte[] classfileBuffer)
    */
   Method transform;

   /**
    * static org.jboss.aop.AspectManager.instance();
    */
   Method instance;

   /**
    * The aspect manager;
    */
   Object aspectManager;

   /**
    * Construct a new system classloader.<p>
    *
    * We cannot do much here, we need to avoid recursion
    *
    * @param parent the default system classloader
    */
   public SystemClassLoader(ClassLoader parent)
   {
      super(parent);
      install();
   }

   /**
    * Load a class, overridden to transform aop enhanced classes
    * and load non jre classes through this classloader.
    *
    * @param name the class name
    * @param resolve whether to resolve the class
    * @return the class
    * @throws ClassNotFoundException when there is no class
    */
   public synchronized Class<?> loadClass(String name, boolean resolve)
      throws ClassNotFoundException
   {
      // Have we already loaded the class?
      Class<?> clazz = findLoadedClass(name);
      if (clazz != null)
      {
         if (resolve) resolveClass(clazz);
         return clazz;
      }

      // Is it a jre class?
      clazz = loadClassByDelegation(name);
      if (clazz != null)
      {
         if (resolve) resolveClass(clazz);
         return clazz;
      }

      // First non jre loadClass initializes
      initialize();

      // Load the class
      try
      {
         ClassBytes origBytes = loadClassBytes(name);
         ClassBytes classBytes = new ClassBytes();

         // If we are initialized check for aop translation
         if (state == INITIALIZED)
         {
            final Object[] args = {this, name, null, null, origBytes.bytes};
            if (!name.startsWith("org.jboss.aop."))
            {
               classBytes.bytes = (byte[]) transform.invoke(aspectManager, args);
               classBytes.protectionDomain = origBytes.protectionDomain;
            }
         }
         if (classBytes.bytes == null)
            classBytes = origBytes;

         // Define the class
         return defineClassFromBytes(name, classBytes, resolve);
      }
      catch (IOException ioe)
      {
         throw new ClassNotFoundException("Unable to load " + name, ioe);
      }
      catch (IllegalAccessException iae)
      {
         throw new Error(iae);
      }
      catch (InvocationTargetException ite)
      {
         throw new Error("Error transforming the class " + name, ite.getCause());
      }
   }

   /**
    * Load the bytecode for a class
    */
   protected ClassBytes loadClassBytes(String name)
      throws ClassNotFoundException, IOException
   {
      final String classFileName = name.replace('.', '/') + ".class";
      final URL url = AccessController.doPrivileged(new PrivilegedAction<URL>() 
      {
         public URL run()
         {
            return getParent().getResource(classFileName);
         }
      });
      ProtectionDomain protectionDomain = null;
      InputStream in = null;
      if (url != null)
      {
         try
         {
            in = AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>()
            {
               public InputStream run() throws Exception
               {
                  return url.openStream();
               }
            });
         }
         catch (PrivilegedActionException e)
         {
            throw new ClassNotFoundException(name, e);
         }
         String urlstring = url.toExternalForm();
         URL urlCS = url;
         if (urlstring.startsWith("jar:"))
         {
            int i = urlstring.indexOf('!');
            String cs = urlstring.substring(4, i);
            urlCS = new URL(cs);
         }
         else
         {
            int i = urlstring.indexOf(classFileName);
            if (i != -1)
            {
               String cs = urlstring.substring(0, i);
               urlCS = new URL(cs);
            }
         }
         CodeSource codeSource = new CodeSource(urlCS, (Certificate[]) null);
         protectionDomain = new ProtectionDomain(codeSource, null, this, null);
      }
      else
      {
         /* Try the system tmpdir/aopdynclasses, the default location
         the AOPClassPool writes dynamic class files to.
         */
         try
         {
            in = AccessController.doPrivileged(new PrivilegedExceptionAction<InputStream>()
            {
               public InputStream run() throws Exception
               {
                  String tmpdir = System.getProperty("java.io.tmpdir");
                  File aopdynclasses = new File(tmpdir, "aopdynclasses");
                  File classFile = new File(aopdynclasses, classFileName);
                  return new FileInputStream(classFile);
               }
            });
         }
         catch (PrivilegedActionException e)
         {
            throw new ClassNotFoundException(name, e);
         }
      }

      byte[][] bufs = new byte[8][];
      int bufsize = 4096;

      for (int i = 0; i < 8; ++i)
      {
         bufs[i] = new byte[bufsize];
         int size = 0;
         int len = 0;
         do
         {
            len = in.read(bufs[i], size, bufsize - size);
            if (len >= 0)
               size += len;
            else
            {
               byte[] result = new byte[bufsize - 4096 + size];
               int s = 0;
               for (int j = 0; j < i; ++j)
               {
                  System.arraycopy(bufs[j], 0, result, s, s + 4096);
                  s = s + s + 4096;
               }

               System.arraycopy(bufs[i], 0, result, s, size);
               ClassBytes classBytes = new ClassBytes();
               classBytes.bytes = result;
               classBytes.protectionDomain = protectionDomain;
               return classBytes;
            }
         }
         while (size < bufsize);
         bufsize *= 2;
      }

      throw new IOException("too much data loading class " + name);
   }

   /**
    * Define a class from the bytes
    *
    * @name the class to define
    * @param b the bytecode
    * @param resolve whether to resolve the class
    * @returns the class
    */
   protected Class<?> defineClassFromBytes(String name, ClassBytes bytes, boolean resolve)
   {
      definePackage(name);
      byte[] b = bytes.bytes;
      Class<?> clazz = defineClass(name, b, 0, b.length, bytes.protectionDomain);
      if (resolve) resolveClass(clazz);
      return clazz;
   }

   /**
    * Define the package for the class if not already done
    * 
    * @todo this properly
    * @param name the class name
    */
   protected void definePackage(String className)
   {
      int i = className.lastIndexOf('.');
      if (i == -1)
         return;

      try
      {
         definePackage(className.substring(0, i), null, null, null, null, null, null, null);
      }
      catch (IllegalArgumentException alreadyDone)
      {
      }
   }

   /**
    * Load a class using this classloader only
    *
    * @param name the class name
    * @return the class
    * @throws ClassNotFoundException when there is no class
    */
   protected Class<?> loadClassLocally(String name)
      throws ClassNotFoundException
   {
      try
      {
         ClassBytes bytes = loadClassBytes(name);
         return defineClassFromBytes(name, bytes, true);
      }
      catch (Throwable ex)
      {
         throw new ClassNotFoundException(name, ex);
      }
   }

   /**
    * Load jre classes from the parent classloader
    *
    * @param name the class name
    * @return the class
    * @throws ClassNotFoundException when there is no class
    */
   protected Class<?> loadClassByDelegation(String name)
      throws ClassNotFoundException
   {
      // FIXME: Only works for Sun for now
      if (name.startsWith("java.") || name.startsWith("javax.")
         || name.startsWith("sun.") || name.startsWith("com.sun.")
         || name.startsWith("org.apache.xerces.") || name.startsWith("org.xml.sax.")
         || name.startsWith("org.w3c.dom."))
         return getParent().loadClass(name);

      return null;
   }

   /**
    * Install the classloader, get reflection objects and load
    * classes using ourself.
    */
   protected synchronized void install()
   {
      try
      {
         // AspectManager
         Class<?> clazz = loadClassLocally("org.jboss.aop.AspectManager");
         Class<?>[] transformSig = {ClassLoader.class, String.class,
                                 Class.class, ProtectionDomain.class, byte[].class};
         transform = clazz.getMethod("transform", transformSig);
         instance = clazz.getMethod("instance", new Class[0]);

         // AspectXmlLoader
         clazz = loadClassLocally("org.jboss.aop.AspectXmlLoader");
         deployXML = clazz.getMethod("deployXML", new Class[]{URL.class});

/*
         // loadClassLocally("org.jboss.aop.Advisable");
         loadClassLocally("org.jboss.aop.Advised");
         // loadClassLocally("org.jboss.aop.Advisor");
         loadClassLocally("org.jboss.aop.AlreadyAdvisedException");
         // loadClassLocally("org.jboss.aop.AOPClassPool");
         // loadClassLocally("org.jboss.aop.AspectManager");
         loadClassLocally("org.jboss.aop.AspectNotificationHandler");
         // loadClassLocally("org.jboss.aop.AspectXmlLoader");
         // loadClassLocally("org.jboss.aop.ClassAdvisor");
         loadClassLocally("org.jboss.aop.ClassInstanceAdvisor");
         // loadClassLocally("org.jboss.aop.ClassMetaData");
         // loadClassLocally("org.jboss.aop.ClassMetaDataLoader");
         loadClassLocally("org.jboss.aop.CLClassPath");
         loadClassLocally("org.jboss.aop.ConstructorComparator");
         loadClassLocally("org.jboss.aop.ConstructorConfig");
         loadClassLocally("org.jboss.aop.ConstructorInvocation");
         loadClassLocally("org.jboss.aop.ConstructorMetaData");
         loadClassLocally("org.jboss.aop.CtConstructorComparator");
         loadClassLocally("org.jboss.aop.CtFieldComparator");
         loadClassLocally("org.jboss.aop.CtMethodComparator");
         loadClassLocally("org.jboss.aop.Dispatcher");
         loadClassLocally("org.jboss.aop.FieldComparator");
         loadClassLocally("org.jboss.aop.FieldInvocation");
         loadClassLocally("org.jboss.aop.FieldMetaData");
         loadClassLocally("org.jboss.aop.GenericInterceptorFactory");
         // loadClassLocally("org.jboss.aop.InstanceAdvised");
         // loadClassLocally("org.jboss.aop.InstanceAdvisor");
         loadClassLocally("org.jboss.aop.Instrumentor");
         // loadClassLocally("org.jboss.aop.InterceptorFactory");
         loadClassLocally("org.jboss.aop.InterceptorFilter");
         // loadClassLocally("org.jboss.aop.Interceptor");
         // loadClassLocally("org.jboss.aop.ClassPointcut");
         // loadClassLocally("org.jboss.aop.InterceptorStack");
         // loadClassLocally("org.jboss.aop.IntroductionPointcut");
         loadClassLocally("org.jboss.aop.InvocationFilterInterceptor");
         loadClassLocally("org.jboss.aop.Invocation");
         loadClassLocally("org.jboss.aop.InvocationResponse");
         loadClassLocally("org.jboss.aop.InvocationType");
         loadClassLocally("org.jboss.aop.Loader");
         loadClassLocally("org.jboss.aop.MarshalledValueInputStream");
         loadClassLocally("org.jboss.aop.MarshalledValue");
         loadClassLocally("org.jboss.aop.MarshalledValueOutputStream");
         // loadClassLocally("org.jboss.aop.MetaDataResolver");
         loadClassLocally("org.jboss.aop.MethodComparator");
         loadClassLocally("org.jboss.aop.MethodConfig");
         loadClassLocally("org.jboss.aop.MethodHashing");
         loadClassLocally("org.jboss.aop.MethodInvocation");
         loadClassLocally("org.jboss.aop.MethodMetaData");
         loadClassLocally("org.jboss.aop.NotAdvisableException");
         loadClassLocally("org.jboss.aop.NotAdvisedException");
         loadClassLocally("org.jboss.aop.PayloadKey");
         loadClassLocally("org.jboss.aop.SimpleClassMetaData");
         loadClassLocally("org.jboss.aop.SimpleClassMetaDataLoader");
         loadClassLocally("org.jboss.aop.SimpleMetaData");
         loadClassLocally("org.jboss.aop.SingletonInterceptorFactory");
         loadClassLocally("org.jboss.aop.StandaloneClassLoader");
         loadClassLocally("org.jboss.aop.ThreadMetaData");
         loadClassLocally("org.jboss.aop.proxy.ClassProxyFactory");
         loadClassLocally("org.jboss.aop.proxy.ClassProxy");
         loadClassLocally("org.jboss.aop.proxy.ClassProxyTemplate");
         loadClassLocally("org.jboss.aop.proxy.DynamicProxyIH");
         // loadClassLocally("org.jboss.aop.proxy.ProxyAdvisor");
         loadClassLocally("org.jboss.util.loading.Translatable");
         loadClassLocally("javassist.compiler.CompileError");
         loadClassLocally("javassist.bytecode.BadBytecode");
         loadClassLocally("javassist.bytecode.ClassFile");
         loadClassLocally("javassist.bytecode.ConstPool");
         */

         state = INSTALLED;
      }
      catch (Throwable t)
      {
         t.printStackTrace();
         throw new Error("Error initializing system classloader", t);
      }
   }

   /**
    * Initialize the aspect manager and load the static aspects
    */
   protected synchronized void initialize()
   {
      // Only one thread will initialize
      if (state != INSTALLED)
         return;
      state = INITIALIZING;

      // Set the context classloader to ourselves
      Thread.currentThread().setContextClassLoader(this);

      // Set up the aspect manager, once this is done we
      // are ready to go
      try
      {
         try
         {
            System.setProperty("jboss.aop.optimized", "false");
            aspectManager = instance.invoke(null, new Object[0]);
         }
         catch (InvocationTargetException ite)
         {
            throw ite.getCause();
         }
         state = INITIALIZED;
      }
      catch (Throwable t)
      {
         t.printStackTrace();
         throw new Error("Error installing aspect manager", t);
      }

      // Install the aop configurations
      try
      {
         Enumeration<URL> enumeration = getParent().getResources("META-INF/jboss-aop.xml");
         while (enumeration.hasMoreElements())
         {
            URL url = enumeration.nextElement();
            deployXML.invoke(null, new Object[]{url});
         }
      }
      catch (Throwable t)
      {
         t.printStackTrace();
         throw new Error("Error deploying aop configrations", t);
      }
   }

   /**
    * ClassBytes.
    */
   private static class ClassBytes
   {
      public ProtectionDomain protectionDomain;
      public byte[] bytes;
   }
}
