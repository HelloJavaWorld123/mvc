package com.jzy.api.po.app;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

/**
 * <b>功能：</b>渠道商查询详情返回充值类型列表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppPriceTypeForDetailPo {

    /**
     * 主键
     */
    private String id;
    /**
     * 应用id
     */
    private String aiId;

    /**
     * 充值类型名称
     */
    private String name;

    /**
     * 单位
     */
    private String unit;


    /**
     * 用户输入数量倍数
     */
    private Integer multiple;

    /**
     * 最小充值数量
     */
    private Integer minmum;

    /**
     * 最大充值数量
     */
    private Integer maxmum;

    /**
     * 1元兑换比例
     */
    private String subscriptionRatio;

}
