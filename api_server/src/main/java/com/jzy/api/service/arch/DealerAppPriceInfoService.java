package com.jzy.api.service.arch;

import com.github.pagehelper.PageInfo;
import com.jzy.api.cnd.app.AppSearchListCnd;
import com.jzy.api.cnd.arch.*;
import com.jzy.api.model.dealer.DealerAppPriceInfo;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.vo.app.AppDetailVo;
import com.jzy.api.vo.app.AppSearchListVo;
import com.jzy.api.vo.dealer.DealerAppPriceInfoDetailVo;
import com.jzy.api.vo.dealer.GetDealerAppVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.ExcelException;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.GenericService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <b>功能：</b>渠道商商品定价<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface DealerAppPriceInfoService extends GenericService<DealerAppPriceInfo> {


    /**
     * <b>功能描述：</b>前台查询商品价格接口<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<DealerAppPriceInfoPo> getPrice(GetPriceCnd getPriceCnd);

    /**
     * <b>功能描述：</b>前台查询商品详情<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppDetailVo getAppDetail(String aiId);


    /**
     * <b>功能描述：</b>渠道商商品热门搜索<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo appSearchList(AppSearchListCnd appSearchListCnd);




    /**
     * <b>功能描述：</b>查询渠道商下对应的商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo getList(GetDealerAppListCnd getDealerAppListCnd);


    /**
     * <b>功能描述：</b>查询渠道商商品定价详情<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    DealerAppPriceInfoDetailVo getDealerAppDetail(GetPriceInfoCnd getPriceInfoCnd);


    /**
     * <b>功能描述：</b>渠道商商品定价信息的保存<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(SavePriceInfoCnd savePriceInfoCnd) throws ExcelException;


    /**
     * <b>功能描述：</b>批量修改上下架状态<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void batchUpdateStatus(BatchUpdateStatusCnd batchUpdateStatusCnd);

    /**
     * <b>功能描述：</b>根据商品id获取商品价格信息<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<DealerAppPriceInfo> queryAppPriceInfoByAppId(Long appId, Long aptId);



}
