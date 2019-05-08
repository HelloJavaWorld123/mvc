package com.jzy.api.service.arch.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.arch.DealerListCnd;
import com.jzy.api.cnd.arch.SaveDealerCnd;
import com.jzy.api.dao.arch.DealerMapper;
import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.model.dealer.DealerBaseInfo;
import com.jzy.api.po.arch.DealerAnalysisInfoPo;
import com.jzy.api.po.dealer.DealerListPo;
import com.jzy.api.service.arch.DealerService;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：</b>渠道商业务处理类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class DealerServiceImpl extends GenericServiceImpl<Dealer> implements DealerService {

    @Resource
    private DealerMapper dealerMapper;

    @Override
    public Dealer queryDealer(Integer dealerId) {
        return dealerMapper.queryById(dealerId.longValue());
    }

    /**
     * <b>功能描述：</b>获取渠道商私钥<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public DealerAnalysisInfoPo getAnalysisInfo(String businessId) {
        return dealerMapper.getAnalysisInfo(businessId);

    }

    /**
     * <b>功能描述：</b>渠道商列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo getList(DealerListCnd dealerListCnd) {
        Integer page = dealerListCnd.getPage();
        Integer limit = dealerListCnd.getLimit();
        Page<DealerListPo> dealerListPoPage = PageHelper.startPage(page, limit);
        List<DealerListPo> dealerListPoList = dealerMapper.getList(dealerListCnd.getQueryName());
        PageVo<DealerListPo> pageVo = new PageVo<>(dealerListPoList);
        pageVo.setLimit(limit);
        pageVo.setPage(page);
        pageVo.setTotalCount(dealerListPoPage.getTotal());
        return pageVo;
    }

    /**
     * <b>功能描述：</b>渠道商信息保存接口<br>
     * <b>修订记录：</b><br>
     * <li>20190422&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(SaveDealerCnd saveDealerCnd) {

    }

    /**
     * <b>功能描述：</b>渠道商详情<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public DealerBaseInfo detail(Integer id) {
        return null;
    }

    @Override
    protected GenericMapper<Dealer> getGenericMapper() {
        return dealerMapper;
    }
}
