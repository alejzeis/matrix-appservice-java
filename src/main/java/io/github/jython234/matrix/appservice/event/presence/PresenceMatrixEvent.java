package io.github.jython234.matrix.appservice.event.presence;

import com.google.gson.annotations.SerializedName;
import io.github.jython234.matrix.appservice.event.MatrixEvent;

/**
 * Represents an update of a Matrix user's presence.
 *
 * "m.presence" event.
 *
 * @author jython234
 */
public class PresenceMatrixEvent extends MatrixEvent {
    transient public static final String TYPE = "m.presence";

    public Content content;

    public static class Content {
        /**
         * The MXC URL of the user's Avatar.
         */
        @SerializedName("avatar_url")
        public String avatarURL;

        /**
         * If the user is currently active
         */
        @SerializedName("currently_active")
        public boolean currentlyActive;
        /**
         * How long ago in milliseconds the user was active.
         */
        @SerializedName("last_active_ago")
        public long lastActiveAgo;
        /**
         * The current presence of the user.
         */
        public Presence presence;
        /**
         * The matrix user ID of the user.
         */
        @SerializedName("user_id")
        public String userId;
    }
}
