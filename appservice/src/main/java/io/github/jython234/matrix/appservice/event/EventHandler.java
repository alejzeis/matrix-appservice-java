package io.github.jython234.matrix.appservice.event;

/**
 * Represents a Handler class that will handle managing
 * events pushed by the appservice library.
 *
 * @author jython234
 */
public interface EventHandler {
    /**
     * Handles a MatrixEvent sent by the homeserver.
     * @param event The MatrixEvent to handle.
     */
    void onMatrixEvent(MatrixEvent event);

}