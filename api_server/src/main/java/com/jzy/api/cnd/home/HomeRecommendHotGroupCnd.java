package com.jzy.api.cnd.home;

import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>首页推荐分组<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class HomeRecommendHotGroupCnd {

    /**
     * 主键id
     */
    private Long id;

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
     * 修改时间
     */
    protected String modifyTime;
}