package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;

@Table(name = "tb_advertisings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Advertising {

    private Integer adId;

    private Integer type;

    private String sourceUrl;

    private String targetUrl;
}
