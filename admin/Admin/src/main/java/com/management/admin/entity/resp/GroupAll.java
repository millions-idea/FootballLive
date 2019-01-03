package com.management.admin.entity.resp;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupAll {
    private Integer code;

    private Integer count;

    private List<Group> infos;
}
