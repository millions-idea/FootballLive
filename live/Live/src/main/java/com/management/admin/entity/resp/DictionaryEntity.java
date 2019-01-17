/***
 * @pName management
 * @name Dictionary
 * @user HongWei
 * @date 2018/8/16
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_dictionary")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryEntity {
    @Id
    private Integer dictionaryId;
    @Column(name = "`key`")
    private String key;
    @Column(name = "`value`")
    private String value;
}
