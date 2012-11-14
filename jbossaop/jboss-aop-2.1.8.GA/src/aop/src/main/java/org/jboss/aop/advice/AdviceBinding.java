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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.pointcut.PointcutExpression;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.aop.pointcut.ast.PointcutExpressionParser;
import org.jboss.aop.util.logging.AOPLogger;

/**
 * Binds a pointcut expression to one ore more advices/interceptors.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77480 $
 * @see AspectManager#addBinding(AdviceBinding)
 * @see AspectManager#removeBinding(String)
 */
public class AdviceBinding
{
   private static final AOPLogger logger = AOPLogger.getLogger(AdviceBinding.class);
   
   private static volatile long counter = 0;

   /**
    * Name that identifies this binding in its {@link Domain domain}.
    */
   protected String name;
   
   /**
    * Identifies when the advices/interceptors contained in this binding should
    * be invoked.
    */
   protected Pointcut pointcut;
   
   /**
    * A control flow restriction (in its AST form). Can be {@code null}.
    */
   protected ASTCFlowExpression cflow;
   
   /**
    * A control flow restriction (in its string form). Can be {@code null}
    */
   protected String cflowString;

   // not list because of redundancy caused by successive calls of ClassAdvisor.rebuildInterceptors
   /**
    * Contains all the client advisors, mapped to a boolean value.
    */
   protected Map<Advisor, Boolean> advisors = new WeakHashMap<Advisor, Boolean>();
   
   /**
    * The factories responsible for creating the bound interceptor instances.
    */
   protected InterceptorFactory[] interceptorFactories = new InterceptorFactory[0];

   
   public AdviceBinding() {}

   /**
    * Constructor to be used internally.
    * 
    * @param name             identifies this definition in its {@link Domain domain}
    * @param p                pointcut expression. Only the joinpoints that satisfy
    *                         this expression will be intercepted by the bound
    *                         interceptors.
    * @param cflow            a control flow condition in the form of an {@code AST}
    *                         parser
    * @param cflowString      a control flow expression
    * @param factories        creates the objects that will perform interception
    *                         on the matched joinpoints
    * @throws ParseException  when {@code cflowString} is not {@code null} and
    *                         contains a syntax error
    */
   public AdviceBinding(String name, Pointcut p, ASTCFlowExpression cflow, String cflowString, InterceptorFactory[] factories) throws ParseException
   {
      this.name = name;
      interceptorFactories = factories;
      this.cflow = cflow;

      pointcut = p;
      this.cflowString = cflowString;
   }

   /**
    * This constructor is used for creation of advice bindings programmatically on
    * dynamic AOP operations.
    * <p>
    * The {@link #name name} of the advice will be generated automatically.
    * <p>
    * Bound interceptors will be invoked only on the joinpoints that are matched
    * by {@code pointcutExpression}, and that satisfy {@code cflow} if it is not
    * {@code null}.
    * 
    * @param pointcutExpression pointcut expression. Only the joinpoints that satisfy
    *                           this expression will be intercepted by the bound
    *                           interceptors. 
    * @param cflow              a control flow expression. Can be {@code null} if no
    *                           such condition is necessary. Notice that using
    *                           control flow conditions requires runtime checks and
    *                           may impact your system performance. Always prefer to
    *                           use pointcut expressions of the form {@code call(...)
    *                           AND within(...)} or {@code call(...) AND
    *                           withincode(...)} instead, whenever applicable.
    * @throws ParseException    when {@code cflow} is not {@code null} and contains a
    *                           syntax error
    */
   public AdviceBinding(String pointcutExpression, String cflow) throws ParseException
   {
      this(Long.toString(System.currentTimeMillis()) + ":" + Long.toString(counter++), pointcutExpression, cflow);
   }

   /**
    * This constructor is used for creation of advice bindings programmatically on
    * dynamic AOP operations. Call this constructor when you want to define the
    * binding's name.
    * <p>
    * Bound interceptors will be invoked only on the joinpoints that are matched
    * by {@code pointcutExpression}, and that satisfy {@code cflow} if it is not
    * {@code null}.
    *
    * @param name               identifies this binding in its {@link Domain domain}.
    * @param pointcutExpression pointcut expression. Only the joinpoints that satisfy
    *                           this expression can be intercepted by the bound
    *                           interceptors. 
    * @param cflow              a control flow expression. Can be {@code null} if no
    *                           such condition is necessary. Notice that using
    *                           control flow conditions requires runtime checks and
    *                           may impact your system performance. Always prefer to
    *                           use pointcut expressions of the form {@code call(...)
    *                           AND within(...)} or {@code call(...) AND
    *                           withincode(...)} instead, whenever applicable.
    * @throws ParseException    when {@code cflow} is not {@code null} and contains a
    *                           syntax error
    */
   public AdviceBinding(String name, String pointcutExpression, String cflow) throws ParseException
   {
      this.name = name;
      setPointcutExpression(pointcutExpression);
      setCFlowExpression(cflow);
      interceptorFactories = new InterceptorFactory[0];
   }

   /**
    * Defines a control-flow restriction to this binding.
    * <br>
    * Bound interceptors will not be invoked when this restriction is not satisfied.
    * <br>
    * Notice that using control flow conditions requires runtime checks and may
    * impact your system performance. Always prefer to use pointcut expressions of
    * the form {@code call(...) AND within(...)} or {@code call(...) AND
    * withincode(...)} instead, whenever applicable.
    * 
    * @param cflow              a control flow expression.
    * @throws ParseException    when {@code cflow} is not {@code null} and contains a
    *                           syntax error
    */
   public void setCFlowExpression(String cflow)
           throws ParseException
   {
      if (cflow != null)
      {
         cflowString = cflow;
         this.cflow = new PointcutExpressionParser(new StringReader(cflowString)).CFlowExpression();
      }
   }

