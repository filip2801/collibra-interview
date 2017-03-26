package com.hexagon_software.collibra.interview.graph.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.NonNull;
import org.apache.commons.lang3.Validate;

public class Node {

    @NonNull
    private NodeName name;
    @NonNull
    private Set<Edge> outgoingEdges;
    @NonNull
    private Set<Edge> incomingEdges;

    public Node(NodeName name) {
        this.name = name;
        this.outgoingEdges = new HashSet<>();
        this.incomingEdges = new HashSet<>();
    }

    public NodeName getName() {
        return name;
    }

    Set<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    Set<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    void addEdge(Node end, int weight) {
        Edge edge = new Edge(this, end, weight);

        outgoingEdges.add(edge);
        end.addIncomingEdge(edge);
    }

    private void addIncomingEdge(Edge edge) {
        Validate.isTrue(edge.getEnd().equals(this), "end node does not equal to this");

        incomingEdges.add(edge);
    }

    void removeIncomingEdge(Edge edge) {
        incomingEdges.remove(edge);
    }

    public void removeOutgoingEdge(Edge edge) {
        outgoingEdges.remove(edge);
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
