package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.DealerAppListCnd;
import com.jzy.api.service.app.CategoryService;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.result.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 产品分类controller
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Controller
@ResponseBody
@RequestMapping("category")
public class CategoryController {

    private final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    private CategoryService categoryService;

    /**
     * 产品分类列表(H5使用)
     * @return {@link ApiResult}
     */
    @RequestMapping("list")
    public ApiResult list() {
        List<CategoryVo> categoryList;
        try {
            categoryList = categoryService.listByDealerId(Long.valueOf("1001"));
        } catch (Exception e) {
            logger.error("admin产品分类异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(categoryList);
    }

    @RequestMapping("dealerAppList")
    public ApiResult dealerAppList(@RequestBody DealerAppListCnd dealerAppListCnd) {
        Map<String, Object> dealerAppList;
        try {
            dealerAppList = categoryService.dealerAppList(dealerAppListCnd.getCateId(), Long.valueOf("1001"));
        } catch (Exception e) {
            logger.error("admin产品分类商品列表异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(dealerAppList);
    }
}
