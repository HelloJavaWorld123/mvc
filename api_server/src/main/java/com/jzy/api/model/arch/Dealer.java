package com.jzy.api.model.arch;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>商户<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class Dealer extends GenericModel {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String idnum;
    private String name;
    private String contact;
    private String telno;
    private int verified;
    private String state;
    private String pubkey;
    private String prikey;
    private Date time;
    private String desc;
    private String remark; // JSONString格式，临时存储经销商的SUP、API接口商户号及Key{"sup_businessid":"","sup_key":"","supapi_des":"","supapi_key":"","supapi_businessid":""}
    /* ----- 临时数据 ----- */
    private String supBusinessid;
    private String supKey;
    private String supApiBusinessid;
    private String supApiKey;
    private String supApiDes;

}
