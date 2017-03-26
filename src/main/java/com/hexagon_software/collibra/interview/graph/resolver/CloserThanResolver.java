package com.hexagon_software.collibra.interview.graph.resolver;

import java.util.Set;
import java.util.stream.Collectors;

import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import com.hexagon_software.collibra.interview.graph.model.Graph;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CloserThanResolver {

    private final DistanceToNodesResolver distanceToNodesResolver;

    public Set<NodeName> resolve(Graph graph, NodeName start, int weight) throws NodeNotFound {
        return distanceToNodesResolver.resolve(graph, start)
                .entrySet()
                .stream()
                .filter(e -> e.getValue() < weight)
                .filter(e -> !e.getKey().getName().equals(start))
                .map(e -> e.getKey().getName())
                .collect(Collectors.toSet());
    }

}
