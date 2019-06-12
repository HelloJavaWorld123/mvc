package com.jzy.api.service.auth.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.dao.auth.SysEmpMapper;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.service.auth.SysEmpService;
import com.jzy.api.vo.auth.SysEmpVo;
import com.jzy.framework.bean.vo.PageVo;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author : RXK
 * Date : 2019/5/30 12:11
 * Version: V1.0.0
 * Desc: 后台用户相关管理
 **/
@Service
public class SysEmpServiceImpl implements SysEmpService {

	@Resource
	private SysEmpMapper sysEmpMapper;

	@Resource
	private RedissonClient redissonClient;

	@Override
	public PageVo<SysEmpVo> list(SysEmpCnd sysEmpCnd) {
		Page<SysEmp> page = PageHelper.startPage(sysEmpCnd.getPage(), sysEmpCnd.getLimit());
		List<SysEmpVo> result = sysEmpMapper.list(sysEmpCnd);
		return new PageVo<>(sysEmpCnd.getPage(), sysEmpCnd.getLimit(), page.getTotal(), result);
	}

	@Override
	public Integer add(SysEmp sysEmp) throws DuplicateKeyException {
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
		deleteCache(sysEmpCnd.getId());
		return sysEmpMapper.update(sysEmp);
	}


	@Override
	public Integer deleteById(Long id, Long operatorId) {
		deleteCache(id);
		return sysEmpMapper.deleteById(id,operatorId);
	}


	@Override
	public List<SysEmp> findByName(String name, Long userId) {
		return sysEmpMapper.findByName(name,userId);
	}

	@Override
	public void deleteCache(Long id) {
		String userRoleIdsKey = ApiRedisCacheConstant.USER_ROLE_IDS_CACHE+id;
		String userPermKey = ApiRedisCacheConstant.USER_PERMISSION_CACHE +id;
		String userRoleKey = ApiRedisCacheConstant.USER_ROLE_CACHE+id;
		redissonClient.getBucket(userRoleIdsKey).deleteAsync();
		redissonClient.getBucket(userPermKey)
					  .deleteAsync();
		redissonClient.getBucket(userRoleKey)
					  .deleteAsync();
	}

	@Override
	public List<Emp> findNameByDealerId(String name, Long dealerId) {
		return sysEmpMapper.findNameByDealerId(name,dealerId);
	}
}
