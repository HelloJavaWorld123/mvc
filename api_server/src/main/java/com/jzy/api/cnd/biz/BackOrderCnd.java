package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.ReportPageCnd;
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
public class BackOrderCnd extends ReportPageCnd {
    /**
     * 搜索框条件
     * 商户编号、订单编号、流水编号
     */
    private String key;
    /**
     * sup状态
     */
    private Integer supStatus;
    /**
     * 订单状态
     */
    private Integer status;
}
