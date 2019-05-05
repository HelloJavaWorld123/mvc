package com.jzy.api.po.arch;

import lombok.Data;

import javax.annotation.Resource;

/**
 * <b>功能：</b>解析查询渠道商配置信息查询返回数据<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerParamInfoPo {

    /*健*/
    private String dealerKey;
    /*值*/
    private String dealerValue;
    /*备注*/
    private String dealerNote;

}
