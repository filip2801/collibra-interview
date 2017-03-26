package com.hexagon_software.collibra.interview.graph.command;

import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.NonNull;
import lombok.Value;

@Value
public class ShortestPathCommand {

    @NonNull
    NodeName start;

    @NonNull
    NodeName end;

}
