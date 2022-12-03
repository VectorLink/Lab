package com.lesson.lesson2;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {
        final ByteBuffer allocate = ByteBuffer.allocateDirect(10);
        Character a='a';
        for (int i=0;i<3;i++){
           allocate.put(a.toString().getBytes());
           a++;
       }
        allocate.flip();
        log.info("{}", StandardCharsets.UTF_8.decode(allocate));
        allocate.flip();
        final byte b = allocate.get();
        log.info("{}",b);
        final byte c = allocate.get();
        log.info("{}",c);
        allocate.compact();
        for (int i=0;i<9;i++){
            allocate.put(a.toString().getBytes());
            a++;
        }
        allocate.flip();
        log.info("{}", StandardCharsets.UTF_8.decode(allocate));


    }
}
