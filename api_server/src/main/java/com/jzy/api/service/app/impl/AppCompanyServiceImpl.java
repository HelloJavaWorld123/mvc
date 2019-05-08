package com.jzy.api.service.app.impl;

import com.jzy.api.dao.app.AppCompanyMapper;
import com.jzy.api.dao.app.AppTypeMapper;
import com.jzy.api.model.app.AppCompany;
import com.jzy.api.model.app.AppType;
import com.jzy.api.po.app.AppCompanyPo;
import com.jzy.api.po.app.AppTypePo;
import com.jzy.api.service.app.AppCompanyService;
import com.jzy.api.service.app.AppTypeService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service
public class AppCompanyServiceImpl extends GenericServiceImpl<AppCompany> implements AppCompanyService {

    @Resource
    private AppCompanyMapper appCompanyMapper;


    /**
     * <b>功能描述：</b>产品类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<AppCompanyPo> getList() {
        return appCompanyMapper.getList();
    }

    @Override
    protected GenericMapper<AppCompany> getGenericMapper() {
        return appCompanyMapper;
    }
}
