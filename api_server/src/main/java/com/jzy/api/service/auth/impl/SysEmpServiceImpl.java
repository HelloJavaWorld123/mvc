package com.jzy.api.service.auth.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.dao.auth.SysEmpMapper;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.vo.auth.SysEmpVo;
import com.jzy.framework.bean.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Author : RXK
 * Date : 2019/5/30 12:11
 * Version: V1.0.0
 * Desc: 后台用户相关管理
 **/
@Service
public class SysEmpServiceImpl implements SysEmpService {

	@Resource
	private SysEmpMapper sysEmpMapper;

	@Override
	public PageVo<SysEmpVo> list(SysEmpCnd sysEmpCnd) {
		Page<SysEmp> page = PageHelper.startPage(sysEmpCnd.getPage(), sysEmpCnd.getLimit());
		List<SysEmpVo> result = sysEmpMapper.list(sysEmpCnd);
		return new PageVo<>(sysEmpCnd.getPage(), sysEmpCnd.getLimit(), page.getTotal(), result);
	}

	@Override
	public Integer add(SysEmpCnd sysEmpCnd) {
		SysEmp sysEmp = SysEmp.build(sysEmpCnd);
		return sysEmpMapper.add(sysEmp);
	}

	@Override
	public SysEmpVo findById(Long id) {
		SysEmp sysEmp = sysEmpMapper.findById(id);
		SysEmpVo sysEmpVo = null;
		if (Objects.nonNull(sysEmp)) {
			sysEmpVo = new SysEmpVo();
			BeanUtils.copyProperties(sysEmp, sysEmpVo);
			return sysEmpVo;
		}
		return sysEmpVo;
	}

	@Override
	public Integer update(SysEmpCnd sysEmpCnd) {
		SysEmp sysEmp = SysEmp.update(sysEmpCnd);
		return sysEmpMapper.update(sysEmp);
	}

	@Override
	public Integer deleteById(Long id) {
		return sysEmpMapper.deleteById(id);
	}

	@Override
	public SysEmp findByName(String name) {
		return sysEmpMapper.findByName(name);
	}
}
