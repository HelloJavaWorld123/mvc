package com.jzy.api.vo.home;



import com.jzy.api.model.Home.GroupeDetail;
import com.jzy.api.model.Home.HomeRecommendHotDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>首页轮播图推荐列表查询<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DialogBannerVo {

    /**
     * 主图图片url
     */
    private String imageUrl;

    /**
     * 跳转类型 默认1-商品 2-分组
     */
    private Integer goType;

    /**
     * 跳转到的定价id
     */
    private String goId;

    /**
     * 跳转地址
     */
    private String goName;

    /**
     * 轮播图状态 0-禁用 1-启用
     */
    private Integer status;
}
