package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.cnd.app.UpdateStatusBatchCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.AppPageMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
        AppPage appPage = appPageMapper.getPageInfoByAiId(aiId);
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

    /**
     * <b>功能描述：</b>查询商品信息，用于确定是否能删除商品<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public AppInfo queryAppById(Long aiId) {
        return this.getAppInfoMapper(aiId);
    }

    /**
     * <b>功能描述：</b>商品信息保存<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(AppInfo appInfo) {
        List<AppInfo> list = listName(appInfo.getName());
        if (list != null && list.size() > 0) {
            throw new BusException("商品名称重复：".concat(appInfo.getName()));
        }
        this.insert(appInfo);
    }

    /**
     * <b>功能描述：</b>查询名称列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public List<AppInfo> listName(String name) {
        return appInfoMapper.listName(name);
    }

    /**
     * <b>功能描述：</b>商品批量修改状态<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateStatusBatch(UpdateStatusBatchCnd updateStatusBatchCnd) {
        Integer status = updateStatusBatchCnd.getStatus();
        for (Long aiId : updateStatusBatchCnd.getAiIds()) {
            appInfoMapper.updateStatusBatch(aiId, status);
        }
    }

    /**
     * <b>功能描述：</b>批量逻辑删除<br>
     * <b>修订记录：</b><br>
     * <li>20190418&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void deleteBatch(List<Long> newAiIds) {
        for (Long aiId : newAiIds) {
            appInfoMapper.deleteBatch(aiId);
        }

    }

    /**
     * <b>功能描述：</b>获取商品编号<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public int getMaxCode() {

        return appInfoMapper.getMaxCode();

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

    /**
     * <b>功能描述：</b>保存富文本内容<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void saveAppPage(AppPage appPage) {
        Date datetime = new Date();
        appPage.setCreateTime(datetime);
        appPage.setModifyTime(datetime);
        appPageMapper.insert(appPage);
    }

    /**
     * <b>功能描述：</b>修改富文本内容<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateAppPage(AppPage appPage) {
        appPage.setModifyTime(new Date());
        appPageMapper.update(appPage);
    }

    @Override
    protected GenericMapper<AppInfo> getGenericMapper() {
        return appInfoMapper;
    }
}
