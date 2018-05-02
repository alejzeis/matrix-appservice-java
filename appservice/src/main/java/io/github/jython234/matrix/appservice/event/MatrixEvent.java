package io.github.jython234.matrix.appservice.event;

import org.json.simple.JSONObject;

/**
 * Base class for a MatrixEvent. All other MatrixEvent classes
 * will extend from this.
 *
 * @author jython234
 */
public class MatrixEvent {
    public String type;

    /**
     * Encodes this MatrixEvent into a JSONObject
     * the homeserver can understand.
     *
     * @return The encoded JSONObject.
     */
    public JSONObject encode() {
        JSONObject root = new JSONObject();
        root.put("type", this.type);
        return root;
    }

    /**
     * Decodes this MatrixEvent from a JSONObject.
     * @param object The JSONObject that has the encoded JSON.
     */
    public void decode(JSONObject object) {
        this.type = (String) object.get("type");
    }
}
