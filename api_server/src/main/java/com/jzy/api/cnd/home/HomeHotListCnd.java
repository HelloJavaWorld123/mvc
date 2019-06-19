package com.jzy.api.cnd.home;

import com.jzy.framework.bean.cnd.PageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>渠道商推荐里跳转<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190516&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="首页推荐商品列表查询参数")
public class HomeHotListCnd extends PageCnd {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 商户基本信息id
     */
    @ApiModelProperty(value = "渠道商id")
    private String dealerId;

    /**
     * 轮播图名称
     */
    @ApiModelProperty(value = "轮播图名称")
    private String rciName;

    /**
     * 主图图片id
     */
    @ApiModelProperty(value = "图片id")
    private String imageId;

    /**
     * 跳转类型 默认1-商品 2-分组
     */
    @ApiModelProperty(value = "跳转类型1商品，2分组，3网站")
    private Integer goType;

    /**
     * 跳转到的id
     */
    @ApiModelProperty(value = "跳转的商品或分类的id")
    private String goId;
    /**
     * 跳转商品
     */
    @ApiModelProperty(value = "跳转商品或者分类名称")
    private String goName;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    @ApiModelProperty(value = "轮播图名称")
    private Integer state;


    /**
     * 0上中  1左上 2左下 3 右上 4 右下
     */
    @ApiModelProperty(value = "位置")
    private Integer position;
    /**
     * 分组id
     */
    @ApiModelProperty(value = "分组id")
    private String groupId;

    /**
     * 分组顺序 从0开始，数字越小 排在越上面
     */
    @ApiModelProperty(value = "分组排序")
    private Integer groupSort;


}
