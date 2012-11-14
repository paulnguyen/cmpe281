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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.jboss.aop.util.ReflectUtils;

/**
 * Bridge/portability class for resolving annotations in JDK 1.4 and JDK1.5
 * Should be usable in JDK 1.4 and also should support finding invisible annotations.
 * This would be needed for aspect bindings
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 70836 $
 */
public class AnnotationElement extends PortableAnnotationElement
{
   private final static Annotation[] EMPTY_ANNOTATIONS = new Annotation[0];
   /**
    * Get a visible annotation for a particle Method.  If this is JDK 1.5
    * then this is a wrapper for Method.getAnnotation
    *
    * @param method
    * @param annotation
    * @return
    */
   public static <T extends Annotation> T getVisibleAnnotation(Method method, Class<T> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotation(method, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotation(method, annotation);
      }
   }

   /**
    * If constructor has visible annotation return it.  If this is JDK 1.5
    * this is a wrapper for Constructor.getAnnotation()
    *
    * @param con
    * @param annotation
    * @return
    */
   public static <T extends Annotation> T getVisibleAnnotation(Constructor<?> con, Class<T> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotation(con, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotation(con, annotation);
      }
   }

   /**
    * If field has a visible annotation return it.  If this is JDK 1.5 this is a wrapper
    * for Field.getAnnotation.
    *
    * @param field
    * @param annotation
    * @return
    */
   public static <T extends Annotation> T getVisibleAnnotation(Field field, Class<T> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotation(field, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotation(field, annotation);
      }
   }

   /**
    * If class has a visible annotation, return it.  If this is JDK 1.5 this is a wrapper
    * for Class.getAnnotation
    *
    * @param clazz
    * @param annotation
    * @return
    */
   public static <T extends Annotation> T getVisibleAnnotation(Class<?> clazz, Class<T> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotation(clazz, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotation(clazz, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(clazz, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(clazz, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Method m, Class<? extends Annotation> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(m, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(m, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Field f, Class<? extends Annotation> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(f, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(f, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Constructor<?> con, Class<? extends Annotation> annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(con, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(con, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Class<?> clazz, String annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(clazz, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(clazz, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Method m, String annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(m, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(m, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Field f, String annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(f, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(f, annotation);
      }
   }

   public static boolean isVisibleAnnotationPresent(Constructor<?> con, String annotation)
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.isVisibleAnnotationPresent(con, annotation);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.isVisibleAnnotationPresent(con, annotation);
      }
   }

   public static Annotation[] getVisibleAnnotations(Class<?> clazz) throws Exception
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotations(clazz);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotations(clazz);
      }
   }

   public static Annotation[] getVisibleAnnotations(Method m) throws Exception
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotations(m);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotations(m);
      }
   }
   
   public static Annotation[] getVisibleAnnotations(Field f) throws Exception
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotations(f);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotations(f);
      }
   }
   
   public static Annotation[] getVisibleAnnotations(Constructor<?> c) throws Exception
   {
      if (System.getSecurityManager() == null)
      {
         return AnnotationElementAction.NON_PRIVILEGED.getVisibleAnnotations(c);
      }
      else
      {
         return AnnotationElementAction.PRIVILEGED.getVisibleAnnotations(c);
      }
   }
   
   
   private interface AnnotationElementAction
   {
      <T extends Annotation> T getVisibleAnnotation(Method method, Class<T> annotation);

      <T extends Annotation> T getVisibleAnnotation(Constructor<?> con, Class<T> annotation);

      <T extends Annotation> T getVisibleAnnotation(Field field, Class<T> annotation);

      <T extends Annotation> T getVisibleAnnotation(Class<?> clazz, Class<T> annotation);
      
      <T extends Annotation> T getVisibleAnnotation(Method method, String annotation);

      <T extends Annotation> T getVisibleAnnotation(Constructor<?> con, String annotation);

      <T extends Annotation> T getVisibleAnnotation(Field field, String annotation);

      <T extends Annotation> T getVisibleAnnotation(Class<?> clazz, String annotation);

      boolean isVisibleAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotation);

      boolean isVisibleAnnotationPresent(Method m, Class<? extends Annotation> annotation);

      boolean isVisibleAnnotationPresent(Field f, Class<? extends Annotation> annotation);

      boolean isVisibleAnnotationPresent(Constructor<?> con, Class<? extends Annotation> annotation);
      
      boolean isVisibleAnnotationPresent(Class<?> clazz, String annotation);

      boolean isVisibleAnnotationPresent(Method m, String annotation);

      boolean isVisibleAnnotationPresent(Field f, String annotation);

      boolean isVisibleAnnotationPresent(Constructor<?> con, String annotation);

      <T extends Annotation> T[] getVisibleAnnotations(Class<?> clazz) throws Exception;

      <T extends Annotation> T[] getVisibleAnnotations(Method m) throws Exception;
      
      <T extends Annotation> T[] getVisibleAnnotations(Field f) throws Exception;
      
      <T extends Annotation> T[] getVisibleAnnotations(Constructor<?> c) throws Exception;
      
      AnnotationElementAction NON_PRIVILEGED = new AnnotationElementAction()
      {
         public <T extends Annotation> T getVisibleAnnotation(Method method, Class<T> annotation)
         {
            return method.getAnnotation(annotation);
         }

         public <T extends Annotation> T getVisibleAnnotation(Constructor<?> con, Class<T> annotation)
         {
            return con.getAnnotation(annotation);
         }

         public <T extends Annotation> T getVisibleAnnotation(Field field, Class<T> annotation)
         {
            return field.getAnnotation(annotation);
         }

         public <T extends Annotation> T getVisibleAnnotation(Class<?> clazz, Class<T> annotation)
         {
            return clazz.getAnnotation(annotation);
         }
         public <T extends Annotation> T getVisibleAnnotation(Method method, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(method))
            {
                if(a.annotationType().getName().equals(annotation))
                  return (T)a;
            }
            return null;
         }

         public <T extends Annotation> T  getVisibleAnnotation(Constructor<?> con, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(con))
            {
                if(a.annotationType().getName().equals(annotation))
                  return (T)a;
            }
            return null;
         }

         public <T extends Annotation> T  getVisibleAnnotation(Field field, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(field))
            {
                if(a.annotationType().getName().equals(annotation))
                  return (T)a;
            }
            return null;
         }

         public <T extends Annotation> T getVisibleAnnotation(Class<?> clazz, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(clazz))
            {
                if(a.annotationType().getName().equals(annotation))
                  return (T)a;
            }
            return null;
         }

         public boolean isVisibleAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotation)
         {
            return clazz.isAnnotationPresent(annotation);
         }

         public boolean isVisibleAnnotationPresent(Method m, Class<? extends Annotation> annotation)
         {
            return m.isAnnotationPresent(annotation);
         }

         public boolean isVisibleAnnotationPresent(Field f, Class<? extends Annotation> annotation)
         {
            return f.isAnnotationPresent(annotation);
         }

         public boolean isVisibleAnnotationPresent(Constructor<?> con, Class<? extends Annotation> annotation)
         {
            return con.isAnnotationPresent(annotation);
         }
         
         public boolean isVisibleAnnotationPresent(Class<?> clazz, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(clazz))
            {
                if(a.annotationType().getName().equals(annotation))
                  return true;
            }
            return false;
         }

         public boolean isVisibleAnnotationPresent(Method m, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(m))
            {
                if(a.annotationType().getName().equals(annotation))
                  return true;
            }
            return false;
         }

