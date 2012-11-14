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
package org.jboss.aop.deployers;

import org.jboss.logging.Logger;

/**
 * A bean to be deployed with the aspect library in jboss 5 to be able to deploy the 
 * base-aspects.xml file and so that other things like EJB 3 can depend on this. 
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class JBossAspectLibrary
{
   private static final Logger log = Logger.getLogger(JBossAspectLibrary.class);
   AbstractAspectManager aspectManager;

   public AbstractAspectManager getAspectManager()
   {
      return aspectManager;
   }

   public void setAspectManager(AbstractAspectManager aspectManagerBean)
   {
      this.aspectManager = aspectManagerBean;
   }
   
   public void start() throws Exception
   {
      System.out.println("\n\n!!! JBossAspectLibrary should be removed from AOP!!!\n\n");
      log.debug("Starting JBoss AspectLibrary");
      aspectManager.deployBaseAspects();
   }
   
   public void stop()
   {
      log.debug("Stopping JBoss AspectLibrary");
      aspectManager.undeployBaseAspects();
   }

}
