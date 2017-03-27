package com.hexagon_software.collibra.interview.socket.handler;

import org.apache.mina.core.session.IoSession;

public interface SessionAwareMessageHandler {

    boolean isSupported(IoSession session, Object message);

    /**
     * Handle message. Method could throw unchecked exception if handling is not supported.
     *
     * @param session session
     * @param message message
     */
    void handle(IoSession session, Object message);

}
