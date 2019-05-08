package com.jzy.api.service.app;


import com.jzy.api.model.app.AppType;
import com.jzy.api.po.app.AppTypePo;
import com.jzy.framework.service.GenericService;

import java.util.List;

/**
 * <b>功能：</b>应用类型service<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppTypeService extends GenericService<AppType> {

    /**
     * <b>功能描述：</b>产品类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<AppTypePo> getList();


}
