package com.jzy.api.service.sys.impl;

import com.jzy.api.dao.sys.SysImagesMapper;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <b>功能：</b>系统图片保存接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190420&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service("SysImagesService")
public class SysImagesServiceImpl extends GenericServiceImpl<SysImages> implements SysImagesService {
    private final static Logger logger = LoggerFactory.getLogger(SysImagesServiceImpl.class);

    @Resource
    private SysImagesMapper sysImagesMapper;


    /**
     * <b>功能描述：</b>添加图片<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(SysImages sysImages) {
        try {
            this.insert(sysImages);
        } catch (Exception e) {
            logger.error("图片保存失败:fileName = {}", "错误原因：{}", sysImages.getFileName(), e.getMessage());
        }
    }

    /**
     * <b>功能描述：</b>获取当前商品的图片信息<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public SysImages getImageByaiId(Long aiId) {
        return sysImagesMapper.getImageByaiId(aiId);
    }

    /**
     * <b>功能描述：</b>修改图片信息<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateSysImages(SysImages sysImages) {
        try {
            //删除图片
            sysImagesMapper.delete(sysImages.getRelId(), sysImages.getType());
            //保存图片
            this.save(sysImages);
        } catch (Exception e) {
            logger.error("图片更新失败:fileName = {}", "错误原因：{}", sysImages.getFileName(), e.getMessage());
        }


    }
    /**
     * <b>功能描述：</b>商品详情图片列表物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public Integer deleteByRelId(Long aiId) {
        return sysImagesMapper.deleteByRelId(aiId);
    }

    @Override
    protected GenericMapper<SysImages> getGenericMapper() {
        return sysImagesMapper;
    }
}
