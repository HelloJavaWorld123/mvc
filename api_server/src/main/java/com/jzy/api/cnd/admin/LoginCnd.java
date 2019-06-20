package com.jzy.api.cnd.admin;

import com.jzy.framework.bean.cnd.GenericCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import sun.rmi.runtime.Log;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="用户登录参数")
public class LoginCnd extends GenericCnd {

    public interface LoginValidator {}
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    @NotEmpty(groups = {LoginValidator.class},message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    @NotEmpty(groups = {LoginValidator.class},message = "用户密码不能为空")
    @Length(groups = {LoginValidator.class},min = 4,max = 16,message = "请输出正确位数的密码")
    private String pwd;

    @ApiModelProperty(value = "是否记住")
    @NotNull(groups = {LoginValidator.class},message = "是否记住不能为空")
    private Boolean rememberMe;

}
