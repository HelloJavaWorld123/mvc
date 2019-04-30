package com.jzy.api.dao.app;

import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.framework.dao.GenericMapper;

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


}
