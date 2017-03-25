package com.hexagon_software.collibra.interview.graph.model

import spock.lang.Specification

class GraphSpec extends Specification {

    def graph = new Graph()

    def "should add node"() {
        given:
        def node = n('3')
        graph.nodes = nodes

        when:
        def result = graph.addNode(node)

        then:
        result
        graph.nodes == nodes + node as Set

        where:
        nodes << [[], [n('1')], [n('1'), n('2')]]
    }

    def "should not add node"() {
        given:
        def node = n('2')
        def nodes = [n('1'), n('2')] as Set
        graph.nodes = nodes

        when:
        def result = graph.addNode(node)

        then:
        !result
        graph.nodes == nodes
    }

    Node n(String name) {
        new Node(name)
    }
}
