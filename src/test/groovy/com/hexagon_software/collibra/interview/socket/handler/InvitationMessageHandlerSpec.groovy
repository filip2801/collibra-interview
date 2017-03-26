package com.hexagon_software.collibra.interview.socket.handler

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames
import com.hexagon_software.collibra.interview.socket.attribute.Client
import org.apache.mina.core.session.IoSession
import spock.lang.Specification
import spock.lang.Unroll

class InvitationMessageHandlerSpec extends Specification {

    def handler = new InvitationHandler()
    def session = Mock(IoSession)

    @Unroll
    def "#shouldOrNot support message #message"() {
        given:

        expect:
        handler.isSupported(session, message) == supported

        where:
        message                    || supported
        'HI, I\'M ' + uuid()       || true
        'HI, I\'M a--distance'     || true
        'HI, I\'M --'              || true
        'HI, I\'M Filip'           || true
        'HI, I\'M'                 || false
        'HI, I\'M ' + uuid() + " " || false
        'HI I\'M ' + uuid()        || false
        'hi, i\'m ' + uuid()       || false
        null                       || false

        shouldOrNot = supported ? 'should' : 'should not'
    }

    def "should handle message"() {
        given:
        def name = uuid().toString()
        def message = "HI, I'M " + name

        when:
        handler.handle(session, message)

        then:
        1 * session.setAttribute(AttributeNames.CLIENT, new Client(name))
        1 * session.write("HI " + name)
    }

    def uuid() {
        UUID.randomUUID()
    }

}
