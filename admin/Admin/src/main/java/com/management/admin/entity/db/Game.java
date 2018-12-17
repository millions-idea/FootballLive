package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

/**
 * 赛事信息
 */
@Table(name = "tb_games")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Game {

    private Integer gameId;

    private String gameName;

    private String gameIcon;
}
