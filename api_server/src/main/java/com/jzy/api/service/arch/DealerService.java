package com.jzy.api.service.arch;

import com.jzy.api.cnd.arch.DealerListCnd;
import com.jzy.api.cnd.arch.SaveDealerCnd;
import com.jzy.api.cnd.arch.UpdateDealerStatusCnd;
import com.jzy.api.model.dealer.Dealer;

import com.jzy.api.po.arch.DealerAnalysisInfoPo;

import com.jzy.api.vo.dealer.DealerDetailVo;

import com.jzy.framework.bean.vo.PageVo;

import com.jzy.framework.exception.ExcelException;
import com.jzy.framework.service.GenericService;



/**
 * <b>功能：</b>渠道商<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface DealerService extends GenericService<Dealer> {

    /**
     * <b>功能描述：</b>获取渠道商信息<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    Dealer queryDealer(String dealerId);

    /**
     * <b>功能描述：</b>获取渠道商私钥<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    DealerAnalysisInfoPo getAnalysisInfo(String businessId);


    /**
     * <b>功能描述：</b>渠道商列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    PageVo getList(DealerListCnd dealerListCnd);


    /**
     * <b>功能描述：</b>渠道商信息保存接口<br>
     * <b>修订记录：</b><br>
     * <li>20190422&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(SaveDealerCnd saveDealerCnd) throws ExcelException;


   /**
    * <b>功能描述：</b>渠道商详情<br>
    * <b>修订记录：</b><br>
    * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
   DealerDetailVo detail(String id);


    /**
     * <b>功能描述：</b>渠道商状态修改<br>
     * <b>修订记录：</b><br>
     * <li>20190511&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
      int updateStatus(UpdateDealerStatusCnd updateDealerStatusCnd);


}
