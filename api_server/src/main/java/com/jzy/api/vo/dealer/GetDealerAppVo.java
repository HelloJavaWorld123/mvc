package com.jzy.api.vo.dealer;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <b>功能：</b>渠道商商品定价定价列表返回接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class GetDealerAppVo {

    /**
     * sup编号
     */
    private String supNo;
    /**
     * 商品编号
     */
    private String code;


    /**
     * 商品名称
     */
    private String appName;

    /**
     * 产品类型
     */
    private String cateName;

    /**
     * 商品分类
     */
    private String typeName;
    /**
     * 厂商
     */
    private String companyName;

    /**
     * 折扣
     */

    private String discount;


    /**
     * 商品状态：启用或者禁用
     */
    private Integer appStatus;


    /**
     * 渠道商商品状态：上架或者下架   为空则未配置
     */
    private String dealerAppStatus;


    /**
     * 商品Id
     *
     * @return
     */
    private String aiId;

    public String getSupNo() {
        if (null != supNo && null != code && null != discount) {
            if ((supNo.equals(code)) && (code.equals(discount))) {
                return "/";
            }
        }
        Set<String> strings = getSet(supNo);
        return StringUtils.join(strings.toArray(), ",");
    }

    public String getDiscount() {
        if (null != supNo && null != code && null != discount) {
            if ((supNo.equals(code)) && (code.equals(discount))) {
                return "/";
            }
        }
        Set<String> strings = getSet(discount);
        if (strings.size() > 1) {
            return "/";
        } else {
            return strings.iterator().next();
        }
    }

    public String getDealerAppStatus() {
        if (null != supNo && null != code && null != discount) {
            if ((supNo.equals(code)) && (code.equals(discount))) {
                return null;
            }
        }
        return dealerAppStatus;
    }

    /**
     * <b>功能描述：</b>去除重复数据<br>
     * <b>修订记录：</b><br>
     * <li>20190510&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Set<String> getSet(String target) {
        String[] array = target.split(",");
        Set<String> strings = new HashSet<>();
        for (String s : array) {
            strings.add(s);
        }
        return strings;
    }

    public String getTypeName() {
        if (typeName.equals("x")) {
            return "";
        }
        return typeName;
    }
}
