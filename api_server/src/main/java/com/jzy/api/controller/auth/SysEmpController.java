package com.jzy.api.controller.auth;

import com.jzy.api.annos.CreateValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.IDValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.service.auth.SysEmpRoleService;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.service.auth.SysRoleService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.util.MD5Util;
import com.jzy.api.vo.auth.SysEmpVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.result.ApiResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Author : RXK
 * Date : 2019/5/30 12:01
 * Version: V1.0.0
 * Desc: 系统用户相关接口
 **/
@RestController
@RequestMapping("/sys/user")
public class SysEmpController {

	@Autowired
	private SysEmpService sysEmpService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private TableKeyService tableKeyService;

	@Autowired
	private SysEmpRoleService sysEmpRoleService;


	@RequestMapping("/list")
	public ApiResult list(@RequestBody @Validated(value = {PageCnd.PageValidator.class}) SysEmpCnd sysEmpCnd) {
		PageVo<SysEmpVo> result = sysEmpService.list(sysEmpCnd);
		return new ApiResult<>(result);
	}

	@RequestMapping("/add")
	public ApiResult add(@RequestBody @Validated(value = {CreateValidator.class}) SysEmpCnd sysEmpCnd) {
		Integer result = 0;
		try {
			if (Objects.isNull(sysEmpCnd.getId())) {
				sysEmpCnd.setId(tableKeyService.newKey("sys_emp", "id", 0));
				encryptPassword(sysEmpCnd);

				List<SysEmp> byName = sysEmpService.findByName(sysEmpCnd.getName(), sysEmpCnd.getId());
				if (CollectionUtils.isNotEmpty(byName)) {
					return new ApiResult().fail(ResultEnum.USER_NAME_ALREADY_EXIST.getMsg(), ResultEnum.FAIL.getCode());
				}
				sysEmpCnd.setOperatorId(getOperatorId());
			}


			SysEmp sysEmp = SysEmp.build(sysEmpCnd);
			result = sysEmpService.add(sysEmp);
		} catch (DuplicateKeyException e) {
			sysEmpCnd.setId(tableKeyService.newKey("sys_emp", "id", 0));
			add(sysEmpCnd);
		}
		return returnResult(result);
	}

	private Long getOperatorId() {
		Long operatorId;
		SysEmp sysEmp = (SysEmp) SecurityUtils.getSubject()
											  .getPrincipal();
		operatorId = sysEmp.getId();
		return operatorId;
	}


	@RequestMapping("/update")
	public ApiResult update(@RequestBody @Validated(value = {UpdateValidator.class}) SysEmpCnd sysEmpCnd) {
		SysEmpVo sysEmp = sysEmpService.findById(sysEmpCnd.getId());
		if (Objects.isNull(sysEmp)) {
			return new ApiResult().fail(ResultEnum.FAIL);
		}
		encryptPassword(sysEmpCnd);
		sysEmpCnd.setOperatorId(getOperatorId());
		Integer result = sysEmpService.update(sysEmpCnd);
		return returnResult(result);
	}

	@RequestMapping("/delete")
	public ApiResult delete(@RequestBody @Validated(value = {DeleteValidator.class}) SysEmpCnd sysEmpCnd) {
		SysEmpVo emp = sysEmpService.findById(sysEmpCnd.getId());
		if (Objects.isNull(emp)) {
			return new ApiResult().fail(ResultEnum.FAIL);
		}

		Integer result = sysEmpService.deleteById(sysEmpCnd.getId(), getOperatorId());
		if (result == 1) {
			sysEmpRoleService.deleteByEmpId(sysEmpCnd.getId());
		}
		return returnResult(result);
	}

	@RequestMapping("/id")
	public ApiResult getById(@RequestBody @Validated(value = {IDValidator.class}) SysEmpCnd sysEmpCnd) {
		SysEmpVo vo = sysEmpService.findById(sysEmpCnd.getId());
		return new ApiResult<SysEmpVo>().success(vo);
	}

	/**
	 * 检查用户名是否已经存在
	 *
	 * @param sysEmpCnd ：用户名称
	 */
	@RequestMapping("/check")
	public ApiResult userNameExist(@RequestBody @Validated(SysEmpCnd.NameExistValidator.class) SysEmpCnd sysEmpCnd) {
		List<SysEmp> sysEmps = sysEmpService.findByName(sysEmpCnd.getName(), sysEmpCnd.getId());
		Assert.isTrue(Objects.isNull(sysEmps), "用户名已经存在");
		return new ApiResult().success();
	}

	/**
	 * 为用户分配角色
	 */
	@RequestMapping("/allot/role")
	public ApiResult userAddRole(@RequestBody @Validated(value = SysEmpCnd.AllotValidator.class) SysEmpCnd sysEmpCnd) {
		List<Role> roleList = sysRoleService.findByIds(sysEmpCnd.getRoleList());

		Assert.isTrue(CollectionUtils.isNotEmpty(roleList) && roleList.size() == sysEmpCnd.getRoleList()
																						  .size(), "角色信息有误");

		SysEmpVo sysEmpVo = sysEmpService.findById(sysEmpCnd.getId());
		Assert.isTrue(Objects.nonNull(sysEmpVo), "用户信息有误");

		Integer result = sysEmpRoleService.add(sysEmpCnd);
		return result >= 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);
	}


	private ApiResult returnResult(Integer result) {
		return result == 1 ? new ApiResult<ResultEnum>().success(ResultEnum.SUCCESS) : new ApiResult<ResultEnum>().fail(ResultEnum.FAIL);
	}

	private void encryptPassword(SysEmpCnd sysEmpCnd) {
		sysEmpCnd.setPassword(MD5Util.string2MD5(sysEmpCnd.getPassword()));
	}

}
