package com.jzy.api.service.app.impl;

import com.jzy.api.dao.app.CategoryMapper;
import com.jzy.api.model.app.Category;
import com.jzy.api.service.app.CategoryService;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.api.vo.app.DealerAppListVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVo> listByDealerId(Long dealerId) {
        // todo: 需要添加 redis 缓存，参看老版本代码
        // todo: 需要筛选当前 渠道商下，有商品的分类，某个分类下没有对接商品，则不显示其分类
        List<CategoryVo> categoryList = categoryMapper.listByDealerId(dealerId);
        return categoryList;
    }

    @Override
    public Map<String, Object> dealerAppList(Long cateId, Long dealerId) {
        Map<String, Object> params = new HashMap<>();

        List<DealerAppListVo> dealerAppList = categoryMapper.dealerAppList(cateId, dealerId);
        List<DealerAppListVo> dealerAppHotList = null;

        // 使用lambda表达式过滤出结果并放到 dealerAppHotList 列表里
        dealerAppHotList = dealerAppList.stream()
                .filter((DealerAppListVo b) -> b.getIsReco().equals(true))
                .collect(Collectors.toList());

        // 经销商当前分类上架的热门商品
        params.put("dealerAppInfoList", dealerAppList);
        params.put("dealerAppInfoHotList", dealerAppHotList);
        return params;
    }

    /**
     * <b>功能描述：</b>产品分类列表查询（后台查询使用）<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<CategoryVo> getList() {
        return categoryMapper.getList();
    }

    @Override
    protected GenericMapper<Category> getGenericMapper() {
        return categoryMapper;
    }
}
