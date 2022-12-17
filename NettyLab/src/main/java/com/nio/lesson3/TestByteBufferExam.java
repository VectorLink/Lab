package com.nio.lesson3;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * 粘包-半包处理
 */
@Slf4j
public class TestByteBufferExam {
    public static void main(String[] args) {
        final ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put("Hello,world\nI'm lisi\nH".getBytes(StandardCharsets.UTF_8));
        split(buffer);
        buffer.put("ow are you?\n".getBytes(StandardCharsets.UTF_8));
        split(buffer);
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
