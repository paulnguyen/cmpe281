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
package org.jboss.aop.annotation;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.annotation.factory.AnnotationCreator;
import org.jboss.aop.util.UnmodifiableEmptyCollections;

import javassist.CtMember;

/**
 * Repository for annotations that is used by the ClassAdvisor to override annotations.
 * Generics have not been added to this class since that causes problems with EJB3 who have a class overriding this one
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70916 $
 */
public class AnnotationRepository
{
   private static final String CLASS_ANNOTATION = "CLASS";
   
   /** Read/Write lock to be used when lazy creating the collections */
   protected Object lazyCollectionLock = new Object();

   @SuppressWarnings("unchecked") volatile Map annotations = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   @SuppressWarnings("unchecked") volatile Map classAnnotations = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   @SuppressWarnings("unchecked") volatile Map disabledAnnotations = UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP;
   
   @SuppressWarnings("unchecked") 
   public Map getAnnotations()
   {
	   return annotations;
   }
   
   @SuppressWarnings("unchecked") 
   public Map getClassAnnotations()
   {
	   return classAnnotations;
   }

   public void addClassAnnotation(String annotation, String value)
   {
      initClassAnnotationsMap();
      classAnnotations.put(annotation, value);
   }

   @SuppressWarnings("unchecked")
   public void addClassAnnotation(Class annotation, Object value)
   {
      initClassAnnotationsMap();
      classAnnotations.put(annotation.getName(), value);
   }

   @SuppressWarnings("unchecked")
   public Object resolveClassAnnotation(Class annotation)
   {
      Object value = classAnnotations.get(annotation.getName());
      boolean reinsert = value instanceof String;
      value = extractAnnotation(value, annotation);
      if (reinsert)
      {
         classAnnotations.put(annotation.getName(), value);
      }
      return value;
   }

   @SuppressWarnings("unchecked")
   public Object resolveAnnotation(Member m, Class annotation)
   {
      Object value = resolveAnnotation(m, annotation.getName());
      boolean reinsert = value instanceof String;
      value = extractAnnotation(value, annotation);
      if (reinsert)
      {
         addAnnotation(m, annotation, value);
      }
      return value;
   }

   @SuppressWarnings("unchecked")
   protected Object extractAnnotation(Object value, Class annotation)
   {
      if (value == null) return null;
      if (value instanceof String)
      {
         String expr = (String) value;
         try
         {
            return AnnotationCreator.createAnnotation(expr, annotation);
         }
         catch (Exception e)
         {
            throw new RuntimeException("Bad annotation expression " + expr, e);
         }
      }
      return value;
   }

   @SuppressWarnings("unchecked")
   protected Object resolveAnnotation(Member m, String annotation)
   {
      Map map = (Map) annotations.get(m);
      if (map != null)
      {
         return map.get(annotation);
      }
      return null;
   }
   
   @SuppressWarnings("unchecked")
   public void disableAnnotation(Member m, String annotation)
   {
      List annotationList = (List)disabledAnnotations.get(m);
      if (annotationList == null)
      {
         annotationList = new ArrayList();
         initDisabledAnnotationsMap();
         disabledAnnotations.put(m,annotationList);
      }
      annotationList.add(annotation);
   }
   
   @SuppressWarnings("unchecked")
   public void disableAnnotation(String annotation)
   {
      List annotationList = (List)disabledAnnotations.get(CLASS_ANNOTATION);
      if (annotationList == null)
      {
         annotationList = new ArrayList();
         initDisabledAnnotationsMap();
         disabledAnnotations.put(CLASS_ANNOTATION,annotationList);
      }
      annotationList.add(annotation);
   }
   
   @SuppressWarnings("unchecked")
   public void enableAnnotation(String annotation)
   {
      List annotationList = (List)disabledAnnotations.get(CLASS_ANNOTATION);
      if (annotationList != null)
      {
         annotationList.remove(annotation);
      }
      
   }
   
   @SuppressWarnings("unchecked")
   public boolean isDisabled(Member m, Class annotation)
   {
      return isDisabled(m,annotation.getName());
   }
   
   @SuppressWarnings("unchecked")
   public boolean isDisabled(Member m, String annotation)
   {
      List overrideList = (List)disabledAnnotations.get(m);
      if (overrideList != null)
      {
         Iterator overrides = overrideList.iterator();
         while (overrides.hasNext())
         {
            String override = (String)overrides.next();
            if (override.equals(annotation))
               return true;
         }
      }
      return false;
   }
   
   @SuppressWarnings("unchecked")
   public boolean isDisabled(Class annotation)
   {
      return isDisabled(annotation.getName());
   }
   
   @SuppressWarnings("unchecked")
   public boolean isDisabled(String annotation)
   {
      List overrideList = (List)disabledAnnotations.get(CLASS_ANNOTATION);
      if (overrideList != null)
      {
         Iterator overrides = overrideList.iterator();
         while (overrides.hasNext())
         {
            String override = (String)overrides.next();
            if (override.equals(annotation))
               return true;
         }
      }
      return false;
   }

   @SuppressWarnings("unchecked")
   public void addAnnotation(Member m, Class annotation, Object value)
   {
      Map map = (Map) annotations.get(m);
      if (map == null)
      {
         map = new HashMap();
         initAnnotationsMap();
         annotations.put(m, map);
      }
      map.put(annotation.getName(), value);
   }

   @SuppressWarnings("unchecked")
   public void addAnnotation(Member m, String annotation, Object value)
   {
      Map map = (Map) annotations.get(m);
      if (map == null)
      {
         map = new HashMap();
         initAnnotationsMap();
         annotations.put(m, map);
      }
      map.put(annotation, value);
   }

   public boolean hasClassAnnotation(String annotation)
   {
      return classAnnotations.containsKey(annotation);
   }

   @SuppressWarnings("unchecked")
   public boolean hasClassAnnotation(Class annotation)
   {
      return classAnnotations.containsKey(annotation.getName());
   }

   @SuppressWarnings("unchecked")
   public boolean hasAnnotation(Member m, Class annotation)
   {
      return resolveAnnotation(m, annotation.getName()) != null;
   }

   public boolean hasAnnotation(Member m, String annotation)
   {
      return resolveAnnotation(m, annotation) != null;
   }

   @SuppressWarnings("unchecked")
   public boolean hasAnnotation(CtMember m, String annotation)
   {
      Set set = (Set) annotations.get(m);
      if (set != null) return set.contains(annotation);
      return false;
   }

   @SuppressWarnings("unchecked")
   public void addAnnotation(CtMember m, String annotation)
   {
      Set set = (Set) annotations.get(m);
      if (set == null)
      {
         set = new HashSet();
         initAnnotationsMap();
         annotations.put(m, set);
      }
      set.add(annotation);
   }

   @SuppressWarnings("unchecked")
   protected void initAnnotationsMap()
   {
      if (annotations == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (annotations == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               annotations = new ConcurrentHashMap();;
            }
         }
      }
   }

   @SuppressWarnings("unchecked")
   protected void initClassAnnotationsMap()
   {
      if (classAnnotations == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (classAnnotations == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               classAnnotations = new ConcurrentHashMap();;
            }
         }
      }
   }

   @SuppressWarnings("unchecked")
   protected void initDisabledAnnotationsMap()
   {
      if (disabledAnnotations == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
      {
         synchronized(lazyCollectionLock)
         {
            if (disabledAnnotations == UnmodifiableEmptyCollections.EMPTY_CONCURRENT_HASHMAP)
            {
               disabledAnnotations = new ConcurrentHashMap();;
            }
         }
      }
   }
}
