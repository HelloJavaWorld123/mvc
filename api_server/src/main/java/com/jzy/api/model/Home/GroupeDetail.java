package com.jzy.api.model.Home;

/**
 * <b>功能：</b>首页商品推荐查询分组信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class GroupeDetail {

    /**
     * 分组id
     */
    private String groupeId;


    /**
     * 分组名称
     */
    private String groupeName;

    /**
     * 分组顺序 从0开始，数字越小 排在越上面
     */
    private Integer groupSort;


    public String getGroupeId() {
        return groupeId;
    }

    public void setGroupeId(String groupeId) {
        this.groupeId = groupeId;
    }

    public String getGroupeName() {
        return groupeName;
    }

    public void setGroupeName(String groupeName) {
        this.groupeName = groupeName;
    }

    public Integer getGroupSort() {
        return groupSort;
    }

    public void setGroupSort(Integer groupSort) {
        this.groupSort = groupSort;
    }
}
