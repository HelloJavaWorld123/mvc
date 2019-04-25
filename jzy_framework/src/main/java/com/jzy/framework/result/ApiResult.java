package com.jzy.framework.result;

public class ApiResult<T> {
    /**
     * 成功与失败
     * 0：失败
     * 1：成功
     */
    private Integer result = 0;
    /**
     * 错误编码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

}
