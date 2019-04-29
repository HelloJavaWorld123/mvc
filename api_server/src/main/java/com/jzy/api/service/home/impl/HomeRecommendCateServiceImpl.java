package com.jzy.api.service.home.impl;


import com.jzy.api.dao.home.HomeRecommendCateMapper;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.service.home.HomeRecommendCateService;
import com.jzy.api.vo.home.HomeRecommendCateVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：</b>渠道商首页推荐轮播图和推荐分类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class HomeRecommendCateServiceImpl extends GenericServiceImpl<HomeRecommendCate> implements HomeRecommendCateService {

    @Resource
    private HomeRecommendCateMapper homeRecommendCateMapper;

    /**
     * <b>功能描述：</b>渠道商首页推荐轮播图和推荐分类<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return
     */
    @Override
    public List<HomeRecommendCateVo> getList(Integer type, String dealerId) {
        List<HomeRecommendCateVo> homeRecommendCateVos=  homeRecommendCateMapper.getList(type,dealerId);
        return homeRecommendCateVos;

    }


    @Override
    protected GenericMapper<HomeRecommendCate> getGenericMapper() {
        return homeRecommendCateMapper;
    }
}
