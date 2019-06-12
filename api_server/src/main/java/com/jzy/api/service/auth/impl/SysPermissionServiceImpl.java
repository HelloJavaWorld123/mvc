package com.jzy.api.service.auth.impl;

import com.jzy.api.cnd.auth.SysPermissionCnd;
import com.jzy.api.constant.ApiRedisCacheConstant;
import com.jzy.api.dao.auth.SysPermissionMapper;
import com.jzy.api.model.auth.SysPermission;
import com.jzy.api.service.auth.SysPermissionService;
import com.jzy.api.util.DateUtils;
import com.jzy.api.vo.auth.SysPermissionVo;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author : RXK
 * Date : 2019/5/30 10:09
 * Version: V1.0.0
 * Desc:
 **/
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	@Resource
	private RedissonClient redissonClient;

	@Resource
	private SysPermissionMapper sysPermissionMapper;


	@Override
	public List<SysPermissionVo> list(SysPermissionCnd permissionCnd) {
		String key = ApiRedisCacheConstant.PERMISSION_LIST_CACHE;
		RBucket<List<SysPermissionVo>> permissionList = redissonClient.getBucket(key);
		List<SysPermissionVo> list;
		if(permissionList.isExists()){
			list = permissionList.get();
		} else{
			list = sysPermissionMapper.list(permissionCnd);
			permissionList.setAsync(list, DateUtils.SECONDES_ONE_WEEK, TimeUnit.SECONDS);
		}
		return list;
	}

	@Override
	public Integer add(SysPermission permission) {
		clearCache();
		return sysPermissionMapper.add(permission);
	}

	@Override
	public Integer update(SysPermission permission) {
		clearCache();
		return sysPermissionMapper.update(permission);
	}

	@Override
	public Integer delete(Long permissionId) {
		clearCache();
		return sysPermissionMapper.delete(permissionId);
	}

	@Override
	public SysPermission findById(Long id) {
		return sysPermissionMapper.findById(id);
	}

	@Override
	public List<SysPermission> findByKeys(List<String> permValues) {
		return sysPermissionMapper.findByUniqueKeys(permValues);
	}

	@Override
	public SysPermission findByUniqueKey(String uniqueKey) {
		return sysPermissionMapper.findByUniqueKey(uniqueKey);
	}

	@Override
	public List<SysPermission> findByIds(List<Long> permIds) {
		return sysPermissionMapper.findByIds(permIds);
	}

	private void clearCache() {
		String key = ApiRedisCacheConstant.PERMISSION_LIST_CACHE;
		RBucket<List<SysPermissionVo>> bucket = redissonClient.getBucket(key);
		bucket.deleteAsync();
	}
}
