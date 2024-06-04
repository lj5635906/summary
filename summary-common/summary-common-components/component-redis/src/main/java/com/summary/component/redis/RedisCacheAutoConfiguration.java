package com.summary.component.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 设置Redis相关配置
 *
 * @author jie.luo
 * @since 2024/6/2
 */
@Configuration
@ConditionalOnClass
public class RedisCacheAutoConfiguration {


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(redisSerializer);
        // key采用字符串序列化
        template.setKeySerializer(StringRedisSerializer.UTF_8);
        // value序列化
        template.setValueSerializer(redisSerializer);
        // hash key序列化
//        template.setHashKeySerializer(StringRedisSerializer.UTF_8);
        // value
        template.setHashValueSerializer(redisSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisSerializer<Object> redisSerializer) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1)).serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).disableCachingNullValues();

        return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
    }

    @Bean
    public RedisSerializer<Object> redisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 反序列化时候遇到不匹配的属性并不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化时候遇到空对象不抛出异常
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化的时候如果是无效子类型,不抛出异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        // 不使用默认的dateTime进行序列化,
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        objectMapper.registerModule(timeModule);
        // 启用反序列化所需的类型信息,在属性中添加@class
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 配置null值的序列化器
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(objectMapper, null);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}
