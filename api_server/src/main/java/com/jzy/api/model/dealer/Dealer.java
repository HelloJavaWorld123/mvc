package com.jzy.api.model.dealer;

import com.jzy.framework.bean.model.GenericModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <b>功能：</b>经销商Model<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="渠道商信息")
public class Dealer extends GenericModel implements Serializable {
    /**
     * 经销商标识
     */
    @ApiModelProperty(value = "渠道商唯一商户号")
    private String idnum;
    /**
     * 经销商名称
     */
    @ApiModelProperty(value = "渠道商名称")
    private String name;

    /**
     * 经销商联系人
     */
    @ApiModelProperty(value = "渠道商联系人")
    private String contact;
    /**
     * 经销商联系电话
     */
    @ApiModelProperty(value = "渠道商联系电话")
    private String telno;
    /**
     * 审核状态(0:待审核,1:通过,2:驳回,3:禁用)
     */
    @ApiModelProperty(value = "审核状态(0:待审核,1:通过,2:驳回,3:禁用)")
    private int verified;

    /**
     * 经销商状态 0是禁用 1是启用，默认为0
     */
    @ApiModelProperty(value = "状态 0是禁用 1是启用，默认为1")
    private Integer state;
    /**
     * 公钥（API KEY）用于签名使用
     */
    @ApiModelProperty(value = "公钥")
    private String pubkey;
    /**
     * 私钥（DES Key）用于加解密
     */
    @ApiModelProperty(value = "私钥")
    private String prikey;

    /**
     * SUP商户号
     */
    @ApiModelProperty(value = "sup商户号")
    private String supBusinessid;

    /**
     * SUP商户KEY
     */
    @ApiModelProperty(value = "sup商户key")
    private String supKey;

    @ApiModelProperty(value = "排序")
    private String desc;

    @ApiModelProperty(value = "备注")
    private String remark;


}
