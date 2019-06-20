package com.jzy.api.cnd.home;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value="首页轮播图和推荐分类删除参数")
public class CommonDeleteBatchCnd {

    /**
     * 商品主键列表
     */
    @ApiModelProperty(value = "商品或者推荐分类id列表")
    private List<Long> ids = new ArrayList<>();


}
