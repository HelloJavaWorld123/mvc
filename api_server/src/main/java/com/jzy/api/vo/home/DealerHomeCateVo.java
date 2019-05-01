package com.jzy.api.vo.home;


import lombok.Data;

import java.util.Date;

@Data
public class DealerHomeCateVo {
    /**
     * 主键Id
     */
    private Long id;
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
     * 图片路径
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
     * 跳转商品或分组的名称
     */
    private String goName;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 0 轮播图 1 推荐分类
     */
    private Integer type;

    /**
     * 最后修改时间
     */
    private Date modifyTime;
}
