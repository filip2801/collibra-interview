package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import org.apache.mina.core.session.IoSession
import spock.lang.Specification
import spock.lang.Unroll

class AddNodeHandlerSpec extends Specification {

    def handler
    def session
    def graphRepository

    def setup() {
        session = Mock(IoSession)
        graphRepository = Mock(GraphRepository)
        handler = new AddNodeHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(session, message) == supported

        where:
        message           || supported
        'ADD NODE node'   || true
        'ADD NODE node-1' || true
        'ADD EDGE node-1' || false
        'ADD node-1'      || false
        'ADD NODE '       || false

        supportOrNot = supported ? 'support' : 'not support'
    }

    @Unroll
    def "should write #message when node was #addedOrNot"() {
        given:
        graphRepository.addNode(_) >> wasAdded

        when:
        handler.handleMessage(session, 'ADD NODE 1')

        then:
        1 * session.write(message)

        where:
        wasAdded || message
        true     || 'NODE ADDED'
        false    || 'ERROR: NODE ALREADY EXISTS'

        addedOrNot = wasAdded ? 'added' : 'not added'
    }

}
