package com.jzy.api.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * <b>功能：</b>shiro权限url校验<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class UrlPermissionsFilter extends PermissionsAuthorizationFilter {

    /**
     * <b>功能描述：</b>校验url是否有权限<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param o 例如：/test/** = roles[admin]  o的值就为admin
     */
    @Override
    public boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object o) throws RuntimeException {
        Subject subject = getSubject(req, resp);
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        if (subject.isPermitted(uri)) {
            return true;
        }
        return false;
    }
}
