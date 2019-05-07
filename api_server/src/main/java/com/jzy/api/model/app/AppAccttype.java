package com.jzy.api.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>应用账号类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppAccttype extends GenericModel {


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

    /**
     * 描述信息
     */
    private String description;
    /**
     * 备注
     */
    private String remark;

}
