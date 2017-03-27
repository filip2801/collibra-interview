package com.hexagon_software.collibra.interview.handler;

public interface MessageHandler {

    boolean isSupported(Object message);

    /**
     * Handle message. Method could throw unchecked exception if handling is not supported.
     *
     * @param message message
     * @return response message
     */
    Response handle(Object message);

}
