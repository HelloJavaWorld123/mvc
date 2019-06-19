package com.jzy.api.cnd.arch;

import com.jzy.framework.bean.model.GenericModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>渠道商商品定价入参<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="渠道商商品配价充值类型信息")
public class DealerAppPriceTypeCnd {

    /**
     * 充值类型主键
     */
    @ApiModelProperty(value = "商品充值类型id")
    private String aptId;


    /**
     * 是否允许用户自定义输入金额
     */
    @ApiModelProperty(value = "是否允许用户输入自定义金额")
    private Integer isCustom = 0;

    @ApiModelProperty(value = "渠道商商品配价信息")
    private List<DealerAppPriceInfoCnd> dealerAppPriceInfoCnds = new ArrayList<>();


}
