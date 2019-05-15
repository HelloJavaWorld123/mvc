package com.jzy.api.dao.app;


import com.jzy.api.cnd.app.AppCompanyCnd;
import com.jzy.api.cnd.app.AppCompanyListCnd;
import com.jzy.api.model.app.AppCompany;
import com.jzy.api.po.app.AppCompanyPo;
import com.jzy.api.vo.app.AppCompanyVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b>厂商<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppCompanyMapper extends GenericMapper<AppCompany> {

    /**
     * <b>功能描述：</b>厂商<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppCompanyPo> getList();

    /**
     * <b>功能描述：</b>厂商删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delete(@Param("id") Long id);

    /**
     * <b>功能描述：</b>根据厂商name查询数量<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getCountByName(@Param("name") String name);

    /**
     * <b>功能描述：</b>根据厂商name和id查询数量<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getByNameNotId(@Param("name") String name,@Param("id") Long id);

    /**
     * <b>功能描述：</b>厂商分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppCompanyVo> listPage(AppCompanyListCnd appCompanyListCnd);
}
