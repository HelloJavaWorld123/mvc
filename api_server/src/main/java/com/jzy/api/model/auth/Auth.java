package com.jzy.api.model.auth;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Auth<T> {
    /**
     * 节点ID
     */
    private Long id;
    /**
     * 父节点ID
     */
    private Long pId;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点URL
     */
    private String url;
    /**
     * 是否有父节点
     */
    private Boolean hasParent;
    /**
     * 是否有子节点
     */
    private Boolean hasChildren;
    /**
     * 子节点信息
     */
    private List<Auth<T>> children = new ArrayList<>();
}
