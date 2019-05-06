package com.jzy.api.cnd.app;

import lombok.Data;

/**
 * <b>功能描述：</b>前台渠道商对应商品查询服<br>
 * <b>修订记录：</b><br>
 * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
 */
@Data
public class GetServInfoCnd {

    /**
     * 商品表主键
     */

    private String aiId;

    /**
     * 区主键
     */
    private String areaId;


}
