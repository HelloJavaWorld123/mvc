package com.jzy.api.cnd.home;

import com.jzy.framework.bean.cnd.PageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>首页推荐分组<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="首页推荐分页查询参数")
public class HomeHotGroupCnd extends PageCnd {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 商户信息id
     */
    @ApiModelProperty(value = "渠道商id")
    private Long dealerId;

    /**
     * 分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String groupName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private String groupSort;

    /**
     * 状态0禁用，1启用
     */
    @ApiModelProperty(value = "0禁用，1启用")
    private String status;
}
