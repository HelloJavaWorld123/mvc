package com.jzy.api.po.dealer;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

/**
 * 商品面值类型
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @date 2019-01-22
 * @since JDK1.8
 */
@Data
public class AppPriceTypeListPo {

    /**
     * 应用id
     */
    private String aptId;

    /**
     * 充值类型名称
     */
    private String name;


    /**
     * 是否允许用户自定义输入金额
     */
    private Integer isCustom;


}
