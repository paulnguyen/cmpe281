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
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.LogManager;

import junit.framework.TestCase;

import org.jboss.profiler.jvmti.JVMTIInterface;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 *
 * @author <a href="clebert.suconic@jboss.com">Clebert Suconic</a>
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
@SuppressWarnings({"unused", "unchecked"})
public class MemoryLeakTestCase extends TestCase
{
   String jbossAopPath;
   String extraClasses;
   Method aspectXmkLoaderUndeployXmlMethod;
   Method aspectManagerInstanceMethod;
   Method aspectManagerUnregisterClassLoader;

   /**
    * Constructor for UndeployTester.
    * @param arg0
    */
   public MemoryLeakTestCase(String name)
   {
      super(name);
   }

   public MemoryLeakTestCase()
   {
      super();
   }

   public static void main(String[] args) throws Exception
   {
      MemoryLeakTestCase test = new MemoryLeakTestCase();
      test.testWithClassLoader();
   }

   @Override
   protected void setUp() throws Exception
   {
      System.setProperty(AOPTestWithSetup.DISABLE_SECURITY_KEY, "true");
      jbossAopPath = System.getProperty("jboss.aop.path");
      extraClasses = System.getProperty("extraClasses", null);
      super.setUp();
   }

   
   private String getProfilerIdentify(Object obj)
   {
	   return obj.getClass().getName() + "@" + System.identityHashCode(obj);
   }
   
   public void testWithClassLoader() throws Exception
   {

	  LogManager.getLogManager(); // this is just to avoid a thread to be created using this ContextClassLoader inside LogManager
      WeakReference weakReferenceOnLoader = null;
      Class xmlLoader = null; // we need to keep that reference, so only the customer's classLoader will go away.
      // If the classLoader was cleared, referenceClassLoader.get() will be null on assertions.
      // If JBossProfiler/jvmti is available (configured with -agentlib:jbossAgent) we will use it for the assertion, if not we will use this weakReference
      try
      {
         String className = null;
         {
            final ClassLoader oldloader = Thread.currentThread().getContextClassLoader();
            ClassLoader loader = newClassLoader(oldloader);
            
            weakReferenceOnLoader = new WeakReference(loader);

            Thread.currentThread().setContextClassLoader(loader);
            System.out.println("OLD Loader " + oldloader + ", profiler identify=" + getProfilerIdentify(oldloader));
            System.out.println("NEW Loader " + loader + ", profiler identify==" + getProfilerIdentify(loader));

            ClassLoader parent = loader.getParent();
            while (parent != null)
            {
               System.out.println("Parent " + parent + " profilerIdentify=" + getProfilerIdentify(parent));
               parent = parent.getParent();
            }


            Class testClass = getTestCaseClass(loader);
            className = testClass.getName();

            Class aspectManagerClass = loader.loadClass("org.jboss.aop.AspectManager");
            assertNotSame(aspectManagerClass.getClassLoader(), this.getClass().getClassLoader());
            assertNotSame(aspectManagerClass.getClassLoader(), testClass.getClassLoader());

            System.out.println("oldLoader");

            xmlLoader = loader.loadClass("org.jboss.aop.AspectXmlLoader");
            initMethods(aspectManagerClass, xmlLoader);

            assertNotSame(xmlLoader.getClassLoader(),loader);

            ArrayList methods = getTestMethods(testClass);
            Object testInstance = getTestInstance(testClass);

            boolean passed = runTests(testInstance, methods);
            assertTrue(passed);

            undeploy(xmlLoader);

            passed = runTests(testInstance, methods, true);
            System.out.println("============ Ran tests after undeploy with errors: " + passed);

            unregisterClassLoader(aspectManagerClass, loader);

            loader=null;
            testClass=null;
            testInstance = null;
            methods.clear();
            
            AccessController.doPrivileged(new PrivilegedAction<Object>()
            {
               public Object run()
               {
                  Thread.currentThread().setContextClassLoader(oldloader);
                  return null;
               }
            });

            //The test framework does not clear some static fields, make sure these are cleared
            clearEverySingleFieldOnInstances("org.jboss.test.JBossTestSetup");
            clearEverySingleFieldOnInstances("org.jboss.test.AbstractTestSetup");
            clearEverySingleFieldOnInstances("org.jboss.test.AbstractTestCaseWithSetup");
         }

         assertEquals(1, countInstances("org.jboss.aop.AspectManager", true));

         if (extraClasses != null)
         {
            StringTokenizer tok = new StringTokenizer(extraClasses, ":;,");
            while (tok.hasMoreTokens())
            {
               String clazz = tok.nextToken();
               try
               {
                  reportInstanceReferences(weakReferenceOnLoader, clazz);
               }
               catch(Throwable t)
               {
               }
            }
         }

         checkUnload( weakReferenceOnLoader, className);

         // I'm pretty sure nobody would clear that reference. I'm keeping this assertion here just to make it clear why we can't clear xmlLoader
         assertNotNull("The masterClassLoader needs to keep a reference, only the customer's classLoader needs to go away",xmlLoader);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw e;
      }

      System.out.println("Done!");
   }

