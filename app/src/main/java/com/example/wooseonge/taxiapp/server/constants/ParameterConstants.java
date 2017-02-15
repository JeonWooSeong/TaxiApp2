package com.example.wooseonge.taxiapp.server.constants;

/**
 * Created by KSE_NOTEBOOK on 2017-02-15.
 */

public class ParameterConstants {
    private static final String SERVER_URL_TEST = "http://169.53.248.101:9088/api/";

    public static String getServerUrl() {
        return "http://169.53.248.101:9088/api/";
    }
    public static class Common{
        public static final String OPCODE_COMMON = "common/";
        public static final String URI_APPVERSION = "getAppVersion.do";
    }
}
