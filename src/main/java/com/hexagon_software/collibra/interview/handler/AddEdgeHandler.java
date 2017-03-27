package com.hexagon_software.collibra.interview.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand;
import com.hexagon_software.collibra.interview.graph.model.GraphRepository;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AddEdgeHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("^ADD EDGE ((\\w|-)+) ((\\w|-)+) (\\d+)$");

    private final GraphRepository graphRepository;

    @Override
    protected Response handleMessage(String message) {
        AddEdgeCommand command = createCommand(message);
        boolean added = graphRepository.addEdge(command);

        if (added) {
            return response("EDGE ADDED");
        } else {
            return response("ERROR: NODE NOT FOUND");
        }
    }

    @Override
    Pattern getPattern() {
        return PATTERN;
    }

    private AddEdgeCommand createCommand(String message) {
        Matcher matcher = PATTERN.matcher(message);
        matcher.find();

        return new AddEdgeCommand(
                new NodeName(matcher.group(1)),
                new NodeName(matcher.group(3)),
                Integer.parseInt(matcher.group(5)));
    }

}
