package io.github.jython234.matrix.appservice.event;


/**
 * Base class for a MatrixEvent. All other MatrixEvent classes
 * will extend from this.
 *
 * @author jython234
 */
public class MatrixEvent {
    protected String type;

    /**
     * Get the "type" field of this event.
     * @return The matrix type of this event.
     */
    public final String getType() {
        return this.type;
    }
}
