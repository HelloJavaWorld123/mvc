package com.jzy.api.service.key.impl;


import com.jzy.api.service.key.TableKeyService;
import lombok.extern.slf4j.Slf4j;
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


/**
 * <b>功能：</b>主键序列实现类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Service
public class TableKeyServiceImpl implements TableKeyService {

    @Resource
    private RedissonClient redissonClient;

    private static final Long thresholdValue = 100L;

    /**
     * <b>功能描述：</b>获取主键<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public Long newKey(String tableName, String columnName, int centerId) {
        return getAutoSequenceCode(centerId, tableName, columnName);
    }

    /**
     * <b>功能描述：</b>获取新主键- 从redis中自增<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public Long getAutoSequenceCode(Integer centerId, String tableName, String columnName) {

        Long result = this.increasing(tableName, columnName, centerId);

        return result;
    }

    /**
     * <b>功能描述：</b>获取新主键- 从redis中自增<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Long increasing(String tableName, String columnName, Integer centerId) {
        Long curVal = 10000L;
        final String tableKey = "PK_Sys:".concat(tableName).concat("_").concat(columnName);
        RAtomicLong pkIndex = redissonClient.getAtomicLong(tableKey);
        if (pkIndex.isExists()) {
            curVal = pkIndex.incrementAndGet();
        }
        return curVal;
    }

}

