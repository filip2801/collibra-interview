package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.socket.writer.SessionEndingMessageWriter
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

class SessionClosingRequestHandlerSpec extends Specification {

    def handler
    def session
    def sessionEndingMessageWriter

    def setup(){
        sessionEndingMessageWriter = Mock(SessionEndingMessageWriter)
        session = Mock(IoSession)

        handler = new SessionClosingRequestHandler(sessionEndingMessageWriter)
    }

    def "should close session"() {
        when:
        handler.handle(session, 'BYE MATE!')

        then:
        1 * sessionEndingMessageWriter.write(session)
        1 * session.closeOnFlush()
    }

}
