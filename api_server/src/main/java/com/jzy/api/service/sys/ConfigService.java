package com.jzy.api.service.sys;

import java.util.List;
import java.util.Map;

public interface ConfigService {

    List getAllConfigList();

    Map<String, String> getAllConfigMap();

    /**
     * 获取配置
     * 顺序(redis、map、mysql)
     *
     * @param configId 配置Id
     * @return
     */
    String value(String configId);

    /**
     * 系统域名
     *
     * @return
     */
    String domainUrl();

    /**
     * 获取带系统域名的URL
     *
     * @param configId 配置Id
     * @return
     */
    String valueDomainUrl(String configId);

}
