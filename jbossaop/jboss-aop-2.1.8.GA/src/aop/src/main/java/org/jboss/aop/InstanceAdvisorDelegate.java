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
package org.jboss.aop;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.joinpoint.Joinpoint;
import org.jboss.aop.metadata.SimpleMetaData;

/**
 * Initialisation and getting of instance and joinpoint aspects needed by the various kinds of
 * InstanceAdvisor implementations
 *  
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 92143 $
 */
public class InstanceAdvisorDelegate implements Serializable
{
   private static final long serialVersionUID = -5421366346785427537L;
   /** Indicates that a call to the factory has been made, but the factory returned null */
   private static final Object NULL_ASPECT = new Object();
   protected transient WeakReference<Advisor> classAdvisor;
   InstanceAdvisor instanceAdvisor;
   protected transient WeakHashMap<AspectDefinition, Object> aspects;
   protected transient WeakHashMap<AspectDefinition, ConcurrentHashMap<Joinpoint, Object>> joinpointAspects;
   protected SimpleMetaData metadata;


   public InstanceAdvisorDelegate(Advisor classAdvisor, InstanceAdvisor instanceAdvisor)
   {
      this.instanceAdvisor = instanceAdvisor;
      this.classAdvisor = new WeakReference<Advisor>(classAdvisor);
   }

   public Advisor getAdvisor()
   {
      if (classAdvisor != null)
      {
         return classAdvisor.get();
      }
      return null;
   }
   
   public void initialize()
   {
      initializeAspects();
      initializeJoinpointAspects();
   }
   
   private Advisor getClassAdvisor()
   {
      return getAdvisor();
   }
   
   private synchronized void initializeAspects()
   {
      if (getClassAdvisor() == null) return;
      if (aspects != null) return; // doublecheck I know, but I don't want to do synchronization if not needed
      //ClassAdvisor cadvisor = (ClassAdvisor) classAdvisor;
      if (instanceAdvisor instanceof Advisor)
      {
         Advisor ia = (Advisor)instanceAdvisor;
         Set<AspectDefinition> instanceDefs = ia.getPerInstanceAspectDefinitions();
         if (instanceDefs.size() > 0)
         {
            aspects = new WeakHashMap<AspectDefinition, Object>();
            for (AspectDefinition def : instanceDefs)
            {
               ia.addPerInstanceAspect(def);
               Object aspect = def.getFactory().createPerInstance(getClassAdvisor(), instanceAdvisor);
               aspects.put(def, aspect);
            }
         }
      }
      Set<AspectDefinition> defs = getClassAdvisor().getPerInstanceAspectDefinitions();
      if (defs.size() > 0)
      {
         if (aspects == null)
         {
            aspects = new WeakHashMap<AspectDefinition, Object>();
         }
         for (AspectDefinition def : defs)
         {
            Object aspect = def.getFactory().createPerInstance(getClassAdvisor(), instanceAdvisor);
            aspects.put(def, aspect);
         }
      }
   }

   private synchronized void initializeJoinpointAspects()
   {
      if (getClassAdvisor() == null) return;
      if (joinpointAspects != null) return; // doublecheck I know, but I don't want to do synchronization if not needed
      if (instanceAdvisor instanceof Advisor)
      {
         Advisor ia = (Advisor)instanceAdvisor;
         Map<AspectDefinition, Set<Joinpoint>> instanceJpAspects = ia.getPerInstanceJoinpointAspectDefinitions();
         
         if (instanceJpAspects.size() > 0)
         {
            joinpointAspects = new WeakHashMap<AspectDefinition, ConcurrentHashMap<Joinpoint, Object>>();
            for (AspectDefinition def : instanceJpAspects.keySet())
            {
               initJoinpointAspect(def, instanceJpAspects);
               Set<Joinpoint> joinpoints = instanceJpAspects.get(def);
               ia.addPerInstanceJoinpointAspect(joinpoints, def);
            }
         }
      }
      
      Map<AspectDefinition, Set<Joinpoint>> jpAspects = getClassAdvisor().getPerInstanceJoinpointAspectDefinitions();
      if (jpAspects.size() > 0)
      {
         joinpointAspects = new WeakHashMap<AspectDefinition, ConcurrentHashMap<Joinpoint, Object>>();
         for (AspectDefinition def : jpAspects.keySet())
         {
            initJoinpointAspect(def, jpAspects);
         }
      }
   }
   
