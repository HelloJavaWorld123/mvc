package com.jzy.api.controller.biz;

import com.jzy.api.cnd.biz.OrderListCnd;
import com.jzy.api.service.biz.OrderService;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.controller.GenericController;
import com.jzy.framework.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <b>功能：</b>订单业务处理<br>
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
     * <b>功能描述：</b>订单列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryOrderList")
    public ApiResult queryOrderList(@RequestBody OrderListCnd orderListCnd) {
        //orderService.queryOrderList();
        return null;
    }

    /**
     * <b>功能描述：</b>根据订单id删除订单<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/delete")
    public ApiResult delete(@RequestBody IdCnd idCnd) {
        orderService.delete(idCnd.getId());
        return null;
    }

    /**
     * <b>功能描述：</b>根据订单id查询订单详情<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryOrderById")
    public ApiResult queryOrderById(@RequestBody IdCnd idCnd) {
        orderService.queryOrderById(idCnd.getId());
        return null;
    }

    /**
     * <b>功能描述：</b>根据订单id查询卡密<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/queryCardPwdById")
    public ApiResult queryCardPwdById(@RequestBody IdCnd idCnd) {
        orderService.queryCardPwdById(idCnd.getId());
        return null;
    }

    /**
     * <b>功能描述：</b>根据订单id关闭订单<br>
     * <b>修订记录：</b><br>
     * <li>20190420&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @ResponseBody
    @RequestMapping(path = "/closeOrder")
    public ApiResult closeOrder(@RequestBody IdCnd idCnd) {
        orderService.updateOrderClose(idCnd.getId());
        return null;
    }

}