   /**
    * Defines the pointcut expression to be used by this binding.
    * <br>
    * Bound interceptors will be invoked only on the joinpoints that are matched
    * by this expression, and that satisfy the {@code cflow} condition if there is
    * one. 
    * 
    * @param pointcutExpression pointcut expression. Only the joinpoints that satisfy
    *                           this expression can be intercepted by the bound
    *                           interceptors.
    * @throws ParseException    when {@code cflow} is not {@code null} and contains a
    *                           syntax error
    */
   public void setPointcutExpression(String pointcutExpression)
           throws ParseException
   {
      pointcut = new PointcutExpression(Long.toString(System.currentTimeMillis()) + ":" + Long.toString(counter++), pointcutExpression);
   }

   /**
    * Adds an interceptor to the chain.
    * 
    * @param factory creates the interceptor instances that will be invoked during
    *                interception
    */
   public void addInterceptorFactory(InterceptorFactory factory)
   {
      List<InterceptorFactory> list = Arrays.asList(interceptorFactories);
      list = new ArrayList<InterceptorFactory>(list);
      list.add(factory);
      interceptorFactories = list.toArray(new InterceptorFactory[list.size()]);
   }


   /**
    * Adds an interceptor to the chain.  
    *
    * @param clazz the actual class that implements {@link Interceptor}. This class
    *              must provide a default constructor so it can be created.
    *              A {@code GenericInterceptorFactory} will be used to create the
    *              interceptor instances.
    * @see GenericInterceptorFactory
    */
   public void addInterceptor(Class<?> clazz)
   {
      addInterceptorFactory(new GenericInterceptorFactory(clazz));
   }

   /**
    * Returns the name of this binding. This name is unique inside the
    * {@link Domain domain}.
    * 
    * @return name the name that identifies this binding in its {@link Domain domain}
    */
   public String getName()
   {
      return name;
   }

   /**
    * Returns the interceptor factory chain.
    * <p><i>For internal use only.</i>
    * 
    * @return an array containing the interceptor factory chain. This chain will
    *         be used to create an equivalent chain (same order) of interceptor
    *         instances. The generated interceptor chain is the one that will
    *         be used on interception. This chain must not be edited this chain.
    * @see #addInterceptor(Class)
    * @see #addInterceptorFactory(InterceptorFactory)
    */
   public InterceptorFactory[] getInterceptorFactories()
   {
      return interceptorFactories;
   }

   /**
    * Defines the name of this binding. This name must be unique inside the
    * {@link Domain domain}.
    * 
    * @param name the name that identifies this binding in its {@link Domain domain}
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * Returns the pointcut that determines when the bound interceptor chain should
    * be invoked.
    * <p><i>For internal use only.</i>
    * 
    * @return the pointcut object
    */
   public Pointcut getPointcut()
   {
      return pointcut;
   }

   /**
    * Returns the cflow condition in the form an {@code AST} parser.
    * <p><i>For internal use only.</i>
    * 
    * @return the cflow condition that must be satisfied by joinpoints in order for
    *         the bound interceptors to be invoked
    */
   public ASTCFlowExpression getCFlow()
   {
      return cflow;
   }

   /**
    * Returns the cflow condition.
    * 
    * @return the cflow condition expression that must be satisfied by joinpoints in
    *         order for the bound interceptors to be invoked
    */
   public String getCFlowString()
   {
      return cflowString;
   }

   
   /**
    * Adds an advisor as a client of this binding.
    * <p>
    * <i>For internal use only.</i>
    * 
    * @param advisor manages one or more joinpoints that are matched by
    *                the bound {@link #pointcut}.
    */
   public void addAdvisor(Advisor advisor)
   {
      if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("added advisor: " + advisor.getName() + " from binding: " + name);
      // Don't hold a direct reference to an advisor because of undeploy and redeploy.  Use WeakRefrences because
      // we may be having in the future an Advisor per instance.
      synchronized (advisors)
      {
         advisors.put(advisor, Boolean.TRUE);
      }
      
   }

   /**
    * Indicates whether there are any advisors using this binding for interception.
    * 
    * @return {@code true} if and only if there are one or more advisors that use
    *         this binding for interception
    */
   public boolean hasAdvisors()
   {
      return advisors.size() > 0;
   }

   /**
    * Returns the list of the client advisors.
    * <p>
    * <i>For internal use only.</i>
    * 
    * @return the list of the advisors that use this binding for interception
    */
   public ArrayList<Advisor> getAdvisors()
   {
      ArrayList<Advisor> list = new ArrayList<Advisor>(advisors.size());
      synchronized (advisors)
      {
         list.addAll(advisors.keySet());
      }
      return list;
   }

   /**
    * Clears the list of the client advisors.
    * <p>
    * <i>For internal use only.</i>
    */
   public void clearAdvisors()
   {
      synchronized (advisors)
      {
         for (Advisor advisor : advisors.keySet())
         {
            if (advisor.getManager().isAdvisorRegistered(advisor))
            {
               advisor.removeAdviceBinding(this);
            }
         }
         advisors.clear();
      }
   }

   /**
    * Compares this binding with {@code obj} for equality.
    * 
    * @param obj the object to be compared with this binding for equality
    * @return {@code true} if and only if {@code obj} is a binding with a name that
    *         is equal to the name of this binding.
    */
   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (!(obj instanceof AdviceBinding)) return false;
      return ((AdviceBinding) obj).getName().equals(name);
   }

   
   public int hashCode()
   {
      return name.hashCode();
   }
}
