package com.hexagon_software.collibra.interview.graph.model

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand
import com.hexagon_software.collibra.interview.graph.resolver.CloserThanResolver
import com.hexagon_software.collibra.interview.graph.resolver.ShortestPathResolver
import spock.lang.Specification
import spock.lang.Unroll

class GraphLockingRepositorySpec extends Specification {

    def repository
    def shortestPathResolver
    def closerThanResolver
    def graph

    def setup() {
        shortestPathResolver = Mock(ShortestPathResolver)
        closerThanResolver = Mock(CloserThanResolver)
        repository = new GraphLockingRepository(shortestPathResolver, closerThanResolver)

        graph = Mock(Graph)
        repository.graph = graph
    }

    @Unroll
    def "should #addOrNot node"() {
        given:
        def nodeName = new NodeName('name')
        graph.addNode(nodeName) >> wasAdded

        expect:
        repository.addNode(nodeName) == wasAdded

        where:
        wasAdded << [true, false]

        addOrNot = wasAdded ? 'add' : 'not add'
    }

    @Unroll
    def "should #addOrNot edge"() {
        given:
        def command = GroovyMock(AddEdgeCommand)
        graph.addEdge(command) >> wasAdded

        expect:
        repository.addEdge(command) == wasAdded

        where:
        wasAdded << [true, false]

        addOrNot = wasAdded ? 'add' : 'not add'
    }

    @Unroll
    def "should #removeOrNot node"() {
        given:
        def nodeName = new NodeName('name')
        graph.removeNode(nodeName) >> wasRemoved

        expect:
        repository.removeNode(nodeName) == wasRemoved

        where:
        wasRemoved << [true, false]

        removeOrNot = wasRemoved ? 'removed' : 'not removed'
    }

}
