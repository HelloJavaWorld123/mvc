package com.jzy.api.service.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.dao.biz.ExcelExportMapper;
import com.jzy.api.model.biz.ExcelExport;
import com.jzy.api.service.biz.ExcelExportService;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.cnd.ReportPageCnd;
import com.jzy.framework.bean.model.GenericModel;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：</b>Excel导出数据记录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class ExcelExportServiceImpl extends GenericServiceImpl<ExcelExport> implements ExcelExportService {

    @Resource
    private ExcelExportMapper excelExportMapper;

    /**
     * <b>功能描述：</b>excel到处历史记录列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo<ExcelExport> queryExcelExportList(PageCnd pageCnd) {
        Page page = PageHelper.startPage(pageCnd.getPage(), pageCnd.getLimit(), false);
        List<ExcelExport> excelExportList = excelExportMapper.queryExcelExportList(Integer.parseInt(getDealerId()));
        PageVo<ExcelExport> pageVo = new PageVo<>(page.getPageNum(), page.getPageSize());
        pageVo.setRows(excelExportList);
        return pageVo;
    }

    @Override
    protected GenericMapper<ExcelExport> getGenericMapper() {
        return excelExportMapper;
    }
}
