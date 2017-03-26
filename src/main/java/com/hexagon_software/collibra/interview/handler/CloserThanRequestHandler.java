package com.hexagon_software.collibra.interview.handler;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.hexagon_software.collibra.interview.graph.command.CloserThanCommand;
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound;
import com.hexagon_software.collibra.interview.graph.model.GraphRepository;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CloserThanRequestHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("^CLOSER THAN (\\d+) ((\\w|-)+)$");

    private final GraphRepository graphRepository;

    @Override
    protected Response handleMessage(String message) {
        CloserThanCommand command = createCommand(message);

        try {
            Set<NodeName> nodes = graphRepository.closerThan(command);
            return createResponse(nodes);
        } catch (NodeNotFound e) {
            return response("ERROR: NODE NOT FOUND");
        }
    }

    private Response createResponse(Set<NodeName> nodes) {
        return response(nodes.stream()
                .map(NodeName::getValue)
                .sorted()
                .collect(Collectors.joining(",")));
    }

    @Override
    Pattern getPattern() {
        return PATTERN;
    }

    private CloserThanCommand createCommand(String message) {
        Matcher matcher = PATTERN.matcher(message);
        matcher.find();

        return new CloserThanCommand(
                new NodeName(matcher.group(2)),
                Integer.parseInt(matcher.group(1)));
    }

}
