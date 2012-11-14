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
package org.jboss.test.aop.stress.weavetest;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;

import org.jboss.aop.classpool.AOPClassPoolRepository;
import org.jboss.test.aop.stress.AbstractScenario;
import org.jboss.test.aop.stress.SkipWarmup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SkipWarmup
public class GenerateClassesScenario extends AbstractScenario
{
   private static final String WOVEN = ".Woven";
   private static final String VANILLA = ".Vanilla";
   
   boolean initialised;

   private File directory;
   ClassPool pool;
   String packageName;
   
   List<Factory> wovenFactories = new ArrayList<Factory>();  
   
   List<Factory> vanillaFactories = new ArrayList<Factory>();  
   
   public List<Factory> getVanillaFactories()
   {
      return vanillaFactories;
   }
   
   public List<Factory> getWovenFactories()
   {
      return wovenFactories;
   }
   
   public void execute(int thread, int loop) throws Exception
   {
      if (thread > 0)
      {
         throw new RuntimeException("This test can only be used with one thred");
      }
      if (!initialised)
      {
         initialise();
         initialised = true;
      }
      vanillaFactories.add(generateClassAndFactory(loop, VANILLA));
      wovenFactories.add(generateClassAndFactory(loop, WOVEN));
   }
   
   private Factory generateClassAndFactory(int loop, String rootName) throws Exception
   {
      CtClass clazz = createClass(loop, rootName);
      clazz.writeFile(directory.getAbsolutePath());
      clazz.detach();
      
      CtClass factoryClass = createFactory(loop, rootName);
      factoryClass.writeFile(directory.getAbsolutePath());
      Class<Factory> realClass = factoryClass.toClass();
      return realClass.newInstance();
   }
   
   private CtClass createClass(int loop, String rootName) throws Exception
   {
      CtClass clazz = pool.makeClass(packageName + rootName + loop);
      clazz.addInterface(pool.get(MethodCaller.class.getName()));

      CtField field = new CtField(CtClass.intType, "counter", clazz);
      field.setModifiers(Modifier.STATIC & Modifier.PRIVATE);
      clazz.addField(field);
      
      CtMethod method = CtNewMethod.make("public void method(){counter++;}", clazz);
      method.setModifiers(Modifier.PUBLIC);
      clazz.addMethod(method);
      
      return clazz;
   }

   private CtClass createFactory(int loop, String rootName) throws Exception
   {
      CtClass clazz = pool.makeClass(packageName + rootName + "Factory" + loop);
      clazz.addInterface(pool.get(Factory.class.getName()));
      
      CtMethod method = CtNewMethod.make("public " + packageName + ".MethodCaller create(){return new " + packageName + rootName + loop + "();}", clazz);
      method.setModifiers(Modifier.PUBLIC);
      clazz.addMethod(method);
      
      method = CtNewMethod.make("public java.lang.Class loadClass(){return java.lang.Class.forName(\"" + packageName + rootName + loop + "\", false, this.getClass().getClassLoader());}", clazz);
      method.setModifiers(Modifier.PUBLIC);
      clazz.addMethod(method);
      
      return clazz;
   }

   private void initialise() throws Exception
   {
      initDirectory();
      
      pool = AOPClassPoolRepository.getInstance().registerClassLoader(this.getClass().getClassLoader());
      packageName = this.getClass().getPackage().getName();
      CtClass clazz = pool.get("org.jboss.test.aop.stress.weavetest.TemplatePOJO");
      if (clazz == null)
      {
         throw new RuntimeException(TemplatePOJO.class.getName() + " does not exist in pool");
      }
   }
   
   private void initDirectory() throws Exception
   {
      URL url = TemplatePOJO.class.getProtectionDomain().getCodeSource().getLocation();
      URI uri = url.toURI();
      directory = new File(uri);
   }
}
