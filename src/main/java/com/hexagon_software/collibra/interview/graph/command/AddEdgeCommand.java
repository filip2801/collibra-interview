package com.hexagon_software.collibra.interview.graph.command;

import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.Value;
import org.apache.commons.lang3.Validate;

@Value
public class AddEdgeCommand {

    NodeName start;
    NodeName end;
    int weight;

    public AddEdgeCommand(NodeName start, NodeName end, int weight) {
        Validate.notNull(start, "start is null");
        Validate.notNull(end, "end is null");
        Validate.isTrue(weight >= 0, "weight is negative");

        this.start = start;
        this.end = end;
        this.weight = weight;
    }

}
