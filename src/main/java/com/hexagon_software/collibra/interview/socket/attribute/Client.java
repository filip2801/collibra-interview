package com.hexagon_software.collibra.interview.socket.attribute;

import lombok.NonNull;
import lombok.Value;

@Value
public class Client {

    @NonNull
    private final String name;

    @Override
    public String toString() {
        return name;
    }

}
