package com.hexagon_software.collibra.interview.handler

import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import spock.lang.Specification
import spock.lang.Unroll

class AddNodeHandlerSpec extends Specification {

    def handler
    def graphRepository

    def setup() {
        graphRepository = Mock(GraphRepository)
        handler = new AddNodeHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(message) == supported

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

        expect:
        handler.handle('ADD NODE 1') == new Response(message)

        where:
        wasAdded || message
        true     || 'NODE ADDED'
        false    || 'ERROR: NODE ALREADY EXISTS'

        addedOrNot = wasAdded ? 'added' : 'not added'
    }

}
