package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.socket.writer.SessionClosingMessageWriter
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

class SessionClosingRequestHandlerSpec extends Specification {

    def handler
    def session
    def sessionClosingMessageWriter

    def setup(){
        sessionClosingMessageWriter = Mock(SessionClosingMessageWriter)
        session = Mock(IoSession)

        handler = new SessionClosingRequestHandler(sessionClosingMessageWriter)
    }

    def "should close session"() {
        when:
        handler.handle(session, 'BYE MATE!')

        then:
        1 * sessionClosingMessageWriter.write(session)
        1 * session.closeOnFlush()
    }

}
