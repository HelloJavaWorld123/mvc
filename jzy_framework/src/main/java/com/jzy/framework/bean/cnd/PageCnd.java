package com.jzy.framework.bean.cnd;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <b>功能：</b>分页参数基类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190424&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageCnd extends GenericCnd {

    public interface PageValidator{

    }

    /**
     * 第几页
     */
    @NotNull(groups = {PageValidator.class},message = "当前页不能为空")
    private Integer page;
    /**
     * 每页显示多少条
     */
    @NotNull(groups = {PageValidator.class},message = "当前页的条数不能为空")
    private Integer limit;

}
