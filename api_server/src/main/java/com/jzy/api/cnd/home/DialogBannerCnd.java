package com.jzy.api.cnd.home;

import com.jzy.api.model.app.FileInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>渠道商推荐里跳转<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190516&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="618活动弹出和banner参数")
public class DialogBannerCnd {

    /**
     * 1弹窗，2banner
     */
    @ApiModelProperty(value = "1弹窗，2banner")
    private String type;

    /**
     * 渠道商id
     */
    @ApiModelProperty(value = "渠道商id")
    private Long dealerId;

    /**
     * 商品id
     */
    @ApiModelProperty(value = "商品id")
    private String aiId;


}
