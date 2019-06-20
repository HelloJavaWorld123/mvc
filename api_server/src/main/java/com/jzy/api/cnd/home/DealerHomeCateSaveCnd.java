package com.jzy.api.cnd.home;

import com.jzy.api.model.app.FileInfo;
import com.jzy.framework.bean.cnd.PageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="首页分类和轮播图保存更新参数")
public class DealerHomeCateSaveCnd {

    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 渠道商Id
     */
    @ApiModelProperty(value = "渠道商id")
    private String dealerId;

    /**
     * 轮播图名称
     */
    @ApiModelProperty(value = "分类或者轮播图名称")
    private String rciName;

    /**
     * 轮播图排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sortDesc;

    /**
     * 图片Id
     */
    @ApiModelProperty(value = "图片id")
    private String imageId;

    /**
     * 图片路径
     */
    @ApiModelProperty(value = "图片url")
    private String imageUrl;

    /**
     * 跳转类型 默认1-商品 2-分组
     */
    @ApiModelProperty(value = "跳转类型：1商品，2分类，3网站")
    private Integer goType;

    /**
     * 跳转到的id
     */
    @ApiModelProperty(value = "跳转的商品或分类id")
    private String goId;


    /**
     * 跳转商品或分组的名称
     */
    @ApiModelProperty(value = "跳转商品或分类的名称")
    private String goName;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    @ApiModelProperty(value = "状态0禁用，1启用")
    private Integer state;

    /**
     * 0 轮播图 1 推荐分类
     */
    @ApiModelProperty(value = "0轮播图，1推荐分类")
    private Integer type;

    /**
     * 文件信息
     */
    private FileInfo fileInfo;
}
