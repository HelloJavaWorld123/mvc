package com.jzy.api.model.biz;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>Excel导出数据实体类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class ExcelExport extends GenericModel {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件类型
     */
    private Integer type = 0;
    /**
     * 文件下载地址
     */
    private String fileUrl;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 状态: 0 生成中  1 成功  2失败
     */
    private Integer status = 0;
    /**
     * 备注
     */
    private String remark;
    /**
     * 商户id
     */
    private Integer dealerId;

}
