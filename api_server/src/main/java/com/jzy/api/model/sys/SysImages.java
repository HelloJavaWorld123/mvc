package com.jzy.api.model.sys;


import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

/**
 * <b>功能：</b>系统图片保存表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190420&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SysImages extends GenericModel {

    /**
     * 关联业务表
     */
    private Long relId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件保存地址（mongoDB图片地址）
     */
    private String fileUrl;

    /**
     * 文件所属业务类型
     * 1，商品详情中商品的商标
     */
    private int type;

    public SysImages( Long id,Long relId, String fileName, String fileType, String fileUrl, int type) {
        this.relId = relId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileUrl = fileUrl;
        this.type = type;
        this.id=id;
    }

    public SysImages() {
    }
}
