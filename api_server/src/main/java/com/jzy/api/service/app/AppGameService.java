package com.jzy.api.service.app;

import com.jzy.api.cnd.app.AppGameCnd;
import com.jzy.api.cnd.app.GameListCnd;
import com.jzy.api.cnd.app.GetServInfoCnd;
import com.jzy.api.model.app.AppGame;
import com.jzy.api.po.app.AppGamePo;
import com.jzy.api.vo.app.AppGameListVo;
import com.jzy.api.vo.app.AppGameVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.GenericService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.jzy.common.enums.ResultEnum.OPERATION_FAILED;


/**
 * <b>功能：</b>游戏<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface AppGameService extends GenericService<AppGame> {

    /**
     * <b>功能描述：</b>前台渠道商对应商品查询区<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    AppGameListVo getAreaInfo(Long aiId);


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询服<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppGameListVo getServInfo(GetServInfoCnd getServInfoCnd);

    /**
     * <b>功能描述：</b>后台游戏列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<AppGamePo> getList(GameListCnd gameListCnd);

    /**
     * <b>功能描述：</b>后台游戏新增<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void save(AppGame appGame);

    /**
     * <b>功能描述：</b>后台游戏删除<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void delete(Long id);

    /**
     * <b>功能描述：</b>根据id获取appGame<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    AppGame getById(Long id);

    /**
     * <b>功能描述：</b>编辑游戏大区<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    void edit(AppGame appGame);

    /**
     * <b>功能描述：</b>游戏大区分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    PageVo<AppGameVo> listPage(AppGameCnd appGameCnd);
}
