package com.jzy.framework.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.JsonConverter;

public final class JsonConverterUtils {

    private static class JsonConverterHolder {
        private static JsonConverter jc;
    }

    private static class JsonMapperHolder {
        private static ObjectMapper om;
    }

    public static JsonConverter getJc() {
        return JsonConverterHolder.jc;
    }

    public static ObjectMapper getOm() {
        return JsonMapperHolder.om;
    }

    public static void setJc(JsonConverter jsonConverter) {
        if (JsonConverterHolder.jc != null) {
            throw new BusException("System do not allow to change this object.");
        }

        JsonConverterHolder.jc = jsonConverter;
    }

    public static void setOm(ObjectMapper objectMapper) {
        if (JsonMapperHolder.om != null) {
            throw new BusException("System do not allow to change this object.");
        }

        JsonMapperHolder.om = objectMapper;
        JsonMapperHolder.om.setSerializationInclusion(JsonInclude.Include.NON_NULL);//空属性不序列化
        JsonMapperHolder.om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); //json key可以没有双引号
        JsonMapperHolder.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //忽略解析未知的字段
    }

}
