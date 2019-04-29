package com.jzy.api.service.dealer.impl;

import com.jzy.api.model.dealer.Dealer;
import com.jzy.api.service.dealer.DealerService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.mall.pc.cache.BaseContext.IDENTITY_MALL;
import static com.mall.pc.constant.RedisKeyConst.DEALER_CACHE;

/**
 * 经销商服务
 * <p>
 * Created by IntelliJ IDEA. @Date 2019/3/5
 *
 * @Author GuoBing.Zh
 */
@Service
public class DealerServiceImpl extends BaseDao implements DealerService {
    @Resource
    private IRedisService iRedisService;
    @Override
    public Dealer insertDealer(Dealer dm) {
        List<Dealer> deals = this.queryList();
        Dealer maxdealer = deals.stream().max(Comparator.comparingInt(Dealer::getId)).get();
        String maxNum = maxdealer.getIdnum();
        String idnum = "Num0".concat(String.valueOf(Integer.parseInt(maxNum.substring(3)) + 1));
        String prikey = MyEncrypt.getInstance().obscureMd5(idnum);
        String pubkey = Base64.getEncoder().encodeToString(prikey.getBytes(Charset.forName("UTF-8"))).replace("=", "");
        jdbcTemplate.update(sqlMap("dealer.insert"), idnum, dm.getName(), dm.getContact(), dm.getTelno(), pubkey, prikey, dm.getDesc(), dm.getRemark());
        return jdbcTemplate.queryForObject(sqlMap("dealer.queryId"), BeanPropertyRowMapper.newInstance(Dealer.class));
    }

    @Override
    public boolean updateDealer(Dealer dm) {
        boolean updateSucc = jdbcTemplate.update(sqlMap("dealer.update"), dm.getName(), dm.getContact(), dm.getTelno(), dm.getVerified(), dm.getState(), dm.getPubkey(), dm.getPrikey(), dm.getDesc(), dm.getRemark(), dm.getId()) > 0;
        if (updateSucc) {
            iRedisService.setHashValue(DEALER_CACHE, dm.getIdnum(), dm);
        }
        return updateSucc;
    }

    @Override
    public boolean updateVerified(int verified, String idnum) {
        return jdbcTemplate.update(sqlMap("dealer.update.verified"), verified, idnum) > 0;
    }

    @Override
    public boolean verify(int id) {
        return jdbcTemplate.queryForObject(sqlMap("dealer.query.count.id"), Integer.TYPE, id) > 0;
    }

    @Override
    public boolean verifyIdnum(String idnum) {
        return jdbcTemplate.queryForObject(sqlMap("dealer.query.count.idnum"), Integer.TYPE, idnum) > 0;
    }

    @Override
    public Dealer queryInfo(String idnum) {
        Dealer dealer = (Dealer) iRedisService.getHashValue(DEALER_CACHE, idnum);
        if (Objects.nonNull(dealer)) return dealer;
        dealer = jdbcTemplate.queryForObject(sqlMap("dealer.query.info.idnum"), BeanPropertyRowMapper.newInstance(Dealer.class), idnum);
        if (Objects.nonNull(dealer)) {
            iRedisService.setHashValue(DEALER_CACHE, idnum, dealer);
        }
        return dealer;
    }

    @Override
    public Dealer queryInfo(int id) {
        Dealer dealer = (Dealer) iRedisService.getHashValue(DEALER_CACHE, String.valueOf(id));
        if (Objects.nonNull(dealer)) return dealer;
        dealer = jdbcTemplate.queryForObject(sqlMap("dealer.query.info"), BeanPropertyRowMapper.newInstance(Dealer.class), id);
        if (Objects.nonNull(dealer)) {
            iRedisService.setHashValue(DEALER_CACHE, String.valueOf(dealer.getId()), dealer);
        }
        return dealer;
//        return jdbcTemplate.queryForObject(sqlMap("dealer.query.info"), BeanPropertyRowMapper.newInstance(Dealer.class), id);
    }

    @Override
    public Dealer queryByUseridOrDefault(HttpSession session) {
        LoginUserMapper loginUser = LoginUserMapper.getLoginUser(session);
        return (Objects.nonNull(loginUser) && Objects.nonNull(loginUser.getUser())) ? this.queryByUseridOrDefault(loginUser.getUser().getId()) : this.queryInfo(IDENTITY_MALL);
    }

    @Override
    public Dealer queryByUseridOrDefault(String userid) {
        return jdbcTemplate.queryForObject(sqlMap("dealer.query.info.userid.or.default"), BeanPropertyRowMapper.newInstance(Dealer.class), userid);
    }

    @Override
    public List<Dealer> queryList(int verified) {
        return jdbcTemplate.query(sqlMap("dealer.query.list.verified"), new Object[]{ verified }, BeanPropertyRowMapper.newInstance(Dealer.class));
    }

    @Override
    public List<Dealer> queryList() {
        return jdbcTemplate.query(sqlMap("dealer.query.list"), BeanPropertyRowMapper.newInstance(Dealer.class));
    }
}
