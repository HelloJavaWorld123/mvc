package com.jzy.api.model.sys;

import com.jzy.api.model.auth.Role;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Set;

/**
 * <b>功能：</b>渠道商员工<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class Emp extends GenericModel {
    /**
     * 用户名
     */
    private String name;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 职务id
     */
    private Set<Role> roles;
    /**
     * 商户id
     */
    private Integer dealerId;

    //---------------------渠道商员工信息缓存虚列--------------------------

    private String DEALER_EMP_TOKEN;
}
