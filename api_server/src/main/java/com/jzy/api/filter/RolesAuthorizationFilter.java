package com.jzy.api.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RolesAuthorizationFilter extends AuthorizationFilter {

    /**
     * <b>功能描述：</b>校验角色<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param o 例如：/test/** = roles[admin]  o的值就为admin
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object o) {
        Subject subject = getSubject(req, resp);
        String[] rolesArray = (String[]) o;

        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        for (int i = 0; i < rolesArray.length; i++) {
            if (subject.hasRole(rolesArray[i])) {
                return true;
            }
        }
        return false;
    }
}
