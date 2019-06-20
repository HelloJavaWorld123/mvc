package com.jzy.api.model.dealer;

import com.jzy.framework.bean.model.GenericModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <b>功能：</b>渠道商配置参数表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="渠道商配置信息")
public class DealerParam extends GenericModel implements Serializable {
    /*健*/
    @ApiModelProperty(value = "配置参数")
    private String dealerKey;
    /*值*/
    @ApiModelProperty(value = "配置参数值")
    private String dealerValue;
    /*备注*/
    @ApiModelProperty(value = "备注")
    private String dealerNote;
    /**
     * 渠道商主键
     */
    @ApiModelProperty(value = "渠道商id")
    private String dealerId;
}
