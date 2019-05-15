package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.AppGameCnd;
import com.jzy.api.cnd.app.AppGameListCnd;
import com.jzy.api.cnd.app.GameListCnd;
import com.jzy.api.cnd.app.GetServInfoCnd;
import com.jzy.api.model.app.AppGame;
import com.jzy.api.po.app.AppGamePo;
import com.jzy.api.service.app.AppGameService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyStringUtil;
import com.jzy.api.vo.app.AppGameListVo;
import com.jzy.api.vo.app.AppGameVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

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

    @Resource
    private TableKeyService tableKeyService;


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

    /**
     * <b>功能描述：</b>游戏大区分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/index")
    public ApiResult index(@RequestBody AppGameListCnd appGameListCnd) {
        PageVo<AppGameVo> result;
        try {
            result = appGameService.listPage(appGameListCnd);
        } catch (Exception e) {
            logger.error("admin游戏大区分页查询异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(result);
    }

    /**
     * <b>功能描述：</b>根据厂商添加游戏<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/save")
    public ApiResult save(@RequestBody AppGameCnd appGameCnd) {
        try {
            AppGame appGame = new AppGame();
            BeanUtils.copyProperties(appGameCnd,appGame);
            appGame.setId(tableKeyService.newKey("app_game", "id", 0));
            appGameService.save(appGame);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin厂商下面的游戏:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>游戏，大区，服务，物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/delete")
    public ApiResult deleteBatch(@RequestBody IdCnd idCnd) {
        try {
            appGameService.delete(idCnd.getId());
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin游戏大区服务删除:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>编辑游戏大区<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/update")
    public ApiResult update(@RequestBody AppGameCnd appGameCnd) {
        try {
            AppGame appGame = appGameService.getById(appGameCnd.getId());
            if (Objects.isNull(appGame)) {
                return new ApiResult(ResultEnum.APP_EMPTY);
            }
            appGame.setName(appGameCnd.getName());
            appGameService.edit(appGame);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin游戏大区编辑:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

}
