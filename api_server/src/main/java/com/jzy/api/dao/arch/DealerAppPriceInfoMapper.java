package com.jzy.api.dao.arch;

import com.jzy.api.cnd.arch.GetDealerAppListCnd;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.model.dealer.DealerAppPriceType;
import com.jzy.api.po.arch.AppDetailPo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.vo.dealer.GetDealerAppVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;


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

}
