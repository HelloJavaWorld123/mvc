package com.jzy.api.base;

import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.exception.ExcelException;
import com.jzy.framework.exception.PayException;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <b>功能：</b>统一异常处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
public class ProjectHandlerExceptionResolver implements HandlerExceptionResolver {

    @Resource
    private JsonConverter jsonConverter;

//    @Resource
//    private MessageSource messageSource;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        if (e instanceof BusException) {
            log.error("业务异常", e);
            if (ResultEnum.SESSION_VALID.getMsg().equals(e.getMessage())) {
                ajaxJson(new ApiResult().fail(ResultEnum.SESSION_VALID), response);
                return null;
            }
            if(((BusException) e).getCode()==null) {
                ajaxJson(new ApiResult().fail(e.getMessage()), response);
            }else {
                ajaxJson(new ApiResult().fail(e.getMessage(), ((BusException) e).getCode()), response);
            }
            return null;
        }
        if (e instanceof PayException) {
            log.error("支付异常", e);
            ajaxJson(new ApiResult().fail(e.getMessage()), response);
            return null;
        }
        if (e instanceof ExcelException) {
            log.error("Excel导出异常", e);
            ajaxJson(new ApiResult().fail(e.getMessage()), response);
            return null;
        }

        if(e instanceof IllegalArgumentException){
            ajaxJson(new ApiResult().fail(e.getMessage()), response);
            return null;
        }

        log.error("系统错误或繁忙，或者网络错误。请稍后再试", e);
       // ajaxJson(new ApiResult<>().fail(e.getMessage()), response);
        ajaxJson(new ApiResult<>().fail("系统错误或繁忙，或者网络错误。请稍后再试"), response);
        return null;
    }

    /**
     * <b>功能描述：</b>ajax输出json<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void ajaxJson(Object jsonObject, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            jsonConverter.toJson(response.getWriter(), jsonObject);
            if (log.isErrorEnabled()) {
                log.error("返回内容给客户端：{}", jsonConverter.toJson(jsonObject));
            }
        } catch (IOException e) {
            //不处理
        }
    }

}
