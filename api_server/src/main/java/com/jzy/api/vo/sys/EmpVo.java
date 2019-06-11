package com.jzy.api.vo.sys;

import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.sys.Emp;
import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * <b>功能：</b>后台管理返回vo<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmpVo extends GenericVo {
    /**
     * 用户名
     */
    private String name;

    /**
     * token
     */
    private String apiEmpToken;

    /**
     * 当前用户的 权限集合
     */
    private Set<String> permValues;

	private EmpVo(String name, String apiEmpToken) {
		this.name = name;
		this.apiEmpToken = apiEmpToken;
	}

	private EmpVo(Set<String> permValues) {
		this.permValues = permValues;
	}

	public static EmpVo build(SysEmp sysEmp,String apiEmpToken){
		return new EmpVo(sysEmp.getName(),apiEmpToken);
	}

	public static EmpVo build(Set<String> permissionValues) {
		return new EmpVo(permissionValues);
	}
}
