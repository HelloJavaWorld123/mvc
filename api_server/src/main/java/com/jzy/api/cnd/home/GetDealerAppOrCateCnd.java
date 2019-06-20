package com.jzy.api.cnd.home;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="商品信息列表参数")
public class GetDealerAppOrCateCnd implements Serializable {
    @ApiModelProperty(value = "模糊查询：商品名称")
    private String queryName;
    @ApiModelProperty(value = "渠道商id")
    private String dealerId;
}
