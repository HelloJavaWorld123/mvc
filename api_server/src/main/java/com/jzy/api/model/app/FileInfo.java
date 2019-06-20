package com.jzy.api.model.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>图片上传返回图片信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190422&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="图片文件信息")
public class FileInfo {

    /**
     * 文件地址（mongoDB地址）
     */
    @ApiModelProperty(value = "文件url")
    private String fileUrl;


    /**
     * 文件原名
     */
    @ApiModelProperty(value = "文件名称")
    private String fileOrignName;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String contentType;


    /**
     * 图片所属业务类型   1：商品图标  2：渠道商合同上传  3：渠道商营业执照上传 4：联系人身份照片
     */
    @ApiModelProperty(value = "图片所属业务类型（1商品，2渠道商,3轮播图，首页分类，4首页推荐，5反馈，6商品详情")
    private Integer type;


    public FileInfo() {
    }

    public FileInfo(String fileUrl, String fileOrignName, String contextType) {
        this.fileUrl = fileUrl;
        this.fileOrignName = fileOrignName;
        this.contentType = contextType;
    }

}
