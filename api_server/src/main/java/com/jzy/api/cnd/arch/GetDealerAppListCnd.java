package com.jzy.api.cnd.arch;

import com.jzy.framework.bean.cnd.PageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>获取渠道商商品列表传参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="查询渠道商下对应的商品列表参数")
public class GetDealerAppListCnd extends PageCnd {

    /**
     * 渠道商主键
     */
    @ApiModelProperty(value = "渠道商主键id")
    private String dealerId;

    /**
     * 商品类型
     */
    @ApiModelProperty(value = "商品分类id")
    private String cateId;

    /**
     * 商品分类
     */
    @ApiModelProperty(value = "商品类型id")
    private String typeId;

    /**
     * 厂商
     */
    @ApiModelProperty(value = "商品所属厂商id")
    private String acpId;

    /**
     * 模糊搜索名称
     */
    @ApiModelProperty(value = "模糊搜索参数：商品名称，商品编号，sup编号")
    private String searchName;

    /**
     * 商品状态
     */
    @ApiModelProperty(value = "状态：0下架，1上架，2未配置")
    private Integer status;


    public String getCateId() {
        if (cateId.equals("")) {
            return null;
        }
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getTypeId() {
        if (typeId.equals("")) {
            return null;
        }
        return typeId;
    }

    public String getAcpId() {
        if (acpId.equals("")) {
            return null;
        }
        return acpId;
    }

    public void setAcpId(String acpId) {
        this.acpId = acpId;
    }

    public String getSearchName() {
        if (searchName.equals("")) {
            return null;
        }
        return searchName;
    }

    public void setSearchName(String searchName) {

        this.searchName = searchName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
