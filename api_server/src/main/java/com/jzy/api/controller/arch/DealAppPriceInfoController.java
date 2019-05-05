package com.jzy.api.controller.arch;

import com.jzy.api.cnd.app.*;
import com.jzy.api.cnd.arch.GetPriceCnd;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.model.app.AppInfo;
import com.jzy.api.model.app.AppPage;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.po.arch.DealerAppPriceInfoPo;
import com.jzy.api.service.app.AppInfoService;
import com.jzy.api.service.app.AppPriceTypeService;
import com.jzy.api.service.app.IMongoService;
import com.jzy.api.service.arch.DealAppPriceInfoService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.util.HanyuPinyinUtil;
import com.jzy.api.util.RegexUtils;
import com.jzy.api.vo.app.AppInfoDetailVo;
import com.jzy.api.vo.app.AppInfoListVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.cnd.IdCnd;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.result.ApiResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <b>功能：</b>渠道商商品定价<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190505&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Controller
@ResponseBody
@RequestMapping("dealer/app")
public class DealAppPriceInfoController {


    @Resource
    private DealAppPriceInfoService dealAppPriceInfoService;

    /**
     * <b>功能描述：</b>前台查询商品价格接口<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @RequestMapping("price.shtml")
    private ApiResult getPrice(@RequestBody GetPriceCnd getPriceCnd) {
        List<DealerAppPriceInfoPo> dealerAppPriceInfoPoList = dealAppPriceInfoService.getPrice(getPriceCnd);
        return new ApiResult<>(dealerAppPriceInfoPoList);
    }


}
