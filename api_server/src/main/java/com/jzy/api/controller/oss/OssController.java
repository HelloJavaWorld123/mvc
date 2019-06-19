package com.jzy.api.controller.oss;

import com.jzy.api.annos.WithoutLogin;
import com.jzy.api.service.oss.AliyunOssService;
import com.jzy.api.vo.oss.OssPolicyVo;
import com.jzy.common.enums.DirectoryEnum;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassNameName OssController
 * @Description TODO
 * @Author jiazhiyi
 * @DATE 2019/5/13
 * @Version 1.0
 **/
@Controller
@ResponseBody
@RequestMapping("aliyoss")
@Api(tags = "oss相关")
public class OssController {
    private final static Logger logger = LoggerFactory.getLogger(OssController.class);
    @Resource
    private AliyunOssService aliyunOssService;

    @WithoutLogin
    @RequestMapping("/getPolicy")
    @ApiOperation(httpMethod="POST" ,value = "获取oss数据")
    @ApiImplicitParam(name = "directoryType", value = "图片分类：1商品相关，2渠道相关，3轮播图首页分类，4首页推荐，5反馈，6多商品详情")
    public ApiResult getPolicy(@RequestParam(value = "directoryType") Integer directoryType){
        OssPolicyVo resultData = null;
        if(directoryType == DirectoryEnum.DIRECTORY_APP_ENUM.getCode()){
            resultData = aliyunOssService.getPolicy(DirectoryEnum.DIRECTORY_APP_ENUM.getMsg());
        }else if(directoryType == DirectoryEnum.DIRECTORY_DEALER_ENUM.getCode()){
            resultData = aliyunOssService.getPolicy(DirectoryEnum.DIRECTORY_DEALER_ENUM.getMsg());
        }else if(directoryType == DirectoryEnum.DIRECTORY_RCI_ENUM.getCode()){
            resultData = aliyunOssService.getPolicy(DirectoryEnum.DIRECTORY_RCI_ENUM.getMsg());
        }else if(directoryType == DirectoryEnum.DIRECTORY_RECOMMEND_ENUM.getCode()){
            resultData = aliyunOssService.getPolicy(DirectoryEnum.DIRECTORY_RECOMMEND_ENUM.getMsg());
        }else if(directoryType == DirectoryEnum.DIRECTORY_FEEDBACK_ENUM.getCode()){
            resultData = aliyunOssService.getPolicy(DirectoryEnum.DIRECTORY_FEEDBACK_ENUM.getMsg());
        }else{
            logger.error("获取policy", "没有此目录");
            return new ApiResult().fail(ResultEnum.PARAM_ERR.getMsg());
        }
        return new ApiResult<>(resultData);
    }
    @WithoutLogin
    @RequestMapping(path = "/ossCallBack")
    public void ossCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        aliyunOssService.ossCallBack(request,response);
    }
}
