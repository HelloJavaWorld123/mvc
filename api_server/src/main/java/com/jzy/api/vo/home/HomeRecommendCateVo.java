package com.jzy.api.vo.home;


/**
 * <b>功能：</b>渠道商首页推荐轮播图和推荐分类返回参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class HomeRecommendCateVo {

    /**
     * 轮播图名称
     */
    private String rciName;

    /**
     * 轮播图排序
     */
    private Integer sortDesc;

    /**
     * 图片Id
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
     * 跳转商品或分组的名称
     */
    private String goName;


    public String getRciName() {
        return rciName;
    }

    public void setRciName(String rciName) {
        this.rciName = rciName;
    }

    public Integer getSortDesc() {
        return sortDesc;
    }

    public void setSortDesc(Integer sortDesc) {
        this.sortDesc = sortDesc;
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
}
