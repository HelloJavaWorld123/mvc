package com.jzy.framework.service;

import java.io.OutputStream;
import java.io.Writer;

public interface JsonConverter {

    /**
     * <b>功能描述：</b>json字符串转换为相应类型<br>
     * <b>修订记录：</b><br>
     * <li>20131009&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    <T> T fromJson(String json, Class<T> resultType);

    /**
     * <b>功能描述：</b>Object转换为json字符串<br>
     * <b>修订记录：</b><br>
     * <li>20131009&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    String toJson(Object value);

    /**
     * <b>功能描述：</b>Object转换为json字符串, 写入到流<br>
     * <b>修订记录：</b><br>
     * <li>20131009&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    void toJson(OutputStream out, Object value);

    /**
     * <b>功能描述：</b>Object转换为json字符串, 写入到流<br>
     * <b>修订记录：</b><br>
     * <li>20131009&nbsp;&nbsp;|&nbsp;&nbsp;刘庆魁&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    void toJson(Writer out, Object value);

}
