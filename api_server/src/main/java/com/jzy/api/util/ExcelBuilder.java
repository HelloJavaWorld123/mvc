package com.jzy.api.util;

import com.jzy.api.service.biz.impl.ColumnRenderer;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * <b>功能：</b>Excel导入<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
public class ExcelBuilder {

    /**
     * 如果导出行数超过这个值, 会分为多个sheet导出 . excel2003最大行数为65536
     */
    private int maxSheetRows = 65535;

    /**
     * 保存每列的render, key是列名
     */
    private Map<String, ColumnRenderer> columnRenderers = new HashMap<>();

    /**
     * <b>功能描述：</b>ListMap数据转换为excel<br>
     * <b>修订记录：</b><br>
     * <li>20131120&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li> <br>
     * <br>
     * @param sheetName sheet页的名称
     * @param headers 表格标题栏
     * @param columnNames 需要导出的列字段
     * @param datas 表格的数据源, 可以是map, 也可以是bean.
     * @param out 导出文件写入的流
     * @throws IOException
     */
    public void exportExcel(String sheetName, String[] headers, String[] columnNames, List<? extends Object> datas, OutputStream out) throws IOException {

        // 声明一个工作薄
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            int rowNumber = maxSheetRows;//行数太多分多个sheet导出
            if (datas.size() <= rowNumber) {
                rowNumber = datas.size();
            }
            // 初始化工作表的数量
            int sheetNum = getSheetNum(datas.size(), rowNumber);

            for (int x = 1; x < sheetNum + 1; x++) { //循环工作表数量
                // 生成一个表格
                HSSFSheet sheet = workbook.createSheet(sheetName + x);
                // 设置表格默认列宽度为15个字节
                sheet.setDefaultColumnWidth(15);
                // 产生表格标题行
                HSSFRow row = sheet.createRow(0);
                // 插入第一行文字标题
                for (int i = 0; i < headers.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(getStyle(workbook));
                    cell.setCellValue(new HSSFRichTextString(headers[i]));
                }


                // 执行每行插入数据的方法
                //if(datas != null && datas.size() > 0){
                addRowData(rowNumber, row, sheet, datas, 0, columnNames,
                        new HSSFCellStyle[]{getDataFormat(workbook, "yyyy-MM-dd"), getDataFormat(workbook,
                                "yyyy-MM-dd HH:mm:ss")});
                //}
                //调整列宽
                setColumnWidth(sheet, columnNames.length);
            }
            workbook.write(out);
        }
    }

    /**
     * <b>功能描述：</b>设置单元格样式<br>
     * <b>修订记录：</b><br>
     * <li>20170524&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        //设置样式;
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //设置底边框;
        cellStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index); //设置颜色为灰色
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    /**
     * <b>功能描述：</b>调整工作表的列宽<br>
     * <b>修订记录：</b><br>
     * <li>20170524&nbsp;&nbsp;|&nbsp;&nbsp;lhc&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public void setColumnWidth(HSSFSheet sheet, int columnNameSize) {
        for (int i = 0; i < columnNameSize; i++) {
            //先自动调整一下, 但是对中文效果不佳
            sheet.autoSizeColumn(i);
            //将宽度整体增加1.2, 最小为2000(2000大约3个汉字)
            int width = sheet.getColumnWidth(i);
            width = (int) (width * 1.2);
            if (width < 2000) {
                width = 2000;
            }
            //poi限制最大列宽
            if (width >= 255 * 256) {
                width = 255 * 256;
            }
            sheet.setColumnWidth(i, width);
        }
    }

    /**
     * <b>功能描述：</b>设置日期格式<br>
     * <b>修订记录：</b><br>
     * <li>20170524&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public HSSFCellStyle getDataFormat(HSSFWorkbook workbook, String dateFormat) {
        //日期格式
        HSSFDataFormat hssfDataFormat = workbook.createDataFormat();
        HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(hssfDataFormat.getFormat(dateFormat));
        return dateCellStyle;
    }

    /**
     * <b>功能描述：</b>执行每行插入数据的方法<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public void addRowData(int rowNumber, HSSFRow row, HSSFSheet sheet, List<? extends Object> dataSet,
                           int dataNum, String[] columnName, HSSFCellStyle[] dateCellStyles) {
        int index = 0;// 索引下标，用来创建行对象
        for (int i2 = 0; i2 < rowNumber; i2++) {//循环插入每行的数据
            index++;
            row = sheet.createRow(index);
            Object data = dataSet.get(dataNum);
            addColumnData(columnName, row, data, dateCellStyles, null);
            dataNum++;
        }
    }

    /**
     * <b>功能描述：</b>执行每列插入数据的方法<br>
     * <b>修订记录：</b><br>
     * <li>20130624&nbsp;&nbsp;|&nbsp;&nbsp;扈健成&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     * <li>20140311&nbsp;&nbsp;|&nbsp;&nbsp;扈健成&nbsp;&nbsp;|&nbsp;&nbsp;布尔类型导出对钩和空白</li>
     * <br>
     *
     * @param columnNames    需要导出的列字段名称
     * @param row            Excel行对象
     * @param data           数据对象, 可以是map或者bean
     * @param dateCellStyles 日期格式, 第一个是年月日格式, 第二个是年月日时分秒格式
     */
    private void addColumnData(String[] columnNames, HSSFRow row, Object data, HSSFCellStyle[] dateCellStyles, CellStyle setBorder) {
        HSSFCell cell = null;
        ColumnRenderer columnRenderer = null;
        for (int j = 0; j < columnNames.length; j++) {
            String columnName = columnNames[j];
            // 获取列解析器
            columnRenderer = columnRenderers.get(columnName);
            Object value;//要导出的值
            if (columnRenderer == null) {
                if (data instanceof Map) {
                    value = ((Map<String, Object>) data).get(columnName);
                } else {
                    //bean类型 反射获取属性
                    value = ReflectUtil.getFieldValueByGetter(data, columnName);
                }
            } else {
                value = columnRenderer.render(data);
            }
            //生成单元格，给每个单元格进行赋值
            cell = row.createCell(j);
            if (setBorder != null) {
                cell.setCellStyle(setBorder);
            }
            setCellValue(cell, value, dateCellStyles);
        }
    }

    /**
     * <b>功能描述：</b>单元格进行赋值<br>
     * <b>修订记录：</b><br>
     * <li>20170524&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * <li>20171019&nbsp;&nbsp;|&nbsp;&nbsp;翟凤玺&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void setCellValue(HSSFCell cell, Object value, HSSFCellStyle[] dateCellStyles) {
        if (value == null) {
            cell.setCellValue("-");
            return;
        }
        if (value instanceof String) {
            cell.setCellValue(value.toString());
            return;
        }
        if (value instanceof Number) {
            int length = value.toString().length();
            if (length >= 10) {//解决 读取大于10位数字会变科学数字
                String num = value.toString();
                cell.setCellValue(num);
            } else {
                double num = ((Number) value).doubleValue();
                cell.setCellValue(num);
            }
            return;
        }
        if (value instanceof Date) {
            //日期, 区分“年月日和年月日时分秒”两种格式
            Date date = ((Date) value);
            cell.setCellStyle(isDateHasHms(date) ? dateCellStyles[1] : dateCellStyles[0]);
            cell.setCellValue(date);
            return;
        }
        if (value instanceof Boolean) {
            Boolean b = (Boolean) value;
            cell.setCellValue(b ? "√" : "");
            return;
        }
        // 其他类型
        cell.setCellValue(value.toString());
    }

    /**
     * 判断日期是否包含时分秒
     */
    private boolean isDateHasHms(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY) != 0 ||
                cal.get(Calendar.MINUTE) != 0 ||
                cal.get(Calendar.SECOND) != 0 ||
                cal.get(Calendar.MILLISECOND) != 0;
    }

    /**
     * <b>功能描述：</b>初始化工作表数量<br>
     * <b>修订记录：</b><br>
     * <li>20170524&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private int getSheetNum(int size, int rowNumber) {
        int sheetNum;//初始化工作表数量
        if (size == 0) {
            sheetNum = 1;
        } else if (size % rowNumber > 0) {
            sheetNum = size / rowNumber + 1;
        } else {
            sheetNum = size / rowNumber;
        }
        return sheetNum;
    }

}
