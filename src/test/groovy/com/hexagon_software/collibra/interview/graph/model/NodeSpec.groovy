package com.hexagon_software.collibra.interview.graph.model

import spock.lang.Specification


class NodeSpec extends Specification {

    def "should add edge"() {
        given:
        def node = new Node(new NodeName('node'))
        def end1 = new Node(new NodeName('node-1'))
        def end2 = new Node(new NodeName('node-1'))

        when:
        node.addEdge(end1, 2)
        node.addEdge(end2, 10)

        then:
        node.edges == [new Edge(node, end1, 2), new Edge(node, end2, 10)] as Set
    }

}
