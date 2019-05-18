package com.jzy.api.model.Home;

import lombok.Data;

/**
 * <b>功能：</b>首页分组推荐详情<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HomeRecommendHotDetail {

    /**
     * 轮播图名称
     */
    private String rciName;

    /**
     * 主图图片id
     */
    private String imageId;

    /**
     * 主图图片url
     */
    private String imageUrl;

    /**
     * 跳转类型 默认1-商品 2-分组
     */
    private Integer goType;


    /**
     * 如果跳转的是商品则返回商品的信息
     */
    private HotAppInfoDetail hotAppInfoDetail;

    /**
     * 跳转到的id
     */
    private String goId;
    /**
     * 跳转商品
     */
    private String goName;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    private Integer state;

    /**
     * 0上中  1左上 2左下 3 右上 4 右下
     */
    private Integer position;
}





