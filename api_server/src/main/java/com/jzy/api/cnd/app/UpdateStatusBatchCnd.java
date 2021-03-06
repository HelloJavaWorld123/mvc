package com.jzy.api.cnd.app;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>批量修改商品启用状态<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="商品状态修改参数")
public class UpdateStatusBatchCnd {

    /**
     * 是否启用  0是禁用   1是启用
     */
    @ApiModelProperty(value = "0禁用，1启用")
    private Integer status;

    /**
     * 商品主键列表
     */
    @ApiModelProperty(value = "商品主键列表")
    private List<Long> aiIds = new ArrayList<>();


}
