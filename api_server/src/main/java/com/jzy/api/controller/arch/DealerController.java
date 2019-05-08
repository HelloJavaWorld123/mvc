package com.jzy.api.controller.arch;


import com.jzy.api.cnd.arch.DealerListCnd;
import com.jzy.api.cnd.arch.SaveDealerCnd;
import com.jzy.api.model.dealer.DealerBaseInfo;

import com.jzy.api.service.arch.DealerService;
import com.jzy.api.util.PhoneCheckUtil;
import com.jzy.api.vo.dealer.DealerDetailVo;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;


/**
 * <b>功能：</b>渠道商DealerController<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("dealer")
public class DealerController {
    private final static Logger logger = LoggerFactory.getLogger(DealerController.class);

    @Resource
    private DealerService dealerService;

    /**
     * <b>功能描述：</b>渠道商列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/getList")
    public ApiResult getList(@RequestBody DealerListCnd dealerListCnd) {
        PageVo pageVo;
        try {
            pageVo = dealerService.getList(dealerListCnd);
        } catch (Exception e) {
            logger.error("渠道商列表异常:{}", e);
            return new ApiResult("查询失败");
        }
        return new ApiResult<>(pageVo);
    }


    /**
     * <b>功能描述：</b>渠道商信息保存接口<br>
     * <b>修订记录：</b><br>
     * <li>20190422&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("save.shtml")
    public ApiResult save(@RequestBody SaveDealerCnd saveDealerCnd) {
        try {
            String telephone = saveDealerCnd.getDealerBaseInfo().getDealerTelephone();
            //手机号码校验
            if (null != telephone && !"".equals(telephone)) {
                if (!PhoneCheckUtil.isPhoneLegal(telephone)) {
                    return new ApiResult("手机号为空,或格式不正确");
                }
            }
            dealerService.save(saveDealerCnd);
        } catch (Exception e) {
            logger.error("渠道商更新异常:{}", e);
            return new ApiResult("更新失败");
        }
        return new ApiResult<>();


    }

    /**
     * <b>功能描述：</b>渠道商详情<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/detail")
    public ApiResult detail(@RequestBody IdCnd idCnd  ) {
        DealerDetailVo dealerDetailVo;
        try {
            dealerDetailVo = dealerService.detail(idCnd.getId().toString());
        } catch (Exception e) {
            logger.error("渠道商查询异常:{}", e);
            return new ApiResult("查询失败");
        }
        return new ApiResult(dealerDetailVo);
    }


}
