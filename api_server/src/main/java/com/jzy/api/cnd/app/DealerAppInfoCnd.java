package com.jzy.api.cnd.app;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

/**
 * <b>功能：</b>渠道商商品表入参<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class DealerAppInfoCnd{

    /**
     * 经销商id
     */
    private String dealerId;
    /**
     * 应用Id
     */
    private String aiId;
    /**
     * 是否热门
     */
    private Integer isHot=0;
    /**
     * 是否推荐
     */
    private Integer isReco=0;
    /**
     * 状态  0 下架  1上架  2删除
     */
    private Integer status=1;
    /**
     * 排序
     */
    private Integer sort=1000;

}
