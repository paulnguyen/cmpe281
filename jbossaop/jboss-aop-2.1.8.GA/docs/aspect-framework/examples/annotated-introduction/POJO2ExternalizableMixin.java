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
import java.io.*;
/**
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 37406 $
 */
public class POJO2ExternalizableMixin implements java.io.Externalizable
{
   POJO2 pojo;

   public POJO2ExternalizableMixin(POJO2 pojo) 
   {
      this.pojo = pojo;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
   {
      pojo.stuff2 = in.readUTF();  
   }
   public void writeExternal(ObjectOutput out) throws IOException
   {
      out.writeUTF(pojo.stuff2);
   }
}



