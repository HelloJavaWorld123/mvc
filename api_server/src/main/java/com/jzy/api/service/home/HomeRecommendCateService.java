package com.jzy.api.service.home;


import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.vo.home.HomeRecommendCateVo;
import com.jzy.framework.service.GenericService;

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
public interface HomeRecommendCateService extends GenericService<HomeRecommendCate> {


    /**
     * <b>功能描述：</b>渠道商首页推荐轮播图和推荐分类<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return
     */
    List<HomeRecommendCateVo> getList(Integer type);

}
