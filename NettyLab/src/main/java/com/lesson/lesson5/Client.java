package com.lesson.lesson5;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) throws Exception {
        final SocketChannel localhost = SocketChannel.open();
        localhost.connect(new InetSocketAddress("localhost",8080));
        log.info("localhost:");
    }
}
