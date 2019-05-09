package com.jzy.api.model.sys;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

/**
 * <b>功能：</b>前台用户信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190509&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class UserAuth extends GenericModel {
    /**
     * 用户userId
     */
    private String userId;
    /**
     * 用户openId
     */
    private String openId;
    /**
     * 是否微信授权
     * 1：是
     */
    private Integer isWxAuth = 0;
    /**
     * 商户id
     */
    private Integer dealerId;
}
