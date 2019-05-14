package com.jzy.api.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassNameName OssProperties
 * @Description oss配置文件实体
 * @Author jiazhiyi
 * @DATE 2019/5/13
 * @Version 1.0
 **/
@Component
@Data
public class OssProperties implements Serializable {
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.ossFileURIPre}")
    private String ossFileURIPre;
    @Value("${oss.ossBucketName}")
    private String ossBucketName;
    @Value("${oss.ossEndpoint}")
    private String ossEndpoint;
    @Value("${oss.ossFilePathPre}")
    private String ossFilePathPre;
    @Value("${oss.callbackUrl}")
    private String callbackUrl;
}
