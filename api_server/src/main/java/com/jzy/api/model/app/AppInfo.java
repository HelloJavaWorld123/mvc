package com.jzy.api.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jzy.framework.bean.model.GenericModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用表
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Data
public class AppInfo extends GenericModel implements Serializable {

    /**
     * 编号
     */
    private String code;
    /**
     * 分类id
     */
    private Integer cateId;
    /**
     * 账号类型id
     */
    private Integer acctId;
    /**
     * 厂商id
     */
    private Integer acpId;
    /**
     * 游戏id
     */
    private String gameId;
    /**
     * 应用类型id
     */
    private Integer typeId;

    /**
     * 应用名称
     */
    private String name;
    /**
     * 图标地址
     */
    private String icon;
    /**
     * 检索标签
     */
    private String label;
    /**
     * 首字母
     */
    private String firstLetter;
    /**
     * 全拼首字母
     */
    private String spllLetter;
    /**
     * 充值模式:0直充 1 卡密
     */
    private Integer rechargeMode;
    /**
     * 状态  0 下架 1上架  2删除
     */
    private Integer status = 0;

    /**
     * 排序
     */
    private Integer sort = 1000;
    /**
     * 应用描述
     */
    private String describe;
    /**
     * 页面地址
     */
    private String pagePath;

    /**
     * 备注
     */
    private String remark;



    /**
     * 是否热销   0为热销  1为不热销
     */
    private Integer isHot = 0;


    /**
     * 是否推荐 0为推荐  1为不推荐
     */
    private Integer isReco = 0;


}
