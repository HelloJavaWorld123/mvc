package com.jzy.api.service.app;

import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.cnd.app.UpdateStatusBatchCnd;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.po.app.AppInfoPo;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.ExcelException;
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
    void save(AppInfo appInfo) throws ExcelException;


    /**
     * <b>功能描述：</b>批量更新启用禁用状态<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void updateStatusBatch(UpdateStatusBatchCnd updateStatusBatchCnd);

    /**
     * <b>功能描述：</b>批量逻辑删除<br>
     * <b>修订记录：</b><br>
     * <li>20190418&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void deleteBatch(List<Long> newAiIds);


    /**
     * <b>功能描述：</b>查询商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo listPage(AppInfoListCnd appInfoListCnd);

    /**
     * <b>功能描述：</b>保存富文本信息<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void saveAppPage(AppPage AppPage);

    /**
     * <b>功能描述：</b>修改富文本类型<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void updateAppPage(AppPage AppPage);


    /**
     * <b>功能描述：</b>查询商品信息，用于确定是否能删除商品<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppInfo queryAppById(Long aiId);


    /**
     * <b>功能描述：</b>获取商品编号<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getMaxCode();


    /**
     * <b>功能描述：</b>商品名称重复校验<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void checkName(String name, String ai_id) throws ExcelException;


    /**
     * <b>功能描述：</b>根据商品名称获取商品Id<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    List<String> getIdByName(List<String> nameList);

}
