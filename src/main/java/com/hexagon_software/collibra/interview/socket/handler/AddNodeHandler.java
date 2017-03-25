package com.hexagon_software.collibra.interview.socket.handler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hexagon_software.collibra.interview.graph.model.GraphRepository;
import com.hexagon_software.collibra.interview.graph.model.NodeName;
import lombok.AllArgsConstructor;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AddNodeHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("^ADD NODE ((\\w|-)+)$");

    private final GraphRepository graphRepository;

    @Override
    protected void handleMessage(IoSession session, String message) {
        NodeName nodeName = getNodeName(message);
        boolean added = graphRepository.addNode(nodeName);

        if (added) {
            session.write("NODE ADDED");
        } else {
            session.write("ERROR: NODE ALREADY EXISTS");
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
