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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.jboss.aop.AspectManager;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.util.logging.AOPLogger;

/**
 *
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 88401 $
 */
public class PrecedenceSorter
{
   private static final AOPLogger logger = AOPLogger.getLogger(PrecedenceSorter.class);
   
   static Comparator<InterceptorEntry> interceptorComparator = new Comparator<InterceptorEntry>()
   {
      public int compare(InterceptorEntry objA, InterceptorEntry objB)
      {
         InterceptorEntry entryA = objA;
         InterceptorEntry entryB = objB;

         return entryA.precedenceOrder - entryB.precedenceOrder;
      }
   };

   static class InterceptorEntry
   {
      Interceptor interceptor;
      ASTCFlowExpression cflow;
      GeneratedAdvisorInterceptor factoryWrapper;
      int originalOrder;
      int precedenceOrder = -1;
      String classname;
      String method;

      InterceptorEntry(GeneratedAdvisorInterceptor factoryWrapper)
      {
         this.factoryWrapper = factoryWrapper;
         classname = factoryWrapper.getName();

         InterceptorFactory ifac = factoryWrapper.getDelegate();

         if (ifac instanceof GenericInterceptorFactory)
         {
            //Dynamically added interceptors
            classname = ((GenericInterceptorFactory)ifac).getClassName();
         }
         else
         {
            AspectFactory af = factoryWrapper.getAspect().getFactory();
            classname = af.getName();
         }

         if (ifac instanceof AdviceFactory)
         {
            method = ((AdviceFactory)ifac).getAdvice();
         }
      }


      InterceptorEntry(Interceptor interceptor)
      {
         this.interceptor = interceptor;

         String interceptorName = null;
         if (interceptor instanceof PerInstanceInterceptor)
         {
            PerInstanceInterceptor icptr = (PerInstanceInterceptor)interceptor;
            interceptorName = icptr.getName();
         }
         else if (interceptor instanceof PerJoinpointInterceptor)
         {
            PerJoinpointInterceptor icptr = (PerJoinpointInterceptor)interceptor;
            interceptorName = icptr.getName();
         }
         else if (interceptor instanceof CFlowInterceptor)
         {
            CFlowInterceptor icptr = (CFlowInterceptor)interceptor;
            interceptorName = icptr.getName();
         }
         else
         {
            interceptorName = interceptor.getClass().getName();
         }

         try
         {
            boolean isAdvice = interceptorName.startsWith("org.jboss.aop.advice.");
            if (isAdvice)
            {
               String name = interceptor.getName();
               int index = name.lastIndexOf(".");
               classname = name.substring(0, index);
               method = name.substring(index + 1);
            }
            else
            {
               classname = interceptorName;
            }
         }
         catch (RuntimeException e)
         {
            logger.error(interceptor.getName());
            throw e;
         }
      }
      
      InterceptorEntry(Interceptor interceptor, ASTCFlowExpression cflow)
      {
         this(interceptor);
         this.cflow = cflow;
      }

      public String toString()
      {
         return "Entry: " + precedenceOrder + " (" + originalOrder + ")interceptorClass=" + classname + "; adviceMethod=" + method;
      }

   }

   private static boolean matches(InterceptorEntry ientry, PrecedenceDefEntry pentry)
   {
      if (ientry.classname != null && pentry.interceptorClass != null)
      {
         if (ientry.classname.equals(pentry.interceptorClass))
         {
            if (ientry.method == null)
            {
               if (pentry.adviceMethod == null)
               {
                  return true;
               }
            }
            else if (pentry.adviceMethod != null)
            {
               //This was:
               //return ientry.classname.equals(pentry.interceptorClass);
               return ientry.method.equals(pentry.adviceMethod);
            }
         }
      }
      return false;
   }


   public static PrecedenceDefEntry[] createOverallPrecedence(AspectManager manager)
   {
      PrecedenceGraph precedenceGraph = new PrecedenceGraph(manager.getPrecedenceDefs().values());
      return precedenceGraph.getSortedPrecedence();
   }

