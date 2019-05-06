package com.jzy.api.service.app.impl;

import com.jzy.api.cnd.app.GetServInfoCnd;
import com.jzy.api.dao.app.AppGameMapper;
import com.jzy.api.model.app.AppGame;
import com.jzy.api.po.app.AppGameListPo;
import com.jzy.api.service.app.AppGameService;
import com.jzy.api.vo.app.AppGameListVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：</b>游戏<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class AppGameServiceImpl extends GenericServiceImpl<AppGame> implements AppGameService {


    @Resource
    private AppGameMapper appGameMapper;


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询区<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppGameListVo getAreaInfo(Long aiId) {
        List<AppGameListPo> appGameListPos = appGameMapper.getAreaInfo(aiId);
        return new AppGameListVo(appGameListPos);
    }

    /**
     * <b>功能描述：</b>前台渠道商对应商品查询服<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppGameListVo getServInfo(GetServInfoCnd getServInfoCnd) {
        List<AppGameListPo> appGameListPos = appGameMapper.getServInfo(getServInfoCnd.getAiId(),getServInfoCnd.getAreaId());
        return new AppGameListVo(appGameListPos);
    }

    @Override
    protected GenericMapper<AppGame> getGenericMapper() {
        return appGameMapper;
    }


}
