package com.jzy.api.controller.home;

import com.jzy.api.cnd.home.HomeHotGroupCnd;
import com.jzy.api.cnd.home.HomeRecommendHotGroupCnd;
import com.jzy.api.model.Home.HomeRecommendHotGroup;
import com.jzy.api.service.home.HomeRecommendHotGroupService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.util.DateUtils;
import com.jzy.api.vo.home.HomeHotGroupVo;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.jzy.common.enums.ResultEnum.OPERATION_FAILED;

/**
 * <b>功能：</b>渠道商首页推荐Hot<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;鲁伟&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("homeRecommendHotGroup")
public class HomeRecommendHotGroupController {

    private final static Logger logger = LoggerFactory.getLogger(HomeRecommendHotGroupController.class);

    //@Resource
   // private IDealerService iDealerService;

    @Resource
    private HomeRecommendHotGroupService homeRecommendHotGroupService;

    @Resource
    private TableKeyService tableKeyService;

    /**
     * <b>功能描述：</b>首页推荐分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/index")
    public ApiResult index(@RequestBody HomeHotGroupCnd homeHotGroupCnd) {
        PageVo<HomeHotGroupVo> result;
        try {
            result = homeRecommendHotGroupService.listPage(homeHotGroupCnd);
        } catch (Exception e) {
            logger.error("admin首页推荐分组分页查询异常:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>(result);
    }

    /**
     * <b>功能描述：</b>首页推荐分组添加<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/save")
    public ApiResult save(@RequestBody HomeRecommendHotGroupCnd homeRecommendHotGroupCnd) {
        try {
            HomeRecommendHotGroup homeRecommendHotGroup = new HomeRecommendHotGroup();
            BeanUtils.copyProperties(homeRecommendHotGroupCnd,homeRecommendHotGroup);
            homeRecommendHotGroup.setId(tableKeyService.newKey("home_recommend_hot_group", "id", 0));
            homeRecommendHotGroupService.save(homeRecommendHotGroup);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin首页推荐分组添加错误:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>首页推荐分组，物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/delete")
    public ApiResult deleteBatch(@RequestBody IdCnd idCnd) {
        try {
            homeRecommendHotGroupService.delete(idCnd.getId());
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin首页推荐分组删除:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>首页推荐分组<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/update")
    public ApiResult update(@RequestBody HomeRecommendHotGroupCnd homeRecommendHotGroupCnd) {
        try {
            HomeRecommendHotGroup homeRecommendHotGroup = new HomeRecommendHotGroup();
            BeanUtils.copyProperties(homeRecommendHotGroupCnd,homeRecommendHotGroup);
            homeRecommendHotGroup.setId(homeRecommendHotGroupCnd.getId());
            //homeRecommendHotGroup.setModifyTime(DateUtils.formatStrToDate(homeRecommendHotGroupCnd.getModifyTime(),DateUtils.DF_Y_M_D_H_M_S));
            homeRecommendHotGroupService.edit(homeRecommendHotGroup);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin首页推荐分组编辑错误:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>首页推荐分组停用启用<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/setState")
    public ApiResult setStatus(@RequestBody HomeRecommendHotGroupCnd homeRecommendHotGroupCnd) {
        try {
            homeRecommendHotGroupService.setStatus(homeRecommendHotGroupCnd);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin首页推荐分组启用停用:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }
}
