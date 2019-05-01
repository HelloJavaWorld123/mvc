package com.jzy.api.cnd.home;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommonDeleteBatchCnd {

    /**
     * 商品主键列表
     */
    private List<Long> ids = new ArrayList<>();


}
