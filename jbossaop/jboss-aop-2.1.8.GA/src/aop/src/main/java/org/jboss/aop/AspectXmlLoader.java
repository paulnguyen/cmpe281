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
package org.jboss.aop;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jboss.aop.advice.AdviceBinding;
import org.jboss.aop.advice.AdviceFactory;
import org.jboss.aop.advice.AdviceStack;
import org.jboss.aop.advice.AdviceType;
import org.jboss.aop.advice.AspectDefinition;
import org.jboss.aop.advice.AspectFactory;
import org.jboss.aop.advice.AspectFactoryDelegator;
import org.jboss.aop.advice.AspectFactoryWithClassLoader;
import org.jboss.aop.advice.DynamicCFlowDefinition;
import org.jboss.aop.advice.GenericAspectFactory;
import org.jboss.aop.advice.InterceptorFactory;
import org.jboss.aop.advice.PrecedenceDef;
import org.jboss.aop.advice.PrecedenceDefEntry;
import org.jboss.aop.advice.Scope;
import org.jboss.aop.advice.ScopeUtil;
import org.jboss.aop.advice.ScopedInterceptorFactory;
import org.jboss.aop.array.ArrayBinding;
import org.jboss.aop.array.ArrayReplacement;
import org.jboss.aop.array.Type;
import org.jboss.aop.introduction.AnnotationIntroduction;
import org.jboss.aop.introduction.InterfaceIntroduction;
import org.jboss.aop.metadata.ClassMetaDataBinding;
import org.jboss.aop.metadata.ClassMetaDataLoader;
import org.jboss.aop.pointcut.CFlow;
import org.jboss.aop.pointcut.CFlowStack;
import org.jboss.aop.pointcut.DeclareDef;
import org.jboss.aop.pointcut.Pointcut;
import org.jboss.aop.pointcut.PointcutExpression;
import org.jboss.aop.pointcut.Typedef;
import org.jboss.aop.pointcut.TypedefExpression;
import org.jboss.aop.pointcut.ast.ASTCFlowExpression;
import org.jboss.aop.pointcut.ast.ASTStart;
import org.jboss.aop.pointcut.ast.ParseException;
import org.jboss.aop.pointcut.ast.PointcutExpressionParser;
import org.jboss.aop.pointcut.ast.TypeExpressionParser;
import org.jboss.aop.util.XmlHelper;
import org.jboss.aop.util.logging.AOPLogger;
import org.jboss.util.xml.XmlLoadable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision: 77493 $
 */
public class AspectXmlLoader implements XmlLoader
{
   private static final AOPLogger logger = AOPLogger.getLogger(AspectXmlLoader.class);
   
   // Attributes ---------------------------------------------------

   protected int counter;
   protected String defaultBaseName;
   protected AspectManager manager;
   // list of binding names
   protected ArrayList<String> bindings = new ArrayList<String>();
   // list of factory names
   protected ArrayList<String> factories = new ArrayList<String>();
   // list of aspect names
   protected ArrayList<String> aspects = new ArrayList<String>();

   public AspectXmlLoader()
   {
   }

   public void setManager(AspectManager manager)
   {
      if (AspectManager.verbose && logger.isDebugEnabled()) logger.debug("AspectXMLLoader using manager" + manager);
      this.manager = manager;
   }
   
   private ClassLoader cl;
   
   public void setClassLoader(ClassLoader cl)
   {
      this.cl = cl;
   }
   
   public ClassLoader getClassLoader()
   {
      if (cl == null)
         return SecurityActions.getContextClassLoader();
      return cl;
   }

   /**
    * Generates a default name if one is not provided
    */
   private String getName(Element element, String errorHeader)
   {
      String name = element.getAttribute("name");
      if (name != null && !name.equals(""))
      {
         return name;
      }

      return getName(errorHeader);

   }

   private String getName(String errorHeader)
   {
      return defaultBaseName + counter++;
   }

   public void undeployInterceptor(Element element) throws Exception
   {
      String name = element.getAttribute("name");
      if (name != null && name.trim().equals("")) name = null;

      String clazz = element.getAttribute("class");
      if (clazz != null && clazz.trim().equals("")) clazz = null;
      String factory = element.getAttribute("factory");
      if (factory != null && factory.trim().equals("")) factory = null;
      if (name == null) name = (clazz == null) ? factory : clazz;
      //manager.removeAspectDefinition(name);
      aspects.add(name);
      String factoryName = name;
      //manager.removeInterceptorFactory(factoryName);
      factories.add(factoryName);
   }

