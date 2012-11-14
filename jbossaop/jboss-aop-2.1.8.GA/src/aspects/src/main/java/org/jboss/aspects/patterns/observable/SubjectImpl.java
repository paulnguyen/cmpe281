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
package org.jboss.aspects.patterns.observable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The subject implementation.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 71280 $
 */
public class SubjectImpl implements Subject
{
   /** The observers */
   private Set<Observer> observers = Collections.synchronizedSet(new HashSet<Observer>());
   
   /** The subject */
   private Object subject;

   public SubjectImpl(Object subject)
   {
      this.subject = subject;
   }
   
   public void addObserver(Observer observer)
   {
      if(observers.contains(observer))
         return;  // return right away since we have that already.
      observers.add(observer);
   }
   
   public void removeObserver(Observer observer)
   {
      observers.remove(observer);
   }
   
   public void notifyObservers()
   {
      Subject obj = (Subject) subject;
      
      synchronized (observers)
      {
         for (Observer observer : observers)
         {
            observer.fireChange(obj);
         }
      }
   }
}
