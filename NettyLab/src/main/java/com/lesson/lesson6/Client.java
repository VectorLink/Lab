package com.lesson.lesson6;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) throws Exception {
        final SocketChannel localhost = SocketChannel.open();
        localhost.connect(new InetSocketAddress("localhost",8080));
        localhost.write(ByteBuffer.wrap("Hello,world\nI'm lisi\n".getBytes(StandardCharsets.UTF_8)));
        System.in.read();
    }
}
