package com.jzy.api.dao.home;

import com.jzy.api.cnd.home.GetDealerAppOrCateCnd;
import com.jzy.api.vo.home.DealerAppCateVo;
import com.jzy.api.vo.home.DealerAppInfoVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppCateMapper extends GenericMapper {
    List<DealerAppCateVo> getAppCateList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);

    List<DealerAppInfoVo> getDealerAppList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);

    List<DealerAppInfoVo> getDealerAppPriceList(GetDealerAppOrCateCnd getDealerAppOrCateCnd);

    String getAppName(String goId);

    String getAppCateName(String goId);

    /**
     * 通过id获取当前商品的单价
     * @param goId
     * @return
     */
    String getPriceById(@Param("goId") String goId);

    /**
     * 通过id获取当前商品名称
     * @param goId
     * @return
     */
    String getAppNameById(@Param("goId") String goId);
}
