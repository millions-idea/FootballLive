package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

@Table(name =  "tb_publish_message_relations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublishMessageRelation {

    private Integer relationId;

    private Integer msgId;

    private Integer userId;

    private Integer isRead;

    private Date readDate;

    private Date addDate;
}
