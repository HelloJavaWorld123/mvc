package com.jzy.api.controller.biz;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.biz.BackOrderCnd;
import com.jzy.api.model.biz.ExcelExport;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.ExcelExportService;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MD5Util;
import com.jzy.api.vo.biz.BackOrderListVo;
import com.jzy.api.vo.biz.ExcelExportVo;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <b>功能：</b>Excel到处<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190514&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(path = "/excelExport")
public class ExcelExportController extends GenericController {

    @Resource
    private OrderService orderService;

    @Resource
    private ExcelExportService excelExportService;

    @Resource
    private TableKeyService tableKeyService;

    /**
     * <b>功能描述：</b>订单列表接口导出<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping(path = "/orderListExcelExport")
    public ApiResult orderListExcelExport(@RequestBody BackOrderCnd backOrderCnd) {
        List<Order> orderList = orderService.queryExcelExportBackOrderList(backOrderCnd);
        if (orderList == null || orderList.isEmpty()) {
            return new ApiResult().fail("导出订单列表为空");
        }
        List<BackOrderListVo> rowList = convert(orderList, BackOrderListVo.class);
        ExcelExport excelExport = new ExcelExport();
        excelExport.setId(tableKeyService.newKey("order_export_record", "id", 10000));
        excelExport.setStartDate(backOrderCnd.getStartDate());
        excelExport.setEndDate(backOrderCnd.getEndDate());
        excelExport.setDealerId(getDealerId());
        excelExportService.insert(excelExport);
        excelExportService.orderExport(rowList, excelExport.getId());
        return new ApiResult().success();
    }

    /**
     * <b>功能描述：</b>Excel导出历史记录列表接口查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryExcelExportList")
    public ApiResult queryExcelExportList(@RequestBody PageCnd pageCnd) {
        PageVo<ExcelExport> exportPageVo = excelExportService.queryExcelExportList(pageCnd);
        PageVo<ExcelExportVo> pageVo = new PageVo<>(exportPageVo.getPage(), exportPageVo.getLimit());
        if (exportPageVo.getRows() == null || exportPageVo.getRows().isEmpty()) {
            return new ApiResult<>().success(pageVo);
        }
        List<ExcelExportVo> excelExportVoList = convert(exportPageVo.getRows(), ExcelExportVo.class);
        pageVo.setRows(excelExportVoList);
        return new ApiResult<>().success(pageVo);
    }

}
