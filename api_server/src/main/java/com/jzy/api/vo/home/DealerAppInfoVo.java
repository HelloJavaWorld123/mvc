package com.jzy.api.vo.home;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameName DealerAppCateVo
 * @Description TODO
 * @Author jiazhiyi
 * @DATE 2019/5/15
 * @Version 1.0
 **/
@Data
public class DealerAppInfoVo implements Serializable {
    private String appId;
    private String appName;
    private String appCode;
    private String appPrice;
    private String priceId;
}
