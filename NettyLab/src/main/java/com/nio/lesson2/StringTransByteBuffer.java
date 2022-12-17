package com.nio.lesson2;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringTransByteBuffer {
    public static void main(String[] args) {
        String hello="hello";
        //1.
        final ByteBuffer allocate = ByteBuffer.allocate(16);
        allocate.put(hello.getBytes(StandardCharsets.UTF_8));

        //2.
        final ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(hello);

        //3.

        final ByteBuffer wrap = ByteBuffer.wrap(hello.getBytes());


        //4 转为字符串

        final String s = Charset.defaultCharset().decode(allocate).toString();
        final String s1 = StandardCharsets.UTF_8.decode(byteBuffer).toString();
        final String s2 = Charset.forName("UTF-8").decode(wrap).toString();
        log.info("s:{},s1:{},s2:{}",s,s1,s2);
    }
}
