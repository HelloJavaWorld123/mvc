package com.jzy.api.service.app;

import com.jzy.api.cnd.app.CategoryCnd;
import com.jzy.api.cnd.app.CategoryListCnd;
import com.jzy.api.model.app.Category;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.framework.bean.vo.PageVo;
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
    List<CategoryVo> listByDealerId();

    Map<String, Object> dealerAppList(Long cateId);


    /**
     * <b>功能描述：</b>产品分类列表查询（后台查询使用）<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<CategoryVo> getList();

    /**
     * <b>功能描述：</b>产品分类添加<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(Category category);

    /**
     * <b>功能描述：</b>产品分类删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delete(Long valueOf);

    /**
     * <b>功能描述：</b>产品分类编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void edit(Category category);

    /**
     * <b>功能描述：</b>产品分类分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<CategoryVo> listPage(CategoryListCnd categoryListCnd);
}
