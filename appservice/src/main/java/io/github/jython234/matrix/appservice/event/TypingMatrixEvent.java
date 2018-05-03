package io.github.jython234.matrix.appservice.event;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the "m.typing" matrix event.
 *
 * @author jython234
 */
public class TypingMatrixEvent extends MatrixEvent {
    transient public static final String TYPE = "m.typing";

    /**
     * The room id of this event.
     */
    @SerializedName("room_id")
    public String roomId;

    /**
     * Event content
     */
    public Content content;

    public TypingMatrixEvent() {
        this.type = TYPE;
    }

    public static class Content {
        /**
         * List of userIds that are typing
         */
        @SerializedName("user_ids")
        public String[] userIds;
    }
}
