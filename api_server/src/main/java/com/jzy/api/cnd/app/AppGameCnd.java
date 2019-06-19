package com.jzy.api.cnd.app;

import com.jzy.framework.bean.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>游戏表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190514&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="根据厂商添加或更新游戏参数")
public class AppGameCnd {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private String pId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "游戏名称")
    private String name;

    /**
     * 类型 1游戏2大区3服务
     */
    @ApiModelProperty(value = "类型：1游戏2大区3服务")
    private String type;

    /**
     * 状态：1有区有服  2有区没服  3有服没区(一个默认大区)
     */
    @ApiModelProperty(value = "状态：1有区有服  2有区没服  3有服没区(一个默认大区)")
    private String status;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String sort;


}
