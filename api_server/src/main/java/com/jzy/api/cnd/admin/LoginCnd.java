package com.jzy.api.cnd.admin;

import com.jzy.framework.bean.cnd.GenericCnd;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class LoginCnd extends GenericCnd {
    /**
     * 用户名
     */
    @NotBlank
    private String username;
    /**
     * 密码
     */
    @NotBlank
    private String pwd;

}
