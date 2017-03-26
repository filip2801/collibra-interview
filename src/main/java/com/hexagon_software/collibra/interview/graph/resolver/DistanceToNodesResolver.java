package com.hexagon_software.collibra.interview.graph.resolver;

import java.util.Map;

import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import com.hexagon_software.collibra.interview.graph.model.Graph;
import com.hexagon_software.collibra.interview.graph.model.Node;
import com.hexagon_software.collibra.interview.graph.model.NodeName;

public interface DistanceToNodesResolver {

    Integer INFINITY = Integer.MAX_VALUE;

    Map<Node, Integer> resolve(Graph graph, NodeName start) throws NodeNotFound;

}
