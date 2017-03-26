package com.hexagon_software.collibra.interview.graph.model;

import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import com.hexagon_software.collibra.interview.graph.algorithm.DijkstrasAlgorithm;
import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand;
import com.hexagon_software.collibra.interview.graph.command.CloserThanCommand;
import com.hexagon_software.collibra.interview.graph.command.RemoveEdgeCommand;
import com.hexagon_software.collibra.interview.graph.command.ShortestPathCommand;
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import org.springframework.stereotype.Repository;

@Repository
public class GraphLockingRepository implements GraphRepository {

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
    @Override
    public synchronized boolean addNode(NodeName nodeName) {
        return graph.addNode(nodeName);
    }

    /**
     * Add edge to graph.
     *
     * @param command add edge command
     * @return <code>true</code> if edge added, <code>false</code> if node not found
     */
    @Override
    public synchronized boolean addEdge(AddEdgeCommand command) {
        return graph.addEdge(command);
    }

    /**
     * Remove node from graph.
     *
     * @param nodeName node name
     * @return <code>true</code> if node removed, <code>false</code> if node not found
     */
    @Override
    public synchronized boolean removeNode(NodeName nodeName) {
        return graph.removeNode(nodeName);
    }

    /**
     * Remove edge from graph.
     *
     * @param command remove edge command
     * @throws NodeNotFound if node not found
     */
    @Override
    public synchronized void removeEdges(RemoveEdgeCommand command) throws NodeNotFound {
        graph.removeEdges(command);
    }

    @Override
    public synchronized int shortestPath(ShortestPathCommand command) throws NodeNotFound {
        Optional<Node> start = graph.findNodeByName(command.getStart());
        Optional<Node> end = graph.findNodeByName(command.getEnd());

        if (start.isPresent() && end.isPresent()) {
            return new DijkstrasAlgorithm(graph,
                    start.get().getName(),
                    end.get().getName())
                    .shortestPath();
        } else {
            throw new NodeNotFound();
        }
    }

    @Override
    public synchronized Set<NodeName> closerThan(CloserThanCommand command) throws NodeNotFound {
        Optional<Node> node = graph.findNodeByName(command.getNode());

        if (node.isPresent()) {
            return new DijkstrasAlgorithm(graph,
                    node.get().getName())
                    .closerThan(command.getWeight());
        } else {
            throw new NodeNotFound();
        }
    }

}
