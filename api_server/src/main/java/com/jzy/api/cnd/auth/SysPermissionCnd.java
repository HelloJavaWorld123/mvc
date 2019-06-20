package com.jzy.api.cnd.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.framework.bean.cnd.PageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @Author : RXK
 * Date : 2019/5/29 19:06
 * Version: V1.0.0
 * Desc: 权限资源入参
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="权限信息")
public class SysPermissionCnd extends PageCnd {

	@ApiModelProperty(value = "主键id")
	@NotNull(groups = {UpdateValidator.class, DeleteValidator.class},message = "主键不能为空")
	private Long id;

	@ApiModelProperty(value = "权限值，shiro的权限控制表达式")
	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "资源唯一key不能为空")
	@Length(groups = {CreateValidator.class,UpdateValidator.class},max = 50,message = "唯一标识最长不超过50个字符")
	private String uniqueKey;

	/**
	 * 没有的话 传0
	 */
	@ApiModelProperty(value = "父级表达式")
	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "父级唯一key不能为空")
	@Length(groups = {CreateValidator.class,UpdateValidator.class},max = 50,message = "父级标识长度不超过50个字符")
	private String parentKey;

	@ApiModelProperty(value = "权限名称")
	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "资源名称不能为空")
	@Length(groups = {CreateValidator.class,UpdateValidator.class},max = 20,message = "资源名称的长度不超过20个字符")
	private String permName;

	@ApiModelProperty(value = "权限类型：0菜单，1按钮，2接口，3特殊)")
	@NotNull(groups = {CreateValidator.class,UpdateValidator.class},message = "权限类型不能为空")
	@Range(groups = {CreateValidator.class,UpdateValidator.class},min = 0,max = 3,message = "请输入正确的权限类型")
	private Integer permType;

	@ApiModelProperty(value = "是否是叶子节点：0否，1是")
	private Integer leafNode;

	@ApiModelProperty(value = "状态：0正常，1失效")
	@NotNull(groups = {CreateValidator.class,UpdateValidator.class},message = "资源的状态不能为空")
	private Integer permStatus;

	@ApiModelProperty(value = "当前操作人员id")
	private Long operatorId;
}