   private void initJoinpointAspect(AspectDefinition def, Map<AspectDefinition, Set<Joinpoint>> jpAspects)
   {
      ConcurrentHashMap<Joinpoint, Object> joins = new ConcurrentHashMap<Joinpoint, Object>();
      joinpointAspects.put(def, joins);
      Set<Joinpoint> joinpoints = jpAspects.get(def);
      for (Joinpoint joinpoint : joinpoints)
      {
         Object aspect = def.getFactory().createPerJoinpoint(getClassAdvisor(),
               instanceAdvisor, joinpoint);
         if (aspect == null)
         {
            joins.put(joinpoint, NULL_ASPECT);
         }
         else
         {
            joins.put(joinpoint, aspect);
         }
      }
   }
   
   public Object getPerInstanceAspect(String def)
   {
      for (AspectDefinition d : aspects.keySet())
      {
         if (d.getName().equals(def)) return aspects.get(d);
      }
      return null;
   }

   public Object getPerInstanceAspect(AspectDefinition def)
   {
      // aspects is a weak hash map of AspectDefinitions so that perinstance advices can be undeployed/redeployed
      if (aspects == null)
      {
         initializeAspects();
         if (aspects != null)
         {
            return aspects.get(def);
         }
      }
      if (!aspects.containsKey(def))
      {
         synchronized (this) // doublecheck, but I don't want to synchronize everywhere and dynamic aspects are rare
         {
            if (aspects.containsKey(def)) return aspects.get(def);
            if (classAdvisor != null && getClassAdvisor() instanceof ClassAdvisor)
            {
               ClassAdvisor cadvisor = (ClassAdvisor) getClassAdvisor();
               cadvisor.getPerInstanceAspectDefinitions().add(def);
               Object aspect = def.getFactory().createPerInstance(null, null);
               WeakHashMap<AspectDefinition, Object> copy = new WeakHashMap<AspectDefinition, Object>(aspects);
               copy.put(def, aspect);
               aspects = copy;
               return aspect;
            }
         }
      }
      return aspects.get(def);
   }

   public Object getPerInstanceJoinpointAspect(Joinpoint joinpoint, AspectDefinition def)
   {
      // aspects is a weak hash map of AspectDefinitions so that perinstance advices can be undeployed/redeployed
      if (joinpointAspects == null)
      {
         initializeJoinpointAspects();
         return getJoinpointAspect(def, joinpoint);
      }
      Object aspect = getJoinpointAspect(def, joinpoint);
      if (aspect == null)
      {
         synchronized (this) // doublecheck, but I don't want to synchronize everywhere and dynamic aspects are rare
         {
            aspect = getJoinpointAspect(def, joinpoint);
            if (aspect != null)
            {
               if (aspect == NULL_ASPECT)
               {
                  return null;
               }
               return aspect;
            }
            if (classAdvisor != null && getClassAdvisor() instanceof ClassAdvisor)
            {
               ClassAdvisor cadvisor = (ClassAdvisor) getClassAdvisor();
               cadvisor.addPerInstanceJoinpointAspect(joinpoint, def);
               aspect = def.getFactory().createPerJoinpoint(getClassAdvisor(), instanceAdvisor, joinpoint);
               WeakHashMap<AspectDefinition, ConcurrentHashMap<Joinpoint, Object>> copy = new WeakHashMap<AspectDefinition, ConcurrentHashMap<Joinpoint, Object>>(joinpointAspects);
               Map<Joinpoint, Object> map = copy.get(def);
               if (map == null)
               {
                  map = new ConcurrentHashMap<Joinpoint, Object>();
               }
               if (aspect == null)
               {
                  map.put(joinpoint, NULL_ASPECT);
               }
               else
               {
                  map.put(joinpoint, aspect);
               }
               joinpointAspects = copy;
            }
         }
      }
      if (aspect == NULL_ASPECT)
      {
         return null;
      }
      return aspect;
   }

   private Object getJoinpointAspect(AspectDefinition def, Joinpoint joinpoint)
   {
      if (joinpointAspects == null) return null;
      Map<Joinpoint, Object> map = joinpointAspects.get(def);
      if (map == null) return null;
      Object aspect = map.get(joinpoint);
      return aspect;
   }
   
   public SimpleMetaData getMetaData()
   {
      if (metadata == null)
      {
         synchronized (this) // doublecheck :(
         {
            if (metadata == null)
            {
               metadata = new SimpleMetaData();
            }
         }
      }
      return metadata;
   }

}
