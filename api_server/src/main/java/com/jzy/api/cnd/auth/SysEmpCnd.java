package com.jzy.api.cnd.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.IDValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 12:02
 * Version: V1.0.0
 * Desc: 用户信息入参
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEmpCnd extends PageCnd {

	public interface AllotValidator {}

	public interface NameExistValidator{}

	@NotNull(groups = {DeleteValidator.class, UpdateValidator.class, IDValidator.class, AllotValidator.class},message = "主键不能为空")
	private Long id;

	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class,NameExistValidator.class},message = "用户名称不能为空")
	private String name;

	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "用户密码不能为空")
	@Length(groups = {CreateValidator.class,UpdateValidator.class},min = 4,max = 16,message = "请输入正确位数的密码")
	private String password;

	@NotEmpty(groups = {AllotValidator.class},message = "分配的角色不能为空")
	private List<Long> roleList;

	private Integer status = 0;

	//当前操作的用户
	private Long operatorId;

	private Long dealerId = 0L;
}
