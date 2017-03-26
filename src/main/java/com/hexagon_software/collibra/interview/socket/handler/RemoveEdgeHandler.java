package com.hexagon_software.collibra.interview.socket.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand;
import com.hexagon_software.collibra.interview.graph.command.RemoveEdgeCommand;
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import com.hexagon_software.collibra.interview.graph.model.GraphRepository;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RemoveEdgeHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("^REMOVE EDGE ((\\w|-)+) ((\\w|-)+)$");

    private final GraphRepository graphRepository;

    @Override
    protected void handleMessage(IoSession session, String message) {
        RemoveEdgeCommand command = createCommand(message);
        try {
            graphRepository.removeEdges(command);
            session.write("EDGE REMOVED");
        } catch (NodeNotFound e) {
            session.write("ERROR: NODE NOT FOUND");
        }
    }

    @Override
    Pattern getPattern() {
        return PATTERN;
    }

    private RemoveEdgeCommand createCommand(String message) {
        Matcher matcher = PATTERN.matcher(message);
        matcher.find();

        return new RemoveEdgeCommand(
                new NodeName(matcher.group(1)),
                new NodeName(matcher.group(3)));
    }

}
