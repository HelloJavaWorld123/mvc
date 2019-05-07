package com.jzy.api.dao.app;

import com.jzy.api.cnd.app.GetServInfoCnd;
import com.jzy.api.model.app.AppGame;
import com.jzy.api.po.app.AppGameListPo;
import com.jzy.api.vo.app.AppGameListVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b>游戏<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppGameMapper extends GenericMapper<AppGame> {


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询区<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> getAreaInfo(@Param("aiId") Long aiId);


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询服<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> getServInfo(@Param("aiId") String aiId, @Param("areaId") String areaId);

    /**
     * <b>功能描述：</b>校验当前商品是否存在区<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> checkAreaInfo(@Param("pid") String pId);

    /**
     * <b>功能描述：</b>校验当前商品是否存在服<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> checkServInfo(@Param("ids") List<String> ids);


}