package com.jzy.api.service.home;

import com.jzy.api.cnd.home.HomeHotGroupCnd;
import com.jzy.api.cnd.home.HomeRecommendHotGroupCnd;
import com.jzy.api.model.Home.HomeRecommendHotGroup;
import com.jzy.api.vo.home.HomeHotGroupVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.service.GenericService;

/**
 * <b>功能：</b>渠道商首页推荐Hot<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface HomeRecommendHotGroupService extends GenericService<HomeRecommendHotGroup> {

    /**
     * <b>功能描述：</b>后台首页推荐分组添加<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(HomeRecommendHotGroup homeRecommendHotGroup);

    /**
     * <b>功能描述：</b>后台首页推荐分组删除<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delete(Long id);

    /**
     * <b>功能描述：</b>后台首页推荐分组编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void edit(HomeRecommendHotGroup homeRecommendHotGroup);

    /**
     * <b>功能描述：</b>后台首页推荐分组分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<HomeHotGroupVo> listPage(HomeHotGroupCnd homeHotGroupCnd);

    /**
     * <b>功能描述：</b>后台首页推荐分组启用停用br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int setStatus(HomeRecommendHotGroupCnd homeRecommendHotGroupCnd);
}
