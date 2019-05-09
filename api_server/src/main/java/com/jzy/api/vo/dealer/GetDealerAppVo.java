package com.jzy.api.vo.dealer;

import lombok.Data;

/**
 * <b>功能：</b>渠道商商品定价定价列表返回接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class GetDealerAppVo {

    /**
     * sup编号
     */
    private String supNo;
    /**
     * 商品编号
     */
    private String code;


    /**
     * 商品名称
     */
    private String appName;

    /**
     * 产品类型
     */
    private String cateName;

    /**
     * 商品分类
     */
    private String typeName;
    /**
     * 厂商
     */
    private String companyName;

    /**
     * 折扣
     */

    private String discount;


    /**
     * 商品状态：启用或者禁用
     */
    private Integer appStatus;


    /**
     * 渠道商商品状态：上架或者下架   为空则未配置
     */
    private Integer dealerAppStatus;


    /**
     * 商品Id
     *
     * @return
     */
    private String aiId;

    public String getSupNo() {
        if (null != supNo && null != code && null != discount) {
            if ((supNo.equals(code)) && (code.equals(discount))) {
                return "/";
            }
        }
        return supNo;
    }

    public void setSupNo(String supNo) {
        this.supNo = supNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDiscount() {
        if (null != supNo && null != code && null != discount) {
            if ((supNo.equals(code)) && (code.equals(discount))) {
                return "/";
            }
        }
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public Integer getDealerAppStatus() {
        if (null != supNo && null != code && null != discount) {
            if ((supNo.equals(code)) && (code.equals(discount))) {
                return null;
            }
        }
        return dealerAppStatus;
    }

    public void setDealerAppStatus(Integer dealerAppStatus) {
        this.dealerAppStatus = dealerAppStatus;
    }


    public String getAiId() {
        return aiId;
    }

    public void setAiId(String aiId) {
        this.aiId = aiId;
    }
}
