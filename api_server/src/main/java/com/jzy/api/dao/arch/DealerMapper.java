package com.jzy.api.dao.arch;

import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.po.arch.DealerAnalysisInfoPo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <b>功能：</b>渠道商基础信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface DealerMapper extends GenericMapper<Dealer> {


    /**
     * <b>功能描述：</b>获取渠道商私钥<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    DealerAnalysisInfoPo getAnalysisInfo(@Param("idNum") String idNum);

}
