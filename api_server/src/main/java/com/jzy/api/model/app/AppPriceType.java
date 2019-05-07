package com.jzy.api.model.app;

import com.jzy.framework.bean.model.GenericModel;
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
public class AppPriceType extends GenericModel {

    /**
     * 应用id
     */
    private Long aiId;

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
    private Integer multiple = 1;

    /**
     * 最小充值数量
     */
    private Integer minmum = 1;

    /**
     * 最大充值数量
     */
    private Integer maxmum = 1;

    /**
     * 1元兑换比例
     */
    private String subscriptionRatio = "1";

}
