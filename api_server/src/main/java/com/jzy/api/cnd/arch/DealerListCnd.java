package com.jzy.api.cnd.arch;

import com.jzy.framework.bean.cnd.PageCnd;
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
public class DealerListCnd extends PageCnd {

    /**
     * 商户名称，编号，昵称模糊搜索
     */
    private String queryName;


}