   public InterceptorFactory deployInterceptor(Element element) throws Exception
   {
      String name = element.getAttribute("name");
      if (name != null && name.trim().equals("")) name = null;

      String clazz = element.getAttribute("class");
      if (clazz != null && clazz.trim().equals("")) clazz = null;
      String factory1 = element.getAttribute("factory");
      if (factory1 != null && factory1.trim().equals("")) factory1 = null;
      if (clazz == null && factory1 == null) throw new RuntimeException("Interceptor" + " " + name + " must have a class or factory associated with it.");
      if (clazz != null && factory1 != null) throw new RuntimeException("Interceptor" + " " + name + " cannot have both the class and factory attribute set.");
      if (name == null) name = (clazz == null) ? factory1 : clazz;

      String s = element.getAttribute("scope");
      if (s != null && s.trim().equals("")) s = null;

      Scope scope = null;
      if (s != null)
      {
         scope = ScopeUtil.parse(s);
         if (scope == null) throw new RuntimeException("Illegal scope attribute value: " + s);
      }
      AspectDefinition def = manager.getAspectDefinition(name);

      // if interceptor definition already exists, then just check to see if scopes are the same
      // todo deprecate <interceptor> within an advice stack
      if (def != null)
      {
         if (scope == null) scope = Scope.PER_VM;
         if (scope != def.getScope()) throw new RuntimeException("multiple definitions of <interceptor> " + name + " with different scopes is illegal");
      }
      else
      {
         AspectFactory aspectFactory;
         if (clazz != null)
         {
            aspectFactory = new GenericAspectFactory(clazz, element);
            ((AspectFactoryWithClassLoader)aspectFactory).setClassLoader(cl);
         }
         else
         {
            aspectFactory = new AspectFactoryDelegator(factory1, element);
            ((AspectFactoryWithClassLoader)aspectFactory).setClassLoader(cl);
         }
         
         def = new AspectDefinition(name, scope, aspectFactory);
         manager.addAspectDefinition(def);
      }
      InterceptorFactory factory = manager.getInterceptorFactory(name); 
      if (factory == null)
      {
         factory = new ScopedInterceptorFactory(def);
         manager.addInterceptorFactory(name, factory);
      }
      return factory;
   }


   public void undeployAdvice(Element element) throws Exception
   {
      String name = element.getAttribute("name");
      String aspect = element.getAttribute("aspect");
      String factory = aspect + "." + name;
      //manager.removeInterceptorFactory(factory);
      factories.add(factory);
   }

   public InterceptorFactory deployAdvice(Element element, AdviceType type) throws Exception
   {
      String name = element.getAttribute("name");
      String aspect = element.getAttribute("aspect");
      if(aspect == null || aspect.length() < 1)
         throw new RuntimeException("Aspect couldnt be found for element: "+element.getNodeName()+", typo perhaps?");
      AspectDefinition def = manager.getAspectDefinition(aspect);
      if (def == null) throw new RuntimeException("advice " + name + " cannot find aspect " + aspect);
      
      AdviceFactory factory = null;
      if (type == null)
      {
         // use default advice type
         factory = new AdviceFactory(def, name);
      }
      else
      {
         factory = new AdviceFactory(def, name, type);
      }
      
      manager.addInterceptorFactory(factory.getName(), factory);
      return factory;
   }
   

   public void deployBinding(Element element) throws Exception
   {
      String name = getName(element, "binding");
      String pointcut = element.getAttribute("pointcut");
      if (pointcut == null || pointcut.trim().equals(""))
      {
         throw new RuntimeException("Binding must have a pointcut element associated with it.");
      }
      String cflow = element.getAttribute("cflow");
      if (cflow != null && cflow.trim().equals(""))
      {
         cflow = null;
      }
      ASTCFlowExpression cflowExpression = null;
      if (cflow != null)
      {
         try
         {
            cflowExpression = new PointcutExpressionParser(new StringReader(cflow)).CFlowExpression();
         }
         catch (ParseException e)
         {
            throw new RuntimeException(cflow, e);  //To change body of catch statement use Options | File Templates.
         }
      }
      ArrayList<InterceptorFactory> interceptors = loadInterceptors(element);
      InterceptorFactory[] inters = interceptors.toArray(new InterceptorFactory[interceptors.size()]);
      Pointcut p = null;
      try
      {
         p = new PointcutExpression(getName("binding pointcut "), pointcut);
      }
      catch (ParseException ex)
      {
         throw new RuntimeException("<bind> pointcut expression failed: " + pointcut, ex);
      }
      AdviceBinding binding = new AdviceBinding(name, p, cflowExpression, cflow, inters);
      manager.addBinding(binding);
   }

   public void undeployBinding(Element element) throws Exception
   {
      String binding = getName(element, "binding");
      if (binding == null) throw new RuntimeException("undeploying Binding that is null!");
      //manager.removeBinding(binding);//done in bulkUndeploy() step
      bindings.add(binding);
      unloadInterceptors(element);
      String pointcut = getName("pointcut");
      manager.removePointcut(pointcut);
   }
   
   public void deployArrayBinding(Element element) throws Exception
   {
      String name = getName(element, "arraybinding");
      String type = element.getAttribute("type");
      if (type == null || type.trim().equals(""))
      {
         throw new RuntimeException("Binding must have a type");
      }
      
      Type theType = Type.valueOf(type);
      
      ArrayList<InterceptorFactory> interceptors = loadInterceptors(element);
      InterceptorFactory[] inters = interceptors.toArray(new InterceptorFactory[interceptors.size()]);
      for (int i = 0 ; i < inters.length ; i++)
      {
         if (!inters[i].getAspect().getScope().equals(Scope.PER_VM))
         {
            throw new RuntimeException("Only PER_VM scoped aspects/interceptors can be used in arraybindings");
         }
      }
      ArrayBinding binding = new ArrayBinding(name, inters, theType);
      manager.addArrayBinding(binding);
   }

   public void undeployArrayBinding(Element element) throws Exception
   {
      String binding = getName(element, "arraybinding");
      if (binding == null) throw new RuntimeException("undeploying Arraybinding that is null!");
      manager.removeArrayBinding(binding);//done in bulkUndeploy() step
   }

