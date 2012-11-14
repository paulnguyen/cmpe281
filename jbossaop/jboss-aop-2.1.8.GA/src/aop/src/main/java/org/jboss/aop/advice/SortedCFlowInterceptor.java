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
package org.jboss.aop.advice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.aop.joinpoint.Invocation;
import org.jboss.aop.pointcut.CFlowMatcher;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;

/**
 * This interceptor represents an interceptor chain bound with one or more cflow
 * expressions. Such interceptor chain is kept in a precedence-sorted order. When
 * this interceptor is invoked, it wraps the internal chain in the invocation.
 * 
 * Notice that, given that each interceptor can have a cflow expression associated
 * with it, an interceptor will be invoked only if it has no cflow, or if it has a
 * cflow expression that matches the current invocation.
 * 
 * The SortedCFlowInterceptor should belong always to unitary chains; otherwise, we
 * cannot ensure the interceptor invocations will take place in the expected order.
 * 
 * @author  <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @version $Revision: 94085 $
 * @see CFlowInterceptor
 * @see PrecedenceSorter
 */
class SortedCFlowInterceptor implements Interceptor
{
   private Interceptor[] sortedInterceptors;
   private CachedCFlow[] sortedCFlows;
   private CachedCFlow[] cflows;
   private Map<Integer, Interceptor[]> chains;
   
   /**
    * Constructor.
    * 
    * @param sortedInterceptors a list of all interceptors in a precedence-sorted
    *                           order.
    * @param sortedCFlows       a list of the cflow expressions associated with each
    *                           interceptor of the {@code sortedInterceptors} array
    * @see PrecedenceSorter
    */
   public SortedCFlowInterceptor(Interceptor[] sortedInterceptors,
         ASTCFlowExpression[] sortedCFlows)
   {
      this.sortedInterceptors = sortedInterceptors;
      this.sortedCFlows = new CachedCFlow[sortedCFlows.length];
      Collection<CachedCFlow> cflowSet = new HashSet<CachedCFlow>();
      for (int i = 0; i < sortedCFlows.length; i++)
      {
         if (sortedCFlows[i] != null)
         {
            this.sortedCFlows[i] = new CachedCFlow(sortedCFlows[i]);
            cflowSet.add(this.sortedCFlows[i]);
         }
      }
      
      this.chains = new ChainCache(2, 1, true);
      this.cflows = cflowSet.toArray(new CachedCFlow[cflowSet.size()]);
   }
   
   public String getName()
   {
      return "SortedCFlowInterceptor";
   }

   public Object invoke(Invocation invocation) throws Throwable
   {
      // create a key identifying the overall cflow match status
      int key = 0;
      for (int i = 0; i < cflows.length; i++)
      {
         key = key + cflows[i].evaluate(invocation) << i;
      }
      Interceptor[] chain;
      // if we have seen the same match status before, just reuse the chain
      if (this.chains.containsKey(key))
      {
         chain = this.chains.get(key);
      }
      else
      {
         Collection<Interceptor> chainCollection = new ArrayList<Interceptor>();
         for (int i = 0; i < this.sortedInterceptors.length; i++)
         {
            if (this.sortedCFlows[i] == null || this.sortedCFlows[i].matches())
            {
               chainCollection.add(this.sortedInterceptors[i]);
            }
         }
         chain = chainCollection.toArray(new Interceptor[chainCollection.size()]);
         // store the chain in the cache for reuse on subsequent invocations
         this.chains.put(key, chain);
      }
      return invocation.getWrapper(chain).invokeNext();
   }
   
   /**
    * Wraps a cflow expression and caches its matching result during an joinpoint
    * invocation.
    */
   private static class CachedCFlow
   {
      private ASTCFlowExpression cflow;
      private boolean matches;
      
      public CachedCFlow(ASTCFlowExpression cflow)
      {
         this.cflow = cflow;
         this.matches = false;
      }
      
      public byte evaluate(Invocation invocation)
      {
         this.matches = new CFlowMatcher().matches(cflow, invocation);
         return this.matches? (byte) 1: 0;
      }
      
      public boolean matches()
      {
         return this.matches;
      }
   }

   @SuppressWarnings("serial")
   private static class ChainCache extends LinkedHashMap<Integer, Interceptor[]>
   {
      //This class MUST use default serial version id see JBAOP-752
      public ChainCache(int initialCapacity, float loadFactor, boolean accessOrder)
      {
         super(initialCapacity, loadFactor, accessOrder);
      }

      @Override
      protected boolean removeEldestEntry(Entry<Integer,Interceptor[]> eldest)
      {
         return size() > 5;
      }
   }
}