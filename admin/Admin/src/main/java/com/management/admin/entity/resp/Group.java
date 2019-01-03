package com.management.admin.entity.resp;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Group {

    private String owner;

    private String tname;

    private Integer maxusers;

    private Integer tid;

    private Integer size;

    private String custom;
}
