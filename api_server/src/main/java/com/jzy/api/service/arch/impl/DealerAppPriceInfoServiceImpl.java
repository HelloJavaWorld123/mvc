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
import com.jzy.api.dao.sys.SysImagesMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.dealer.DealerAppInfo;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.model.dealer.DealerAppPriceType;
import com.jzy.api.po.app.AppGameListPo;
import com.jzy.api.po.app.AppPriceTypeForDetailPo;
import com.jzy.api.po.arch.AppDetailPo;
import com.jzy.api.po.arch.AppPriceTypePo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
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
import com.jzy.framework.exception.BusException;
import com.jzy.framework.exception.ExcelException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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
    private SysImagesMapper sysImagesMapper;

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
        Integer dealerId = getFrontDealerId();
        List<DealerAppPriceInfoPo> dealerAppPriceInfoPoList = dealerAppPriceInfoMapper.getPrice(aiId, aptId, dealerId + "");
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
        Integer dealerId = getFrontDealerId();
        List<String> aiIdList = checkMap(appInfo, aiId);
        //获取前台商品详情信息
        List<AppDetailPo> appDetailPos = dealerAppPriceInfoMapper.getFrontAppInfo(aiIdList, dealerId + "");
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("aiId",aiId);
        paramsMap.put("dealerId",dealerId+"");
        for (AppDetailPo appDetailPo : appDetailPos) {
            checkAreaAndServ(appDetailPo);
            List<AppPriceTypePo> appPriceTypelist = appPriceTypeMapper.getAppPriceTypePolist(Long.valueOf(appDetailPo.getAppId()), Long.valueOf(dealerId));
            List<AppPriceTypePo> tempList = new LinkedList<>();

            for (AppPriceTypePo apt:appPriceTypelist){
                //dealer_app_price_info
                paramsMap.put("aptId",apt.getTypeId());
                Integer dapiCount = dealerAppPriceInfoMapper.queryExitsByParams(paramsMap);
                if(dapiCount>0){
                    tempList.add(apt);
                }
            }
            appDetailPo.setAppPriceTypePoList(tempList);
            //appDetailPo.setAppPriceTypePoList(appPriceTypelist);
            //查询富文本图片信息
            List<FileInfo> fileInfos = sysImagesMapper.queryImagesList(appDetailPo.getAppId());
            appDetailPo.setFileInfoList(fileInfos);
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
        Integer dealeId = getFrontDealerId();
        Page<AppSearchPo> searchPoPage = PageHelper.startPage(appSearchListCnd.getPage(), appSearchListCnd.getLimit());
        List<AppSearchPo> appSearchPos = dealerAppInfoMapper.appSearchList(appSearchListCnd.getKeyword(), dealeId + "");
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
        List<AppPriceTypeForDetailPo> appPriceTypeMapperList = appPriceTypeService.getAppPriceTypelist(Long.valueOf(getPriceInfoCnd.getAiId()));
        for (AppPriceTypeForDetailPo appPriceType : appPriceTypeMapperList) {
            DealerAppTypePriceInfoPo dealerAppTypePriceInfo = new DealerAppTypePriceInfoPo();
            dealerAppTypePriceInfo.setTypeName(appPriceType.getName());
            dealerAppTypePriceInfo.setAptId(appPriceType.getId());
            dealerAppTypePriceInfo.setTypeUnit(appPriceType.getUnit());
            dealerAppTypePriceInfo.setMultiple(appPriceType.getMultiple());
            dealerAppTypePriceInfo.setMaxmum(appPriceType.getMaxmum());
            dealerAppTypePriceInfo.setMinmum(appPriceType.getMinmum());


            DealerAppPriceType dealerAppPriceType = dealerAppPriceInfoMapper.getDealerAppPriceType(aiId, dealerId, appPriceType.getId());
            if (dealerAppPriceType == null) {
                dealerAppTypePriceInfo.setIsCustom(0);
            } else {
                dealerAppTypePriceInfo.setIsCustom(dealerAppPriceType.getIsCustom());
            }
            //获取商品面值详情
            List<DealerAppPriceInfoPo> dealerAppPriceInfoList = dealerAppPriceInfoMapper.getDealerAppPriceInfo(appPriceType.getId(), aiId, dealerId);
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
    public void save(SavePriceInfoCnd savePriceInfoCnd){

        String aiId = savePriceInfoCnd.getDealerAppInfoCnd().getAiId();
        String dealerId = savePriceInfoCnd.getDealerAppInfoCnd().getDealerId();
        DealerAppInfo dealerAppInfo = new DealerAppInfo();
        BeanUtils.copyProperties(savePriceInfoCnd.getDealerAppInfoCnd(), dealerAppInfo);
        if (dealerAppInfoService.update(dealerAppInfo) == 0) {
            dealerAppInfo.setId(tableKeyService.newKey("dealer_app_info", "id", 0));
            dealerAppInfoService.insert(dealerAppInfo);
        }
        //前台输入校验
        for (DealerAppPriceTypeCnd dealerAppPriceTypeCnd : savePriceInfoCnd.getDealerAppPriceTypeCndList()) {
            //面值不重复校验
            checkPrice(dealerAppPriceTypeCnd);
            //可输入面值比例校验
            checkRatio(dealerAppPriceTypeCnd);
        }

        //更新
        for (DealerAppPriceTypeCnd dealerAppPriceTypeCnd : savePriceInfoCnd.getDealerAppPriceTypeCndList()) {
            //保存是否自定义金额
            DealerAppPriceType dealerAppPriceType = new DealerAppPriceType();
            dealerAppPriceType.setAiId(aiId);
            dealerAppPriceType.setDealerId(dealerId);
            dealerAppPriceType.setAptId(dealerAppPriceTypeCnd.getAptId());
            dealerAppPriceType.setIsCustom(dealerAppPriceTypeCnd.getIsCustom());
            if (dealerAppPriceInfoMapper.updateAppPriceType(dealerAppPriceType) == 0) {
                dealerAppPriceType.setId(tableKeyService.newKey("dealer_app_price_type", "id", 0));
                dealerAppPriceInfoMapper.insertAppPriceType(dealerAppPriceType);
            }

            List<Long> saveList = new ArrayList<>();
            //保存面值信息
            for (DealerAppPriceInfoCnd dealerAppPriceInfoCnd : dealerAppPriceTypeCnd.getDealerAppPriceInfoCnds()) {
                DealerAppPriceInfo dealerAppPriceInfo = getDealerAppPriceInfo(dealerAppPriceInfoCnd);
                dealerAppPriceInfo.setAiId(aiId);
                dealerAppPriceInfo.setDealerId(dealerId);
                dealerAppPriceInfo.setAptId(dealerAppPriceTypeCnd.getAptId());
                if (dealerAppPriceInfo.getId()!=null){
                    this.update(dealerAppPriceInfo);
                }else {
                    dealerAppPriceInfo.setId(tableKeyService.newKey("dealer_app_price_info", "id", 0));
                    this.insert(dealerAppPriceInfo);
                }
                saveList.add(dealerAppPriceInfo.getId());

            }
            //保存到数据库的id列表和查询出来的id列表求差集，做删除操作
            List<Long> queryList = dealerAppPriceInfoMapper.getIdList(dealerAppPriceType);
            queryList.removeAll(saveList);
            if (queryList.size() > 0) {
                for (Long aptId : queryList) {
                    dealerAppPriceInfoMapper.deleteAppPriceInfoById(aptId);
                }
            }
        }


    }

    /**
     * <b>功能描述：</b>获取渠道商面值信息<br>
     * <b>修订记录：</b><br>
     * <li>20190520&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    private DealerAppPriceInfo getDealerAppPriceInfo(DealerAppPriceInfoCnd dealerAppPriceInfoCnd) {

        DealerAppPriceInfo dealerAppPriceInfo = new DealerAppPriceInfo();
        BeanUtils.copyProperties(dealerAppPriceInfoCnd, dealerAppPriceInfo);
        if (dealerAppPriceInfoCnd.getPayPrice() == null) {
            dealerAppPriceInfo.setPayPrice(BigDecimal.ZERO);
        }
        if (dealerAppPriceInfoCnd.getDiscount() == null) {
            dealerAppPriceInfo.setDiscount(BigDecimal.ZERO);
        }
        return dealerAppPriceInfo;
    }

    /**
     * <b>功能描述：</b>当前商品充值类型存在面值重复，请核实！<br>
     * <b>修订记录：</b><br>
     * <li>20190517&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void checkPrice(DealerAppPriceTypeCnd dealerAppPriceTypeCnd) {
        Set<BigDecimal> checkPrice = new HashSet<>();
        List<DealerAppPriceInfoCnd> dealerAppPriceInfoCnds = dealerAppPriceTypeCnd.getDealerAppPriceInfoCnds();
        for (DealerAppPriceInfoCnd dealerAppPriceInfoCnd : dealerAppPriceInfoCnds) {
            checkPrice.add(dealerAppPriceInfoCnd.getPrice());
        }
        if (checkPrice.size() != dealerAppPriceInfoCnds.size()) {
            throw new BusException("当前商品充值类型存在面值重复，请核实！");
        }
    }

    /**
     * <b>功能描述：</b>可输入面值比例校验<br>
     * <b>修订记录：</b><br>
     * <li>20190517&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void checkRatio(DealerAppPriceTypeCnd dealerAppPriceTypeCnd) {
        Integer isCustom = dealerAppPriceTypeCnd.getIsCustom();
        Set<BigDecimal> checkRatio = new HashSet<>();
        if (isCustom == 1) {
            for (DealerAppPriceInfoCnd dealerAppPriceInfoCnd : dealerAppPriceTypeCnd.getDealerAppPriceInfoCnds()) {
                checkRatio.add(dealerAppPriceInfoCnd.getSupPrice().divide(dealerAppPriceInfoCnd.getPrice(), 2, BigDecimal.ROUND_HALF_UP));
            }
            if (checkRatio.size() > 1) {
                throw new BusException("当前商品充值类型存在面值与sup价格不成比例，请核实！");
            }
        }


    }

    /**
     * <b>功能描述：</b>批量修改上下架状态<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateStatus(BatchUpdateStatusCnd batchUpdateStatusCnd) {
        List<String> aiIdList = batchUpdateStatusCnd.getAiIdList();
        String dealerId = batchUpdateStatusCnd.getDealerId();
        Integer status = batchUpdateStatusCnd.getStatus();
        for (String aiId : aiIdList) {
            dealerAppInfoMapper.updateStatus(status, aiId, dealerId);
        }


    }

    /**
     * <b>功能描述：</b>根据商品名称获取商品Id<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private List<String> checkMap(AppInfo appInfo, String aiId) {
        List<String> aiIdList = new ArrayList<>(10);
        List<String> nameList = Arrays.asList("移动话费", "电信话费", "移动流量", "联通流量", "联通话费", "电信流量");
        if (nameList.contains(appInfo.getName())) {
            aiIdList = appInfoService.getIdByName(nameList);
        } else {
            aiIdList.add(aiId);
        }
        return aiIdList;
    }

    /**
     * <b>功能描述：</b>根据商品id获取商品价格信息<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<DealerAppPriceInfo> queryAppPriceInfoByAppId(Long appId, Long aptId) {
        return dealerAppPriceInfoMapper.queryAppPriceInfoByAppId(appId, aptId, getFrontDealerId());
    }

    /*-------------------------------------------------------------------后台接口-----------------------------------------------------*/


}



