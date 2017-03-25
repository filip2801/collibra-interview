package com.hexagon_software.collibra.interview.graph.model

import spock.lang.Specification


class NodeSpec extends Specification {

    def "should add edge"() {
        given:
        def node = new Node(new NodeName('node'))
        def end1 = new Node(new NodeName('node-1'))
        def end2 = new Node(new NodeName('node-1'))

        def edge1 = new Edge(node, end1, 2)
        def edge2 = new Edge(node, end2, 10)

        when:
        node.addEdge(end1, 2)
        node.addEdge(end2, 10)

        then:
        node.outgoingEdges == [edge1, edge2] as Set
        end1.outgoingEdges == [] as Set
        end2.outgoingEdges == [] as Set

        node.incomingEdges == [] as Set
        end1.incomingEdges == [edge1] as Set
        end2.incomingEdges == [edge2] as Set
    }

}
