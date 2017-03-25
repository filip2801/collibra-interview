package com.hexagon_software.collibra.interview.socket.session;

import java.util.Objects;
import java.util.UUID;

public class SessionId {

    private final UUID uuid;

    public SessionId() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return uuid.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SessionId sessionId = (SessionId) object;
        return Objects.equals(uuid, sessionId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}
