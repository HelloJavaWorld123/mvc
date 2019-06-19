package com.jzy.api.cnd.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>应用类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="产品类型保存参数")
public class AppTypeCnd {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 应用分类名称
     */
    @ApiModelProperty(value = "商品所属类型名称")
    private String name;

    /**
     * 序列:值越小越靠前,默认1
     */
    @ApiModelProperty(value = "商品类型排序")
    private Integer sort;



}
