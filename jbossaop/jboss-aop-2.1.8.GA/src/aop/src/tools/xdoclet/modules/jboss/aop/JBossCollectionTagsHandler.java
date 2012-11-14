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

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Properties;
import java.util.Iterator;
import xdoclet.util.Translator;
import xdoclet.XDocletException;
import xdoclet.XDocletTagSupport;
import xdoclet.XDocletMessages;

/**
 * JBossCollectionTagsHandler adds Set and Map support to the XDoclet template
 * language.  This class is largely copied from the XDoclet source for the
 * xdoclet.modules.util.CollectionTagsHandler class, with some additional
 * iteration functionality, and is included here because the XDoclet team 
 * intend to remove the Collection based functionality from the template
 * language.
 *
 * @author Marcus Brito (pazu@animegaiden.com.br)
 * @author <a href="mailto:andy_godwin@hotmail.com">Andy Godwin</a>
 * @created May 28, 2003
 * @version $Revision: 37406 $
 */
public class JBossCollectionTagsHandler
       extends XDocletTagSupport
{
   protected Map     collections = new HashMap();

   /**
    * Obtains one value contained in the collection. This tag only apply to
    * map valued collections, and an XDocletException will be thrown if the
    * specified collection is not a map.
    *
    * @param attributes The attributes of the template tag
    * @return The requested value or null if no such value exists.
    * @throws XDocletException if something goes wrong
    */
   public String get(Properties attributes)
          throws XDocletException
   {
      String name = attributes.getProperty("name");
      String key = attributes.getProperty("key");

      // Makes sure name is specified
      if (name == null || name.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"name"}));
      }

      // Makes key is specified
      if (key == null || key.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"key"}));
      }

      // Makes sure the name exists
      if (!collections.containsKey(name))
      {
         throw new XDocletException("Collection '" + name + "' is not defined");
      }

      // Make sure the name is a map
      if (!(collections.get(name) instanceof Map))
      {
         throw new XDocletException("Collection '" + name + "' is not a map");
      }

      return (String)((Map)collections.get(name)).get(key);
   }


   /**
    * Creates a new utility collection that will store template data. If a
    * collection with the specified name already exists, an XDocletException
    * will be thrown.
    *
    * @param attributes         The attributes of the template tag
    * @throws XDocletException  if something goes wrong
    */
   public void create(Properties attributes)
          throws XDocletException
   {
      String name = attributes.getProperty("name");
      String type = attributes.getProperty("type");

      // Make sure name is specified
      if (name == null || name.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"name"}));
      }

      // Prevent collection overwriting
      if (collections.containsKey(name))
      {
         throw new XDocletException("Collection '" + name + "' already exists");
      }

      // Creates a new map or set, as requested
      if ("map".equals(type))
         collections.put(name, new HashMap());
      else
         collections.put(name, new HashSet());
   }

   /**
    * Puts a new element into the specified collection. If the collection is
    * a set, only the 'name' and 'value' attributes should be specified. If
    * the collection is a map, the 'key' value should also be specified. If
    * the 'key' is specified and the collection is a set, or if 'key' is not
    * specified and the collection is a map, an XDocletException will be
    * thrown.
    *
    * @param attributes         The attributes of the template tag
    * @throws XDocletException  if something goes wrong
    */
   public void put(Properties attributes)
          throws XDocletException
   {
      String name = attributes.getProperty("name");
      String key = attributes.getProperty("key");
      String value = attributes.getProperty("value");

      // Makes sure name is specified
      if (name == null || name.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"name"}));
      }

      // Makes sure value is specified
      if (value == null || value.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"value"}));
      }

      // Make sure the collection exists
      if (!collections.containsKey(name))
      {
         throw new XDocletException("Collection '" + name + "' is not defined");
      }

      // Puts the value into the collection, as requested
      if (collections.get(name) instanceof Map)
      {
         if (key == null || key.length() == 0)
         {
            throw new XDocletException(Translator.getString(XDocletMessages.class,
               XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"key"}));
         }

         ((Map) collections.get(name)).put(key, value);
      }
      else
      {
         // Throws an exception if key is specified
         if (key != null)
         {
            throw new XDocletException("Collection '" + name + "' is not a map");
         }

         ((Set) collections.get(name)).add(value);
      }
   }


   /**
    * Removes an element from the specified collection. One of 'key' or 'value'
    * attributes should be specified, depending if the collection is a map or
    * a set.
    *
    * @param attributes         The attributes of the template tag
    * @throws XDocletException  if something goes wrong
    */
   public void remove(Properties attributes)
          throws XDocletException
   {
      String name = attributes.getProperty("name");
      String key = attributes.getProperty("key");
      String value = attributes.getProperty("value");

      // Makes sure name is specified
      if (name == null || name.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"name"}));
      }

      // Make sure name exists
      if (!collections.containsKey(name))
      {
         throw new XDocletException("Collection '" + name + "' is not defined");
      }

      // Removes the key from the name, as requested
      if (collections.get(name) instanceof Map)
      {
         // Makes sure key is specified
         if (key == null || key.length() == 0)
         {
            throw new XDocletException(Translator.getString(XDocletMessages.class,
               XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"key"}));
         }

         ((Map) collections.get(name)).remove(key);
      }
      else
      {
         // Makes sure value is specified
         if (value == null || value.length() == 0)
         {
            throw new XDocletException(Translator.getString(XDocletMessages.class,
               XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"value"}));
         }

         ((Set) collections.get(name)).remove(key);
      }
   }

   /**
    * Generates the contained template code if the specified collection
    * contains the key or value passed as attributes. If the collection is
    * a set, only the 'value' attribute should be specified. If the
    * collection is a map, the 'key' attribute should be specifed and if
    * the 'value' attribute is also specified, an additional check for
    * equality will be made.
    *
    * @param template              The block tag contents
    * @param attributes            The attributes of the tag template
    * @exception XDocletException
    */
   public void ifContains(String template, Properties attributes)
          throws XDocletException
   {
      if (contains(attributes))
         generate(template);
   }

   /**
    * Generates the contained template code if the specified collection doesn't
    * contain the key or value passed as attributes. If the collection is a
    * set, only the 'value' attribute should be specified. If the collection
    * is a map, the 'key' attribute should be specifed and if the 'value'
    * attribute is also specified, an additional check for equality will be
    * made.
    *
    * @param template              The block tag contents
    * @param attributes            The attributes of the tag template
    * @throws XDocletException
    */
   public void ifDoesntContain(String template, Properties attributes)
          throws XDocletException
   {
      if (!contains(attributes))
         generate(template);
   }

   /**
    * Destroys the specified collection. The collection must exist or an
    * exception will be thrown.
    *
    * @param attributes         The attributes of the tag template
    * @throws XDocletException  if something goes wrong
    */
   public void destroy(Properties attributes)
          throws XDocletException
   {
      String name = attributes.getProperty("name");

      // Makes sure name is specified
      if (name == null || name.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"name"}));
      }

      // Makes sure name exists
      if (!collections.containsKey(name))
      {
         throw new XDocletException("Collection '" + name + "' is not defined");
      }

      collections.remove(name);
   }


   /**
    * Checks if the specified collection contains a certain key/value. If the
    * collection is a set, only the 'value' attribute should be specified. If
    * the collection is a map, the 'key' value should be specified and if the
    * 'value' attribute is also specified, an additional check for equality
    * will be made.
    *
    * @param attributes            The attributes of the template tag
    * @return
    * @throws XDocletException
    */
   private boolean contains(Properties attributes)
           throws XDocletException
   {
      String name = attributes.getProperty("name");
      String key = attributes.getProperty("key");
      String value = attributes.getProperty("value");
        
      // Makes sure name is specified
      if (name == null || name.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"name"}));
      }

      // Make sure name exists
      if (!collections.containsKey(name))
      {
         throw new XDocletException("Collection '" + name + "' is not defined");
      }

      // Checks if the collection contains the key/value specified
      if (collections.get(name) instanceof Map)
      {
         // Makes sure key is specified
         if (key == null || key.length() == 0)
         {
            throw new XDocletException(Translator.getString(XDocletMessages.class,
               XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"key"}));
         }

         // If value is specified, checks for equality
         if (value == null)
            return ((Map) collections.get(name)).containsKey(key);
         else
            return value.equals(((Map) collections.get(name)).get(key));
      }
      else
      {
         // Makes sure value is specified
         if (value == null || value.length() == 0)
         {
            throw new XDocletException(Translator.getString(XDocletMessages.class,
               XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"value"}));
         }

         return ((Set) collections.get(name)).contains(value);
      }
   }

   /**
    * Iterates over the contents of the specified collection and generates
    * the enclosed template for each element in the collection.
    *
    * @param template              The block tag contents
    * @param attributes            The attributes of the template tag
    * @throws XDocletException
    */
   public void forAllCollectionElements(String template, Properties attributes)
          throws XDocletException
   {
      String name = attributes.getProperty("name");

      // Makes sure name is specified
      if (name == null || name.length() == 0)
      {
         throw new XDocletException(Translator.getString(XDocletMessages.class,
            XDocletMessages.PARAMETER_MISSING_OR_EMPTY, new String[]{"name"}));
      }

      Object o = collections.get(name);
      // Make sure name exists
      if (o == null)
      {
         throw new XDocletException("Collection '" + name + "' is not defined");
      }
        
      if (o instanceof Map)
      {
         Iterator iter = ((Map)o).entrySet().iterator();
         while (iter.hasNext())
         {
            Map.Entry entry = (Map.Entry)iter.next();
            curKey = (String)entry.getKey();
            curValue = (String)entry.getValue();
            generate(template);
         }
      }
      else
      {
         curName = name;
         Iterator iter = ((Set)o).iterator();
         while (iter.hasNext())
         {
            curValue = (String)iter.next();
            generate(template);
         }
      }

      curValue = null;          
      curKey = null;          
   }
    
   public String currentValue()
   {
      return curValue;
   }
    
   public String currentKey()
          throws XDocletException
   {
      // if we're iterating over a Set, the key is invalid, so barf
      if (curKey == null)
      {
         throw new XDocletException("Collection '" + curName + "' is not a map");
      }
      return curKey;
   }
    
   private String curValue;
   private String curKey;
   private String curName;
}


