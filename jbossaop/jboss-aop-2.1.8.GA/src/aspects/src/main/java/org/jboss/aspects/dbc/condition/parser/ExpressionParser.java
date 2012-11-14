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
package org.jboss.aspects.dbc.condition.parser;

/**
 * A very simple expression parser, for the reason that I really,
 * really cannot get my head around defining grammars in 
 * JavaCC. If this grammar gets more complex I guess JavaCC 
 * is the way forward...
 * 
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision: 37406 $
 */
public class ExpressionParser
{
   static final String FORALL = "forall ";
   static final String EXISTS = "exists ";
   static final String IMPLIES = " implies ";
   static final String JAVA = "java: ";
   static final String IN = " in ";
   static final String SEPARATOR = " | ";
   
   public static Expression parseExpression(String expr)
   {
      return parse(expr);
   }
   
   private static Expression parse(String expr)
   {
      expr = expr.trim();
      if (expr.startsWith(FORALL))
      {
         return parseForAll(expr);
      }
      else if (expr.startsWith(EXISTS))
      {
         return parseExists(expr);         
      }
      else if (expr.indexOf(IMPLIES) > 0)
      {
         return parseImplies(expr);         
      }
      else if (expr.startsWith(JAVA))
      {
         return parseJava(expr);
      }
      else
      {
         return parseBoolean(expr);         
      }
   }
   
   private static Expression parseForAll(String expr)
   {
      int in = expr.indexOf(IN);
      if (in < 0) throw new RuntimeException("forall expressions must have an 'in' clause: " + expr);
      
      String declaration = expr.substring(FORALL.length(), in);
      
      int sep = expr.indexOf(SEPARATOR);
      if (sep < 0) throw new RuntimeException("forall expressions must have a '|': " + expr);
      String collection = expr.substring(in + IN.length(), sep);
      
      String body = expr.substring(sep + SEPARATOR.length());
      Expression sub = parse(body);
      ForAllExpression forAll = new ForAllExpression(declaration, collection, sub);
      return forAll;
   }
   
   private static Expression parseExists(String expr)
   {
      int in = expr.indexOf(IN);
      if (in < 0) throw new RuntimeException("exists expressions must have an 'in' clause: " + expr);
      
      String declaration = expr.substring(EXISTS.length(), in);
      
      int sep = expr.indexOf(SEPARATOR);
      if (sep < 0) throw new RuntimeException("exists expressions must have a '|': " + expr);
      String collection = expr.substring(in + IN.length(), sep);
      
      String body = expr.substring(sep + SEPARATOR.length());
      Expression sub = parse(body);
      ExistsExpression exists = new ExistsExpression(declaration, collection, sub);
      return exists;
   }
   
   
   private static Expression parseImplies(String expr)
   {
      int impl = expr.indexOf(IMPLIES);
      String exprA = expr.substring(0, impl);
      String exprB = expr.substring(impl + IMPLIES.length());

      if (exprA.trim().length() == 0 || exprB.trim().length() == 0)
      {
         throw new RuntimeException("implies expressions must take two simple boolean expressions: " + expr);
      }
      
      if (exprA.endsWith(";"))
      {
         exprA = expr.substring(0, expr.length() - 1);
      }

      try
      {
         Expression condA = parse(exprA);
         Expression condB = parse(exprB);
         
         ImpliesExpression implies = new ImpliesExpression((BooleanExpression)condA, (BooleanExpression)condB);
         return implies;
      }
      catch (ClassCastException e)
      {
         throw new RuntimeException("implies expressions must take two simple boolean expressions: " + expr);
      }      
   }
   
   private static Expression parseJava(String expr)
   {
      expr = expr.substring(JAVA.length());
      return new JavaExpression(expr);      
   }
   
   private static Expression parseBoolean(String expr)
   {
      if (expr.endsWith(";"))
      {
         expr = expr.substring(0, expr.length() - 1);
      }
      return new BooleanExpression(expr);      
   }
   
   public static void main(String[] args)
   {
      doit("a == b");
      doit("a == b implies b == a");
      doit("forall IEmployee e in getEmployees() | getRooms().contains(e.getOffice());");
      doit("exists IEmployee e in getEmployees() | !getRooms().contains(e.getOffice())");
      doit("forall IEmployee e1 in getEmployees() | forall IEmployee e2 in getEmployees() | (e1 != e2) implies e1.getOffice() != e2.getOffice()");
      doit("java: for (int i = 0){i > 0;}");
   }
   
   public static void doit(String str)
   {
      System.out.println(str);
      System.out.println();
      Expression expr = ExpressionParser.parseExpression(str);
      BeanshellGenerator gen = new BeanshellGenerator(expr);
      System.out.println(gen.createBeanshellCode());
      System.out.println("-----------------------");
   }
}
