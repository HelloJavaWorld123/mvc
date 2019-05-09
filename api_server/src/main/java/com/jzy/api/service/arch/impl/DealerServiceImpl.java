package com.jzy.api.service.arch.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.arch.DealerListCnd;
import com.jzy.api.cnd.arch.SaveDealerCnd;
import com.jzy.api.dao.arch.DealerBaseInfoMapper;
import com.jzy.api.dao.arch.DealerMapper;
import com.jzy.api.dao.arch.DealerParamMapper;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.model.dealer.DealerBaseInfo;
import com.jzy.api.model.dealer.DealerParam;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.po.arch.DealerAnalysisInfoPo;
import com.jzy.api.po.arch.DealerParamInfoPo;
import com.jzy.api.po.dealer.DealerListPo;
import com.jzy.api.po.dealer.DealerPo;
import com.jzy.api.service.arch.DealerParamService;
import com.jzy.api.service.arch.DealerService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.util.MyEncrypt;
import com.jzy.api.vo.dealer.DealerDetailVo;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

/**
 * <b>功能：</b>渠道商业务处理类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190430&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class DealerServiceImpl extends GenericServiceImpl<Dealer> implements DealerService {

    @Resource
    private SysImagesService sysImagesService;

    @Resource
    private TableKeyService tableKeyService;

    @Resource
    private DealerMapper dealerMapper;

    @Resource
    private DealerParamMapper dealerParamMapper;

    @Resource
    private DealerBaseInfoMapper dealerBaseInfoMapper;


    @Resource
    private DealerParamService dealerParamService;

    @Override
    public Dealer queryDealer(Integer dealerId) {
        return dealerMapper.queryById(dealerId.longValue());
    }

    /**
     * <b>功能描述：</b>获取渠道商私钥<br>
     * <b>修订记录：</b><br>
     * <li>20190505&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public DealerAnalysisInfoPo getAnalysisInfo(String businessId) {
        return dealerMapper.getAnalysisInfo(businessId);

    }

    /**
     * <b>功能描述：</b>渠道商列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo getList(DealerListCnd dealerListCnd) {
        Integer page = dealerListCnd.getPage();
        Integer limit = dealerListCnd.getLimit();
        Page<DealerListPo> dealerListPoPage = PageHelper.startPage(page, limit);
        List<DealerListPo> dealerListPoList = dealerMapper.getList(dealerListCnd.getQueryName());
        PageVo<DealerListPo> pageVo = new PageVo<>(dealerListPoList);
        pageVo.setLimit(limit);
        pageVo.setPage(page);
        pageVo.setTotalCount(dealerListPoPage.getTotal());
        return pageVo;
    }

    /**
     * <b>功能描述：</b>渠道商信息保存接口<br>
     * <b>修订记录：</b><br>
     * <li>20190422&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void save(SaveDealerCnd saveDealerCnd) {
        Dealer dealer = saveDealerCnd.getDealer();
        DealerBaseInfo dbi = saveDealerCnd.getDealerBaseInfo();
        List<DealerParam> dpmList = saveDealerCnd.getDpmList();
        List<FileInfo> fileInfoMapperList = saveDealerCnd.getFileInfo();
        if (null == dbi.getDealerId()) {//渠道商信息的新增
            //渠道商主表信息的添加
            dealer = insertDealer(dealer);
            //渠道商基础信息和配置信息新增
            dbi = insertDealerBaseInfo(dealer, dbi, dpmList);
            //修改图片信息
            if (!CollectionUtils.isEmpty(fileInfoMapperList)) {
                for (FileInfo fileInfoMapper : saveDealerCnd.getFileInfo()) {
                    sysImagesService.save(new SysImages(Long.valueOf(tableKeyService.newKey("sys_images", "id", 0)), dbi.getId().toString(),
                            fileInfoMapper.getFileOrignName(), fileInfoMapper.getContentType(), fileInfoMapper.getFileUrl(), fileInfoMapper.getType()));
                }
            }
        } else {//渠道商信息的修改
            //渠道商主表信息的修改
            updateDealer(dealer);
            //渠道商基础信息和配置信息的修改
            updateDealerBaseInfo(dbi, dpmList);
            //图片信息修改
            if (!CollectionUtils.isEmpty(dpmList)) {
                for (FileInfo fileInfoMapper : saveDealerCnd.getFileInfo()) {
                    sysImagesService.updateSysImages(new SysImages(Long.valueOf(tableKeyService.newKey("sys_images", "id", 0)), dbi.getId().toString(),
                            fileInfoMapper.getFileOrignName(), fileInfoMapper.getContentType(), fileInfoMapper.getFileUrl(), fileInfoMapper.getType()));
                }
            }
        }
    }

    /**
     * <b>功能描述：</b>渠道商基础信息和配置信息的修改<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void updateDealerBaseInfo(DealerBaseInfo dbi, List<DealerParam> dpmList) {
        dealerBaseInfoMapper.update(dbi);
        dealerParamService.updateDealerParam(dbi.getDealerId(), dpmList);
    }


    /**
     * <b>功能描述：</b>渠道商主表信息的修改<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private void updateDealer(Dealer dealer) {
        update(dealer);
    }

    /**
     * <b>功能描述：</b>渠道商基础信息保存<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public DealerBaseInfo insertDealerBaseInfo(Dealer dealer, DealerBaseInfo dbi, List<DealerParam> dpmList) {
        //新增渠道商配置表
        dealerParamService.save(dealer.getId(), dpmList);
        //新增dealer_base_info 表
        dbi.setDealerId(dealer.getId().toString());
        dbi.setDealerNo("D0" + dealer.getIdnum().substring(3));
        dealerBaseInfoMapper.insert(dbi);
        return dbi;
    }

    /**
     * <b>功能描述：</b>保存渠道商主表信息<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Dealer insertDealer(Dealer dealer) {
        //获取经销商标识最大值
        String maxNum = dealerMapper.getMaxIdNum();
        String idnum = "Num0".concat(String.valueOf(Integer.parseInt(maxNum.substring(3)) + 1));
        String prikey = MyEncrypt.getInstance().obscureMd5(idnum);
        String pubkey = Base64.getEncoder().encodeToString(prikey.getBytes(Charset.forName("UTF-8"))).replace("=", "");
        dealer.setId(tableKeyService.newKey("dealer", "id", 0));
        dealer.setPrikey(prikey);
        dealer.setIdnum(idnum);
        dealer.setPubkey(pubkey);
        this.insert(dealer);
        return dealer;
    }

    /**
     * <b>功能描述：</b>渠道商详情<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public DealerDetailVo detail(String id) {
        DealerDetailVo dealerDetailVo = dealerMapper.detail(id);
        List<DealerParamInfoPo> dealerParamInfoPos = dealerParamMapper.getDealerParamInfo(id);
        dealerDetailVo.setDpmList(dealerParamInfoPos);
        return dealerDetailVo;
    }

    @Override
    protected GenericMapper<Dealer> getGenericMapper() {
        return dealerMapper;
    }
}
