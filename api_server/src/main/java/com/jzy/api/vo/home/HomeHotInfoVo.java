package com.jzy.api.vo.home;

import lombok.Data;

/**
 * <b>功能：</b>首页推荐分组商品详细信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190516&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HomeHotInfoVo {

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
     * 主图图片url
     */
    private String imageUrl;

    /**
     * 跳转类型 默认1-商品 2-分组
     */
    private Integer goType;

    /**
     * 跳转到的id
     */
    private String goId;
    /**
     * 跳转商品
     */
    private String goName;

    /**
     * 跳转商品价格
     */
    private String goPrice;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    private Integer state;


    /**
     * 0上中  1左上 2左下 3 右上 4 右下
     */
    private Integer position;
    /**
     * 分组id
     */
    private String groupId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 分组顺序 从0开始，数字越小 排在越上面
     */
    private Integer groupSort;
}
