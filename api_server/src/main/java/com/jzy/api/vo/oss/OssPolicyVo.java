package com.jzy.api.vo.oss;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameName OssPolicyVo
 * @Description TODO
 * @Author jiazhiyi
 * @DATE 2019/5/13
 * @Version 1.0
 **/
@Data
public class OssPolicyVo implements Serializable {
    private String accessid;
    private String policy;
    private String signature;
    private String dir;
    private String host;
    private String expire;
    private String callbackUrl;
    private String callback;
}
