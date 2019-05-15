package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppAccttypeCnd;
import com.jzy.api.cnd.app.AppAccttypeListCnd;
import com.jzy.api.dao.app.AppAccttypeMapper;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.model.app.AppAccttype;
import com.jzy.api.po.app.AppAccttypeListPo;
import com.jzy.api.service.app.AppAccttypeService;
import com.jzy.api.vo.app.AppAccttypeVo;
import com.jzy.api.vo.app.AppCompanyVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * <b>功能：</b>应用账号类型<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class AppAccttypeServiceImpl extends GenericServiceImpl<AppAccttype> implements AppAccttypeService {

    @Resource
    private AppAccttypeMapper appAccttypeMapper;

    @Resource
    private AppInfoMapper appInfoMapper;

    /**
     * <b>功能描述：</b>账号类型列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<AppAccttypeListPo> list() {
        return  appAccttypeMapper.list();
    }

    /**
     * <b>功能描述：</b>账号类型添加<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(AppAccttype appAccttype) {
        try {
            int count = appAccttypeMapper.getByName(appAccttype.getMainName(), appAccttype.getSubName());
            if (count > 0) {
                throw new BusException(appAccttype.getMainName() + "账号类型名称已经存在");
            }
            appAccttype.setMainRegular(URLDecoder.decode(appAccttype.getMainRegular(), "UTF-8"));
            appAccttype.setSubRegular(URLDecoder.decode(appAccttype.getSubRegular(), "UTF-8"));
            this.insert(appAccttype);
        }catch (Exception e){
            throw new BusException("保存正则解码错误!");
        }
    }

    /**
     * <b>功能描述：</b>账号类型删除<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void delete(Long id) {
        int count =  appInfoMapper.getCountByAcctTypeId(id);
        if(count>0){
            throw new BusException(ResultEnum.APP_UNABLE_DELETE.getMsg());
        }
        appAccttypeMapper.delete(id);
    }

    /**
     * <b>功能描述：</b>账号类型编辑<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void edit(AppAccttype appAccttype) {
        try {
            int count = appAccttypeMapper.getByNameNoId(appAccttype.getMainName(), appAccttype.getSubName(),appAccttype.getId());
            if (count > 0) {
                throw new BusException(appAccttype.getMainName() + "账号类型名称已经存在");
            }
            appAccttype.setMainRegular(URLDecoder.decode(appAccttype.getMainRegular(), "UTF-8"));
            appAccttype.setSubRegular(URLDecoder.decode(appAccttype.getSubRegular(), "UTF-8"));
            this.update(appAccttype);
        }catch (Exception e){
            throw new BusException("保存正则解码错误!");
        }
    }

    /**
     * <b>功能描述：</b>分页查询账号类型<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(AppAccttypeListCnd appAccttypeListCnd) {
        Integer page = appAccttypeListCnd.getPage();
        Integer limit = appAccttypeListCnd.getLimit();
        Page<AppAccttypeVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<AppAccttypeVo> appAcctTypeList = appAccttypeMapper.listPage(appAccttypeListCnd);
        PageVo<AppAccttypeVo> pageVo = new PageVo<>(appAcctTypeList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    @Override
    protected GenericMapper<AppAccttype> getGenericMapper() {
        return appAccttypeMapper;
    }
}
