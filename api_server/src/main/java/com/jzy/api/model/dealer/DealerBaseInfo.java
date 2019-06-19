package com.jzy.api.model.dealer;

import com.jzy.framework.bean.model.GenericModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>功能：</b>渠道商基础信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="渠道商基础信息")
public class DealerBaseInfo extends GenericModel implements Serializable {

    /*商户编号*/
    @ApiModelProperty(value = "渠道商编号")
    private String dealerNo;
    /*对应dealer表中的id*/
    @ApiModelProperty(value = "渠道商主键id")
    private String dealerId;
    /*商户名称*/
    @ApiModelProperty(value = "渠道商名称")
    private String dealerName;
    /*商户简称*/
    @ApiModelProperty(value = "渠道商简称")
    private String dealerShortName;
    /*营业执照编号*/
    @ApiModelProperty(value = "渠道商营业执照编号")
    private String dealerBusinessNum;
    /*营业执照*/
    @ApiModelProperty(value = "渠道商营业执照图片url")
    private String dealerBusinessPicture;
    /*合同*/
    @ApiModelProperty(value = "渠道商合同图片url")
    private String dealerContract;
    /*法人名称*/
    @ApiModelProperty(value = "渠道商法人名称")
    private String dealerLegalName;
    /*法企业地址*/
    @ApiModelProperty(value = "渠道商企业地址")
    private String dealerAddress;
    /*联系人*/
    @ApiModelProperty(value = "渠道商联系人")
    private String dealerContact;
    /*手机号*/
    @ApiModelProperty(value = "渠道商手机号")
    private String dealerTelephone;
    /*身份证号*/
    @ApiModelProperty(value = "渠道商身份证号码")
    private String dealerCard;
    /*身份证照片*/
    @ApiModelProperty(value = "身份证图片url")
    private String dealerCardPicture;
    /*开户行名称*/
    @ApiModelProperty(value = "开户行名称")
    private String dealerBankName;
    /*开户行帐号*/
    @ApiModelProperty(value = "开户行账号")
    private String dealerBankNo;
    //    /*登录帐号*/
    @ApiModelProperty(value = "登录账号")
    private String dealerLoginName;
    /*登录密码*/
    @ApiModelProperty(value = "登录密码")
    private String dealerPassword;
    /*收款方式*/
    @ApiModelProperty(value = "收款方式")
    private String dealerPaymentMethod;
    /*特殊备注*/
    @ApiModelProperty(value = "特殊备注")
    private String notes;


}
