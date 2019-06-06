package com.jzy.api.cnd.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.IDValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.annotations.Delete;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/29 17:47
 * Version: V1.0.0
 * Desc:
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleCnd extends PageCnd {


	public interface Allot{}

	public interface RoleValueExist{}

	@NotNull(groups = {UpdateValidator.class, DeleteValidator.class, IDValidator.class,Allot.class},message = "主键不能为空")
	private Long id;

	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class},message = "角色名称不能为空")
	private String name;

	@NotEmpty(groups = {CreateValidator.class,UpdateValidator.class,RoleValueExist.class},message = "角色值不能为空")
	private String roleValue;

	private String description;

	private Integer status = 0;

	private Integer delFlag;

	private List<Long> permIds;

	@NotNull(groups = {Allot.class},message = "资源类型不能为空")
	@Range(groups = {Allot.class},min = 0,max = 3,message = "请输入正确范围内的数字")
	private Integer permType;

	private Date createTime;

	private Long createId;
}
