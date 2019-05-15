package com.jzy.api.service.biz;

import com.jzy.api.model.biz.ExcelExport;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.service.GenericService;

/**
 * <b>功能：</b>Excel导出数据记录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface ExcelExportService extends GenericService<ExcelExport> {

    /**
     * <b>功能描述：</b>excel到处历史记录列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<ExcelExport> queryExcelExportList(PageCnd pageCnd);

}
