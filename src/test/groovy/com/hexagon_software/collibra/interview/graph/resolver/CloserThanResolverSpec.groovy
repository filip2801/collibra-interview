package com.hexagon_software.collibra.interview.graph.resolver

import com.hexagon_software.collibra.interview.graph.model.Graph
import com.hexagon_software.collibra.interview.graph.model.Node
import com.hexagon_software.collibra.interview.graph.model.NodeName
import spock.lang.Specification


class CloserThanResolverSpec extends Specification {

    def closerThanResolver
    def distanceToNodesResolver
    def graph
    def startNodeName
    def weight

    def setup() {
        distanceToNodesResolver = Mock(DistanceToNodesResolver)
        closerThanResolver = new CloserThanResolver(distanceToNodesResolver)

        graph = Mock(Graph)
        startNodeName = new NodeName('start-node')
        weight = 10
    }

    def "should return nodes closer than 10"() {
        given:
        def start = new Node(startNodeName)
        def name1 = new NodeName('node-1')
        def name2 = new NodeName('node-1')

        def n1 = new Node(name1)
        def n2 = new Node(name2)
        def n3 = new Node(new NodeName('node-3'))
        def n4 = new Node(new NodeName('node-4'))

        distanceToNodesResolver.resolve(graph, startNodeName) >> [
                (n1)   : 5,
                (n2)   : 9,
                (n3)   : 10,
                (n4)   : 11,
                (start): 4]

        expect:
        closerThanResolver.resolve(graph, startNodeName, weight) == [name1, name2] as Set
    }

}
