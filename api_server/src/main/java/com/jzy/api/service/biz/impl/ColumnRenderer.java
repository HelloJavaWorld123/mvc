package com.jzy.api.service.biz.impl;

public abstract class ColumnRenderer {

    private String col;

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public ColumnRenderer(String col) {
        super();
        this.col = col;
    }

    /**
     * <b>功能描述：</b>返回解析后的值<br>
     * <b>修订记录：</b><br>
     * <li>20131120&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     * @param data 当前导出行的数据
     */
    public abstract Object render(Object data);
}
