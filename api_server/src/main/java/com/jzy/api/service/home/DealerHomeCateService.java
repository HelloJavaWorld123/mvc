package com.jzy.api.service.home;


import com.jzy.api.cnd.home.DealerHomeCateListCnd;
import com.jzy.api.cnd.home.CommonUpdateStatusCnd;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.vo.home.DealerHomeCateVo;
import com.jzy.framework.service.GenericService;

import java.util.List;

public interface DealerHomeCateService extends GenericService<HomeRecommendCate> {

    List<DealerHomeCateVo> listPage(DealerHomeCateListCnd listCnd);

    Long listPageCount(DealerHomeCateListCnd listCnd);

    void updateStatusBatch(CommonUpdateStatusCnd updateStatusCnd);

    void deleteBatch(List<Long> ids);

    DealerHomeCateVo getById(Long id);

    void save(HomeRecommendCate info);
}
