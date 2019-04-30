package com.jzy.api.service.app.impl;

import com.jzy.api.service.app.IMongoService;
import com.jzy.api.util.CommUtils;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.exception.BusException;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * MongoDB服务
 * <p>
 * Created by IntelliJ IDEA. @Date 2019-01-02
 *
 * @Author GuoBing.Zh
 */
@Service
public class MongoServiceImpl implements IMongoService {

    private static Logger logger = Logger.getLogger(MongoServiceImpl.class);

    @Resource
    private GridFsTemplate gridFsTemplate;

    @Override
    public String uploadFile(MultipartFile mfile) {
        String ex = null;
        try {
            ex = CommUtils.lowerUUID().concat(mfile.getOriginalFilename().substring(mfile.getOriginalFilename().lastIndexOf(".")));

            ObjectId objectId = gridFsTemplate.store(mfile.getInputStream(), ex, mfile.getContentType());

            logger.debug("：：：Mongo Server - ".concat(Thread.currentThread().getStackTrace()[1].getMethodName()).concat(" - { ObjectId:").concat(objectId.toString()).concat(" }"));
        } catch (IOException e) {
            logger.error("：：：Err - MongoDB存储异常", e);

            throw new BusException(ResultEnum.MONGO_ERR.getMsg());
        }

        return ex;
    }

    @Override
    public String uploadFile(File file) {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);

            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            return uploadFile(multipartFile);
        } catch (IOException e) {
            logger.error("：：：Err - MongoDB存储异常", e);
            throw new BusException(ResultEnum.MONGO_ERR.getMsg());
        }
    }

    @Override
    public boolean deleteFile(String filename) {
        try {
            gridFsTemplate.delete(creat(filename));
        } catch (Exception e) {
            logger.error("：：：Err - MongoDB删除异常", e);

            throw new BusException(ResultEnum.MONGO_ERR.getMsg());
        }

        return true;
    }

    @Override
    public GridFsResource downFile(String filename) {
        GridFsResource resource = null;
        try {
            resource = gridFsTemplate.getResource(filename);
        } catch (Exception e) {
            logger.error("：：：Err - MongoDB查询异常", e);

            throw new BusException(ResultEnum.MONGO_ERR.getMsg());
        }

        return resource;
    }

    private Query creat(String filename) {
        return query(GridFsCriteria.whereFilename().is(filename));
    }
}