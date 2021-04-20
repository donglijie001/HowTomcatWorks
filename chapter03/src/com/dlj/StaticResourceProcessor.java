package com.dlj;

import com.dlj.connector.http.HttpRequest;
import com.dlj.connector.http.HttpResponse;

import java.io.IOException;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/18/21 7:46 PM
 */
public class StaticResourceProcessor {
    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
