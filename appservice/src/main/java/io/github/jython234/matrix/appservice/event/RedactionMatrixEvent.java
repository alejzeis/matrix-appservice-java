package io.github.jython234.matrix.appservice.event;

import com.google.gson.annotations.SerializedName;
import org.springframework.lang.Nullable;

/**
 * Represents an "m.room.redaction" Matrix event.
 *
 * @author jython234
 */
public class RedactionMatrixEvent extends MatrixEvent {
    transient public static final String TYPE = "m.room.redaction";

    /**
     * The Matrix Room ID of the room this redaction was in.
     */
    @SerializedName("room_id")
    public String roomId;

    /**
     * The user who redacted the event.
     */
    public String sender;

    /**
     * This is NOT the ID of the event that was redacted!
     */
    @SerializedName("event_id")
    public String eventId;

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
