package com.jzy.api.aop;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * <b>功能：</b>CAT监控切面<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Aspect
@Component
public class CatTransactionAspect {

    private static final String CAT_TRANSACTION_TYPE_CONTROLLER = "Controller";
    private static final String CAT_TRANSACTION_TYPE_SERVICE = "Service";
    private static final String CAT_TRANSACTION_TYPE_DAO = "Dao";
    private static final String CAT_TRANSACTION_TYPE_RPCCLIENT = "RpcClient";
    private static final String CAT_TRANSACTION_TYPE_APICLIENT = "ApiClient";
    private static final String CAT_TRANSACTION_TYPE_HTTPCLIENT = "HttpClient";
    private static final String CAT_TRANSCATION_TYPE_REDISCLIENT = "RedisClient";


    private Object doMonitor(ProceedingJoinPoint proceedingJoinPoint, String transactionType) throws Throwable {
        String canonicalName = proceedingJoinPoint.getTarget().getClass().getCanonicalName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Transaction transaction = Cat.newTransaction(transactionType, canonicalName + "." + methodName);
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
            transaction.setStatus(Transaction.SUCCESS);
        } catch (Throwable throwable) {
            transaction.setStatus(throwable);
            throw throwable;
        } finally {
            if (transaction != null)
                transaction.complete();
        }
        return object;
    }

    @Around(value = "catControllerMonitorPointCut()")
    public Object doRestAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return doMonitor(proceedingJoinPoint, CAT_TRANSACTION_TYPE_CONTROLLER);
    }

    @Around(value = "catServiceMonitorPointCut()")
    public Object doServiceAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return doMonitor(proceedingJoinPoint, CAT_TRANSACTION_TYPE_SERVICE);
    }


    @Around(value = "catRedissionCommandClientMonitorPointCut()")
    public Object doRedissionCommandAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return doMonitor(proceedingJoinPoint, CAT_TRANSCATION_TYPE_REDISCLIENT);
    }

    @Pointcut("execution(* com.jzy.api.controller..*.*(..))")
    public void catControllerMonitorPointCut() {
    }

    @Pointcut("execution(* com.jzy.api.service..*.*(..))")
    public void catServiceMonitorPointCut() {
    }


    @Pointcut(value = "execution(* org.redisson.command.CommandExecutor.*(..))")
    public void catRedissionCommandClientMonitorPointCut() {
    }

}
