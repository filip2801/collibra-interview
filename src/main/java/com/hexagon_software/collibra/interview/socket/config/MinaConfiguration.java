package com.hexagon_software.collibra.interview.socket.config;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import com.hexagon_software.collibra.interview.socket.session.session.SessionIdFilter;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinaConfiguration {

    @Value("${socket.server.port}")
    private int socketServerPort;

    @Value("${socket.idle.reader}")
    private int readerIdle;

    @Bean(initMethod = "bind", destroyMethod = "unbind")
    IoAcceptor ioAcceptor(DefaultIoFilterChainBuilder filterChainBuilder, IoHandler handler) {
        IoAcceptor ioAcceptor = new NioSocketAcceptor();
        ioAcceptor.setFilterChainBuilder(filterChainBuilder);
        ioAcceptor.setHandler(handler);
        ioAcceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, readerIdle);
        ioAcceptor.setDefaultLocalAddress(new InetSocketAddress(socketServerPort));

        return ioAcceptor;
    }

    @Bean
    DefaultIoFilterChainBuilder filterChainBuilder(ProtocolCodecFilter codecFilter) {
        DefaultIoFilterChainBuilder filterChainBuilder = new DefaultIoFilterChainBuilder();
        filterChainBuilder.addLast("sessionIdFilter", new SessionIdFilter());
        filterChainBuilder.addLast("codecFilter", codecFilter);
        filterChainBuilder.addLast("loggingFilter", new LoggingFilter());

        return filterChainBuilder;
    }

    @Bean
    ProtocolCodecFilter codecFilter() {
        return new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")));
    }

}
