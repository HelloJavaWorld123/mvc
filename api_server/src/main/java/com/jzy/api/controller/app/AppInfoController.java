package com.jzy.api.controller.app;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.app.AppBatchDeleteCnd;
import com.jzy.api.cnd.app.AppInfoListCnd;
import com.jzy.api.cnd.app.SaveAppInfoCnd;
import com.jzy.api.cnd.app.UpdateStatusBatchCnd;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.oss.AliyunOssService;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.common.enums.DirectoryEnum;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 应用controller
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Controller
@ResponseBody
@RequestMapping("appInfo")
@Api(tags = "商品管理")
public class AppInfoController {

    private final static Logger logger = LoggerFactory.getLogger(AppInfoController.class);

    @Resource
    private AppInfoService appInfoService;

    @Resource
    private TableKeyService tableKeyService;

    @Resource
    private AliyunOssService aliyunOssService;


    /**
     * <b>功能描述：</b>商品列表分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * <li>2019.05.01&nbsp;&nbsp;|&nbsp;&nbsp;贾昭凯&nbsp;&nbsp;|&nbsp;&nbsp;修改SQL对于新数据库的错误，添加分页实体结构</li><br>
     */
    @RequestMapping("admin/index")
    @RequiresPermissions(value = {"a:appInfo:list"})
    @ApiOperation(httpMethod="POST" ,value = "分页查询")
    public ApiResult index(@RequestBody AppInfoListCnd appInfoListCnd) {
        PageVo<AppInfoListVo> result;
        try {
            result = appInfoService.listPage(appInfoListCnd);
        } catch (Exception e) {
            logger.error("admin产品列表异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(result);
    }

    /**
     * <b>功能描述：</b>获取商品详情<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/getAppInfo")
    @RequiresPermissions(value = {"a:appInfo:getAppInfo"})
    @ApiOperation(httpMethod="POST" ,value = "商品详情")
    public ApiResult getAppInfo(@RequestBody IdCnd idCnd) {
        AppInfoDetailVo appInfoDetailVo;
        try {
            appInfoDetailVo = appInfoService.getAppInfo(idCnd.getId());
        } catch (Exception e) {
            logger.error("admin-app_info_id={},获取基础商品详情异常:{}", idCnd.getId(), e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(appInfoDetailVo);
    }

    /**
     * <b>功能描述：</b>添加/更新商品操作<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param saveAppInfoCnd 商品对象信息
     */
    @Transactional
    @RequestMapping("admin/save")
    @RequiresPermissions(value = "a:appInfo:save")
    @ApiOperation(httpMethod="POST" ,value = "商品保存/更新")
    public ApiResult save(@RequestBody SaveAppInfoCnd saveAppInfoCnd) {
        appInfoService.saveAppInfo(saveAppInfoCnd);
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>商品批量修改状态<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/updateStatusBatch")
    @RequiresPermissions(value = "a:appInfo:updateStatusBatch")
    @ApiOperation(httpMethod="POST" ,value = "修改状态")
    public ApiResult updateStatusBatch(@RequestBody UpdateStatusBatchCnd updateStatusBatchCnd) {
        appInfoService.updateStatusBatch(updateStatusBatchCnd);
//        try {
//            appInfoService.updateStatusBatch(updateStatusBatchCnd);
//        } catch (Exception e) {
//            logger.error("admin-产品ai_id{}修改状态{}错误,异常：{}", null, updateStatusBatchCnd.getStatus(), e);
//            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
//        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>商品列表页面批量逻辑删除商品操作<br>
     * <b>修订记录：</b><br>
     * <li>20190418&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @Transactional
    @RequestMapping("admin/deleteBatch")
    @RequiresPermissions(value = "a:appInfo:deleteBatch")
    @ApiOperation(httpMethod="POST" ,value = "删除")
    public ApiResult deleteBatch(@RequestBody AppBatchDeleteCnd appBatchDeleteCnd) {
        appInfoService.delete(appBatchDeleteCnd);
        return new ApiResult<>();
    }


    /**
     * <b>功能描述：</b>图片的上传功能<br>
     * <b>修订记录：</b><br>
     * <li>20190422&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
//    @RequestMapping("admin/uploadFile")
//    public ApiResult uploadFile(@RequestParam(value = "file", required = false) MultipartFile mfile) {
//        String fileUrl = "";
//        try {
//            if (mfile != null) {
//                fileUrl = iMongoService.uploadFile(mfile);
//            }
//        } catch (Exception e) {
//            logger.error("图片上传失败！:{}", e);
//            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
//        }
//
//        return new ApiResult<>(new FileInfo(fileUrl, mfile.getOriginalFilename(), mfile.getContentType()));
//    }

    /**
     * oss 上传
     *
     * @param mfile
     * @return com.jzy.framework.result.ApiResult
     * @Description
     * @Author lchl
     * @Date 2019/5/13 11:13 AM
     */
    @WithoutLogin
    @RequestMapping("admin/uploadFile")
    @ApiOperation(httpMethod="POST" ,value = "oss后端上传通用方法")
    public ApiResult uploadFile(@RequestParam(value = "file", required = true) MultipartFile mfile
            , @RequestParam(value = "directoryType") Integer directoryType) {

        String fileUrl = "";

        try {

            if (null != mfile) {
                String filename = mfile.getOriginalFilename();
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(mfile.getBytes());
                    os.close();
                    mfile.transferTo(newFile);
                    //上传到OSS
                    if (directoryType == DirectoryEnum.DIRECTORY_APP_ENUM.getCode()) {
                        fileUrl = aliyunOssService.upload(newFile, DirectoryEnum.DIRECTORY_APP_ENUM.getMsg());
                    } else if (directoryType == DirectoryEnum.DIRECTORY_DEALER_ENUM.getCode()) {
                        fileUrl = aliyunOssService.upload(newFile, DirectoryEnum.DIRECTORY_DEALER_ENUM.getMsg());
                    } else if (directoryType == DirectoryEnum.DIRECTORY_RCI_ENUM.getCode()) {
                        fileUrl = aliyunOssService.upload(newFile, DirectoryEnum.DIRECTORY_RCI_ENUM.getMsg());
                    } else if (directoryType == DirectoryEnum.DIRECTORY_RECOMMEND_ENUM.getCode()) {
                        fileUrl = aliyunOssService.upload(newFile, DirectoryEnum.DIRECTORY_RECOMMEND_ENUM.getMsg());
                    } else if (directoryType == DirectoryEnum.DIRECTORY_FEEDBACK_ENUM.getCode()) {
                        fileUrl = aliyunOssService.upload(newFile, DirectoryEnum.DIRECTORY_FEEDBACK_ENUM.getMsg());
                    }else if (directoryType == DirectoryEnum.DIRECTORY_APPDETAIL_ENUM.getCode()) {
                        fileUrl = aliyunOssService.upload(newFile, DirectoryEnum.DIRECTORY_APPDETAIL_ENUM.getMsg());
                    } else {
                        logger.error("图片上传失败！:{}", "没有此目录");
                        return new ApiResult().fail(ResultEnum.PARAM_ERR.getMsg());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("图片上传失败！:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }

        return new ApiResult<>(new FileInfo(fileUrl, mfile.getOriginalFilename(), mfile.getContentType()));
    }

    /**
     * <b>功能描述：</b>获取图片信息<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param icon 商品图标地址名称（mongo地址）
     */
//    @WithoutLogin
//    @RequestMapping("admin/downFile")
//    public ApiResult downFile(@RequestParam(value = "icon") String icon, HttpServletResponse response) {
//        try {
//            this.downPicture(icon, response);
//        } catch (Exception e) {
//            logger.error("图片下载异常！:{}", e);
//            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
//        }
//        return new ApiResult<>();
//    }

    /**
     * <b>功能描述：</b>文件上传<br>
     * <b>修订记录：</b><br>
     * <li>20190422&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
//    private void downPicture(String icon, HttpServletResponse response) {
//        try {
//            GridFsResource ds = iMongoService.downFile(icon);
//            //设置response头信息
//            response.reset();
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-disposition", "attachment; filename=" + icon);
//            IOUtils.copy(ds.getInputStream(), response.getOutputStream());
//        } catch (IOException e) {
//            logger.error("文件上传失败！");
//            //e.printStackTrace();
//        }
//    }
}
