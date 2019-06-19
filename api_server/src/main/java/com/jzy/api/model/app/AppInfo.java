package com.jzy.api.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.framework.bean.model.GenericModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用表
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Data
@ApiModel(value="商品基本信息")
public class AppInfo extends GenericModel implements Serializable {

    /**
     * 编号
     */
    @ApiModelProperty(value = "商品编号")
    private String code;
    /**
     * 分类id
     */
    @ApiModelProperty(value = "商品分类id")
    private String cateId;
    /**
     * 账号类型id
     */
    @ApiModelProperty(value = "账号类型id")
    private Long acctId;
    /**
     * 厂商id
     */
    @ApiModelProperty(value = "厂商id")
    private String acpId;
    /**
     * 游戏id
     */
    @ApiModelProperty(value = "游戏id")
    private Long  gameId;
    /**
     * 应用类型id
     */
    @ApiModelProperty(value = "商品类型id")
    private String typeId;

    /**
     * 应用名称
     */
    @ApiModelProperty(value = "商品名称")
    private String name;
    /**
     * 图标地址
     */
    @ApiModelProperty(value = "商品图片url")
    private String icon;
    /**
     * 检索标签
     */
    @ApiModelProperty(value = "检索标签")
    private String label;
    /**
     * 首字母
     */
    @ApiModelProperty(value = "首字母")
    private String firstLetter;
    /**
     * 全拼首字母
     */
    @ApiModelProperty(value = "商品全拼首字母")
    private String spllLetter;
    /**
     * 充值模式:0直充 1 卡密
     */
    @ApiModelProperty(value = "充值模式：0直充，1卡密")
    private Integer rechargeMode;
    /**
     * 状态  0 下架 1上架  2删除
     */
    @ApiModelProperty(value = "状态：0下架，1上架，2删除")
    private Integer status = 0;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort = 1000;
    /**
     * 应用描述
     */
    @ApiModelProperty(value = "商品描述")
    private String describe;
    /**
     * 页面地址
     */
    @ApiModelProperty(value = "商品展示地址")
    private String pagePath;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * 是否热销   0为热销  1为不热销
     */
    @ApiModelProperty(value = "是否为热销0热销，1不热销")
    private Integer isHot = 0;


    /**
     * 是否推荐 0为推荐  1为不推荐
     */
    @ApiModelProperty(value = "是否推荐0推荐，1不推荐")
    private Integer isReco = 0;


}
