package com.jzy.api.service.arch.impl;


import com.jzy.api.cnd.arch.GetPriceCnd;
import com.jzy.api.dao.arch.DealerAppPriceInfoMapper;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.service.arch.DealAppPriceInfoService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    private DealerAppPriceInfoMapper dealerAppPriceInfoMapper;

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
        String dealerId="1001";
        List<DealerAppPriceInfoPo> dealerAppPriceInfoPoList = dealerAppPriceInfoMapper.getPrice(aiId, aptId, dealerId);
        return dealerAppPriceInfoPoList;
    }
}
