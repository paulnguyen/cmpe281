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
package org.jboss.test.aop.memoryleaks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.jboss.profiler.jvmti.JVMTIInterface;

/**
 * 
 * @author <a href="clebert.suconic@jboss.com">Clebert Suconic</a>
 */
@SuppressWarnings({"unused", "unchecked"})
public class MemoryLeakTester extends TestCase
{
   
   /**
    * Constructor for UndeployTester.
    * @param arg0
    */
   public MemoryLeakTester(String name)
   {
      super(name);
   }
   
   public MemoryLeakTester()
   {
      super();
   }

   private static void printVariables()
   {
      Properties props = System.getProperties();
      Enumeration iter = props.keys();
      
      System.out.println("properties:");
      while (iter.hasMoreElements())
      {
         Object key = iter.nextElement();
         System.out.println(key + "=" + props.get(key));
         System.out.println();
      }
   }

   private static ClassLoader newClassLoader() throws Exception {
       //printVariables();
      URL classLocation =  MemoryLeakTester.class.getProtectionDomain().getCodeSource().getLocation();
       StringTokenizer tokenString = new StringTokenizer(System.getProperty("java.class.path"),File.pathSeparator);
       String pathIgnore = System.getProperty("path.ignore");
       if (pathIgnore==null)
       {
          pathIgnore = classLocation.toString();
       } else
       {
          System.out.println("pathIgnore=" + pathIgnore);
       }
       
       
       ArrayList urls = new ArrayList();
       while (tokenString.hasMoreElements())
       {
          String value = tokenString.nextToken();
          // use toURI().toURL() because of bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4273532
          URL itemLocation = new File(value).toURI().toURL();
          if (!itemLocation.equals(classLocation) && !itemLocation.toString().equals(pathIgnore))
          {
             //System.out.println("Location:" + itemLocation);
             urls.add(itemLocation);
          }
       }
       
       URL[] urlArray= (URL[])urls.toArray(new URL[urls.size()]);
       
       ClassLoader masterClassLoader = URLClassLoader.newInstance(urlArray,null);
       
       
       ClassLoader appClassLoader = URLClassLoader.newInstance(new URL[] {classLocation},masterClassLoader);
       
       return appClassLoader;
    }
       

   private void undeploy(Class xmlLoader) throws Exception
   {
      String strurl = System.getProperty("jboss.aop.path");
      assertNotNull("Property jboss.aop.path should be defined",strurl);
      strurl = strurl.replace('\\','/');
      URL url = new URL("file:/" + strurl);
      
      Method method = xmlLoader.getDeclaredMethod("undeployXML", new Class[] {URL.class});
      method.invoke(null, new Object[] {url});
      //AspectXmlLoader.undeployXML(url); -- I need to use reflection operations as I don't want to take the chance on letting the JVM using a different classLoader
   }
   
   public void testWithClassLoader() throws Exception
   {
      WeakReference weakReferenceOnLoader = null;
      Class xmlLoader = null; // we need to keep that reference, so only the customer's classLoader will go away.
      // If the classLoader was cleared, referenceClassLoader.get() will be null on assertions.
      // If JBossProfiler/jvmti is available (configured with -agentlib:jbossAgent) we will use it for the assertion, if not we will use this weakReference
      try
      {
      {
         ClassLoader oldloader = Thread.currentThread().getContextClassLoader();
         ClassLoader loader = newClassLoader();
         weakReferenceOnLoader = new WeakReference(loader);
         
         Thread.currentThread().setContextClassLoader(loader);
   
         Class testClass  = loader.loadClass("org.jboss.test.aop.memoryleaks.Test");
         assertSame("Fix your classpath, this test is not valid",loader, testClass.getClassLoader());
         assertNotSame(testClass.getClassLoader(), this.getClass().getClassLoader());
         
         Class aspectManagerClass = loader.loadClass("org.jboss.aop.AspectManager");
         assertNotSame(aspectManagerClass.getClassLoader(), this.getClass().getClassLoader());

         Class pojoClass = loader.loadClass("org.jboss.test.aop.memoryleaks.POJO");
         assertNotSame(pojoClass.getClassLoader(), this.getClass().getClassLoader());

         Class interceptorClass = loader.loadClass("org.jboss.test.aop.memoryleaks.TestInterceptor");
         assertNotSame(interceptorClass.getClassLoader(), this.getClass().getClassLoader());
         
         Class aspectClass = loader.loadClass("org.jboss.test.aop.memoryleaks.TestAspect");
         assertNotSame(interceptorClass.getClassLoader(), this.getClass().getClassLoader());
         
         xmlLoader = loader.loadClass("org.jboss.aop.AspectXmlLoader");
         assertNotSame(xmlLoader.getClassLoader(),loader);
         
         
         Class aopinterceptorClass = loader.loadClass("org.jboss.aop.advice.Interceptor");
         assertSame(aopinterceptorClass.getClassLoader(),xmlLoader.getClassLoader());
         //System.out.println("testWithClassLoader ChildClassLoader = " + xmlLoader.getClassLoader());
         //ClassLoader superLoader = xmlLoader.getClassLoader();
                  
         Object pojo = null;
         pojo = pojoClass.newInstance();
         
         Method testMethod =null;
         testMethod = pojoClass.getMethod("method", new Class[] {});
         testMethod.invoke(pojo, new Object[] {});
         
         Field icptrIntercepted = interceptorClass.getDeclaredField("intercepted");
         Field aspectIntercepted = aspectClass.getDeclaredField("intercepted"); 
         
         assertTrue(icptrIntercepted.getBoolean(null));
         assertTrue(aspectIntercepted.getBoolean(null));
         
         icptrIntercepted.setBoolean(null,false);
         aspectIntercepted.setBoolean(null,false);

         undeploy(xmlLoader);

         testMethod.invoke(pojo, new Object[] {});

         assertFalse(icptrIntercepted.getBoolean(null));
         assertFalse(aspectIntercepted.getBoolean(null));
         
         unregisterClassLoader(aspectManagerClass, loader);
         
         icptrIntercepted=null;
         aspectIntercepted=null;
         loader=null;
         testClass=null;
         pojoClass=null;
         pojo = null;
         interceptorClass = null;
         aspectClass = null;
         testMethod = null;
         //xmlLoader = null;
         Thread.currentThread().setContextClassLoader(oldloader);
      }

      assertEquals(1, countInstances("org.jboss.aop.AspectManager"));
      //checkUnload( weakReferenceOnLoader,"org.jboss.test.aop.memoryleaks.Test");
      checkUnload( weakReferenceOnLoader,"org.jboss.test.aop.memoryleaks.TestAspect");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }
     
      // I'm pretty sure nobody would clear that reference. I'm keeping this assertion here just to make it clear why we can't clear xmlLoader
      assertNotNull("The masterClassLoader needs to keep a reference, only the customer's classLoader needs to go away",xmlLoader);
      

      /*Class pojoClass = loader.loadClass("org.jboss.test.aop.memoryleaks.POJO");
      Class interceptorClass = loader.loadClass("org.jboss.test.aop.memoryleaks.TestInterceptor"); */
   }

