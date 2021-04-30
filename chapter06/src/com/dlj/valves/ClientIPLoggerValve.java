package com.dlj.valves;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * 打印客户端的IP地址到控制台
 * @author lijiedong
 * @version 1.0
 * @date 4/29/21 4:13 PM
 */
public class ClientIPLoggerValve implements Valve, Contained {
    protected Container container;
    public Container getContainer() {
        return null;
    }

    public void setContainer(Container container) {
        this.container=container;
    }

    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        // Pass this request on to the next valve in our pipeline
        context.invokeNext(request,response);
        System.out.println("Client IP Logger Valve");
        ServletRequest sreq = request.getRequest();
        System.out.println(sreq.getRemoteAddr());
        System.out.println("---------------------------------------------------------------");
    }
}
