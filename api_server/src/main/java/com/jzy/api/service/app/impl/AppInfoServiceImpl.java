package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.cnd.app.UpdateStatusBatchCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.AppPageMapper;
import com.jzy.api.dao.home.HomeRecommendCateMapper;
import com.jzy.api.dao.home.HomeRecommendHotMapper;
import com.jzy.api.dao.sys.SysImagesMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.po.app.AppInfoPo;
import com.jzy.api.po.app.AppPriceTypeForDetailPo;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.exception.ExcelException;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppInfoServiceImpl extends GenericServiceImpl<AppInfo> implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;

    @Resource
    private AppPriceTypeService appPriceTypeService;

    @Resource
    private AppPageMapper appPageMapper;

    @Resource
    private SysImagesMapper sysImagesMapper;

    @Resource
    private HomeRecommendCateMapper homeRecommendCateMapper;

    @Resource
    private HomeRecommendHotMapper homeRecommendHotMapper;


    /**
     * <b>功能描述：</b>获取商品详细信息<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppInfoDetailVo getAppInfo(Long aiId) {

        AppInfoPo appInfoPo = appInfoMapper.getAppInfo(aiId);
        List<AppPriceTypeForDetailPo> appPriceTypeMappers = appPriceTypeService.getAppPriceTypelist(aiId);
        //获取商品富文本
        AppPage appPage = appPageMapper.getPageInfoByAiId(aiId);
        //获取多图片信息
        List<FileInfo> fileInfos = sysImagesMapper.queryImagesList(aiId.toString());
        AppInfoDetailVo appInfoDetailVo = new AppInfoDetailVo(appInfoPo, appPriceTypeMappers, appPage);
        appInfoDetailVo.setFileInfoList(fileInfos);
        return appInfoDetailVo;
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
    public void save(AppInfo appInfo) throws ExcelException {
        checkName(appInfo.getName(), null);
        this.insert(appInfo);
    }

    /**
     * <b>功能描述：</b>商品名称重复校验<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    public void checkName(String name, String ai_id) throws ExcelException {
        List<AppInfo> list = listName(name, ai_id);
        if (list != null && list.size() > 0) {
            throw new ExcelException("商品名称重复：".concat(name));
        }
    }


    /**
     * <b>功能描述：</b>查询名称列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public List<AppInfo> listName(String name, String ai_id) {
        return appInfoMapper.listName(name, ai_id);
    }

    /**
     * <b>功能描述：</b>商品批量修改状态<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateStatusBatch(UpdateStatusBatchCnd updateStatusBatchCnd) {
        Integer status = updateStatusBatchCnd.getStatus();
        if(status == 0){
            Map<String,Object> paramsMap = new HashMap<>();
            paramsMap.put("aiIdList",updateStatusBatchCnd.getAiIds());
            Integer hrc = homeRecommendCateMapper.queryExistById(paramsMap);
            if(hrc>0){
                throw new BusException(ResultEnum.ADMIN_UNABE_DELETE);//"无法下架，首页轮播或分组正在使用"
            }
            //home_recommend_hot
            Integer hrh = homeRecommendHotMapper.queryExistById(paramsMap);
            if(hrh>0){
                throw new BusException(ResultEnum.ADMIN_UNABLE_DELETE_R);//"无法下架，首页推荐正在使用"
            }
        }
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
    public PageVo listPage(AppInfoListCnd appInfoListCnd) {
        Integer page = appInfoListCnd.getPage();
        Integer limit = appInfoListCnd.getLimit();
        Page<AppInfoListVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<AppInfoListVo> appInfoListVoList = appInfoMapper.listPage(appInfoListCnd);
        PageVo<AppInfoListVo> pageVo = new PageVo<>(appInfoListVoList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
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
     * <b>功能描述：</b>根据商品名称获取商品Id<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    public List<String> getIdByName(List<String> nameList) {
        return appInfoMapper.getIdByName(nameList);
    }

    /**
     * <b>功能描述：</b>修改富文本内容<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateAppPage(AppPage appPage) {
        //删除
        appPage.setModifyTime(new Date());
        if (appPageMapper.update(appPage) == 0) {
            this.saveAppPage(appPage);
        }
    }

    @Override
    protected GenericMapper<AppInfo> getGenericMapper() {
        return appInfoMapper;
    }
}
