package com.jzy.api.po.arch;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>渠道商需要解析的信息查询<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAnalysisInfoPo {

    /**
     * 渠道商主键
     */
    private String dealerId;

    /**
     * 渠道商公钥
     */
    private String pubKey;

    /**
     * 渠道商私钥
     */
    private String priKey;
}
