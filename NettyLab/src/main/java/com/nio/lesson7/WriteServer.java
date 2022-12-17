package com.nio.lesson7;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试 write 数据
 */
@Slf4j
public class WriteServer {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(Boolean.FALSE);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(8080));

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                final SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    final SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(Boolean.FALSE);
                    final SelectionKey register = socketChannel.register(selector, SelectionKey.OP_READ, null);
                    //客户端链接后，向客户端发送大量的数据
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < 8000000; i++) {
                        stringBuilder.append('a');
                    }
                    ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(stringBuilder.toString());
                    socketChannel.write(byteBuffer);
                    //数据量太大，可能一次写不完
                    if (byteBuffer.hasRemaining()) {
                        log.info("剩余：{}",byteBuffer.remaining());
                        register.interestOps(register.interestOps() + SelectionKey.OP_WRITE);
                        register.attach(byteBuffer);
                    }
                } else if (selectionKey.isWritable()) {
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    socketChannel.write(byteBuffer);
                    log.info("剩余：{}",byteBuffer.remaining());
                    //写完之后，取消事件
                    if (!byteBuffer.hasRemaining()) {
                        selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                        selectionKey.attach(null);
                    }

                }

            }

        }
    }
}
