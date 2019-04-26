package com.jzy.api.cnd;

import com.jzy.framework.bean.cnd.GenericCnd;
import lombok.Data;

/**
 * <b>功能：</b>微信授权输入参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class WxOAuthCnd extends GenericCnd {

    /**
     * type 授权类型{"oauth":内置网页授权, "qroauth":网站应用授权}
     */
    private String type = "oauth";

}
