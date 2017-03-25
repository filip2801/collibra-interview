package com.hexagon_software.collibra.interview.session;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SessionId sessionId = (SessionId) o;
        return Objects.equals(uuid, sessionId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

}
