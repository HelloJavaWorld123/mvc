/*
package com.jzy.api.service.auth.impl;


import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

*/
/**
 * <b>功能：</b>主键序列实现类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 *//*

@Service
public class SysTableKeyServiceImpl {

    @Resource
    private RedissonClient redissonClient;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    */
/*
     定义门槛阈值，当子增加值距  thresholdValue 取余小于阈值 1/10时候
     数据库保证 自增值天花板值 + thresholdValue 更新
     *//*

    private static final Long thresholdValue = 100L;

*/
/**
 * <b>功能：</b>获取主键<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 *//*

    public long newkey(String tableName, String columnName, int centerId) {

        final String lockStr = "PK:".concat(tableName).concat(columnName);
        RLock lock = null;// 店铺登录加上同步锁，防止并发请求数据异常
        try {
            lock = redissonClient.getLock(lockStr);
            lock.lock(10, TimeUnit.SECONDS);
            return getAutoSequenceCode(centerId, tableName, columnName);
        } finally {


            if (lock != null) {
                try {
                    lock.unlock();
                } catch (Exception ignored) {
                }
            }
        }
    }

*/
/**
 * <b>功能：</b>获取新主键- 从redis中自增<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 *//*

    public Long getAutoSequenceCode(Integer centerId, String tableName, String columnName) {

        Long result = this.increasing(tableName, columnName, centerId);

        return result;
    }

    */
/**
     * <b>功能：</b>获取新主键- 从redis中自增<br>
     * <b>Copyright JZY</b>
     * <ul>
     * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
     * </ul>
     *//*

    private Long increasing(String tableName, String columnName, Integer centerId) {
        Long curVal = 1L;
        final String tableKey = "SysPK:".concat(tableName).concat(columnName);
        RAtomicLong pkIndex = redissonClient.getAtomicLong(tableKey);

        if (pkIndex.isExists()) {
            curVal = pkIndex.get();
            curVal++;
        }
        pkIndex.set(curVal);
        return curVal;
    }

}
*/
