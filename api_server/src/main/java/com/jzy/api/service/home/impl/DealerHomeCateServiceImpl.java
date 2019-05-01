package com.jzy.api.service.home.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.home.DealerHomeCateListCnd;
import com.jzy.api.cnd.home.CommonUpdateStatusCnd;
import com.jzy.api.dao.home.DealerHomeCateMapper;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.service.home.DealerHomeCateService;
import com.jzy.api.vo.home.DealerHomeCateVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DealerHomeCateServiceImpl extends GenericServiceImpl<HomeRecommendCate> implements DealerHomeCateService {

    @Resource
    private DealerHomeCateMapper mapper;

    @Override
    protected GenericMapper<HomeRecommendCate> getGenericMapper() {
        return mapper;
    }

    @Override
    public List<DealerHomeCateVo> listPage(DealerHomeCateListCnd listCnd) {
        Page<DealerHomeCateVo> page = PageHelper.startPage(listCnd.getPage(), listCnd.getLimit());
        return mapper.listPage(listCnd);
    }


    @Override
    public Integer listPageCount(DealerHomeCateListCnd listCnd) {
        return mapper.listPageCount(listCnd);
    }

    @Override
    public void updateStatusBatch(CommonUpdateStatusCnd updateStatusCnd) {
        Integer status = updateStatusCnd.getStatus();
        for (Long id : updateStatusCnd.getIds()) {
            mapper.updateStatusBatch(id, status);
        }
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            mapper.deleteBatch(id);
        }
    }

    @Override
    public void save(HomeRecommendCate info) {
        HomeRecommendCate entity = mapper.queryById(info.getId());
        if (entity != null) {
            // 修改
            mapper.update(info);
        }else{
            // 新增
            mapper.insert(info);
        }
    }

    @Override
    public DealerHomeCateVo getById(Long id){
        return mapper.getById(id);
    }
}
