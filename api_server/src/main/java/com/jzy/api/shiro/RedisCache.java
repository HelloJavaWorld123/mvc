package com.jzy.api.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.Collection;
import java.util.Set;

/**
 * Author : RXK
 * Date : 2019/6/11 12:14
 * Version: V1.0.0
 * Desc:
 **/
public class RedisCache<K,V> implements Cache<K,V> {


	private RedissonClient redissonClient;

	private String cacheKey;

	public RedisCache(RedissonClient redissonClient,String cacheKey) {
		this.cacheKey = cacheKey;
		this.redissonClient = redissonClient;
	}

	@Override
	public V get(K k) throws CacheException {
		String key = getKey(k);
		RBucket<V> bucket = redissonClient.getBucket(key);
		return bucket.get();
	}

	@Override
	public V put(K k, V v) throws CacheException {
		RBucket<V> bucket = redissonClient.getBucket(getKey(k));
		bucket.setAsync(v);
		return v;
	}

	@Override
	public V remove(K k) throws CacheException {
		RBucket<V> bucket = redissonClient.getBucket(getKey(k));
		V v = bucket.get();
		bucket.deleteAsync();
		return v;
	}

	@Override
	public void clear() throws CacheException {

	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public Set<K> keys() {
		return null;
	}

	@Override
	public Collection<V> values() {
		return null;
	}

	public RedissonClient getRedissonClient() {
		return redissonClient;
	}

	public void setRedissonClient(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	private String getKey(K k) {
		if(k instanceof String){
			return (String) k;
		}else{
			return JSON.toJSONString(k);
		}
	}
}
