package com.jzy.api.service.app;

import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.vo.app.AppInfoDetailVo;
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
    AppInfoDetailVo getAppInfo(String appInfoId);

    /**
     * 查询
     *
     * @param id 主键
     * @return {@link AppInfo}
     */
    AppInfo queryAppById(String id);

    /**
     * 查询
     *
     * @param name 名称
     * @return {@link AppInfo}
     */
    AppInfo getName(String name);

    List<AppInfo> listName(String name);

    /**
     * 查询商品列表
     *
     * @param statusNotEqual null：查询所有状态， not null:查询条件是不等于条件， 例子：2：删除，查询!=2的商品信息
     * @return List
     */
    List<AppInfo> list(Integer statusNotEqual);

    /**
     * 多个id查询数据
     *
     * @param ids id数组
     * @return List
     */
    List<AppInfo> list(String[] ids);

    /**
     * 新增商品
     *
     * @param appInfo {@link AppInfo}
     * @return true/false
     */
    Boolean save(AppInfo appInfo);

    /**
     * 更新商品状态
     *
     * @param aiId   商品id
     * @param status 修改的状态(0下架，1上架，2删除)
     * @return true/false
     */
    Boolean updateStatus(String aiId, Integer status);

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
     * 批量从SUP初始化商品
     *
     * @param values
     * @return true/false
     */
    Boolean saveBatchInit(List<Object[]> values);

    /**
     * 查询最大值code
     *
     * @return max_code
     */
    int getMaxCode();

    /**
     * 分页列表
     *
     * @param cateId       分类id
     * @param acpId        厂商id
     * @param typeId       类型id
     * @param rechargeMode 到账模式
     * @param status       状态
     * @param query        模糊查询条件（商品编号，商品名称）
     * @param pageNo       当前页
     * @param pageSize     页数据大小
     */
    void listPage(Integer cateId, Integer acpId, Integer typeId, Integer rechargeMode, Integer status, String query, int pageNo, int pageSize);

    /**
     * 清空redis缓存
     */
    void deleteRedisKey();


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