   @Deprecated
   public static ArrayList<PrecedenceDefEntry> mergePrecedenceDef(ArrayList<PrecedenceDefEntry> overall, PrecedenceDef precedenceDef)
   {
      // This method does not follow the improved algorithm used on PrecedenceGraph.
      // If you have the precedences
      //		1) A, D
      //    2) C, E
      //    3) C, D
      //After adding 2) to 1) since there is no relationship defined you get:
      //    i) A, D, C, E
      //After adding 3) to i) you end up with an overall precedence of
      //    ii) A, C, D, C, E,
      //In practice this should be fine, since the applyPrecedence() looks for the
      //first matching entry, so the second (duplicate) occurrence of C is ignored.
      PrecedenceDefEntry[] entries = precedenceDef.getEntries();
      int start = 0, end = 0;
      int size = overall.size();
      for (int i = 0 ; i < size ; i++)
      {
         PrecedenceDefEntry global = overall.get(i);
         boolean found = false;

         //find current overall precedence entry in the new set of defs
         for (int j = start ; j < entries.length ; j++)
         {
            PrecedenceDefEntry cur = entries[j];

            if (cur.equals(global))
            {
               found = true;
               end = j;
               break;
            }
         }

         //We found it. Now insert everything until this into global and
         //reset the counters
         if (found)
         {
            int insert = i;
            for (int j = start ; j < end ; j++)
            {
               overall.add(insert++, entries[j]);
            }
            end++;
            start = end;
         }
      }

      for (int j = start ; j < entries.length ; j++)
      {
         overall.add(entries[j]);
      }

      return overall;
   }

   public static Interceptor[] applyPrecedence(Interceptor[] interceptors, AspectManager manager)
   {
      if (interceptors.length == 0)
         return interceptors;

      ArrayList<InterceptorEntry> all = new ArrayList<InterceptorEntry>(interceptors.length);
      ArrayList<InterceptorEntry> precedence = new ArrayList<InterceptorEntry>(interceptors.length);
      PrecedenceDefEntry[] precedenceEntries = manager.getSortedPrecedenceDefEntries();
      boolean cflowFound = false;
      //Figure out what interceptors have precedence
      for (int i = 0 ; i < interceptors.length ; i++)
      {
         InterceptorEntry[] interceptorEntries;
         ASTCFlowExpression cflow = null;
         // Break cflow interceptor into separate interceptor units
         if (interceptors[i] instanceof CFlowInterceptor)
         {
            cflowFound = true;
            CFlowInterceptor cflowInterceptor = (CFlowInterceptor) interceptors[i];
            cflow = cflowInterceptor.getExpr();
            interceptorEntries = new InterceptorEntry[cflowInterceptor.getChain().length];
            for (int j = 0; j < interceptorEntries.length; j++)
            {
               interceptorEntries[j] = new InterceptorEntry(cflowInterceptor.getChain()[j], cflow);
               all.add(interceptorEntries[j]);
            }
         }
         else
         {
            interceptorEntries = new InterceptorEntry[]{new InterceptorEntry(interceptors[i])};
            all.add(interceptorEntries[0]);
         }
         for (int k = 0; k < interceptorEntries.length; k++)
         {
            InterceptorEntry interceptorEntry = interceptorEntries[k];
            for (int j = 0 ; j < precedenceEntries.length ; j++)
            {
               if (matches(interceptorEntry, precedenceEntries[j]))
               {
                  //This interceptor is defined in the precedence
                  interceptorEntry.originalOrder = all.size() - interceptorEntries.length + k;
                  interceptorEntry.precedenceOrder = j;
                  precedence.add(interceptorEntry);
                  break;
               }
            }
         }
      }

      //Sort the interceptors having precedence
      Collections.sort(precedence, interceptorComparator);
      Interceptor[] sortedInterceptors = new Interceptor[all.size()];
      ASTCFlowExpression[] sortedCFlows = new ASTCFlowExpression[all.size()];

      //Build up new array of interceptors depending on their precedence
      int prec = 0;
      int allSize = all.size();
      int precedenceSize = precedence.size();

      for (int i = 0 ; i < allSize ; i++)
      {
         InterceptorEntry entry = all.get(i);

         if (entry.precedenceOrder >= 0 && prec < precedenceSize)
         {
            entry = precedence.get(prec++);
         }
         sortedInterceptors[i] = entry.interceptor;
         sortedCFlows[i] = entry.cflow;
      }
      if (cflowFound)
      {
         return new Interceptor[]{new SortedCFlowInterceptor(
               sortedInterceptors, sortedCFlows)};
      }
      return sortedInterceptors;
   }

