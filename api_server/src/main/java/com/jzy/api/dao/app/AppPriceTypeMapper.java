package com.jzy.api.dao.app;

import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.po.app.AppPriceTypeForDetailPo;
import com.jzy.api.po.arch.AppPriceTypePo;
import com.jzy.api.po.dealer.AppPriceTypeListPo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b>商品充值类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppPriceTypeMapper extends GenericMapper<AppPriceType> {

    /**
     * <b>功能描述：</b>查询当前商品充值类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppPriceTypeListPo> getAppPriceTypelist(@Param("aiId") String aiId, @Param("dealerId") String dealerId);

    /**
     * <b>功能描述：</b>前台营业查询当前充值类型商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppPriceTypePo> getAppPriceTypePolist(@Param("aiId") Long aiId, @Param("dealerId") Long dealerId);

    /**
     * <b>功能描述：</b>物理删除当前商品下的所有充值类型<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void deleteByAiId(@Param("aiId") Long aiId);


    /**
     * <b>功能描述：</b>查询当前商品充值类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppPriceTypeForDetailPo> getAppPriceTypelistByaiId(@Param("aiId") Long aiId);


    /**
     * <b>功能描述：</b>保存到数据库的id列表和查询出来的id列表求差集，做删除操作<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<String> getIdList(@Param("aiId") Long aiId);

}
