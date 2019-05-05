package com.jzy.api.service.arch.impl;

import com.jzy.api.dao.arch.DealerParamMapper;
import com.jzy.api.po.arch.DealerParamInfoPo;
import com.jzy.api.service.arch.DealerParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@Service
public class DealerParamServiceImpl implements DealerParamService {

    @Resource
    private DealerParamMapper dealerParamMapper;


    /**
     * <b>功能描述：</b>根据渠道商id获取渠道商配置信息<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<DealerParamInfoPo> getDealerParamInfo(String dealerId) {
        return  dealerParamMapper.getDealerParamInfo(dealerId);
    }
}
