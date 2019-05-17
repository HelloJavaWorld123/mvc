package com.jzy.api.vo.home;

import lombok.Data;

/**
 * <b>功能：</b>首页推荐分组商品列表信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190516&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HomeHotVo {

    /**
     * 主键
     */
    private String id;
    /**
     * 商户基本信息id
     */
    private String dealerId;

    /**
     * 轮播图名称
     */
    private String rciName;

    /**
     * 主图图片id
     */
    private String imageUrl;

    /**
     * 跳转商品
     */
    private String goName;

    /**
     * 商品位置
     */
    private String position;
}
