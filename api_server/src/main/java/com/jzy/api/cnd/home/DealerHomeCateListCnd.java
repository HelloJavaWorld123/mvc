package com.jzy.api.cnd.home;

import com.jzy.framework.bean.cnd.PageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="首页分类和轮播图分页查询参数")
public class DealerHomeCateListCnd extends PageCnd {

    /**
     * 渠道商Id
     */
    @ApiModelProperty(value = "渠道商id")
    private String dealerId;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    @ApiModelProperty(value = "0禁用，1启用")
    private Integer status;

    /**
     * 0 轮播图 1 推荐分类
     */
    @ApiModelProperty(value = "0轮播图，1推荐分类")
    private Integer type;
}
