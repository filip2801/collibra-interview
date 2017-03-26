package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand
import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import com.hexagon_software.collibra.interview.graph.model.NodeName
import org.apache.mina.core.session.IoSession
import spock.lang.Specification
import spock.lang.Unroll

class AddEdgeHandlerSpec extends Specification {

    def handler
    def session
    def graphRepository

    def setup() {
        session = Mock(IoSession)
        graphRepository = Mock(GraphRepository)
        handler = new AddEdgeHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(session, message) == supported

        where:
        message                      || supported
        'ADD EDGE node node-1 1'     || true
        'ADD EDGE node-1 node-2 10'  || true
        'ADD EDGE -- - 0'            || true
        'ADD EDGE node-1 node-2 -10' || false
        'ADD EDGE node-1 node-2 -'   || false
        'ADD EDGE node-1 node-2 a'   || false
        'ADD EDGE node-1 node-2'     || false
        'ADD EDGE node-1 10'         || false
        'ADD EDGE'                   || false

        supportOrNot = supported ? 'support' : 'not support'
    }

    @Unroll
    def "should write #message when edge was #addedOrNot"() {
        given:
        def command = new AddEdgeCommand(new NodeName('node-1'), new NodeName('node-2'), 10)
        graphRepository.addEdge(command) >> wasAdded

        when:
        handler.handle(session, 'ADD EDGE node-1 node-2 10')

        then:
        1 * session.write(message)

        where:
        wasAdded || message
        true     || 'EDGE ADDED'
        false    || 'ERROR: NODE NOT FOUND'

        addedOrNot = wasAdded ? 'added' : 'not added'
    }

}
