package com.hexagon_software.collibra.interview.socket.handler;

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames;
import com.hexagon_software.collibra.interview.socket.writer.SessionClosingMessageWriter;
import lombok.AllArgsConstructor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CollibraIoHandler extends IoHandlerAdapter {

    private final ReceivedMessageHandler receivedMessageHandler;
    private final SessionClosingMessageWriter sessionClosingWriter;

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        session.write("HI, I'M " + session.getAttribute(AttributeNames.SESSION_ID));
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

        receivedMessageHandler.handle(session, message);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);

        sessionClosingWriter.write(session);
    }

}
