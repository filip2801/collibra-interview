package com.hexagon_software.collibra.interview.graph.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand;

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

    boolean addEdge(AddEdgeCommand command) {
        Optional<Node> start = findNodeByName(command.getStart());
        if (start.isPresent()) {
            Optional<Node> end = findNodeByName(command.getEnd());

            if (end.isPresent()) {
                Node startNode = start.get();
                startNode.addEdge(end.get(), command.getWeight());

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private Optional<Node> findNodeByName(NodeName name) {
        return nodes.stream()
                .filter(n -> n.getName().equals(name))
                .findAny();
    }

}
