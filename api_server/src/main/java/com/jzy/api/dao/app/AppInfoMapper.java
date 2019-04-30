package com.jzy.api.dao.app;

import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.cnd.app.UpdateStatusBatchCnd;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * <b>功能：</b>商品mapper文件<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppInfoMapper extends GenericMapper<AppInfo> {

    /**
     * <b>功能描述：</b>分页查询商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppInfoListVo> listPage(AppInfoListCnd appInfoListCnd);

    /**
     * <b>功能描述：</b>查询商品信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppInfo queryById(@Param("aiId") Long aiId);


    /**
     * <b>功能描述：</b>商品批量修改状态<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param aiId   商品主键
     * @param status 启用/禁用
     */
    Boolean updateStatusBatch(@Param("aiId") Long aiId, @Param("status") Integer status);


    /**
     * <b>功能描述：</b>批量逻辑删除<br>
     * <b>修订记录：</b><br>
     * <li>20190418&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void deleteBatch(@Param("aiId") Long aiId);


}
