package com.jzy.api.controller.auth;

import com.jzy.api.annos.AddValidator;
import com.jzy.api.annos.DeleteValidator;
import com.jzy.api.annos.IDValidator;
import com.jzy.api.annos.UpdateValidator;
import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.model.auth.Role;
import com.jzy.api.service.auth.SysEmpRoleService;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.service.auth.SysRoleService;
import com.jzy.api.vo.auth.SysEmpVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.result.ApiResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	private SysEmpRoleService sysEmpRoleService;



	@RequestMapping("/list")
	public ApiResult list(@RequestBody @Validated(value = {PageCnd.PageValidator.class})SysEmpCnd sysEmpCnd){
		PageVo<SysEmpVo> result = sysEmpService.list(sysEmpCnd);
		return new ApiResult<>(result);
	}

	@RequestMapping("/add")
	public ApiResult add(@RequestBody @Validated(value = {AddValidator.class})SysEmpCnd sysEmpCnd){
		Integer result = sysEmpService.add(sysEmpCnd);
		return result == 1 ? new ApiResult<>(ResultEnum.SUCCESS) : new ApiResult<>(ResultEnum.FAIL);
	}


	@RequestMapping("/update")
	public ApiResult update(@RequestBody @Validated(value = {UpdateValidator.class})SysEmpCnd sysEmpCnd){
		SysEmpVo sysEmp = sysEmpService.findById(sysEmpCnd.getId());
		if (Objects.isNull(sysEmp)) {
			return new ApiResult().fail(ResultEnum.FAIL);
		}
		Integer result = sysEmpService.update(sysEmpCnd);
		return result == 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);
	}

	@RequestMapping("/delete")
	public ApiResult delete(@RequestBody @Validated(value = {DeleteValidator.class})SysEmpCnd sysEmpCnd){
		SysEmpVo emp = sysEmpService.findById(sysEmpCnd.getId());
		if (Objects.isNull(emp)) {
			return new ApiResult().fail(ResultEnum.FAIL);
		}

		Integer result = sysEmpService.deleteById(sysEmpCnd.getId());
		return result == 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);

	}

	@RequestMapping("/id")
	public ApiResult getById(@RequestBody @Validated(value = {IDValidator.class})SysEmpCnd sysEmpCnd){
		SysEmpVo vo = sysEmpService.findById(sysEmpCnd.getId());
		return new ApiResult<SysEmpVo>().success(vo);
	}

	/**
	 * 为用户分配角色
	 */
	@RequestMapping("/allot/role")
	public ApiResult userAddRole(@RequestBody @Validated(value = SysEmpCnd.Allot.class) SysEmpCnd sysEmpCnd){
		List<Role> roleList = sysRoleService.findByIds(sysEmpCnd.getRoleList());
		if(CollectionUtils.isEmpty(roleList) && roleList.size() != sysEmpCnd.getRoleList().size()){
			return new ApiResult().fail("角色信息有误",ResultEnum.FAIL.getCode());
		}
		SysEmpVo sysEmpVo = sysEmpService.findById(sysEmpCnd.getId());
		if(Objects.isNull(sysEmpVo)){
			return new ApiResult().fail("用户信息有误",ResultEnum.FAIL.getCode());
		}
		Integer result = sysEmpRoleService.add(sysEmpCnd);
		return result >= 1 ? new ApiResult<>().success(ResultEnum.SUCCESS) : new ApiResult().fail(ResultEnum.FAIL);
	}

}
