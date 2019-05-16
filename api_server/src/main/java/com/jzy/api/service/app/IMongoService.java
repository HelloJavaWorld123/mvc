//package com.jzy.api.service.app;
//
//import org.springframework.data.mongodb.gridfs.GridFsResource;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//
///**
// * MongoDB服务接口
// * <p>
// * Created by IntelliJ IDEA. @Date 2019-01-02
// *
// * @Author GuoBing.Zh
// */
//public interface IMongoService {
//
//    /**
//     * 存入文件
//     *
//     * @param mfile
//     * @return filename
//     */
//    String uploadFile(MultipartFile mfile);
//
//
//    /**
//     * 存入文件
//     *
//     * @param file
//     * @return filename
//     */
//    String uploadFile(File file);
//
//    /**
//     * 删除文件
//     *
//     * @param filename
//     * @return
//     */
//    boolean deleteFile(String filename);
//
//    /**
//     * 取出文件
//     *
//     * @param filename
//     * @return
//     */
//    GridFsResource downFile(String filename);
//}
