package com.hexagon_software.collibra.interview.socket.handler;

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollibraIoHandler extends IoHandlerAdapter {

    @Autowired
    private InvitationMessageHandler invitationMessageHandler;

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);

        session.write("HI, I'M " + session.getAttribute(AttributeNames.SESSION_ID));
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

        String msg = (String) message;
        if (msg.startsWith("HI, I'M ")) {
            invitationMessageHandler.handle(session, message);
        } else {
            session.write("SORRY, I DIDN’T UNDERSTAND THAT");
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        session.write("BYE " + session.getAttribute(AttributeNames.CLIENT) + ", WE SPOKE FOR 30000 MS");
    }
}
