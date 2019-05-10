package com.jzy.api.model.dealer;

import com.jzy.framework.bean.model.GenericModel;
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
public class Dealer extends GenericModel implements Serializable {
    /**
     * 经销商标识
     */
    private String idnum;
    /**
     * 经销商名称
     */
    private String name;

    /**
     * 经销商联系人
     */
    private String contact;
    /**
     * 经销商联系电话
     */
    private String telno;
    /**
     * 审核状态(0:待审核,1:通过,2:驳回,3:禁用)
     */
    private int verified;

    /**
     * 经销商状态 0是启用 1是禁用，默认为0
     */
    private Integer state=0;
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


    private String desc;


    private String remark;

}
