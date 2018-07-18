package io.github.jython234.matrix.appservice.event.presence;

import com.google.gson.annotations.SerializedName;

/**
 * Represent's a User's presence on Matrix.
 *
 * @author jython234
 */
public enum Presence {
    @SerializedName("online")
    ONLINE,
    @SerializedName("offline")
    OFFLINE,
    @SerializedName("unavailable")
    UNAVAILABLE
}
