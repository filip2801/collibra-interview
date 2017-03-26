package com.hexagon_software.collibra.interview.socket.handler;

import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.handler.exception.MessageHandlingNotSupported;
import org.apache.mina.core.session.IoSession;

public abstract class MatchedByPatternSessionAwareHandler implements SessionAwareMessageHandler {

    @Override
    public boolean isSupported(IoSession ioSession, Object message) {
        return message instanceof String && getPattern().matcher((String) message).matches();
    }

    @Override
    public void handle(IoSession session, Object message) {
        if (isSupported(session, message)) {
            handleMessage(session, (String) message);
        } else {
            throw new MessageHandlingNotSupported(message);
        }
    }

    protected abstract void handleMessage(IoSession session, String message);

    abstract Pattern getPattern();

}
