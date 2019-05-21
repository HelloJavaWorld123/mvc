package com.jzy.api.po.app;

import lombok.Data;

/**
 * <b>功能：</b>商品状态<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190521&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppStatus {
    /**
     * 渠道商的商品状态
     * 状态 1 启用  0 禁用  默认0
     */
    private Integer dealerAppStatus;
    /**
     * 商品状态
     * 状态 0下架 1上架 2删除
     */
    private Integer appStatus;

}
