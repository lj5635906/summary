package com.summary.component.redis;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.summary.common.core.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_MS_PATTERN;

/**
 * LocalDateTime 时间序列化
 *
 * @author jie.luo
 * @since 2022-07-12
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateTimeUtils.convertLocalDateTimeToString(localDateTime, NORM_DATETIME_MS_PATTERN));
    }

}
