package com.hexagon_software.collibra.interview.handler.exception;

public class MessageHandlingNotSupported extends RuntimeException {

    public MessageHandlingNotSupported(Object messageForHandling) {
        super("Cannot handle message: " + messageForHandling);
    }

}
