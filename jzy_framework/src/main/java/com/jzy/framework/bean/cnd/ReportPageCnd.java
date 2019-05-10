package com.jzy.framework.bean.cnd;

import lombok.Data;

import java.sql.Timestamp;

/**
 * <b>功能：</b>分页查询报表数据<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class ReportPageCnd extends PageCnd {
    /**
     * 开始时间
     */
    private Timestamp startDate;
    /**
     * 结束时间
     */
    private Timestamp endDate;
}
