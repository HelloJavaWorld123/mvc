package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.BackOrderCnd;
import com.jzy.api.cnd.biz.CodeCnd;
import com.jzy.api.cnd.biz.MonthOrderCnd;
import com.jzy.api.cnd.biz.RunMonthOrderCnd;
import com.jzy.api.model.biz.Order;
import com.jzy.api.service.biz.OrderService;
import com.jzy.api.vo.biz.BackOrderDetailVo;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <b>功能：</b>后端订单业务处理<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190426&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(path = "/order")
public class OrderController extends GenericController {

    @Resource
    private OrderService orderService;

    /**
     * <b>功能描述：</b>订单详情查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryBackOrderById")
    public ApiResult queryBackOrderById(@RequestBody CodeCnd codeCnd) {
        Order order = orderService.queryBackOrderById(codeCnd.getOrderId());
        BackOrderDetailVo orderDetailVo = convert(order, BackOrderDetailVo.class);
        return new ApiResult<>().success(orderDetailVo);
    }

    /**
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryBackOrderList")
    public ApiResult queryBackOrderList(@RequestBody BackOrderCnd backOrderCnd) {
        orderService.queryBackOrderList(backOrderCnd);
        return new ApiResult<>().success();
    }

    /**
     * <b>功能描述：</b>月订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryMonthOrderList")
    public ApiResult queryMonthOrderList(@RequestBody MonthOrderCnd monthOrderCnd) {
        orderService.queryMonthOrderList(monthOrderCnd);
        return new ApiResult<>().success();
    }

    /**
     * <b>功能描述：</b>归档月账单数据<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/runMonthOrderList")
    public ApiResult runMonthOrderList(@RequestBody RunMonthOrderCnd runMonthOrderCnd) {
        orderService.runMonthOrderList(runMonthOrderCnd);
        return new ApiResult<>().success();
    }
}
