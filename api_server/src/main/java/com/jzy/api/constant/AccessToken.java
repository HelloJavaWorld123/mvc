package com.jzy.api.constant;

public enum AccessToken {

    /**
     * 后端登录成功标识
     */
    EMP("apiEmpToken"),

    /**
     * 前端登录成功标识
     */
    USER("apiUserToken"),

    /**
     * 用于判断是前台还是后端登录
     */
    APP("appType");

    /**
     * 错误类型 *
     */
    private String value;

    AccessToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
