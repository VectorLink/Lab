package com.nio.lesson5;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

/**
 * BIO服务
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(Boolean.FALSE);
        List<SocketChannel> channels = new ArrayList<>();
        final ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (Objects.nonNull(socketChannel)) {
                    log.debug("{}", socketChannel);
                    socketChannel.configureBlocking(Boolean.FALSE);
                    channels.add(socketChannel);
                }

                for (SocketChannel channel : channels) {
                    int read = channel.read(byteBuffer);
                    if (read>0) {
                        byteBuffer.flip();
                        log.info("messgae:{}", StandardCharsets.UTF_8.decode(byteBuffer));
                        byteBuffer.clear();
                    }
                }
            } catch (Exception e) {
                log.info("错误",e);
            }
        }
    }
}
