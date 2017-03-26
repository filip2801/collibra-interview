package com.hexagon_software.collibra.interview.graph.model;

import javax.annotation.PostConstruct;

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand;
import com.hexagon_software.collibra.interview.graph.command.RemoveEdgeCommand;
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
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
    public synchronized boolean addNode(NodeName nodeName) {
        return graph.addNode(nodeName);
    }

    /**
     * Add edge to graph.
     *
     * @param command add edge command
     * @return <code>true</code> if edge added, <code>false</code> if node not found
     */
    public synchronized boolean addEdge(AddEdgeCommand command) {
        return graph.addEdge(command);
    }

    /**
     * Remove node from graph.
     *
     * @param nodeName node name
     * @return <code>true</code> if node removed, <code>false</code> if node not found
     */
    public synchronized boolean removeNode(NodeName nodeName) {
        return graph.removeNode(nodeName);
    }

    /**
     * Remove edge from graph.
     *
     * @param command remove edge command
     * @throws NodeNotFound if node not found
     */
    public synchronized void removeEdges(RemoveEdgeCommand command) throws NodeNotFound {
        graph.removeEdges(command);
    }

}
