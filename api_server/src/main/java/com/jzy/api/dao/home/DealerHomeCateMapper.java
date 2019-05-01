package com.jzy.api.dao.home;

import com.jzy.api.cnd.home.DealerHomeCateListCnd;
import com.jzy.api.cnd.home.CommonUpdateStatusCnd;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.vo.home.DealerHomeCateVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b><br>
 */
public interface DealerHomeCateMapper extends GenericMapper<HomeRecommendCate> {

    List<DealerHomeCateVo> listPage(DealerHomeCateListCnd listCnd);

    Integer listPageCount(DealerHomeCateListCnd listCnd);

    Boolean updateStatusBatch(@Param("id") Long id, @Param("status") Integer status);

    void deleteBatch(@Param("id") Long id);

    DealerHomeCateVo getById(@Param("id") Long id);
}
