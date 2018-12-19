package com.management.admin.entity.dbExt;

/**
 * @ClassName TeamCompetition
 * @Description TODO
 * @Author ZXL01
 * @Date 2018/12/18 19:56
 * Version 1.0
 **/

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName GameCategory
 * @Description 球队与赛事 封装类
 * @Author ZXL01
 * @Date 2018/12/18 4:06
 * Version 1.0
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamCompetition {

    private Integer teamId;

    private String teamName;

    private String teamIcon;

    private Integer gameId;

    private String gameName;

    private String gameIcon;

    private Integer categoryId;

    private Integer isDelete;
}