   private Object getTestInstance(Class testClass) throws Exception
   {

      Constructor[] constructors = testClass.getConstructors();
      Constructor defaultConstructor =  null;
      Constructor nameConstructor =  null;

      for (int i = 0 ; i < constructors.length ; i++)
      {
         System.out.println("found ctor " + constructors[i]);
         Class[] params = constructors[i].getParameterTypes();

         if (params.length == 0)
         {
            defaultConstructor = constructors[i];
         }
         else if (params.length == 1 && params[i].equals(String.class))
         {
            nameConstructor = constructors[i];
         }
      }

      if (nameConstructor != null)
      {
         return nameConstructor.newInstance(new Object[] {testClass.getName()});
      }
      if (defaultConstructor != null)
      {
         return defaultConstructor.newInstance(new Object[0]);
      }
      throw new RuntimeException("Could not find a constructor for " + testClass.getName());
   }
   private ArrayList getTestMethods(Class testClass)
   {
      System.out.println("Determining test methods");
      ArrayList testMethods = new ArrayList();
      Method methods[] = testClass.getMethods();
      for (int i = 0 ; i < methods.length ; i++)
      {
         if (methods[i].getName().indexOf("test") == 0 && methods[i].getParameterTypes().length == 0 && methods[i].getReturnType().equals(Void.TYPE))
         {
            testMethods.add(methods[i]);
         }
      }
      return testMethods;
   }

   private boolean runTests(Object testInstance, ArrayList methods)
   {
      return runTests(testInstance, methods, false);
   }

   private boolean runTests(Object testInstance, ArrayList methods, boolean breakOnError)
   {
      Method setup = null;
      try
      {
         setup = testInstance.getClass().getDeclaredMethod("setUp", new Class[0]);
         setup.setAccessible(true);
      }
      catch (Exception e)
      {
      }

      Method tearDown = null;
      try
      {
         tearDown = testInstance.getClass().getDeclaredMethod("tearDown", new Class[0]);
         tearDown.setAccessible(true);
      }
      catch (Exception e)
      {
      }

      boolean passed = true;
      for (Iterator it = methods.iterator() ; it.hasNext() ; )
      {
         Method test = (Method)it.next();
         try
         {
            System.out.println("============ Running test " + testInstance.getClass().getName() + "." + test.getName());

            if (setup != null)
            {
               setup.invoke(testInstance, new Object[0]);
            }

            test.invoke(testInstance, new Object[0]);
            if (!breakOnError)
            {
               System.out.println("============ Succeeded test " + testInstance.getClass().getName() + "." + test.getName());
            }
         }
         catch(Throwable t)
         {
            passed = false;
            if (breakOnError)
            {
               break;
            }
            else
            {
               System.out.println("============ Failed test " + testInstance.getClass().getName() + "." + test.getName() + "\n" + t);
               t.printStackTrace();
            }
         }
         finally
         {
            if (tearDown != null)
            {
               try
               {
                  tearDown.invoke(testInstance, new Object[0]);
               }
               catch(Exception e)
               {
               }
            }

         }
      }

      return passed;
   }

