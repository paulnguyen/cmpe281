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
package org.jboss.aop.advice;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Method;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.InstanceAdvisor;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.util.logging.AOPLogger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 81450 $
 */
public class GenericAspectFactory extends AspectFactoryWithClassLoaderSupport
{   
   private static final AOPLogger logger = AOPLogger.getLogger(GenericAspectFactory.class);
   
   final static Class<?>[] ADVISOR_INJECTOR_SIGNATURE = new Class[]{Advisor.class};
   final static Class<?>[] INSTANCE_ADVISOR_INJECTOR_SIGNATURE = new Class[]{InstanceAdvisor.class};
   final static Class<?>[] JOINPOINT_INJECTOR_SIGNATURE = new Class[]{Joinpoint.class};

   private String classname;
   private Element element;

   public GenericAspectFactory(String classname, Element element)
   {
      this.classname = classname;
      this.element = element;
   }

   /** Augment the PropertyEditorManager search path to incorporate the JBoss
    * specific editors. This simply references the PropertyEditors.class to
    * invoke its static initialization block.
    */
   static
   {
      SecurityActions.initEditors(); // I got ClassCirculatoryErrors so I don't reference PropertyEditors.class in jboss common
   }


   public String getClassname()
   {
      return classname;
   }

   public void setClassname(String classname)
   {
      this.classname = classname;
   }

   public String getName()
   {
      return classname;
   }

   public Element getElement()
   {
      return element;
   }

   public void setElement(Element element)
   {
      this.element = element;
   }

