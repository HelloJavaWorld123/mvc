package com.jzy.api.service.app.impl;

import com.jzy.api.cnd.app.SaveAppPriceTypeListCnd;
import com.jzy.api.dao.app.AppPriceTypeMapper;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        // jdbcTemplate.update(sqlMap("app_price_type.delete"), aiId);
//        List<Object[]> objects = new ArrayList<>(10);
//        for (AppPriceType appPriceTypeMapper : saveAppPriceTypeListCnd.getAppPriceTypeMapperList()) {
//            appPriceTypeMapper.setAiId(aiId);
//            appPriceTypeMapper.setId(CommUtils.uniqueOrderStr());
//            //`id`, `ai_id`, `name`, `unit`, `maxmum`, `minmum`, `multiple`, `subscription_ratio`, `gmt_create`, `gmt_modified`, `modifier_id`, creator_id, delflag
//            objects.add(new Object[]{appPriceTypeMapper.getId(), saveAppPriceTypeListCnd.getAiId(),
//                    appPriceTypeMapper.getName(), appPriceTypeMapper.getUnit(), appPriceTypeMapper.getMaxmum(),
//                    appPriceTypeMapper.getMinmum(), appPriceTypeMapper.getMultiple(),
//                    appPriceTypeMapper.getSubscriptionRatio(), appPriceTypeMapper.getGmtCreate(),
//                    appPriceTypeMapper.getGmtModified(), appPriceTypeMapper.getModifierId(),
//                    appPriceTypeMapper.getCreatorId(),
//                    appPriceTypeMapper.getDelflag()
//            });
//        }
//        this.batchSql(sqlMap("app_price_type.save"), objects);

    }


    /**
     * <b>功能描述：</b>查询当前商品充值类型列表<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public List<AppPriceType> getAppPriceTypelist(Long id) {
        List<AppPriceType> appPriceTypeMappers = appPriceTypeMapper.getAppPriceTypelist(id);
        return appPriceTypeMappers;

    }

    @Override
    protected GenericMapper<AppPriceType> getGenericMapper() {
        return null;
    }
}
