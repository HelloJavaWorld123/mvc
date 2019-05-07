package com.jzy.api.service.app;

import com.jzy.api.model.app.Category;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.framework.service.GenericService;

import java.util.List;
import java.util.Map;

/**
 * 应用业务层接口
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
public interface CategoryService extends GenericService<Category> {

    /**
     * 获取商品分类列表
     * @return List
     */
    List<CategoryVo> listByDealerId(Long dealerId);

    Map<String, Object> dealerAppList(Long cateId, Long dealerId);
}
