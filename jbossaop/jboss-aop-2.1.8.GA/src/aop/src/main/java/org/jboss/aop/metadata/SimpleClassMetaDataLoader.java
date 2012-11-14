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
package org.jboss.aop.metadata;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.aop.Advisor;
import org.jboss.aop.expressions.ConstructorExpression;
import org.jboss.aop.util.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <description>
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70500 $
 * @see <related>
 */
public class SimpleClassMetaDataLoader implements ClassMetaDataLoader
{
   public final static SimpleClassMetaDataLoader singleton = new SimpleClassMetaDataLoader();

   // Public --------------------------------------------------------
   public ClassMetaDataBinding importMetaData(Element element, String name, String tag, String classExpr) throws Exception
   {
      SimpleClassMetaDataBinding data = new SimpleClassMetaDataBinding(this, name, tag, classExpr);
      Element defaultElement = XmlHelper.getOptionalChild(element, "default");
      if (defaultElement != null)
      {
         NodeList children = defaultElement.getChildNodes();
         boolean hasTag = false;
         for (int i = 0; i < children.getLength(); i++)
         {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
               Element child = (Element) children.item(i);
               String attr = child.getTagName();
               String val = XmlHelper.getElementContent(child, "");
               data.addDefaultMetaData(tag, attr, val);
               hasTag = true;
            }
         }
         if (!hasTag) data.addDefaultMetaData(tag, MetaDataResolver.EMPTY_TAG, new Object());
      }

