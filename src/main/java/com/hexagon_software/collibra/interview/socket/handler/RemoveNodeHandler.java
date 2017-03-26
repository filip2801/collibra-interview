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
public class RemoveNodeHandler extends MatchedByPatternHandler {

    private static final Pattern PATTERN = Pattern.compile("^REMOVE NODE ((\\w|-)+)$");

    private final GraphRepository graphRepository;

    @Override
    protected void handleMessage(IoSession session, String message) {
        NodeName nodeName = getNodeName(message);
        boolean removed = graphRepository.removeNode(nodeName);

        if (removed) {
            session.write("NODE REMOVED");
        } else {
            session.write("ERROR: NODE NOT FOUND");
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
