package com.jzy.api.service.app;

import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.framework.service.GenericService;

import java.util.List;
import java.util.Map;

/**
 * 应用业务层接口
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
public interface AppInfoService extends GenericService<AppInfo> {

    /**
     * 获取单个商品详情
     *
     * @param appInfoId 基础商品id
     */
    AppInfoDetailVo getAppInfo(Long appInfoId);


    /**
     * 新增商品
     *
     * @param appInfo {@link AppInfo}
     * @return true/false
     */
    Boolean save(AppInfo appInfo);

    /**
     * 批量更新状态
     *
     * @return
     */
    Boolean updateStatusBatch(List<Object[]> values);

    /**
     * <b>功能描述：</b>批量逻辑删除<br>
     * <b>修订记录：</b><br>
     * <li>20190418&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    Boolean deleteBatch(List<Object[]> values);



    /**
     * <b>功能描述：</b>查询商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppInfoListVo> listPage(AppInfoListCnd appInfoListCnd);

    /**
     * <b>功能描述：</b>保存富文本信息<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    Boolean saveAppPage(AppPage AppPage);

    /**
     * <b>功能描述：</b>修改富文本类型<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    Boolean updateAppPage(AppPage AppPage);

}
