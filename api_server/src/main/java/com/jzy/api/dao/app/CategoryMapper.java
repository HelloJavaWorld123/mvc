package com.jzy.api.dao.app;

import com.jzy.api.model.app.Category;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.api.vo.app.DealerAppListVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b>产品分类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface CategoryMapper extends GenericMapper<Category> {

    List<CategoryVo> listByDealerId(@Param("dealerId") Long dealerId);

    List<DealerAppListVo> dealerAppList(@Param("cateId") Long cateId, @Param("dealerId") Long dealerId);

    /**
     * <b>功能描述：</b>产品分类列表查询（后台查询使用）<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<CategoryVo> getList();

}
