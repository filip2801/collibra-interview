package com.hexagon_software.collibra.interview.handler

import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import com.hexagon_software.collibra.interview.graph.model.NodeName
import org.apache.mina.core.session.IoSession
import spock.lang.Specification
import spock.lang.Unroll

class RemoveNodeHandlerSpec extends Specification {

    def handler
    def graphRepository
    def session

    def setup() {
        session = Mock(IoSession)
        graphRepository = Mock(GraphRepository)
        handler = new RemoveNodeHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(message) == supported

        where:
        message              || supported
        'REMOVE NODE node-1' || true
        'REMOVE NODE 1'      || true
        'REMOVE NODE --'     || true
        'REMOVE NODE '       || false
        'REMOVE node-1 '     || false

        supportOrNot = supported ? 'support' : 'not support'
    }

    @Unroll
    def "should write message #message when node #removedOrNot"() {
        given:
        graphRepository.removeNode(new NodeName('node-1')) >> wasRemoved

        expect:
        handler.handle('REMOVE NODE node-1') == new Response(message)

        where:
        message                 || wasRemoved
        'NODE REMOVED'          || true
        'ERROR: NODE NOT FOUND' || false

        removedOrNot = wasRemoved ? 'removed' : 'not removed'
    }

}
