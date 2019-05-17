package com.jzy.api.service.app.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jzy.api.cnd.app.AppGameCnd;
import com.jzy.api.cnd.app.AppGameListCnd;
import com.jzy.api.cnd.app.GameListCnd;
import com.jzy.api.cnd.app.GetServInfoCnd;
import com.jzy.api.dao.app.AppGameMapper;
import com.jzy.api.dao.app.AppInfoMapper;
import com.jzy.api.model.app.AppGame;
import com.jzy.api.po.app.AppGameListPo;
import com.jzy.api.po.app.AppGamePo;
import com.jzy.api.service.app.AppGameService;
import com.jzy.api.service.key.TableKeyService;
import com.jzy.api.util.CommUtils;
import com.jzy.api.util.MyStringUtil;
import com.jzy.api.vo.app.AppGameListVo;
import com.jzy.api.vo.app.AppGameVo;
import com.jzy.common.enums.ResultEnum;
import com.jzy.framework.bean.vo.PageVo;
import com.jzy.framework.dao.GenericMapper;
import com.jzy.framework.exception.BusException;
import com.jzy.framework.service.impl.GenericServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <b>功能：</b>游戏<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190506&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;唐永刚&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class AppGameServiceImpl extends GenericServiceImpl<AppGame> implements AppGameService {


    @Resource
    private AppGameMapper appGameMapper;

    @Resource
    private AppInfoMapper appInfoMapper;

    @Resource
    private TableKeyService tableKeyService;


    /**
     * <b>功能描述：</b>前台渠道商对应商品查询区<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppGameListVo getAreaInfo(Long aiId) {
        List<AppGameListPo> appGameListPos = appGameMapper.getAreaInfo(aiId);
        return new AppGameListVo(appGameListPos);
    }

    /**
     * <b>功能描述：</b>前台渠道商对应商品查询服<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppGameListVo getServInfo(GetServInfoCnd getServInfoCnd) {
        List<AppGameListPo> appGameListPos = appGameMapper.getServInfo(getServInfoCnd.getAiId(), getServInfoCnd.getAreaId());
        return new AppGameListVo(appGameListPos);
    }

    /**
     * <b>功能描述：</b>后台游戏列表查询<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */

    @Override
    public List<AppGamePo> getList(GameListCnd gameListCnd) {
        return appGameMapper.getList(gameListCnd);
    }

    /**
     * <b>功能描述：</b>游戏大区分页查询<br>
     * <b>修订记录：</b><br>
     * <li>20190430&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public PageVo listPage(AppGameListCnd appGameListCnd) {
        Integer page = appGameListCnd.getPage();
        Integer limit = appGameListCnd.getLimit();
        Page<AppGameVo> infoListVoPage = PageHelper.startPage(page, limit);
        List<AppGameVo> appGameList = appGameMapper.listPage(appGameListCnd);
        PageVo<AppGameVo> pageVo = new PageVo<>(appGameList);
        pageVo.setTotalCount(infoListVoPage.getTotal());
        pageVo.setPage(page);
        pageVo.setLimit(limit);
        return pageVo;
    }

    /**
     * <b>功能描述：</b>后台游戏添加<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void save(AppGame appGame) {
        // 验证参数是否为空
        if (StringUtils.isEmpty(appGame.getName()) || StringUtils.isEmpty(appGame.getType()) || StringUtils.isEmpty(appGame.getStatus())) {
            throw new BusException(ResultEnum.PARAM_ERR.getMsg());
        }
        // 验证p_id下名称是否重复
        if (countAppGame(appGame.getPId(), appGame.getName(), appGame.getType()) > 0) {
            throw new BusException("名称重复: ".concat(appGame.getName()));
        }

        AppGame appGanmeSecond = appGameMapper.getByPid(appGame.getPId());
        if (Objects.isNull(appGanmeSecond) && !"1".equals(appGame.getType())) {
            throw new BusException(ResultEnum.PARAM_ERR.getMsg());
        }
        // 验证添加游戏是否创建默认大区
        if ("1".equals(appGame.getType()) && "3".equals(appGame.getStatus())) {
            this.insert(appGame);
            AppGame appGameStatus = new AppGame();
            BeanUtils.copyProperties(appGame,appGameStatus);
            appGameStatus.setPId(MyStringUtil.getString(appGame.getId()));
            appGameStatus.setId(tableKeyService.newKey("app_game", "id", 0));
            appGameStatus.setName("默认大区");
            this.insert(appGameStatus);
        }else {
            // 验证是否能添加区
            if ("2".equals(appGame.getType()) && "3".equals(appGanmeSecond.getStatus())) {
                throw new BusException("不能添加新的区");
                // 验证是否能添加服
            } else if ("3".equals(appGame.getType()) && "2".equals(appGanmeSecond.getStatus())) {
                throw new BusException("不能添加新的服");
            }
            this.insert(appGame);
        }
    }

    /**
     * <b>功能描述：</b>后台游大区戏删除<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void delete(Long id) {
        AppGame appGame = appGameMapper.getById(id);
        if (Objects.isNull(appGame)) {
            throw new BusException(ResultEnum.PARAM_ERR.getMsg());
        }
        //类型为游戏时，查询是否还有app_info商品引用
        if ("1".equals(appGame.getType())) {
            int count = appInfoMapper.getByGameId(id);
            if (count > 0) {
                throw new BusException("已关联商品，无法删除");
            }
        }
        List<String> pidList = listPid(id.toString());
        pidList.add(id.toString());
        appGameMapper.deleteBatch(pidList);
    }

    /**
     * <b>功能描述：</b>根据id获取appgame<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public AppGame getById(Long id) {
        return this.queryById(id);
    }

    /**
     * <b>功能描述：</b>编辑游戏大区<br>
     * <b>修订记录：</b><br>
     * <li>20190514&nbsp;&nbsp;|&nbsp;&nbsp;鲁伟&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public void edit(AppGame appGame) {
        int count = appGameMapper.getByNameNotId(appGame.getName(),appGame.getId());
        if (count>0) {
            throw new BusException(appGame.getName()+"游戏大区名称已经存在");
        }
        this.update(appGame);
    }

    public int countAppGame(String pId, String name, String type){
        return appGameMapper.getByIdNameType(pId,name,type);
    }

    public List<String> listPid(String pId) {
        List<String> query = appGameMapper.getListByPid(pId);
        List<String> result = new ArrayList<String>(query);
        query.forEach(str -> {
            List<String> s = listPid(String.valueOf(str));
            if (!s.isEmpty()) {
                result.addAll(s);
            }
        });
        return result;
    }

    @Override
    protected GenericMapper<AppGame> getGenericMapper() {
        return appGameMapper;
    }


}
