package com.summary.common.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.summary.common.core.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LocalDateTime 时间序列化
 *
 * @author jie.luo
 * @since 2022-07-12
 */
public class LocalDateTimeToEpochMilliSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(DateTimeUtils.convertLocalDateTimeToEpochMilli(localDateTime));
    }

}
