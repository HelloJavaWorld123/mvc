package com.jzy.api.service.home.impl;

import com.jzy.api.cnd.home.HomeAnalysisCnd;
import com.jzy.api.cnd.home.HomeAuthCnd;
import com.jzy.api.model.sys.UserAuth;
import com.jzy.api.po.arch.DataInfo;
import com.jzy.api.po.arch.DealerAnalysisInfoPo;
import com.jzy.api.po.arch.DealerParamInfoPo;
import com.jzy.api.service.arch.DealerParamService;
import com.jzy.api.service.arch.DealerService;
import com.jzy.api.service.home.HomeAnalysisService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.UserAuthService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.DesUtil;
import com.jzy.api.util.MD5Util;
import com.jzy.api.util.MyEncrypt;
import com.jzy.api.vo.home.HomeAnalysisInfoVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.cache.UserCache;
import com.jzy.framework.exception.BusException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
@Slf4j
public class HomeAnalysisServiceImpl implements HomeAnalysisService {


    @Resource
    private RedissonClient redissonClient;

    @Resource
    private DealerService dealerService;

    @Resource
    private DealerParamService dealerParamService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private TableKeyService tableKeyService;

    /**
     * <b>功能描述：</b>解析加密信息返回给前端<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public HomeAnalysisInfoVo update(HomeAnalysisCnd homeAnalysisCnd) {
        String businessId = homeAnalysisCnd.getBusinessID();
        String mData = homeAnalysisCnd.getData();
        //根据渠道商标识获取解析加密信息
        DealerAnalysisInfoPo dealerAnalysisInfoPo = dealerService.getAnalysisInfo(businessId);
        if (dealerAnalysisInfoPo == null){
            throw new BusException(ResultEnum.DEALER_FORBIDDEN.getMsg(),ResultEnum.DEALER_FORBIDDEN.getCode());
        }
        DataInfo dataInfo = verification(businessId, dealerAnalysisInfoPo.getPubKey(), dealerAnalysisInfoPo.getPriKey(), mData);
        if (!dataInfo.getFlag()) {
            return null;
        }
        HomeAnalysisInfoVo homeAnalysisInfoVo = new HomeAnalysisInfoVo();
        String token = CommUtils.lowerUUID();
        if (homeAnalysisCnd.getIsWxAuth() == 1) {//微信
            homeAnalysisInfoVo.setUserId(token);
        } else {
            //非微信
            homeAnalysisInfoVo.setUserId(dataInfo.getUserId());

            UserAuth userAuthTemp = userAuthService.queryUserAuthByUserId(homeAnalysisInfoVo.getUserId(),Integer.parseInt(dealerAnalysisInfoPo.getDealerId()));
            if (null==userAuthTemp){
                insertUserAuth(homeAnalysisInfoVo.getUserId(), homeAnalysisCnd.getIsWxAuth(), dealerAnalysisInfoPo.getDealerId());
            }

        }

        redisUserCache(token,homeAnalysisInfoVo.getUserId(), Integer.parseInt(dealerAnalysisInfoPo.getDealerId()));
        homeAnalysisInfoVo.setToken(token);

        //根据渠道商id获取渠道商配置信息
        List<DealerParamInfoPo> dealerParamInfoPos = dealerParamService.getDealerParamInfo(dealerAnalysisInfoPo.getDealerId());
        homeAnalysisInfoVo.setDealerParamInfoPos(dealerParamInfoPos);
        homeAnalysisInfoVo.setBusinessId(businessId);

        return homeAnalysisInfoVo;
    }
    /** 保存到缓存
     * @Description
     * @Author lchl
     * @Date 2019/5/29 6:46 PM
     * @param token
     * @param userId
     * @param dealerId
     * @return void
     */
    private void redisUserCache(String token,String userId, Integer dealerId) {

        UserCache userCache = new UserCache();
        userCache.setUserId(userId);
        userCache.setDealerId(dealerId);

        RBucket<UserCache> homeAnalysisInfoVoRBucket = redissonClient.getBucket(token);
        homeAnalysisInfoVoRBucket.set(userCache, 1, TimeUnit.DAYS);
    }

    /**
     * <b>功能描述：</b>缓存用户信息<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void insertUserAuth(String userId, Integer isWxAuth, String dealerId) {
        // 存储用户信息到本地数据库中
        UserAuth userAuth = new UserAuth();
        userAuth.setId(tableKeyService.newKey("user_auth", "id", 1000));
        userAuth.setUserId(userId);
        userAuth.setIsWxAuth(isWxAuth);
        userAuth.setDealerId(Integer.parseInt(dealerId));
//        try{
        userAuthService.insert(userAuth);
//        } catch (Exception ignore) {
//            log.info("yichangshi:",ignore);
//        }

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
                dataInfo.setToken(map.get("Sign"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dataInfo;
    }


    /**
     * 加密渠道商信息
     * @param homeAuthCnd
     * @return
     */
    public String getauth(HomeAuthCnd homeAuthCnd) {
        String businessId = homeAuthCnd.getBusinessID();
        String userId = homeAuthCnd.getUserId();

        //根据渠道商标识获取解析加密信息
        DealerAnalysisInfoPo dealerAnalysisInfoPo = dealerService.getAnalysisInfo(businessId);
        if (dealerAnalysisInfoPo == null){
            throw new BusException(ResultEnum.DEALER_FORBIDDEN.getMsg(),ResultEnum.DEALER_FORBIDDEN.getCode());
        }
        String data = encryption(businessId, dealerAnalysisInfoPo.getPubKey(), dealerAnalysisInfoPo.getPriKey(), userId);
        return data;
    }

    private String encryption(String businessId, String pubkey, String prikey, String userId) {
        String mData = "";
        try {
            String timestamp = "1559034456";
            String signData = MyEncrypt.getInstance().md5(businessId + userId + "1" + timestamp + prikey);
            String oData = "UserID=".concat(userId).concat("&Timestamp=").concat(timestamp).concat("&ApiId=1&Sign=").concat(signData);
            String des3Decrypt = DesUtil.des3Eencrypt(oData, pubkey);
            mData = java.net.URLEncoder.encode(des3Decrypt, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return mData;
    }
}
