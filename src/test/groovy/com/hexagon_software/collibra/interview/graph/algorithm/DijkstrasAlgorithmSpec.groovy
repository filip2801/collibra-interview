package com.hexagon_software.collibra.interview.graph.algorithm

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand
import com.hexagon_software.collibra.interview.graph.model.Graph
import com.hexagon_software.collibra.interview.graph.model.NodeName
import spock.lang.Specification
import spock.lang.Unroll

class DijkstrasAlgorithmSpec extends Specification {

    def resolver
    def graph
    def startNode

    def setup() {
        initGraph()
        startNode = nodeName('node-0')
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

    @Unroll
    def "should find shortest path to #destination with weight #weight"() {
        given:
        resolver = new DijkstrasAlgorithm(graph, startNode, destination)

        expect:
        resolver.shortestPath() == weight

        where:
        destination        || weight
        nodeName('node-0') || 0
        nodeName('node-1') || 3
        nodeName('node-2') || 4
        nodeName('node-3') || 6
        nodeName('node-4') || 3
        nodeName('node-5') || 5
        nodeName('node-6') || Integer.MAX_VALUE
    }

    NodeName nodeName(String name) {
        new NodeName(name)
    }

    AddEdgeCommand addEdgeCmd(NodeName start, NodeName end, int weight) {
        new AddEdgeCommand(start, end, weight)
    }

}
