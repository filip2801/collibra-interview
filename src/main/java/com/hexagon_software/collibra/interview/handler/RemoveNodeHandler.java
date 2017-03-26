package com.hexagon_software.collibra.interview.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.graph.model.GraphRepository;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RemoveNodeHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("^REMOVE NODE ((\\w|-)+)$");

    private final GraphRepository graphRepository;

    @Override
    protected Response handleMessage(String message) {
        NodeName nodeName = getNodeName(message);
        boolean removed = graphRepository.removeNode(nodeName);

        if (removed) {
            return response("NODE REMOVED");
        } else {
            return response("ERROR: NODE NOT FOUND");
        }
    }

    @Override
    Pattern getPattern() {
        return PATTERN;
    }

    private NodeName getNodeName(String message) {
        Matcher matcher = PATTERN.matcher(message);
        matcher.find();

        return new NodeName(matcher.group(1));
    }

}
