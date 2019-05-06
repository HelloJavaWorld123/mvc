package com.jzy.api.controller.home;

import com.jzy.api.cnd.home.CommonDeleteBatchCnd;
import com.jzy.api.cnd.home.CommonUpdateStatusCnd;
import com.jzy.api.cnd.home.DealerHomeCateListCnd;
import com.jzy.api.cnd.home.DealerHomeCateSaveCnd;
import com.jzy.api.constant.HomeEnums;
import com.jzy.api.model.Home.HomeRecommendCate;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.service.app.IMongoService;
import com.jzy.api.service.home.DealerHomeCateService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.vo.home.DealerHomeCateVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * <b>功能：后台管理轮播图</b>
 * add by jiazk 2019.05.01
 */
@Controller
@ResponseBody
@RequestMapping("ManageCarousel")
public class ManageCarouselController {

    private final static Logger logger = LoggerFactory.getLogger(ManageCarouselController.class);

    @Resource
    private DealerHomeCateService dealerHomeCateService;

    @Resource
    private SysImagesService sysImagesService;

    @Resource
    private IMongoService iMongoService;

    @Resource
    private TableKeyService tableKeyService;

    @RequestMapping("index")
    public ApiResult index(@RequestBody DealerHomeCateListCnd listCnd) {
        PageVo<DealerHomeCateVo> result = new PageVo<>();
        try {
            // 给查询类型赋默认值，0 轮播图类型 1 首页分类
            listCnd.setType(HomeEnums.HomeCateType.carousel.ordinal());
            result.setTotalCount(dealerHomeCateService.listPageCount(listCnd));
            result.setRows(dealerHomeCateService.listPage(listCnd));
        } catch (Exception e) {
            logger.error("admin轮播图列表异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(result);
    }

    @RequestMapping("getAppInfo")
    public ApiResult getAppInfo(@RequestBody IdCnd idCnd) {
        DealerHomeCateVo detailVo;
        try {
            detailVo = dealerHomeCateService.getById(idCnd.getId());
        } catch (Exception e) {
            logger.error("admin-app_info_id={},获取基础商品详情异常:{}", idCnd.getId(), e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(detailVo);
    }

    @RequestMapping("save")
    public ApiResult save(@RequestBody DealerHomeCateSaveCnd infoCnd) {
        try {

            FileInfo mfile = null;
            if (null != infoCnd.getFileInfo()) {
                mfile = infoCnd.getFileInfo();
            }

            //保存图片信息
            if (!StringUtils.isEmpty(infoCnd.getId())) {//更新操作时，先进行图片的删除操作
                SysImages imagesMapper = sysImagesService.getImageByaiId(infoCnd.getId());
                if (null != imagesMapper) {
                    iMongoService.deleteFile(imagesMapper.getFileUrl());
                }
            }

            HomeRecommendCate HRC = new HomeRecommendCate();
            HRC.setRciName(infoCnd.getRciName());
            HRC.setSortDesc(infoCnd.getSortDesc());
            HRC.setDealerId(infoCnd.getDealerId());
            HRC.setGoId(infoCnd.getGoId());
            HRC.setGoName(infoCnd.getGoName());
            HRC.setGoType(infoCnd.getGoType());
            // 给查询类型赋默认值，0 轮播图类型 1 首页分类
            HRC.setType(HomeEnums.HomeCateType.carousel.ordinal());
            HRC.setState(infoCnd.getState());
            if (StringUtils.isEmpty(infoCnd.getId())) {//新增操作
                infoCnd.setId(tableKeyService.newKey("home_recommend_cate", "id", 0));
                HRC.setId(infoCnd.getId());
                dealerHomeCateService.insert(HRC);

                //图片新增
                if (null != mfile) {
                    sysImagesService.save(getSystemImagesMapper(infoCnd, mfile));
                }
            } else {
                HRC.setId(infoCnd.getId());
                dealerHomeCateService.update(HRC);

                //图片修改
                if (null != mfile) {
                    sysImagesService.update(getSystemImagesMapper(infoCnd, mfile));
                }
            }
        } catch (Exception e) {
            logger.error("admin添加轮播图异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>();
    }

    private SysImages getSystemImagesMapper(DealerHomeCateSaveCnd infoCnd, FileInfo mfile) {
        return new SysImages(tableKeyService.newKey("home_recommend_cate", "id", 0), infoCnd.getId(), mfile.getFileOrignName(), mfile.getContentType(), infoCnd.getImageUrl(), HomeEnums.ImageType.category.ordinal());
    }

    @RequestMapping("updateStatusBatch")
    public ApiResult updateStatusBatch(@RequestBody CommonUpdateStatusCnd updateStatusBatchCnd) {
        try {
            dealerHomeCateService.updateStatusBatch(updateStatusBatchCnd);
        } catch (Exception e) {
            logger.error("admin-轮播图id{}修改状态{}错误,异常：{}", null, updateStatusBatchCnd.getStatus(), e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>();
    }

    @RequestMapping("deleteBatch")
    public ApiResult deleteBatch(@RequestBody CommonDeleteBatchCnd commonDeleteBatchCnd) {
        try {
            List<Long> ids = commonDeleteBatchCnd.getIds();
            for (Long id : ids) {
                DealerHomeCateVo entity = dealerHomeCateService.getById(id);
                if (entity.getStatus() != 0) {
                    throw new BusException("存在轮播图未禁用，不能进行批量删除！");
                }
            }
            dealerHomeCateService.deleteBatch(ids);
        } catch (Exception e) {
            logger.error("admin-轮播图id{}逻辑删除{}错误,异常：{}", null, 1, e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>();
    }
}
