package com.jzy.api.service.app;


import com.jzy.api.cnd.app.AppAccttypeCnd;
import com.jzy.api.cnd.app.AppAccttypeListCnd;
import com.jzy.api.model.app.AppAccttype;
import com.jzy.api.po.app.AppAccttypeListPo;
import com.jzy.api.vo.app.AppAccttypeVo;
import com.jzy.framework.bean.vo.PageVo;
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
    List<AppAccttypeListPo> list();


    /**
     * <b>功能描述：</b>账号类型添加<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(AppAccttype appAccttype);

    /**
     * <b>功能描述：</b>账号类型删除<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delete(Long id);

    /**
     * <b>功能描述：</b>账号类型编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void edit(AppAccttype appAccttype);

    /**
     * <b>功能描述：</b>账号类型分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<AppAccttypeVo> listPage(AppAccttypeListCnd appAccttypeListCnd);
}
