package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.ReportPageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>后端订单列表接口查询<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="订单参数")
public class BackOrderCnd extends ReportPageCnd {
    /**
     * 渠道商id
     */
    @ApiModelProperty(value = "渠道商id")
    private Integer dealerId;
    /**
     * 搜索框条件
     * 商户编号、订单编号、流水编号
     */
    @ApiModelProperty(value = "模糊搜索：商户编号，订单编号，流水号")
    private String key;
    /**
     * sup状态
     */
    @ApiModelProperty(value = "sup状态：0未提交1已提交2成功3失败")
    private Integer supStatus;
    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态0待支付,1充值中,2充值成功,3充值失败,4充值关闭 5 退款成功")
    private Integer status;
}
