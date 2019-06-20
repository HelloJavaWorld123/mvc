package com.jzy.api.cnd.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <b>功能：</b>游戏列表查询入参<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
@ApiModel(value="后台游戏列表查询参数")
public class GameListCnd {

    /**
     * 父Id
     */
    @ApiModelProperty(value = "游戏父id")
    private String pId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型：1游戏2大区3服务")
    private String type;

    /**
     * 名称模糊搜索
     */
    @ApiModelProperty(value = "游戏区服名称模糊搜索")
    private String queryName;


}
