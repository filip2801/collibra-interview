package com.hexagon_software.collibra.interview.socket.handler;

import org.apache.mina.core.session.IoSession;

public interface SessionAwareMessageHandler {

    boolean isSupported(IoSession session, Object message);

    void handle(IoSession session, Object message);

}
