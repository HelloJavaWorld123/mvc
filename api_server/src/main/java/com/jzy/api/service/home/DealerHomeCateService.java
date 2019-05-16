package com.jzy.api.service.home;


import com.github.pagehelper.PageInfo;
import com.jzy.api.cnd.home.DealerHomeCateListCnd;
import com.jzy.api.cnd.home.CommonUpdateStatusCnd;
import com.jzy.api.cnd.home.DealerHomeCateSaveCnd;
import com.jzy.api.cnd.home.GetDealerAppOrCateCnd;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.vo.home.DealerAppCateVo;
import com.jzy.api.vo.home.DealerAppInfoVo;
import com.jzy.api.vo.home.DealerHomeCateVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.GenericService;

import java.util.List;

public interface DealerHomeCateService extends GenericService<HomeRecommendCate> {

    List<DealerHomeCateVo> listPage(DealerHomeCateListCnd listCnd);

    Long listPageCount(DealerHomeCateListCnd listCnd);

    void updateStatusBatch(CommonUpdateStatusCnd updateStatusCnd);

    void deleteBatch(List<Long> ids);

    DealerHomeCateVo getById(Long id);

    void save(HomeRecommendCate info);
    /** 轮播图列表
     * @Description
     * @Author lchl
     * @Date 2019/5/15 10:44 AM
     * @param listCnd
     */
    PageVo<DealerHomeCateVo> listForPage(DealerHomeCateListCnd listCnd);
    /** 轮播图保存
     * @Description
     * @Author lchl
     * @Date 2019/5/15 11:17 AM
     * @param infoCnd
     * @return com.jzy.framework.result.ApiResult
     */
    ApiResult save(DealerHomeCateSaveCnd infoCnd);
    /** 获取商品分类
     * @Description
     * @Author lchl
     * @Date 2019/5/15 3:19 PM
     * @param getDealerAppOrCateCnd
     * @return java.util.List<com.jzy.api.vo.home.DealerAppCateVo>
     */
    List<DealerAppCateVo> getAppCate(GetDealerAppOrCateCnd getDealerAppOrCateCnd);
    /** 获取渠道商商品信息列表
     * @Description
     * @Author lchl
     * @Date 2019/5/15 4:08 PM
     * @param getDealerAppOrCateCnd
     * @return java.util.List<com.jzy.api.vo.home.DealerAppInfoVo>
     */
    List<DealerAppInfoVo> getDealerAppList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);

    List<DealerAppInfoVo> getDealerAppPriceList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);
}
