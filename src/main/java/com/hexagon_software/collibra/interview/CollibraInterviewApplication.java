package com.hexagon_software.collibra.interview;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CollibraInterviewApplication {

    public static void main(String[] args) throws IOException, InterruptedException {
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.setHandler(new SimpleHandler());
        acceptor.getSessionConfig().setIdleTime(IdleStatus.READER_IDLE, 30);
        acceptor.bind(new InetSocketAddress(50000));
    }

}
