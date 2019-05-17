package com.jzy.api.vo.app;

import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;

/**
 * <b>功能：</b>应用账号类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190514&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppAccttypeVo {

    /**
     * 主键
     */
    private String id;

    /**
     * 主账号名称
     */
    private String mainName;

    /**
     * 主账号正则
     */
    private String mainRegular;

    /**
     * 主账号错误提示信息
     */
    private String mainMsg;

    /**
     * 副账号名称
     */
    private String subName;

    /**
     * 副账号正则
     */
    private String subRegular;

    /**
     * 副账号错误提示信息
     */
    private String subMsg;


}
