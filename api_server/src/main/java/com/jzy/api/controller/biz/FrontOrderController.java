package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.CardPwdCnd;
import com.jzy.api.cnd.biz.CodeCnd;
import com.jzy.api.cnd.biz.OrderListCnd;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.vo.biz.CardPwdVo;
import com.jzy.api.vo.biz.FrontOrderVo;
import com.jzy.api.vo.biz.OrderDetailVo;
import com.jzy.api.vo.biz.StatusVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <b>功能：</b>前台订单相关操作<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(path = "/frontOrder")
public class FrontOrderController extends GenericController {

    @Resource
    private OrderService orderService;

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryOrderList")
    public ApiResult queryOrderList(@RequestBody OrderListCnd orderListCnd) {
        PageVo<FrontOrderVo> pageVo = orderService.queryFrontOrderList(orderListCnd.getPage(), orderListCnd.getLimit(),
                orderListCnd.getStatus());
        return new ApiResult<>(pageVo).success();
    }

    /**
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryOrderDetail")
    public ApiResult queryOrderDetail(@RequestBody CodeCnd codeCnd) {
        Order order = orderService.queryOrderDetail(codeCnd.getOrderId());
        return new ApiResult<>().success(getOrderDetailVo(order));
    }

    /**
     * <b>功能描述：</b>订单详情返回参数<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private OrderDetailVo getOrderDetailVo(Order order) {
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrderId(order.getOrderId());
        orderDetailVo.setOutTradeNo(order.getOutTradeNo());
        orderDetailVo.setTotalFee(order.getTotalFee());
        orderDetailVo.setTradeFee(order.getTradeFee());
        orderDetailVo.setPrice(order.getPrice());
        orderDetailVo.setTradeMethod(order.getTradeMethod());
        orderDetailVo.setPriceTypeName(order.getPriceTypeName());
        orderDetailVo.setStatus(order.getStatus());
        orderDetailVo.setAccount(order.getAccount());
        orderDetailVo.setType(order.getType());
        orderDetailVo.setAcctType(order.getAcctType());
        orderDetailVo.setNumber(order.getNumber());
        orderDetailVo.setPriceTypeUnit(order.getPriceTypeUnit());
        orderDetailVo.setCreateTime(order.getCreateTime());
        orderDetailVo.setPayTime(order.getPayTime());
        orderDetailVo.setFinishTime(order.getFinishTime());
        orderDetailVo.setAppId(order.getAppId() + "");
        orderDetailVo.setAppName(order.getAppName());
        orderDetailVo.setAppIcon(order.getAppIcon());
        orderDetailVo.setRechargeMode(order.getRechargeMode());
        orderDetailVo.setTradeStatus(order.getTradeStatus());
        if (order.getCardPwdList() != null && !order.getCardPwdList().isEmpty()) {
            orderDetailVo.setCardPwdList(convert(order.getCardPwdList(), CardPwdVo.class));
        }
        return orderDetailVo;
    }

    /**
     * <b>功能描述：</b>查询本地的支付状态<br>
     * <b>修订记录：</b><br>
     * <li>20190419&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("/queryOrderStatus")
    public ApiResult queryOrderStatus(@RequestBody CodeCnd codeCnd) {
        Order order = orderService.queryOrderStatus(codeCnd.getOrderId());
        StatusVo statusVo = new StatusVo();
        statusVo.setStatus(order.getStatus());
        statusVo.setAppId(order.getAppId() + "");
        return new ApiResult<>().success(statusVo);
    }

    /**
     * <b>功能描述：</b>根据订单id删除订单<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/delete")
    public ApiResult delete(@RequestBody CodeCnd codeCnd) {
        orderService.delete(codeCnd.getOrderId());
        return new ApiResult().success();
    }

    /**
     * <b>功能描述：</b>根据订单id查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryCardPwdByIdAndCardNo")
    public ApiResult queryCardPwdByIdAndCardNo(@RequestBody CardPwdCnd cardPwdCnd) {
        String cardPwd = orderService.queryCardPwdByIdAndCardNo(cardPwdCnd.getCardPwdId(), cardPwdCnd.getCardNo());
        return new ApiResult<>().success(cardPwd);
    }

    /**
     * <b>功能描述：</b>根据订单id关闭订单<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/closeOrder")
    public ApiResult closeOrder(@RequestBody CodeCnd codeCnd) {
        orderService.updateStatus(codeCnd.getOrderId(), 4);
        return new ApiResult().success();
    }

}
