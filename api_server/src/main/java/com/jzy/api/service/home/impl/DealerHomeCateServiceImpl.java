package com.jzy.api.service.home.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzy.api.cnd.home.DealerHomeCateListCnd;
import com.jzy.api.cnd.home.CommonUpdateStatusCnd;
import com.jzy.api.cnd.home.DealerHomeCateSaveCnd;
import com.jzy.api.cnd.home.GetDealerAppOrCateCnd;
import com.jzy.api.constant.HomeEnums;
import com.jzy.api.dao.home.AppCateMapper;
import com.jzy.api.dao.home.DealerHomeCateMapper;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.service.home.DealerHomeCateService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.vo.home.DealerAppCateVo;
import com.jzy.api.vo.home.DealerAppInfoVo;
import com.jzy.api.vo.home.DealerHomeCateVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DealerHomeCateServiceImpl extends GenericServiceImpl<HomeRecommendCate> implements DealerHomeCateService {

    @Resource
    private DealerHomeCateMapper mapper;

    @Resource
    private AppCateMapper appCateMapper;

    @Resource
    private TableKeyService tableKeyService;
    @Resource
    private SysImagesService sysImagesService;

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
    public Long listPageCount(DealerHomeCateListCnd listCnd) {
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

    @Override
    public PageVo<DealerHomeCateVo> listForPage(DealerHomeCateListCnd listCnd) {

        PageVo<DealerHomeCateVo> dealerHomeCateVoPageVo = new PageVo<>();

        PageHelper.startPage(listCnd.getPage(), listCnd.getLimit());
        List<DealerHomeCateVo> list = mapper.listPage(listCnd);
        PageInfo<DealerHomeCateVo> result = new PageInfo<>(list);

        dealerHomeCateVoPageVo.setTotalCount(result.getTotal());
        dealerHomeCateVoPageVo.setPage(listCnd.getPage());
        dealerHomeCateVoPageVo.setLimit(listCnd.getLimit());
        dealerHomeCateVoPageVo.setRows(list);

        return dealerHomeCateVoPageVo;
    }

    @Override
    public ApiResult save(DealerHomeCateSaveCnd infoCnd) {
        FileInfo mfile = null;
        if (null != infoCnd.getFileInfo()) {
            mfile = infoCnd.getFileInfo();
        }
        HomeRecommendCate HRC = new HomeRecommendCate();
        HRC.setRciName(infoCnd.getRciName());
        HRC.setSortDesc(infoCnd.getSortDesc());
        HRC.setDealerId(infoCnd.getDealerId());
        HRC.setGoType(infoCnd.getGoType());
        HRC.setGoId(infoCnd.getGoId());
        String goName = "";
        if(infoCnd.getGoType() == 1){
            goName = appCateMapper.getAppName(infoCnd.getGoId());
        }else if(infoCnd.getGoType() ==2){
            goName = appCateMapper.getAppCateName(infoCnd.getGoId());
            if(goName==null){
                goName="推荐专区";
            }
        }else{
            goName = infoCnd.getGoName();
            HRC.setGoId("0");
        }


        HRC.setGoName(goName);


        // 给查询类型赋默认值，0 轮播图类型 1 首页分类
        //HRC.setType(HomeEnums.HomeCateType.carousel.ordinal());
        HRC.setType(infoCnd.getType());
        HRC.setState(infoCnd.getState());
        HRC.setImageUrl(infoCnd.getImageUrl());


        if (StringUtils.isEmpty(infoCnd.getId())) {//新增操作
            infoCnd.setId(tableKeyService.newKey("home_recommend_cate", "id", 0));
            HRC.setState(1);
            //图片新增
            if (null != mfile) {
                SysImages images = getSystemImagesMapper(infoCnd, mfile);
                HRC.setImageId(images.getId().toString());
                sysImagesService.save(images);
            }
            HRC.setId(infoCnd.getId());
            this.insert(HRC);

        } else {
            //图片修改
            if (null != mfile) {
                SysImages images = getSystemImagesMapper(infoCnd, mfile);
                HRC.setImageId(images.getId().toString());
                images.setRelId(infoCnd.getId().toString());
                sysImagesService.update(images);
            }
            HRC.setId(infoCnd.getId());
            this.update(HRC);
        }
        return new ApiResult();
    }

    private SysImages getSystemImagesMapper(DealerHomeCateSaveCnd infoCnd, FileInfo mfile) {
        return new SysImages(tableKeyService.newKey("home_recommend_cate", "id", 0), infoCnd.getId().toString(), mfile.getFileOrignName(), mfile.getContentType(), infoCnd.getImageUrl(), HomeEnums.ImageType.category.ordinal());
    }

    @Override
    public List<DealerAppCateVo> getAppCate(GetDealerAppOrCateCnd getDealerAppOrCateCnd) {
        return appCateMapper.getAppCateList(getDealerAppOrCateCnd);
    }

    @Override
    public List<DealerAppInfoVo> getDealerAppList(GetDealerAppOrCateCnd getDealerAppOrCateCnd) {
        return appCateMapper.getDealerAppList(getDealerAppOrCateCnd);
    }

    @Override
    public List<DealerAppInfoVo> getDealerAppPriceList(GetDealerAppOrCateCnd getDealerAppOrCateCnd) {
        return appCateMapper.getDealerAppPriceList(getDealerAppOrCateCnd);
    }
}
