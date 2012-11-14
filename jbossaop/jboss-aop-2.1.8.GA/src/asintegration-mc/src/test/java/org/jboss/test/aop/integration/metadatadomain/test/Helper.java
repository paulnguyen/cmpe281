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
package org.jboss.test.aop.integration.metadatadomain.test;

import org.jboss.aop.AspectManager;
import org.jboss.aop.Domain;
import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.plugins.repository.basic.BasicMetaDataRepository;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.MutableMetaData;
import org.jboss.metadata.spi.repository.MutableMetaDataRepository;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.stack.MetaDataStack;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision: 1.1 $
 */
public class Helper
{

   static Domain createScopedDomain(String name)
   {
      AspectManager manager = AspectManager.getTopLevelAspectManager();
      Domain scopedDomain = new Domain(manager, name, false);
      return scopedDomain;
   }

   static void createAndPushMetaData(Domain scopedDomain)
   {
      //Deployer creates Domain depending on classloader rules, and adds it to metadata for deployment
      ScopeKey scopeKey = createScope("Test", scopedDomain.getDomainName());
      MutableMetaDataRepository repository = new BasicMetaDataRepository();
      MetaDataRetrieval retrieval = new MemoryMetaDataLoader(scopeKey);
      repository.addMetaDataRetrieval(retrieval);
      
      if (scopedDomain != null)
      {
         ((MutableMetaData)retrieval).addMetaData(scopedDomain, Domain.class);
      }
      MetaData metadata = repository.getMetaData(scopeKey);
      MetaDataStack.push(metadata);
   }

   static ScopeKey createScope(String app, String dep)
   {
      ScopeKey result = ScopeKey.DEFAULT_SCOPE.clone();
      result.addScope(CommonLevels.APPLICATION, app);
      result.addScope(CommonLevels.DEPLOYMENT, dep);
      return result;
   }

}
