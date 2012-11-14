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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.io.StringReader;
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.TypeMatcher;
import org.jboss.aop.pointcut.Util;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.pointcut.ast.TypeExpressionParser;
import org.jboss.aop.pointcut.ast.ParseException;
import javassist.CtClass;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70842 $
 */
public class InterfaceIntroduction
{
   public static class Mixin
   {
      protected String classname;
      protected String[] interfaces;
      protected String construction;
      protected boolean trans;

      public Mixin() {}

      public Mixin(String classname, String[] interfaces, String construction, boolean trans)
      {
         this.classname = classname;
         this.interfaces = interfaces;
         this.construction = construction;
         this.trans = trans;
      }

      public String getClassName()
      {
         return classname;
      }

      public String[] getInterfaces()
      {
         return interfaces;
      }

      public String getConstruction()
      {
         return construction;
      }

      public boolean isTransient()
      {
         return trans;
      }

      public void setClassname(String classname)
      {
         this.classname = classname;
      }

      public void setInterfaces(String[] interfaces)
      {
         this.interfaces = interfaces;
      }

      public void setConstruction(String construction)
      {
         this.construction = construction;
      }

      public void setTrans(boolean trans)
      {
         this.trans = trans;
      }


   }

   protected String name;
   protected ArrayList<WeakReference<Advisor>> advisors = new ArrayList<WeakReference<Advisor>>();
   protected String[] interfaces;
   protected ArrayList<InterfaceIntroduction.Mixin> mixins = new ArrayList<InterfaceIntroduction.Mixin>();
   protected ClassExpression classExpr;
   protected ASTStart ast;
   
   protected String constructorClass; // name of the class containing the mixin constructor method
   protected String constructorMethod; // name of the mixin constructor method

   public InterfaceIntroduction()
   {

   }
   
   public InterfaceIntroduction(String name, String exp, String[] interfaces)
   {
      this.name = name;
      this.interfaces = interfaces;
      this.classExpr = new ClassExpression(exp);
   }
   
   // call this constructor only when constructor method receives the mixin target
   // as parameter, for posterior validation of constructor method signature
   public InterfaceIntroduction(String name, String exp, String[] interfaces,
         String constructorClass, String constructorMethod)
   {
      this(name, exp, interfaces);
      this.constructorClass = constructorClass;
      this.constructorMethod = constructorMethod;
   }

   public InterfaceIntroduction(String name, ASTStart ast, String[] interfaces)
   {
      this.name = name;
      this.ast = ast;
      this.interfaces = interfaces;
   }

   // call this constructor only when constructor method receives the mixin target
   // as parameter, for posterior validation of constructor method signature
   public InterfaceIntroduction(String name, ASTStart ast, String[] interfaces,
         String constructorClass, String constructorMethod)
   {
      this(name, ast, interfaces);
      this.constructorClass = constructorClass;
      this.constructorMethod = constructorMethod;
   }

   public void setClassExpression(String exp)
   {
      this.classExpr = new ClassExpression(exp);
   }

   public void setTypeExpression(String exp)
   {
      try
      {
         ast = new TypeExpressionParser(new StringReader(exp)).Start();
      }
      catch (ParseException e)
      {
         throw new RuntimeException(e);
      }
   }

   public void setMixins(ArrayList<InterfaceIntroduction.Mixin> mixins)
   {
      this.mixins = mixins;
   }
   
   public void addMixin(Mixin mixin)
   {
      mixins.add(mixin);
   }

   public void setInterfaces(String[] interfaces)
   {
      this.interfaces = interfaces;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public String[] getInterfaces()
   {
      return interfaces;
   }

   public ArrayList<InterfaceIntroduction.Mixin> getMixins()
   {
      return mixins;
   }

   // this value is set only when there is a mixin constructor method and this
   // method receives the target as parameter
   public String getConstructorClass()
   {
      return this.constructorClass;
   }

   // this value is set only when there is a mixin constructor method and this
   // method receives the target as parameter
   public String getConstructorMethod()
   {
      return this.constructorMethod;
   }
   
   public void addAdvisor(Advisor advisor)
   {
      advisors.add(new WeakReference<Advisor>(advisor));
      advisor.addInterfaceIntroduction(this);
   }

   public void clearAdvisors()
   {
      for (int i = 0; i < advisors.size(); i++)
      {
         Advisor advisor = advisors.get(i).get();
         if (advisor != null)
            advisor.removeInterfaceIntroduction(this);
      }
   }

   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (!(obj instanceof InterfaceIntroduction)) return false;
      return ((InterfaceIntroduction) obj).getName().equals(name);
   }

   public int hashCode()
   {
      return name.hashCode();
   }

   public String getClassExpr()
   {
      if (classExpr == null) return null;
      return classExpr.getOriginal();
   }

   public ASTStart getAst()
   {
      return ast;
   }

   public boolean matches(Advisor advisor, CtClass clazz) throws Exception
   {
      if (classExpr != null)
         return Util.matchesClassExpr(classExpr, clazz, advisor);
      else
      {
         TypeMatcher matcher = new TypeMatcher(advisor, clazz);
         return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
      }
   }

   public boolean matches(Advisor advisor, Class<?> clazz)
   {
      if (classExpr != null)
         return Util.matchesClassExpr(classExpr, clazz, advisor);
      else
      {
         TypeMatcher matcher = new TypeMatcher(advisor, clazz);
         return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
      }
   }
}
