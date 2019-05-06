package com.jzy.api.service.home.impl;

import com.jzy.api.cnd.home.HomeAnalysisCnd;
import com.jzy.api.po.arch.DataInfo;
import com.jzy.api.po.arch.DealerAnalysisInfoPo;
import com.jzy.api.po.arch.DealerParamInfoPo;
import com.jzy.api.service.arch.DealerParamService;
import com.jzy.api.service.arch.DealerService;
import com.jzy.api.service.home.HomeAnalysisService;
import com.jzy.api.util.DesUtil;
import com.jzy.api.util.MyEncrypt;
import com.jzy.api.vo.home.HomeAnalysisInfoVo;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jzy.api.constant.ApiRedisCacheConstant.CACHE_DEALER_ANALYSIS_INFO;


/**
 * <b>功能：</b>渠道商信息解析<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class HomeAnalysisServiceImpl implements HomeAnalysisService {


    @Resource
    private RedissonClient redissonClient;

    @Resource
    private DealerService dealerService;

    @Resource
    private DealerParamService dealerParamService;

    /**
     * <b>功能描述：</b>解析加密信息返回给前端<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public HomeAnalysisInfoVo getInfo(HomeAnalysisCnd homeAnalysisCnd) {
        HomeAnalysisInfoVo homeAnalysisInfoVo = null;
        try {
            String businessId = homeAnalysisCnd.getBusinessID();
            String mData = homeAnalysisCnd.getData();
            //根据渠道商标识获取解析加密信息
            DealerAnalysisInfoPo dealerAnalysisInfoPo = dealerService.getAnalysisInfo(businessId);
            DataInfo dataInfo = verification(businessId, dealerAnalysisInfoPo.getPubKey(), dealerAnalysisInfoPo.getPriKey(), mData);
            if (!dataInfo.getFlag()) {
                return homeAnalysisInfoVo;
            }
            homeAnalysisInfoVo = new HomeAnalysisInfoVo();
            homeAnalysisInfoVo.setUserId(dataInfo.getUserId());
            homeAnalysisInfoVo.setTaken(dataInfo.getTaken());
            //根据渠道商id获取渠道商配置信息
            List<DealerParamInfoPo> dealerParamInfoPos = dealerParamService.getDealerParamInfo(dealerAnalysisInfoPo.getDealerId());
            homeAnalysisInfoVo.setDealerParamInfoPos(dealerParamInfoPos);
            homeAnalysisInfoVo.setBusinessId(businessId);
            RBucket<HomeAnalysisInfoVo> homeAnalysisInfoVoRBucket = redissonClient.getBucket(dataInfo.getTaken());
            homeAnalysisInfoVoRBucket.set(homeAnalysisInfoVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return homeAnalysisInfoVo;

    }

    /**
     * <b>功能描述：</b>Data数据参数检验是否正确<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private DataInfo verification(String businessId, String pubkey, String prikey, String mData) {
        DataInfo dataInfo = null;
        try {
            mData = java.net.URLDecoder.decode(mData, "utf-8");
            String des3Decrypt = DesUtil.des3Decrypt(mData, pubkey, "utf-8");
            //des3Decrypt:     UserID=UID20180501&Timestamp=1557038625&ApiId=1&Sign=f390650470ad1342ea2e3f46a97009ac
            Map<String, String> map = new HashMap<>();
            String[] Array = des3Decrypt.split("&");
            for (String i : Array) {
                String[] jArray = i.split("=");
                List<String> jList = Arrays.asList(jArray);
                map.put(jList.get(0), jList.get(1));
            }
            String ss = businessId + map.get("UserID") + map.get("ApiId") + map.get("Timestamp") + prikey;
            String sign = MyEncrypt.getInstance().md5(ss);
            if (sign.equals(map.get("Sign"))) {
                dataInfo = new DataInfo();
                dataInfo.setFlag(true);
                dataInfo.setUserId(map.get("UserID"));
                dataInfo.setTaken(map.get("Sign"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dataInfo;
    }

}
