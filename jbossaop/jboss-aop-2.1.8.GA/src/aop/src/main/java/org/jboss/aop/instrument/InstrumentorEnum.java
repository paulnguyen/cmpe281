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
package org.jboss.aop.instrument;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 45806 $
 */
public class InstrumentorEnum
{
   public static final InstrumentorEnum CLASSIC = new InstrumentorEnum(1);
   public static final InstrumentorEnum GENERATED_ADVISOR = new InstrumentorEnum(2);
   public static final InstrumentorEnum OTHER_INSTRUMENTOR = new InstrumentorEnum(1000); //The jrockit aop instrumentor is in another jar which we should not depend on

   int instrumentor;

   public InstrumentorEnum(int i)
   {
      instrumentor = i;
   }
}
