package com.hexagon_software.collibra.interview.graph.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.NonNull;

public class Node {

    @NonNull
    private final NodeName name;
    @NonNull
    private final Set<Edge> edges;

    public Node(NodeName name) {
        this.name = name;
        this.edges = new HashSet<>();
    }

    public NodeName getName() {
        return name;
    }

    void addEdge(Node end, int weight) {
        Edge edge = new Edge(this, end, weight);
        edges.add(edge);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Node node = (Node) obj;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
