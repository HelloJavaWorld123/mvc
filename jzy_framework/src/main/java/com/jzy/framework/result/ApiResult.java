package com.jzy.framework.result;

import com.jzy.common.enums.ResultEnum;
import lombok.Data;

/**
 * <b>功能：</b>业务处理返回类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class ApiResult<T> {
    /**
     * 成功与失败
     * 0：失败
     * 1：成功
     */
    private Integer code = 1;
    /**
     * 错误信息
     */
    private String msg = "SUCCESS";
    /**
     * 返回数据
     */
    private T data = null;

    public ApiResult() {
    }

    /**
     * <b>功能描述：</b>响应成功<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public ApiResult(T t) {
        super();
        this.data = t;
    }

    public ApiResult success() {
        return this;
    }

    public ApiResult success(T t) {
        this.data = t;
        return this;
    }

    public ApiResult fail(String msg) {
        this.code = 0;
        this.msg = msg;
        return this;
    }

    public ApiResult fail(String msg,Integer code) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ApiResult fail(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        return this;
    }

}
