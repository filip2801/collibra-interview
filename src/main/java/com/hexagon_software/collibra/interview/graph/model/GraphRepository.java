package com.hexagon_software.collibra.interview.graph.model;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class GraphRepository {

    private Graph graph;

    @PostConstruct
    synchronized void initGraph() {
        graph = new Graph();
    }

    /**
     * Add node to graph.
     *
     * @param nodeName node name
     * @return <code>true</code> if node added, <code>false</code> if node not added because node with same name already exists
     */
    public synchronized boolean addNode(String nodeName) {
        Node node = new Node(nodeName);
        return graph.addNode(node);
    }

}
