package com.jzy.api.dao.app;

import com.jzy.api.model.app.AppAccttype;
import com.jzy.api.po.app.AppAccttypeListPo;
import com.jzy.framework.dao.GenericMapper;

import java.util.List;

/**
 * <b>功能：</b>账号类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppAccttypeMapper extends GenericMapper<AppAccttype> {

    /**
     * <b>功能描述：</b>账号类型列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppAccttypeListPo> list();


}
