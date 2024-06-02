package com.summary.component.redis;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.summary.common.core.utils.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;

/**
 * LocalDate 时间反序列化
 *
 * @author jie.luo
 * @since 2022-07-12
 */
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateTime = jsonParser.getText();
        if (StringUtils.isBlank(dateTime)) {
            return null;
        }
        return DateTimeUtils.convertStringToLocalDate(dateTime, DatePattern.NORM_DATE_PATTERN);
    }
}
