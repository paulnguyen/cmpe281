/*
* JBoss, Home of Professional Open Source.
* Copyright 2006, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors. 
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
package org.jboss.test.aop.annotationoverride;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.aop.Advised;
import org.jboss.aop.Advisor;
import org.jboss.aop.AspectManager;
import org.jboss.aop.ClassContainer;
import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.plugins.repository.basic.BasicMetaDataRepository;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.repository.MutableMetaDataRepository;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.signature.ConstructorSignature;
import org.jboss.metadata.spi.signature.FieldSignature;
import org.jboss.metadata.spi.signature.MethodSignature;
import org.jboss.metadata.spi.signature.Signature;
import org.jboss.test.aop.AOPTestWithSetup;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings({"unused"})
public class AnnotationOverrideTestCase extends AOPTestWithSetup
{
   public AnnotationOverrideTestCase(String arg0)
   {
      // FIXME AnnotationOverrideTestCase constructor
      super(arg0);
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("AnnotationOverrideTestCase");
      suite.addTestSuite(AnnotationOverrideTestCase.class);
      return suite;
   }

   public void testWovenClassNoMetaData() throws Exception
   {
      runTests(false);
   }
   
   public void testWovenClassWithMetaData() throws Exception
   {
      runTests(true);
   }
   
   public void testClassContainerProxiedClass() throws Exception
   {
      ClassContainer container = new ClassContainer("test", AspectManager.instance());
      container.setClass(Proxied.class);
      container.initializeClassContainer();
      
      Some some = container.resolveTypedAnnotation(Some.class);
      assertNotNull(some);
      some = (Some)container.resolveAnnotation(Some.class);
      assertNotNull(some);
      Other other = container.resolveTypedAnnotation(Other.class);
      assertNull(other);
      other = (Other)container.resolveAnnotation(Other.class);
      assertNull(other);
      
      Method m = Proxied.class.getDeclaredMethod("method");
      some = container.resolveTypedAnnotation(m, Some.class);
      assertNull(some);
      some = (Some)container.resolveAnnotation(m, Some.class);
      assertNull(some);
      other = container.resolveTypedAnnotation(m, Other.class);
      assertNotNull(other);
      other = (Other)container.resolveAnnotation(m, Other.class);
      assertNotNull(other);
      assertEquals("method", other.value());
   }

   private void runTests(boolean useMetaData) throws Exception
   {
      Woven woven = new Woven();
      Advisor advisor = ((Advised)woven)._getAdvisor();

      Method m = Woven.class.getDeclaredMethod("method"); 
      Field f = Woven.class.getDeclaredField("field");
      Constructor<Woven> c = Woven.class.getDeclaredConstructor();

      if (useMetaData)
      {
         Annotation[] classAnnotations = new Annotation[] {new SomeMDImpl()};
         Annotation[] ctorAnnotations = new Annotation[] {new OtherMDImpl("ctor")}; 
         Annotation[] methodAnnotations = new Annotation[] {new OtherMDImpl("method")};
         Annotation[] fieldAnnotations = new Annotation[] {new OtherMDImpl("field")};
         setupMetaData(advisor, c, m, f, classAnnotations, ctorAnnotations, methodAnnotations, fieldAnnotations);
      }
      
      Some some = advisor.resolveTypedAnnotation(Some.class);
      assertNotNull(some);
      some = (Some)advisor.resolveAnnotation(Some.class);
      assertNotNull(some);
      Other other = advisor.resolveTypedAnnotation(Other.class);
      assertNull(other);
      other = (Other)advisor.resolveAnnotation(Other.class);
      assertNull(other);
      
      SomeAnnotation someAnn = advisor.resolveTypedAnnotation(SomeAnnotation.class);
      assertNotNull(someAnn);
      someAnn = (SomeAnnotation)advisor.resolveAnnotation(SomeAnnotation.class);
      assertNotNull(someAnn);
      OtherAnnotation otherAnn = advisor.resolveTypedAnnotation(OtherAnnotation.class);
      assertNull(otherAnn);
      otherAnn = (OtherAnnotation)advisor.resolveAnnotation(OtherAnnotation.class);
      assertNull(otherAnn);
      
      if (useMetaData)
      {
         SomeMD someMD = advisor.resolveTypedAnnotation(SomeMD.class);
         assertNotNull(someMD);
         someMD = (SomeMD)advisor.resolveAnnotation(SomeMD.class);
         assertNotNull(someMD);
         OtherMD otherMD = advisor.resolveTypedAnnotation(OtherMD.class);
         assertNull(otherMD);
         otherMD = (OtherMD)advisor.resolveAnnotation(OtherMD.class);
         assertNull(otherMD);
      }
      
      some = advisor.resolveTypedAnnotation(m, Some.class);
      assertNull(some);
      some = (Some)advisor.resolveAnnotation(m, Some.class);
      assertNull(some);
      other = advisor.resolveTypedAnnotation(m, Other.class);
      assertNotNull(other);
      other = (Other)advisor.resolveAnnotation(m, Other.class);
      assertNotNull(other);
      assertEquals("method", other.value());
      
      someAnn = advisor.resolveTypedAnnotation(m, SomeAnnotation.class);
      assertNull(some);
      someAnn = (SomeAnnotation)advisor.resolveAnnotation(m, SomeAnnotation.class);
      assertNull(some);
      otherAnn = advisor.resolveTypedAnnotation(m, OtherAnnotation.class);
      assertNotNull(otherAnn);
      otherAnn = (OtherAnnotation)advisor.resolveAnnotation(m, OtherAnnotation.class);
      assertNotNull(otherAnn);
      assertEquals("method", otherAnn.value());
      
      if (useMetaData)
      {
         SomeMD someMD = advisor.resolveTypedAnnotation(m, SomeMD.class);
         assertNull(someMD);
         someMD = (SomeMD)advisor.resolveAnnotation(m, SomeMD.class);
         assertNull(someMD);
         OtherMD otherMD = advisor.resolveTypedAnnotation(m, OtherMD.class);
         assertNotNull(otherMD);
         otherMD = (OtherMD)advisor.resolveAnnotation(m, OtherMD.class);
         assertNotNull(otherMD);
         assertEquals("method", otherMD.value());
      }
      
      some = advisor.resolveTypedAnnotation(f, Some.class);
      assertNull(some);
      some = (Some)advisor.resolveAnnotation(f, Some.class);
      assertNull(some);
      other = advisor.resolveTypedAnnotation(f, Other.class);
      assertNotNull(other);
      other = (Other)advisor.resolveAnnotation(f, Other.class);
      assertNotNull(other);
      assertEquals("field", other.value());
      
      someAnn = advisor.resolveTypedAnnotation(f, SomeAnnotation.class);
      assertNull(some);
      someAnn = (SomeAnnotation)advisor.resolveAnnotation(f, SomeAnnotation.class);
      assertNull(some);
      otherAnn = advisor.resolveTypedAnnotation(f, OtherAnnotation.class);
      assertNotNull(other);
      otherAnn = (OtherAnnotation)advisor.resolveAnnotation(f, OtherAnnotation.class);
      assertNotNull(other);
      assertEquals("field", other.value());
      
      if (useMetaData)
      {
         SomeMD someMD = advisor.resolveTypedAnnotation(f, SomeMD.class);
         assertNull(someMD);
         someMD = (SomeMD)advisor.resolveAnnotation(f, SomeMD.class);
         assertNull(someMD);
         OtherMD otherMD = advisor.resolveTypedAnnotation(f, OtherMD.class);
         assertNotNull(otherMD);
         otherMD = (OtherMD)advisor.resolveAnnotation(f, OtherMD.class);
         assertNotNull(otherMD);
         assertEquals("field", otherMD.value());
      }
      
      some = advisor.resolveTypedAnnotation(c, Some.class);
      assertNull(some);
      some = (Some)advisor.resolveAnnotation(c, Some.class);
      assertNull(some);
      other = advisor.resolveTypedAnnotation(c, Other.class);
      assertNotNull(other);
      other = (Other)advisor.resolveAnnotation(c, Other.class);
      assertNotNull(other);
      assertEquals("ctor", other.value());

      someAnn = advisor.resolveTypedAnnotation(c, SomeAnnotation.class);
      assertNull(some);
      someAnn = (SomeAnnotation)advisor.resolveAnnotation(c, SomeAnnotation.class);
      assertNull(some);
      otherAnn = advisor.resolveTypedAnnotation(c, OtherAnnotation.class);
      assertNotNull(other);
      otherAnn = (OtherAnnotation)advisor.resolveAnnotation(c, OtherAnnotation.class);
      assertNotNull(other);
      assertEquals("ctor", other.value());
      
      if (useMetaData)
      {
         SomeMD someMD = advisor.resolveTypedAnnotation(c, SomeMD.class);
         assertNull(someMD);
         someMD = (SomeMD)advisor.resolveAnnotation(c, SomeMD.class);
         assertNull(someMD);
         OtherMD otherMD = advisor.resolveTypedAnnotation(c, OtherMD.class);
         assertNotNull(otherMD);
         otherMD = (OtherMD)advisor.resolveAnnotation(c, OtherMD.class);
         assertNotNull(otherMD);
         assertEquals("ctor", otherMD.value());
      }
      
      assertTrue(advisor.hasAnnotation(SomeAnnotation.class.getName()));
      assertTrue(advisor.hasAnnotation(Woven.class, SomeAnnotation.class.getName()));
      assertTrue(advisor.hasAnnotation(Woven.class, SomeAnnotation.class));
      
      assertTrue(advisor.hasAnnotation(Some.class.getName()));
      assertTrue(advisor.hasAnnotation(Woven.class, Some.class.getName()));
      assertTrue(advisor.hasAnnotation(Woven.class, Some.class));
      
      assertFalse(advisor.hasAnnotation(Other.class.getName()));
      assertFalse(advisor.hasAnnotation(Woven.class, Other.class.getName()));
      assertFalse(advisor.hasAnnotation(Woven.class, Other.class));
      
      assertTrue(advisor.hasAnnotation(c, Other.class.getName()));
      assertFalse(advisor.hasAnnotation(c, Some.class.getName()));
      assertFalse(advisor.hasAnnotation(c, SomeAnnotation.class.getName()));
      
      assertTrue(advisor.hasAnnotation(f, Other.class.getName()));
      assertFalse(advisor.hasAnnotation(f, Some.class.getName()));
      assertFalse(advisor.hasAnnotation(f, SomeAnnotation.class.getName()));
      
      assertTrue(advisor.hasAnnotation(m, Other.class.getName()));
      assertTrue(advisor.hasAnnotation(m, Other.class));
      assertFalse(advisor.hasAnnotation(m, Some.class.getName()));
      assertFalse(advisor.hasAnnotation(m, Some.class));
      assertFalse(advisor.hasAnnotation(m, SomeAnnotation.class.getName()));
      assertFalse(advisor.hasAnnotation(m, SomeAnnotation.class));
   }
   
   private void setupMetaData(Advisor advisor, 
         Constructor<?> c, 
         Method m, 
         Field f, 
         Annotation[] classAnnotations,
         Annotation[] ctorAnnotations, 
         Annotation[] methodAnnotations, 
         Annotation[] fieldAnnotations)
   {
      MutableMetaDataRepository repository = new BasicMetaDataRepository();
      
      ScopeKey scopeKey = ScopeKey.DEFAULT_SCOPE.clone();
      scopeKey.addScope(CommonLevels.INSTANCE, "Test");
      scopeKey.addScope(CommonLevels.CLASS, advisor.getClazz().getName());
      scopeKey.addScope(CommonLevels.WORK, String.valueOf(hashCode()));
      ScopeKey key = scopeKey;
      ScopeKey mutableScope = new ScopeKey(CommonLevels.INSTANCE, "Test".toString());
      MemoryMetaDataLoader mutable = new MemoryMetaDataLoader(mutableScope);
      repository.addMetaDataRetrieval(mutable);
      addClassAnnotations(advisor.getClazz(), mutable, classAnnotations);
      addMethodAnnotations(m, mutable, methodAnnotations);
      addFieldAnnotations(f, mutable, fieldAnnotations);
      addConstructorAnnotations(c, mutable, ctorAnnotations);
      
      MetaData metadata = new MetaDataRetrievalToMetaDataBridge(mutable); 
      
      advisor.setMetadata(metadata);
   }
   
   private void addClassAnnotations(Class<?> clazz, MemoryMetaDataLoader mutable, Annotation[] extraAnnotations)
   {
      Annotation[] anns = clazz.getAnnotations();
      for (Annotation ann : anns)
      {
         mutable.addAnnotation(ann);
      }
      for (Annotation ann : extraAnnotations)
      {
         mutable.addAnnotation(ann);
      }
   }
   
   private void addMethodAnnotations(Method m, MemoryMetaDataLoader mutable, Annotation[] extraAnnotations)
   {
      addJoinpointAnnotations(mutable, new MethodSignature(m), m.getName(), m.getAnnotations(), extraAnnotations);
   }
   
   private void addFieldAnnotations(Field f, MemoryMetaDataLoader mutable, Annotation[] extraAnnotations)
   {
      addJoinpointAnnotations(mutable, new FieldSignature(f), f.getName(), f.getAnnotations(), extraAnnotations);
   }
   
   private void addConstructorAnnotations(Constructor<?> c, MemoryMetaDataLoader mutable, Annotation[] extraAnnotations)
   {
      addJoinpointAnnotations(mutable, new ConstructorSignature(c), c.getName(), c.getAnnotations(), extraAnnotations);
   }
   
   private void addJoinpointAnnotations(MemoryMetaDataLoader mutable, Signature sig, String name, Annotation[] annotations, Annotation[] extraAnnotations)
   {
      ScopeKey scope = new ScopeKey(CommonLevels.JOINPOINT_OVERRIDE, name);
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader(scope);
      for (Annotation ann : annotations)
      {
         loader.addAnnotation(ann);
      }
      for (Annotation ann : extraAnnotations)
      {
         loader.addAnnotation(ann);
      }
      mutable.addComponentMetaDataRetrieval(sig, loader);
   }
}
