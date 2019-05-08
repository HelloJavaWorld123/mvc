package com.jzy.api.cnd.arch;

import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.model.dealer.DealerBaseInfo;
import com.jzy.api.model.dealer.DealerParam;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>保存渠道商信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190422&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SaveDealerCnd {
    /**
     * 渠道商主表信息 ：关联dealer表
     */
    private Dealer dealer;
    /**
     * 渠道商基础信息：关联Dealer_base_info
     */
    private DealerBaseInfo dealerBaseInfo;

    /**
     * 图片文件信息
     */
    private List<FileInfo> fileInfo = new ArrayList<>(10);

    /**
     * 配置相关信息
     */
    private List<DealerParam> dpmList = new ArrayList<>();

}
