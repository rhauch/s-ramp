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
package org.overlord.sramp.atom.providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.jboss.downloads.overlord.sramp._2013.auditing.AuditEntry;
import org.jboss.resteasy.plugins.providers.jaxb.JAXBMarshalException;
import org.jboss.resteasy.plugins.providers.jaxb.JAXBUnmarshalException;

/**
 * A RESTEasy provider for reading/writing an S-RAMP ontology. S-RAMP ontologies are defined using a sub-set
 * of the OWL Lite specification, which in turn builds on AuditEntry.
 *
 * @author eric.wittmann@redhat.com
 */
@Provider
@Produces("application/auditEntry+xml")
@Consumes("application/auditEntry+xml")
public class AuditEntryProvider implements MessageBodyReader<AuditEntry>, MessageBodyWriter<AuditEntry> {

	private static JAXBContext auditEntryContext;
	{
		try {
			auditEntryContext = JAXBContext.newInstance(AuditEntry.class);
		} catch (JAXBException e) {
			auditEntryContext = null;
		}
	}

	/**
	 * Constructor.
	 */
	public AuditEntryProvider() {
	}

	/**
	 * @see javax.ws.rs.ext.MessageBodyReader#isReadable(java.lang.Class, java.lang.reflect.Type,
	 *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return AuditEntry.class.isAssignableFrom(type)
				|| org.overlord.sramp.atom.MediaType.APPLICATION_AUDIT_ENTRY_XML_TYPE.equals(mediaType);
	}

	/**
	 * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class, java.lang.reflect.Type,
	 *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return AuditEntry.class.isAssignableFrom(type)
				|| org.overlord.sramp.atom.MediaType.APPLICATION_AUDIT_ENTRY_XML_TYPE.equals(mediaType);
	}

	/**
	 * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object, java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType)
	 */
	@Override
	public long getSize(AuditEntry t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	/**
	 * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object, java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType,
	 *      javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
	 */
	@Override
	public void writeTo(AuditEntry t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		if (auditEntryContext == null)
			throw new JAXBMarshalException("Unable to marshal: " + mediaType, new NullPointerException("Failed to create the audit entry JAXB context."));
		try {
			auditEntryContext.createMarshaller().marshal(t, entityStream);
		} catch (JAXBException e) {
			throw new JAXBMarshalException("Unable to marshal: " + mediaType, e);
		}
	}

	/**
	 * @see javax.ws.rs.ext.MessageBodyReader#readFrom(java.lang.Class, java.lang.reflect.Type,
	 *      java.lang.annotation.Annotation[], javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap,
	 *      java.io.InputStream)
	 */
	@Override
	public AuditEntry readFrom(Class<AuditEntry> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException,
			WebApplicationException {
		if (auditEntryContext == null)
			throw new JAXBUnmarshalException("Unable to marshal: " + mediaType, new NullPointerException("Failed to create the audit entry JAXB context."));
		try {
			AuditEntry entry = (AuditEntry) auditEntryContext.createUnmarshaller().unmarshal(entityStream);
			return entry;
		} catch (JAXBException e) {
			throw new JAXBUnmarshalException("Unable to unmarshal: " + mediaType, e);
		}
	}

}
