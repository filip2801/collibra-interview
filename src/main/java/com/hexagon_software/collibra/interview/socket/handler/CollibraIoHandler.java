package com.hexagon_software.collibra.interview.socket.handler;

import java.util.Optional;
import java.util.Set;

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames;
import com.hexagon_software.collibra.interview.socket.attribute.Client;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@Component
public class CollibraIoHandler extends IoHandlerAdapter {

    private final Set<MessageHandler> handlers;

    public CollibraIoHandler(Set<MessageHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        session.write("HI, I'M " + session.getAttribute(AttributeNames.SESSION_ID));
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

        Optional<MessageHandler> handler = handlers.stream()
                .filter(h -> h.isSupported(session, message))
                .findAny();

        if (handler.isPresent()) {
            handler.get().handle(session, message);
        } else {
            unsupportedMessage(session);
        }
    }

    private void unsupportedMessage(IoSession session) {
        session.write("SORRY, I DIDN’T UNDERSTAND THAT");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);

        long sessionTime = System.currentTimeMillis() - session.getCreationTime();
        Client client = (Client) session.getAttribute(AttributeNames.CLIENT);
        session.write("BYE " + client + ", WE SPOKE FOR " + sessionTime + " MS");
    }

}
