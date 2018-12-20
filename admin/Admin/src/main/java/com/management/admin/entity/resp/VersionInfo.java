/***
 * @pName Admin
 * @name VersionInfo
 * @user HongWei
 * @date 2018/12/20
 * @desc
 */
package com.management.admin.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VersionInfo {
    private String version;
    private String iosDownload;
    private String androidDownload;
    //VersionResp(value.contains("U") ? 1 : 0, value.replace("U","").trim());
    private Integer update;
}
