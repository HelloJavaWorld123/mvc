package com.jzy.api.util;


import com.jzy.api.constant.BaseContext;

import java.util.Map;

public class ConfigUtil {

    private static Map<String, String> configMap; // 配置信息

    /**
     * 获取指定id项的配置信息
     *
     * @param configId 配置项id
     * @return
     */
    public static String get(String configId) {
        return configMap.get(configId);
    }

    /**
     * 获取带域名的URL
     *
     * @param configId
     * @return
     */
    public static String getUrlWithDomain(String configId) {
        String uri = get(configId);
        return get(BaseContext.BASIC_SITE_DNS).concat(uri != null ? uri : "");
    }

    public static void setConfigMap(Map<String, String> configMap) {
        ConfigUtil.configMap = configMap;
        BaseContext.UPLOAD_LOOK_URL = configMap.get("upload_look_url");
    }

}
