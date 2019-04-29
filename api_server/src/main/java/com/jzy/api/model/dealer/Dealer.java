package com.jzy.api.model.dealer;

import com.jzy.framework.bean.model.GenericModel;

import java.io.Serializable;

/**
 * 经销商Model
 * Created by IntelliJ IDEA. @Date 2019/3/5
 *
 * @Author GuoBing.Zh
 */
public class Dealer extends GenericModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
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
    private String state;
    /**
     *公钥（API KEY）用于签名使用
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
    

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getPrikey() {
        return prikey;
    }

    public void setPrikey(String prikey) {
        this.prikey = prikey;
    }

    public String getSupBusinessid() {
        return supBusinessid;
    }

    public void setSupBusinessid(String supBusinessid) {
        this.supBusinessid = supBusinessid;
    }

    public String getSupKey() {
        return supKey;
    }

    public void setSupKey(String supKey) {
        this.supKey = supKey;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
