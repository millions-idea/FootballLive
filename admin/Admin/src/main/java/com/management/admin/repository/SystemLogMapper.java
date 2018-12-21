package com.management.admin.repository;

import com.management.admin.entity.db.SystemLog;
import com.management.admin.entity.dbExt.LiveDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SystemLogMapper extends MyMapper<SystemLog>{

    // 要用方法：1：拷贝分页，2：查看详情

    @Select("SELECT t1.log_id,t1.section,t1.content,t1.add_date from tb_system_logs t1 where ${condition} GROUP BY t1.log_id ORDER BY t1.add_date DESC LIMIT #{page},${limit}")
    /**
     * 分页查询 韦德 2018年8月30日11:33:22
     * @param page
     * @param limit
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    List<SystemLog> selectLimit(@Param("page") Integer page, @Param("limit") String limit
            , @Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("SELECT COUNT(t1.log_id) FROM tb_system_logs t1 where ${condition}")
    /**
     * 分页查询记录数 韦德 2018年8月30日11:33:30
     * @param state
     * @param beginTime
     * @param endTime
     * @param where
     * @return
     */
    Integer selectLimitCount(@Param("isEnable") Integer isEnable
            , @Param("beginTime") String beginTime
            , @Param("endTime") String endTime
            , @Param("condition") String condition);

    @Select("select count(*) from tb_lives")
    Integer selectSystemLogCount();

    /**
     * 根据日志编号查询相应的日志详情 狗蛋 2018年12月18日21:30:45
     * @param logId
     * @return
     */
    @Select("select * from tb_system_logs where log_id=#{logId}")
    SystemLog querySystemLogById(Integer logId);

    /**
     * 添加系统日志 狗蛋 2018年12月21日19:38:49
     * @param systemLog
     * @return
     */
    @Insert("insert into tb_system_logs(user_id,section,content,add_date) values(#{userId}#{section},#{content},NOW())")
    Integer insertSystemLog(SystemLog systemLog);

}
