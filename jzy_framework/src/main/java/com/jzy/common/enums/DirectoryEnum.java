package com.jzy.common.enums;

/**
 * @ClassNameName DirectoryEnum
 * @Description oss目录
 * @Author jiazhiyi
 * @DATE 2019/5/13
 * @Version 1.0
 **/
public enum DirectoryEnum {
    //商品相关
    DIRECTORY_APP_ENUM(1, "app"),
    //渠道商相关
    DIRECTORY_DEALER_ENUM(2, "dealer"),
    //轮播图、首页分类相关
    DIRECTORY_RCI_ENUM(3, "rci"),
    //首页推荐
    DIRECTORY_RECOMMEND_ENUM(4, "recommend"),
    //反馈
    DIRECTORY_FEEDBACK_ENUM(5, "feedback")
    ;

    private int code;

    private String msg;

    DirectoryEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
