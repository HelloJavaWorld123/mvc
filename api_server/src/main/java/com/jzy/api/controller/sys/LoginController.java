package com.jzy.api.controller.sys;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.admin.LoginCnd;
import com.jzy.api.dao.auth.AuthMapper;
import com.jzy.api.model.auth.Role;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.service.auth.AuthService;
import com.jzy.api.service.sys.EmpService;
import com.jzy.api.vo.sys.EmpVo;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>后台管理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Controller
@RequestMapping(path="/login")
public class LoginController extends GenericController {

    @Resource
    private EmpService userService;

    @Resource
    private AuthService authService;

    /**
     * <b>功能描述：</b>获取用户资源列表<br>
     * <b>修订记录：</b><br>
     * <li>20190424&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @WithoutLogin
    @ResponseBody
    @RequestMapping(path="/managerLogin")
    public ApiResult managerLogin(@RequestBody LoginCnd loginCnd){
        ApiResult<EmpVo> apiResult = new ApiResult<>();
        Emp emp = userService.login(loginCnd.getUsername(), loginCnd.getPwd());
        EmpVo empVo = new EmpVo();
        empVo.setName(emp.getName());
        // TODO: 2019/4/28 目前只返回一个角色名即可
        List<Role> roleList = new ArrayList<>(emp.getRoles());
        if (!roleList.isEmpty()) {
            empVo.setRoleName(roleList.get(0).getName());
        }
        empVo.setApiEmpToken(emp.getApiEmpToken());
        return apiResult.success(empVo);
    }

}
