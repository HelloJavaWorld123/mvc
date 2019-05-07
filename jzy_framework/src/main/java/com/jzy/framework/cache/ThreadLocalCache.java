package com.jzy.framework.cache;

/**
 * <b>功能：</b>线程本地存储局部变量<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class ThreadLocalCache {

    private static ThreadLocal<ContextHolder> context = new ThreadLocal<>();

    public static void setContextHolder(ContextHolder contextHolder) {
        context.set(contextHolder);
    }

    public static ContextHolder getContextHolder() {
        return context.get();
    }

    public static void remove(){
        context.remove();
    }

}
