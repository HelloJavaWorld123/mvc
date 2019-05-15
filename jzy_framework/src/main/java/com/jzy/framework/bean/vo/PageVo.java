package com.jzy.framework.bean.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>基础父类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class PageVo<T> extends GenericVo {

    /**
     * 第几页
     */
    private Integer page;
    /**
     * 每页显示多少条
     */
    private Integer limit;
    /**
     * 总条数
     */
    private Long totalCount;
    /**
     * 分页返回列表数据
     */
    private List<T> rows = new ArrayList<>();

    public  PageVo(List<T> rows) {
        this.rows = rows;
    }

    public PageVo() {
    }

    public PageVo(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    public PageVo(Integer page, Integer limit, Long totalCount) {
        this.page = page;
        this.limit = limit;
        this.totalCount = totalCount;
    }

    public PageVo(Integer page, Integer limit, Long totalCount, List<T> rows) {
        this.page = page;
        this.limit = limit;
        this.totalCount = totalCount;
        this.rows = rows;
    }
}
