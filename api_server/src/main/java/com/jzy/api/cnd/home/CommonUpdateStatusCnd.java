package com.jzy.api.cnd.home;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommonUpdateStatusCnd {

    /**
     * 是否启用  0是禁用   1是启用
     */
    private Integer status;

    private List<Long> ids = new ArrayList<>();
}
