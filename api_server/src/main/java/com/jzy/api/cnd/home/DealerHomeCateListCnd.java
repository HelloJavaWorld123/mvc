package com.jzy.api.cnd.home;

import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;

@Data
public class DealerHomeCateListCnd extends PageCnd {

    /**
     * 渠道商Id
     */
    private String dealerId;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 0 轮播图 1 推荐分类
     */
    private Integer type;
}
