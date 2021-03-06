/*
 * Copyright 2013 JBoss Inc
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
package org.overlord.sramp.repository.jcr.audit;

import org.overlord.sramp.common.Sramp;

/**
 * Implement this interface to create a handler specific to a kind of bundle of
 * JCR events.
 * @author eric.wittmann@redhat.com
 */
public interface AuditEventBundleHandler {

    /**
     * Called to handle the bundle of events.  This means creating an "audit:auditEntry"
     * node under an artifact node, for example.
     * @param sramp
     * @param eventBundle
     */
    public void handle(Sramp sramp, AuditEventBundle eventBundle) throws Exception;

}
