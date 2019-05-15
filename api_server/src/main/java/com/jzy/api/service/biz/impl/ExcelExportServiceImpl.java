package com.jzy.api.service.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.dao.biz.ExcelExportMapper;
import com.jzy.api.model.biz.ExcelExport;
import com.jzy.api.service.biz.ExcelExportService;
import com.jzy.api.util.ExcelUtil;
import com.jzy.api.vo.biz.BackOrderListVo;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.cnd.ReportPageCnd;
import com.jzy.framework.bean.model.GenericModel;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * <b>功能描述：</b>导出<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void export(List<BackOrderListVo> rowList) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", "订单编号");
        map.put("outTradeNo", "第三方流水单号");
        map.put("dealerNum", "渠道商编号");
        map.put("dealerName", "渠道商名称");
        map.put("account", "充值帐号");
        map.put("appName", "商品名称");
        map.put("price", "面值");
        map.put("discount", "折扣");
        map.put("totalFee", "支付金额");
        map.put("tradeFee", "实付金额");
        map.put("dealerPrice", "渠道商价格");
        map.put("payWayName", "支付方式");
        map.put("status", "支付金额");
        map.put("supStatus", "实付金额");
        map.put("payTime", "渠道商利润");
        map.put("merchantProfit", "渠道商利润");
        // ExcelUtil.excelToList()
    }

    @Override
    protected GenericMapper<ExcelExport> getGenericMapper() {
        return excelExportMapper;
    }
}
