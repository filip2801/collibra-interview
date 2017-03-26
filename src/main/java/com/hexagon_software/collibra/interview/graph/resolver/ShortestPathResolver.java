package com.hexagon_software.collibra.interview.graph.resolver;

import java.util.Optional;

import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import com.hexagon_software.collibra.interview.graph.model.Graph;
import com.hexagon_software.collibra.interview.graph.model.Node;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ShortestPathResolver {

    private final DistanceToNodesResolver distanceToNodesResolver;

    public int resolve(Graph graph, NodeName start, NodeName end) throws NodeNotFound {
        Optional<Node> endNode = graph.findNodeByName(end);
        if (!endNode.isPresent()) {
            throw new NodeNotFound();
        }

        return distanceToNodesResolver.resolve(graph, start)
                .get(endNode.get());
    }

}
