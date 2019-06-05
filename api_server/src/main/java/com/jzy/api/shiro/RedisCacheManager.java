package com.jzy.api.shiro;

import com.jzy.api.constant.ApiRedisCacheConstant;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * Author : RXK
 * Date : 2019/6/5 14:57
 * Version: V1.0.0
 * Desc:
 **/
public class RedisCacheManager implements CacheManager {

	@Override
	public <K, V> Cache<K, V> getCache(String s) throws CacheException {
		RedisCache<V> cache = new RedisCache<>(ApiRedisCacheConstant.ADMIN_SHIRO_CACHE + s);
		return (Cache<K, V>) cache;
	}
}
