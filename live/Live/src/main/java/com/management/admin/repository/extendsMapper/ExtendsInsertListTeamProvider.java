/***
 * @pName management
 * @name ExtendsSelectProvider
 * @user DF
 * @date 2018/8/27
 * @desc
 */
package com.management.admin.repository.extendsMapper;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Set;

public class ExtendsInsertListTeamProvider extends MapperTemplate {
    public ExtendsInsertListTeamProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 批量插入
     *
     * @param ms
     */
    public String inserUpdatetTeamList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
        sql.append(" VALUES ");
        sql.append("<foreach collection=\"list\" item=\"record\" separator=\",\" >");
        sql.append("<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if (!column.isId() && column.isInsertable()) {
                sql.append(column.getColumnHolder("record") + ",");
            }
            /*if(!column.getColumn().contains("game_id")){
                sql.append("game_id=(SELECT game_id FROM tb_games WHERE cloud_id = "  +  "VALUES(cloud_id)" + " LIMIT 1),");
            }*/
        }
        sql.append("</trim>");
        sql.append("</foreach>");
        sql.append(" ON DUPLICATE KEY UPDATE ");
        sql.append("<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
        //获取全部列
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnList) {
            if(!column.getColumn().equals("team_id") && !column.getColumn().equals("cloud_id"))
            sql.append(column.getColumn() + "=" + "VALUES(" + column.getColumn() + ")" + ",");
        }
        sql.append("</trim>");
        return sql.toString();
    }

}
