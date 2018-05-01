package io.github.jython234.matrix.appservice.event;

import org.json.simple.JSONObject;

/**
 * Base class for a MatrixEvent. All other MatrixEvent classes
 * will extend from this.
 *
 * @author jython234
 */
public abstract class MatrixEvent {
    /**
     * Encodes this MatrixEvent into a JSONObject
     * the homeserver can understand.
     *
     * @return The encoded JSONObject.
     */
    public abstract JSONObject encode();

    /**
     * Decodes this MatrixEvent from a JSONObject.
     * @param object The JSONObject that has the encoded JSON.
     */
    public abstract void decode(JSONObject object);
}
