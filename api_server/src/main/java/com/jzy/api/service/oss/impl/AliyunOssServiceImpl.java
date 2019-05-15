package com.jzy.api.service.oss.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.*;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.jzy.api.constant.OssProperties;
import com.jzy.api.service.oss.AliyunOssService;
import com.jzy.api.util.OssUtil;
import com.jzy.api.vo.oss.OssPolicyVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @ClassNameName AliyunOssServiceImpl
 * @Description TODO
 * @Author jiazhiyi
 * @DATE 2019/5/13
 * @Version 1.0
 **/
@Service
public class AliyunOssServiceImpl implements AliyunOssService {
    private Logger logger = LoggerFactory.getLogger(AliyunOssServiceImpl.class);
//    @Autowired
//    private OssProperties ossProperties;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.ossFileURIPre}")
    private String ossFileURIPre;
    @Value("${oss.ossBucketName}")
    private String bucketName;
    @Value("${oss.ossEndpoint}")
    private String endpoint;
    @Value("${oss.ossFilePathPre}")
    private String fileHost;
    @Value("${oss.callbackUrl}")
    private String callbackUrl;



    public  String upload(File file) throws IOException {

        logger.debug("=========>OSS文件上传开始："+file.getName());
//        String accessKeyId= ossProperties.getAccessKeyId();
//        String accessKeySecret=ossProperties.getAccessKeySecret();
//        String bucketName=ossProperties.getOssBucketName();
//        String fileHost=ossProperties.getOssFilePathPre();
//        String endpoint = ossProperties.getOssEndpoint();
        if(null == file){
            return null;
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String fileUrl = "";
        try {
            if(null!=fileHost &&!"".equals(fileHost)){
                fileUrl = fileHost +    "/"  + UUID.randomUUID().toString().replace("-", "").concat(file.getName().substring(file.getName().lastIndexOf(".")));
            }else{
                fileUrl = UUID.randomUUID().toString().replace("-", "").concat(file.getName().substring(file.getName().lastIndexOf(".")));
            }
            PutObjectResult objectResult = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            logger.debug(JSON.toJSONString(objectResult));
        } catch (OSSException oe) {
            oe.printStackTrace();
            logger.error("OSSException oss上传报错："+oe.getMessage());
        } catch (ClientException ce) {
            logger.error("ClientException oss上传报错："+ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return fileUrl;
    }
    public  String upload(File file,String directoryName){

        logger.debug("=========>OSS文件上传开始："+file.getName()+"---directoryName---"+directoryName);
//        String accessKeyId= ossProperties.getAccessKeyId();
//        String accessKeySecret=ossProperties.getAccessKeySecret();
//        String bucketName=ossProperties.getOssBucketName();
//        String endpoint = ossProperties.getOssEndpoint();
        if(null == file){
            return null;
        }
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String fileUrl = "";
        try {
            if(null!=directoryName &&!"".equals(directoryName)){
                fileUrl = directoryName +    "/"  + UUID.randomUUID().toString().replace("-", "").concat(file.getName().substring(file.getName().lastIndexOf(".")));
            }else{
                fileUrl = UUID.randomUUID().toString().replace("-", "").concat(file.getName().substring(file.getName().lastIndexOf(".")));
            }
            ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));

        } catch (OSSException oe) {
            oe.printStackTrace();
            logger.error("OSSException oss上传报错："+oe.getMessage());
        } catch (ClientException ce) {
            logger.error("ClientException oss上传报错："+ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return fileUrl;
    }

    public boolean delete(List<String> keys){

        logger.debug("=========>OSS文件删除开始");
        boolean flag = false;
//        String accessKeyId= ossProperties.getAccessKeyId();
//        String accessKeySecret=ossProperties.getAccessKeySecret();
//        String bucketName=ossProperties.getOssBucketName();
//        String endpoint = ossProperties.getOssEndpoint();
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));

            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            for (String object : deletedObjects) {
                logger.debug("oss删除文件："+object);
                flag = true;
            }
        } catch (OSSException oe) {
            oe.printStackTrace();
            logger.error("OSSException oss删除报错："+oe.getMessage());
        } catch (ClientException ce) {
            logger.error("ClientException oss删除报错："+ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
        return flag;
    }



    public void delete(String key){
        logger.debug("=========>OSS单文件删除开始");
//        String accessKeyId= ossProperties.getAccessKeyId();
//        String accessKeySecret=ossProperties.getAccessKeySecret();
//        String bucketName=ossProperties.getOssBucketName();
//        String endpoint = ossProperties.getOssEndpoint();
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 删除文件。
            ossClient.deleteObject(bucketName, key);
        } catch (OSSException oe) {
            oe.printStackTrace();
            logger.error("OSSException oss删除报错："+oe.getMessage());
        } catch (ClientException ce) {
            logger.error("ClientException oss删除报错："+ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }

    public OSSObject getOssFileObject(String fileKey){
        logger.debug("=========>OSS文件获取开始：");
//        String accessKeyId= ossProperties.getAccessKeyId();
//        String accessKeySecret=ossProperties.getAccessKeySecret();
//        String bucketName=ossProperties.getOssBucketName();
//        String endpoint = ossProperties.getOssEndpoint();

        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = client.getObject(bucketName,fileKey);
        return  ossObject;
    }

    @Override
    public OssPolicyVo getPolicy(String msg) {

        OssPolicyVo ossPolicyVo = new OssPolicyVo();

//        String accessKeyId= ossProperties.getAccessKeyId();
//        String accessKeySecret=ossProperties.getAccessKeySecret();
//        String endpoint = ossProperties.getOssEndpoint();
//        String ossFileURIPre = ossProperties.getOssFileURIPre();

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, msg+"/");

            String postPolicy = ossClient.generatePostPolicy(expiration,policyConds);

            byte[] binaryData = postPolicy.getBytes(Charset.forName("UTF-8"));
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            ossPolicyVo.setAccessid(accessKeyId);
            ossPolicyVo.setPolicy(encodedPolicy);
            ossPolicyVo.setSignature(postSignature);
            ossPolicyVo.setDir(msg);
            ossPolicyVo.setHost(ossFileURIPre);
            ossPolicyVo.setExpire(String.valueOf(expireEndTime / 1000));

            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody","filename=${object}&size=${size}&mimeType=${mimeType}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());

            ossPolicyVo.setCallback(base64CallbackBody);
        }finally {
            ossClient.shutdown();
        }
        return ossPolicyVo;
    }

    @Override
    public void ossCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ossCallbackBody = OssUtil.GetPostBody(request.getInputStream(),
                Integer.parseInt(request.getHeader("content-length")));
        boolean ret = OssUtil.VerifyOSSCallbackRequest(request, ossCallbackBody);
        System.out.println("verify result : " + ret);
        // System.out.println("OSS Callback Body:" + ossCallbackBody);
        if (ret) {
            OssUtil.response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
        } else {
            OssUtil.response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
