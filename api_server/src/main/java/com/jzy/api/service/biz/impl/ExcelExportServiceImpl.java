package com.jzy.api.service.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.dao.biz.ExcelExportMapper;
import com.jzy.api.model.biz.ExcelExport;
import com.jzy.api.service.biz.ExcelExportService;
import com.jzy.api.service.oss.AliyunOssService;
import com.jzy.api.util.ExcelBuilder;
import com.jzy.api.vo.biz.BackOrderExportListVo;
import com.jzy.api.vo.biz.BackOrderListVo;
import com.jzy.framework.bean.cnd.PageCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * <b>功能：</b>Excel导出数据记录<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Service
public class ExcelExportServiceImpl extends GenericServiceImpl<ExcelExport> implements ExcelExportService {

    @Resource
    private ExcelExportMapper excelExportMapper;

    @Resource
    private AliyunOssService aliyunOssService;

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
     * <b>功能描述：</b>根据md5查询excel导出<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public boolean isAlreadyExport(String md5) {
        return excelExportMapper.isAlreadyExport(md5);
    }

    /**
     * <b>功能描述：</b>导出<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void orderExport(List<BackOrderListVo> rowList, Long id) {

        String[] headers = {"订单编号", "第三方流水单号", "渠道商编号", "渠道商名称","充值帐号", "商品名称",
                "面值", "折扣", "支付金额", "实付金额","渠道商价格", "支付方式", "订单状态","sup状态",
                "充值时间", "渠道商利润"};
        String[] columnName = {"code", "outTradeNo", "dealerNum", "dealerName", "account", "appName",
                "price", "discount", "totalFee", "tradeFee", "dealerPrice", "payWayName", "status", "supStatus",
                "payTime", "merchantProfit"};

        List<BackOrderExportListVo> data = getData(rowList);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = null;
        try {
            new ExcelBuilder().exportExcel("order",  headers, columnName, data, out);
            byte[] fileByte = out.toByteArray();
            long length = fileByte.length;
            in = new ByteArrayInputStream(fileByte);
            String fileName = "order_" + System.currentTimeMillis();
            aliyunOssService.uploadFile(in, length, fileName, ".xls");
        } catch (Exception ignore) {

        } finally {
            try {
                out.close();
                if (in != null) in.close();
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
    }

    /**
     * <b>功能描述：</b>这里写功能描述<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private List<BackOrderExportListVo> getData(List<BackOrderListVo> rowList) {
        List<BackOrderExportListVo> data = new ArrayList<>();
        for (BackOrderListVo backOrderListVo: rowList) {
            BackOrderExportListVo backOrderExportListVo = new BackOrderExportListVo();
            backOrderExportListVo.setCode(backOrderListVo.getCode());
            backOrderExportListVo.setOutTradeNo(backOrderListVo.getOutTradeNo());
            backOrderExportListVo.setDealerNum(backOrderListVo.getDealerNum());
            backOrderExportListVo.setDealerName(backOrderListVo.getDealerName());
            backOrderExportListVo.setAccount(backOrderListVo.getAccount());
            backOrderExportListVo.setAppName(backOrderListVo.getAppName());
            backOrderExportListVo.setPrice(backOrderListVo.getPrice());
            backOrderExportListVo.setDiscount(backOrderListVo.getDiscount());
            backOrderExportListVo.setTotalFee(backOrderListVo.getTotalFee());
            backOrderExportListVo.setTradeFee(backOrderListVo.getTradeFee());
            backOrderExportListVo.setDealerPrice(backOrderListVo.getDealerPrice());
            backOrderExportListVo.setPayWayName(backOrderListVo.getTradeMethod());
            backOrderExportListVo.setStatus(backOrderListVo.getStatus());
            if (!StringUtils.isEmpty(backOrderListVo.getSupStatus())) {
                backOrderExportListVo.setSupStatus(Integer.parseInt(backOrderListVo.getSupStatus()));
            }
            backOrderExportListVo.setPayTime(backOrderListVo.getPayTime());
            backOrderExportListVo.setMerchantProfit(backOrderListVo.getMerchantProfit());
            data.add(backOrderExportListVo);
        }
        return data;
    }

    @Override
    protected GenericMapper<ExcelExport> getGenericMapper() {
        return excelExportMapper;
    }
}
