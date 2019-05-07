package com.jzy.api.aop;

import com.jzy.framework.exception.BusException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <b>功能：</b>参数校验aop<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Aspect
@Component
public class ParamValidateAspect {

    @Resource
    protected Validator validator;

    /**
     * <b>功能描述：</b>定义切面<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Pointcut("execution(* com.jzy.api.controller..*.*(..))")
    public void controller() { }

    /**
     * <b>功能描述：</b>请求参数校验<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Before("controller()")
    public void controller(JoinPoint point) {
//        MethodSignature signature= (MethodSignature) point.getSignature();
//        // 获取方法参数上的注解
//        signature.getMethod().getParameterAnnotations();
        Object[] obj = point.getArgs();
        if (obj == null || obj.length == 0) {
            return;
        }
        if (obj[0] instanceof HttpServletRequest || obj[0] instanceof HttpServletResponse) {
            return;
        }
        Set<ConstraintViolation<Object>> validate = validator.validate(obj[0]);
        if (!validate.isEmpty()) {
            //验证未通过, 取第一个抛出异常
            ConstraintViolation v = validate.iterator().next();
            //封装异常信息, 类名.属性名: 信息, 例如:[DbiShop.code]: 个数必须在0和5之间
            String msg = String.format("[%s.%s]: %s", v.getRootBeanClass().getSimpleName(),
                    v.getPropertyPath().toString(), v.getMessage());
            if(!v.getMessage().contains("null") && !v.getMessage().equalsIgnoreCase("不能为空")){
                throw new BusException(v.getMessage());
            }
            throw new BusException("请求参数错误" + (v.getMessage().contains("null") || v.getMessage().equalsIgnoreCase("不能为空") ? msg : v.getMessage()));
        }
    }

}
