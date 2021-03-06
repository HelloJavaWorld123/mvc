package com.jzy.api.vo.dealer;

import com.jzy.api.model.dealer.DealerParam;
import com.jzy.api.po.arch.DealerParamInfoPo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <b>功能：</b>渠道商详情返回<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerDetailVo implements Serializable {

    /*商户基础信息主键*/
    private Integer id;
    /*商户编号*/
    private String dealerNo;
    /*对应dealer表中的id*/
    private Integer dealerId;
    /*商户名称*/
    private String dealerName;
    /*商户简称*/
    private String dealerShortName;
    /*营业执照编号*/
    private String dealerBusinessNum;
    /*营业执照*/
    private String dealerBusinessPicture;
    /*合同*/
    private String dealerContract;
    /*法人名称*/
    private String dealerLegalName;
    /*法企业地址*/
    private String dealerAddress;
    /*联系人*/
    private String dealerContact;
    /*手机号*/
    private String dealerTelephone;
    /*身份证号*/
    private String dealerCard;
    /*身份证照片*/
    private String dealerCardPicture;
    /*开户行名称*/
    private String dealerBankName;
    /*开户行帐号*/
    private String dealerBankNo;
    /*登录帐号*/
    private String dealerLoginName;
    /*登录密码*/
    private String dealerPassword;
    /*收款方式*/
    private String dealerPaymentMethod;
    /*特殊备注*/
    private String notes;
    /*1-启用 0-停用*/
    private String state;
    /*渠道商唯一编号*/
    private String idnum;
    /**
     * 公钥（API KEY）用于签名使用
     */
    private String pubkey;
    /**
     * 私钥（DES Key）用于加解密
     */
    private String prikey;

    /**
     * SUP商户号
     */
    private String supBusinessid;

    /**
     * SUP商户KEY
     */
    private String supKey;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    //配置相关
    private List<DealerParamInfoPo> dpmList = new ArrayList<>();
}
