package com.lesson.lesson7;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));

        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        AtomicInteger atomicInteger=new AtomicInteger(0);
        while (true) {
            int write = socketChannel.read(byteBuffer);
            log.info("读取数：{}",write);
            atomicInteger.addAndGet(write);
            byteBuffer.clear();
            log.info("总数：{}",atomicInteger.get());
        }


    }
}
