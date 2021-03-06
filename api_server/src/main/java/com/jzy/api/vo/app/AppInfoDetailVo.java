package com.jzy.api.vo.app;

import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.po.app.AppInfoPo;
import com.jzy.api.po.app.AppPriceTypeForDetailPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>查询商品详情接口返回<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190419&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppInfoDetailVo {

    /**
     * 商品单表详情
     */
    private AppInfoPo appInfo;
    /**
     * 商品充值类型列表
     */
    private List<AppPriceTypeForDetailPo> AppPriceTypeList = new ArrayList<>(10);

    /**
     * 多图片信息
     */
    private List<FileInfo> fileInfoList = new ArrayList<>();

    /**
     * 商品文本信息
     */
    private AppPage appPage;


    public AppInfoDetailVo(AppInfoPo AppInfo, List<AppPriceTypeForDetailPo> AppPriceTypeList, AppPage AppPage) {
        this.appInfo = AppInfo;
        this.AppPriceTypeList = AppPriceTypeList;
        this.appPage = AppPage;
    }

    public AppInfoPo getAppInfo() {
        return appInfo;
    }

}
