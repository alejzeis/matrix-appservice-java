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
