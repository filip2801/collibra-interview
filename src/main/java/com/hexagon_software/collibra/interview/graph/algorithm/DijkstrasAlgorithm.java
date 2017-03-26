package com.hexagon_software.collibra.interview.graph.algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.hexagon_software.collibra.interview.graph.model.Graph;
import com.hexagon_software.collibra.interview.graph.model.Node;
import com.hexagon_software.collibra.interview.graph.model.NodeName;

public class DijkstrasAlgorithm {

    private final Node start;
    private final Set<Node> unknown;
    private final Set<Node> processed;
    private final Map<Node, Integer> distance;
    private final Map<Node, Node> previous;

    private Node end;

    public DijkstrasAlgorithm(Graph graph, NodeName start, NodeName end) {
        this.start = graph.findNodeByName(start).get();
        this.end = graph.findNodeByName(end).get();
        unknown = graph.getNodes();
        processed = new HashSet<>();
        distance = initDistances(this.unknown, this.start);
        previous = new HashMap<>();
    }

    public DijkstrasAlgorithm(Graph graph, NodeName node) {
        this.start = graph.findNodeByName(node).get();
        unknown = graph.getNodes();
        processed = new HashSet<>();
        distance = initDistances(this.unknown, this.start);
        previous = new HashMap<>();
    }

    public int shortestPath() {
        resolve();
        return distance.get(end);
    }

    public Set<NodeName> closerThan(int weight) {
        resolve();
        return distance.entrySet()
                .stream()
                .filter(e -> e.getValue() < weight)
                .filter(e -> !e.getKey().equals(start))
                .map(e -> e.getKey().getName())
                .collect(Collectors.toSet());
    }

    private void resolve() {
        while (!unknown.isEmpty()) {
            Node nearest = findNearestFromQ();
            unknown.remove(nearest);
            processed.add(nearest);
            updatePreviouses(nearest);
        }
    }

    private void updatePreviouses(Node node) {
        Integer nodeWeight = distance.get(node);
        node.getOutgoingEdges().stream()
                .filter(e -> !nodeWeight.equals(Integer.MAX_VALUE) && distance.get(e.getEnd()) > e.getWeight() + nodeWeight)
                .forEach(e -> {
                    distance.put(e.getEnd(), e.getWeight() + nodeWeight);
                    previous.put(e.getEnd(), node);
                });
    }

    private Node findNearestFromQ() {
        return unknown.stream()
                .min(Comparator.comparing(distance::get))
                .get();
    }

    private Map<Node, Integer> initDistances(Set<Node> nodes, Node start) {
        Map<Node, Integer> distances = new HashMap<>();
        nodes.forEach(n -> distances.put(n, Integer.MAX_VALUE));
        distances.put(start, 0);

        return distances;
    }

}
