package com.jzy.api.service.home.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.home.HomeHotGroupCnd;
import com.jzy.api.cnd.home.HomeRecommendHotCnd;
import com.jzy.api.cnd.home.HomeRecommendHotGroupCnd;
import com.jzy.api.dao.home.HomeRecommendHotGroupMapper;
import com.jzy.api.model.Home.*;
import com.jzy.api.service.home.HomeRecommendHotGroupService;
import com.jzy.api.service.home.HomeRecommendHotService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.vo.home.HomeHotGroupVo;
import com.jzy.api.vo.home.HomeHotVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <b>功能：</b>渠道商首页推荐Hot<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class HomeRecommendHotGroupServiceImpl extends GenericServiceImpl<HomeRecommendHotGroup> implements HomeRecommendHotGroupService {

    @Resource
    private HomeRecommendHotGroupMapper homeRecommendHotGroupMapper;

    @Resource
    private HomeRecommendHotService homeRecommendHotService;

    @Resource
    private TableKeyService tableKeyService;

    /**
     * <b>功能描述：</b>首页推荐分组分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(HomeHotGroupCnd homeHotGroupCnd) {
        Integer page = homeHotGroupCnd.getPage();
        Integer limit = homeHotGroupCnd.getLimit();
        Page<HomeHotGroupVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<HomeHotGroupVo> appInfoListVoList = homeRecommendHotGroupMapper.listPage(homeHotGroupCnd);
        PageVo<HomeHotGroupVo> pageVo = new PageVo<>(appInfoListVoList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    /**
     * <b>功能描述：</b>后台首页推荐分组添加<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void save(HomeRecommendHotGroup homeRecommendHotGroup) {
        //homeRecommendHotGroup.setDealerId(dealerId.longValue());
        int count = homeRecommendHotGroupMapper.getByName(homeRecommendHotGroup.getGroupName(), homeRecommendHotGroup.getDealerId());
        if(count>0){
            throw new BusException(homeRecommendHotGroup.getGroupName()+"首页推荐分组名称已经存在");
        }
        //添加到基本信息到homehot表
        for(int i=0;i<=4;i++) {
            HomeRecommendHot homeRecommendHot = new HomeRecommendHot();
            homeRecommendHot.setId(tableKeyService.newKey("home_recommend_hot", "id", 0));
            homeRecommendHot.setGroupId(homeRecommendHotGroup.getId().toString());
            homeRecommendHot.setDealerId(homeRecommendHotGroup.getDealerId());
            homeRecommendHot.setPosition(i);
            //默认跳转商品
            homeRecommendHot.setGoType(1);
            homeRecommendHotService.save(homeRecommendHot);
        }
        this.insert(homeRecommendHotGroup);
    }

    /**
     * <b>功能描述：</b>后台首页推荐分组删除<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    @Override
    public void delete(Long id) {
        int count = homeRecommendHotGroupMapper.getByIdStatus(id);
        if(count>0){
            throw new BusException("分组启用状态不能删除");
        }
        //查询分组下面的所有商品
        List<HomeHotVo> list = homeRecommendHotService.getByGroupId(id);
        List<String> result = new ArrayList<>();
        for(HomeHotVo homeHotVo:list){
            result.add(homeHotVo.getId());
        }
        //删除分组表下面的商品
        homeRecommendHotService.delateBatch(result);
        //删除分组信息
        homeRecommendHotGroupMapper.delete(id);
    }

    /**
     * <b>功能描述：</b>后台首页推荐分组编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void edit(HomeRecommendHotGroup homeRecommendHotGroup) {
        int count =  homeRecommendHotGroupMapper.getCountByNameNoId(homeRecommendHotGroup.getGroupName(),homeRecommendHotGroup.getId(),homeRecommendHotGroup.getDealerId());
        if(count>0){
            throw new BusException("分组名称相同");
        }
        this.update(homeRecommendHotGroup);
    }

    /**
     * <b>功能描述：</b>后台首页推荐分组启用停用<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void setStatus(HomeRecommendHotGroupCnd homeRecommendHotGroupCnd) {
        homeRecommendHotGroupMapper.setStatus(homeRecommendHotGroupCnd.getId(),homeRecommendHotGroupCnd.getState());
    }


    @Override
    protected GenericMapper<HomeRecommendHotGroup> getGenericMapper() {
        return homeRecommendHotGroupMapper;
    }
}