   private void deployPrecedence(Element element) throws Exception
   {
      String name = getName(element, "precedence");

      ArrayList<PrecedenceDefEntry> precedenceEntries = new ArrayList<PrecedenceDefEntry>();
      NodeList children2 = element.getChildNodes();
      for (int j = 0; j < children2.getLength(); j++)
      {
         if (children2.item(j).getNodeType() == Node.ELEMENT_NODE)
         {
            Element interceptorElement = (Element) children2.item(j);
            String tag2 = interceptorElement.getTagName();
            if (tag2.equals("interceptor-ref"))
            {
               String iname = interceptorElement.getAttribute("name");
               if (iname == null || iname.length() == 0)
               {
                  throw new RuntimeException("name must be specified for interceptor-ref in precedence declaration");
               }
               precedenceEntries.add(new PrecedenceDefEntry(iname, null));
            }
            else if (tag2.equals("advice"))
            {
               String method = interceptorElement.getAttribute("name");
               String aspect = interceptorElement.getAttribute("aspect");

               if (method == null || method.length() == 0)
               {
                  throw new RuntimeException("name must be specified for advice in precedence declaration");
               }

               if (aspect == null || aspect.length() == 0)
               {
                  throw new RuntimeException("aspect must be specified for advice in precedence declaration");
               }

               precedenceEntries.add(new PrecedenceDefEntry(aspect, method));
            }
            else
            {
               throw new RuntimeException("Invalid child element of precedence : " + tag2);
            }
         }
      }

      PrecedenceDefEntry[] entries = precedenceEntries.toArray(new PrecedenceDefEntry[precedenceEntries.size()]);
      manager.addPrecedence(new PrecedenceDef(name, entries));
   }


   private ArrayList<InterceptorFactory> loadInterceptors(Element element) throws Exception
   {
      ArrayList<InterceptorFactory> interceptors = new ArrayList<InterceptorFactory>();
      NodeList children2 = element.getChildNodes();
      for (int j = 0; j < children2.getLength(); j++)
      {
         if (children2.item(j).getNodeType() == Node.ELEMENT_NODE)
         {
            Element interceptorElement = (Element) children2.item(j);
            String tag2 = interceptorElement.getTagName();
            if (tag2.equals("interceptor"))
            {
               InterceptorFactory factory = deployInterceptor(interceptorElement);
               interceptors.add(factory);
            }
            else if (tag2.equals("interceptor-ref"))
            {
               String iname = interceptorElement.getAttribute("name");
               if (iname == null) throw new RuntimeException("interceptor-ref has null name attribute");
               InterceptorFactory factory = manager.getInterceptorFactory(iname);
               if (factory == null) throw new RuntimeException("unable to resolve interceptor-ref: " + iname);
               interceptors.add(factory);
            }
            else if (tag2.equals("stack-ref"))
            {
               String name = interceptorElement.getAttribute("name");
               AdviceStack stack = manager.getAdviceStack(name);
               if (stack == null) throw new Exception("there is no <stack> defined for name: " + name);
               interceptors.addAll(stack.getInterceptorFactories());
            }
            else
            {
               AdviceType type = null;
               if (!tag2.equals("advice"))
               {
                  try
                  {
                     type = Enum.valueOf(AdviceType.class, tag2.toUpperCase());
                  }
                  catch (IllegalArgumentException e)
                  {
                     StringBuffer message = new StringBuffer();
                     message.append("unexpected tag inside binding: \'");
                     message.append(tag2);
                     message.append("\'\n\t\t\t\t\t   should be one of: \n\t\t\t\t\t\t'interceptor\', \n\t\t\t\t\t\t'interceptor-ref\', \n\t\t\t\t\t\t'advice\', \n\t\t\t\t\t\t'");
                     for (AdviceType adviceType : AdviceType.values())
                     {
                        message.append(adviceType.getName());
                        message.append("\', \n\t\t\t\t\t\t'");
                     }
                     message.append("stack-ref\'.\n");
                     throw new RuntimeException(message.toString());
                  }
               }
               InterceptorFactory factory = deployAdvice(interceptorElement, type);
               interceptors.add(factory);
            }
         }
      }
      return interceptors;
   }

   private void unloadInterceptors(Element element) throws Exception
   {
      NodeList children2 = element.getChildNodes();
      for (int j = 0; j < children2.getLength(); j++)
      {
         if (children2.item(j).getNodeType() == Node.ELEMENT_NODE)
         {
            Element interceptorElement = (Element) children2.item(j);
            String tag2 = interceptorElement.getTagName();
            if (tag2.equals("interceptor"))
            {
               undeployInterceptor(interceptorElement);
            }
            if (tag2.equals("advice"))
            {
               undeployAdvice(interceptorElement);
            }
         }
      }
   }

   public void undeployAspect(Element pointcut) throws Exception
   {
      String clazz = pointcut.getAttribute("class");
      if (clazz == null || clazz.trim().equals("")) clazz = null;
      String name = pointcut.getAttribute("name");
      if (name == null || name.trim().equals("")) name = clazz;
      if (name == null) return;
      //manager.removeAspectDefinition(name);
      aspects.add(name);
   }

