package com.hexagon_software.collibra.interview.handler;

public interface MessageHandler {

    boolean isSupported(Object message);

    Response handle(Object message);

}
