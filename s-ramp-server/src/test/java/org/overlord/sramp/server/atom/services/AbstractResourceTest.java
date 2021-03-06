/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.sramp.server.atom.services;

import org.junit.AfterClass;
import org.junit.Before;
import org.overlord.sramp.atom.providers.AuditEntryProvider;
import org.overlord.sramp.atom.providers.HttpResponseProvider;
import org.overlord.sramp.atom.providers.OntologyProvider;
import org.overlord.sramp.atom.providers.SrampAtomExceptionProvider;
import org.overlord.sramp.common.SrampConstants;
import org.overlord.sramp.common.test.resteasy.BaseResourceTest;
import org.overlord.sramp.repository.PersistenceFactory;
import org.overlord.sramp.repository.jcr.modeshape.AbstractNoAuditingJCRPersistenceTest;
import org.overlord.sramp.repository.jcr.modeshape.JCRRepositoryCleaner;

/**
 * Base class for s-ramp resource tests. Handles some of the setup boilerplate.
 *
 * @author eric.wittmann@redhat.com
 */
public abstract class AbstractResourceTest extends BaseResourceTest {

	public static void setUpResourceTest() throws Exception {
		// use the in-memory config for unit tests
        System.setProperty("sramp.modeshape.config.url", "classpath://" + AbstractNoAuditingJCRPersistenceTest.class.getName()
                + "/META-INF/modeshape-configs/junit-sramp-config.json");

		dispatcher.getRegistry().addPerRequestResource(ServiceDocumentResource.class);
		dispatcher.getRegistry().addPerRequestResource(ArtifactResource.class);
		dispatcher.getRegistry().addPerRequestResource(FeedResource.class);
		dispatcher.getRegistry().addPerRequestResource(QueryResource.class);
		dispatcher.getRegistry().addPerRequestResource(BatchResource.class);
        dispatcher.getRegistry().addPerRequestResource(OntologyResource.class);
        dispatcher.getRegistry().addPerRequestResource(AuditResource.class);

		deployment.getProviderFactory().registerProvider(SrampAtomExceptionProvider.class);
		deployment.getProviderFactory().registerProvider(HttpResponseProvider.class);
        deployment.getProviderFactory().registerProvider(OntologyProvider.class);
        deployment.getProviderFactory().registerProvider(AuditEntryProvider.class);
	}

	@Before
	public void cleanRepository() {
		new JCRRepositoryCleaner().clean();
	}

	@AfterClass
	public static void cleanup() {
		PersistenceFactory.newInstance().shutdown();
        System.clearProperty(SrampConstants.SRAMP_CONFIG_AUDITING);
	}

}
