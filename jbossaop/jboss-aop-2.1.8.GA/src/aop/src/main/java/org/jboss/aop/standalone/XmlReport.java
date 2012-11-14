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
package org.jboss.aop.standalone;

import gnu.trove.TLongObjectHashMap;

import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.CallerConstructorInfo;
import org.jboss.aop.CallerMethodInfo;
import org.jboss.aop.ClassAdvisor;
import org.jboss.aop.MethodInfo;
import org.jboss.aop.advice.AbstractAdvice;
import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.CFlowInterceptor;
import org.jboss.aop.advice.Interceptor;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.metadata.ConstructorMetaData;
import org.jboss.aop.metadata.FieldMetaData;
import org.jboss.aop.metadata.MetaDataResolver;
import org.jboss.aop.metadata.MethodMetaData;
import org.jboss.aop.metadata.SimpleMetaData;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 71279 $
 */
public class XmlReport
{
   public static String toXml()
   {
      Package root = Package.aopClassMap();
      StringWriter writer = new StringWriter();
      PrintWriter pw = new PrintWriter(writer);
      pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
      pw.println("<aop-report>");
      outputPackage(1, pw, root);
      outputUnboundBindings(1, pw);
      pw.println("</aop-report>");
      pw.flush();
      return writer.toString();
   }

   private static String getIndent(int indent)
   {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < indent; i++)
      {
         sb.append(" ");
      }

