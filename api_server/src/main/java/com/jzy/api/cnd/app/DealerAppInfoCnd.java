package com.jzy.api.cnd.app;

import com.jzy.framework.bean.model.GenericModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>渠道商商品表入参<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="渠道商商品信息")
public class DealerAppInfoCnd{

    /**
     * 经销商id
     */
    @ApiModelProperty(value = "渠道商主键id")
    private String dealerId;
    /**
     * 应用Id
     */
    @ApiModelProperty(value = "商品主键id")
    private String aiId;
    /**
     * 是否热门
     */
    @ApiModelProperty(value = "是否热门(默认值0 否 , 1是)")
    private Integer isHot=0;
    /**
     * 是否推荐
     */
    @ApiModelProperty(value = "是否推荐(默认值0 否, 1是)")
    private Integer isReco=0;
    /**
     * 状态  0 下架  1上架  2删除
     */
    @ApiModelProperty(value = "状态 0下架 1上架 2未配置")
    private Integer status=1;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort=1000;

}
