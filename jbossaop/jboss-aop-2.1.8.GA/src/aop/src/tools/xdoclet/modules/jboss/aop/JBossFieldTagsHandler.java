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
package xdoclet.modules.jboss.aop;

import java.util.Properties;
import xdoclet.tagshandler.FieldTagsHandler;
import xdoclet.XDocletException;

/**
 * JBossFieldTagsHandler simply adds a couple of template methods missing
 * from the existing XDoclet FieldTagsHandler.
 *
 * @author <a href="mailto:andy_godwin@hotmail.com">Andy Godwin</a>
 * @created May 28, 2003
 * @version $Revision: 37406 $
 */
public class JBossFieldTagsHandler
       extends FieldTagsHandler
{
   /**
    * An XDoclet content template tag handler that executes the enclosing
    * template if the specified tag attribute value for the current field is
    * NOT equal to the supplied value.
    */
   public void ifFieldTagValueNotEquals(String template, Properties attributes)
          throws XDocletException
   {
      if (!isTagValueEqual(attributes, FOR_FIELD))
      {
         generate(template);
      }
   }

   /**
    * An XDoclet content template tag handler that executes the enclosing
    * template if the specified tag attribute value for the current field does
    * NOT exist.
    */
   public void ifDoesntHaveFieldTag(String template, Properties attributes)
          throws XDocletException
   {
      if (!hasTag(attributes, FOR_FIELD))
      {
         generate(template);
      }
   }
}
