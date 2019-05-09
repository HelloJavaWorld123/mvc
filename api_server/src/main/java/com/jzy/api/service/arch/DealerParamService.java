package com.jzy.api.service.arch;

import com.jzy.api.model.dealer.DealerBaseInfo;
import com.jzy.api.model.dealer.DealerParam;
import com.jzy.api.po.arch.DealerParamInfoPo;
import com.jzy.framework.service.GenericService;

import java.util.List;

/**
 * <b>功能：</b>渠道商配置信息表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface DealerParamService extends GenericService<DealerParam> {

    /**
     * <b>功能描述：</b>根据渠道商id获取渠道商配置信息<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<DealerParamInfoPo> getDealerParamInfo(String dealerId);

    /**
     * <b>功能描述：</b>渠道商信息保存<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(Long dealerId, List<DealerParam> dpmList);


    /**
     * <b>功能描述：</b>渠道商配置信息的修改<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void updateDealerParam(String dealerId,List<DealerParam> dpmList);

}