   private void checkUnload(WeakReference weakReferenceOnLoader, String className) throws Exception
   {
      JVMTIInterface jvmti = new JVMTIInterface();
      if (jvmti.isActive())
      {
         
         //clearEverySingleFieldOnInstances("org.jboss.aop.AspectManager"); // This part is not intended to be commited. It could be used during debug, and you could use to release references on purpose, just to evaluate behavior

         jvmti.forceReleaseOnSoftReferences();
         jvmti.forceGC();
         Class clazz = jvmti.getClassByName(className);
         if (clazz!=null)
         {
            jvmti.heapSnapshot("snapshot", "mem");
            clazz=null;
            
            String report =jvmti.exploreClassReferences(className, 10, true, false, false, false, false);
            
            //System.out.println(report);
            File outputfile = new File("./leaks-report.html");
            FileOutputStream outfile = new FileOutputStream(outputfile);
            PrintStream realoutput = new PrintStream(outfile);
            realoutput.println(report);
            realoutput.close();
            
            
            jvmti.forceGC();
            
            clazz = jvmti.getClassByName(className);
            
            if (clazz==null)
            {
                System.out.println("Attention: After clearing every field on AspectManager, GC could release the classLoader");
            }
            
            fail ("Class " + className + " still referenced. Look at report for more details");
         }
      }
      assertNull("The classLoader is supposed to be released. Something is holding a reference. If you activate -agentlib:jbossAgent this testcase will generate a report with referenceHolders.",weakReferenceOnLoader.get());
   }
   
   public Field[] getDeclaredFields(Class clazz)
   {
      ArrayList list = new ArrayList();
      for (Class classIteration = clazz;classIteration!=null;classIteration=classIteration.getSuperclass())
      {
          Field[] fields = classIteration.getDeclaredFields();
          for (int i = 0; i < fields.length; i++)
          {
             fields[i].setAccessible(true);
             list.add(fields[i]);
          }
          
      }
      
      return (Field[]) list.toArray(new Field[list.size()]);
   }
   
   
   private void clearEverySingleFieldOnInstances(String className)
   {
      System.out.println("Clearing " + className);
      JVMTIInterface jvmti = new JVMTIInterface();
      Class classes[] = jvmti.getLoadedClasses();
      Object objects[] = null;
      
      for (int i=0;i<classes.length;i++)
      {
         if (classes[i].getName().equals(className))
         {
            Field fields[] = getDeclaredFields(classes[i]);
            objects = jvmti.getAllObjects(classes[i]);
            for (int j=0;j<objects.length;j++)
            {
               resetObject(objects[j], fields);
            }
            if (objects.length==0)
            {
               resetObject(null, fields);
            }
         }
      }
      classes= null;
      objects = null;
   }

   private static void resetObject(Object object, Field[] fields)
   {
      for (int fieldN=0;fieldN<fields.length;fieldN++)
      {
         try
         {
            //System.out.print("Setting "+fields[fieldN].getName());
            fields[fieldN].set(object,null);
            //System.out.println("...done");
         }
         catch (Exception e)
         {
           // System.out.println("...error " + e.getMessage());
            //System.out.println("Exception " + e.getMessage() + " happened during setField");
         }
      }
   }

   private int countInstances(String name)
   {
      JVMTIInterface jvmti = new JVMTIInterface();
      int i = jvmti.getAllObjects(name).length;
      return i;
   }

   private void unregisterClassLoader(Class aspectManagerClass, ClassLoader loader) throws Exception
   {
      Method instance = aspectManagerClass.getDeclaredMethod("instance", new Class[0]);
      Object aspectManager = instance.invoke(null, new Object[0]);
      
      Method unregisterClassLoader = aspectManagerClass.getDeclaredMethod("unregisterClassLoader", new Class[] {ClassLoader.class});
      unregisterClassLoader.invoke(aspectManager, new Object[] {loader});
   }
}
