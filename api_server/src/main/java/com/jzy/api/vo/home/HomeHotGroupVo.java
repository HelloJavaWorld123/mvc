package com.jzy.api.vo.home;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>首页推荐分组列表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HomeHotGroupVo {

    /**
     * 主键
     */
    private String id;
    /**
     * 商户信息id
     */
    private String dealerId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 排序
     */
    private String groupSort;

    /**
     * 状态1禁用，2启用
     */
    private String state;

    /**
     * 最后修改时间
     */
    private Date modifyTime;
}
