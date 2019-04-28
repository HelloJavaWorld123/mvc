package com.jzy.framework.service.impl;

import com.jzy.framework.bean.model.GenericModel;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.GenericService;

import java.util.Date;

/**
 * <b>功能：</b>这里写功能描述<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190425&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public abstract class GenericServiceImpl<T extends GenericModel> implements GenericService<T> {

    /**
     * <b>功能描述：</b>新增<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int insert(T t) {
        t.setDelFlag(0);
        if (t.getCreatorId() == null) {
            t.setCreatorId(0L);
        }
        if (t.getModifierId() == null) {
            t.setModifierId(0L);
        }
        t.setCreateTime(new Date());
        t.setModifyTime(t.getCreateTime());
        return getGenericMapper().insert(t);
    }

    /**
     * <b>功能描述：</b>修改<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int update(T t) {
        t.setModifyTime(new Date());
        return getGenericMapper().update(t);
    }

    /**
     * <b>功能描述：</b>根据id查询详情<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public T queryById(Long id) {
        return getGenericMapper().queryById(id);
    }

    /**
     * <b>功能描述：</b>子类传递具体的mapper处理类<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    protected abstract GenericMapper<T> getGenericMapper();
}
