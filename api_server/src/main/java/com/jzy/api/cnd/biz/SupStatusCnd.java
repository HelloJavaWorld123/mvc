package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.GenericCnd;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * <b>功能：</b>更新SUP状态<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190518&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SupStatusCnd extends GenericCnd {
    /**
     * 订单id
     */
    @NotBlank
    private String orderId;
    /**
     * sup状态
     * 2：成功；3：失败
     */
    @Range(min = 2, max = 3)
    @NotNull
    private Integer supStatus = 0;
}
