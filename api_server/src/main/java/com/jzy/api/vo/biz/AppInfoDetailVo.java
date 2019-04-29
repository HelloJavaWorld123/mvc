package com.jzy.api.vo.biz;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>功能：</b>查询商品详情接口返回<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190429&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppInfoDetailVo extends GenericVo {
    /**
     * 商品单表详情
     */
//    private AppInfoMapper appInfoMapper;
    /**
     * 商品充值类型列表
     */
    private List<Map<String, Object>> appPriceTypeMapperList = new ArrayList<>(10);

    /**
     * 商品文本信息
     */
//    private AppPageMapper appPageMapper;
}
