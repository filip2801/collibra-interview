package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.handler.MessageHandler
import com.hexagon_software.collibra.interview.handler.Response
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

class ReceivedMessageHandlerSpec extends Specification {

    def receivedMessageHandler
    def messageHandlers
    def sessionAwareMessageHandlers
    def session

    def setup() {
        messageHandlers = [new SimpleHandler()] as Set
        sessionAwareMessageHandlers = [new SimpleSessionAwareHandler()] as Set
        receivedMessageHandler = new ReceivedMessageHandler(messageHandlers, sessionAwareMessageHandlers)

        session = Mock(IoSession)
    }

    def "should be handled by message handler"() {
        given:
        def message = 'handler-1'

        when:
        receivedMessageHandler.handle(session, message)

        then:
        1 * session.write('OK-1')
    }

    def "should be handled by session aware message handler"() {
        given:
        def message = 'handler-2'

        when:
        receivedMessageHandler.handle(session, message)

        then:
        1 * session.write('OK-2')
    }

    def "should not be handled by any message handler"() {
        given:
        def message = 'unknown'

        when:
        receivedMessageHandler.handle(session, message)

        then:
        1 * session.write('SORRY, I DIDN’T UNDERSTAND THAT')
    }


    class SimpleHandler implements MessageHandler {

        @Override
        boolean isSupported(Object message) {
            return message == 'handler-1'
        }

        @Override
        Response handle(Object message) {
            new Response('OK-1')
        }

    }

    class SimpleSessionAwareHandler implements SessionAwareMessageHandler {

        @Override
        boolean isSupported(IoSession session, Object message) {
            return message == 'handler-2'
        }

        @Override
        void handle(IoSession session, Object message) {
            session.write('OK-2')
        }

    }
}
