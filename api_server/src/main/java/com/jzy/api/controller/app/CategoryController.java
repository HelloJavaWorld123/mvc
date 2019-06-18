package com.jzy.api.controller.app;

import com.jzy.api.cnd.app.CategoryCnd;
import com.jzy.api.cnd.app.CategoryListCnd;
import com.jzy.api.cnd.app.DealerAppListCnd;
import com.jzy.api.model.app.Category;
import com.jzy.api.service.app.CategoryService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.vo.app.CategoryVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.jzy.common.enums.ResultEnum.OPERATION_FAILED;

/**
 * 产品分类controller
 *
 * @author <a href="mailto:aparatrooper@163.com">aparatrooper</a>
 * @since JDK1.8
 */
@Controller
@ResponseBody
@RequestMapping("category")
@Api(tags = "分类")
public class CategoryController {

    private final static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Resource
    private CategoryService categoryService;

    @Resource
    private TableKeyService tableKeyService;

    /**
     * 产品分类列表(H5使用)
     * @return {@link ApiResult}
     */
    @RequestMapping("list")
    @ApiOperation(httpMethod="POST" ,value = "前端-产品分类列表")
    public ApiResult list() {
        List<CategoryVo> categoryList;
        try {
            categoryList = categoryService.listByDealerId();
        } catch (Exception e) {
            logger.error("admin产品分类异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(categoryList);
    }

    /**
     * <b>功能描述：</b>产品分类分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/index")
    @RequiresPermissions(value = "a:category:index")
    @ApiOperation(httpMethod="POST" ,value = "产品分类分页列表")
    public ApiResult index(@RequestBody CategoryListCnd categoryListCnd) {
        PageVo<CategoryVo> result;
        try {
            result = categoryService.listPage(categoryListCnd);
        } catch (Exception e) {
            logger.error("admin产品分类分页查询异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>(result);
    }

   /**
    * <b>功能描述：</b>产品分类列表查询（后台查询使用）<br>
    * <b>修订记录：</b><br>
    * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    @RequestMapping("admin/list")
    @RequiresPermissions("a:category:list")
    @ApiOperation(httpMethod="POST" ,value = "产品分类列表")
    public ApiResult getList() {
        List<CategoryVo> categoryList;
        try {
            categoryList = categoryService.getList();
        } catch (Exception e) {
            logger.error("admin产品分类异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(categoryList);
    }

    @RequestMapping("dealerAppList")
    @ApiOperation(httpMethod="POST" ,value = "前端-产品分类--商品列表")
    public ApiResult dealerAppList(@RequestBody DealerAppListCnd dealerAppListCnd) {
        Map<String, Object> dealerAppList;
        try {
            dealerAppList = categoryService.dealerAppList(dealerAppListCnd.getCateId());
        } catch (Exception e) {
            logger.error("admin产品分类商品列表异常:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED.getMsg());
        }
        return new ApiResult<>(dealerAppList);
    }

    /**
     * <b>功能描述：</b>添加产品分类<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/save")
    @RequiresPermissions(value = "a:category:save")
    @ApiOperation(httpMethod="POST" ,value = "保存")
    public ApiResult save(@RequestBody CategoryCnd categoryCnd) {
        try {
            Category category = new Category();
            BeanUtils.copyProperties(categoryCnd,category);
            category.setId(tableKeyService.newKey("app_cate", "id", 0));
            categoryService.save(category);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin产品分类添加:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>产品分类，物理删除<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return {@l}ink ApiResult
     */
    @RequestMapping("admin/delete")
    @RequiresPermissions(value = "a:category:delete")
    @ApiOperation(httpMethod="POST" ,value = "删除")
    public ApiResult deleteBatch(@RequestBody IdCnd idCnd) {
        try {
            categoryService.delete(Long.valueOf(idCnd.getId()));
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin产品分类删除:{}", e);
            return new ApiResult().fail(OPERATION_FAILED);
        }
        return new ApiResult<>();
    }

    /**
     * <b>功能描述：</b>编辑产品分类<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("admin/update")
    @RequiresPermissions(value = "a:category:update")
    @ApiOperation(httpMethod="POST" ,value = "更新")
    public ApiResult update(@RequestBody CategoryCnd categoryCnd) {
        try {
            Category category = new Category();
            BeanUtils.copyProperties(categoryCnd,category);
            category.setId(categoryCnd.getId());
            categoryService.edit(category);
        }catch (BusException e){
            return new ApiResult().fail(e.getMessage());
        }catch (Exception e){
            logger.error("admin产品分类编辑:{}", e);
            return new ApiResult().fail(ResultEnum.OPERATION_FAILED);
        }
        return new ApiResult<>();
    }
}
