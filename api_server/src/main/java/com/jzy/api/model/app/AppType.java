package com.jzy.api.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>应用类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppType extends GenericModel {


    /**
     * 应用分类名称
     */
    private String name;

    /**
     * 序列:值越小越靠前,默认1
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;



}
