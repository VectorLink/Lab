package com.nio.lesson8;

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
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.extern.slf4j.Slf4j;

/**
 * 多线程版NIO
 */
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));
        Selector selector=Selector.open();
        Thread.currentThread().setName("boss");
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT,null);
         Worker worker = new Worker("worker-0");
        for (;;){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()){
                    log.info("begin to connected ====");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    log.info("before register=====,{}",socketChannel.getRemoteAddress());
                    worker.register(socketChannel);
                    log.info("after register=====");
                }
            }
        }

    }

    /**
     * 内部工作类
     */
    @Slf4j
    static class Worker implements Runnable {
        /**
         * 工作线程
         */
        Thread thread;
        String name;
        /**
         * 工作线程selector
         */
        Selector worker;

        ConcurrentLinkedQueue<Runnable> registerTask=new ConcurrentLinkedQueue<>();

        public volatile Boolean start=false;

        public Worker(String name) {
            this.name = name;
        }

        public void register(SocketChannel socketChannel) throws IOException {
            if (!start) {
                thread = new Thread(this, name);
                worker = Selector.open();
                thread.start();
                start=true;
            }
            registerTask.add(()->{
                try {
                    socketChannel.register(worker,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            worker.wakeup();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    worker.select();
                    final Runnable poll = registerTask.poll();
                    if (Objects.nonNull(poll)){
                        poll.run();
                    }
                    final Iterator<SelectionKey> iterator = worker.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        final SelectionKey next = iterator.next();
                        iterator.remove();
                        if (next.isReadable()) {
                            final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            SocketChannel socketChannel = (SocketChannel) next.channel();
                            socketChannel.read(byteBuffer);
                            //输出
                            byteBuffer.flip();
                            log.info("MSG:{}", StandardCharsets.UTF_8.decode(byteBuffer));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
