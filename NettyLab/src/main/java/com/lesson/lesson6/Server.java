package com.lesson.lesson6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * NIO版本
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
         Selector selector = Selector.open();

        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(Boolean.FALSE);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, null);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        for (;;){
            selector.select();
             Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()){
                final SelectionKey selectionKey = keyIterator.next();
                log.info("{}",selectionKey);
                keyIterator.remove();
                //判断链接实践
                if (selectionKey.isAcceptable()){
                  ServerSocketChannel socketChannel= (ServerSocketChannel) selectionKey.channel();
                    final SocketChannel socketChannel1 = socketChannel.accept();
                    socketChannel1.configureBlocking(Boolean.FALSE);
                    socketChannel1.register(selector,SelectionKey.OP_READ,null);
                }else if (selectionKey.isReadable()){
                    try {
                        SocketChannel socketChannel= (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer= (ByteBuffer) selectionKey.attachment();
                        if (Objects.isNull(byteBuffer)){
                            byteBuffer= ByteBuffer.allocate(10);
                        }
                        log.info("byteBuffer:size{}",byteBuffer.capacity());
                        final int read = socketChannel.read(byteBuffer);
                        if (read==-1){
                            selectionKey.cancel();
                        }
                        split(byteBuffer);
                        if (byteBuffer.position()==byteBuffer.limit()){
                             ByteBuffer newByteBuffer = ByteBuffer.allocate(byteBuffer.capacity() * 2);
                           //将原始数据切换到写模式，然后将原来的数据放到新的数据里面去
                             byteBuffer.flip();
                             newByteBuffer.put(byteBuffer);
                             byteBuffer=newByteBuffer;
                        }
                        selectionKey.attach(byteBuffer);
                    } catch (Exception e) {
                      log.info("发生异常信息：",e);
                      selectionKey.cancel();
                    }
                }


            }
        }
    }
    private static void split(ByteBuffer buffer) {
        //切换读模式
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            //找到一条完整的消息
            if (buffer.get(i)=='\n') {
                final int length = i + 1 - buffer.position();
                ByteBuffer allocate = ByteBuffer.allocate(length);
                for (int j = 0; j < length; j++) {
                    allocate.put(buffer.get());
                }
                allocate.flip();
                log.info("{}",StandardCharsets.UTF_8.decode(allocate));
            }
        }
        buffer.compact();

    }
}
