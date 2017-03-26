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
    def sessionClosingWriter
    def receivedMessageHandler

    def setup() {
        session = Mock(IoSession)
        sessionClosingWriter = Mock(SessionClosingMessageWriter)
        receivedMessageHandler = Mock(ReceivedMessageHandler)

        handler = new CollibraIoHandler(receivedMessageHandler, sessionClosingWriter)
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

    def "should delegate message handling"() {
        when:
        handler.messageReceived(session, 'OK')

        then:
        1 * receivedMessageHandler.handle(session, 'OK')
    }

}
