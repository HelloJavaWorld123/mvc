package com.jzy.api.model.app;

import com.jzy.framework.bean.model.GenericModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商品面值类型
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @date 2019-01-22
 * @since JDK1.8
 */
@Data
@ApiModel(value="商品充值类型")
public class AppPriceType extends GenericModel {

    /**
     * 应用id
     */
    @ApiModelProperty(value = "商品主键id")
    private Long aiId;

    /**
     * 充值类型名称
     */
    @ApiModelProperty(value = "商品充值类型名称")
    private String name;

    /**
     * 单位
     */
    @ApiModelProperty(value = "充值单位")
    private String unit;


    /**
     * 用户输入数量倍数
     */
    @ApiModelProperty(value = "数量倍数")
    private Integer multiple;

    /**
     * 最小充值数量
     */
    @ApiModelProperty(value = "最小充值数量")
    private Integer minmum;

    /**
     * 最大充值数量
     */
    @ApiModelProperty(value = "最大充值数量")
    private Integer maxmum;

    /**
     * 1元兑换比例
     */
    @ApiModelProperty(value = "1元兑换比例")
    private String subscriptionRatio;

}
