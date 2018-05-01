package io.github.jython234.matrix.appservice;

import io.github.jython234.matrix.appservice.MatrixAppservice;
import io.github.jython234.matrix.appservice.event.EventHandler;
import io.github.jython234.matrix.appservice.event.MatrixEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Internal EventManager that manages pushing out events
 * to the various Handlers.
 *
 * @author jython234
 */
public class EventManager {
    private final List<EventHandler> handlers = new CopyOnWriteArrayList<>();
    private MatrixAppservice appservice;

    public EventManager(MatrixAppservice appservice) {
        this.appservice = appservice;
    }

    /**
     * Register an EventHandler with the Manager. New events
     * will then be sent to this Handler, in addition to the
     * ones already registered.
     *
     * @param handler The EventHandler to be registered.
     */
    public void registerHandler(EventHandler handler) {
        this.handlers.add(handler);
    }

    /**
     * Remove an EventHandler from the Manager. It will no longer
     * then receive events.
     * @param handler The EventHandler to be removed.
     */
    public void deregisterHandler(EventHandler handler) {
        this.handlers.remove(handler);
    }

    /**
     * Process a MatrixEvent and send it out to all the other Handlers.
     * @param event The event to be processed.
     */
    protected void processMatrixEvent(MatrixEvent event) {
        synchronized (this.handlers) {
            this.handlers.forEach((EventHandler e) -> e.onMatrixEvent(event));
        }
    }
}
