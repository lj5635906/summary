package com.summary.common.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.summary.common.core.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * LocalDateTime 时间反序列化
 *
 * @author jie.luo
 * @since 2022-07-12
 */
public class LocalDateTimeOfEpochMilliDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Number numberValue = jsonParser.getNumberValue();
        if (null == numberValue) {
            return null;
        }
        return DateTimeUtils.convertLocalDateTimeOfTimestamp((Long) numberValue);
    }
}
