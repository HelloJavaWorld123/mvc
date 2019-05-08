package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.GameListCnd;
import com.jzy.api.cnd.app.GetServInfoCnd;
import com.jzy.api.po.app.AppGamePo;
import com.jzy.api.service.app.AppGameService;
import com.jzy.api.vo.app.AppGameListVo;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.jzy.common.enums.ResultEnum.OPERATION_FAILED;


/**
 * <b>功能：</b>查询区和服<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("appGame")
public class AppGameController {

    private final static Logger logger = LoggerFactory.getLogger(AppGameController.class);

    @Resource
    private AppGameService appGameService;


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询区<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getAreaInfo")
    public ApiResult getAreaInfo(@RequestBody IdCnd idCnd) {
        AppGameListVo appGameListVo = appGameService.getAreaInfo(idCnd.getId());
        return new ApiResult(appGameListVo);
    }

    /**
     * <b>功能描述：</b>前台渠道商对应商品查询服<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("getServInfo")
    public ApiResult getServInfo(@RequestBody GetServInfoCnd getServInfoCnd) {
        AppGameListVo appGameListVo = appGameService.getServInfo(getServInfoCnd);
        return new ApiResult(appGameListVo);
    }

    /**
     * <b>功能描述：</b>后台游戏列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/getList")
    public ApiResult getList(@RequestBody GameListCnd gameListCnd) {
        List<AppGamePo> gameList;
        try {
            gameList = appGameService.getList(gameListCnd);
        } catch (Exception e) {
            logger.error("admin-AppGameMgtCOntrooler分页列表异常:{}", e);
            return new ApiResult(OPERATION_FAILED);
        }
        return new ApiResult<>(gameList);
    }


}
