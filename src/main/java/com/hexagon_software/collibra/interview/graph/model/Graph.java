package com.hexagon_software.collibra.interview.graph.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand;
import com.hexagon_software.collibra.interview.graph.command.RemoveEdgeCommand;
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;

public class Graph {

    private Set<Node> nodes;

    public Graph() {
        nodes = new HashSet<>();
    }

    public Set<Node> getNodes() {
        return new HashSet<>(nodes);
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

    boolean removeNode(NodeName nodeName) {
        Optional<Node> optional = findNodeByName(nodeName);
        if (optional.isPresent()) {
            Node node = optional.get();
            node.getIncomingEdges()
                    .forEach(e -> e.getStart().removeIncomingEdge(e));

            nodes.remove(node);
        }

        return optional.isPresent();
    }

    synchronized void removeEdges(RemoveEdgeCommand command) throws NodeNotFound {
        Optional<Node> nodeStart = findNodeByName(command.getStart());
        Optional<Node> nodeEnd = findNodeByName(command.getEnd());

        if (nodeStart.isPresent() && nodeEnd.isPresent()) {
            Node start = nodeStart.get();
            Node end = nodeEnd.get();

            start.getOutgoingEdgesWithEndNode(end)
                    .forEach(e -> {
                        e.getEnd().removeIncomingEdge(e);
                        start.removeOutgoingEdge(e);
                    });
        } else {
            throw new NodeNotFound();
        }
    }

    public Optional<Node> findNodeByName(NodeName name) {
        return nodes.stream()
                .filter(n -> n.getName().equals(name))
                .findAny();
    }

}
