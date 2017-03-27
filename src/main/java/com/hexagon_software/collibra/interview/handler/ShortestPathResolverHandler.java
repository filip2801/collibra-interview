package com.hexagon_software.collibra.interview.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.graph.command.ShortestPathCommand;
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import com.hexagon_software.collibra.interview.graph.model.GraphRepository;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ShortestPathResolverHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("^SHORTEST PATH ((\\w|-)+) ((\\w|-)+)$");

    private final GraphRepository graphRepository;

    @Override
    protected Response handleMessage(String message) {
        ShortestPathCommand command = createCommand(message);

        try {
            Integer weight = graphRepository.shortestPath(command);
            return response(weight.toString());
        } catch (NodeNotFound e) {
            return response("ERROR: NODE NOT FOUND");
        }
    }

    @Override
    Pattern getPattern() {
        return PATTERN;
    }

    private ShortestPathCommand createCommand(String message) {
        Matcher matcher = PATTERN.matcher(message);
        matcher.find();

        return new ShortestPathCommand(
                new NodeName(matcher.group(1)),
                new NodeName(matcher.group(3)));
    }
}