   public static GeneratedAdvisorInterceptor[] applyPrecedence(GeneratedAdvisorInterceptor[] interceptors, AspectManager manager)
   {
      ArrayList<InterceptorEntry> all = new ArrayList<InterceptorEntry>(interceptors.length);
      ArrayList<InterceptorEntry> precedence = new ArrayList<InterceptorEntry>(interceptors.length);
      PrecedenceDefEntry[] precedenceEntries = manager.getSortedPrecedenceDefEntries();

      //Figure out what interceptors have precedence
      for (int i = 0 ; i < interceptors.length ; i++)
      {
         InterceptorEntry interceptorEntry = new InterceptorEntry(interceptors[i]);
         all.add(interceptorEntry);
         for (int j = 0 ; j < precedenceEntries.length ; j++)
         {
            if (matches(interceptorEntry, precedenceEntries[j]))
            {
               //This interceptor is defined in the precedence
               interceptorEntry.originalOrder = i;
               interceptorEntry.precedenceOrder = j;
               precedence.add(interceptorEntry);
               break;
            }
         }
      }

      //Sort the interceptors having precedence
      Collections.sort(precedence, interceptorComparator);
      GeneratedAdvisorInterceptor[] sortedInterceptors = new GeneratedAdvisorInterceptor[interceptors.length];

      //Build up new array of interceptors depending on their precedence
      int prec = 0;
      int allSize = all.size();
      int precedenceSize = precedence.size();

      for (int i = 0 ; i < allSize ; i++)
      {
         InterceptorEntry entry = all.get(i);

         if (entry.precedenceOrder >= 0 && prec < precedenceSize)
         {
            entry = precedence.get(prec++);
         }
         sortedInterceptors[i] = entry.factoryWrapper;
      }

      return sortedInterceptors;
   }

/* public static void main(String[] args)
   {
      System.out.println("Hello");
      AspectManager manager = new AspectManager();
      PrecedenceDef def = new PrecedenceDef("3",new PrecedenceDefEntry[]{
            new PrecedenceDefEntry("A", null),
         	new PrecedenceDefEntry("D", null)});
      manager.addPrecedence(def);
      outputOverAll(manager);

      def = new PrecedenceDef("4",new PrecedenceDefEntry[]{
            new PrecedenceDefEntry("C", null),
            new PrecedenceDefEntry("E", null)});
      manager.addPrecedence(def);
      outputOverAll(manager);

      def = new PrecedenceDef("5",new PrecedenceDefEntry[]{
            new PrecedenceDefEntry("C", null),
            new PrecedenceDefEntry("D", null)});
      manager.addPrecedence(def);
      outputOverAll(manager);
      
      def = new PrecedenceDef("6",new PrecedenceDefEntry[]{
            new PrecedenceDefEntry("E", null),
            new PrecedenceDefEntry("C", null)});
      manager.addPrecedence(def);
      outputOverAll(manager);

   }

   private static void outputOverAll(AspectManager manager)
   {
      PrecedenceDefEntry[] entries = manager.getSortedPrecedenceDefEntries();
      for (int i = 0 ; i < entries.length ; i++)
      {
         System.out.println("\t" + entries[i]);
      }
      System.out.println("==================================");
   }
*/
}

class PrecedenceGraph
{
   private Map<PrecedenceDefEntry, Node> nodes;
   
