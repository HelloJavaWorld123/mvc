package com.jzy.framework.service.impl;

import com.jzy.framework.bean.model.GenericModel;
import com.jzy.framework.dao.BaseMapper;
import com.jzy.framework.service.BaseService;

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
public abstract class GenericBaseService<T extends GenericModel> implements BaseService<T> {

    /**
     * <b>功能描述：</b>新增<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int insert(T t) {
        t.setDelflg(0);
        t.setCreateTime(new Date());
        t.setModifyTime(t.getCreateTime());
        return getBaseMapper().insert(t);
    }

    /**
     * <b>功能描述：</b>修改<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int update(T t) {
        return getBaseMapper().update(t);
    }

    /**
     * <b>功能描述：</b>删除<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int delete(Long id, Integer dealerId) {
        return getBaseMapper().delete(id, dealerId);
    }

    /**
     * <b>功能描述：</b>根据id查询详情<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public T queryById(Long id, Integer dealerId) {
        return getBaseMapper().queryById(id, dealerId);
    }

    /**
     * <b>功能描述：</b>子类传递具体的mapper处理类<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    protected abstract BaseMapper<T> getBaseMapper();
}
