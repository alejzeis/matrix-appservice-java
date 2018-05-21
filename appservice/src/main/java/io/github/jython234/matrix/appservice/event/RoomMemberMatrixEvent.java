package io.github.jython234.matrix.appservice.event;

import com.google.gson.annotations.SerializedName;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
    public String stateKey;

    /**
     * The Event content.
     */
    public Content content;

    /**
     * Represents the "content" field of the event.
     */
    public static class Content {
        /**
         * The user's avatar ID.
         */
        @Nullable public String avatarURL;
        /**
         * The user's display name.
         */
        @Nullable public String displayname;

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
            BAN;
        }
    }
}
