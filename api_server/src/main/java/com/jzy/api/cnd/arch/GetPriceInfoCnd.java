package com.jzy.api.cnd.arch;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <b>功能：</b>查询渠道商商品定价详情接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class GetPriceInfoCnd {

    @NotBlank
    private String aiId;

    @NotBlank
    private String dealerId;
}