      Element classElement = XmlHelper.getOptionalChild(element, "class");
      if (classElement != null)
      {
         NodeList children = classElement.getChildNodes();
         boolean hasTag = false;
         for (int i = 0; i < children.getLength(); i++)
         {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
               Element child = (Element) children.item(i);
               String attr = child.getTagName();
               String val = XmlHelper.getElementContent(child, "");
               data.addClassMetaData(tag, attr, val);
               hasTag = true;
            }
         }
         if (!hasTag)
         {
            data.addClassMetaData(tag, MetaDataResolver.EMPTY_TAG, new Object());
         }
      }

      Iterator<Element> it = XmlHelper.getChildrenByTagName(element, "method");
      while (it.hasNext())
      {
         Element method = it.next();
         String methodName = method.getAttribute("name");
         String expr = method.getAttribute("expr");
         if (methodName != null && methodName.equals(""))
         {
            methodName = null;
         }
         if (expr != null && expr.equals(""))
         {
            expr = null;
         }
         if (expr == null && methodName == null) throw new RuntimeException("neither a name or expr attribute has been defined");
         if (expr != null && methodName != null) throw new RuntimeException("cannot specify a name and expr in same place");

         if (expr == null && methodName != null)
         {
            expr = "* " + methodName + "(..)";
         }

         NodeList children = method.getChildNodes();
         boolean hadTag = false;
         for (int i = 0; i < children.getLength(); i++)
         {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
               Element child = (Element) children.item(i);
               String attr = child.getTagName();
               String val = XmlHelper.getElementContent(child, "");
               data.queueMethodMetaData(expr, tag, attr, val);
               hadTag = true;
            }
         }
         if (!hadTag)  // put in an empty tag
         {
            // put in an empty tag for annotations with no attributes
            data.queueMethodMetaData(expr, tag, MetaDataResolver.EMPTY_TAG, new Object());
         }

      }
      it = XmlHelper.getChildrenByTagName(element, "field");
      while (it.hasNext())
      {
         Element field = it.next();
         String fieldName = field.getAttribute("name");
         NodeList children = field.getChildNodes();
         boolean hadTag = false;
         for (int i = 0; i < children.getLength(); i++)
         {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
               Element child = (Element) children.item(i);
               String attr = child.getTagName();
               String val = XmlHelper.getElementContent(child, "");
               data.queueFieldMetaData(fieldName, tag, attr, val);
               hadTag = true;
            }
         }
         if (!hadTag)  // put in an empty tag
         {
            // put in an empty tag for annotations with no attributes
            data.queueFieldMetaData(fieldName, tag, MetaDataResolver.EMPTY_TAG, new Object());
         }
      }
      it = XmlHelper.getChildrenByTagName(element, "constructor");
      while (it.hasNext())
      {
         Element field = it.next();
         String expr = field.getAttribute("expr");
         NodeList children = field.getChildNodes();
         boolean hadTag = false;
         for (int i = 0; i < children.getLength(); i++)
         {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
            {
               Element child = (Element) children.item(i);
               String attr = child.getTagName();
               String val = XmlHelper.getElementContent(child, "");
               data.queueConstructorMetaData(expr, tag, attr, val);
               hadTag = true;
            }
         }
         if (!hadTag)  // put in an empty tag
         {
            // put in an empty tag for annotations with no attributes
            data.queueConstructorMetaData(expr, tag, MetaDataResolver.EMPTY_TAG, new Object());
         }
      }
      return data;
   }

   public void bind(Advisor advisor, ClassMetaDataBinding data, Method[] methods, Field[] fields, Constructor<?>[] constructors) throws Exception
   {
      SimpleClassMetaDataBinding meta = (SimpleClassMetaDataBinding) data;
      SimpleMetaData defaultData = advisor.getDefaultMetaData();
      defaultData.mergeIn(meta.getDefaultMetaData());
      SimpleMetaData classData = advisor.getClassMetaData();
      classData.mergeIn(meta.getClassMetaData());


      if (methods != null)
      {
         MethodMetaData methodData = advisor.getMethodMetaData();
         bindMethodMetaData(advisor, meta, methodData, methods);
      }

      if (fields != null)
      {
         FieldMetaData fieldData = advisor.getFieldMetaData();
         bindFieldMetaData(meta, fieldData, fields);
      }
      if (constructors != null)
      {
         ConstructorMetaData condata = advisor.getConstructorMetaData();
         bindConstructorMetaData(meta, condata, constructors);
      }
   }

   protected void bindMethodMetaData(Advisor advisor, SimpleClassMetaDataBinding data, MethodMetaData methodMetaData, Method[] advisedMethods)
   {
      if (advisor.getClazz() == null)
      {
         //Class has not yet been attached, so method tables etc. will be empty, no point in going on
         //TODO: move into bind()?
         return;
      }
      
      boolean exactMatch = data.matches(advisor, advisor.getClazz());
      ArrayList<SimpleClassMetaDataBinding.QueuedMethodMetaData> queuedData = data.getQueuedMethodMetaData();
      for (int i = 0; i < queuedData.size(); i++)
      {
         SimpleClassMetaDataBinding.QueuedMethodMetaData queued = queuedData.get(i);
         for (int j = 0; j < advisedMethods.length; j++)
         {
            if (queued.expr.matches(advisedMethods[j]))
            {
               boolean add = true; 
               if (advisor.chainOverridingForInheritedMethods())
               {
                  //old skool weaving doesn't support metadata overriding, so only do this extra work for generated advisors
                  //exactMatch = data.exactlyMatchesAdvisor(advisor);
                  if (!exactMatch)
                  {
                     //Only add if the method is inherited and we had no exact match on the advisor already
                     add = (advisor.getClazz() != advisedMethods[j].getDeclaringClass());
                     
                     if (add)
                     {
                        if (methodMetaData.getMethodMetaData(advisedMethods[j], queued.tag, queued.attr) != null)
                        {
                           add = methodMetaData.tagWasMatchedInexactly(advisedMethods[j], queued.tag, queued.attr);
                        }
                     }
                  }
               }
               
               if (add)
               {
                  methodMetaData.addMethodMetaData(advisedMethods[j], queued.tag, queued.attr, queued.value, exactMatch);
               }
            }
         }
      }
   }

   protected void bindFieldMetaData(SimpleClassMetaDataBinding data, FieldMetaData fieldMetaData, Field[] advisedFields)
   {
      ArrayList<SimpleClassMetaDataBinding.QueuedMetaData> queuedData = data.getQueuedFieldMetaData();
      for (int i = 0; i < queuedData.size(); i++)
      {
         SimpleClassMetaDataBinding.QueuedMetaData queued = queuedData.get(i);
         for (int j = 0; j < advisedFields.length; j++)
         {
            String fieldName = advisedFields[j].getName();
            if (queued.matches(fieldName))
            {
               fieldMetaData.addFieldMetaData(advisedFields[j], queued.tag, queued.attr, queued.value);
            }
         }
      }
   }

   protected void bindConstructorMetaData(SimpleClassMetaDataBinding data, ConstructorMetaData conMetaData, Constructor<?>[] advisedCons)
   {
      ArrayList<SimpleClassMetaDataBinding.QueuedConstructorMetaData> queuedData = data.getQueuedConstructorMetaData();
      for (int i = 0; i < queuedData.size(); i++)
      {
         SimpleClassMetaDataBinding.QueuedConstructorMetaData queued = queuedData.get(i);
         ConstructorExpression expression = queued.expr;
         for (int j = 0; j < advisedCons.length; j++)
         {
            if (expression.matchParamsOnly(advisedCons[j]))
            {
               conMetaData.addConstructorMetaData(advisedCons[j], queued.tag, queued.attr, queued.value);
            }
         }
      }
   }

   public void bind(Advisor advisor, ClassMetaDataBinding data, CtMethod[] methods, CtField[] fields, CtConstructor[] constructors) throws Exception
   {
      SimpleClassMetaDataBinding meta = (SimpleClassMetaDataBinding) data;
      SimpleMetaData defaultData = advisor.getDefaultMetaData();
      defaultData.mergeIn(meta.getDefaultMetaData());
      SimpleMetaData classData = advisor.getClassMetaData();
      classData.mergeIn(meta.getClassMetaData());
      if (methods != null)
      {
         MethodMetaData methodData = advisor.getMethodMetaData();
         bindMethodMetaData(meta, methodData, methods);
      }

      if (fields != null)
      {
         FieldMetaData fieldData = advisor.getFieldMetaData();
         bindFieldMetaData(meta, fieldData, fields);
      }
      if (constructors != null)
      {
         ConstructorMetaData condata = advisor.getConstructorMetaData();
         bindConstructorMetaData(meta, condata, constructors);
      }
   }


   protected void bindMethodMetaData(SimpleClassMetaDataBinding data, MethodMetaData methodMetaData, CtMethod[] advisedMethods) throws NotFoundException
   {
      ArrayList<SimpleClassMetaDataBinding.QueuedMethodMetaData> queuedData = data.getQueuedMethodMetaData();
      for (int i = 0; i < queuedData.size(); i++)
      {
         SimpleClassMetaDataBinding.QueuedMethodMetaData queued = queuedData.get(i);
         for (int j = 0; j < advisedMethods.length; j++)
         {
            if (queued.expr.matches(advisedMethods[j]))
            {
               methodMetaData.addMethodMetaData(advisedMethods[j], queued.tag, queued.attr, queued.value);
            }
         }
      }
   }

   protected void bindFieldMetaData(SimpleClassMetaDataBinding data, FieldMetaData fieldMetaData, CtField[] advisedFields)
   {
      ArrayList<SimpleClassMetaDataBinding.QueuedMetaData> queuedData = data.getQueuedFieldMetaData();
      for (int i = 0; i < queuedData.size(); i++)
      {
         SimpleClassMetaDataBinding.QueuedMetaData queued = queuedData.get(i);
         for (int j = 0; j < advisedFields.length; j++)
         {
            String fieldName = advisedFields[j].getName();
            if (queued.matches(fieldName))
            {
               fieldMetaData.addFieldMetaData(advisedFields[j].getName(), queued.tag, queued.attr, queued.value);
            }
         }
      }
   }

   protected void bindConstructorMetaData(SimpleClassMetaDataBinding data, ConstructorMetaData conMetaData, CtConstructor[] advisedCons) throws NotFoundException
   {
      ArrayList<SimpleClassMetaDataBinding.QueuedConstructorMetaData> queuedData = data.getQueuedConstructorMetaData();
      for (int i = 0; i < queuedData.size(); i++)
      {
         SimpleClassMetaDataBinding.QueuedConstructorMetaData queued = queuedData.get(i);
         ConstructorExpression expression = queued.expr;
         for (int j = 0; j < advisedCons.length; j++)
         {
            if (expression.matchParamsOnly(advisedCons[j]))
            {
               conMetaData.addConstructorMetaData(advisedCons[j], queued.tag, queued.attr, queued.value);
            }
         }
      }
   }

}
