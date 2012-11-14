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
import org.jboss.aop.Aspect;
import org.jboss.aop.Mixin;
import org.jboss.aop.Introduction;

/**
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
@Aspect
public class MyAspect
{
   @Introduction (target=POJO.class, interfaces={java.io.Serializable.class})
   public static Object noInterfacesPOJOIntro;

   @Mixin (target=POJO2.class, interfaces={java.io.Externalizable.class})
   public static POJO2ExternalizableMixin createExternalizableMixin(POJO2 pojo) {
       return new POJO2ExternalizableMixin(pojo);
   }

   @Introduction (typeExpression="class(POJO3) OR class(POJO4)", interfaces={java.io.Serializable.class})
   public static Object withTypeExpression;

}