      return sb.toString();
   }

   public static void indenter(Writer w, int indent)
   {
      try
      {
         w.write(getIndent(indent));
      }
      catch (IOException e)
      {
         throw new RuntimeException("IOException in indent", e);
      }
   }

   protected static String simpleType(Class<?> type)
   {
      Class<?> ret = type;
      if (ret.isArray())
      {
         Class<?> arr = ret;
         String array = "";
         while (arr.isArray())
         {
            array += "[]";
            arr = arr.getComponentType();
         }
         return arr.getName();
      }
      return ret.getName();
   }

   public static void outputPackage(int indent, PrintWriter pw, Package root)
   {
      Iterator<Map.Entry<String, Package>> it1 = root.packages.entrySet().iterator();
      while (it1.hasNext())
      {
         Map.Entry<String, Package> entry = it1.next();
         String pkgName = entry.getKey();
         indenter(pw, indent);
         pw.println("<package name=\"" + pkgName + "\">");
         Package p = entry.getValue();
         outputPackage(indent + 1, pw, p);
         indenter(pw, indent);
         pw.println("</package>");
      }
      Iterator<Map.Entry<String, Advisor>> it2 = root.advisors.entrySet().iterator();
      while (it2.hasNext())
      {
         Map.Entry<String, Advisor> entry = it2.next();
         String classname = entry.getKey();
         indenter(pw, indent);
         pw.println("<class name=\"" + classname + "\">");
         ClassAdvisor advisor = (ClassAdvisor) entry.getValue();
         outputAdvisor(indent + 1, pw, advisor, classname);
         indenter(pw, indent);
         pw.println("</class>");
      }
   }

   public static void outputAdvisor(int indent, PrintWriter pw, ClassAdvisor advisor, String baseName)
   {
      ArrayList<InterfaceIntroduction> introductions = advisor.getInterfaceIntroductions();
      if (introductions != null && introductions.size() > 0)
      {
         indenter(pw, indent);
         pw.println("<introductions>");
         indent++;
         for (int i = 0; i < introductions.size(); i++)
         {
            InterfaceIntroduction pointcut = introductions.get(i);
            indenter(pw, indent);
            pw.println("<introduction classExpr=\"" + pointcut.getClassExpr() + "\">");
            indent++;
            String[] intfs = pointcut.getInterfaces();
            ArrayList<InterfaceIntroduction.Mixin> mixins = pointcut.getMixins();

            if (intfs != null && intfs.length > 0)
            {
               //Show interfaces
               for (int j = 0; j < intfs.length; j++)
               {
                  indenter(pw, indent);
                  pw.println("<interface class=\"" + intfs[j] + "\"/>");
               }
            }
            else if (mixins != null && mixins.size() > 0)
            {
               //Show mixins
               for (int j = 0; j < mixins.size(); j++)
               {
                  InterfaceIntroduction.Mixin mixin = mixins.get(j);
                  String[] mixifs = mixin.getInterfaces();

                  for (int k = 0; k < mixifs.length; k++)
                  {
                     indenter(pw, indent);
                     pw.println("<interface class=\"" + mixifs[j] + "\"/>");
                  }
                  indenter(pw, indent);
                  pw.println("<mixin class=\"" + mixin.getClassName() + "\"/>");

               }
            }

            indent--;
            indenter(pw, indent);
            pw.println("</introduction>");
         }
         indent--;
         indenter(pw, indent);
         pw.println("</introductions>");
      }
      indenter(pw, indent);
      pw.println("<constructors>");
      indent++;
      for (int i = 0; i < advisor.getConstructors().length; i++)
      {
         Constructor<?> con = advisor.getConstructors()[i];
         Interceptor[] chain = advisor.getConstructorInfos()[i].getInterceptors();
         HashMap<String, TLongObjectHashMap> methodCallers = advisor.getMethodCalledByConInterceptors()[i];
         HashMap<String, TLongObjectHashMap> conCallers = advisor.getConCalledByConInterceptors()[i];

         if ((chain != null && chain.length > 0) || methodCallers != null || conCallers != null)
         {
            indenter(pw, indent);
            pw.println("<constructor signature=\"" + con.toString() + "\">");
            if (chain != null && chain.length > 0)
            {
               outputChain(indent + 1, pw, chain);
            }
            if (methodCallers != null)
            {
               outputMethodCallers(indent + 1, pw, methodCallers);
            }
            if (conCallers != null)
            {
               outputConCallers(indent + 1, pw, conCallers);
            }
            indenter(pw, indent);
            pw.println("</constructor>");
         }

      }
      indent--;
      indenter(pw, indent);
      pw.println("</constructors>");
      indenter(pw, indent);
      pw.println("<fields-read>");
      indent++;
      for (int i = 0; i < advisor.getAdvisedFields().length; i++)
      {
         Field f = advisor.getAdvisedFields()[i];
         Interceptor[] chain = advisor.getFieldReadInfos()[i].getInterceptors();
         if (chain != null && chain.length > 0)
         {
            indenter(pw, indent);
            pw.println("<field name=\"" + f.getName() + "\">");
            outputChain(indent + 1, pw, chain);
            indenter(pw, indent);
            pw.println("</field>");
         }

      }
      indent--;
      indenter(pw, indent);
      pw.println("</fields-read>");
      indenter(pw, indent);
      pw.println("<fields-write>");
      indent++;
      for (int i = 0; i < advisor.getAdvisedFields().length; i++)
      {
         Field f = advisor.getAdvisedFields()[i];
         Interceptor[] chain = advisor.getFieldReadInfos()[i].getInterceptors();
         if (chain != null && chain.length > 0)
         {
            indenter(pw, indent);
            pw.println("<field name=\"" + f.getName() + "\">");
            outputChain(indent + 1, pw, chain);
            indenter(pw, indent);
            pw.println("</field>");
         }

      }
      indent--;
      indenter(pw, indent);
      pw.println("</fields-write>");
      indenter(pw, indent);
      pw.println("<methods>");
      indent++;
      long[] keys = advisor.getMethodInterceptors().keys();
      for (int i = 0; i < keys.length; i++)
      {
         long key = keys[i];
         MethodInfo method = (MethodInfo) advisor.getMethodInterceptors().get(key);
         @SuppressWarnings("unchecked")
         HashMap methodCallers = (HashMap) advisor.getMethodCalledByMethodInterceptors().get(key);
         @SuppressWarnings("unchecked")
         HashMap conCallers = (HashMap) advisor.getConCalledByMethodInterceptors().get(key);
         if (method == null && methodCallers == null) continue;
         if (method != null && methodCallers == null && (method.getInterceptors() == null || method.getInterceptors().length < 1)) continue;
         indenter(pw, indent);
         pw.println("<method signature=\"" + method.getMethod().toString() + "\">");
         if (method != null)
         {
            Interceptor[] chain = method.getInterceptors();
            if (chain != null && chain.length > 0)
            {
               outputChain(indent + 1, pw, chain);
            }
         }
         if (methodCallers != null)
         {
            outputMethodCallers(indent + 1, pw, methodCallers);
         }
         if (conCallers != null)
         {
            outputConCallers(indent + 1, pw, conCallers);
         }
         indenter(pw, indent);
         pw.println("</method>");

      }
      indent--;
      indenter(pw, indent);
      pw.println("</methods>");

      outputMetadata(indent, pw, advisor);
   }


   public static void outputMethodCallers(int indent, PrintWriter pw, HashMap<String, TLongObjectHashMap> called)
   {
      indenter(pw, indent);
      pw.println("<method-callers>");
      indent++;
      Iterator<TLongObjectHashMap> it = called.values().iterator();
      while (it.hasNext())
      {
         TLongObjectHashMap map = it.next();
         Object[] values = map.getValues();
         for (int i = 0; i < values.length; i++)
         {
            CallerMethodInfo caller = (CallerMethodInfo) values[i];
            indenter(pw, indent);
            if (caller.getInterceptors() != null)
            {
               pw.println("<called-method signature=\"" + caller.getMethod() + "\">");
               outputChain(indent + 1, pw, caller.getInterceptors());
               indenter(pw, indent);
               pw.println("</called-method>");
            }
            else
            {
               pw.println("<called-method signature=\"" + caller.getMethod() + "\"/>");

            }
         }
      }
      indent--;
      indenter(pw, indent);
      pw.println("</method-callers>");
   }

   public static void outputConCallers(int indent, PrintWriter pw, HashMap<String, TLongObjectHashMap> called)
   {
      indenter(pw, indent);
      pw.println("<constructor-callers>");
      indent++;
      Iterator<TLongObjectHashMap> it = called.values().iterator();
      while (it.hasNext())
      {
         TLongObjectHashMap map = it.next();
         Object[] values = map.getValues();
         for (int i = 0; i < values.length; i++)
         {
            CallerConstructorInfo caller = (CallerConstructorInfo) values[i];
            indenter(pw, indent);
            if (caller.getInterceptors() != null)
            {
               pw.println("<called-constructor signature=\"" + caller.getConstructor() + "\">");
               outputChain(indent + 1, pw, caller.getInterceptors());
               indenter(pw, indent);
               pw.println("</called-constructor>");
            }
            else
            {
               pw.println("<called-constructor signature=\"" + caller.getConstructor() + "\"/>");

            }
         }
      }
      indent--;
      indenter(pw, indent);
      pw.println("</constructor-callers>");
   }

   public static void outputChain(int indent, PrintWriter pw, Interceptor[] chain)
   {
      indenter(pw, indent);
      pw.println("<interceptors>");
      indent++;
      for (int i = 0; i < chain.length; i++)
      {
         if (chain[i] instanceof AbstractAdvice)
         {
            indenter(pw, indent);
            pw.println("<advice name=\"" + chain[i].getName() + "\"/>");
         }
         else if (chain[i] instanceof CFlowInterceptor)
         {
            indenter(pw, indent);
            pw.println("<cflow expr=\"" + ((CFlowInterceptor) chain[i]).getCFlowString() + "\"/>");
         }
         else
         {
            indenter(pw, indent);
            pw.println("<interceptor class=\"" + chain[i].getClass().getName() + "\"/>");
         }
      }
      indent--;
      indenter(pw, indent);
      pw.println("</interceptors>");
   }

   public static void outputUnboundBindings(int indent, PrintWriter pw)
   {
      boolean first = true;
      for (AdviceBinding binding : AspectManager.instance().getBindings().values())
      {
         if (!binding.hasAdvisors())
         {
            if (first)
            {
               first = false;
               indenter(pw, indent);
               pw.println("<unbound-bindings>");
               indent++;
            }
            indenter(pw, indent);
            pw.print("<binding pointcut=\"" + binding.getPointcut().getExpr() + "\"");
            if (binding.getCFlowString() != null)
            {
               pw.print(" cflow=\"" + binding.getCFlowString() + "\"");
            }
            pw.println(" />");
         }
      }
      if (!first)
      {
         indent--;
         indenter(pw, indent);
         pw.println("</unbound-bindings>");
      }
   }

   public static void outputMetadata(int indent, PrintWriter pw, ClassAdvisor advisor)
   {
      StringWriter sw = new StringWriter();
      PrintWriter metaWriter = new PrintWriter(sw);

      StringBuffer sb = new StringBuffer();
      sb.append(getDefaultMetadataXml(indent, metaWriter, advisor));
      sb.append(getClassMetadataXml(indent, metaWriter, advisor));
      sb.append(getConstructorMetadataXml(indent, pw, advisor));
      sb.append(getMethodMetadataXml(indent, pw, advisor));
      sb.append(getFieldMetadataXml(indent, pw, advisor));
      //sb.append();
      
      if (sb.length() > 0)
      {
         indenter(pw, indent);
         pw.println("<metadata>");
         pw.print(sb.toString());
         indenter(pw, indent);
         pw.println("</metadata>");
      }
   }

   public static String getDefaultMetadataXml(int indent, PrintWriter pw, ClassAdvisor advisor)
   {
      SimpleMetaData metadata = advisor.getDefaultMetaData();

      indent++;
      StringBuffer xml = getMetadataXml(indent, advisor, metadata);

      if (xml != null)
      {
         xml.insert(0, getIndent(indent) + "<default>\r\n");
         xml.append(getIndent(indent) + "</default>\r\n");
         return xml.toString();
      }
      indent--;

      return "";
   }

   public static String getClassMetadataXml(int indent, PrintWriter pw, ClassAdvisor advisor)
   {
      SimpleMetaData metadata = advisor.getClassMetaData();

      indent++;
      StringBuffer xml = getMetadataXml(indent, advisor, metadata);

      if (xml != null)
      {
         xml.insert(0, getIndent(indent) + "<class>\r\n");
         xml.append(getIndent(indent) + "</class>\r\n");
         return xml.toString();
      }
      indent--;

      return "";
   }

   public static String getFieldMetadataXml(int indent, PrintWriter pw, ClassAdvisor advisor)
   {
      FieldMetaData fieldMetaData = advisor.getFieldMetaData();

      StringBuffer xml = new StringBuffer();
      indent++;
      for (Iterator<String> it = fieldMetaData.getFields(); it.hasNext();)
      {
         String field = it.next();
         org.jboss.aop.metadata.SimpleMetaData fieldData = fieldMetaData.getFieldMetaData(field);
         indent++;
         indent++;
         StringBuffer sb = getMetadataXml(indent, advisor, fieldData);
         indent--;
         indent--;

         if (sb != null)
         {
            indent++;
            xml.append(getIndent(indent) + "<field name=\"" + field + "\">" + "\r\n");
            xml.append(sb);
            xml.append(getIndent(indent) + "</field>" + "\r\n");
            indent--;
         }
      }

      if (xml.length() > 0)
      {
         xml.insert(0, getIndent(indent) + "<fields>" + "\r\n");
         xml.append(getIndent(indent) + "</fields>" + "\r\n");
      }
      indent--;

      return xml.toString();
   }

   public static String getConstructorMetadataXml(int indent, PrintWriter pw, ClassAdvisor advisor)
   {
      ConstructorMetaData constructorMetaData = advisor.getConstructorMetaData();

      StringBuffer xml = new StringBuffer();
      indent++;
      for (Iterator<String> it = constructorMetaData.getConstructors(); it.hasNext();)
      {
         String constructor = it.next();
         org.jboss.aop.metadata.SimpleMetaData constructorData = constructorMetaData.getConstructorMetaData(constructor);
         indent++;
         indent++;
         StringBuffer sb = getMetadataXml(indent, advisor, constructorData);
         indent--;
         indent--;

         if (sb != null)
         {
            indent++;
            xml.append(getIndent(indent) + "<constructor name=\"" + constructor + "\">" + "\r\n");
            xml.append(sb);
            xml.append(getIndent(indent) + "</constructor>" + "\r\n");
            indent--;
         }
      }

      if (xml.length() > 0)
      {
         xml.insert(0, getIndent(indent) + "<constructors>" + "\r\n");
         xml.append(getIndent(indent) + "</constructors>" + "\r\n");
      }
      indent--;

      return xml.toString();
   }

   public static String getMethodMetadataXml(int indent, PrintWriter pw, ClassAdvisor advisor)
   {
      MethodMetaData methodMetaData = advisor.getMethodMetaData();

      StringBuffer xml = new StringBuffer();
      indent++;
      for (Iterator<String> it = methodMetaData.getMethods(); it.hasNext();)
      {
         String method = it.next();
         org.jboss.aop.metadata.SimpleMetaData methodData = methodMetaData.getMethodMetaData(method);
         indent++;
         indent++;
         StringBuffer sb = getMetadataXml(indent, advisor, methodData);
         indent--;
         indent--;

         if (sb != null)
         {
            indent++;
            xml.append(getIndent(indent) + "<method name=\"" + method + "\">" + "\r\n");
            xml.append(sb);
            xml.append(getIndent(indent) + "</method>" + "\r\n");
            indent--;
         }
      }

      if (xml.length() > 0)
      {
         xml.insert(0, getIndent(indent) + "<methods>" + "\r\n");
         xml.append(getIndent(indent) + "</methods>" + "\r\n");
      }
      indent--;

      return xml.toString();
   }

   public static StringBuffer getMetadataXml(int indent, ClassAdvisor advisor, SimpleMetaData metadata)
   {
      StringWriter sw = new StringWriter();
      HashSet<Object> tags = metadata.tags();
      if (tags.size() == 0)
      {
         return null;
      }

      for (Iterator<Object> tagsIt = tags.iterator(); tagsIt.hasNext();)
      {
         String tag = (String) tagsIt.next();
         HashMap<Object, SimpleMetaData.MetaDataValue> groupAttrs = metadata.tag(tag);

         indent++;
         indenter(sw, indent);
         sw.write("<tag name=\"" + tag + "\">" + "\r\n");
         indent++;

         if (groupAttrs == null)
         {
            continue;
         }

         boolean hasValues = false;
         for (Iterator<Map.Entry<Object, SimpleMetaData.MetaDataValue>> attrsIt = groupAttrs.entrySet().iterator(); attrsIt.hasNext();)
         {
            Map.Entry<Object, SimpleMetaData.MetaDataValue> entry = attrsIt.next();
            String attr = (String) entry.getKey();
            if (!attr.equals(MetaDataResolver.EMPTY_TAG))
            {
               hasValues = true;
               SimpleMetaData.MetaDataValue value = entry.getValue();

               indenter(sw, indent);
               sw.write("<attribute name=\"" + attr + "\">" + "\r\n");

               indent++;
               indenter(sw, indent);
               sw.write("<type>" + value.value.getClass().getName() + "</type>" + "\r\n");
               indenter(sw, indent);
               sw.write("<value-as-string>" + value.value.toString() + "</value-as-string>" + "\r\n");
               indenter(sw, indent);
               sw.write("<serialization>" + value.type + "</serialization>" + "\r\n");
               indent--;

               indenter(sw, indent);
               sw.write("</attribute>" + "\r\n");
            }
         }

         if (!hasValues)
         {
            indenter(sw, indent);
            sw.write("<empty/>" + "\r\n");
         }

         indent--;
         indenter(sw, indent);
         sw.write("</tag>" + "\r\n");
         indent--;
      }

      return sw.getBuffer();
   }


}
