package com.jzy.api.cnd.home;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassNameName GetDealerAppOrCateCnd
 * @Description 渠道商查询商品分类或商品
 * @Author jiazhiyi
 * @DATE 2019/5/15
 * @Version 1.0
 **/
@Data
public class GetDealerAppOrCateCnd implements Serializable {
    private String queryName;
    private String dealerId;
}
