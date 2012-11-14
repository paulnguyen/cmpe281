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
package org.jboss.aop.deployment;

import java.util.concurrent.atomic.AtomicLong;

import javax.management.JMException;
import javax.management.ListenerNotFoundException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import org.jboss.aop.asintegration.core.AspectManagerServiceDelegate;
import org.jboss.aop.asintegration.core.AspectManagerServiceDelegateJRockit;
import org.jboss.mx.notification.ListenerRegistration;
import org.jboss.mx.notification.ListenerRegistry;

/**
 * AspectManager service meant for use with JRockit or JDK 1.4
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 94155 $
 */
public class AspectManagerService extends AbstractAspectManagerService
{
   /** Sequence number for jmx notifications we send out */ 
   private AtomicLong sequenceNumber = new AtomicLong();   
   
   /** The registered listeners */
   private ListenerRegistry registry = new ListenerRegistry();


   protected AspectManagerServiceDelegate createDelegate()
   {
      return new AspectManagerServiceDelegateJRockit();
   }

   public void attachClass(String classname)
   {
      Notification msg = new Notification("AOP class attached", this, sequenceNumber.incrementAndGet());
      msg.setUserData(classname);
      sendNotification(msg);
   }

   public void addNotificationListener(NotificationListener listener,
         NotificationFilter filter,
         Object handback)
   {
      try
      {
         registry.add(listener, filter, handback);
      }
      catch (JMException e)
      {
         // This shouldn't happen?
         throw new RuntimeException(e.toString());
      }
   }
   
   public void removeNotificationListener(NotificationListener listener)
   throws ListenerNotFoundException
   {
      registry.remove(listener);
   }
   
   public void removeNotificationListener(NotificationListener listener,
               NotificationFilter filter,
               Object handback)
   throws ListenerNotFoundException
   {
      registry.remove(listener, filter, handback);
   }


   public void sendNotification(Notification notification)
   {
      ListenerRegistry.ListenerRegistrationIterator iterator = registry.iterator();
      while (iterator.hasNext())
      {
         ListenerRegistration registration = iterator.nextRegistration();
         NotificationFilter filter = registration.getFilter();
         if (filter == null)
            handleNotification(registration.getListener(), notification, registration.getHandback());
         else if (filter.isNotificationEnabled(notification))
            handleNotification(registration.getListener(), notification, registration.getHandback());
      }
   }
   
   
   /**
    * Handle the notification, the default implementation is to synchronously invoke the listener.
    *
    * @param listener the listener to notify
    * @param notification the notification
    * @param handback the handback object
    */
   public void handleNotification(NotificationListener listener,
                                     Notification notification,
                                     Object handback)
   {
      try
      {
         listener.handleNotification(notification, handback);
      }
      catch (Throwable ignored)
      {
         log.debug("Ignored unhandled throwable from listener", ignored);
      }
   }
}
