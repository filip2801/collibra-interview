package com.hexagon_software.collibra.interview.socket.handler;

import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.socket.writer.SessionClosingMessageWriter;
import lombok.AllArgsConstructor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SessionClosingRequestHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("BYE MATE!");

    private final SessionClosingMessageWriter sessionClosingMessageWriter;

    @Override
    protected void handleMessage(IoSession session, String message) {
        sessionClosingMessageWriter.write(session);
        session.closeOnFlush();
    }

    @Override
    Pattern getPattern() {
        return PATTERN;
    }

}
