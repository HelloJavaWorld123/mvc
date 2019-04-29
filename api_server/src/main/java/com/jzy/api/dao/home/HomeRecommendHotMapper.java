package com.jzy.api.dao.home;

import com.jzy.api.model.Home.GroupeDetail;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.model.Home.HomeRecommendHot;
import com.jzy.api.model.Home.HotAppInfoDetail;
import com.jzy.api.vo.home.HomeRecommendCateVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * <b>功能：</b>登录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface HomeRecommendHotMapper extends GenericMapper<HomeRecommendHot> {

    /**
     * <b>功能描述：</b>查询分组信息<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<GroupeDetail> getGroupeDetailList(@Param("dealerId") String dealerId);

    /**
     * <b>功能描述：</b>查询当前渠道商下的推荐商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<HomeRecommendHot> queryHotList(@Param("dealerId") String dealerId);


    /**
     * <b>功能描述：</b>获取当前分组下的某个商品信息<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    HotAppInfoDetail getHotAppInfoDetail(@Param("dealerId") String dealerId, @Param("goId") String goId);


}
