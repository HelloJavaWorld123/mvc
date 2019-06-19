package com.jzy.api.cnd.arch;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>批量修改商品定价上下架装填<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="渠道商商品批量上下架参数")
public class BatchUpdateStatusCnd {

    /**
     * 渠道商Id
     */
    @ApiModelProperty(value = "渠道商主键id")
    private String dealerId;
    /**
     * 商品Id列表
     */
    @ApiModelProperty(value = "商品id列表")
    private List<String> aiIdList = new ArrayList<>(10);


    /**
     * 商品需要进行的修改状态   状态 0 下架 1上架
     */
    @ApiModelProperty(value = "0下架，1上架")
    private Integer status;

}
