package com.hexagon_software.collibra.interview.socket.writer;

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames;
import com.hexagon_software.collibra.interview.socket.attribute.Client;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@Component
public class SessionEndingMessageWriter {

    public void write(IoSession session) {
        long sessionTime = System.currentTimeMillis() - session.getCreationTime();
        Client client = (Client) session.getAttribute(AttributeNames.CLIENT);

        session.write("BYE " + client + ", WE SPOKE FOR " + sessionTime + " MS");
    }

}
