package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames
import com.hexagon_software.collibra.interview.socket.session.SessionId
import com.hexagon_software.collibra.interview.socket.writer.SessionClosingMessageWriter
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

class CollibraIoHandlerSpec extends Specification {

    def handler
    def session
    def matchingHandler
    def notMatchedHandler
    def sessionClosingWriter

    def unsupportedMessageResponse = 'SORRY, I DIDN’T UNDERSTAND THAT'

    def setup() {
        matchingHandler = new MatchingHandler()
        notMatchedHandler = new NotMatchedHandler()
        session = Mock(IoSession)
        sessionClosingWriter = Mock(SessionClosingMessageWriter)

        handler = new CollibraIoHandler([matchingHandler, notMatchedHandler] as Set, sessionClosingWriter)
    }

    def "should write message after session opening"() {
        given:
        def sessionId = new SessionId()
        session.getAttribute(AttributeNames.SESSION_ID) >> sessionId

        when:
        handler.sessionOpened(session)

        then:
        1 * session.write('HI, I\'M ' + sessionId)
    }

    def "should delegate sessionClosingWriter when session idle time exceeded"() {
        when:
        handler.sessionIdle(session, IdleStatus.READER_IDLE)

        then:
        1 * sessionClosingWriter.write(session)
    }

    def "should handle message"() {
        when:
        handler.messageReceived(session, 'OK')

        then:
        1 * session.write("OK")
        0 * session.write("Not OK")
        0 * session.write(unsupportedMessageResponse)
    }

    def "should send response when message not supported"() {
        when:
        handler.messageReceived(session, 'UNKNOWN')

        then:
        1 * session.write(unsupportedMessageResponse)
        0 * session.write("OK")
        0 * session.write("Not OK")
    }

    class MatchingHandler implements MessageHandler {

        @Override
        boolean isSupported(IoSession session, Object message) {
            return message == 'OK'
        }

        @Override
        void handle(IoSession session, Object message) {
            session.write('OK')
        }

    }

    class NotMatchedHandler implements MessageHandler {

        @Override
        boolean isSupported(IoSession session, Object message) {
            return false
        }

        @Override
        void handle(IoSession session, Object message) {
            session.write('NOT OK')
        }

    }

}