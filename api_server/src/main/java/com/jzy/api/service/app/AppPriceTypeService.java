package com.jzy.api.service.app;

import com.jzy.api.cnd.app.SaveAppPriceTypeListCnd;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.po.app.AppPriceTypeForDetailPo;
import com.jzy.api.po.arch.AppPriceTypePo;
import com.jzy.api.po.dealer.AppPriceTypeListPo;
import com.jzy.framework.service.GenericService;

import java.util.List;

/**
 * <b>功能：</b>商品充值类型表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190418&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */

public interface AppPriceTypeService extends GenericService<AppPriceType> {
    /**
     * <b>功能描述：</b>批量保存充值列表<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void saveAppPriceTypeList(SaveAppPriceTypeListCnd saveAppPriceTypeListCnd);


    /**
     * <b>功能描述：</b>查询当前商品充值类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<AppPriceTypeListPo> getAppPriceTypelist(String aiId, String dealerId);


    /**
     * <b>功能描述：</b>查询当前商品充值类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<AppPriceTypeForDetailPo> getAppPriceTypelist(Long id);

}
