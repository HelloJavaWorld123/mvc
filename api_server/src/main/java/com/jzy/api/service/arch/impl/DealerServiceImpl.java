package com.jzy.api.service.arch.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.arch.DealerListCnd;
import com.jzy.api.cnd.arch.SaveDealerCnd;
import com.jzy.api.cnd.arch.UpdateDealerStatusCnd;
import com.jzy.api.dao.arch.DealerBaseInfoMapper;
import com.jzy.api.dao.arch.DealerMapper;
import com.jzy.api.dao.arch.DealerParamMapper;
import com.jzy.api.dao.biz.OrderMapper;
import com.jzy.api.dao.sys.SysEmpRoleMapper;
import com.jzy.api.model.app.FileInfo;
import com.jzy.api.model.auth.SysEmpRole;
import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.model.dealer.DealerBaseInfo;
import com.jzy.api.model.dealer.DealerParam;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.model.sys.SysImages;
import com.jzy.api.po.arch.DealerAnalysisInfoPo;
import com.jzy.api.po.arch.DealerParamInfoPo;
import com.jzy.api.po.dealer.DealerListPo;
import com.jzy.api.service.arch.DealerBaseInfoService;
import com.jzy.api.service.arch.DealerParamService;
import com.jzy.api.service.arch.DealerService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.service.sys.EmpService;
import com.jzy.api.service.sys.SysImagesService;
import com.jzy.api.util.MD5Util;
import com.jzy.api.util.MyEncrypt;
import com.jzy.api.vo.dealer.DealerDetailVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.*;

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
    private SysEmpRoleMapper sysEmpRoleMapper;

    @Resource
    private DealerBaseInfoService dealerBaseInfoService;

    @Resource
    private EmpService empService;


    @Resource
    private DealerParamService dealerParamService;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public Dealer queryDealer(String dealerId) {
        return dealerMapper.queryById(Long.valueOf(dealerId));
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
        Dealer dealer = saveDealerCnd.getDealerMapper();
        dealer.setState(1);
        DealerBaseInfo dbi = saveDealerCnd.getDealerBaseInfoMapper();
        List<DealerParam> dpmList = saveDealerCnd.getDpmList();
        List<FileInfo> fileInfoMapperList = saveDealerCnd.getFileInfoMapper();
        if (null == dbi.getDealerId() || "".equals(dbi.getDealerId())) {//渠道商信息的新增
            //渠道商主表信息的添加
            dealer = insertDealer(dealer, dbi);
            //渠道商基础信息和配置信息新增
            dbi = insertDealerBaseInfo(dealer, dbi, dpmList);
            //修改图片信息
            if (!CollectionUtils.isEmpty(fileInfoMapperList)) {
                for (FileInfo fileInfoMapper : saveDealerCnd.getFileInfoMapper()) {
                    sysImagesService.save(new SysImages(Long.valueOf(tableKeyService.newKey("sys_images", "id", 0)), dealer.getId().toString(),
                            fileInfoMapper.getFileOrignName(), fileInfoMapper.getContentType(), fileInfoMapper.getFileUrl(), fileInfoMapper.getType()));
                }
            }
            //保存渠道商对应的登录用户信息
            Emp emp = getEmp(dbi);
            emp.setId(tableKeyService.newKey("sys_emp", "id", 0));
            if (empService.checkNameList(emp.getName(), null).size() > 0) {
                throw new BusException("渠道商登录用户名重复，请重新输入！");
            }
            empService.insert(emp);
            //保存渠道商登录用户角色信息
            SysEmpRole sysEmpRole = new SysEmpRole();
            sysEmpRole.setEmpId(emp.getId().toString());
            sysEmpRole.setRoleId("3");
            sysEmpRoleMapper.insert(sysEmpRole);

        } else {//渠道商信息的修改
            //渠道商主表信息的修改
            updateDealer(dealer, dbi);
            //渠道商基础信息和配置信息的修改
            updateDealerBaseInfo(dbi, dpmList);
            //图片信息修改
            if (!CollectionUtils.isEmpty(dpmList)) {
                for (FileInfo fileInfoMapper : saveDealerCnd.getFileInfoMapper()) {
                    sysImagesService.updateSysImages(new SysImages(Long.valueOf(tableKeyService.newKey("sys_images", "id", 0)), dealer.getId().toString(),
                            fileInfoMapper.getFileOrignName(), fileInfoMapper.getContentType(), fileInfoMapper.getFileUrl(), fileInfoMapper.getType()));
                }
            }
            //修改渠道商登录用户信息
            Emp emp = getEmp(dbi);
            if (empService.checkNameList(emp.getName(), emp.getDealerId() + "").size() > 0) {
                throw new BusException("渠道商登录用户名重复，请重新输入！");
            }
            Integer rows = empService.update(emp);
            if (rows.equals(0)){
                emp.setId(tableKeyService.newKey("sys_emp", "id", 0));
                empService.insert(emp);
            }
        }
    }

    /**
     * <b>功能描述：</b>获取渠道商对应的登录用户信息<br>
     * <b>修订记录：</b><br>
     * <li>20190511&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Emp getEmp(DealerBaseInfo dbi) {
        Emp emp = new Emp();
        if (!StringUtils.isEmpty(dbi.getDealerLoginName())) {
            emp.setName(dbi.getDealerLoginName());
        }
        if (!StringUtils.isEmpty(dbi.getDealerPassword())) {
            emp.setPwd(MD5Util.string2MD5(dbi.getDealerPassword()));
        }
        emp.setDealerId(Integer.parseInt(dbi.getDealerId()));
        return emp;
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
    private void updateDealer(Dealer dealer, DealerBaseInfo dbi) {
        dealer.setName(dbi.getDealerName());
        dealer.setContact(dbi.getDealerContact());
        dealer.setTelno(dbi.getDealerTelephone());
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
        dbi.setId(tableKeyService.newKey("dealer_base_info", "id", 0));
        dbi.setDealerId(dealer.getId().toString());
        dbi.setDealerNo("D0" + dealer.getIdnum().substring(3));
        dealerBaseInfoService.insert(dbi);
        return dbi;
    }

    /**
     * <b>功能描述：</b>保存渠道商主表信息<br>
     * <b>修订记录：</b><br>
     * <li>20190509&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Dealer insertDealer(Dealer dealer, DealerBaseInfo dbi) {
        //获取经销商标识最大值
        getPubAndPriKey(dealer);
        dealer.setId(tableKeyService.newKey("dealer", "id", 0));
        dealer.setName(dbi.getDealerName());
        dealer.setContact(dbi.getDealerContact());
        dealer.setTelno(dbi.getDealerTelephone());
        dealer.setVerified(1);
        this.insert(dealer);
        return dealer;
    }

    /**
     * <b>功能描述：</b>获取公钥和私钥<br>
     * <b>修订记录：</b><br>
     * <li>20190520&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    private Dealer getPubAndPriKey(Dealer dealer) {
        String maxNum = dealerMapper.getMaxIdNum();
        String idnum = "Num".concat(String.valueOf(Integer.parseInt(maxNum) + 1));
        String pubkey = MyEncrypt.getInstance().obscureMd5(idnum);
        String prikey = Base64.getEncoder().encodeToString(pubkey.getBytes(Charset.forName("UTF-8"))).replace("=", "");
        dealer.setPrikey(prikey);
        dealer.setIdnum(idnum);
        dealer.setPubkey(pubkey);
        return dealer;
    }

    public static void main(String[] args) {


        String pubkey = MyEncrypt.getInstance().obscureMd5(new Date().toString());
        String prikey = Base64.getEncoder().encodeToString(pubkey.getBytes(Charset.forName("UTF-8"))).replace("=", "");


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
        if (dealerParamInfoPos.size() > 0) {
            dealerDetailVo.setDpmList(dealerParamInfoPos);
        }
        return dealerDetailVo;
    }

    /**
     * <b>功能描述：</b>渠道商状态修改<br>
     * <b>修订记录：</b><br>
     * <li>20190511&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public int updateStatus(UpdateDealerStatusCnd updateDealerStatusCnd) {
        return dealerMapper.updateStatus(Long.valueOf(updateDealerStatusCnd.getDealerId()),updateDealerStatusCnd.getState());
    }

    /**
     * <b>功能描述：</b>充值渠道商公钥和私钥<br>
     * <b>修订记录：</b><br>
     * <li>20190520&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void updateDealerPubAndPriKey(Long dealerId) {
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("dealerId",dealerId);
        Integer count = orderMapper.queryCountByParams(paramsMap);

        if(count>0){
           throw new BusException(ResultEnum.DEALER_UNABE_KEY.getMsg(),ResultEnum.DEALER_UNABE_KEY.getCode());
        }

        Dealer dealer = new Dealer();
        dealer.setId(dealerId);
        String pubkey = MyEncrypt.getInstance().obscureMd5(new Date().toString());
        String prikey = Base64.getEncoder().encodeToString(pubkey.getBytes(Charset.forName("UTF-8"))).replace("=", "");
        dealer.setPubkey(pubkey);
        dealer.setPrikey(prikey);
        this.update(dealer);
    }

    @Override
    protected GenericMapper<Dealer> getGenericMapper() {
        return dealerMapper;
    }
}
