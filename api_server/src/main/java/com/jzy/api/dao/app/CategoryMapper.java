package com.jzy.api.dao.app;

import com.jzy.api.model.app.Category;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.api.vo.app.DealerAppListVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper extends GenericMapper<Category> {

    List<CategoryVo> listByDealerId(@Param("dealerId") Long dealerId);

    List<DealerAppListVo> dealerAppList(@Param("cateId") Long cateId, @Param("dealerId") Long dealerId);
}
