package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.*;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.dao.app.AppPageMapper;
import com.jzy.api.dao.sys.SysImagesMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.app.AppPriceType;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.po.app.AppInfoPo;
import com.jzy.api.po.app.AppPriceTypeForDetailPo;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.util.HanyuPinyinUtil;
import com.jzy.api.util.RegexUtils;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
        public class AppInfoServiceImpl extends GenericServiceImpl<AppInfo> implements AppInfoService {

    @Resource
    private AppInfoMapper appInfoMapper;

    @Resource
    private AppPriceTypeService appPriceTypeService;

    @Resource
    private AppPageMapper appPageMapper;

    @Resource
    private SysImagesMapper sysImagesMapper;

    @Resource
    private TableKeyService tableKeyService;

    @Resource
    private SysImagesService sysImagesService;

    /**
     * <b>功能描述：</b>获取商品详细信息<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppInfoDetailVo getAppInfo(Long aiId) {

        AppInfoPo appInfoPo = appInfoMapper.getAppInfo(aiId);
        List<AppPriceTypeForDetailPo> appPriceTypeMappers = appPriceTypeService.getAppPriceTypelist(aiId);
        //获取商品富文本
        AppPage appPage = appPageMapper.getPageInfoByAiId(aiId);
        //获取多图片信息
        List<FileInfo> fileInfos = sysImagesMapper.queryImagesList(aiId.toString());
        AppInfoDetailVo appInfoDetailVo = new AppInfoDetailVo(appInfoPo, appPriceTypeMappers, appPage);
        appInfoDetailVo.setFileInfoList(fileInfos);
        return appInfoDetailVo;
    }

    /**
     * <b>功能描述：</b>查询商品信息<br>
     * <b>修订记录：</b><br>
     * <li>20190425&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public AppInfo getAppInfoMapper(Long id) {
        return appInfoMapper.queryById(id);
    }

    /**
     * <b>功能描述：</b>查询商品信息，用于确定是否能删除商品<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public AppInfo queryAppById(Long aiId) {
        return this.getAppInfoMapper(aiId);
    }

    /**
     * <b>功能描述：</b>商品信息保存<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(AppInfo appInfo){
        checkName(appInfo.getName(), null);
        this.insert(appInfo);
    }

    /**
     * <b>功能描述：</b>商品名称重复校验<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    public void checkName(String name, String ai_id) {
        List<AppInfo> list = listName(name, ai_id);
        if (list != null && list.size() > 0) {
            throw new BusException("商品名称重复：".concat(name));
        }
    }


    /**
     * <b>功能描述：</b>查询名称列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public List<AppInfo> listName(String name, String ai_id) {
        return appInfoMapper.listName(name, ai_id);
    }

    /**
     * <b>功能描述：</b>商品批量修改状态<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateStatusBatch(UpdateStatusBatchCnd updateStatusBatchCnd) {
        Integer status = updateStatusBatchCnd.getStatus();
        for (Long aiId : updateStatusBatchCnd.getAiIds()) {
            appInfoMapper.updateStatusBatch(aiId, status);
        }
    }

    /**
     * <b>功能描述：</b>批量逻辑删除<br>
     * <b>修订记录：</b><br>
     * <li>20190418&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void deleteBatch(List<Long> newAiIds) {
        for (Long aiId : newAiIds) {
            appInfoMapper.deleteBatch(aiId);
        }

    }

    /**
     * <b>功能描述：</b>获取商品编号<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public int getMaxCode() {

        return appInfoMapper.getMaxCode();

    }

    /**
     * <b>功能描述：</b>分页查询商品列表<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(AppInfoListCnd appInfoListCnd) {
        Integer page = appInfoListCnd.getPage();
        Integer limit = appInfoListCnd.getLimit();
        Page<AppInfoListVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<AppInfoListVo> appInfoListVoList = appInfoMapper.listPage(appInfoListCnd);
        PageVo<AppInfoListVo> pageVo = new PageVo<>(appInfoListVoList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    /**
     * <b>功能描述：</b>保存富文本内容<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void saveAppPage(AppPage appPage) {
        Date datetime = new Date();
        appPage.setCreateTime(datetime);
        appPage.setModifyTime(datetime);
        appPageMapper.insert(appPage);
    }


    /**
     * <b>功能描述：</b>根据商品名称获取商品Id<br>
     * <b>修订记录：</b><br>
     * <li>20190506&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    public List<String> getIdByName(List<String> nameList) {
        return appInfoMapper.getIdByName(nameList);
    }

    @Override
    public void saveAppInfo(SaveAppInfoCnd saveAppInfoCnd) {
        List<FileInfo> fileInfos=  saveAppInfoCnd.getFileInfoList();
        AppInfo ai = saveAppInfoCnd.getAppInfo();
        SaveAppPriceTypeListCnd saveAppPriceTypeListCnd = new SaveAppPriceTypeListCnd();
        //AppPage appPageMapper = saveAppInfoCnd.getAppPage();
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
            ai.setCode(String.valueOf(this.getMaxCode() + 1));
            this.save(ai);
            //图片新增
            if(fileInfos.size()>0){
                for (FileInfo mfile:fileInfos){
                    sysImagesService.save(getSystemImagesMapper(ai, mfile));
                }
            }
            //保存充值类型信息
            saveAppPriceTypeListCnd.setAiId(ai.getId());
            saveAppPriceTypeListCnd.setAppPriceTypeList(saveAppInfoCnd.getAppPriceTypeList());
            appPriceTypeService.saveAppPriceTypeList(saveAppPriceTypeListCnd);
            //保存富文本信息
//            appPageMapper.setAiId(ai.getId());
//            appInfoService.saveAppPage(appPageMapper);
        } else {//更新操作
            this.checkName(ai.getName(), ai.getId() + "");
            this.update(ai);
            //商品详情图片列表物理删除
            sysImagesService.deleteByRelId(ai.getId());
            //图片修改
            if(fileInfos.size()>0){
                for (FileInfo fileInfo:fileInfos){
                    SysImages sysImages = getSystemImagesMapper(ai, fileInfo);
                    // Integer flag = sysImagesService.update(sysImages);
                    //if (flag == 0) {
                    sysImagesService.save(sysImages);
                    // }
                }
            }

            //保存充值类型信息
            saveAppPriceTypeListCnd.setAiId(ai.getId());
            saveAppPriceTypeListCnd.setAppPriceTypeList(saveAppInfoCnd.getAppPriceTypeList());
            appPriceTypeService.saveAppPriceTypeList(saveAppPriceTypeListCnd);
            //修改富文本信息
//            appPageMapper.setAiId(ai.getId());
//            appInfoService.updateAppPage(appPageMapper);
        }
    }

    @Override
    public void delete(AppBatchDeleteCnd appBatchDeleteCnd) {
        try {
            List<Long> newAiIds = appBatchDeleteCnd.getAiIds();
            for (Long aiId : newAiIds) {
                AppInfo appinfo = this.queryAppById(aiId);
                if (appinfo.getStatus() != 0) {
                    throw new BusException("存在商品未禁用，不能进行批量删除！");
                }
            }
            this.deleteBatch(newAiIds);
        } catch (Exception e) {
            throw new BusException("admin-产品ai_id{}逻辑删除{}错误,异常：{}");
        }
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
     * <b>功能描述：</b>修改富文本内容<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateAppPage(AppPage appPage) {
        //删除
        appPage.setModifyTime(new Date());
        if (appPageMapper.update(appPage) == 0) {
            this.saveAppPage(appPage);
        }
    }

    /**
     * <b>功能描述：</b>获取图片实体<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private SysImages getSystemImagesMapper(AppInfo ai, FileInfo mfile) {
        return new SysImages(tableKeyService.newKey("sys_images", "id", 0), ai.getId().toString(), mfile.getFileOrignName(), mfile.getContentType(), mfile.getFileUrl(), mfile.getType());
    }

    @Override
    protected GenericMapper<AppInfo> getGenericMapper() {
        return appInfoMapper;
    }
}