         public boolean isVisibleAnnotationPresent(Field f, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(f))
            {
                if(a.annotationType().getName().equals(annotation))
                  return true;
            }
            return false;
         }

         public boolean isVisibleAnnotationPresent(Constructor<?> con, String annotation)
         {
            for(Annotation a : getVisibleAnnotations(con))
            {
                if(a.annotationType().getName().equals(annotation))
                  return true;
            }
            return false;
         }

         public <T extends Annotation> T[] getVisibleAnnotations(Class<?> clazz)
         {
            return (T[])clazz.getAnnotations();
         }

         public <T extends Annotation> T[] getVisibleAnnotations(Method m)
         {
            if (m.getName().startsWith("access$") && !ReflectUtils.isNotAccessMethod(m))
            {
               return (T[]) EMPTY_ANNOTATIONS;
            }
            return (T[])m.getAnnotations();
         }
         
         public <T extends Annotation> T[] getVisibleAnnotations(Field f)
         {
            return (T[])f.getAnnotations();
         }
         
         public <T extends Annotation> T[] getVisibleAnnotations(Constructor<?> c)
         {
            return (T[])c.getAnnotations();
         }
      };
      

      AnnotationElementAction PRIVILEGED = new AnnotationElementAction()
      {
         public <T extends Annotation> T getVisibleAnnotation(final Method method, final Class<T> annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  return method.getAnnotation(annotation);
               }
            });
         }

         public <T extends Annotation> T  getVisibleAnnotation(final Constructor<?> con, final Class<T> annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  return con.getAnnotation(annotation);
               }
            });
         }

         public <T extends Annotation> T getVisibleAnnotation(final Field field, final Class<T> annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  return field.getAnnotation(annotation);
               }
            });
         }

         public <T extends Annotation> T getVisibleAnnotation(final Class<?> clazz, final Class<T> annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  return clazz.getAnnotation(annotation);
               }
            });
         }

         public <T extends Annotation> T getVisibleAnnotation(final Method method, final String annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  for(Annotation a : method.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return (T)a;
                  }
                  return null;
               }
            });
         }

         public <T extends Annotation> T getVisibleAnnotation(final Constructor<?> con, final String annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  for(Annotation a : con.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return (T)a;
                  }
                  return null;
               }
            });
         }

         public <T extends Annotation> T getVisibleAnnotation(final Field field, final String annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  for(Annotation a : field.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return (T)a;
                  }
                  return null;
               }
            });
         }

         public <T extends Annotation> T getVisibleAnnotation(final Class<?> clazz, final String annotation)
         {
            return AccessController.doPrivileged(new PrivilegedAction<T>(){
               public T run()
               {
                  for(Annotation a : clazz.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return (T)a;
                  }
                  return null;
               }
            });
         }
         
         public boolean isVisibleAnnotationPresent(final Class<?> clazz, final Class<? extends Annotation> annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  return clazz.isAnnotationPresent(annotation) ? Boolean.TRUE : Boolean.FALSE;
               }
            });
            
            return present.booleanValue();
         }

         public boolean isVisibleAnnotationPresent(final Method m, final Class<? extends Annotation> annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  return m.isAnnotationPresent(annotation) ? Boolean.TRUE : Boolean.FALSE;
               }
            });
            
            return present.booleanValue();
         }

         public boolean isVisibleAnnotationPresent(final Field f, final Class<? extends Annotation> annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  return f.isAnnotationPresent(annotation) ? Boolean.TRUE : Boolean.FALSE;
               }
            });
            return present;
         }

         public boolean isVisibleAnnotationPresent(final Constructor<?> con, final Class<? extends Annotation> annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  return con.isAnnotationPresent(annotation) ? Boolean.TRUE : Boolean.FALSE;
               }
            });
            return present;
         }


         public boolean isVisibleAnnotationPresent(final Class<?> clazz, final String annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  for(Annotation a : clazz.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return Boolean.TRUE;
                  }
                  return Boolean.FALSE;
               }
            });
            
            return present.booleanValue();
         }

         public boolean isVisibleAnnotationPresent(final Method m, final String annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  for(Annotation a : m.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return Boolean.TRUE;
                  }
                  return Boolean.FALSE;
               }
            });
            
            return present.booleanValue();
         }

         public boolean isVisibleAnnotationPresent(final Field f, final String annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  for(Annotation a : f.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return Boolean.TRUE;
                  }
                  return Boolean.FALSE;
               }
            });
            return present;
         }

         public boolean isVisibleAnnotationPresent(final Constructor<?> con, final String annotation)
         {
            Boolean present = AccessController.doPrivileged(new PrivilegedAction<Boolean>(){
               public Boolean run()
               {
                  for(Annotation a : con.getAnnotations())
                  {
                      if(a.annotationType().getName().equals(annotation))
                        return Boolean.TRUE;
                  }
                  return Boolean.FALSE;
               }
            });
            return present;
         }

         public <T extends Annotation> T[] getVisibleAnnotations(final Class<?> clazz) throws Exception 
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<T[]>()
               {
                  public T[] run() throws Exception
                  {
                     return (T[])clazz.getAnnotations();
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw e.getException();
            }
         }

         public <T extends Annotation> T[] getVisibleAnnotations(final Method m) throws Exception 
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<T[]>(){
                  public T[] run() throws Exception
                  {
                     if (m.getName().startsWith("access$") && !ReflectUtils.isNotAccessMethod(m))
                     {
                        return (T[])EMPTY_ANNOTATIONS;
                     }
                     return (T[])m.getAnnotations();
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw e.getException();
            }
         }
         
         public <T extends Annotation> T[] getVisibleAnnotations(final Field f) throws Exception
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<T[]>(){
                  public T[] run() throws Exception
                  {
                     return (T[])f.getAnnotations();
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw e.getException();
            }
         }
         
         public <T extends Annotation> T[] getVisibleAnnotations(final Constructor<?> c) throws Exception
         {
            try
            {
               return AccessController.doPrivileged(new PrivilegedExceptionAction<T[]>(){
                  public T[] run() throws Exception
                  {
                     return (T[])c.getAnnotations();
                  }
               });
            }
            catch (PrivilegedActionException e)
            {
               throw e.getException();
            }
         }
      };
   }
}
