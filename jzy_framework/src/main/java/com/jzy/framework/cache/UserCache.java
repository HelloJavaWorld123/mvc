package com.jzy.framework.cache;

import lombok.Data;

@Data
public class UserCache {

    private String userId;

    private Integer dealerId;

    public UserCache() {
    }

    public UserCache(String userId, Integer dealerId) {
        this.userId = userId;
        this.dealerId = dealerId;
    }
}