   private Class getTestCaseClass(ClassLoader loader) throws Exception
   {
      String className = System.getProperty("test.to.run");
      assertNotNull("Test to be run must be passed in test.to.run system property", className);

      Class testClass  = loader.loadClass(className);
      assertSame("Fix your classpath, this test is not valid", loader, testClass.getClassLoader());
      assertNotSame(testClass.getClassLoader(), this.getClass().getClassLoader());
      return testClass;
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

            String report =jvmti.exploreClassReferences(className, 15, true, false, false, false, true);

            //System.out.println(report);
            String reportDir = System.getProperty("leak.report.dir");
            assertNotNull("You must pass in the directory for the reports as leak.report.dir", reportDir);
            File outputfile = new File(reportDir + "/leak-reoprt-" + className + ".html");
            FileOutputStream outfile = new FileOutputStream(outputfile);
            PrintStream realoutput = new PrintStream(outfile);
            realoutput.println(report);
            realoutput.close();


            jvmti.forceGC();

            clazz = jvmti.getClassByName(className);

            clearEverySingleFieldOnInstances("org.jboss.aop.AspectManager"); // This part is not intended to be commited. It could be used during debug, and you could use to release references on purpose, just to evaluate behavior

            clazz = jvmti.getClassByName(className);
            if (clazz==null)
            {
                System.out.println("Attention: After clearing every field on AspectManager, GC could release the classLoader");
            }

            fail ("Class " + className + " still referenced. Look at report for more details");
         }
      }
   }

   private void reportInstanceReferences(WeakReference weakReferenceOnLoader, String className) throws Exception
   {
      JVMTIInterface jvmti = new JVMTIInterface();
      if (jvmti.isActive())
      {

         jvmti.forceReleaseOnSoftReferences();
         jvmti.forceGC();
         Class clazz = jvmti.getClassByName(className);
         if (clazz!=null)
         {
            jvmti.heapSnapshot("snapshot", "mem");
            clazz=null;
            Object[] objects = jvmti.getAllObjects(className);
            System.out.println("============> Instances of " + className + " " + objects.length);

            //String report =jvmti.exploreClassReferences(className, 15, true, false, false, false, true);
            String report = jvmti.exploreObjectReferences(className, 15, false);



            System.out.println("================= " + className + " ================");
            System.out.println(report);
            String reportDir = System.getProperty("leak.report.dir");
            assertNotNull("You must pass in the directory for the reports as leak.report.dir", reportDir);
            File outputfile = new File(reportDir + "/leak-report-instances" + className + ".html");
            FileOutputStream outfile = new FileOutputStream(outputfile);
            PrintStream realoutput = new PrintStream(outfile);
            realoutput.println(report);
            realoutput.close();
         }
      }
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
        	System.out.println("---> Found class " + className);
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
            fields[fieldN].set(object,null);
         }
         catch (Exception e)
         {
            System.out.println("Exception  happened during setField " + e);
         }
      }
   }

   private int countInstances(String name, boolean notSubClasses)
   {
      JVMTIInterface jvmti = new JVMTIInterface();
      Object[] objects = jvmti.getAllObjects(name);
      int subClasses = 0;
      for (int i = 0 ; i < objects.length ; i++)
      {
         if (!objects[i].getClass().getName().equals(name))
         {
            subClasses++;
         }
      }

      return objects.length - subClasses;
   }

   private void unregisterClassLoader(Class aspectManagerClass, ClassLoader loader) throws Exception
   {
      System.out.println("============ Unregistering ClassLoader");
      Object aspectManager = aspectManagerInstanceMethod.invoke(null, new Object[0]);

      aspectManagerUnregisterClassLoader.invoke(aspectManager, new Object[] {loader});
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

   private static ClassLoader newClassLoader(ClassLoader parent) throws Exception {
       //printVariables();
       URL classLocation =  MemoryLeakTestCase.class.getProtectionDomain().getCodeSource().getLocation();
       StringTokenizer tokenString = new StringTokenizer(System.getProperty("java.class.path"),File.pathSeparator);
       System.out.println("java.class.path " + System.getProperty("java.class.path"));
       String pathIgnore = System.getProperty("path.ignore");
       System.out.println("path.ignore " + pathIgnore);
       if (pathIgnore==null)
       {
          pathIgnore = classLocation.toString();
       } else
       {
          System.out.println("pathIgnore=" + pathIgnore);
       }


       //ArrayList urls = new ArrayList();
       ArrayList<URL> urls = new ArrayList<URL>();
       while (tokenString.hasMoreElements())
       {
          String value = tokenString.nextToken();
          // use toURI().toURL() because of bug http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4273532
          URL itemLocation = new File(value).toURI().toURL();
          if (!itemLocation.equals(classLocation) && !itemLocation.toString().equals(pathIgnore))
          {
             urls.add(itemLocation);
          }
          else
          {
             System.out.println("Skipping " + classLocation);
          }
       }

       URL[] urlArray= urls.toArray(new URL[urls.size()]);

       ClassLoader masterClassLoader = URLClassLoader.newInstance(urlArray,null);


       ClassLoader appClassLoader = URLClassLoader.newInstance(new URL[] {classLocation},masterClassLoader);

       return appClassLoader;
    }

   private void initMethods(Class aspectManagerClass, Class xmlLoader) throws Exception
   {
      aspectXmkLoaderUndeployXmlMethod = xmlLoader.getDeclaredMethod("undeployXML", new Class[] {URL.class});
      aspectManagerInstanceMethod = aspectManagerClass.getDeclaredMethod("instance", new Class[0]);
      aspectManagerUnregisterClassLoader = aspectManagerClass.getDeclaredMethod("unregisterClassLoader", new Class[] {ClassLoader.class});
   }

   private void undeploy(Class xmlLoader) throws Exception
   {
      String strurl = jbossAopPath;
      assertNotNull("Property jboss.aop.path should be defined",strurl);
      strurl = strurl.replace('\\','/');
      if (!strurl.startsWith("/"))
      {
    	  strurl = "/" + strurl;
      }
      URL url = new URL("file://" + strurl);
      
      aspectXmkLoaderUndeployXmlMethod.invoke(null, new Object[] {url});

      System.out.println("\n====================================================================");
      System.out.println("!!!! Undeployed " + url);
      System.out.println("=====================================================================\n");
   }

}
