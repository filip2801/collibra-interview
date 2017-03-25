package com.hexagon_software.collibra.interview.socket.handler;

import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.socket.writer.SessionEndingMessageWriter;
import lombok.AllArgsConstructor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SessionClosingRequestHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("BYE MATE!");

    private final SessionEndingMessageWriter sessionEndingMessageWriter;

    @Override
    protected void handleMessage(IoSession session, String message) {
        sessionEndingMessageWriter.write(session);
        session.closeOnFlush();
    }

    @Override
    Pattern getPattern() {
        return PATTERN;
    }

}
