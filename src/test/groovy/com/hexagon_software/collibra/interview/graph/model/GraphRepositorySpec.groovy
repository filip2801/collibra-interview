package com.hexagon_software.collibra.interview.graph.model

import spock.lang.Specification
import spock.lang.Unroll

class GraphRepositorySpec extends Specification {

    def repository = new GraphRepository()
    def graph = Mock(Graph)

    def setup() {
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

}
