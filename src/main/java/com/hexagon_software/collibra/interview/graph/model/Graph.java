package com.hexagon_software.collibra.interview.graph.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Graph {

    private Set<Node> nodes;

    public Graph() {
        nodes = new HashSet<>();
    }

    /**
     * Add node to graph.
     *
     * @param nodeName node name
     * @return <code>true</code> if node added, <code>false</code> if node not added because node with same name already exists
     */
    boolean addNode(NodeName nodeName) {
        Optional<Node> foundNode = findNodeByName(nodeName);

        if (!foundNode.isPresent()) {
            nodes.add(new Node(nodeName));
        }

        return !foundNode.isPresent();
    }

    private Optional<Node> findNodeByName(NodeName name) {
        return nodes.stream()
                .filter(n -> n.getName().equals(name))
                .findAny();
    }

}
