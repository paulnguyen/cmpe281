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
package org.jboss.aop.util;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.bytecode.Descriptor;

/**
 * Compares CtConstructors. Used to ensure that the constructor IDs line up between the
 * instrumentor and advisor.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71276 $
 */
public class CtConstructorComparator implements java.util.Comparator<CtConstructor> {

    private CtConstructorComparator() {}

    public static final java.util.Comparator<CtConstructor> INSTANCE = new CtConstructorComparator();
        
    public int compare(CtConstructor m1, CtConstructor m2) {
        try {
            CtClass[] args1 = m1.getParameterTypes();
            CtClass[] args2 = m2.getParameterTypes();
            if (args1.length < args2.length) return -1;
            if (args1.length > args2.length) return 1;
            for (int i = 0; i < args1.length; i++) {
                String name1 =
                    Descriptor.toJavaName(Descriptor.toJvmName(args1[i]));
                String name2 =
                    Descriptor.toJavaName(Descriptor.toJvmName(args2[i]));
                int result = name1.compareTo(name2);
                if (result != 0) return result;
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        // unreachable.
        throw new Error();
    }
}
