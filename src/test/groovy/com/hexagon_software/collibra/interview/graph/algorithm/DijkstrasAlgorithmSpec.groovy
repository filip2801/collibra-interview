package com.hexagon_software.collibra.interview.graph.algorithm

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound
import com.hexagon_software.collibra.interview.graph.model.Graph
import com.hexagon_software.collibra.interview.graph.model.Node
import com.hexagon_software.collibra.interview.graph.model.NodeName
import spock.lang.Specification

class DijkstrasAlgorithmSpec extends Specification {

    def resolver = new DijkstrasAlgorithm()
    def graph

    def setup() {
        initGraph()
    }

    def "should calculate distance to nodes"() {
        given:
        def startNode = nodeName('node-0')

        when:
        def distances = resolver.resolve(graph, startNode)

        then:
        distances[node('node-0')] == 0
        distances[node('node-1')] == 3
        distances[node('node-2')] == 4
        distances[node('node-3')] == 6
        distances[node('node-4')] == 3
        distances[node('node-5')] == 5
        distances[node('node-6')] == DijkstrasAlgorithm.INFINITY
    }

    def "should throw exception when node not found"() {
        given:
        def startNode = nodeName('not-existing-node')

        when:
        resolver.resolve(graph, startNode)

        then:
        thrown NodeNotFound
    }

    NodeName nodeName(String name) {
        new NodeName(name)
    }

    Node node(String name) {
        new Node(new NodeName(name))
    }

    AddEdgeCommand addEdgeCmd(NodeName start, NodeName end, int weight) {
        new AddEdgeCommand(start, end, weight)
    }

    def initGraph() {
        graph = new Graph()
        def n0 = nodeName('node-0')
        def n1 = nodeName('node-1')
        def n2 = nodeName('node-2')
        def n3 = nodeName('node-3')
        def n4 = nodeName('node-4')
        def n5 = nodeName('node-5')
        def n6 = nodeName('node-6')

        graph.addNode(n0)
        graph.addNode(n1)
        graph.addNode(n2)
        graph.addNode(n3)
        graph.addNode(n4)
        graph.addNode(n5)
        graph.addNode(n6)

        graph.addEdge(addEdgeCmd(n0, n1, 3))
        graph.addEdge(addEdgeCmd(n0, n4, 3))
        graph.addEdge(addEdgeCmd(n1, n2, 1))
        graph.addEdge(addEdgeCmd(n2, n3, 3))
        graph.addEdge(addEdgeCmd(n2, n5, 1))
        graph.addEdge(addEdgeCmd(n3, n1, 3))
        graph.addEdge(addEdgeCmd(n4, n5, 2))
        graph.addEdge(addEdgeCmd(n5, n0, 6))
        graph.addEdge(addEdgeCmd(n5, n3, 1))
        graph.addEdge(addEdgeCmd(n6, n3, 10))
    }

}
