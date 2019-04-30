package com.jzy.api.cnd.app;

import com.jzy.framework.bean.cnd.PageCnd;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <b>功能：</b>商品列表查询传参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AppInfoListCnd extends PageCnd {

    /**
     * 商品类型
     */
    private Long cateId;
    /**
     * 商品分类
     */

    private Long typeId;
    /**
     * 厂商
     */

    private Long acpId;

    /**
     * 充值类型
     */
    private Integer rechargeMode;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 模糊搜索
     */
    private String query;


}
