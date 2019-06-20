package com.jzy.api.cnd.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能描述：</b>前台渠道商对应商品查询服<br>
 * <b>修订记录：</b><br>
 * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
 */
@Data
@ApiModel(value="前端-渠道商对应商品查询服参数")
public class GetServInfoCnd {

    /**
     * 商品表主键
     */
    @ApiModelProperty(value = "商品主键id")
    private String aiId;

    /**
     * 区主键
     */
    @ApiModelProperty(value = "区主键id")
    private String areaId;


}
