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

import org.jboss.aop.expressions.ConstructorExpression;
import org.jboss.aop.expressions.MethodExpression;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70500 $
 *
 */
public class SimpleClassMetaDataBinding extends ClassMetaDataBinding
{
   public class QueuedMetaData
   {
      protected Pattern expr;
      public String tag;
      public String attr;
      public Object value;

      public QueuedMetaData(String exp, String tag, String attr, Object value)
      {
         exp = exp.replaceAll("\\.", "\\\\.");
         exp = exp.replaceAll("\\*", ".*");
         this.expr = Pattern.compile(exp);
         this.tag = tag;
         this.attr = attr;
         this.value = value;
      }

      public String getExpr() { return this.expr.toString(); }
      public boolean matches(String match)
      {
         Matcher m = expr.matcher(match);
         return m.matches();
      }
   }

   public class QueuedMethodMetaData
   {
      public MethodExpression expr;
      public String tag;
      public String attr;
      public Object value;

      public QueuedMethodMetaData(String expr, String tag, String attr, Object value)
      {
         this.expr = new MethodExpression(expr);
         this.tag = tag;
         this.attr = attr;
         this.value = value;
      }
   }

   public class QueuedConstructorMetaData
   {
      public ConstructorExpression expr;
      public String tag;
      public String attr;
      public Object value;

      public QueuedConstructorMetaData(String expr, String tag, String attr, Object value)
      {
         this.expr = new ConstructorExpression(expr);
         this.tag = tag;
         this.attr = attr;
         this.value = value;
      }
   }

   protected SimpleMetaData defaultMetaData = new SimpleMetaData();
   protected SimpleMetaData classMetaData = new SimpleMetaData();
   protected ArrayList<QueuedMethodMetaData> queuedMethodMetaData = new ArrayList<QueuedMethodMetaData>();
   protected ArrayList<QueuedMetaData> queuedFieldMetaData = new ArrayList<QueuedMetaData>();
   protected ArrayList<QueuedConstructorMetaData> queuedConstructorMetaData = new ArrayList<QueuedConstructorMetaData>();

   public SimpleClassMetaDataBinding(ClassMetaDataLoader loader, String name, String tag, String expr)
   {
      super(loader, name, tag, expr);
   }

   public void addDefaultMetaData(String tag, String attr, Object value)
   {
      defaultMetaData.addMetaData(tag, attr, value);
   }

   public void addClassMetaData(String tag, String attr, Object value)
   {
      classMetaData.addMetaData(tag, attr, value);
   }

   /**
    * Queues up binding for method meta data
    */
   public void queueMethodMetaData(String methodExpr, String tag, String attr, Object value)
   {
      queuedMethodMetaData.add(new QueuedMethodMetaData(methodExpr, tag, attr, value));
   }

   public ArrayList<QueuedMethodMetaData> getQueuedMethodMetaData()
   {
      return queuedMethodMetaData;
   }

   /**
    * Queues up binding for method meta data
    */
   public void queueFieldMetaData(String fieldExpr, String tag, String attr, Object value)
   {
      queuedFieldMetaData.add(new QueuedMetaData(fieldExpr, tag, attr, value));
   }

   public ArrayList<QueuedMetaData> getQueuedFieldMetaData()
   {
      return queuedFieldMetaData;
   }

   /**
    * Queues up binding for method meta data
    */
   public void queueConstructorMetaData(String constructorExpr, String tag, String attr, Object value)
   {
      queuedConstructorMetaData.add(new QueuedConstructorMetaData(constructorExpr, tag, attr, value));
   }

   public ArrayList<QueuedConstructorMetaData> getQueuedConstructorMetaData()
   {
      return queuedConstructorMetaData;
   }

   public SimpleMetaData getDefaultMetaData()
   {
      return defaultMetaData;
   }

   public SimpleMetaData getClassMetaData()
   {
      return classMetaData;
   }

}
