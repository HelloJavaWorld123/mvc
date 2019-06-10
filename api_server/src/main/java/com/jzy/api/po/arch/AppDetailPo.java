package com.jzy.api.po.arch;

import com.jzy.api.model.app.FileInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>前台查询商品详情<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppDetailPo {

    /**
     * 商品主键
     */
    private String appId;

    /**
     * 商品名称
     */
    private String appName;

    /**
     * 商品图标
     */
    private String appIcon;

    /**
     * 商品类型(默认值0游戏,1服务,2卡密)
     */

    private String typeId;

    /**
     * 主账号名称
     */
    private String accMainName;
    /**
     * 主账号正则
     */
    private String accMainRegular;
    /**
     * 主账号提示信息
     */
    private String accMainMsg;

    /**
     * 主帐号最大长度
     */
    private Integer accMainMaxlen;

    /**
     *副帐号最大长度
     */
    private Integer accSubMaxlen;



    /**
     * 游戏id
     */
    private String gameId;

    /**
     * 附属账号名称
     */
    private String accSubName;

    /**
     * 附属账号正则
     */

    private String accSubRegular;

    /**
     * 附属账号提示信息
     */

    private String accSubMsg;

    /**
     * 商品描述信息 app_page 表里的 content
     */

    private String content;


    /**
     * 充值模式: 0 直充、1 卡密
     */
    private Integer rechargeMode;

    /**
     * 是否有大区
     */
    private Boolean isArea;

    /**
     * 是否有服务器
     */
    private Boolean isServ;

    /**
     * 是否允许用户输入自定义金额
     */
    private Boolean isCustom;

    /**
     * 商品充值类型列表
     */
    private List<AppPriceTypePo> appPriceTypePoList = new ArrayList<>();


    /**
     * 渠道商图片信息（富文本）
     */
    private List<FileInfo> fileInfoList = new ArrayList<>();


    public String getAccMainName() {
        if (accMainName == null) {
            return "";

        }
        return accMainName;
    }

    public String getAccMainRegular() {
        if (accMainRegular == null) {
            return "";

        }
        return accMainRegular;
    }

    public String getAccMainMsg() {
        if (accMainMsg == null) {
            return "";

        }
        return accMainMsg;
    }

    public String getAccSubName() {
        if (accSubName == null) {
            return "";

        }
        return accSubName;
    }

    public String getAccSubRegular() {
        if (accSubRegular == null) {
            return "";

        }
        return accSubRegular;
    }

    public String getAccSubMsg() {
        if (accSubMsg == null) {
            return "";
        }
        return accSubMsg;
    }
}
