package io.github.jython234.matrix.appservice.event;

import org.json.simple.JSONObject;

/**
 * Represents a RawMatrixEvent that either doesn't have a
 * specific class implemented yet or it is unknown. The raw
 * JSON data is accessible via the <code>data</code> property.
 *
 * @author jython234
 */
public class RawMatrixEvent extends MatrixEvent {
    /**
     * The raw JSON data of this event
     */
    public JSONObject data;

    public RawMatrixEvent(JSONObject data) {
        this.data = data;
        this.type = (String) data.get("type");
    }
}
