package com.hexagon_software.collibra.interview.handler

import com.hexagon_software.collibra.interview.graph.command.ShortestPathCommand
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound
import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import com.hexagon_software.collibra.interview.graph.model.NodeName
import org.apache.mina.core.session.IoSession
import spock.lang.Specification
import spock.lang.Unroll

class ShortestPathHandlerSpec extends Specification {

    def handler
    def graphRepository

    def setup() {
        graphRepository = Mock(GraphRepository)
        handler = new ShortestPathResolverHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(message) == supported

        where:
        message                       || supported
        'SHORTEST PATH node node-1'   || true
        'SHORTEST PATH node-1 node'   || true
        'SHORTEST PATH node-1 node-2' || true
        'SHORTEST PATH node-1'        || false
        'SHORTEST node-1 node-2'      || false
        'PATH node-1 node-2'          || false

        supportOrNot = supported ? 'support' : 'not support'
    }

    @Unroll
    def "should write message with calculated shortest path"() {
        given:
        def command = new ShortestPathCommand(new NodeName('node-1'), new NodeName('node-2'))
        graphRepository.shortestPath(command) >> 10

        expect:
        handler.handle('SHORTEST PATH node-1 node-2') == new Response('10')
    }

    @Unroll
    def "should write message when node not found"() {
        given:
        def command = new ShortestPathCommand(new NodeName('node-1'), new NodeName('node-2'))
        graphRepository.shortestPath(command) >> { throw new NodeNotFound() }

        expect:
        handler.handle('SHORTEST PATH node-1 node-2') == new Response('ERROR: NODE NOT FOUND')
    }

}
