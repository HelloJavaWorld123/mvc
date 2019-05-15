package com.jzy.api.service.oss;

import com.aliyun.oss.model.OSSObject;
import com.jzy.api.vo.oss.OssPolicyVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface AliyunOssService {
    /** oss文件上传
     * @Description
     * @Author lchl
     * @Date 2019/5/6 4:52 PM
     * @param file
     * @return java.lang.String
     */
    public  String upload(File file) throws IOException;
    /** oss文件上传
     * @Description
     * @Author lchl
     * @Date 2019/5/13 11:48 AM
     * @param newFile
     * @param directoryName 目录
     * @return java.lang.String
     */
    String upload(File newFile, String directoryName);

    /**删除文件 暂时没权限
     * @Description You have no right to access this object because of bucket acl.
     * @Author lchl
     * @Date 2019/5/6 5:19 PM
     * @param keys
     * @return boolean
     */
    boolean delete(List<String> keys);
    /** 获取文件
     * @Description
     * @Author lchl
     * @Date 2019/5/6 5:20 PM
     * @param fileKey
     * @return com.aliyun.oss.model.OSSObject
     */
    public OSSObject getOssFileObject(String fileKey);


    public void delete(String fileUrl);
    /** 获取oss policy信息
     * @Description
     * @Author lchl
     * @Date 2019/5/13 4:16 PM
     * @param msg
     * @return com.jzy.api.vo.oss.OssPolicyVo
     */
    public OssPolicyVo getPolicy(String msg);
    /**oss回调
     * @Description
     * @Author lchl
     * @Date 2019/5/13 4:37 PM
     * @param request
     * @param response
     * @return void
     */
    public void ossCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException;

    String uploadFile(InputStream in, long length, String fileName, String extName);
}
