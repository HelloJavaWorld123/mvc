package com.jzy.api.constant;

/**
 * <b>功能：</b>redis缓存中的key<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class ApiRedisCacheConstant {
    /**
     * 版本
     */
    private static final String VERSION = "1.1";
    /**
     * 渠道商员工信息缓存
     */
    public static final String CACHE_DEALER_EMP = "cache_dealer_emp_" + VERSION + ":";

    /**
     * 渠道商解析信息缓存
     */
    public static final String CACHE_DEALER_ANALYSIS_INFO = "cache_dealer_analysis_info_" + VERSION + ":";


    /**
     * 后台用户基本信息缓存
     */
    public static final String USER_BASE_INFO_CACHE = "user_base_info_cache_";

    /**
     * 当前用户角色id的缓存
     */
    public static final String USER_ROLE_IDS_CACHE = "user_role_ids_cache_";

    /**
     * 用户角色值的缓存
     */
    public static final String USER_ROLE_CACHE = "user_role_cache_";

    /**
     * 用户权限值的缓存
     */
    public static final String USER_PERMISSION_CACHE = "user_permission_cache_";

}
