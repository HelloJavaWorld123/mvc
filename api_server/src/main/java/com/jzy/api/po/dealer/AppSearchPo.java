package com.jzy.api.po.dealer;

import lombok.Data;

/**
 * <b>功能：</b>热搜商品列表返回<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppSearchPo {

    /**
     * 商品主键
     */
    private String aiId;

    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品图片地址
     */
    private String icon;


}
