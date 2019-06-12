package com.jzy.api.service.auth;

import com.jzy.api.model.sys.Emp;
import com.jzy.framework.service.GenericService;

import java.util.List;

/**
 * @author : RXK
 * 后期更改为 {@link : com.jzy.api.service.auth.SysEmpService}
 */
@Deprecated
public interface EmpService extends GenericService<Emp> {

    /**
     * <b>功能描述：</b>用户名称校验<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<Emp> checkNameList(String name,String id);

}
