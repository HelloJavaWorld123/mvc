package com.jzy.api.service.arch;

import com.jzy.api.model.dealer.DealerAppInfo;
import com.jzy.api.po.app.AppStatus;
import com.jzy.framework.service.GenericService;

/**
 * <b>功能：</b>渠道商商品表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190509&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface DealerAppInfoService extends GenericService<DealerAppInfo> {

    /**
     * <b>功能描述：</b>根据商品id查询商品是否存在<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param appId 商品id
     */
    AppStatus queryAppStatus(Long appId);
}
