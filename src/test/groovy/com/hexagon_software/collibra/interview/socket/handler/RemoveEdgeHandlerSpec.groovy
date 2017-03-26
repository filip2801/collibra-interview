package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.graph.command.RemoveEdgeCommand
import com.hexagon_software.collibra.interview.graph.exception.NodeNotFound
import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import com.hexagon_software.collibra.interview.graph.model.NodeName
import org.apache.mina.core.session.IoSession
import spock.lang.Specification
import spock.lang.Unroll

class RemoveEdgeHandlerSpec extends Specification {

    def handler
    def session
    def graphRepository

    def setup() {
        session = Mock(IoSession)
        graphRepository = Mock(GraphRepository)
        handler = new RemoveEdgeHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(session, message) == supported

        where:
        message                      || supported
        'REMOVE EDGE node node-1'    || true
        'REMOVE EDGE node-1 node-2'  || true
        'REMOVE EDGE node-1 node-2 ' || false
        'REMOVE node-1 node-2'       || false
        'REMOVE EDGE node-1'         || false

        supportOrNot = supported ? 'support' : 'not support'
    }

    @Unroll
    def "should write message when edge removing ends with success"() {
        given:
        def command = new RemoveEdgeCommand(new NodeName('node-1'), new NodeName('node-2'))

        when:
        handler.handle(session, 'REMOVE EDGE node-1 node-2')

        then:
        1 * session.write('EDGE REMOVED')
        1 * graphRepository.removeEdges(command)
    }

    @Unroll
    def "should write message when edge removing failed"() {
        given:
        def command = new RemoveEdgeCommand(new NodeName('node-1'), new NodeName('node-2'))
        graphRepository.removeEdges(command) >> { throw new NodeNotFound() }

        when:
        handler.handle(session, 'REMOVE EDGE node-1 node-2')

        then:
        1 * session.write('ERROR: NODE NOT FOUND')
    }

}
