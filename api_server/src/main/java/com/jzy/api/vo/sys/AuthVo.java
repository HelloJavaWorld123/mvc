package com.jzy.api.vo.sys;

import com.jzy.framework.bean.vo.GenericVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>权限返回参数<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190428&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class AuthVo extends GenericVo {
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
    private List<AuthVo> children = new ArrayList<>();
}
