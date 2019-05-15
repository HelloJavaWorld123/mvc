package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.CategoryCnd;
import com.jzy.api.cnd.app.CategoryListCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.CategoryMapper;
import com.jzy.api.model.app.Category;
import com.jzy.api.service.app.CategoryService;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.api.vo.app.DealerAppListVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private AppInfoMapper appInfoMapper;

    @Override
    public List<CategoryVo> listByDealerId() {
        // todo: 需要添加 redis 缓存，参看老版本代码
        Integer dealerId=getFrontDealerId();
        List<CategoryVo> categoryList = categoryMapper.listByDealerId(dealerId + "");
        return categoryList;
    }

    @Override
    public Map<String, Object> dealerAppList(Long cateId) {
        Map<String, Object> params = new HashMap<>();
        Integer dealerId=getFrontDealerId();
        List<DealerAppListVo> dealerAppList = categoryMapper.dealerAppList(cateId, dealerId + "");
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

    /**
     * <b>功能描述：</b>产品分类分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(CategoryListCnd categoryListCnd) {
        Integer page = categoryListCnd.getPage();
        Integer limit = categoryListCnd.getLimit();
        Page<CategoryVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<CategoryVo> appInfoListVoList = categoryMapper.listPage(categoryListCnd);
        PageVo<CategoryVo> pageVo = new PageVo<>(appInfoListVoList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    /**
     * <b>功能描述：</b>产品分类添加<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(Category category) {
        int count = categoryMapper.getByName(category.getName());
        if(count>0){
            throw new BusException(category.getName()+"产品分类名称已经存在");
        }
        this.insert(category);
    }

    /**
     * <b>功能描述：</b>产品分类删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void delete(Long id) {
        int count =  appInfoMapper.getCountByCateId(id);
        if(count>0){
            throw new BusException(ResultEnum.APP_UNABLE_DELETE.getMsg());
        }
        categoryMapper.delete(id);
    }

    /**
     * <b>功能描述：</b>产品分类编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void edit(Category category) {
        int count =  categoryMapper.getCountByNameNoId(category.getName(),category.getId());
        if(count>0){
            throw new BusException(ResultEnum.APP_UNABLE_DELETE.getMsg());
        }
        this.update(category);
    }

    @Override
    protected GenericMapper<Category> getGenericMapper() {
        return categoryMapper;
    }
}
