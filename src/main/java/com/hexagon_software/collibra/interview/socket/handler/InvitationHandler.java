package com.hexagon_software.collibra.interview.socket.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames;
import com.hexagon_software.collibra.interview.socket.attribute.Client;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@Component
public class InvitationHandler extends MatchedByPatternSessionAwareHandler {

    private static final Pattern PATTERN = Pattern.compile("^HI, I'M ((\\w|-)+)$");

    @Override
    protected void handleMessage(IoSession session, String message) {
        Client client = getClient(message);

        session.setAttribute(AttributeNames.CLIENT, client);
        session.write("HI " + client);
    }

    @Override
    public Pattern getPattern() {
        return PATTERN;
    }

    private Client getClient(String message) {
        Matcher matcher = PATTERN.matcher(message);
        matcher.find();

        return new Client(matcher.group(1));
    }

}
