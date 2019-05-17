package com.jzy.framework.controller;

import com.jzy.common.enums.ResultEnum;
import com.jzy.common.util.CglibBeanCopierUtils;
import com.jzy.framework.bean.vo.GenericVo;
import com.jzy.framework.cache.EmpCache;
import com.jzy.framework.cache.ThreadLocalCache;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.JsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>基础父类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超$&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public abstract class GenericController  {

    @Resource
    protected JsonConverter jsonConverter;

    /**
     * <b>功能描述：</b>获取后端渠道商信息<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    protected EmpCache getDealer() {
        EmpCache empCache = ThreadLocalCache.getContextHolder().getEmpCache();
        if (empCache == null) {
            throw new BusException(ResultEnum.SESSION_VALID.getMsg());
        }
        return empCache;
    }


    /**
     * <b>功能描述：</b>获取前台渠道商id<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    protected Integer getDealerId() {
        return getDealer().getDealerId();
    }

    /**
     * <b>功能描述：</b>model准换为Vo_对象转化<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    protected <T extends GenericVo> T convert(Object model, Class<T> vo) {
        try {
            T t = vo.newInstance();
            CglibBeanCopierUtils.copyProperties(model, t);
            return t;
        } catch (Exception e) {
            throw new BusException("对象转换异常", e);
        }
    }

    /**
     * <b>功能描述：</b>model准换为Vo_对象转化<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    protected <T extends GenericVo> List<T> convert(List<? extends Object> models, Class<T> vo) {
        if (models != null && models.size() > 0) {
            // 针对同一类型的集合进行优化，只获取一次对象，减少从Map中遍历的次数，提高效率
//            BeanCopier beanCopier = CglibBeanCopierUtils.getBeanCopier(models.get(0), vo);
            List<T> list = new ArrayList<>();
            for (Object model : models) {
                try {
                    if (model != null) {
                        T t = vo.newInstance();
                        CglibBeanCopierUtils.copyProperties(model, t);
                        // beanCopier.copy(model, t, null);
                        list.add(t);
                    }
                } catch (Exception e) {
                    throw new BusException("对象转换异常", e);
                }
            }
            return list;
        }
        return null;
    }

}
