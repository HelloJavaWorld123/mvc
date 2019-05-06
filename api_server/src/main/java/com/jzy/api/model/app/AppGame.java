package com.jzy.api.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;
/**
 * <b>功能：</b>游戏表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppGame extends GenericModel {

    /**
     * 父id
     */
    private String pId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型 0 游戏  1 大区   2服务器
     */
    private String type;

    /**
     * 状态 0 有区有服  1 有区没服  2 有服没区(一个默认大区)
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;



    /**
     * 备注
     */
    private String remark;


}
