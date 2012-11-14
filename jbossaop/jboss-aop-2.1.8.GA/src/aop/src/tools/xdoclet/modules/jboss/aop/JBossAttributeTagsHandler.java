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

import java.util.Set;
import java.util.HashSet;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.Collection;
import xjavadoc.XTag;
import xdoclet.XDocletException;
import xdoclet.XDocletTagSupport;

/**
 * JBossAttributeTagsHandler provides a means of iterating over arbitrary tag
 * attributes for cases where the names of tag attributes are not known.
 * <p>
 * It iterates over all the attributes of the current tag (class, method or
 * field), except those attributes listed in a specified comma separated list,
 * and, for each, provides access to the attribute name and it's value.
 * <p>
 * This is useful, for example, when handling classes that implement the
 * XmlLoadable interface, where the xml content of a deployment descriptor
 * element is unknown. 
 * <p>
 * Note, the template tag corresponding to this TagHandler MUST be wrapped
 * inside the appropriate tag enumeration tag, for example:
 * <p>
 * <pre>
 *   &lt;XDtClass:forAllClassTags tagName="..."&gt;
 *     &lt;XDtJBossAttribute:forAllClassTagAttributes ignore="..."&gt;
 *       &lt;!-- do something with the attribute name and/or value --&gt; 
 *     &lt;/XDtJBossAttribute:forAllClassTagAttributes&gt;
 *   &lt;/XDtClass:forAllClassTags&gt;
 * </pre>
 *
 * @author <a href="mailto:andy_godwin@hotmail.com">Andy Godwin</a>
 * @created May 28, 2003
 * @version $Revision: 37406 $
 */
public class JBossAttributeTagsHandler
       extends XDocletTagSupport
{
   /**
    * Iterates over all the attributes of the current class tag, except
    * those specified in a comma separated ignore list, and executes the
    * enclosing template for each attribute.
    */
   public void forAllClassTagAttributes(String template, Properties attributes)
          throws XDocletException
   {
      forAllTagAttributes(template, attributes, getCurrentClassTag());
   }
  
   /**
    * Iterates over all the attributes of the current method tag, except
    * those specified in a comma separated ignore list, and executes the
    * enclosing template for each attribute.
    */
   public void forAllMethodTagAttributes(String template, Properties attributes)
          throws XDocletException
   {
      forAllTagAttributes(template, attributes, getCurrentMethodTag());
   }
  
   /**
    * Iterates over all the attributes of the current field tag, except
    * those specified in a comma separated ignore list, and executes the
    * enclosing template for each attribute.
    */
   public void forAllFieldTagAttributes(String template, Properties attributes)
          throws XDocletException
   {
      forAllTagAttributes(template, attributes, getCurrentFieldTag());
   }
  
   /**
    * Returns the name of the current attribute.
    */
   public String attributeName()
   {
      return name;
   }

   /**
    * Returns the value of the current attribute.
    */
   public String attributeValue()
   {
      return value;
   }

   /**
    * Iterates over all the attributes of the supplied XTag object, and for
    * each attribute not included in the tag attribute "ignore", generates the
    * enclosed template.
    */
   private void forAllTagAttributes(String template,
                                    Properties attributes,
                                    XTag tag)
           throws XDocletException
   {
      if (tag != null)
      {
         Set ignoreSet = new HashSet();

         String ignoreStr = attributes.getProperty("ignore");
         if (ignoreStr != null)
         {
            StringTokenizer st = new StringTokenizer(ignoreStr, ",");
            while (st.hasMoreTokens())
               ignoreSet.add(st.nextToken());
         }
      
         Collection c = tag.getAttributeNames();
         Iterator iter = c.iterator();
         while (iter.hasNext())
         {
            name = (String)iter.next();
            if (ignoreSet.contains(name))
               continue;
            value = tag.getAttributeValue(name);
            generate(template);
         }
      }
   }
  
   private String name;
   private String value;
}

