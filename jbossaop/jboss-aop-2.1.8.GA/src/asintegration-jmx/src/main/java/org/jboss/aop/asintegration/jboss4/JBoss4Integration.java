/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.aop.asintegration.jboss4;

import java.io.File;
import java.lang.reflect.Constructor;

import javassist.ClassPool;
import javassist.scopedpool.ScopedClassPool;
import javassist.scopedpool.ScopedClassPoolFactory;
import javassist.scopedpool.ScopedClassPoolRepository;

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.jboss.aop.AspectManager;
import org.jboss.aop.asintegration.JBossIntegration;
import org.jboss.aop.classpool.AOPClassLoaderScopingPolicy;
import org.jboss.aop.classpool.ucl.JBossUclDelegatingClassPoolFactory;
import org.jboss.aop.domain.ScopedRepositoryClassLoaderDomain;
import org.jboss.logging.Logger;
import org.jboss.mx.loading.HeirarchicalLoaderRepository3;
import org.jboss.mx.loading.RepositoryClassLoader;
import org.jboss.mx.server.ServerConstants;
import org.jboss.mx.util.MBeanServerLocator;
import org.jboss.mx.util.ObjectNameFactory;

/**
 * JBoss4Integration.<p>
 * 
 * This class and its associated classes are
 * for the old JBoss4 integration with the LoaderRepository<p>
 * 
 * <ul>Related Classes:
 * <li> {@link JBossClassPool}
 * <li> {@link JBossClassPoolFactory}
 * <li> {@link ScopedRepositoryClassLoaderHelper}
 * <li> {@link LoaderRepositoryUrlUtil}
 * <li> {@link ScopedRepositoryClassLoaderDomain}
 * <li> {@link ScopedJBossClassPool}
 * </ul>
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBoss4Integration implements JBossIntegration, ScopedClassPoolFactory
{
   Logger logger = Logger.getLogger(JBoss4Integration.class);
   
   static {
      //pre-load necessary classes so that we avoid NoClassDefFoundErrors on JRockit when using the RepositoryClassloader hook
      //When AspectManager.translate() is called the first time, these classes have not been loaded yet, and this is what causes
      //JRockit to get confused
      @SuppressWarnings("unused")
      Class<?> clazz = HeirarchicalLoaderRepository3.class;
   }

   static final ObjectName DEFAULT_LOADER_REPOSITORY = ObjectNameFactory.create(ServerConstants.DEFAULT_LOADER_NAME);
   
   /** The delegate classpool factory */
   private ScopedClassPoolFactory delegateClassPoolFactory;
   
   private String classPoolFactoryClassName; 
   
   public boolean isValidClassLoader(ClassLoader loader)
   {
      if (!(loader instanceof RepositoryClassLoader)) return false;
      return ((RepositoryClassLoader) loader).getLoaderRepository() != null;
   }

   public AOPClassLoaderScopingPolicy createAOPClassLoaderScopingPolicy()
   {
      return new RepositoryClassLoaderScopingPolicy();
   }

   public String getClassPoolFactoryClassName()
   {
      if (classPoolFactoryClassName == null)
      {
         classPoolFactoryClassName = JBossClassPoolFactory.class.getName();
      }
      return classPoolFactoryClassName;
   }
   
   public void setClassPoolFactoryName(String classname)
   {
      if (delegateClassPoolFactory != null && classname != null)
      {
         logger.warn("Setting classpool factory name to " + classname + " will be ignored since the classpool factory has already been initialised");
      }
      else
      {
         this.classPoolFactoryClassName = classname;
      }
   }
   
   public ScopedClassPoolFactory createScopedClassPoolFactory(File tmpDir) throws Exception
   {
      String classname = getClassPoolFactoryClassName();
      if (classname.equals(JBossClassPoolFactory.class.getName()))
      {
         delegateClassPoolFactory = new JBossClassPoolFactory(tmpDir);
      }
      else if (classname.equals(JBossUclDelegatingClassPoolFactory.class.getName()))
      {
         delegateClassPoolFactory = new JBossUclDelegatingClassPoolFactory(tmpDir);
      }
      else
      {
         logger.info("Unsupported class pool factory " + classname + " attempting to invoke via taking a (File)");
         try
         {
            Class<?> clazz = Class.forName(classname);
            Constructor<?> ctor = clazz.getConstructor(File.class);
            delegateClassPoolFactory = (ScopedClassPoolFactory)ctor.newInstance(tmpDir);
         }
         catch(Exception e)
         {
            logger.warn("Error instantiating " + classname + " defaulting to " + JBossClassPoolFactory.class.getName());
            classPoolFactoryClassName = classname;
            createScopedClassPoolFactory(tmpDir);
         }
      }
         
      return this;
   }
   
   public ScopedClassPool create(ClassLoader cl, ClassPool src, ScopedClassPoolRepository repository)
   {
      return delegateClassPoolFactory.create(cl, src, repository);
   }

   public ScopedClassPool create(ClassPool src, ScopedClassPoolRepository repository)
   {
      return delegateClassPoolFactory.create(src, repository);
   }
   
   public void attachDeprecatedTranslator()
   {
      AspectManager mgr = AspectManager.instance();
      MBeanServer server = MBeanServerLocator.locateJBoss();
      try
      {
         server.setAttribute(DEFAULT_LOADER_REPOSITORY, new Attribute("Translator", mgr));
      }
      catch (InstanceNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      catch (AttributeNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      catch (InvalidAttributeValueException e)
      {
         throw new RuntimeException(e);
      }
      catch (MBeanException e)
      {
         throw new RuntimeException(e);
      }
      catch (ReflectionException e)
      {
         throw new RuntimeException(e);
      }
   }

   public void detachDeprecatedTranslator()
   {
      MBeanServer server = MBeanServerLocator.locateJBoss();
      try
      {
         server.setAttribute(DEFAULT_LOADER_REPOSITORY, new Attribute("Translator", null));
      }
      catch (InstanceNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      catch (AttributeNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      catch (InvalidAttributeValueException e)
      {
         throw new RuntimeException(e);
      }
      catch (MBeanException e)
      {
         throw new RuntimeException(e);
      }
      catch (ReflectionException e)
      {
         throw new RuntimeException(e);
      }
   }
}
