package com.jzy.api.cnd.home;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value="更新状态参数")
public class CommonUpdateStatusCnd {

    /**
     * 是否启用  0是禁用   1是启用
     */
    @ApiModelProperty(value = "0禁用，1启用")
    private Integer status;

    @ApiModelProperty(value = "商品或者推荐分类id列表")
    private List<Long> ids = new ArrayList<>();
}
