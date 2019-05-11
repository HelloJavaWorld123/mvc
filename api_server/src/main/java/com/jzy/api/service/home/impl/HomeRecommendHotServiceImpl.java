package com.jzy.api.service.home.impl;


import com.jzy.api.dao.home.HomeRecommendHotMapper;
import com.jzy.api.model.Home.GroupeDetail;
import com.jzy.api.model.Home.HomeRecommendHot;
import com.jzy.api.model.Home.HomeRecommendHotDetail;
import com.jzy.api.model.Home.HotAppInfoDetail;
import com.jzy.api.service.home.HomeRecommendHotService;
import com.jzy.api.vo.home.HomeRecommendHotVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.GenericService;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>渠道商首页推荐Hot<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class HomeRecommendHotServiceImpl extends GenericServiceImpl<HomeRecommendHot> implements HomeRecommendHotService {

    @Resource
    private HomeRecommendHotMapper homeRecommendHotMapper;

    /**
     * <b>功能描述：</b>首页查询商品推荐列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<HomeRecommendHotVo> getList() {
        String dealerId=getFrontDealerId();
        List<HomeRecommendHotVo> homeRecommendHotVoList = new ArrayList<>();
        //查询分组信息
        List<GroupeDetail> groupeDetailList = homeRecommendHotMapper.getGroupeDetailList(dealerId);
        List<HomeRecommendHot> HomeRecommendHots = homeRecommendHotMapper.queryHotList(dealerId);
        //拼装数据
        for (GroupeDetail groupeDetail : groupeDetailList) {
            HomeRecommendHotVo homeRecommendHotVo = new HomeRecommendHotVo();
            homeRecommendHotVo.setGroupeDetail(groupeDetail);
            List<HomeRecommendHotDetail> homeRecommendHotDetails = getHomeRecommendHotDetailList(groupeDetail.getGroupeId(), HomeRecommendHots);
            homeRecommendHotVo.setHomeRecommendHotDetailList(homeRecommendHotDetails);
            homeRecommendHotVoList.add(homeRecommendHotVo);
        }

        return homeRecommendHotVoList;
    }

    /**
     * <b>功能描述：</b>获取当前分组下的所有的商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private List<HomeRecommendHotDetail> getHomeRecommendHotDetailList(String groupeId, List<HomeRecommendHot> HomeRecommendHots) {
        List<HomeRecommendHotDetail> homeRecommendHotDetails = new ArrayList<>(10);
        for (HomeRecommendHot HomeRecommendHot : HomeRecommendHots) {
            String dealerId = HomeRecommendHot.getDealerId();
            String goId = HomeRecommendHot.getGoId();
            if (HomeRecommendHot.getGroupId().equals(groupeId)) {
                HomeRecommendHotDetail homeRecommendHotDetail = new HomeRecommendHotDetail();
                BeanUtils.copyProperties(HomeRecommendHot, homeRecommendHotDetail);
                //获取商品信息
                HotAppInfoDetail hotAppInfoDetail = homeRecommendHotMapper.getHotAppInfoDetail(dealerId, goId);
                if (null != hotAppInfoDetail) {
                    homeRecommendHotDetail.setHotAppInfoDetail(hotAppInfoDetail);
                }
                homeRecommendHotDetails.add(homeRecommendHotDetail);
            }
        }
        return homeRecommendHotDetails;
    }

    @Override
    protected GenericMapper<HomeRecommendHot> getGenericMapper() {
        return homeRecommendHotMapper;
    }
}
