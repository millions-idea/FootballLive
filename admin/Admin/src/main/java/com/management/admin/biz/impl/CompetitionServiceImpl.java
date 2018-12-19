package com.management.admin.biz.impl;

import com.management.admin.biz.ICompetitionService;
import com.management.admin.entity.db.AdminUser;
import com.management.admin.entity.db.Game;
import com.management.admin.entity.db.LiveCategory;
import com.management.admin.entity.dbExt.GameCategory;
import com.management.admin.entity.enums.UserRoleEnum;
import com.management.admin.repository.CompetitionMapper;
import com.management.admin.repository.utils.ConditionUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName ICompetitionServiceImpl
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/18 2:01
 * Version 1.0
 **/

/**
 * 赛事管理接口实现类 DF 2018年12月18日02:49:10
 */
@Service
@Transactional
public class CompetitionServiceImpl implements  ICompetitionService {

    private final CompetitionMapper competitionMapper;
    @Autowired
    public CompetitionServiceImpl(CompetitionMapper competitionMapper){
        this.competitionMapper=competitionMapper;
    }

    /**
     * 查询ALL赛事 DF 2018-12-18 14:43:462
     * @return
     */
    @Override
    public List<Game> getGames() {
        List<Game> games=competitionMapper.selectGames();
        if(games!=null){
            return games;
        }
        return null;
    }

    /**
     * 查询直播分类 DF 2018-12-18 14:43:462
     * @return
     */
    @Override
    public List<LiveCategory> selectLiveCategory() {
        List<LiveCategory> liveCategories=competitionMapper.selectLiveCategory();
        if(liveCategories!=null){
            return liveCategories;
        }
        return null ;
    }

    /**
     * 添加赛事信息 DF 2018-12-18 14:43:462
     * @return
     */
    @Override
    public boolean addCompetition(Game game) {
        int result=competitionMapper.addCompetition(game);
        if(result>0) {
            return true;
        }
        return false;
    }

    /**
     * 加载赛事信息列表分页记录数 DF 2018年12月17日14:40:233
     * @return
     */
    @Override
    public Integer getCompetitionLimitCount() {
        return competitionMapper.selectLimitCount();
    }

    /**
     * 分页加载赛事信息列表 DF 2018-12-17 14:39:562
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<GameCategory> getCompetitionLimit(Integer page, String limit) {
        page = ConditionUtil.extractPageIndex(page, limit);
        List<GameCategory> list = competitionMapper.selectLimit(page, limit);
        return list;
    }

    /**
     * 删除赛事信息 提莫 2018年12月18日19:33:30
     * @return
     */
    @Override
    public boolean deleteCompetition(Integer gameId) {
        Integer  result=competitionMapper.deleteCompetition(gameId);
        if(result>0){
            return true;
        }
        return false;
    }
}