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

    @SerializedName("room_id")
    public String roomId;

    public String sender;

    @SerializedName("event_id")
    public String eventId;

    public MessageContent content;

    public MessageMatrixEvent() {
        this.type = TYPE;
    }
}
