package com.jzy.api.service.arch.impl;

import com.jzy.api.dao.arch.DealerBaseInfoMapper;
import com.jzy.api.model.dealer.DealerBaseInfo;
import com.jzy.api.service.arch.DealerBaseInfoService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <b>功能：</b>渠道商基础信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190509&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class DealerBaseInfoServiceImpl  extends GenericServiceImpl<DealerBaseInfo> implements DealerBaseInfoService {

    @Resource
    private DealerBaseInfoMapper dealerBaseInfoMapper;
    @Override
    protected GenericMapper<DealerBaseInfo> getGenericMapper() {
        return dealerBaseInfoMapper;
    }
}
