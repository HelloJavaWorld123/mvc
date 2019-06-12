package com.jzy.api.cnd.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @Author : RXK
 * Date : 2019/5/29 19:06
 * Version: V1.0.0
 * Desc: 权限资源入参
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPermissionCnd extends PageCnd {

	@NotNull(groups = {UpdateValidator.class, DeleteValidator.class},message = "主键不能为空")
	private Long id;

	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "资源唯一key不能为空")
	private String uniqueKey;

	/**
	 * 没有的话 传0
	 */
	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "父级唯一key不能为空")
	private String parentKey;

	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "资源名称不能为空")
	private String permName;

	@NotNull(groups = {CreateValidator.class,UpdateValidator.class},message = "权限类型不能为空")
	private Integer permType;

	private Integer leafNode;

	@NotNull(groups = {CreateValidator.class,UpdateValidator.class},message = "资源的状态不能为空")
	private Integer permStatus;

	private Long operatorId;
}
