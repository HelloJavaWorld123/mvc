package com.jzy.api.dao.arch;

import com.jzy.api.model.dealer.DealerAppInfo;
import com.jzy.api.po.app.AppStatus;
import com.jzy.api.po.dealer.AppSearchPo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b>功能：</b>渠道商商品表<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface DealerAppInfoMapper extends GenericMapper<DealerAppInfo> {

    /**
     * <b>功能描述：</b>前台分页热搜查询<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppSearchPo> appSearchList(@Param("keyword") String keyword, @Param("dealerId") String dealerId);


    /**
     * <b>功能描述：</b>修改上下架状态<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
      int updateStatus(@Param("status")  Integer status, @Param("aiId")  String aiId, @Param("dealerId")  String dealerId);

      /**
     * <b>功能描述：</b>根据商品id查询商品是否存在<br>
     * <b>修订记录：</b><br>
     * <li>20190516&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param appId 商品i
     * @param dealerId 代理商id
     */
      AppStatus queryAppStatus(@Param("appId") Long appId, @Param("dealerId") Integer dealerId);

    /**
     *  查询定价商品面值中商品启用数量
     * @param status
     * @param aiId
     * @param dealerId
     * @return
     */
      int getStatusCount(@Param("status") Integer status, @Param("aiId") String aiId,@Param("dealerId") String dealerId);
}
