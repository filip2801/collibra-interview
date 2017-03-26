package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.graph.command.CloserThanCommand
import com.hexagon_software.collibra.interview.graph.command.ShortestPathCommand
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound
import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import com.hexagon_software.collibra.interview.graph.model.NodeName
import org.apache.mina.core.session.IoSession
import spock.lang.Specification
import spock.lang.Unroll

class CloserThanRequestHandlerSpec extends Specification {

    def handler
    def session
    def graphRepository

    def setup() {
        session = Mock(IoSession)
        graphRepository = Mock(GraphRepository)
        handler = new CloserThanRequestHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(session, message) == supported

        where:
        message                 || supported
        'CLOSER THAN 10 node-1' || true
        'CLOSER THAN 0 node'    || true
        'CLOSER THAN node-1 10' || false
        'CLOSER THAN node 0'    || false
        'CLOSER THAN 10'        || false
        'CLOSER 10 node-1'      || false

        supportOrNot = supported ? 'support' : 'not support'
    }

    @Unroll
    def "should write message with closer than 10 nodes"() {
        given:
        def command = new CloserThanCommand(new NodeName('node-1'), 10)
        graphRepository.closerThan(command) >> [node('a'), node('c'), node('d'), node('b')]

        when:
        handler.handleMessage(session, 'CLOSER THAN 10 node-1')

        then:
        1 * session.write('a,b,c,d')
    }

    @Unroll
    def "should write message when node not found"() {
        given:
        def command = new CloserThanCommand(new NodeName('node-1'), 10)
        graphRepository.closerThan(command) >> { throw new NodeNotFound() }

        when:
        handler.handleMessage(session, 'CLOSER THAN 10 node-1')

        then:
        1 * session.write('ERROR: NODE NOT FOUND')
    }

    NodeName node(String name) {
        new NodeName(name)
    }

}
