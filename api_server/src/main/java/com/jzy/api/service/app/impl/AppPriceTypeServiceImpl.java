package com.jzy.api.service.app.impl;

import com.jzy.api.cnd.app.SaveAppPriceTypeListCnd;
import com.jzy.api.dao.app.AppPriceTypeMapper;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.po.dealer.AppPriceTypeListPo;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>功能：</b>商品充值类型实现类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190418&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service("AppPriceTypeService")
public class AppPriceTypeServiceImpl extends GenericServiceImpl<AppPriceType> implements AppPriceTypeService {

    @Resource
    private AppPriceTypeMapper appPriceTypeMapper;

    @Resource
    private TableKeyService tableKeyService;

    /**
     * <b>功能描述：</b>充值类型批量操作<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void saveAppPriceTypeList(SaveAppPriceTypeListCnd saveAppPriceTypeListCnd) {
        Long aiId = saveAppPriceTypeListCnd.getAiId();
        //物理删除当前商品下的所有充值类型
        appPriceTypeMapper.deleteByAiId(aiId);
        for (AppPriceType appPriceType : saveAppPriceTypeListCnd.getAppPriceTypeList()) {
            appPriceType.setAiId(aiId);
            appPriceType.setId(tableKeyService.newKey("app_price_type", "id", 0));
            Date dateTime = new Date();
            appPriceType.setCreateTime(dateTime);
            appPriceType.setModifyTime(dateTime);
            appPriceTypeMapper.insert(appPriceType);
        }
    }


    /**
     * <b>功能描述：</b>查询当前商品充值类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public List<AppPriceTypeListPo> getAppPriceTypelist(String  aiId, String dealerId) {
        List<AppPriceTypeListPo> appPriceTypeMappers = appPriceTypeMapper.getAppPriceTypelist(aiId,dealerId);
        return appPriceTypeMappers;

    }

    /**
     * <b>功能描述：</b>查询当前商品充值类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public List<AppPriceType> getAppPriceTypelist(Long id) {
        List<AppPriceType> appPriceTypeMappers = appPriceTypeMapper.getAppPriceTypelistByaiId(id);
        return appPriceTypeMappers;

    }
    @Override
    protected GenericMapper<AppPriceType> getGenericMapper() {
        return null;
    }
}
