package com.jzy.api.service.arch.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppSearchListCnd;
import com.jzy.api.cnd.arch.*;
import com.jzy.api.dao.app.AppGameMapper;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.AppPriceTypeMapper;
import com.jzy.api.dao.arch.DealerAppInfoMapper;
import com.jzy.api.dao.arch.DealerAppPriceInfoMapper;
import com.jzy.api.dao.arch.DealerAppPriceTypeMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.dealer.DealerAppInfo;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.model.dealer.DealerAppPriceType;
import com.jzy.api.po.app.AppGameListPo;
import com.jzy.api.po.arch.AppDetailPo;
import com.jzy.api.po.arch.AppPriceTypePo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.po.dealer.AppPriceTypeListPo;
import com.jzy.api.po.dealer.AppSearchPo;
import com.jzy.api.po.dealer.DealerAppTypePriceInfoPo;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.service.arch.DealerAppInfoService;
import com.jzy.api.service.arch.DealerAppPriceInfoService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.vo.app.AppDetailVo;
import com.jzy.api.vo.dealer.DealerAppPriceInfoDetailVo;
import com.jzy.api.vo.dealer.GetDealerAppVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能：</b>渠道商商品定价<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class DealerAppPriceInfoServiceImpl extends GenericServiceImpl<DealerAppPriceInfo> implements DealerAppPriceInfoService {


    @Resource
    private AppGameMapper appGameMapper;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private DealerAppPriceInfoMapper dealerAppPriceInfoMapper;

    @Resource
    private AppPriceTypeMapper appPriceTypeMapper;

    @Resource
    private DealerAppInfoMapper dealerAppInfoMapper;

    @Resource
    private AppInfoService appInfoService;

    @Resource
    private AppPriceTypeService appPriceTypeService;

    @Resource
    private TableKeyService tableKeyService;

    @Resource
    private DealerAppInfoService dealerAppInfoService;

    @Resource
    private DealerAppPriceTypeMapper dealerAppPriceTypeMapper;

    @Override
    protected GenericMapper<DealerAppPriceInfo> getGenericMapper() {
        return dealerAppPriceInfoMapper;
    }

    /**
     * <b>功能描述：</b>前台查询商品价格接口<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<DealerAppPriceInfoPo> getPrice(GetPriceCnd getPriceCnd) {
        String aiId = getPriceCnd.getAiId();
        String aptId = getPriceCnd.getAptId();
        String dealerId = getFrontDealerId();
        List<DealerAppPriceInfoPo> dealerAppPriceInfoPoList = dealerAppPriceInfoMapper.getPrice(aiId, aptId, dealerId);
        return dealerAppPriceInfoPoList;
    }

    /**
     * <b>功能描述：</b>前台查询商品详情<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppDetailVo getAppDetail(String aiId) {
        AppDetailVo appDetailVo = new AppDetailVo();
        AppInfo appInfo = appInfoMapper.queryById(Long.valueOf(aiId));
        String dealerId = getFrontDealerId();
        List<String> aiIdList = checkMap(appInfo, aiId);
        //获取前台商品详情信息
        List<AppDetailPo> appDetailPos = dealerAppPriceInfoMapper.getFrontAppInfo(aiIdList, dealerId);
        for (AppDetailPo appDetailPo : appDetailPos) {
            checkAreaAndServ(appDetailPo);
            List<AppPriceTypePo> appPriceTypelist = appPriceTypeMapper.getAppPriceTypePolist(Long.valueOf(appDetailPo.getAppId()));
            appDetailPo.setAppPriceTypePoList(appPriceTypelist);
        }
        appDetailVo.setAppDetailPoList(appDetailPos);
        return appDetailVo;
    }

    /**
     * <b>功能描述：</b>校验是否存在区和服<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private AppDetailPo checkAreaAndServ(AppDetailPo appDetailPo) {
        List<String> ids = new ArrayList<>();
        //查询区
        List<AppGameListPo> appAreaListPos = appGameMapper.checkAreaInfo(appDetailPo.getGameId());
        if (appAreaListPos.size() > 0) {
            appDetailPo.setIsArea(true);
            //拼装查询是否存在服的app_game主键
            ids = getIds(appAreaListPos, ids);
        } else {
            appDetailPo.setIsArea(false);
        }
        //查询服
        if (ids.size() > 0) {//存在区不为空且不是无，确定是否存在服（有区）
            checkServExist(ids, appDetailPo);
        } else {//存在区为空或者无（无区有服）
            List<AppGameListPo> checkAppAreaListPo = appGameMapper.getAreaInfo(Long.valueOf(appDetailPo.getAppId()));
            ids = getIds(checkAppAreaListPo, ids);
            checkServExist(ids, appDetailPo);
        }
        return appDetailPo;
    }

    /**
     * <b>功能描述：</b>拼装查询是否存在服的app_game主键<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private List<String> getIds(List<AppGameListPo> appAreaListPos, List<String> ids) {
        for (AppGameListPo appGameListPo : appAreaListPos) {
            ids.add(appGameListPo.getId());
        }
        return ids;
    }

    /**
     * <b>功能描述：</b>校验是否存在服<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private AppDetailPo checkServExist(List<String> ids, AppDetailPo appDetailPo) {
        // 没有大区，则不检查 服 数据
        if (ids.isEmpty()) {
            appDetailPo.setIsServ(false);
            return appDetailPo;
        }

        List<AppGameListPo> appServListPos = appGameMapper.checkServInfo(ids);
        if (appServListPos.size() > 0) {
            appDetailPo.setIsServ(true);
        } else {
            appDetailPo.setIsServ(false);
        }
        return appDetailPo;
    }

    /**
     * <b>功能描述：</b>渠道商商品热门搜索<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo appSearchList(AppSearchListCnd appSearchListCnd) {
        Integer page = appSearchListCnd.getPage();
        Integer limit = appSearchListCnd.getLimit();
        String dealeId = getFrontDealerId();
        Page<AppSearchPo> searchPoPage = PageHelper.startPage(appSearchListCnd.getPage(), appSearchListCnd.getLimit());
        List<AppSearchPo> appSearchPos = dealerAppInfoMapper.appSearchList(appSearchListCnd.getKeyword(), dealeId);
        PageVo<AppSearchPo> pageVo = new PageVo<>(appSearchPos);
        pageVo.setTotalCount(searchPoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }


    /**
     * <b>功能描述：</b>查询渠道商下对应的商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo getList(GetDealerAppListCnd getDealerAppListCnd) {

        Integer page = getDealerAppListCnd.getPage();
        Integer limit = getDealerAppListCnd.getLimit();
        Page pageInfo = PageHelper.startPage(page, limit);
        List<GetDealerAppVo> getDealerAppVoList = dealerAppPriceInfoMapper.getList(getDealerAppListCnd);
        PageVo<GetDealerAppVo> getDealerAppVoPageVo = new PageVo<>(getDealerAppVoList);
        getDealerAppVoPageVo.setTotalCount(pageInfo.getTotal());
        getDealerAppVoPageVo.setPage(page);
        getDealerAppVoPageVo.setLimit(limit);
        return getDealerAppVoPageVo;
    }


    /**
     * <b>功能描述：</b>查询渠道商商品定价详情<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public DealerAppPriceInfoDetailVo getDealerAppDetail(GetPriceInfoCnd getPriceInfoCnd) {
        String dealerId = getPriceInfoCnd.getDealerId();
        String aiId = getPriceInfoCnd.getAiId();
        DealerAppPriceInfoDetailVo dealerAppPriceInfoDetailVo = new DealerAppPriceInfoDetailVo();
        List<DealerAppTypePriceInfoPo> dealerAppTypePriceInfoList = new ArrayList<>(10);
        dealerAppPriceInfoDetailVo.setDealerAppTypePriceInfoList(dealerAppTypePriceInfoList);
        //查询商品详情
        AppInfo appinfo = appInfoService.queryAppById(Long.valueOf(getPriceInfoCnd.getAiId()));
        dealerAppPriceInfoDetailVo.setAppName(appinfo.getName());
        dealerAppPriceInfoDetailVo.setAppCode(appinfo.getCode());
        //查询充值类型列表
        List<AppPriceTypeListPo> appPriceTypeMapperList = appPriceTypeService.getAppPriceTypelist(getPriceInfoCnd.getAiId(), getPriceInfoCnd.getDealerId());
        for (AppPriceTypeListPo appPriceType : appPriceTypeMapperList) {
            DealerAppTypePriceInfoPo dealerAppTypePriceInfo = new DealerAppTypePriceInfoPo();
            dealerAppTypePriceInfo.setTypeName(appPriceType.getName());
            dealerAppTypePriceInfo.setAptId(appPriceType.getAptId());
            dealerAppTypePriceInfo.setIsCustom(appPriceType.getIsCustom());
            //获取商品面值详情
            List<DealerAppPriceInfoPo> dealerAppPriceInfoList = dealerAppPriceInfoMapper.getDealerAppPriceInfo(appPriceType.getAptId(), aiId, dealerId);
            dealerAppTypePriceInfo.setDealerAppPriceInfoPoList(dealerAppPriceInfoList);
            dealerAppTypePriceInfoList.add(dealerAppTypePriceInfo);
        }
        return dealerAppPriceInfoDetailVo;
    }

    /**
     * <b>功能描述：</b>渠道商商品定价保存<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(SavePriceInfoCnd savePriceInfoCnd) {

        String aiId = savePriceInfoCnd.getDealerAppInfoCnd().getAiId();
        String dealerId = savePriceInfoCnd.getDealerAppInfoCnd().getDealerId();
        DealerAppInfo dealerAppInfo = new DealerAppInfo();
        BeanUtils.copyProperties(savePriceInfoCnd.getDealerAppInfoCnd(), dealerAppInfo);
        if (dealerAppInfoService.update(dealerAppInfo) == 0) {
            dealerAppInfo.setId(tableKeyService.newKey("dealer_app_info", "id", 0));
            dealerAppInfoService.insert(dealerAppInfo);
        }
        //全量更新  物理删除
        dealerAppPriceInfoMapper.deleteByDealerIdAndaiId(aiId, dealerId);
        dealerAppPriceTypeMapper.delete(dealerId, aiId);
        //更新
        for (DealerAppPriceInfoCnd dapi : savePriceInfoCnd.getDealerAppPriceInfoPoList()) {
            DealerAppPriceInfo dealerAppPriceInfo = new DealerAppPriceInfo();
            BeanUtils.copyProperties(dapi, dealerAppPriceInfo);
            dealerAppPriceInfo.setId(tableKeyService.newKey("dealer_app_price_info", "id", 0));
            dealerAppPriceInfo.setAiId(aiId);
            dealerAppPriceInfo.setDealerId(dealerId);
            this.insert(dealerAppPriceInfo);
            //保存是否自定义金额
            DealerAppPriceType dealerAppPriceType = new DealerAppPriceType();
            dealerAppPriceType.setAiId(aiId);
            dealerAppPriceType.setDealerId(dealerId);
            dealerAppPriceType.setAptId(dealerAppPriceInfo.getAptId());
            dealerAppPriceType.setIsCustom(dapi.getIsCustom());
            dealerAppPriceTypeMapper.insert(dealerAppPriceType);

        }

    }

    /**
     * <b>功能描述：</b>批量修改上下架状态<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void batchUpdateStatus(BatchUpdateStatusCnd batchUpdateStatusCnd) {
        List<String> aiIdList = batchUpdateStatusCnd.getAiIdList();
        String dealerId = batchUpdateStatusCnd.getDealerId();
        Integer status = batchUpdateStatusCnd.getStatus();
        for (String aiId : aiIdList) {
            dealerAppInfoMapper.updateStatus(status, aiId, dealerId);
        }


    }

    /**
     * <b>功能描述：</b>创建商品名称常量<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private List<String> checkMap(AppInfo appInfo, String aiId) {
        Map<String, Object> map = new HashMap<>();
        List<String> aiIdList = new ArrayList<>();
        map.put("移动话费", 1);
        map.put("联通话费", 1);
        map.put("电信话费", 1);
        map.put("移动流量", 1);
        map.put("联通流量", 1);
        map.put("电信流量", 1);
        if (map.containsKey(appInfo.getName())) {
            aiIdList.add("15570566368665931");
            aiIdList.add("155705663681750532");
            aiIdList.add("155705663683486412");
            aiIdList.add("155705663793823380");
            aiIdList.add("155705663795659260");
            aiIdList.add("155705663797342898");
        } else {
            aiIdList.add(aiId);
        }
        return aiIdList;
    }


    /*-------------------------------------------------------------------后台接口-----------------------------------------------------*/


}


