package com.jzy.api.service.home.impl;


import com.jzy.api.model.Home.GroupeDetail;
import com.jzy.api.model.Home.HomeRecommendHot;
import com.jzy.api.model.Home.HomeRecommendHotDetail;
import com.jzy.api.model.Home.HotAppInfoDetail;
import com.jzy.api.service.home.HomeRecommendHotService;
import com.jzy.api.vo.home.HomeRecommendHotVo;
import com.mall.dao.impl.BaseDao;
import com.mall.mapper.GroupeDetail;
import com.mall.mapper.HomeRecommendHotDetail;
import com.mall.mapper.HomeRecommendHot;
import com.mall.mapper.HotAppInfoDetail;
import com.mall.pc.service.HomeRecommendHotService;
import com.mall.vo.HomeRecommendHotVo;
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
public class HomeRecommendHotServiceImpl extends BaseDao implements HomeRecommendHotService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * <b>功能描述：</b>首页查询商品推荐列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<HomeRecommendHotVo> getList(String dealerId) {

        List<HomeRecommendHotVo> homeRecommendHotVoList = new ArrayList<>();
        //查询分组信息
        List<GroupeDetail> groupeDetailList = jdbcTemplate.query(sqlMap("home_recommend_hot.getGroupe"),
                BeanPropertyRowMapper.newInstance(GroupeDetail.class), dealerId);
        List<HomeRecommendHot> HomeRecommendHots = jdbcTemplate.query(sqlMap("home_recommend_hot.list"),
                BeanPropertyRowMapper.newInstance(HomeRecommendHot.class), dealerId);
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
                HotAppInfoDetail hotAppInfoDetail = jdbcTemplate.queryForObject(sqlMap("dealer_app_price_info_getOne"), BeanPropertyRowMapper.newInstance(HotAppInfoDetail.class), dealerId, goId);
                if (null != hotAppInfoDetail) {
                    homeRecommendHotDetail.setHotAppInfoDetail(hotAppInfoDetail);
                }
                homeRecommendHotDetails.add(homeRecommendHotDetail);
            }
        }
        return homeRecommendHotDetails;
    }
}
