package io.github.jython234.matrix.appservice.event.message;

import com.google.gson.annotations.SerializedName;
import io.github.jython234.matrix.appservice.event.MatrixEvent;

/**
 * Represents an "m.message" MatrixEvent. This event is complex,
 * as it can represent different types of media as it's message.
 *
 * @author jython234
 */
public class MessageMatrixEvent extends MatrixEvent {
    transient public static final String TYPE = "m.message";

    /**
     * The Matrix Room ID of the room this message was in
     */
    @SerializedName("room_id")
    public String roomId;

    /**
     * The sender of the message
     */
    public String sender;

    @SerializedName("event_id")
    public String eventId;

    /**
     * The Message content.
     */
    public MessageContent content;

    public MessageMatrixEvent() {
        this.type = TYPE;
    }
}
