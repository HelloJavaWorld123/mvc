package com.jzy.common.enums;

/**
 * 返回参数枚举
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
public enum ResultEnum {

    OPERATION_FAILED(-3, "操作失败"),
    INVALID_REQ(-2, "无效请求，请刷新页面"),
    UNKONW_ERR(-1, "未知错误"),
    SUCCESS(0, "成功"),
    FAIL(1, "失败"),
    TIMED_OUT(101, "已超时，请重新登录"),
    SYSTEM_BUSY(102, "系统繁忙，请稍候尝试"),
    PARAM_ERR(103, "不合法的参数"),
    VERIFY_EXPIRED(1001, "验证码已过期"),
    VERIFY_ERR(1002, "验证码错误!"),
    MOBILE_ERR(1005, "手机号码格式有误"),
    MOBILE_EXIST(1006, "手机号码已存在"),
    MOBILE_DIFFER(1007, "手机号码不正确"),
    ADDR_EXCESS(1011, "您的地址数量已达上限"),
    MONGO_ERR(2001, "MONGO服务不可用"),
    REDIS_ERR(2002, "REDIS服务不可用"),
    WECHAT_ERR(2003, "微信服务不可用"),
    ACCOUNT_NOTEXIST(4001, "帐号不存在"),
    ACCOUNT_UPWDERR(4002, "密码有误"),
    PERMISSION_DENIED(4003, "权限不足"),
    AUTH_OVERFLOW(4005, "资源分配溢权"),
    AUTH_ASSIGNERR(4006, "资源分配失败"),
    APP_UNABLE_DELETE(4020, "正在使用,无法删除"),
    APP_NAME_NOTEMPTY(4021, "名称不能为空"),
    APP_EMPTY(4022, "资源不存在");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}