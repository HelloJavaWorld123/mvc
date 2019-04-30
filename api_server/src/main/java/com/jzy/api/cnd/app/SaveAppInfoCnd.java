
package com.jzy.api.cnd.app;

import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.app.FileInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>保存商品信息传参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190420&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SaveAppInfoCnd {
    /**
     * 商品基础信息
     */
    private AppInfo appInfo;

    /**
     * 商品充值类型
     */
    private List<AppPriceType> AppPriceTypeList = new ArrayList<>(10);


    /**
     * 商品文本信息
     */
    private com.jzy.api.model.app.AppPage appPage;


    /**
     * 文件信息
     */
    private FileInfo fileInfo;


}
