package com.hexagon_software.collibra.interview.socket.session;

import com.hexagon_software.collibra.interview.socket.handler.CollibraIoHandler;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

public class SessionIdFilter extends IoFilterAdapter {

    @Override
    public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
        super.sessionCreated(nextFilter, session);

        session.setAttribute(CollibraIoHandler.SESSION_ID, new SessionId());
    }

}