   public AspectDefinition deployAspect(Element element, String type) throws Exception
   {
      String name = element.getAttribute("name");
      if (name != null && name.trim().equals("")) name = null;

      String clazz = element.getAttribute("class");
      if (clazz != null && clazz.trim().equals("")) clazz = null;
      String factory = element.getAttribute("factory");
      if (factory != null && factory.trim().equals("")) factory = null;
      if (clazz == null && factory == null) throw new RuntimeException(type + " " + name + " must have a class or factory associated with it.");
      if (clazz != null && factory != null) throw new RuntimeException(type + " " + name + " cannot have both the class and factory attribute set.");
      if (name == null) name = (clazz == null) ? factory : clazz;

      String s = element.getAttribute("scope");
      if (s != null && s.trim().equals("")) s = null;

      Scope scope = null;
      if (s != null)
      {
         scope = ScopeUtil.parse(s);
         if (scope == null) throw new RuntimeException("Illegal scope attribute value: " + s);
      }
      AspectFactory aspectFactory;
      if (clazz != null)
      {
         aspectFactory = new GenericAspectFactory(clazz, element);
         ((AspectFactoryWithClassLoader)aspectFactory).setClassLoader(cl);
      }
      else
      {
         aspectFactory = new AspectFactoryDelegator(factory, element);
         ((AspectFactoryWithClassLoader)aspectFactory).setClassLoader(cl);
      }
      
      AspectDefinition def = new AspectDefinition(name, scope, aspectFactory);
      manager.addAspectDefinition(def);
      return def;
   }

   public void undeployCFlowStack(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      manager.removeCFlowStack(name);
   }

   public void deployCFlowStack(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      if (name != null && name.trim().equals("")) name = null;
      if (name == null) throw new RuntimeException("name required for a cflow-stack");
      CFlowStack stack = new CFlowStack(name);
      NodeList children2 = pointcut.getChildNodes();
      for (int j = 0; j < children2.getLength(); j++)
      {
         if (children2.item(j).getNodeType() == Node.ELEMENT_NODE)
         {
            Element cflowElement = (Element) children2.item(j);
            String tag2 = cflowElement.getTagName();
            String expr = cflowElement.getAttribute("expr");
            if (expr != null && expr.trim().equals(""))
            {
               throw new RuntimeException(tag2 + " requires an expr attribute to be defined");
            }
            if (tag2.equals("called"))
            {
               stack.addCFlow(new CFlow(expr, false));
            }
            if (tag2.equals("not-called"))
            {
               stack.addCFlow(new CFlow(expr, true));
            }
         }
      }

      manager.addCFlowStack(stack);
   }


   public void undeployInterceptorStack(Element element) throws Exception
   {
      String name = element.getAttribute("name");
      unloadInterceptors(element);
      manager.removeInterceptorStack(name);
   }

   public void deployInterceptorStack(Element element) throws Exception
   {
      ArrayList<InterceptorFactory> interceptors = loadInterceptors(element);
      String name = element.getAttribute("name");
      AdviceStack stack = new AdviceStack(name, interceptors);
      manager.addAdviceStack(stack);
   }

   private ClassMetaDataBinding loadMetaData(Element element)
           throws Exception
   {
      String classExpr = element.getAttribute("class");
      String tag = element.getAttribute("tag");
      String name = getName(element, "metadata: " + tag + " " + classExpr);
      ClassMetaDataLoader loader = manager.findClassMetaDataLoader(tag);
      return loader.importMetaData(element, name, tag, classExpr);
   }

   public void undeployMetaDataLoader(Element element) throws Exception
   {
      String tag = element.getAttribute("tag");
      manager.removeClassMetaDataLoader(tag);
   }

   public void deployMetaDataLoader(Element element) throws Exception
   {
      String tag = element.getAttribute("tag");
      String classname = element.getAttribute("class");
      Class<?> clazz = getClassLoader().loadClass(classname);
      ClassMetaDataLoader loader = (ClassMetaDataLoader) clazz.newInstance();
      if (loader instanceof XmlLoadable)
      {
         ((XmlLoadable) loader).importXml(element);
      }
      manager.addClassMetaDataLoader(tag, loader);
   }

   public void deployClassMetaData(Element element) throws Exception
   {
      ClassMetaDataBinding data = loadMetaData(element);
      manager.addClassMetaData(data);
   }

   public void undeployClassMetaData(Element element) throws Exception
   {
      String classExpr = element.getAttribute("class");
      String tag = element.getAttribute("tag");
      String name = getName(element, "metadata: " + tag + " " + classExpr);
      manager.removeClassMetaData(name);
   }

   public void undeployPointcut(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      manager.removePointcut(name);
   }

   public void undeployPluggablePointcut(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      manager.removePointcut(name);
   }

   public void deployPluggablePointcut(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      if (name == null || name.trim().equals(""))
      {
         throw new RuntimeException("pluggable pointcut declaration must have a name associated with it");
      }
      String clazz = pointcut.getAttribute("class");
      if (clazz != null && clazz.trim().equals(""))
      {
         throw new RuntimeException("pluggable pointcut declaration must have an expr associated with it");
      }

      Pointcut p = null;

      Class<?> pClass = null;
      try
      {
         pClass = getClassLoader().loadClass(clazz);
      }
      catch (ClassNotFoundException e)
      {
         throw new RuntimeException("pluggable pointcut class not found: " + clazz);
      }
      p = (Pointcut) pClass.newInstance();
      if (p instanceof XmlLoadable)
      {
         ((XmlLoadable) p).importXml(pointcut);
      }
      manager.addPointcut(p);
   }

