package com.dlj.connector.http;

import java.io.File;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/20/21 2:23 PM
 * 存储一些常量，直接声明为final
 */
public final class Constants {
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";
    public static final String Package = "com.dlj.connector.http";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;
}
