package com.summary.common.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.summary.common.core.utils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_MS_PATTERN;

/**
 * LocalDateTime 时间反序列化
 *
 * @author jie.luo
 * @since 2022-07-12
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateTime = jsonParser.getText();
        if (StringUtils.isBlank(dateTime)) {
            return null;
        }
        return DateTimeUtils.convertStringToLocalDateTime(dateTime, NORM_DATETIME_MS_PATTERN);
    }
}