   public void undeployDynamicCFlow(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      manager.removeDynamicCFlow(name);
   }

   public void deployDynamicCFlow(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      if (name == null || name.trim().equals(""))
      {
         throw new RuntimeException("dynamic cflow declaration must have a name associated with it");
      }
      String clazz = pointcut.getAttribute("class");
      if (clazz != null && clazz.trim().equals(""))
      {
         throw new RuntimeException("dynamic cflow declaration must have an expr associated with it");
      }

      manager.addDynamicCFlow(name, new DynamicCFlowDefinition(pointcut, clazz, name));
   }

   public void deployPointcut(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      if (name == null || name.trim().equals(""))
      {
         throw new RuntimeException("pointcut declaration must have a name associated with it");
      }
      String expr = pointcut.getAttribute("expr");
      if (expr == null || expr.trim().equals(""))
      {
         throw new RuntimeException("pointcut declaration must have an expr associated with it");
      }
      Pointcut p = null;
      try
      {
         p = new PointcutExpression(name, expr);
      }
      catch (ParseException ex)
      {
         throw new RuntimeException("<pointcut name='" + name + "' expr='" + expr + "'/> failed", ex);
      }
      manager.addPointcut(p);
   }

   public void undeployPrepare(Element pointcut) throws Exception
   {
      String name = getName(pointcut, "prepare");
      manager.removePointcut(name);
   }

   public void deployPrepare(Element pointcut) throws Exception
   {
      String name = getName(pointcut, "prepare");
      String expr = pointcut.getAttribute("expr");
      if (expr != null && expr.trim().equals(""))
      {
         throw new RuntimeException("pointcut declaration must have an expr associated with it");
      }
      Pointcut p = null;
      try
      {
         p = new PointcutExpression(name, expr);
      }
      catch (ParseException ex)
      {
         throw new RuntimeException("<prepare> failed: " + expr, ex);
      }
      manager.addPointcut(p);
   }
   
   public void undeployArrayReplacement(Element pointcut) throws Exception
   {
      String name = getName(pointcut, "arrayReplacement");
      manager.removeArrayReplacement(name);
   }
   
   public void deployArrayReplacement(Element pointcut) throws Exception
   {
      String name = getName(pointcut, "arrayReplacement");
      String classExpr = pointcut.getAttribute("class");
      if (classExpr != null && classExpr.trim().equals(""))
      {
         classExpr = null;
      }

      String ast = pointcut.getAttribute("expr");
      if (ast != null && ast.trim().equals(""))
      {
         ast = null;
      }

      if (classExpr == null && ast == null)
      {
         throw new RuntimeException("A class nor a expr attribute is defined for this <arrayreplacement>");
      }

      if (classExpr != null && ast != null)
      {
         throw new RuntimeException("You cannot define both a class and expr attribute in the same <arrayreplacement>");
      }

      ArrayReplacement pcut = null;
      if (classExpr != null)
      {
         pcut = new ArrayReplacement(name, classExpr);
      }
      else
      {
         ASTStart start = new TypeExpressionParser(new StringReader(ast)).Start();
         pcut = new ArrayReplacement(name, start);
      }
      manager.addArrayReplacement(pcut);
   }


   public void deployAnnotationIntroduction(Element pointcut) throws Exception
   {
      manager.addAnnotationIntroduction(loadAnnotationIntroduction(pointcut));
   }

   public void deployAnnotationOverride(Element pointcut) throws Exception
   {
      manager.addAnnotationOverride(loadAnnotationIntroduction(pointcut));
   }

   public void undeployAnnotationIntroduction(Element pointcut) throws Exception
   {
      manager.removeAnnotationIntroduction(loadAnnotationIntroduction(pointcut));
   }

   public void undeployAnnotationOverride(Element pointcut) throws Exception
   {
      manager.removeAnnotationOverride(loadAnnotationIntroduction(pointcut));
   }

   public AnnotationIntroduction loadAnnotationIntroduction(Element pointcut) throws Exception
   {
      String expr = pointcut.getAttribute("expr");
      if (expr != null && expr.trim().equals(""))
      {
         throw new RuntimeException("annotation introduction must have an expr attribute");
      }

      String invisible = pointcut.getAttribute("invisible");
      if (invisible != null && expr.trim().equals(""))
      {
         throw new RuntimeException("annotation introduction must have an invisible attribute");
      }

      boolean isInvisble = new Boolean(invisible).booleanValue();

      String annotation = XmlHelper.getElementContent(pointcut);

      return AnnotationIntroduction.createComplexAnnotationIntroduction(expr, annotation, isInvisble);
   }

   public void undeployIntroductionPointcut(Element pointcut) throws Exception
   {
      String name = getName(pointcut, "introduction-pointcut");

      manager.removeInterfaceIntroduction(name);
   }

