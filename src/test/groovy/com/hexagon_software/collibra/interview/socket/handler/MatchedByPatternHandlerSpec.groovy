package com.hexagon_software.collibra.interview.socket.handler

import org.apache.mina.core.session.DummySession
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

import java.util.regex.Pattern

class MatchedByPatternHandlerSpec extends Specification {

    def handler = new SimpleHandler()
    def session = new DummySession()

    def "should handle message"() {
        given:
        def message = "handle"

        when:
        handler.handle(session, message)

        then:
        session.getAttribute("passed")
    }

    def "should throw exception when message cannot be handled"() {
        given:
        def message = "sth"

        when:
        handler.handle(session, message)

        then:
        thrown MessageHandlingNotSupported
    }

    class SimpleHandler extends MatchedByPatternHandler {

        @Override
        protected void handleMessage(IoSession session, String message) {
            session.setAttribute("passed", true)
        }

        @Override
        Pattern getPattern() {
            return Pattern.compile("handle")
        }

    }

}
