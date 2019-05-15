package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppCompanyCnd;
import com.jzy.api.cnd.app.AppCompanyListCnd;
import com.jzy.api.dao.app.AppCompanyMapper;
import com.jzy.api.dao.app.AppGameMapper;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.model.app.AppCompany;
import com.jzy.api.po.app.AppCompanyPo;
import com.jzy.api.service.app.AppCompanyService;
import com.jzy.api.vo.app.AppCompanyVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：</b>厂商service<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class AppCompanyServiceImpl extends GenericServiceImpl<AppCompany> implements AppCompanyService {

    @Resource
    private AppCompanyMapper appCompanyMapper;

    @Resource
    private AppInfoMapper appInfoMapper;

    @Resource
    private AppGameMapper appGameMapper;


    /**
     * <b>功能描述：</b>厂商列表<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<AppCompanyPo> getList() {
        return appCompanyMapper.getList();
    }

    /**
     * <b>功能描述：</b>分页查询厂商列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(AppCompanyListCnd appCompanyListCnd) {
        Integer page = appCompanyListCnd.getPage();
        Integer limit = appCompanyListCnd.getLimit();
        Page<AppCompanyVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<AppCompanyVo> appInfoListVoList = appCompanyMapper.listPage(appCompanyListCnd);
        PageVo<AppCompanyVo> pageVo = new PageVo<>(appInfoListVoList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    /**
     * <b>功能描述：</b>厂商添加<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int save(AppCompany appCompany) {
        int count = appCompanyMapper.getCountByName(appCompany.getName());
        if (count > 0) {
            throw new BusException(appCompany.getName()+"厂商名称已经存在");
        }

        return this.insert(appCompany);
    }

    /**
     * <b>功能描述：</b>厂商删除<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void delete(Long id) {
        //商品表中app_info是否正在使用该厂商
        int acpCount = appInfoMapper.getCountByAcpId(id);
        //游戏表app_game中是否正在使用该厂商
        int pCount =  appGameMapper.getCountByPId(id);
        if(acpCount>0){
            throw new BusException(ResultEnum.APP_UNABLE_DELETE.getMsg());
        }
        if(pCount>0){
            throw new BusException(ResultEnum.APP_UNABLE_DELETE.getMsg());
        }
        //如果没有使用过，可以删除
        appCompanyMapper.delete(id);
    }

    /**
     * <b>功能描述：</b>厂商编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void edit(AppCompany appCompany) {
        int count = appCompanyMapper.getByNameNotId(appCompany.getName(),appCompany.getId());
        if (count>0) {
            throw new BusException(appCompany.getName()+"厂商名称已经存在");
        }
        this.update(appCompany);
    }

    @Override
    protected GenericMapper<AppCompany> getGenericMapper() {
        return appCompanyMapper;
    }
}
