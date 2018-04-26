/*
 * Copyright © 2018, jython234
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.jython234.matrix.appservice.registration;

import java.util.List;

/**
 * Represents a matrix appservice registration,
 * a YAML configuration file that identifies an appservice
 * to the homeserver.
 *
 * @author jython234
 */
public class Registration {
    /**
     * The unique ID of the appservice.
     */
    protected String id;
    /**
     * The URL to which the homeserver will send the appservice requests.
     */
    protected String url;
    /**
     * The secret token used by the appservice for requests to the homserver.
     */
    protected String asToken;
    /**
     * The secret token used by the homeserver for requests to the appservice.
     */
    protected String hsToken;
    /**
     * The matrix localpart of the appservice bot user.
     */
    protected String senderLocalpart;

    /**
     * The namespaces that belong to the appservice.
     */
    protected Namespaces namespaces;


    /**
     * Represents user, rooms and aliases namespaces that belong to the appservice.
     *
     * @author jython234
     */
    public static class Namespaces {
        public List<UserNamespace> users;
        public List<AliasNamespace> aliases;
        public String[] rooms;
    }

    /**
     * Represents a unique namespace of users on the matrix server
     * that belong to this appservice. The namespace may or may
     * not be exclusive.
     *
     * @author jython234
     */
    public static class UserNamespace {
        public boolean exclusive;
        public String regex;
    }

    /**
     * Represents a unique namespace of aliases on the matrix server
     * that belong to this appservice. The namespace may or may
     * not be exclusive.
     *
     * @author jython234
     */
    public static class AliasNamespace extends UserNamespace {
    }

    /**
     * Get the appservice ID.
     * @return The appservice ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the appservice's base URL.
     * @return The URL that requests to the appservice should go to.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Get the appservice token.
     * @return The appservice token.
     */
    public String getAsToken() {
        return this.asToken;
    }

    /**
     * Get the homeserver's token.
     * @return The homeserver's token.
     */
    public String getHsToken() {
        return this.hsToken;
    }

    /**
     * Get the appservice's localpart.
     * @return The appservice's bot localpart.
     */
    public String getSenderLocalpart() {
        return this.senderLocalpart;
    }

    /**
     * Get the appservice's namespaces.
     * @return The appservice's namespaces.
     */
    public Namespaces getNamespaces() {
        return this.namespaces;
    }
}
