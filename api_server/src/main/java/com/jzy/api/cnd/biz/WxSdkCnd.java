package com.jzy.api.cnd.biz;

import com.jzy.framework.bean.cnd.GenericCnd;
import lombok.Data;

/**
 * <b>功能：</b>微信授权输入参数<br>
 */
@Data
public class WxSdkCnd extends GenericCnd {

    /**
     * url 为调用页面的完整 url
     */
    private String url = "";

}
