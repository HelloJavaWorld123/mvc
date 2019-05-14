package com.jzy.api.service.app;


import com.jzy.api.cnd.app.AppCompanyCnd;
import com.jzy.api.model.app.AppCompany;
import com.jzy.api.po.app.AppCompanyPo;
import com.jzy.api.vo.app.AppCompanyVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.service.GenericService;

import java.util.List;

/**
 * <b>功能：</b>厂商service<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppCompanyService extends GenericService<AppCompany> {

    /**
     * <b>功能描述：</b>厂商列表<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<AppCompanyPo> getList();


    /**
     * <b>功能描述：</b>厂商添加<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int save(AppCompany appCompany);

    /**
     * <b>功能描述：</b>厂商删除<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delete(Long id);

    /**
     * <b>功能描述：</b>厂商编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void edit(AppCompany appCompany);

    /**
     * <b>功能描述：</b>厂商列表分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<AppCompanyVo> listPage(AppCompanyCnd appCompanyCnd);
}
