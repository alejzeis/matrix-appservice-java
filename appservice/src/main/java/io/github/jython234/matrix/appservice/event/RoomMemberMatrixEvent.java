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

import com.google.gson.annotations.SerializedName;
import org.springframework.lang.NonNull;

/**
 * Represents an "m.room.member" Matrix event.
 */
public class RoomMemberMatrixEvent extends MatrixEvent {
    transient public static final String TYPE = "m.room.member";

    /**
     * The Matrix Room ID of the room this event was in.
     */
    @SerializedName("room_id")
    public String roomId;

    /**
     * The sender of the message.
     */
    public String sender;

    @SerializedName("event_id")
    public String eventId;

    @SerializedName("state_key")
    @NonNull public String stateKey;

    /**
     * The Event content.
     */
    @NonNull public Content content;

    /**
     * Represents the "content" field of the event.
     */
    public static class Content {
        /**
         * The user's avatar ID.
         */
        public String avatarURL;
        /**
         * The user's display name.
         */
        public String displayname;

        @NonNull public Membership membership;

        public boolean isDirect;

        public enum Membership {
            /**
             * An event where someone is being invited to a room. In this case
             * the {@link RoomMemberMatrixEvent#sender} is the room member and
             * {@link RoomMemberMatrixEvent#stateKey} is the user being invited.
             */
            @SerializedName("invite")
            INVITE,
            /**
             * An event where someone is joining a room.
             * {@link RoomMemberMatrixEvent#sender} is the person joining the room.
             */
            @SerializedName("join")
            JOIN,
            /*
             * TODO: figure out what this is
             */
            @SerializedName("knock")
            KNOCK,
            /**
             * An event where someone is leaving a room.
             * {@link RoomMemberMatrixEvent#sender} is the person leaving the room.
             */
            @SerializedName("leave")
            LEAVE,
            /**
             * An event where someone is being banned from a room.
             * {@link RoomMemberMatrixEvent#sender} is the person banning the user, who is
             * {@link RoomMemberMatrixEvent#stateKey}
             */
            @SerializedName("ban")
            BAN
        }
    }
}
