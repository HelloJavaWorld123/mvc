package com.jzy.api.dao.biz;

import com.jzy.api.model.biz.Order;
import com.jzy.framework.dao.GenericMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.math.BigDecimal;
import java.util.List;

/**
 * <b>功能：</b>订单处理mapper<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface OrderMapper extends GenericMapper<Order> {

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    List<Order> queryOrderList();

    /**
     * <b>功能描述：</b>根据订单id查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    String queryCardPwdById(Long id);

    /**
     * <b>功能描述：</b>根据订单id关闭订单<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    int updateOrderClose(Long id);

    /**
     * <b>功能描述：</b>删除订单<br>
     * <b>修订记录：</b><br>
     * <li>20190426&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     */
    int updateOrderDelFlag(Long id);

    /**
     * <b>功能描述：</b>更新订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int updateStatus(@Param("id") String id, @Param("status") Integer status);

    /**
     * <b>功能描述：</b>更新订单状态<br>
     * <b>修订记录：</b><br>
     * <li>20190429&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    int updateTradeStatus(@Param("id") String id, @Param("tradeStatus") Integer tradeStatus);

    /**
     * <b>功能描述：</b>更新支付状态<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     * @param status 订单状态0待支付,1充值中,2充值成功,3充值失败,4充值关闭
     * @param supStatus SUP返回的状态 sup状态，0未提交1已提交2成功3失败
     * @param tradeStatus 支付状态
     */
    int updateStatusTradeStatusSupStatus(String id, Integer status, String tradeStatus, Integer supStatus);

    int updateStatusTrade(String id, Integer status, String tradeCode, BigDecimal tradeFee, String tradeStatus);

    int updateStatusSupStatusTrade(String id, Integer status, Integer supStatus, String tradeCode, BigDecimal tradeFee, String tradeStatus);

    /**
     * <b>功能描述：</b>更新订单的支付方式，流水号，交易状态<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param id 订单id
     * @param status 订单状态；0待支付, 1充值中,   2充值成功,  3充值失败,  4充值关闭
     * @param tradeMethod 支付方式；0：微信；1：支付宝
     * @param outTradeNo 流水号
     */
    int updateStatusTradeMethod(String id, Integer status, Integer tradeMethod, String outTradeNo);




}
