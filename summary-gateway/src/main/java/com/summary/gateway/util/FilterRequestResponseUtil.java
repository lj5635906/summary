package com.summary.gateway.util;

import io.netty.buffer.ByteBufAllocator;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * gateway 响应相关
 *
 * @author jie.luo
 * @since 2024/6/5
 */
public class FilterRequestResponseUtil {

    /**
     * spring cloud gateway 获取post请求的body体
     *
     * @param body
     * @return
     */
    public static String resolveBodyFromRequest(Flux<DataBuffer> body) {
        AtomicReference<String> bodyRef = new AtomicReference<>();
        // 缓存读取的request body信息
        body.subscribe(dataBuffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(dataBuffer.asByteBuffer());
            DataBufferUtils.release(dataBuffer);
            bodyRef.set(charBuffer.toString());
        });
        // 获取request body
        return bodyRef.get();

    }

    /**
     * 读取body内容
     *
     * @param body
     * @return
     */
    public static String resolveBodyFromRequest2(Flux<DataBuffer> body) {
        StringBuilder sb = new StringBuilder();
        body.subscribe(buffer -> {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            sb.append(bodyString);
        });
        return formatStr(sb.toString());
    }

    /**
     * 去掉空格,换行和制表符
     *
     * @param str
     * @return
     */
    private static String formatStr(String str) {
        if (str != null && str.length() > 0) {
            Pattern p = compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            return m.replaceAll("");
        }
        return str;
    }

    public static DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    public static Mono<Void> response(ServerWebExchange exchange, String responseContent) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.OK);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = responseContent.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }
}
