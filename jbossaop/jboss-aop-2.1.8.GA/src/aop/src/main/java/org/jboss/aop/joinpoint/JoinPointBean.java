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
package org.jboss.aop.joinpoint;

import java.lang.annotation.Annotation;

import org.jboss.aop.Advisor;

/**
 * Represents a call made on a joinpoint
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public interface JoinPointBean
{
   /**
    * Gets the advisor
    */
   Advisor getAdvisor();

   /**
    * Gets the advisor's class
    */
   Class<?> getClazz();

   /**
    * Resolves metadata on the class
    */
   Object resolveClassMetaData(Object key, Object attr);

   /**
    * Resolves annotations for the class
    */
   <T extends Annotation> T resolveClassAnnotation(Class<T> annotation);

   /**
    * Resolves annotations on the particular joinpoint (field, constructor, method etc.)
    */
   <T extends Annotation> T resolveAnnotation(Class<T> annotation);

}