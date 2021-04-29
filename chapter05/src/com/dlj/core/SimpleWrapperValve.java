package com.dlj.core;

import org.apache.catalina.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/29/21 5:13 PM
 */
public class SimpleWrapperValve implements Valve , Contained {
    protected Container container;
    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container=container;
    }

    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
        System.out.println("basic valve");
           SimpleWrapper wrapper= (SimpleWrapper) getContainer();
        ServletRequest sreq = request.getRequest();
        ServletResponse sres = response.getResponse();
        Servlet servlet=null;
        HttpServletRequest hreq=null;
        if (sreq instanceof HttpServletRequest){
            hreq= (HttpServletRequest) sreq;
        }
        HttpServletResponse hres=null;
        if (sres instanceof HttpServletResponse){
            hres= (HttpServletResponse) sres;
        }
        // 分配一个servlet去处理请求
        try{
            servlet = wrapper.allocate();
            if (hreq!=null&&hres!=null){
                servlet.service(hreq,hres);
            }else {
                servlet.service(sreq,sres);
            }

        }catch (ServletException e){
            System.out.println("can not allocate calss!");
        }

    }
}
