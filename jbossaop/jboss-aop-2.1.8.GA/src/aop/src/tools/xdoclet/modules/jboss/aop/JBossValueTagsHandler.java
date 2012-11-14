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
import xdoclet.XDocletException;
import xdoclet.XDocletTagSupport;

/**
 * JBossValueTagsHandler provides a simple template variable mechanism.
 * This is essentially the same as the matchValue in XDoclet's
 * AbstractProgramElementTagsHandler, except that in that class the
 * matchValue is oftentimes set to null by template methods so the
 * value can't be relied upon to persist throughout the template.  
 *
 * @author <a href="mailto:andy_godwin@hotmail.com">Andy Godwin</a>
 * @created July 5, 2003
 * @version $Revision: 37406 $
 */
public class JBossValueTagsHandler
       extends XDocletTagSupport
{
   /**
    * The value of the variable.
    */
   protected String value;
   
   /**
    * Sets the value of the variable.
    *
    * @param template The body of the block tag
    * @param attributes The attributes of the template tag
    * @throws XDocletException 
    */
   public void setValue(String template, Properties attributes)
          throws XDocletException
   {
      value = attributes.getProperty("value");
      generate(template);
      value = null;
   }

   /**
    * Returns the value of the variable.
    *
    * @return the value of the variable
    * @throws XDocletException 
    */
   public String value()
          throws XDocletException
   {
      return value;
   }
}

