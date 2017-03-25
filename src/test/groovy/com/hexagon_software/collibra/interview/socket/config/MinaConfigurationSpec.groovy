package com.hexagon_software.collibra.interview.socket.config

import com.hexagon_software.collibra.interview.socket.session.SessionIdFilter
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder
import org.apache.mina.core.service.IoHandler
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.logging.LoggingFilter
import spock.lang.Specification

class MinaConfigurationSpec extends Specification {

    def minaConfiguration = new MinaConfiguration()

    def "should create filter chain builder"() {
        given:
        def codecFilter = Mock(ProtocolCodecFilter)

        when:
        def chain = minaConfiguration.filterChainBuilder(codecFilter)

        then:
        chain.getEntry(SessionIdFilter)
        chain.getEntry(LoggingFilter)
        chain.getEntry(ProtocolCodecFilter)
    }

    def "should create ioAcceptor bean"() {
        given:
        def port = 60000
        def idle = 10

        minaConfiguration.socketServerPort = port
        minaConfiguration.readerIdle = idle

        def filterChainBuilder = Mock(DefaultIoFilterChainBuilder)
        def ioHandler = Mock(IoHandler)

        when:
        def ioAcceptor = minaConfiguration.ioAcceptor(filterChainBuilder, ioHandler)

        then:
        ioAcceptor.filterChainBuilder == filterChainBuilder
        ioAcceptor.handler == ioHandler
        ioAcceptor.sessionConfig.getIdleTime(IdleStatus.READER_IDLE) == idle
        ioAcceptor.defaultLocalAddress == new InetSocketAddress(port)
    }

}
