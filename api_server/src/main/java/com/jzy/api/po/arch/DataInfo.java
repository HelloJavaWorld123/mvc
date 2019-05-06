package com.jzy.api.po.arch;

import lombok.Data;

/**
 * <b>功能：</b>请求参数解析产生的数据<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DataInfo {

    /**
     * 买家Id
     */
    private String userId;

    /**
     * 是否正确
     */
    private Boolean flag;

    /**
     * 签名作为taken
     */
    private String taken;
}
