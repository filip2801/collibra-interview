package com.hexagon_software.collibra.interview.graph.command;

import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.Value;
import org.apache.commons.lang3.Validate;

@Value
public class CloserThanCommand {

    NodeName node;
    int weight;

    public CloserThanCommand(NodeName node, int weight) {
        Validate.notNull(node, "node is null");
        Validate.notNull(weight < 0, "weight is negative");

        this.node = node;
        this.weight = weight;
    }

}
