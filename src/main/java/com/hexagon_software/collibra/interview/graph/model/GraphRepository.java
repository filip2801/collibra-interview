package com.hexagon_software.collibra.interview.graph.model;

import java.util.Set;

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand;
import com.hexagon_software.collibra.interview.graph.command.CloserThanCommand;
import com.hexagon_software.collibra.interview.graph.command.RemoveEdgeCommand;
import com.hexagon_software.collibra.interview.graph.command.ShortestPathCommand;
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;

public interface GraphRepository {

    /**
     * Add node to graph.
     *
     * @param nodeName node name
     * @return <code>true</code> if node added, <code>false</code> if node not added because node with same name already exists
     */
    boolean addNode(NodeName nodeName);

    /**
     * Add edge to graph.
     *
     * @param command add edge command
     * @return <code>true</code> if edge added, <code>false</code> if node not found
     */
    boolean addEdge(AddEdgeCommand command);

    /**
     * Remove node from graph.
     *
     * @param nodeName node name
     * @return <code>true</code> if node removed, <code>false</code> if node not found
     */
    boolean removeNode(NodeName nodeName);

    /**
     * Remove edge from graph.
     *
     * @param command remove edge command
     * @throws NodeNotFound if node not found
     */
    void removeEdges(RemoveEdgeCommand command) throws NodeNotFound;

    int shortestPath(ShortestPathCommand command) throws NodeNotFound;

    Set<NodeName> closerThan(CloserThanCommand command) throws NodeNotFound;

}
