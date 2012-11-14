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

package org.jboss.aspects.asynchronous.aspects.jboss;

import org.jboss.aop.annotation.AnnotationElement;
import org.jboss.aop.joinpoint.MethodInvocation;
import org.jboss.aspects.asynchronous.aspects.AsynchronousFacade;
import org.jboss.aspects.asynchronous.aspects.AsynchronousFacadeImpl;
import org.jboss.aspects.asynchronous.aspects.Utils;
import org.jboss.aspects.asynchronous.concurrent.ThreadManagerFactory;

/**
 * @author <a href="mailto:chussenet@yahoo.com">{Claude Hussenet Independent Consultant}</a>.
 * @version <tt>$Revision: 71280 $</tt>
 */
public class AsynchronousAspect
{
   private AsynchronousInvokeTask asynchronousInvokeTask =
   new AsynchronousInvokeTask();

   public AsynchronousAspect() {}

   public Object execute(MethodInvocation invocation) throws Throwable
   {
      AsynchronousFacade asynchronousResult = null;
      if (invocation.getTargetObject() != null)
         asynchronousResult = (AsynchronousFacade) invocation.getTargetObject();
      else
      {
         Object[] tObject = invocation.getArguments();
         for (int i = 0; i < tObject.length; i++)
         {
            Object object = tObject[i];
            if (object instanceof AsynchronousFacade)
            {
               asynchronousResult = (AsynchronousFacade) object;
               break;
            }
         }
         if (asynchronousResult == null)
            asynchronousResult = new AsynchronousFacadeImpl();
      }
      Asynchronous asynchronous = (Asynchronous) AnnotationElement.getAnyAnnotation(invocation.getMethod(), Asynchronous.class);
      if (asynchronous!=null)
      {
      	if (asynchronousResult.getTimeout() == 0)
         asynchronousResult.setTimeout(asynchronous.timeout());

        if ((asynchronousResult.getId()!=null) && (asynchronousResult.getId().equals("None"))
       	                                       &&
       	              (asynchronous.id()!=null)&&(asynchronous.id().length()!=0) )
      	 asynchronousResult.setId(asynchronous.id());
	  }
      
      asynchronousResult.setAsynchronousTask(ThreadManagerFactory.getThreadManager().process(ThreadManagerFactory.createNewThreadManagerRequest(asynchronousResult.getId(),
      new InvokeTaskInputParameters((MethodInvocation) invocation.copy()),
      asynchronousInvokeTask,
      asynchronousResult.getTimeout())));
      Class<?> returnType = invocation.getActualMethod().getReturnType();
      return Utils.returnInitObject(returnType);
   }
}
