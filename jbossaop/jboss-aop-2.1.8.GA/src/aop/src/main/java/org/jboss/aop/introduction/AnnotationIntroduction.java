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
package org.jboss.aop.introduction;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.annotation.factory.ast.ASTAnnotation;
import org.jboss.annotation.factory.ast.AnnotationParser;
import org.jboss.annotation.factory.ast.ParseException;
import org.jboss.aop.pointcut.AnnotationMatcher;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.TypeExpressionParser;
import org.jboss.aop.util.logging.AOPLogger;
import org.jboss.util.StringPropertyReplacer;

import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 79516 $
 */
public class AnnotationIntroduction
{
   final static AOPLogger logger = AOPLogger.getLogger(AnnotationIntroduction.class);
   
   public static AnnotationIntroduction createMethodAnnotationIntroduction(String methodExpr, String annotationExpr, boolean invisible)
   {
      String expr = "method(" + methodExpr + ")";
      return new AnnotationIntroduction(expr, annotationExpr, invisible);
   }

   public static AnnotationIntroduction createConstructorAnnotationIntroduction(String conExpr, String annotationExpr, boolean invisible)
   {
      String expr = "constructor(" + conExpr + ")";
      return new AnnotationIntroduction(expr, annotationExpr, invisible);
   }

   public static AnnotationIntroduction createFieldAnnotationIntroduction(String fieldExpr, String annotationExpr, boolean invisible)
   {
      String expr = "field(" + fieldExpr + ")";
      return new AnnotationIntroduction(expr, annotationExpr, invisible);
   }

   public static AnnotationIntroduction createClassAnnotationIntroduction(String classExpr, String annotationExpr, boolean invisible)
   {
      String expr = "class(" + classExpr + ")";
      return new AnnotationIntroduction(expr, annotationExpr, invisible);
   }

   public static AnnotationIntroduction createComplexAnnotationIntroduction(String expr, String annotationExpr, boolean invisible)
   {
      return new AnnotationIntroduction(expr, annotationExpr, invisible);
   }


   private String originalExpression;
   private String originalAnnotationExpr;
   private ASTStart target;
   private ASTAnnotation annotation;
   private boolean invisible;

   private AnnotationIntroduction(String expr, String annotationExpr, boolean invisible)
   {
      this.invisible = invisible;

      originalAnnotationExpr = StringPropertyReplacer.replaceProperties(annotationExpr);
      originalExpression = expr;
      try
      {
         if (AspectManager.verbose && logger.isTraceEnabled())
         {
            logger.trace("Creating annotation from " + originalAnnotationExpr);
         }
         AnnotationParser parser = new AnnotationParser(new StringReader(originalAnnotationExpr));
         org.jboss.annotation.factory.ast.ASTStart start = parser.Start();
         annotation = (ASTAnnotation) start.jjtGetChild(0);
      }
      catch (ParseException e)
      {
         throw new RuntimeException(annotationExpr, e);  //To change body of catch statement use Options | File Templates.
      }
      try
      {
         TypeExpressionParser parser = new TypeExpressionParser(new StringReader(expr));
         target = parser.Start();
      }
      catch (org.jboss.aop.pointcut.ast.ParseException e)
      {
         throw new RuntimeException(expr, e);  //To change body of catch statement use Options | File Templates.
      }
   }

   public boolean matches(Advisor advisor, CtClass clazz)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, clazz);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, CtMethod method)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, method);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, CtConstructor con)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, con);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, CtField field)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, field);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, Class<?> clazz)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, clazz);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, Method method)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, method);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, Constructor<?> con)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, con);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public boolean matches(Advisor advisor, Field field)
   {
      AnnotationMatcher matcher = new AnnotationMatcher(advisor, field);
      return ((Boolean) target.jjtAccept(matcher, null)).booleanValue();
   }

   public String getOriginalExpression()
   {
      return originalExpression;
   }

   public String getOriginalAnnotationExpr()
   {
      return originalAnnotationExpr;
   }

   public ASTAnnotation getAnnotation()
   {
      return annotation;
   }

   public boolean isInvisible()
   {
      return invisible;
   }

   public ASTStart getTarget()
   {
      return target;
   }

}
