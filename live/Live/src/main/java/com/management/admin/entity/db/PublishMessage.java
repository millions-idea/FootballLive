/***
 * @pName Live
 * @name PublishMessage
 * @user HongWei
 * @date 2019/1/25
 * @desc
 */
package com.management.admin.entity.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_publish_messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublishMessage {
    private Integer msgId;
    private String message;
    private Date addDate;
    private Integer type;
}
