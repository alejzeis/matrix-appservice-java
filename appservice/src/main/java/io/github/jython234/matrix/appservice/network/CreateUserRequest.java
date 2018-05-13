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
package io.github.jython234.matrix.appservice.network;

import com.google.gson.annotations.SerializedName;
import org.springframework.lang.Nullable;

/**
 * Represents a request to provision a new matrix user on the
 * homeserver. This is used when the homeserver sends a user query request
 * to the appservice.
 *
 * @author jython234
 */
public final class CreateUserRequest {
    /**
     * The Matrix User's localpart, ex. "fakeuser" would be "@fakeuser:fakeserver.com"
     *
     * @see io.github.jython234.matrix.appservice.Util#getLocalpart(String)
     */
    public String localpart;
    /**
     * The Matrix User's display name which is seen by other users in
     * rooms.
     */
    public String displayname;
    /**
     * An MXC URL of an image to set this user's profile picture to. It may be null, and
     * in that case a profile picture will not be set.
     */
    @Nullable public String avatarUrl;

    static class CreateUserRequestJSON {
        public String type = "m.login.application_service";
        public String username;

        CreateUserRequestJSON(String username) {
            this.username = username;
        }
    }

    static class CreateUserResponseJSON {
        @SerializedName("user_id")
        public String userId;
        // Extra not needed
    }
}
