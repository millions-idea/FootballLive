/***
 * @pName Admin
 * @name TeamMapper
 * @user HongWei
 * @date 2018/12/17
 * @desc
 */
package com.management.admin.repository;

import com.management.admin.entity.db.Team;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TeamMapper extends MyMapper<Team> {
    /**
     * 查询团队详细信息列表 DF 2018年12月17日23:27:00
     * @param teamIdList
     * @return
     */
    @Select("SELECT * FROM tb_teams WHERE team_id IN(${teamId}) AND is_delete = 0")
    List<Team> selectTeams(@Param("teamId") String teamIdList);
}
