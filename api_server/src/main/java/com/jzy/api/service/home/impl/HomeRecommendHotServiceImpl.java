package com.jzy.api.service.home.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.home.DialogBannerCnd;
import com.jzy.api.cnd.home.HomeHotListCnd;
import com.jzy.api.cnd.home.HomeRecommendHotCnd;
import com.jzy.api.constant.HomeEnums;
import com.jzy.api.dao.home.AppCateMapper;
import com.jzy.api.dao.home.HomeRecommendHotMapper;
import com.jzy.api.model.Home.*;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.service.home.HomeRecommendHotService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.vo.home.DialogBannerVo;
import com.jzy.api.vo.home.HomeHotInfoVo;
import com.jzy.api.vo.home.HomeHotVo;
import com.jzy.api.vo.home.HomeRecommendHotVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.GenericService;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>功能：</b>渠道商首页推荐Hot<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class HomeRecommendHotServiceImpl extends GenericServiceImpl<HomeRecommendHot> implements HomeRecommendHotService {

    @Resource
    private HomeRecommendHotMapper homeRecommendHotMapper;

    @Resource
    private SysImagesService sysImagesService;

    @Resource
    private TableKeyService tableKeyService;

    @Resource AppCateMapper appCateMapper;

    /**
     * <b>功能描述：</b>首页查询商品推荐列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
//    @Override
//    public List<HomeRecommendHotVo> getList() {
//        Integer dealerId=getFrontDealerId();
//        List<HomeRecommendHotVo> homeRecommendHotVoList = new ArrayList<>();
//        //查询分组信息
//        List<GroupeDetail> groupeDetailList = homeRecommendHotMapper.getGroupeDetailList(dealerId + "");
//        List<HomeRecommendHot> HomeRecommendHots = homeRecommendHotMapper.queryHotList(dealerId + "");
//        //拼装数据
//        for (GroupeDetail groupeDetail : groupeDetailList) {
//            HomeRecommendHotVo homeRecommendHotVo = new HomeRecommendHotVo();
//            homeRecommendHotVo.setGroupeDetail(groupeDetail);
//            List<HomeRecommendHotDetail> homeRecommendHotDetails = getHomeRecommendHotDetailList(groupeDetail.getGroupeId(), HomeRecommendHots);
//            homeRecommendHotVo.setHomeRecommendHotDetailList(homeRecommendHotDetails);
//            homeRecommendHotVoList.add(homeRecommendHotVo);
//        }
//
//        return homeRecommendHotVoList;
//    }

    public List<HomeRecommendHotVo> getList() {
        Integer dealerId=getFrontDealerId();
        List<HomeRecommendHotVo> homeRecommendHotVoList = new ArrayList<>();
        //查询分组信息
        List<GroupeDetail> groupeDetailList = homeRecommendHotMapper.getGroupeList(dealerId + "");
        //查询商品信息
        for (GroupeDetail groupeDetail : groupeDetailList) {
            HomeRecommendHotVo homeRecommendHotVo = new HomeRecommendHotVo();
            homeRecommendHotVo.setGroupeDetail(groupeDetail);
            //获取分组下面所有所有商品信息
            List<HomeRecommendHotDetail> homeRecommendHotDetails = homeRecommendHotMapper.getHomeRecommendHotDetails(groupeDetail.getGroupeId());
            homeRecommendHotVo.setHomeRecommendHotDetailList(homeRecommendHotDetails);
            homeRecommendHotVoList.add(homeRecommendHotVo);
        }
        return homeRecommendHotVoList;
    }

    /**
     * <b>功能描述：</b>获取当前分组下的所有的商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190428&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private List<HomeRecommendHotDetail> getHomeRecommendHotDetailList(String groupeId, List<HomeRecommendHot> HomeRecommendHots) {
        List<HomeRecommendHotDetail> homeRecommendHotDetails = new ArrayList<>(10);
        for (HomeRecommendHot HomeRecommendHot : HomeRecommendHots) {
            String dealerId = HomeRecommendHot.getDealerId();
            String goId = HomeRecommendHot.getGoId();
            if (HomeRecommendHot.getGroupId().equals(groupeId)) {
                HomeRecommendHotDetail homeRecommendHotDetail = new HomeRecommendHotDetail();
                BeanUtils.copyProperties(HomeRecommendHot, homeRecommendHotDetail);
                //获取商品信息
                HotAppInfoDetail hotAppInfoDetail = homeRecommendHotMapper.getHotAppInfoDetail(dealerId, goId);
                if (null != hotAppInfoDetail) {
                    homeRecommendHotDetail.setHotAppInfoDetail(hotAppInfoDetail);
                }
                homeRecommendHotDetails.add(homeRecommendHotDetail);
            }
        }
        return homeRecommendHotDetails;
    }

    /**
     * <b>功能描述：</b>产品类型分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(HomeHotListCnd homeHotListCnd) {
        Integer page = homeHotListCnd.getPage();
        Integer limit = homeHotListCnd.getLimit();
        Page<HomeHotVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<HomeHotVo> appInfoListVoList = homeRecommendHotMapper.listPage(homeHotListCnd);
        PageVo<HomeHotVo> pageVo = new PageVo<>(appInfoListVoList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    /**
     * <b>功能描述：</b>根据id获取首页推荐商品信息<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public HomeHotInfoVo getHomeInfoHot(Long id) {
        HomeHotInfoVo homeHotInfoVo = homeRecommendHotMapper.getHomeInfoHot(id);
        return homeHotInfoVo;
    }

    /**
     * <b>功能描述：</b>后台首页推荐分组商品添加<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(HomeRecommendHot homeRecommendHot) {
        this.insert(homeRecommendHot);
    }

    /**
     * <b>功能描述：</b>后台首页推荐分组商品编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void edit(HomeRecommendHotCnd homeRecommendHotCnd) {
        FileInfo mfile = null;
        if (null != homeRecommendHotCnd.getFileInfo()) {
            mfile = homeRecommendHotCnd.getFileInfo();
        }
        //商品位置不能相同
        int countPosition = homeRecommendHotMapper.getByPosition(homeRecommendHotCnd.getId(),
                homeRecommendHotCnd.getGroupId(),homeRecommendHotCnd.getPosition());
        if(countPosition>0){
            throw new BusException(homeRecommendHotCnd.getGoName()+"首页推荐分组中位置不能相同");
        }
        //商品名称不能相同
        int countGoId = homeRecommendHotMapper.getByName(homeRecommendHotCnd.getId(),
                homeRecommendHotCnd.getGroupId(),homeRecommendHotCnd.getGoId());
        if(countGoId>0){
            throw new BusException(homeRecommendHotCnd.getGoName()+"首页推荐分组中商品名称不能相同");
        }
        //获取goname，如果是商品
        if(homeRecommendHotCnd.getGoType() == 1){
            //获取跳转商品名称
            homeRecommendHotCnd.setGoName(appCateMapper.getAppNameById(homeRecommendHotCnd.getGoId()));
        }else if(homeRecommendHotCnd.getGoType() ==2||homeRecommendHotCnd.getGoType() ==3){//如果是分组
            if (homeRecommendHotCnd.getImageUrl() == null){
                throw new BusException("首页推荐分组如果商品跳转分组必须上传图片");
            }
            if (homeRecommendHotCnd.getPosition()!=0){
                throw new BusException("分组只能在中上，不能再其他位置");
            }
            if(homeRecommendHotCnd.getGoType() ==2) {
                //获取跳转分组名称
                homeRecommendHotCnd.setGoName(appCateMapper.getAppCateName(homeRecommendHotCnd.getGoId()));
            }
            if(homeRecommendHotCnd.getImageUrl()!=null&&homeRecommendHotCnd.getFileInfo()!=null) {
                //生成图片信息对象
                SysImages images = getSystemImagesMapper(homeRecommendHotCnd, mfile);
                //添加新的图片信息
                sysImagesService.save(images);
            }
        }
        homeRecommendHotCnd.setState(1);
        //修改人id
        homeRecommendHotCnd.setModifierId(getDealerId().longValue());
        //修改时间
        homeRecommendHotCnd.setModifyTime(new Date());
        //保存推荐商品信息
        homeRecommendHotMapper.edit(homeRecommendHotCnd);
    }

    private SysImages getSystemImagesMapper(HomeRecommendHotCnd infoCnd, FileInfo mfile) {
        return new SysImages(tableKeyService.newKey("home_recommend_cate", "id", 0), infoCnd.getId().toString(), mfile.getFileOrignName(), mfile.getContentType(), infoCnd.getImageUrl(), HomeEnums.ImageType.recommend.ordinal());
    }

    /**
     * <b>功能描述：</b>查询分组下面所有商品<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<HomeHotVo> getByGroupId(Long id) {
        return homeRecommendHotMapper.getByGroupId(id);
    }
    /**
     * <b>功能描述：</b>删除分组下面所有商品<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void delateBatch(List<String> result) {
        homeRecommendHotMapper.deleteBatch(result);
    }

    @Override
    public int getByGroupIdAndStatus(Long groupId) {
        return homeRecommendHotMapper.getByGroupIdAndStatus(groupId);
    }

    @Override
    protected GenericMapper<HomeRecommendHot> getGenericMapper() {
        return homeRecommendHotMapper;
    }
}