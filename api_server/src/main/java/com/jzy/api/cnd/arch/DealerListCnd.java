package com.jzy.api.cnd.arch;

import com.jzy.framework.bean.cnd.PageCnd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <b>功能：</b>渠道商列表查询入参<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="渠道商列表查询参数")
public class DealerListCnd extends PageCnd {

    /**
     * 商户名称，编号，昵称模糊搜索
     */
    @ApiModelProperty(value = "模糊查询参数：渠道商名称，渠道商简称，渠道商编号")
    private String queryName;


}
