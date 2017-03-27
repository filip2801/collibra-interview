package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.handler.exception.MessageHandlingNotSupported
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

import java.util.regex.Pattern

class MatchedByPatternSessionAwareHandlerSpec extends Specification {

    def handler = new SimpleHandler()
    def session = Mock(IoSession)

    def "should handle message"() {
        given:
        def message = "handle"

        when:
        handler.handle(session, message)

        then:
        1 * session.write('passed')
    }

    def "should throw exception when message cannot be handled"() {
        given:
        def message = "sth"

        when:
        handler.handle(session, message)

        then:
        thrown MessageHandlingNotSupported
    }

    class SimpleHandler extends MatchedByPatternSessionAwareHandler {

        @Override
        protected void handleMessage(IoSession session, String message) {
            session.write('passed')
        }

        @Override
        Pattern getPattern() {
            return Pattern.compile("handle")
        }

    }

}
