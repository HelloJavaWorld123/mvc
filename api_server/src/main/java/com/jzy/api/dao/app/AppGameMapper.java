package com.jzy.api.dao.app;

import com.jzy.api.cnd.app.AppGameCnd;
import com.jzy.api.cnd.app.GameListCnd;
import com.jzy.api.cnd.app.GetServInfoCnd;
import com.jzy.api.model.app.AppGame;
import com.jzy.api.po.app.AppGameListPo;
import com.jzy.api.po.app.AppGamePo;
import com.jzy.api.vo.app.AppGameListVo;
import com.jzy.api.vo.app.AppGameVo;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
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
public interface AppGameMapper extends GenericMapper<AppGame> {


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询区<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> getAreaInfo(@Param("aiId") Long aiId);


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询服<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> getServInfo(@Param("aiId") String aiId, @Param("areaId") String areaId);

    /**
     * <b>功能描述：</b>校验当前商品是否存在区<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> checkAreaInfo(@Param("pid") String pId);

    /**
     * <b>功能描述：</b>校验当前商品是否存在服<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameListPo> checkServInfo(@Param("ids") List<String> ids);


    /**
     * <b>功能描述：</b>后台游戏列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGamePo> getList(GameListCnd gameListCnd);

    /**
     * <b>功能描述：</b>根据厂商id查询游戏数量<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getCountByPId(@Param("pId") Long pId);

    /**
     * <b>功能描述：</b>根据pid,name,type查询数量<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getByIdNameType(@Param("pId") String pId, @Param("name") String name, @Param("type") String type);

    /**
     * <b>功能描述：</b>根据pid查询游戏信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppGame getByPid(@Param("pId") String pId);

    /**
     * <b>功能描述：</b>根据id查询appgame<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppGame getById(@Param("id") Long id);

    /**
     * <b>功能描述：</b>根据pid下面所有appgame<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGame> getListByPid(@Param("pId") Long pId);

    /**
     * <b>功能描述：</b>根据id批量删除<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void deleteBatch(List<Long> pidList);

    /**
     * <b>功能描述：</b>根据name和id查询游戏大区数量<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int getByNameNotId(@Param("name") String name,@Param("id") Long id);

    /**
     * <b>功能描述：</b>游戏大区分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGameVo> listPage(AppGameCnd appGameCnd);
}
