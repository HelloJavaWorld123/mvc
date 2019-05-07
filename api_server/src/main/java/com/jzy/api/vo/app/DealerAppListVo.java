package com.jzy.api.vo.app;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

/**
 * 应用分类
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Data
public class DealerAppListVo {

    private String id;

    private String name;

    private Integer sort;

    private String icon;

    private String firstLetter;

    private String spllLetter;

    private Boolean isReco;

    private Boolean isHot;
}
