package com.jzy.api.dao.app;

import com.jzy.api.model.app.AppPage;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * <b>功能：</b>富文本<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppPageMapper extends GenericMapper<AppPage> {
    /**
     * <b>功能描述：</b>获取商品富文本<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppPage getPageInfoByAiId(@Param("aiId") Long aiId);

}
