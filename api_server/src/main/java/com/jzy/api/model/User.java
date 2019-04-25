package com.jzy.api.model;

import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

/**
 * Created by baozun on 2016/3/8.
 * 用户模型
 */
@Data
public class User extends GenericModel {

    private String user_id;
    private String username;//用户名
    private String password;//密码
    private String age;
    private String set;
    private String create_time;
    private String modified_time;
    private String state;
}
