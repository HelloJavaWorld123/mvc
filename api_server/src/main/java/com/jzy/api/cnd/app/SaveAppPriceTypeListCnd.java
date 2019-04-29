package com.jzy.api.cnd.app;


import com.jzy.api.model.app.AppPriceType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <b>功能：</b>商品充值类型批量添加 <br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190419&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SaveAppPriceTypeListCnd implements Serializable {

    /**
     * 商品的主键
     */
    private String aiId;

    /**
     * 充值类型列表
     */
    private List<AppPriceType> AppPriceTypeList = new ArrayList<>(10);


}
