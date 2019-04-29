package com.jzy.api.model.biz;

import java.io.Serializable;
import java.util.Date;

public class SecurityToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String accessToken;// token凭证
    private int expiresIn;// 凭证有效时长 - 秒
    private String refreshToken;// 用于刷新凭证
    private String openId;// 用户标识
    private String scope;// 网页授权作用域
    private int region;// token定义 - 0：全局 1：网页
    private Date createTime;

    public SecurityToken() {
        super();
    }

    public SecurityToken(String id, String accessToken, int expiresIn, int region, Date createTime) {
        super();
        this.id = id;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.region = region;
        this.createTime = createTime;
    }

    public SecurityToken(String id, String accessToken, int expiresIn, String refreshToken, String openId, String scope,
                         int region, Date createTime) {
        super();
        this.id = id;
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.openId = openId;
        this.scope = scope;
        this.region = region;
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
