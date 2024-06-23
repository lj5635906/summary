package com.summary.common.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.summary.common.core.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDate;

/**
 * LocalDate 时间序列化
 *
 * @author jie.luo
 * @since 2022-07-12
 */
public class LocalDateToEpochDaySerializer extends JsonSerializer<LocalDate> {

    @Override
    public void serialize(LocalDate localDate, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(DateTimeUtils.convertLocalDateToEpochDay(localDate));
    }

}
