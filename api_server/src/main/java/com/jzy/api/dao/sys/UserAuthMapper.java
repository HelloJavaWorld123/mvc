package com.jzy.api.dao.sys;

import com.jzy.api.model.sys.UserAuth;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <b>功能：</b>前台用户信息保存<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190509&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface UserAuthMapper extends GenericMapper<UserAuth> {

    /**
     * <b>功能描述：</b>根据用户id获取用户授权<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    UserAuth queryUserAuthByUserId(@Param("userId") String userId,@Param("dealerId") Integer dealerId);

}
