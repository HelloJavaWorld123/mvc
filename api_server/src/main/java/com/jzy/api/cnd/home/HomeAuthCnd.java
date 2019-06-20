package com.jzy.api.cnd.home;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <b>功能：</b>加密渠道商信息传参<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="加密渠道商信息参数")
public class HomeAuthCnd {
    /**
     * 经销商标识
     */
    @ApiModelProperty(value = "渠道商唯一标识")
    @NotBlank
    private String businessID;

    /**
     * 用户UserId
     */
    @ApiModelProperty(value = "用户id")
    @NotBlank
    private String userId;
}



