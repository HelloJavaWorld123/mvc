package com.jzy.api.dao.home;

import com.jzy.api.cnd.home.HomeHotGroupCnd;
import com.jzy.api.model.Home.HomeRecommendHotGroup;
import com.jzy.api.vo.home.HomeHotGroupVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b>登录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface HomeRecommendHotGroupMapper extends GenericMapper<HomeRecommendHotGroup> {


    /**
     * <b>功能描述：</b>根据name查询分组数量<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getByName(@Param("groupName") String groupName, @Param("dealerId") String dealerId);

    /**
     * <b>功能描述：</b>根据id查询启用分组数量<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getByIdStatus(@Param("id") Long id);

    /**
     * <b>功能描述：</b>删除分组<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delete(@Param("id") Long id);

    /**
     * <b>功能描述：</b>根据name和id查询分组数量<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getCountByNameNoId(@Param("groupName") String groupName, @Param("id") Long id, @Param("dealerId") String dealerId);

    /**
     * <b>功能描述：</b>根据name和id查询分组数量<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<HomeHotGroupVo> listPage(HomeHotGroupCnd homeHotGroupCnd);

    /**
     * <b>功能描述：</b>首页推荐分组启用停用<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int setStatus(@Param("id") Long id, @Param("state") String state);
}
