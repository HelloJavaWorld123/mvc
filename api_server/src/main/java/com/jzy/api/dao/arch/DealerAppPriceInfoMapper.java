package com.jzy.api.dao.arch;

import com.jzy.api.cnd.arch.GetPriceCnd;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
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
    List<DealerAppPriceInfoPo> getPrice(@Param("aiId") String aiId,@Param ("aptId")  String aptId,@Param ("dealerId") String dealerId);


}
