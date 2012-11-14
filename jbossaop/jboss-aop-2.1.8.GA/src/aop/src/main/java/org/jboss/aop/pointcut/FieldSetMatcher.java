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
package org.jboss.aop.pointcut;

import javassist.CtField;
import javassist.NotFoundException;
import org.jboss.aop.Advisor;
import org.jboss.aop.pointcut.ast.ASTSet;
import org.jboss.aop.pointcut.ast.ASTStart;

import java.lang.reflect.Field;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class FieldSetMatcher extends FieldMatcher
{
   private CtField ctField;
   private Field field;

   public FieldSetMatcher(Advisor advisor, CtField field, ASTStart start) throws NotFoundException
   {
      super(advisor, field, start);
      this.ctField = field;
   }

   public FieldSetMatcher(Advisor advisor, Field field, ASTStart start)
   {
      super(advisor, field, start);
      this.field = field;
   }

   public Object visit(ASTSet node, Object data)
   {
      return node.jjtGetChild(0).jjtAccept(this, null);
   }

   protected Boolean resolvePointcut(Pointcut p)
   {
      if (ctField != null)
      {
         try
         {
            return new Boolean(p.matchesSet(advisor, ctField));
         }
         catch (NotFoundException e)
         {
            throw new RuntimeException(e);  //To change body of catch statement use Options | File Templates.
         }
      }
      else
      {
         return new Boolean(p.matchesSet(advisor, field));
      }
   }
}
