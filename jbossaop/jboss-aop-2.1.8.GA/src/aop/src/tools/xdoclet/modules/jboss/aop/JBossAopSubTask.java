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

import xdoclet.XmlSubTask;

/**
 * JBossAopSubTask is an xml-based XDoclet sub task for generation of
 * jboss-aop.xml AOP deployment descriptors
 *
 * @ant.element display-name="JBossAOP" name="jbossaop"
 *              parent="xdoclet.modules.jboss.aop.JBossAopTask"
 *
 * @author <a href="mailto:andy_godwin@hotmail.com">Andy Godwin</a>
 * @created May 28, 2003
 * @version $Revision: 37406 $
 */
public class JBossAopSubTask
       extends XmlSubTask
{
   public final static String SUBTASK_NAME = "jbossaop";

   private final static String JBOSSAOP_DTD =
      "http://www.jboss.org/j2ee/dtd/jbossaop.dtd";
   private static String DEFAULT_TEMPLATE_FILE =
      "xdoclet/modules/jboss/aop/resources/jboss-aop.xdt";
   private static String GENERATED_FILE_NAME = "jboss-aop.xml";
   private static String CLASS_TAG = "jboss-aop";

   public JBossAopSubTask()
   {
      setTemplateURL(
         getClass().getClassLoader().getResource(DEFAULT_TEMPLATE_FILE));
      setDestinationFile(GENERATED_FILE_NAME);
      setHavingClassTag(CLASS_TAG);
      setValidateXML(false);
      setSubTaskName(SUBTASK_NAME);
   }
}


