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
package org.jboss.aop.microcontainer.lifecycle;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.TypeMatcher;
import org.jboss.aop.pointcut.Util;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ClassExpression;
import org.jboss.aop.pointcut.ast.TypeExpressionParser;

/**
 * A binding for callbacks for when a MC bean reaches a certain state
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class LifecycleCallbackBinding
{
   /**
    * The name of this binding
    */
   protected String name;
   
   /**
    * The class to match
    */
   protected ClassExpression classes;
   
   /**
    * A type expression picking out the classes to apply this to
    */
   protected ASTStart ast;
   
   /**
    * A list of beans
    */
   List<LifecycleCallbackDefinition> lifecycleCallbackDefinitions = new ArrayList<LifecycleCallbackDefinition>();
   
   /**
    * This will be an instance of org.jboss.dependency.spi.ControllerState from the MC project
    */
   Object controllerState;


   public LifecycleCallbackBinding(String name, String classString, String typeExpression, Object controllerState) throws Exception
   {
      this.name = name;
      
      if (classString == null && typeExpression == null)
      {
         throw new RuntimeException("Need either classString or typeExpression");
      }
      if (classString != null && typeExpression != null)
      {
         throw new RuntimeException("Should not use both classString and typeExpression");
      }
      
      if (classString != null)
      {
         this.classes = new ClassExpression(classString);
      }
      else if (typeExpression != null)
      {
         this.ast = new TypeExpressionParser(new StringReader(typeExpression)).Start();
      }
      
      this.controllerState = controllerState;
   }
   
   public String getName()
   {
      return name;
   }

   public void addLifecycleCallback(String bean, String installMethod, String uninstallMethod)
   {
      lifecycleCallbackDefinitions.add(new LifecycleCallbackDefinition(bean, installMethod, uninstallMethod));
   }

   public List<LifecycleCallbackDefinition> getLifecycleCallbacks()
   {
      return lifecycleCallbackDefinitions;
   }
   
   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (!(obj instanceof LifecycleCallbackBinding)) return false;
      return ((LifecycleCallbackBinding) obj).getName().equals(name);
   }

   public int hashCode()
   {
      return name.hashCode();
   }

   public boolean matches(Advisor advisor, Class<?> clazz)
   {
      if (classes != null)
      {
         return Util.matchesClassExpr(classes, clazz, advisor);
      }
      else
      {
         TypeMatcher matcher = new TypeMatcher(advisor, clazz);
         return ((Boolean) ast.jjtAccept(matcher, null)).booleanValue();
      }
   }
   
   public Object getControllerState()
   {
      return controllerState;
   }
   
}
