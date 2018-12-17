package com.management.admin.repository;

import com.management.admin.entity.db.Information;
import org.apache.ibatis.annotations.Insert;

public interface InformationMapper extends MyMapper<Information> {

    /**
     * 添加情报
     * @param information
     * @return
     */
    @Insert("insert into tb_informations(game_id,live_id,content) values(gameId,liveId,content)")
    Integer insertInformation(Information information);
}
