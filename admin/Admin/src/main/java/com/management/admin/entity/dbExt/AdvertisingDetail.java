package com.management.admin.entity.dbExt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingDetail {
    private Integer adId;

    private Integer liveId;

    private String liveTitle;

    private boolean type;

    private String typeStr;

    private String sourceUrl;

    private String targetUrl;
}
