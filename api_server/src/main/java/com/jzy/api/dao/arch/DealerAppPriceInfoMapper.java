package com.jzy.api.dao.arch;

import com.jzy.api.cnd.arch.GetDealerAppListCnd;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.model.dealer.DealerAppPriceType;
import com.jzy.api.po.arch.AppDetailPo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.vo.dealer.GetDealerAppVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;


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
public interface DealerAppPriceInfoMapper extends GenericMapper<DealerAppPriceInfo> {

    /**
     * <b>功能描述：</b>前台查询商品价格接口<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<DealerAppPriceInfoPo> getPrice(@Param("aiId") String aiId, @Param("aptId") String aptId, @Param("dealerId") String dealerId);

    /**
     * <b>功能描述：</b>获取前台商品详情信息<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppDetailPo> getFrontAppInfo(@Param("aiIdList") List<String> aiIdList, @Param("dealerId") String dealerId);


    /**
     * <b>功能描述：</b>查询渠道商下对应的商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<GetDealerAppVo> getList(GetDealerAppListCnd getDealerAppListCnd);


    /**
     * <b>功能描述：</b>查询渠道商商品面值详情<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<DealerAppPriceInfoPo> getDealerAppPriceInfo(@Param("aptId") String aptId,
                                                     @Param("aiId") String aiId, @Param("dealerId") String dealerId);

    /**
     * <b>功能描述：</b>全量更新  物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int deleteByDealerIdAndaiId(@Param("aiId") String aiId, @Param("dealerId") String dealerId);

    int deleteAppPriceType(@Param("dealerId") String dealerId, @Param("aiId") String aiId);
    int insertAppPriceType(DealerAppPriceType dealerAppPriceType);
    DealerAppPriceType getDealerAppPriceType(@Param("aiId") String aiId, @Param("dealerId") String dealerId,
                                             @Param("aptId") String aptId);

    /**
     * <b>功能描述：</b>根据商品id获取商品价格信息<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param appId 商品id
     * @param dealerId 渠道商id
     */
    List<DealerAppPriceInfo> queryAppPriceInfoByAppId(@Param("appId") Long appId,
                                                      @Param("aptId") Long aptId,
                                                      @Param("dealerId") Integer dealerId);

    /**
     * <b>功能描述：</b>修改渠道商默认面值信息<br>
     * <b>修订记录：</b><br>
     * <li>20190520&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int updateAppPriceType(DealerAppPriceType dealerAppPriceType);

    /**
     * <b>功能描述：</b>保存到数据库的id列表和查询出来的id列表求差集，做删除操作<br>
     * <b>修订记录：</b><br>
     * <li>20190520&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<Long>  getIdList(DealerAppPriceType dealerAppPriceType);

    /**
     * <b>功能描述：</b>渠道商商品面值表根据主键删除<br>
     * <b>修订记录：</b><br>
     * <li>20190520&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
      int deleteAppPriceInfoById(@Param("id") Long id);
    /**是否存在 渠道商商品定价详情
     * @Description
     * @Author lchl
     * @Date 2019/5/23 5:02 PM
     * @param paramsMap aiId 商品id aptId 商品充值类型id dealerId 渠道商id
     * @return java.lang.Integer
     */
    Integer queryExitsByParams(Map<String, Object> paramsMap);
}
