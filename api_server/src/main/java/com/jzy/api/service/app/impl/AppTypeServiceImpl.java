package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppTypeCnd;
import com.jzy.api.cnd.app.AppTypeListCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.AppTypeMapper;
import com.jzy.api.model.app.AppType;
import com.jzy.api.po.app.AppTypePo;
import com.jzy.api.service.app.AppTypeService;
import com.jzy.api.vo.app.AppTypeVo;
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
 * <b>功能：</b>应用类型service<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class AppTypeServiceImpl extends GenericServiceImpl<AppType> implements AppTypeService {

@Resource
private AppTypeMapper appTypeMapper;

@Resource
private AppInfoMapper appInfoMapper;


    /**
     * <b>功能描述：</b>产品类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public List<AppTypePo> getList() {
        return appTypeMapper.getList();
    }

    /**
     * <b>功能描述：</b>产品类型分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(AppTypeListCnd appTypeListCnd) {
        Integer page = appTypeListCnd.getPage();
        Integer limit = appTypeListCnd.getLimit();
        Page<AppTypeVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<AppTypeVo> appInfoListVoList = appTypeMapper.listPage(appTypeListCnd);
        PageVo<AppTypeVo> pageVo = new PageVo<>(appInfoListVoList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    /**
     * <b>功能描述：</b>产品类型添加<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int save(AppType appType) {
        int count = appTypeMapper.getByName(appType.getName());
        if(count>0){
            throw new BusException(appType.getName()+"商品类型名称已经存在");
        }
        return this.insert(appType);
    }

    /**
     * <b>功能描述：</b>产品类型删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void delete(Long id) {
        int count =  appInfoMapper.getCountByTypeId(id);
        if(count>0){
            throw new BusException(ResultEnum.APP_UNABLE_DELETE.getMsg());
        }
        appTypeMapper.delete(id);
    }
    @Override
    protected GenericMapper<AppType> getGenericMapper() {
        return appTypeMapper;
    }

}
