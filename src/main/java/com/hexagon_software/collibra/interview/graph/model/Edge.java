package com.hexagon_software.collibra.interview.graph.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class Edge {

    @NonNull
    Node start;
    @NonNull
    Node end;
    @NonNull
    int weight;

}
