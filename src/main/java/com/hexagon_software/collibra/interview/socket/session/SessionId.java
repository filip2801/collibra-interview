package com.hexagon_software.collibra.interview.socket.session;

import java.util.UUID;

import lombok.Value;

@Value
public class SessionId {

    private final UUID uuid;

    public SessionId() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return uuid.toString();
    }

}
