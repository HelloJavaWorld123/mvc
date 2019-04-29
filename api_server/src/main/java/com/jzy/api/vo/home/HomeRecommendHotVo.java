package com.jzy.api.vo.home;



import com.jzy.api.model.Home.GroupeDetail;
import com.jzy.api.model.Home.HomeRecommendHotDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>首页轮播图推荐列表查询<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class HomeRecommendHotVo {

    private GroupeDetail groupeDetail;


    /**
     * 分组详情
     */
    private List<HomeRecommendHotDetail> homeRecommendHotDetailList = new ArrayList<>(10);


    public List<HomeRecommendHotDetail> getHomeRecommendHotDetailList() {
        return homeRecommendHotDetailList;
    }

    public void setHomeRecommendHotDetailList(List<HomeRecommendHotDetail> homeRecommendHotDetailList) {
        this.homeRecommendHotDetailList = homeRecommendHotDetailList;
    }

    public GroupeDetail getGroupeDetail() {
        return groupeDetail;
    }

    public void setGroupeDetail(GroupeDetail groupeDetail) {
        this.groupeDetail = groupeDetail;
    }
}
