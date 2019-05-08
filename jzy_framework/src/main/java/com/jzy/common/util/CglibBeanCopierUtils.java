package com.jzy.common.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <b>功能：</b>属性拷贝<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class CglibBeanCopierUtils {

    /**
     * 初始化map的容量，防止重新散列
     */
    private static Map<String, BeanCopier> beanCopierMap = new ConcurrentHashMap<>(64);

    /**
     * <b>功能描述：</b>拷贝属性<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static void copyProperties(Object source, Object target) {
        // 获取拷贝对象
        BeanCopier copier = getBeanCopier(source, target);
        // 属性拷贝
        copier.copy(source, target, null);
    }

    /**
     * <b>功能描述：</b>获取拷贝对象<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static BeanCopier getBeanCopier(Object source, Object target) {
        String key = source.toString() + target.toString();
        return beanCopierMap.putIfAbsent(key,
                BeanCopier.create(source.getClass(), target.getClass(), false));
    }

}
