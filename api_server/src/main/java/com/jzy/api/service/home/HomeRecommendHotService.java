package com.jzy.api.service.home;

import com.jzy.api.cnd.home.DialogBannerCnd;
import com.jzy.api.cnd.home.HomeHotListCnd;
import com.jzy.api.cnd.home.HomeRecommendHotCnd;
import com.jzy.api.model.Home.HomeRecommendHot;
import com.jzy.api.model.Home.HomeRecommendHotDetail;
import com.jzy.api.vo.home.DialogBannerVo;
import com.jzy.api.vo.home.HomeHotInfoVo;
import com.jzy.api.vo.home.HomeHotVo;
import com.jzy.api.vo.home.HomeRecommendHotVo;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.service.GenericService;

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
public interface HomeRecommendHotService extends GenericService<HomeRecommendHot> {


    /**
     * <b>功能描述：</b>首页查询商品推荐列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<HomeRecommendHotVo> getList();

    /**
     * <b>功能描述：</b>后台首页推荐分组添加<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(HomeRecommendHot homeRecommendHot);

    /**
     * <b>功能描述：</b>后台首页推荐分组商品编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void edit(HomeRecommendHotCnd homeRecommendHotCnd);

    /**
     * <b>功能描述：</b>后台首页推荐分组商品分页列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<HomeHotVo> listPage(HomeHotListCnd homeHotListCnd);

    /**
     * <b>功能描述：</b>根据id获取首页推荐商品信息<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    HomeHotInfoVo getHomeInfoHot(Long id);

    /**
     * <b>功能描述：</b>查询分组下面所有商品<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<HomeHotVo> getByGroupId(Long id);

    /**
     * <b>功能描述：</b>删除分组下面所有商品<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delateBatch(List<String> result);

    List<HomeRecommendHotDetail> getDialogBanner(DialogBannerCnd dialogBannerCnd);

    List<HomeRecommendHotDetail> getLikeAppInfo(IdCnd idCnd);

    int getByGroupIdAndStatus(Long id);
}
