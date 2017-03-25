package com.hexagon_software.collibra.interview.socket.attribute;

public class Client {

    private final String name;

    public Client(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
