package io.github.jython234.matrix.appservice.event;

import io.github.jython234.matrix.appservice.event.MatrixEvent;
import org.json.simple.JSONObject;

/**
 * Represents a Raw Matrix Event, one that simply stores the whole event
 * data in a JSON object.
 *
 * @author jython234
 */
public class RawMatrixEvent extends MatrixEvent {
    /**
     * Represents the raw JSON data of the event.
     */
    public JSONObject data;

    @Override
    public JSONObject encode() {
        return this.data;
    }

    @Override
    public void decode(JSONObject object) {
        this.data = object;
    }
}