   @Deprecated
   /**
    * @deprecated Not public API
    */
   public Class<?> getClazz()
   {
      try
      {
         return loadClass(classname);
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }
   
   private Class<?> getClazz(Advisor advisor)
   {
      try
      {
         ClassLoader cl = null;
         if (advisor != null)
         {
            //Get the correct classloader based on the class of the advisor
            Class<?> clazz = advisor.getClazz();
            cl = SecurityActions.getClassLoader((clazz != null) ? clazz: advisor.getClass());
         }
         if (cl != null)
         {
            pushScopedClassLoader(cl);
         }
         return loadClass(classname);
      }
      catch(ClassNotFoundException e)
      {
         throw new RuntimeException(e);
      }
      finally
      {
         popScopedClassLoader();
      }
   }

   public Object createPerVM()
   {
      try
      {
         Object aspect = getClazz(null).newInstance();
         configureInstance(aspect, null, null, null);
         return aspect;
      }
      catch (Exception re)
      {
         if (re instanceof RuntimeException) throw (RuntimeException) re;
         throw new RuntimeException(re);
      }
   }

   public Object createPerClass(Advisor advisor)
   {
      try
      {
         Object aspect = getClazz(advisor).newInstance();
         configureInstance(aspect, advisor, null, null);
         return aspect;
      }
      catch (Exception re)
      {
         if (re instanceof RuntimeException) throw (RuntimeException) re;
         throw new RuntimeException(re);
      }
   }

   public Object createPerInstance(Advisor advisor, InstanceAdvisor instanceAdvisor)
   {
      try
      {
         Object aspect = getClazz(advisor).newInstance();
         configureInstance(aspect, advisor, instanceAdvisor, null);
         return aspect;
      }
      catch (Exception re)
      {
         if (re instanceof RuntimeException) throw (RuntimeException) re;
         throw new RuntimeException(re);
      }
   }

   public Object createPerJoinpoint(Advisor advisor, Joinpoint jp)
   {
      try
      {
         Object aspect = getClazz(advisor).newInstance();
         configureInstance(aspect, advisor, null, jp);
         return aspect;
      }
      catch (Exception re)
      {
         if (re instanceof RuntimeException) throw (RuntimeException) re;
         throw new RuntimeException(re);
      }
   }

   public Object createPerJoinpoint(Advisor advisor, InstanceAdvisor instanceAdvisor, Joinpoint jp)
   {
      try
      {
         Object aspect = getClazz(advisor).newInstance();
         configureInstance(aspect, advisor, instanceAdvisor, jp);
         return aspect;
      }
      catch (Exception re)
      {
         if (re instanceof RuntimeException) throw (RuntimeException) re;
         throw new RuntimeException(re);
      }
   }

   protected void configureInstance(Object instance, Advisor advisor, InstanceAdvisor instanceAdvisor, Joinpoint jp)
   {
      if (element == null) return;
      BeanInfo beanInfo = null;
      try
      {
         beanInfo = Introspector.getBeanInfo(instance.getClass());
      }
      catch (IntrospectionException e)
      {
         throw new RuntimeException(e.getMessage(), e);
      }
      PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
      if (descriptors == null)
      {
         descriptors = new PropertyDescriptor[0];
      }

      NodeList children = element.getChildNodes();

      for (int i = 0; i < children.getLength(); i++)
      {
         if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
         {
            Element element = (Element) children.item(i);
            String tagname = element.getTagName();

            String attributeName = element.getAttribute("name");

            if (tagname.equals("attribute"))
            {
               String attributeText = element.getFirstChild().getNodeValue();
               setAttribute(instance, descriptors, attributeName, attributeText);
            }
            else if (tagname.equals("advisor-attribute"))
            {
               injectAdvisor(instance, advisor, attributeName);
            }
            else if (tagname.equals("joinpoint-attribute"))
            {
               injectJoinpoint(instance, jp, attributeName);
            }
            else if (tagname.equals("instance-advisor-attribute"))
            {
               injectInstanceAdvisor(instance, instanceAdvisor, attributeName);
            }
         }
      }
   }

   protected void setAttribute(Object instance, PropertyDescriptor[] descriptors, String attributeName, String attributeText)
   {
      boolean foundProperty = false;

      for (int i = 0; i < descriptors.length; i++)
      {
         if (attributeName.equalsIgnoreCase(descriptors[i].getName()))
         {
            foundProperty = true;
            Class<?> typeClass = descriptors[i].getPropertyType();

            Object value;
            PropertyEditor editor = PropertyEditorManager.findEditor(typeClass);
            if (editor == null)
            {
               throw new RuntimeException
                       ("No property editor for attribute: " + attributeName +
                       "; type=" + typeClass + " in " + classname);
            }

            editor.setAsText(attributeText);
            value = editor.getValue();
            try
            {
               descriptors[i].getWriteMethod().invoke(instance, new Object[]{value});
            }
            catch (Exception e)
            {
               throw new RuntimeException("Error setting attribute '" +
                       attributeName + "' in " + classname, e);
            }
            break;
         }
      }//for descriptors

      if (!foundProperty)
      {
         throw new RuntimeException("Could not find attribute '" + attributeName
                 + "' in aspect/interceptor class " + classname);
      }
   }

   protected void injectAdvisor(Object instance, Advisor advisor, String attributeName)
   {
      if (advisor == null)
      {
         if (AspectManager.verbose && logger.isDebugEnabled())
         {
            logger.debug("Ignoring attempt to set advisor attribute on PER_VM scoped aspect/interceptor: " + classname);
         }
         return;
      }

      String injector = getInjectorName(attributeName);
      try
      {
         Method m = instance.getClass().getMethod(injector, ADVISOR_INJECTOR_SIGNATURE);
         m.invoke(instance, new Object[]{advisor});
      }
      catch (Exception e)
      {
         throw new RuntimeException("Aspect/interceptor " + classname + " does not contain a public org.jboss.aop.Advisor injector called " + injector);
      }
   }

   protected void injectJoinpoint(Object instance, Joinpoint jp, String attributeName)
   {
      if (jp == null)
      {
         if (AspectManager.verbose && logger.isDebugEnabled())
         {
            logger.debug("Ignoring attempt to set joinpoint attribute on aspect/interceptor: " + classname + " which is not scoped PER_JOINPOINT");
         }
         return;
      }

      String injector = getInjectorName(attributeName);
      try
      {
         Method m = instance.getClass().getMethod(injector, JOINPOINT_INJECTOR_SIGNATURE);
         m.invoke(instance, new Object[]{jp});
      }
      catch (Exception e)
      {
         throw new RuntimeException("Aspect/interceptor " + classname + " does not contain a public org.jboss.aop.Joinpoint injector called " + injector);
      }
   }

   protected void injectInstanceAdvisor(Object instance, InstanceAdvisor instanceAdvisor, String attributeName)
   {
      if (instanceAdvisor == null)
      {
         if (AspectManager.verbose && logger.isDebugEnabled())
         {
            logger.debug("Ignoring attempt to set instance advisor attribute on aspect/interceptor: " + classname + " which is not scoped PER_INSTANCE or PER_JOINPOINT");
         }
         return;
      }

      String injector = getInjectorName(attributeName);
      try
      {
         Method m = instance.getClass().getMethod(injector, INSTANCE_ADVISOR_INJECTOR_SIGNATURE);
         m.invoke(instance, new Object[]{instanceAdvisor});
      }
      catch (Exception e)
      {
         throw new RuntimeException("Aspect/interceptor " + classname + " does not contain a public org.jboss.aop.InstanceAdvisor injector called " + injector);
      }
   }

   protected String getInjectorName(String attributeName)
   {
      //For now assume that the Attribute name begins with upper case
      char firstChar = attributeName.charAt(0);
      if (Character.isLowerCase(firstChar))
      {
         attributeName = Character.toUpperCase(firstChar) + attributeName.substring(1);
      }

      return "set" + attributeName;
   }
}

