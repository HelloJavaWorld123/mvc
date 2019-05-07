package com.jzy.api.service.app;


import com.jzy.api.model.app.AppAccttype;
import com.jzy.framework.service.GenericService;

import java.util.List;

/**
 * <b>功能：</b>应用账号<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppAccttypeService extends GenericService<AppAccttype> {



    /**
     * 查询列表
     *
     * @return List
     */
    List<AppAccttype> list();


}
