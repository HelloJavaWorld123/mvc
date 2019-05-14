package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.GenericCnd;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <b>功能：</b>查询卡密输入参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190514&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class CardPwdCnd extends GenericCnd {
    /**
     * 卡密id
     */
    @NotBlank
    private String cardPwdId;
    /**
     * 卡号
     */
    @NotBlank
    private String cardNo;

}