   public PrecedenceGraph(Collection<PrecedenceDef> precedenceDefs)
   {
      nodes = new HashMap<PrecedenceDefEntry, Node>();
      for(PrecedenceDef precedence: precedenceDefs)
      {
         PrecedenceDefEntry[] entries = precedence.getEntries();
         if (entries.length < 2)
         {
            continue;
         }
         Node node1 = createNode(entries[0]);
         for (int i = 1; i < entries.length; i++)
         {
            Node node2 = createNode(entries[i]);
            node1.addNextNode(node2);
            node1 = node2;
         }
      }
   }
   
   private Node createNode(PrecedenceDefEntry entry)
   {
      Node node = nodes.get(entry);
      if (node == null)
      {
         node = new Node(entry);
         nodes.put(entry, node);
      }
      return node;
   }
   
   public PrecedenceDefEntry[] getSortedPrecedence()
   {
      PrecedenceDefEntry[] overallPrecedence = new PrecedenceDefEntry[nodes.size()];
      int i = 0;
      for (Node node: nodes.values())
      {
         // work only with the first nodes of precedence chains
         if (node.hasPreviousNode() || node.isVisited())
         {
            continue;
         }
         i = node.addSortedPrecedence(overallPrecedence, i);
         if (i == overallPrecedence.length)
         {
            break;
         }
      }
      return overallPrecedence;
   }
   
   private static class Node
   {
      private enum SearchStatus {NOT_VISITED, VISITING, VISITED, CLEARED};
      private PrecedenceDefEntry precedenceEntry;
      private SearchStatus searchStatus;
      
      private Collection<Node> next;
      private Collection<Node> previous;
      
      public Node(PrecedenceDefEntry precedenceEntry)
      {
         this.precedenceEntry = precedenceEntry;
         this.searchStatus = SearchStatus.NOT_VISITED;
         this.next = new ArrayList<Node>();
         this.previous = new ArrayList<Node>();
      }
      

      public boolean hasPreviousNode()
      {
         return !this.previous.isEmpty();
      }
      
      public boolean isVisited()
      {
         return searchStatus != SearchStatus.NOT_VISITED;
      }
      
      public void addNextNode(Node node)
      {
         if (!next.contains(node))
         {
            for (Node previousNode: previous)
            {
               previousNode.removeEdge(node);
            }
            next.add(node);
            node.previous.add(this);
         }
      }
      
      public int addSortedPrecedence(PrecedenceDefEntry[] sortedPrecedence, int index)
      {
         switch (searchStatus)
         {
            case NOT_VISITED:
               this.searchStatus = SearchStatus.VISITING;
               if (!previous.isEmpty())
               {
                  for (Node node: previous)
                  {
                     if (!node.isVisited())
                     {
                        index = node.addSortedPrecedence(sortedPrecedence, index);
                     }
                  }
               }
               this.searchStatus = SearchStatus.VISITED;
               sortedPrecedence[index++] = precedenceEntry;
               boolean cleared = true;
               for (Node node: next)
               {
                  int newIndex = node.addSortedPrecedence(sortedPrecedence, index);
                  if (newIndex == -1)
                  {
                     cleared = false;
                  }
                  else
                  {
                     index = newIndex;
                  }
               }
               if (cleared)
               {
                  searchStatus = SearchStatus.CLEARED;
               }
               break;
            case VISITING:
               // warn that this method is returning without processing the branch
               index = -1;
               break;
            case VISITED:
               throw new RuntimeException("The specified advice precedence rules contain an invalid cyclic dependency");
            case CLEARED:
               // do nothing, this branch of the graph is already cleared
               break;
         }
         return index;
      }
      
      private void removeEdge(Node node)
      {
         next.remove(node);
         node.previous.remove(this);
         // create an auxiliary collection to avoid concurrent modification
         Collection<Node> toRemove = new ArrayList<Node>();
         for (Node previousNode: previous)
         {
            toRemove.add(previousNode);
         }
         for (Node previousNode:toRemove)
         {
            previousNode.removeEdge(node);
         }
      }
      
      public int hashCode()
      {
         return precedenceEntry.hashCode();
      }
      
      public boolean equals(Node other)
      {
         return precedenceEntry.equals(other);
      }
   }
}
