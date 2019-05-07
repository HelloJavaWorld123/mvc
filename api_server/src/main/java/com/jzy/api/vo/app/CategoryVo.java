package com.jzy.api.vo.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

/**
 * 应用分类
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Data
public class CategoryVo extends GenericModel {

    /**
     * 应用分类名称
     */
    private String name;

    /**
     * 序列:值越小越靠前,默认1
     */
    private Integer sort;
}
