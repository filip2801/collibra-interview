package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames
import com.hexagon_software.collibra.interview.socket.attribute.Client
import com.hexagon_software.collibra.interview.socket.session.SessionId
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

import java.util.regex.Pattern

class CollibraIoHandlerSpec extends Specification {

    def handler
    def session
    def matchingHandler
    def notMatchedHandler

    def unsupportedMessageResponse = 'SORRY, I DIDN’T UNDERSTAND THAT'

    def setup() {
        matchingHandler = new MatchingHandler()
        notMatchedHandler = new NotMatchedHandler()
        session = Mock(IoSession)

        handler = new CollibraIoHandler([matchingHandler, notMatchedHandler] as Set)
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

    def "should write message when session idle time exceeded"() {
        given:
        def client = new Client("R2D2")
        def pattern = Pattern.compile('BYE ' + client + ', WE SPOKE FOR (\\d)+ MS')

        session.getCreationTime() >> System.currentTimeMillis()
        session.getAttribute(AttributeNames.CLIENT) >> client

        when:
        handler.sessionIdle(session, IdleStatus.READER_IDLE)

        then:
        1 * session.write({
            pattern.matcher(it).matches()
        })
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
