package com.dlj.startup;

import com.dlj.connector.http.HttpConnector;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/19/21 3:16 PM
 * 应用程序启动类
 */
public class Bootstrap {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
