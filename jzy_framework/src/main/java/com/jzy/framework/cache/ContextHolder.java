package com.jzy.framework.cache;

import lombok.Data;

@Data
public class ContextHolder {
    /**
     * 后端信息缓存
     */
    private EmpCache empCache;
    /**
     * 前台信息缓存
     */
    private UserCache userCache;

}
