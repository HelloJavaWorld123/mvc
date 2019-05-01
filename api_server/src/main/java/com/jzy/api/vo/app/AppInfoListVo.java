package com.jzy.api.vo.app;

import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>商品列表返回参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppInfoListVo {


    /**
     * 商品主键
     */
    private String aiId;
    /**
     * 商品排序
     */
    private String aiSort;
    /**
     * 商品编码
     */
    private String aiCode;

    /**
     * 商品名称
     */
    private String aiName;

    /**
     * 商品图标
     */
    private String aiIcon;

    /**
     * 充值类型（直充/卡密）
     */
    private Integer aiRechargeMode;

    /**
     * 启用/禁用
     */
    private Integer status;

    /**
     * 最后修改时间
     */
    private Date modifyTime;

    /**
     * 分类名称
     */
    private String atName;

    /**
     * 厂商名称
     */
    private String acpName;


}
