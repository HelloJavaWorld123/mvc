package com.jzy.api.model.Home;


import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>前台营业首页推荐轮播图和推荐分类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HomeRecommendCate extends GenericModel {

    /**
     * 渠道商Id
     */
    private String dealerId;

    /**
     * 轮播图名称
     */
    private String rciName;

    /**
     * 轮播图排序
     */
    private Integer sortDesc;

    /**
     * 图片Id
     */
    private String imageId;

    /**
     * 跳转类型 默认1-商品 2-分组
     */

    private Integer goType;

    /**
     * 跳转到的id
     */
    private String goId;


    /**
     * 跳转商品或分组的名称
     */
    private String goName;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    private Integer state;

    /**
     * 0 轮播图 1 推荐分类
     */
    private Integer type;

}
