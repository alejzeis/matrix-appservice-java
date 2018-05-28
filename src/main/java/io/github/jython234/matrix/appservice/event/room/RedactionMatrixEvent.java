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
package io.github.jython234.matrix.appservice.event.room;

import com.google.gson.annotations.SerializedName;
import io.github.jython234.matrix.appservice.event.room.RoomEvent;
import org.springframework.lang.Nullable;

/**
 * Represents an "m.room.redaction" Matrix event.
 *
 * @author jython234
 */
public class RedactionMatrixEvent extends RoomEvent {
    transient public static final String TYPE = "m.room.redaction";

    /**
     * The event ID of the event that was redacted.
     */
    @SerializedName("redacts")
    public String redactedEvent;

    /**
     * Contains the reason for the redaction, if there is one.
     * This may not exist.
     */
    @Nullable public Content content;

    public RedactionMatrixEvent() {
        this.type = TYPE;
    }

    /**
     * Represents the redaction event content.
     *
     * @author jython234
     */
    public static class Content {
        /**
         * The reason for the redaction, may be null.
         */
        @Nullable public String reason;
    }
}
