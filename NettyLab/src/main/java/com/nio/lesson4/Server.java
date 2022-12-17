package com.nio.lesson4;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * BIO服务
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));

        List<SocketChannel> channels = new ArrayList<>();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            log.debug("{}", socketChannel);
            channels.add(socketChannel);

            for (SocketChannel channel : channels) {
                channel.read(byteBuffer);
                byteBuffer.flip();
                log.info("messgae:{}", StandardCharsets.UTF_8.decode(byteBuffer));
                byteBuffer.clear();
            }
        }
    }
}
