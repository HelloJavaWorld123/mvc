package com.jzy.api.dao.home;

import com.jzy.api.cnd.home.HomeHotListCnd;
import com.jzy.api.cnd.home.HomeRecommendHotCnd;
import com.jzy.api.model.Home.*;
import com.jzy.api.vo.home.HomeHotInfoVo;
import com.jzy.api.vo.home.HomeHotVo;
import com.jzy.api.vo.home.HomeRecommendCateVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;
import java.util.Map;

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

    List<GroupeDetail> getGroupeList(@Param("dealerId") String dealerId);

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

    List<HomeRecommendHotDetail> getHomeRecommendHotDetails(@Param("groupId") String groupeId);

    /**
     * <b>功能描述：</b>根据位置获取分组下面的商品数量<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getByPosition(@Param("id") Long id, @Param("groupId") String groupId, @Param("position") Integer position);

    /**
     * <b>功能描述：</b>根据名称获取分组下商品数量<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getByName(@Param("id") Long id, @Param("groupId") String groupId, @Param("goId") String goId);

    /**
     * <b>功能描述：</b>首页推荐分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<HomeHotVo> listPage(HomeHotListCnd homeHotListCnd);

    /**
     * <b>功能描述：</b>根据id获取首页推荐商品信息<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    HomeHotInfoVo getHomeInfoHot(@Param("id") Long id);

    /**
     * <b>功能描述：</b>查询分组下面所有商品<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<HomeHotVo> getByGroupId(@Param("id") Long id);

    /**
     * <b>功能描述：</b>删除分组下面所有商品<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void deleteBatch(List<String> result);

    /**
     * <b>功能描述：</b>首页推荐分组商品编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void edit(HomeRecommendHotCnd homeRecommendHotCnd);

    Integer queryExistById(Map<String, Object> paramsMap);
}
