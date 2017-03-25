package com.hexagon_software.collibra.interview.adapter;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@Component
public class CollibraIoHandler extends IoHandlerAdapter {

    public static final String SESSION_ID = "SESSION_ID";

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);

        session.write("HI, I'M " + session.getAttribute(SESSION_ID));
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

        String msg = (String) message;
        if (msg.startsWith("HI, I'M ")) {
            String name = msg.substring(8);

            session.setAttribute("NAME", name);

            session.write("HI " + name);
        } else {
            session.write("SORRY, I DIDN’T UNDERSTAND THAT");
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        session.write("BYE " + session.getAttribute("NAME") + ", WE SPOKE FOR 30000 MS");
    }
}
