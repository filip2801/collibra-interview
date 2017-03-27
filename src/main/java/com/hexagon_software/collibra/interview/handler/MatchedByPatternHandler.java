package com.hexagon_software.collibra.interview.handler;

import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.handler.exception.MessageHandlingNotSupported;

public abstract class MatchedByPatternHandler implements MessageHandler {

    @Override
    public boolean isSupported(Object message) {
        return message instanceof String && getPattern().matcher((String) message).matches();
    }

    @Override
    public Response handle(Object message) {
        if (isSupported(message)) {
            return handleMessage((String) message);
        } else {
            throw new MessageHandlingNotSupported(message);
        }
    }

    protected abstract Response handleMessage(String message);

    abstract Pattern getPattern();

    protected Response response(String response) {
        return new Response(response);
    }

}
