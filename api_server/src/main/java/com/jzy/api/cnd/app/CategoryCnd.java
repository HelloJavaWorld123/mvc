package com.jzy.api.cnd.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用分类
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Data
@ApiModel(value="商品分类保存更新参数")
public class CategoryCnd{

    /**
     * id
     */
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 应用分类名称
     */
    @ApiModelProperty(value = "商品所属分类名称")
    private String name;

    /**
     * 序列:值越小越靠前,默认1
     */
    @ApiModelProperty(value = "商品分类排序")
    private Integer sort;
}
