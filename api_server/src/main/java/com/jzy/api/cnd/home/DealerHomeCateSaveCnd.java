package com.jzy.api.cnd.home;

import com.jzy.api.model.app.FileInfo;
import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;

@Data
public class DealerHomeCateSaveCnd {

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
     * 图片Id
     */
    private String imageId;

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
    private Integer state;

    /**
     * 0 轮播图 1 推荐分类
     */
    private Integer type;

    /**
     * 文件信息
     */
    private FileInfo fileInfo;
}
