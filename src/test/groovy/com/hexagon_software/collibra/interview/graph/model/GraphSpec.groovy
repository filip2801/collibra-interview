package com.hexagon_software.collibra.interview.graph.model

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class GraphSpec extends Specification {

    def graph = new Graph()

    @Unroll
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

    def "should add edge"() {
        given:
        def nodeName1 = new NodeName('1')
        def nodeName2 = new NodeName('2')
        def command = new AddEdgeCommand(nodeName1, nodeName2, 10)

        def node1 = mockNode(nodeName1)
        def node2 = mockNode(nodeName2)
        graph.nodes = [node1, node2]

        when:
        def result = graph.addEdge(command)

        then:
        result
        1 * node1.addEdge(node2, 10)
    }

    def "should not add edge"() {
        given:
        def nodeName1 = new NodeName('1')
        def nodeName2 = new NodeName('2')
        def notExisted = new NodeName('3')
        def command = new AddEdgeCommand(nodeName1, notExisted, 10)

        def node1 = mockNode(nodeName1)
        def node2 = mockNode(nodeName2)
        graph.nodes = [node1, node2]

        when:
        def result = graph.addEdge(command)

        then:
        !result
        0 * node1.addEdge(_, _)
    }

    def "should remove node"() {
        given:
        def node1 = n('1')
        def node2 = n('2')
        graph.nodes = [node1, node2]

        when:
        def result = graph.removeNode(new NodeName('1'))

        then:
        result
        graph.nodes == [node2] as Set
    }

    def "should not remove not existing node"() {
        given:
        def node1 = n('1')
        def node2 = n('2')
        graph.nodes = [node1, node2]

        when:
        def result = graph.removeNode(new NodeName('not-existing'))

        then:
        !result
        graph.nodes == [node1, node2] as Set
    }

    def "should remove connected edges"() {
        given:
        def node1 = mockNode(new NodeName('1'))
        def node2 = mockNode(new NodeName('2'))
        def node3 = mockNode(new NodeName('3'))
        def edgeTo2 = new Edge(node2, node1, 10)
        def edgeTo3 = new Edge(node3, node1, 10)

        graph.nodes = [node1, node2, node3]
        node1.getIncomingEdges() >> [edgeTo2, edgeTo3]

        when:
        def result = graph.removeNode(new NodeName('1'))

        then:
        result
        graph.nodes == [node2, node3] as Set
        1 * node2.removeIncomingEdge(edgeTo2)
        1 * node3.removeIncomingEdge(edgeTo3)
    }

    Node n(String name) {
        new Node(new NodeName(name))
    }

    Node mockNode(NodeName nodeName) {
        def node = Mock(Node)
        node.getName() >> nodeName
        node
    }

}
