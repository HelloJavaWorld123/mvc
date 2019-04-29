package com.jzy.api.service.dealer;

import com.jzy.api.model.dealer.Dealer;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 经销商服务接口
 * <p>
 * Created by IntelliJ IDEA. @Date 2019/3/5
 *
 * @Author GuoBing.Zh
 */
public interface DealerService {
    /**
     * 添加经销商
     *
     * @param Dealer
     * @return
     */
    Dealer insertDealer(Dealer Dealer);

    /**
     * 更新经销商信息
     *
     * @param Dealer
     * @return
     */
    boolean updateDealer(Dealer Dealer);

    /**
     * 更新经销商审核状态
     *
     * @param verified
     * @param idnum
     * @return
     */
    boolean updateVerified(int verified, String idnum);

    /**
     * 查询有无经销商id
     *
     * @param id
     * @return true:已存在
     */
    boolean verify(int id);

    /**
     * 查询有无经销商IDNUM
     *
     * @param idnum
     * @return true:已存在
     */
    boolean verifyIdnum(String idnum);

    /**
     * 查取指定idnum的经销商户信息
     *
     * @param idnum
     * @return
     */
    Dealer queryInfo(String idnum);

    /**
     * 查取指定ID的经销商信息
     *
     * @param id
     * @return
     */
    Dealer queryInfo(int id);

    /**
     * 根据当前登录状态取经销商信息
     * 未登录，默认取900Mall商城配置
     *
     * @param session
     * @return
     */
    Dealer queryByUseridOrDefault(HttpSession session);

    /**
     * 查取指定userid用户的经销商信息
     * 没有经销商标识的默认使用900Mall经销商信息，否则使用指定经销商信息
     *
     * @param userid
     * @return
     */
    Dealer queryByUseridOrDefault(String userid);

    /**
     * 查取指定verified的经销商列表
     *
     * @param verified 审核状态(0:待审核,1:通过,2:驳回,3:禁用)
     * @return
     */
    List<Dealer> queryList(int verified);

    /**
     * 查取经销商列表
     *
     * @return
     */
    List<Dealer> queryList();
}
