package com.jzy.api.util;

import com.jzy.framework.exception.BusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

    /**
     * <b>功能描述：</b>通过属性的get方法获取属性值. 如果找不到get方法会抛出runtime异常<br>
     * <b>修订记录：</b><br>
     * <li>20140116&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     * <br>
     *
     * @param instance  要操作的对象
     * @param fieldName 属性名
     * @throws 如果是找不到方法, 会抛出一个包含NoSuchMethodException的RuntimeException
     */
    public static Object getFieldValueByGetter(Object instance, String fieldName) {
        String fieldMethodName = capitalize(fieldName);//首字母大写
        String methodName1 = "get" + fieldMethodName;
        String methodName2 = "is" + fieldMethodName;

        //查找 get 或者 is 方法
        Method method = getMethod(instance.getClass(), methodName1);
        if (method == null) {
            method = getMethod(instance.getClass(), methodName2);
        }

        if (method == null) {
            throw new RuntimeException(new NoSuchMethodException(String.format("类%s找不到属性%s的get方法",
                    instance.getClass().toString(), fieldName)));
        }

        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <b>功能描述：</b>首字母大写<br>
     * <b>修订记录：</b><br>
     * <li>20140116&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li> <br>
     */
    private static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * <b>功能描述：</b>据方法名获取方法, 无该方法返回null<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private static Method getMethod(Class<? extends Object> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (SecurityException e) {
            throw new BusException(e);
        }
    }

}
