package com.hexagon_software.collibra.interview.graph.model

import spock.lang.Specification

class GraphSpec extends Specification {

    def graph = new Graph()

    def "should add node"() {
        given:
        def nodeName = new NodeName('3')
        def node = new Node(nodeName)
        graph.nodes = nodes

        when:
        def result = graph.addNode(nodeName)

        then:
        result
        graph.nodes == nodes + node as Set

        where:
        nodes << [[], [n('1')], [n('1'), n('2')]]
    }

    def "should not add node"() {
        given:
        def nodeName = new NodeName('2')
        def nodes = [n('1'), n('2')] as Set
        graph.nodes = nodes

        when:
        def result = graph.addNode(nodeName)

        then:
        !result
        graph.nodes == nodes
    }

    Node n(String name) {
        new Node(new NodeName(name))
    }
}
