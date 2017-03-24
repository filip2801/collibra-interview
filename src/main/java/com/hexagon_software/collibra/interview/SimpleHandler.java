package com.hexagon_software.collibra.interview;

import java.util.UUID;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class SimpleHandler extends IoHandlerAdapter {

    public static final String ID = "ID";

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        System.out.println("Session created");

        UUID uuid = UUID.randomUUID();

        session.write("HI, I'M " + uuid);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);

        String msg = (String) message;
        if(msg.startsWith("HI, I'M ")) {
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
        session.write("BYE "+ session.getAttribute("NAME")+", WE SPOKE FOR 30000 MS");
    }
}
