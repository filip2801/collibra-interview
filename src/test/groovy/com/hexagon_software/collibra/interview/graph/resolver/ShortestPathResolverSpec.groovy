package com.hexagon_software.collibra.interview.graph.resolver

import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound
import com.hexagon_software.collibra.interview.graph.model.Graph
import com.hexagon_software.collibra.interview.graph.model.Node
import com.hexagon_software.collibra.interview.graph.model.NodeName
import spock.lang.Specification

class ShortestPathResolverSpec extends Specification {

    def shortestPathResolver
    def distanceToNodesResolver
    def graph
    def start
    def end

    def setup() {
        distanceToNodesResolver = Mock(DistanceToNodesResolver)
        shortestPathResolver = new ShortestPathResolver(distanceToNodesResolver)

        graph = Mock(Graph)
        start = new NodeName('node-1')
        end = new NodeName('node-2')
    }

    def "should return distance exception"() {
        given:
        def endNode = Mock(Node)
        graph.findNodeByName(end) >> Optional.of(endNode)

        distanceToNodesResolver.resolve(graph, start) >> [(Mock(Node)): 20, (Mock(Node)): 5, (endNode): 10]

        expect:
        shortestPathResolver.resolve(graph, start, end) == 10
    }

    def "should propagate exception"() {
        given:
        graph.findNodeByName(end) >> Optional.of(Mock(Node))
        distanceToNodesResolver.resolve(graph, start) >> { throw new NodeNotFound() }

        when:
        shortestPathResolver.resolve(graph, start, end)

        then:
        thrown NodeNotFound
    }

    def "should throw exception when end node not found"() {
        given:
        graph.findNodeByName(end) >> Optional.empty()

        when:
        shortestPathResolver.resolve(graph, start, end)

        then:
        thrown NodeNotFound
        0 * distanceToNodesResolver._
    }

}
