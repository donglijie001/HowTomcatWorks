package com.dlj.valves;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 把请求头打印到控制台上
 * @author lijiedong
 * @version 1.0
 * @date 4/29/21 4:35 PM
 */
public class HeaderLoggerValve implements Valve, Contained {
    protected Container container;
    public Container getContainer() {
        return container;
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
        System.out.println("Header Logger Valve");
        ServletRequest sreq = request.getRequest();
        if (sreq instanceof HttpServletRequest){
            HttpServletRequest hreq= (HttpServletRequest) sreq;
            Enumeration headerNames = hreq.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement().toString();
                String headerValve = hreq.getHeader(headerName);
                System.out.println(headerName+":"+headerValve);
            }
        }else {
            System.out.println("not an HTTP Request");
        }
    }
}
