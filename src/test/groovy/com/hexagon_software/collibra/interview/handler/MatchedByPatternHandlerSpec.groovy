package com.hexagon_software.collibra.interview.handler

import com.hexagon_software.collibra.interview.handler.exception.MessageHandlingNotSupported
import spock.lang.Specification

import java.util.regex.Pattern

class MatchedByPatternHandlerSpec extends Specification {

    def handler = new SimpleHandler()

    def "should handle message"() {
        given:
        def message = "handle"

        expect:
        handler.handle(message) == new Response('passed')
    }

    def "should throw exception when message cannot be handled"() {
        given:
        def message = "sth"

        when:
        handler.handle(message)

        then:
        thrown MessageHandlingNotSupported
    }

    class SimpleHandler extends MatchedByPatternHandler {

        @Override
        protected Response handleMessage(String message) {
            new Response('passed')
        }

        @Override
        Pattern getPattern() {
            return Pattern.compile("handle")
        }

    }

}
