/***
 * @pName Admin
 * @name DictionaryInfo
 * @user HongWei
 * @date 2018/12/20
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryInfo {
    private Integer dictionaryId;
    private String key;
    private String value;
    private String url;
}
