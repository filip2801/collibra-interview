package com.hexagon_software.collibra.interview.graph.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class NodeName {

    @NonNull
    String value;

    @Override
    public String toString() {
        return value;
    }

}
