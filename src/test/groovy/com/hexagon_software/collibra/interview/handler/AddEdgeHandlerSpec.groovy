package com.hexagon_software.collibra.interview.handler

import com.hexagon_software.collibra.interview.graph.command.AddEdgeCommand
import com.hexagon_software.collibra.interview.graph.model.GraphRepository
import com.hexagon_software.collibra.interview.graph.model.NodeName
import spock.lang.Specification
import spock.lang.Unroll

class AddEdgeHandlerSpec extends Specification {

    def handler
    def graphRepository

    def setup() {
        graphRepository = Mock(GraphRepository)
        handler = new AddEdgeHandler(graphRepository)
    }

    @Unroll
    def "should #supportOrNot message #message"() {
        expect:
        handler.isSupported(message) == supported

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

        expect:
        handler.handle('ADD EDGE node-1 node-2 10') == new Response(message)

        where:
        wasAdded || message
        true     || 'EDGE ADDED'
        false    || 'ERROR: NODE NOT FOUND'

        addedOrNot = wasAdded ? 'added' : 'not added'
    }

}