   public void deployIntroductionPointcut(Element pointcut) throws Exception
   {
      String name = getName(pointcut, "introduction");
      String classExpr = pointcut.getAttribute("class");
      if (classExpr != null && classExpr.trim().equals(""))
      {
         classExpr = null;
      }

      String ast = pointcut.getAttribute("expr");
      if (ast != null && ast.trim().equals(""))
      {
         ast = null;
      }


      if (classExpr == null && ast == null)
      {
         throw new RuntimeException("A class nor a expr attribute is defined for this <introduction>");
      }

      if (classExpr != null && ast != null)
      {
         throw new RuntimeException("You cannot define both a class and expr attribute in the same <introduction>");
      }

      String intfs = XmlHelper.getOptionalChildContent(pointcut, "interfaces");
      String[] ifaces = null;
      if (intfs != null)
      {
         StringTokenizer tokenizer = new StringTokenizer(intfs, ",");
         ArrayList<String> interfaces = new ArrayList<String>();
         while (tokenizer.hasMoreTokens())
         {
            String intf = tokenizer.nextToken().trim();
            if (!intf.equals("")) interfaces.add(intf);
         }
         ifaces = interfaces.toArray(new String[interfaces.size()]);
      }

      InterfaceIntroduction pcut = null;
      if (classExpr != null)
      {
         pcut = new InterfaceIntroduction(name, classExpr, ifaces);
      }
      else
      {
         ASTStart start = new TypeExpressionParser(new StringReader(ast)).Start();
         pcut = new InterfaceIntroduction(name, start, ifaces);
      }
      Iterator<Element> it = XmlHelper.getChildrenByTagName(pointcut, "mixin");
      while (it.hasNext())
      {
         Element mixin = it.next();
         if (mixin != null)
         {
            String construction = XmlHelper.getOptionalChildContent(mixin, "construction");
            String classname = XmlHelper.getUniqueChildContent(mixin, "class");
            String isTransientString = mixin.getAttribute("transient");
            boolean isTransient = true;
            if (isTransientString == null || isTransientString.trim().equals(""))
            {
               isTransient = true;
            }
            else
            {
               isTransient = new Boolean(isTransientString).booleanValue();
            }

            intfs = XmlHelper.getUniqueChildContent(mixin, "interfaces");
            StringTokenizer tokenizer = new StringTokenizer(intfs, ",");
            ArrayList<String> interfaces = new ArrayList<String>();
            while (tokenizer.hasMoreTokens())
            {
               String intf = tokenizer.nextToken().trim();
               if (!intf.equals("")) interfaces.add(intf);
            }
            ifaces = interfaces.toArray(new String[interfaces.size()]);
            pcut.getMixins().add(new InterfaceIntroduction.Mixin(classname, ifaces, construction, isTransient));
         }
      }
      manager.addInterfaceIntroduction(pcut);
   }

   public void deployTypedef(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      if (name == null || name.trim().equals(""))
      {
         throw new RuntimeException("typedef declaration must have a name associated with it");
      }
      String expr = pointcut.getAttribute("expr");
      if (expr == null || expr.trim().equals(""))
      {
         throw new RuntimeException("typedef declaration must have an expr associated with it");
      }

      try
      {
         Typedef typedef = new TypedefExpression(name, expr);
         manager.addTypedef(typedef);
      }
      catch (ParseException ex)
      {
         throw new RuntimeException("<typedef name='" + name + "' expr='" + expr + "'/> failed", ex);
      }
   }

   public void undeployTypedef(Element pointcut) throws Exception
   {
      String name = pointcut.getAttribute("name");
      manager.removeTypedef(name);
   }

   public void deployDeclare(Element pointcut, String tagName) throws Exception
   {
      String name = getName(pointcut, "declare");
      String expr = pointcut.getAttribute("expr");
      if (expr == null || expr.trim().equals(""))
      {
         throw new RuntimeException("declare declaration must have an expr associated with it");
      }

      boolean warning = (tagName.equals("declare-warning"));
      String msg = XmlHelper.getElementContent(pointcut);


      try
      {
         DeclareDef declare = new DeclareDef(name, expr, warning, msg);
         manager.addDeclare(declare);
      }
      catch (ParseException ex)
      {
         throw new RuntimeException("<declare name='" + name + "' expr='" + expr + "'/> failed", ex);
      }
   }

   public void undeployDeclare(Element pointcut) throws Exception
   {
      String name = getName(pointcut, "declare");
      manager.removeDeclare(name);
   }


   private void setupDefaultName(URL url) throws Exception
   {
      if (url == null) return;
      counter = 0;
      defaultBaseName = url.toString();
   }

   public void deployXML(Document doc, URL url, ClassLoader cl) throws Exception
   {
      setClassLoader(cl);
      deployXML(doc, url);
   }

   public void deployXML(Document doc, URL url) throws Exception
   {
      setupDefaultName(url);
      Element top = doc.getDocumentElement();
      try
      {
         deployTopElements(top);
      }
      catch (Exception e)
      {
         logger.error(e.getMessage() + " " + url, e);
         throw new RuntimeException(e);
      }
   }

