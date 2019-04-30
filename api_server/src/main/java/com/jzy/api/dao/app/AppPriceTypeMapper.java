package com.jzy.api.dao.app;

import com.jzy.api.model.app.AppPriceType;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

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
    List<AppPriceType> getAppPriceTypelist(@Param("aiId") Long aiId);

}
