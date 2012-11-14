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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.Util;
import org.jboss.aop.pointcut.ast.ClassExpression;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70500 $
 */
public abstract class ClassMetaDataBinding
{
   protected ClassExpression classExpr;
   protected String expr;
   protected String name;
   protected String tag;
   protected ArrayList<WeakReference<Advisor>> advisors = new ArrayList<WeakReference<Advisor>>();
   protected ClassMetaDataLoader loader;

   public ClassMetaDataBinding(ClassMetaDataLoader loader, String name, String tag, String exp)
   {
      this.name = name;
      this.tag = tag;
      this.loader = loader;
      expr = exp;
      this.classExpr = new ClassExpression(expr);
   }

   public ClassMetaDataLoader getLoader()
   {
      return loader;
   }

   public String getName()
   {
      return name;
   }
   
   public String getTag()
   {
      return tag;
   }

   public void addAdvisor(Advisor advisor)
   {
      // Don't hold a direct reference to an advisor because of undeploy and redeploy.  Use WeakRefrences because
      // we may be having in the future an Advisor per instance.
      synchronized (advisors)
      {
         Iterator<WeakReference<Advisor>> it = advisors.iterator();
         while (it.hasNext())
         {
            WeakReference<Advisor> ref = it.next();
            Advisor adv = ref.get();
            if (adv == null) it.remove();
         }
         advisors.add(new WeakReference<Advisor>(advisor));
      }
      advisor.addClassMetaData(this);
   }

   public void clearAdvisors()
   {
      synchronized (advisors)
      {
         for (int i = 0; i < advisors.size(); i++)
         {
            WeakReference<Advisor> ref = advisors.get(i);
            Advisor advisor = ref.get();
            if (advisor != null)
               advisor.removeClassMetaData(this);
         }
         advisors.clear();
      }
   }

   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (!(obj instanceof ClassMetaDataBinding)) return false;
      return ((ClassMetaDataBinding) obj).getName().equals(name);
   }

   public int hashCode()
   {
      return name.hashCode();
   }

   public boolean matches(Advisor advisor, Class<?> clazz)
   {
      return Util.matchesClassExpr(classExpr, clazz, advisor);
   }

   public boolean matches(Advisor advisor, CtClass clazz) throws NotFoundException
   {
      return Util.matchesClassExpr(classExpr, clazz, advisor);
   }

   public String getClassExpr()
   {
      return expr;
   }
}
