package com.hexagon_software.collibra.interview.socket.writer

import com.hexagon_software.collibra.interview.socket.attribute.AttributeNames
import com.hexagon_software.collibra.interview.socket.attribute.Client
import org.apache.mina.core.session.IoSession
import spock.lang.Specification

import java.util.regex.Pattern

class SessionClosingMessageWriterSpec extends Specification {

    def writer = new SessionClosingMessageWriter()
    def session = Mock(IoSession)

    def "should write message"() {
        given:
        def client = new Client("R2D2")
        def pattern = Pattern.compile('BYE ' + client + ', WE SPOKE FOR (\\distance)+ MS')

        session.getCreationTime() >> System.currentTimeMillis()
        session.getAttribute(AttributeNames.CLIENT) >> client

        when:
        writer.write(session)

        then:
        1 * session.write({
            pattern.matcher(it).matches()
        })
    }

}
