package com.hexagon_software.collibra.interview.socket.handler;

import java.util.Optional;
import java.util.Set;

import com.hexagon_software.collibra.interview.handler.MessageHandler;
import com.hexagon_software.collibra.interview.handler.Response;
import lombok.AllArgsConstructor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
class ReceivedMessageHandler {

    private final Set<MessageHandler> handlers;
    private final Set<SessionAwareMessageHandler> sessionAwareMessageHandlers;

    void handle(IoSession session, Object message) {
        Optional<MessageHandler> handler = findSupportedHandler(message);

        if (handler.isPresent()) {
            Response response = handler.get().handle(message);
            session.write(response.getValue());
        } else {
            Optional<SessionAwareMessageHandler> sessionAwareHandler = findSupportedSessionAwareHandler(session, message);
            if (sessionAwareHandler.isPresent()) {
                sessionAwareHandler.get().handle(session, message);
            } else {
                unsupportedMessage(session);
            }
        }
    }

    private Optional<MessageHandler> findSupportedHandler(Object message) {
        return handlers.stream()
                .filter(h -> h.isSupported(message))
                .findAny();
    }

    private Optional<SessionAwareMessageHandler> findSupportedSessionAwareHandler(IoSession session, Object message) {
        return sessionAwareMessageHandlers.stream()
                .filter(h -> h.isSupported(session, message))
                .findAny();
    }

    private void unsupportedMessage(IoSession session) {
        session.write("SORRY, I DIDN’T UNDERSTAND THAT");
    }

}
