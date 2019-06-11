package com.jzy.api.service.auth.impl;

import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.dao.auth.SysEmpRoleMapper;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.service.auth.SysEmpRoleService;
import com.jzy.api.util.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Author : RXK
 * Date : 2019/5/30 14:39
 * Version: V1.0.0
 * Desc:
 **/
@Service
public class SysEmpRoleServiceImpl implements SysEmpRoleService {

	@Resource
	private RedissonClient redissonClient;

	@Resource
	private SysEmpRoleMapper sysEmpRoleMapper;

	@Override
	public Integer add(SysEmpCnd sysEmpCnd) {
		sysEmpRoleMapper.deleteByEmpId(sysEmpCnd.getId());
		return sysEmpRoleMapper.batchInsert(sysEmpCnd.getId(), sysEmpCnd.getRoleList());
	}

	@Override
	public List<SysEmpRole> findByEmpId(Long empId) {
		return sysEmpRoleMapper.findByEmpId(empId);
	}

	@Override
	@Async(value = "excelExportAsyncExecutor")
	public void deleteByEmpId(Long id) {
		sysEmpRoleMapper.deleteByEmpId(id);
	}

	@Override
	public List<SysEmpRole> findByRoleId(Long id) {
		return sysEmpRoleMapper.findByRoleId(id);
	}

	@Override
	public List<Long> findRoleIdsByEmpId(Long id) {
		List<Long> roleIds = null;
		String key = ApiRedisCacheConstant.USER_ROLE_IDS_CACHE + id;
		RBucket<List<Long>> bucket = redissonClient.getBucket(key);
		if (bucket.isExists()) {
			roleIds = bucket.get();
		} else {
			List<SysEmpRole> roles = this.findByEmpId(id);
			if (CollectionUtils.isNotEmpty(roles)) {
				roleIds = roles.stream()
							   .filter(Objects::nonNull)
							   .map(SysEmpRole::getRoleId)
							   .collect(Collectors.toList());
				bucket.set(roleIds, DateUtils.SECONDS_PER_DAY, TimeUnit.SECONDS);
			}
		}

		return roleIds;
	}
}
