package com.hexagon_software.collibra.interview.socket.handler;

public class MessageHandlingNotSupported extends RuntimeException {

    public MessageHandlingNotSupported(Object messageForHandling) {
        super("Cannot handle message: " + messageForHandling);
    }

}
