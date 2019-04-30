package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.auth.Auth;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppInfoServiceImpl extends GenericServiceImpl<AppInfo> implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;


    @Override
    public AppInfoDetailVo getAppInfo(String appInfoId) {
        return null;
    }

    @Override
    public AppInfo queryAppById(String id) {
        return null;
    }

    @Override
    public AppInfo getName(String name) {
        return null;
    }

    @Override
    public List<AppInfo> listName(String name) {
        return null;
    }

    @Override
    public List<AppInfo> list(Integer statusNotEqual) {
        return null;
    }

    @Override
    public List<AppInfo> list(String[] ids) {
        return null;
    }

    @Override
    public Boolean save(AppInfo appInfo) {
        return null;
    }

    @Override
    public Boolean updateStatus(String aiId, Integer status) {
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

    @Override
    public Boolean saveBatchInit(List<Object[]> values) {
        return null;
    }

    @Override
    public int getMaxCode() {
        return 0;
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
    public void deleteRedisKey() {

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