   public void deployDomain(Element element) throws Exception
   {
      String name = element.getAttribute("name");
      if (name == null || name.trim().equals(""))
      {
         throw new RuntimeException("domain declaration must have a name associated with it");
      }

      boolean parentFirst = false;
      String parentFirstString = element.getAttribute("parentFirst");
      if (parentFirstString == null || parentFirstString.trim().equals(""))
      {
         parentFirst = false;
      }
      else
      {
         parentFirst = new Boolean(parentFirstString).booleanValue();
      }

      boolean inheritDefs = true;
      String inheritDefsString = element.getAttribute("inheritDefinitions");
      if (inheritDefsString == null || inheritDefsString.trim().equals(""))
      {
         inheritDefs = true;
      }
      else
      {
         inheritDefs = new Boolean(inheritDefsString).booleanValue();
      }

      boolean inheritBindings = false;
      String inheritBindingsString = element.getAttribute("inheritBindings");
      if (inheritBindingsString == null || inheritBindingsString.trim().equals(""))
      {
         inheritBindings = false;
      }
      else
      {
         inheritBindings = new Boolean(inheritBindingsString).booleanValue();
      }

      AspectManager parent = manager;
      String extend = element.getAttribute("extends");
      if (extend != null && !extend.trim().equals(""))
      {
         extend = extend.trim();
         DomainDefinition parentDef = manager.getContainer(extend);
         if (parentDef == null) throw new RuntimeException("unable to find parent Domain: " + extend);
         parent = parentDef.getManager();
      }

      DomainDefinition def = new DomainDefinition(name, parent, parentFirst, inheritDefs, inheritBindings);
      AspectManager push = manager;
      try
      {
         manager = def.getManager();
         deployTopElements(element);
      }
      finally
      {
         manager = push;
      }
      push.addContainer(def);
   }

   public void undeployDomain(Element element) throws Exception
   {
      String name = element.getAttribute("name");
      if (name == null || name.trim().equals(""))
      {
         throw new RuntimeException("container declaration must have a name associated with it");
      }

      DomainDefinition def = manager.getContainer(name);
      if (def == null)
      {
         logger.warn("No domain found with name: " + name);
         return;
      }
      AspectManager push = manager;
      ArrayList<String> oldFactories = factories;
      ArrayList<String> oldAspects = aspects;
      ArrayList<String> oldBindings = bindings;
      try
      {
         factories = new ArrayList<String>();
         aspects = new ArrayList<String>();
         bindings = new ArrayList<String>();
         manager = def.getManager();
         undeployTopElements(element);
         bulkUndeploy();
      }
      finally
      {
         manager = push;
      }
      push.removeContainer(name);
      factories = oldFactories;
      aspects = oldAspects;
      bindings = oldBindings;
   }

   private void deployTopElements(Element top)
           throws Exception
   {
      NodeList children = top.getChildNodes();
      for (int i = 0; i < children.getLength(); i++)
      {
         if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
         {
            Element element = (Element) children.item(i);
            String tag = element.getTagName();
            if (tag.equals("interceptor"))
            {
               deployInterceptor(element);
            }
            else if (tag.equals("introduction"))
            {
               deployIntroductionPointcut(element);
            }
            else if (tag.equals("metadata-loader"))
            {
               deployMetaDataLoader(element);
            }
            else if (tag.equals("metadata"))
            {
               deployClassMetaData(element);
            }
            else if (tag.equals("stack"))
            {
               deployInterceptorStack(element);
            }
            else if (tag.equals("aspect"))
            {
               deployAspect(element, "Aspect");
            }
            else if (tag.equals("pointcut"))
            {
               deployPointcut(element);
            }
            else if (tag.equals("pluggable-pointcut"))
            {
               deployPluggablePointcut(element);
            }
            else if (tag.equals("bind"))
            {
               deployBinding(element);
            }
            else if (tag.equals("prepare"))
            {
               deployPrepare(element);
            }
            else if (tag.equals("cflow-stack"))
            {
               deployCFlowStack(element);
            }
            else if (tag.equals("dynamic-cflow"))
            {
               deployDynamicCFlow(element);
            }
            else if (tag.equals("annotation-introduction"))
            {
               deployAnnotationIntroduction(element);
            }
            else if (tag.equals("annotation"))
            {
               deployAnnotationOverride(element);
            }
            else if (tag.equals("typedef"))
            {
               deployTypedef(element);
            }
            else if (tag.equals("domain"))
            {
               deployDomain(element);
            }
            else if (tag.equals("precedence"))
            {
               deployPrecedence(element);
            }
            else if (tag.equals("declare-error") || tag.equals("declare-warning"))
            {
               deployDeclare(element, tag);
            }
            else if (tag.equals("loader-repository"))
            {
               //Handled by AspctDeployer in JBoss
            }
            else if (tag.equals("arrayreplacement"))
            {
               deployArrayReplacement(element);
            }
            else if (tag.equals("arraybind"))
            {
               deployArrayBinding(element);
            }
            else
            {
               throw new IllegalArgumentException("Unknown AOP tag: " + tag);
            }
         }
      }
   }

   public void undeployXML(Document doc, URL url) throws Exception
   {
      //Check for URL here
      setupDefaultName(url);
      undeployTopElements(doc.getDocumentElement());

      bulkUndeploy();


   }

   private void bulkUndeploy()
   {
      // undeploy bindings last because it takes a lot of effort to rebuild
      // interceptor chains of all advisors.
      manager.removeBindings(bindings);
      for (int i = 0; i < factories.size(); i++)
      {
         String factory = factories.get(i);
         manager.removeInterceptorFactory(factory);
      }
      for (int i = 0; i < aspects.size(); i++)
      {
         String aspect = aspects.get(i);
         manager.removeAspectDefinition(aspect);
      }
   }

