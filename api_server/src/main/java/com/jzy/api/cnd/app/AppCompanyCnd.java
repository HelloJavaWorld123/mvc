package com.jzy.api.cnd.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>厂商表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="厂商保存更新参数")
public class AppCompanyCnd {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "厂商名称")
    private String name;

    /**
     * 厂商icon
     */
    @ApiModelProperty(value = "厂商图片")
    private String icon;
}
