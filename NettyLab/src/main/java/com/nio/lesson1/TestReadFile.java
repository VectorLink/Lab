package com.nio.lesson1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试文件读入
 */
@Slf4j
public class TestReadFile {
    public static void main(String[] args) throws IOException {
        testChannelRead();
        testRandomAccess();
    }

    private static void testRandomAccess() throws IOException {
        final RandomAccessFile randomAccessFile = new RandomAccessFile(
               new File("D:\\IdeaWorkSpace\\Lab\\NettyLab\\src\\main\\resources\\test.txt"),"r");

        while (true){
            final String s = randomAccessFile.readLine();
            if (StringUtils.isBlank(s)){
                break;
            }

            log.info("{}", new String(s.getBytes(StandardCharsets.ISO_8859_1),StandardCharsets.UTF_8) );

        }
    }

    private static void testChannelRead() throws IOException {
        final FileChannel channel = new FileInputStream("D:\\IdeaWorkSpace\\Lab\\NettyLab\\src\\main\\resources\\test.txt").getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        AtomicInteger atomicInteger=new AtomicInteger(0);
        while (true) {
            final int frequency = atomicInteger.addAndGet(1);
            log.info("次数：{}",frequency);
            final int read = channel.read(byteBuffer);
            if (read <= 0) {
                break;
            }
            // 切换读模式，将指针放到起始位置
            byteBuffer.flip();
            log.info("{}", StandardCharsets.UTF_8.decode(byteBuffer));
            // 切换到写模式
            byteBuffer.clear();
        }
        channel.close();
    }

}
