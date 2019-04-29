package com.jzy.api.model.Home;

import java.util.Date;

/**
 * <b>功能：</b>渠道商推荐里跳转<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class HomeRecommendHot {
    /**
     * 主键
     */
    private String id;

    /**
     * 商户基本信息id
     */
    private String dealerId;

    /**
     * 轮播图名称
     */
    private String rciName;

    /**
     * 主图图片id
     */
    private String imageId;

    /**
     * 跳转类型 默认1-商品 2-分组
     */
    private Integer goType;

    /**
     * 跳转到的id
     */
    private String goId;
    /**
     * 跳转商品
     */
    private String goName;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    private Integer state;

    /**
     * 创建人id
     */
    private String creatorId;
    /**
     * 修改人id
     */

    private String modifierId;
    /**
     * create_time
     */
    private Date createTime;

    /**
     *修改时间
     */
    private Date modifyTime;


    /**
     * 0上中  1左上 2左下 3 右上 4 右下
     */
    private Integer position;
    /**
     * 分组id
     */
    private String groupId;

    /**
     * 分组顺序 从0开始，数字越小 排在越上面
     */
    private Integer groupSort;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getRciName() {
        return rciName;
    }

    public void setRciName(String rciName) {
        this.rciName = rciName;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Integer getGoType() {
        return goType;
    }

    public void setGoType(Integer goType) {
        this.goType = goType;
    }

    public String getGoId() {
        return goId;
    }

    public void setGoId(String goId) {
        this.goId = goId;
    }

    public String getGoName() {
        return goName;
    }

    public void setGoName(String goName) {
        this.goName = goName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupSort() {
        return groupSort;
    }

    public void setGroupSort(Integer groupSort) {
        this.groupSort = groupSort;
    }
}
