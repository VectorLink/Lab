package service;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import com.netty.version1.protocol.StringMessage;

public class TestLengthFieldDecoder {
    public static void main(String[] args) {
        EmbeddedChannel channel=new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,0,4,0,0),
                new LoggingHandler(LogLevel.DEBUG ),
                new StringMessage()
        );

        ByteBuf byteBuf= ByteBufAllocator.DEFAULT.buffer();

        sendContent(byteBuf, "Hello,world");
        sendContent(byteBuf, "this is a test");
        sendContent(byteBuf, "this is my home");
        channel.writeInbound(byteBuf);
    }

    private static void sendContent(ByteBuf byteBuf, String content) {
        final byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        final int length = bytes.length;

        byteBuf.writeInt(length);
        byteBuf.writeBytes(byteBuf);
    }
}
