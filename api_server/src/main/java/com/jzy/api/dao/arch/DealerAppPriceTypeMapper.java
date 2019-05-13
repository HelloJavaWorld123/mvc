package com.jzy.api.dao.arch;

import com.jzy.api.model.dealer.DealerAppPriceType;
import org.apache.ibatis.annotations.Param;

/**
 * <b>功能：</b>渠道商关联商品充值类型表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface DealerAppPriceTypeMapper {

    /**
     * <b>功能描述：</b>新增<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int insert(DealerAppPriceType dealerAppPriceType);

    /**
     * <b>功能描述：</b>物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int delete(@Param("dealerId") String dealerId, @Param("aiId") String aiId);

}
