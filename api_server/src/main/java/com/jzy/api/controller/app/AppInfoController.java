package com.jzy.api.controller.app;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.cnd.app.*;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.service.app.IMongoService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.oss.AliyunOssService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.util.HanyuPinyinUtil;
import com.jzy.api.util.RegexUtils;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.common.enums.DirectoryEnum;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.exception.ExcelException;
import com.jzy.framework.result.ApiResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 应用controller
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Controller
@ResponseBody
@RequestMapping("appInfo")
public class AppInfoController {

    private final static Logger logger = LoggerFactory.getLogger(AppInfoController.class);

    @Resource
    private AppPriceTypeService appPriceTypeService;

    @Resource
    private SysImagesService sysImagesService;

    @Resource
    private AppInfoService appInfoService;
    @Resource
    private IMongoService iMongoService;

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
    @RequestMapping("admin/save")
    public ApiResult save(@RequestBody SaveAppInfoCnd saveAppInfoCnd) throws ExcelException {
        FileInfo mfile = null;
        AppInfo ai = saveAppInfoCnd.getAppInfo();
        SaveAppPriceTypeListCnd saveAppPriceTypeListCnd = new SaveAppPriceTypeListCnd();
        AppPage appPageMapper = saveAppInfoCnd.getAppPage();
        if (null != saveAppInfoCnd.getFileInfo()) {
            mfile = saveAppInfoCnd.getFileInfo();
        }
        ai = verification(ai);
        //保存图片信息
            /*if (!StringUtils.isEmpty(ai.getId())) {//更新操作时，先进行图片的删除操作
                SysImages imagesMapper = sysImagesService.getImageByaiId(ai.getId());
                if (null != imagesMapper) {
                    //iMongoService.deleteFile(imagesMapper.getFileUrl());
                    aliyunOssService.delete(imagesMapper.getFileUrl());
                }
            }*/
        if (StringUtils.isEmpty(ai.getId())) {//新增操作
            ai.setId(tableKeyService.newKey("app_info", "id", 0));
            ai.setPagePath("");
            ai.setCode(String.valueOf(appInfoService.getMaxCode() + 1));
            appInfoService.save(ai);
            //图片新增
            if (null != mfile) {
                sysImagesService.save(getSystemImagesMapper(ai, mfile));
            }
            //保存充值类型信息
            saveAppPriceTypeListCnd.setAiId(ai.getId());
            saveAppPriceTypeListCnd.setAppPriceTypeList(saveAppInfoCnd.getAppPriceTypeList());
            appPriceTypeService.saveAppPriceTypeList(saveAppPriceTypeListCnd);
            //保存富文本信息
            appPageMapper.setAiId(ai.getId());
            appInfoService.saveAppPage(appPageMapper);
        } else {//更新操作
            appInfoService.checkName(ai.getName(), ai.getId() + "");
            appInfoService.update(ai);
            //图片修改
            if (null != mfile) {
                SysImages sysImages = getSystemImagesMapper(ai, mfile);
                Integer flag = sysImagesService.update(sysImages);
                if (flag == 0) {
                    sysImagesService.save(sysImages);
                }
            }
            //保存充值类型信息
            saveAppPriceTypeListCnd.setAiId(ai.getId());
            saveAppPriceTypeListCnd.setAppPriceTypeList(saveAppInfoCnd.getAppPriceTypeList());
            appPriceTypeService.saveAppPriceTypeList(saveAppPriceTypeListCnd);
            //修改富文本信息
            appPageMapper.setAiId(ai.getId());
            appInfoService.updateAppPage(appPageMapper);
        }
        return new ApiResult<>();
    }


    /**
     * <b>功能描述：</b>获取图片实体<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private SysImages getSystemImagesMapper(AppInfo ai, FileInfo mfile) {
        return new SysImages(tableKeyService.newKey("sys_images", "id", 0), ai.getId().toString(), mfile.getFileOrignName(), mfile.getContentType(), ai.getIcon(), 1);
    }

    /**
     * 新增、更新验证处理AppInfo
     *
     * @param ai {@link AppInfoMapper}
     * @return {@link AppInfoMapper}
     */
    private AppInfo verification(AppInfo ai) {
        if (!StringUtils.isEmpty(ai.getLabel())) {
            if (!RegexUtils.isMatch("^[A-Za-z0-9\\u4e00-\\u9fa5,，]{0,200}$", ai.getLabel())) {
                throw new BusException("标签关键词错误，只允许200内大小写字母、数字、中文、中英文\"，\"");
            }
            String label = ai.getLabel().replaceAll("\\s*", "").replaceAll("，", ",");
            List<String> labelList = Arrays.asList(label.split(","));
            labelList = labelList.parallelStream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
            label = String.join(",", labelList);
//            label = HanyuPinyinUtil.toHanyuPinyin(ai.getName()).concat(",").concat(label);
            ai.setLabel(label);
        }
        ai.setFirstLetter(HanyuPinyinUtil.getFirstLettersLo(ai.getName()));
        ai.setSpllLetter(HanyuPinyinUtil.getSpllLetterLo(ai.getName()));

        return ai;
    }

    /**
     * <b>功能描述：</b>商品批量修改状态<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/updateStatusBatch")
    public ApiResult updateStatusBatch(@RequestBody UpdateStatusBatchCnd updateStatusBatchCnd) {
        try {
            appInfoService.updateStatusBatch(updateStatusBatchCnd);
        } catch (Exception e) {
            logger.error("admin-产品ai_id{}修改状态{}错误,异常：{}", null, updateStatusBatchCnd.getStatus(), e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>商品列表页面批量逻辑删除商品操作<br>
     * <b>修订记录：</b><br>
     * <li>20190418&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/deleteBatch")
    public ApiResult deleteBatch(@RequestBody AppBatchDeleteCnd appBatchDeleteCnd) {
        try {
            List<Long> newAiIds = appBatchDeleteCnd.getAiIds();
            for (Long aiId : newAiIds) {
                AppInfo appinfo = appInfoService.queryAppById(aiId);
                if (appinfo.getStatus() != 0) {
                    throw new BusException("存在商品未禁用，不能进行批量删除！");
                }
            }
            appInfoService.deleteBatch(newAiIds);
        } catch (Exception e) {
            logger.error("admin-产品ai_id{}逻辑删除{}错误,异常：{}", null, 1, e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
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
    @RequestMapping("admin/uploadFile")
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
    @WithoutLogin
    @RequestMapping("admin/downFile")
    public ApiResult downFile(@RequestParam(value = "icon") String icon, HttpServletResponse response) {
        try {
            this.downPicture(icon, response);
        } catch (Exception e) {
            logger.error("图片下载异常！:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>文件上传<br>
     * <b>修订记录：</b><br>
     * <li>20190422&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void downPicture(String icon, HttpServletResponse response) {
        try {
            GridFsResource ds = iMongoService.downFile(icon);
            //设置response头信息
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + icon);
            IOUtils.copy(ds.getInputStream(), response.getOutputStream());
        } catch (IOException e) {
            logger.error("文件上传失败！");
            //e.printStackTrace();
        }
    }
}
