package com.jzy.api.service.biz.impl;

import com.jzy.api.dao.biz.CardPwdMapper;
import com.jzy.api.model.biz.CardPwd;
import com.jzy.api.service.biz.CardPwdService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <b>功能：</b>卡密<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class CardPwdServiceImpl extends GenericServiceImpl<CardPwd> implements CardPwdService {

    @Resource
    private CardPwdMapper cardPwdMapper;

    /**
     * <b>功能描述：</b>查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 主键
     */
    @Override
    public String queryCardPwd(Long id) {
        return cardPwdMapper.queryCardPwd(id);
    }

    /**
     * <b>功能描述：</b>是否存在该订单的卡号<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param orderId 订单id
     * @param cardNo 卡号
     */
    @Override
    public boolean isExist(String orderId, String cardNo) {
        return cardPwdMapper.isExist(orderId, cardNo);
    }

    @Override
    protected GenericMapper<CardPwd> getGenericMapper() {
        return cardPwdMapper;
    }
}