   private void undeployTopElements(Element top)
           throws Exception
   {
      NodeList children = top.getChildNodes();
      for (int i = 0; i < children.getLength(); i++)
      {
         if (children.item(i).getNodeType() == Node.ELEMENT_NODE)
         {
            Element element = (Element) children.item(i);
            String tag = element.getTagName();
            if (tag.equals("interceptor"))
            {
               undeployInterceptor(element);
            }
            else if (tag.equals("introduction"))
            {
               undeployIntroductionPointcut(element);
            }
            else if (tag.equals("metadata-loader"))
            {
               undeployMetaDataLoader(element);
            }
            else if (tag.equals("metadata"))
            {
               undeployClassMetaData(element);
            }
            else if (tag.equals("stack"))
            {
               undeployInterceptorStack(element);
            }
            else if (tag.equals("aspect"))
            {
               undeployAspect(element);
            }
            else if (tag.equals("pointcut"))
            {
               undeployPointcut(element);
            }
            else if (tag.equals("bind"))
            {
               undeployBinding(element);
            }
            else if (tag.equals("prepare"))
            {
               undeployPrepare(element);
            }
            else if (tag.equals("cflow-stack"))
            {
               undeployCFlowStack(element);
            }
            else if (tag.equals("pluggable-pointcut"))
            {
               undeployPluggablePointcut(element);
            }
            else if (tag.equals("dynamic-cflow"))
            {
               undeployDynamicCFlow(element);
            }
            else if (tag.equals("typedef"))
            {
               undeployTypedef(element);
            }
            else if (tag.equals("annotation-introduction"))
            {
               undeployAnnotationIntroduction(element);
            }
            else if (tag.equals("annotation"))
            {
               undeployAnnotationOverride(element);
            }
            else if (tag.equals("domain"))
            {
               undeployDomain(element);
            }
            else if (tag.equals("declare-error") || tag.equals("declare-warning"))
            {
               undeployDeclare(element);
            }
            else if (tag.equals("arrayreplacement"))
            {
               undeployArrayReplacement(element);
            }
            else if (tag.equals("arraybind"))
            {
               undeployArrayBinding(element);
            }
         }
      }
   }

   public static XmlLoaderFactory factory = null;


   public void deploy(URL url, AspectManager manager, ClassLoader cl) throws Exception
   {
      setClassLoader(cl);
      deploy(url, manager);
   }

   public void deploy(URL url, AspectManager manager) throws Exception
   {
      setManager(manager);
      deployXML(loadURL(url), url);
   }

   public void undeploy(URL url, AspectManager manager) throws Exception
   {
      setManager(manager);
      undeployXML(loadURL(url), url);
   }

   public static void deployXML(URL url) throws Exception
   {
      deployXML(url, null, AspectManager.instance());
   }

   /**
    * @deprecated Should pass in AspectManager explicitly
    */
   @Deprecated
   public static void deployXML(URL url, ClassLoader cl) throws Exception
   {
      XmlLoader loader = null;
      if (factory == null)
      {
         loader = new AspectXmlLoader();
      }
      else
      {
         loader = factory.create();
      }
      loader.setClassLoader(cl);
      deployXML(url, cl, AspectManager.instance());
   }
   
   public static void deployXML(URL url, ClassLoader cl, AspectManager manager) throws Exception
   {
      XmlLoader loader = null;
      if (factory == null)
      {
         loader = new AspectXmlLoader();
      }
      else
      {
         loader = factory.create();
      }
      loader.setClassLoader(cl);
      loader.deploy(url, manager);
   }

   public static void undeployXML(URL url) throws Exception
   {
      undeployXML(url, AspectManager.instance());
   }

   public static void undeployXML(URL url, AspectManager manager) throws Exception
   {
      XmlLoader loader = null;
      if (factory == null)
      {
         loader = new AspectXmlLoader();
      }
      else
      {
         loader = factory.create();
      }
      loader.undeploy(url, manager);
   }

   private static class Resolver implements EntityResolver
   {
      public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
      {
         if (systemId.endsWith("jboss-aop_1_0.dtd"))
         {
            try
            {
               URL url = AspectXmlLoader.class.getResource("/jboss-aop_1_0.dtd");
               InputStream is = url.openStream();
               return new InputSource(is);
            }
            catch (IOException e)
            {
               e.printStackTrace();
               return null;
            }
         }
         else if (systemId.endsWith("jboss-aop_2_0.xsd"))
         {
            try
            {
               URL url = AspectXmlLoader.class.getResource("/jboss-aop_2_0.xsd");
               InputStream is = url.openStream();
               return new InputSource(is);
            }
            catch (IOException e)
            {
               e.printStackTrace();
               return null;
            }
         }
         return null;
      }
   }

   public static Document loadURL(URL configURL) throws Exception
   {
      InputStream is = configURL != null ? configURL.openStream() : null;
      if (is == null)
         throw new IOException("Failed to obtain InputStream from url: " + configURL);

      return loadDocument(is);
   }

   public static Document loadDocument(InputStream is)
           throws ParserConfigurationException, SAXException, IOException
   {
      DocumentBuilderFactory docBuilderFactory = null;
      docBuilderFactory = DocumentBuilderFactory.newInstance();
      docBuilderFactory.setValidating(false);
      InputSource source = new InputSource(is);
      //URL url = AspectXmlLoader.class.getResource("/jboss-aop_2_0.xsd");
      //source.setSystemId(url.toString());
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      docBuilder.setEntityResolver(new Resolver());
      //docBuilder.setErrorHandler(new LocalErrorHandler());
      Document doc = docBuilder.parse(source);
      return doc;
   }

}
