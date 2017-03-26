package com.hexagon_software.collibra.interview.graph.algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import com.hexagon_software.collibra.interview.graph.model.Graph;
import com.hexagon_software.collibra.interview.graph.model.Node;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import com.hexagon_software.collibra.interview.graph.resolver.DistanceToNodesResolver;
import org.springframework.stereotype.Component;

@Component
public class DijkstrasAlgorithm implements DistanceToNodesResolver {

    @Override
    public Map<Node, Integer> resolve(Graph graph, NodeName start) throws NodeNotFound {
        Optional<Node> startNode = graph.findNodeByName(start);
        if (!startNode.isPresent()) {
            throw new NodeNotFound();
        }

        Resolver resolver = new Resolver(graph, startNode.get());
        return resolver.resolve();
    }

    private class Resolver {

        private final Set<Node> unprocessed;
        private final Set<Node> processed;
        private final Map<Node, Integer> distance;
        private final Map<Node, Node> previous;

        private Resolver(Graph graph, Node start) {
            unprocessed = graph.getNodes();
            processed = new HashSet<>();
            distance = initDistances(unprocessed, start);
            previous = new HashMap<>();
        }

        private Map<Node, Integer> resolve() {
            while (!unprocessed.isEmpty()) {
                Node nearest = findNearestFromUnprocessed();
                unprocessed.remove(nearest);
                processed.add(nearest);
                updatePreviousNodes(nearest);
            }
            return distance;
        }

        private void updatePreviousNodes(Node node) {
            Integer nodeWeight = distance.get(node);
            node.getOutgoingEdges().stream()
                    .filter(e -> isaNotInfinity(nodeWeight) && distance.get(e.getEnd()) > e.getWeight() + nodeWeight)
                    .forEach(e -> {
                        distance.put(e.getEnd(), e.getWeight() + nodeWeight);
                        previous.put(e.getEnd(), node);
                    });
        }

        private Node findNearestFromUnprocessed() {
            return unprocessed.stream()
                    .min(Comparator.comparing(distance::get))
                    .get();
        }

        private Map<Node, Integer> initDistances(Set<Node> nodes, Node start) {
            Map<Node, Integer> distances = new HashMap<>();
            nodes.forEach(n -> distances.put(n, INFINITY));
            distances.put(start, 0);

            return distances;
        }

    }

    private boolean isaNotInfinity(Integer nodeWeight) {
        return !nodeWeight.equals(INFINITY);
    }

}
