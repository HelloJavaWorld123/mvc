package com.jzy.api.service.app;

import com.jzy.api.model.app.Category;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.GenericService;
import org.springframework.web.bind.annotation.RequestMapping;

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
     *
     * @return List
     */
    List<CategoryVo> listByDealerId(Long dealerId);

    Map<String, Object> dealerAppList(Long cateId, Long dealerId);


    /**
     * <b>功能描述：</b>产品分类列表查询（后台查询使用）<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<CategoryVo> getList();
}
