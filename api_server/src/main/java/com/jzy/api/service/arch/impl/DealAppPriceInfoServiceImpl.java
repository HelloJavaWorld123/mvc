package com.jzy.api.service.arch.impl;


import com.jzy.api.cnd.arch.GetPriceCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.AppPriceTypeMapper;
import com.jzy.api.dao.arch.DealerAppPriceInfoMapper;
import com.jzy.api.dao.arch.DealerMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.po.arch.AppDetailPo;
import com.jzy.api.po.arch.AppPriceTypePo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.service.arch.DealAppPriceInfoService;
import com.jzy.api.vo.app.AppDetailVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
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
public class DealAppPriceInfoServiceImpl extends GenericServiceImpl<DealerAppPriceInfo> implements DealAppPriceInfoService {


    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private DealerAppPriceInfoMapper dealerAppPriceInfoMapper;

    @Resource
    private AppPriceTypeMapper appPriceTypeMapper;

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
        String dealerId = "1001";
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
        List<String> aiIdList = checkMap(appInfo, aiId);
        //获取前台商品详情信息
        List<AppDetailPo> appDetailPos = dealerAppPriceInfoMapper.getFrontAppInfo(aiIdList, "1001");
        for (AppDetailPo appDetailPo : appDetailPos) {
            List<AppPriceTypePo> appPriceTypelist = appPriceTypeMapper.getAppPriceTypePolist(Long.valueOf(appDetailPo.getAppId()));
            appDetailPo.setAppPriceTypePoList(appPriceTypelist);
        }
        appDetailVo.setAppDetailPoList(appDetailPos);
        return appDetailVo;
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


}



