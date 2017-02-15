package com.example.wooseonge.taxiapp.bean;

import lombok.Data;
import lombok.ToString;

/**
 * Created by KSE_NOTEBOOK on 2017-02-15.
 */
@ToString
public @Data class AppVersion {
    String deviceCode;
    String regdate;
    String nowDate;
    String versionCode;
    String deviceName;
}
