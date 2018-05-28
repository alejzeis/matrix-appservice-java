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
package io.github.jython234.matrix.appservice.event;

import io.github.jython234.matrix.appservice.network.CreateRoomRequest;
import io.github.jython234.matrix.appservice.network.CreateUserRequest;

/**
 * Represents a Handler class that will handle managing
 * events pushed by the appservice library.
 *
 * @author jython234
 */
public interface EventHandler {
    /**
     * Handles a MatrixEvent sent by the homeserver.
     * @param event The MatrixEvent to handle.
     */
    void onMatrixEvent(MatrixEvent event);

    /**
     * Called when the homeserver queries the appservice
     * for a room alias. This will then be called to determine if the room
     * should be created or not.
     *
     * Once created if true, the {@link #onRoomAliasCreated(String)} method will be called.
     *
     * @param alias The room alias being queried
     * @return A {@link CreateRoomRequest} instance with options for the room if it to be created, or <code>null</code> if not.
     *          The library will handle creation of the room if not null.
     */
    CreateRoomRequest onRoomAliasQueried(String alias);

    /**
     * Called when the appservice creates a room
     * in response to the {@link #onRoomAliasQueried(String)} method.
     *
     * @param alias The room alias that was created.
     */
    default void onRoomAliasCreated(String alias) { }

    /**
     * Called when the homeserver queries the appservice for a user.
     * This will be called to determine if the user should be provisioned or not.
     *
     * @param alias The Matrix User's ID
     * @return A {@link CreateUserRequest} instance with parameters for the new user if it is to be created, or <code>null</code> if not.
     *          The library will handle provisioning the user if not null.
     */
    CreateUserRequest onUserAliasQueried(String alias);

    /**
     * Called when the appservice provisions a user in response
     * to the {@link #onUserAliasQueried(String)} method.
     *
     * @param localpart The Matrix User's localpart.
     */
    default void onUserProvisioned(String localpart) { }

}