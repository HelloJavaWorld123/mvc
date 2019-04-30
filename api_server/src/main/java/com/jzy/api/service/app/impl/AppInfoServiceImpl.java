package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.AppPageMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.auth.Auth;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppInfoServiceImpl extends GenericServiceImpl<AppInfo> implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;

    @Resource
    private AppPriceTypeService appPriceTypeService;


    @Resource
    private AppPageMapper appPageMapper;


    /**
     * <b>功能描述：</b>获取商品详细信息<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppInfoDetailVo getAppInfo(Long aiId) {

        AppInfo appInfoMapper = getAppInfoMapper(aiId);
        List<AppPriceType> appPriceTypeMappers = appPriceTypeService.getAppPriceTypelist(aiId);
        //获取商品富文本
       AppPage appPage= appPageMapper.getPageInfoByAiId(aiId);
        return new AppInfoDetailVo(appInfoMapper, appPriceTypeMappers, appPage);
    }

    /**
     * <b>功能描述：</b>查询商品信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public AppInfo getAppInfoMapper(Long id) {
        return appInfoMapper.queryById(id);
    }


    @Override
    public Boolean save(AppInfo appInfo) {
        return null;
    }


    @Override
    public Boolean updateStatusBatch(List<Object[]> values) {
        return null;
    }

    @Override
    public Boolean deleteBatch(List<Object[]> values) {
        return null;
    }


    /**
     * <b>功能描述：</b>分页查询商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<AppInfoListVo> listPage(AppInfoListCnd appInfoListCnd) {
        Page<AppInfoListVo> page = PageHelper.startPage(appInfoListCnd.getPage(), appInfoListCnd.getLimit());
        return appInfoMapper.listPage(appInfoListCnd);
    }

    @Override
    public Boolean saveAppPage(AppPage AppPage) {
        return null;
    }

    @Override
    public Boolean updateAppPage(AppPage AppPage) {
        return null;
    }

    @Override
    protected GenericMapper<AppInfo> getGenericMapper() {
        return appInfoMapper;
    }
}
