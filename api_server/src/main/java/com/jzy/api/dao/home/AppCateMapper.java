package com.jzy.api.dao.home;

import com.jzy.api.cnd.home.GetDealerAppOrCateCnd;
import com.jzy.api.vo.home.DealerAppCateVo;
import com.jzy.api.vo.home.DealerAppInfoVo;
import com.jzy.framework.dao.GenericMapper;

import java.util.List;

public interface AppCateMapper extends GenericMapper {
    List<DealerAppCateVo> getAppCateList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);

    List<DealerAppInfoVo> getDealerAppList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);

    List<DealerAppInfoVo> getDealerAppPriceList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);

    String getAppName(String goId);

    String getAppCateName(String goId);
}
