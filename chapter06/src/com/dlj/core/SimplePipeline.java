package com.dlj.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/29/21 3:13 PM
 */
public class SimplePipeline implements Pipeline ,Lifecycle{
    public SimplePipeline(Container container) {
        setContainer(container);
    }

    public Valve getBasic() {
        return null;
    }

    //基本阀门
    protected Valve basic = null;

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    // 处理请求的容器
    protected Container container = null;
    protected Valve[] valves = new Valve[0];

    // 设置基本阀门
    public void setBasic(Valve valve) {

        this.basic = valve;
        // 设置基本阀门关联容器，这个我还不知道为啥要设置
        ((Contained) valve).setContainer(container);
    }

    public void addValve(Valve valve) {
        if (valve instanceof Contained) {
            ((Contained) valve).setContainer(container);
        }
        synchronized (valves) {
            // 创建一个新的Valve数组
            Valve[] results = new Valve[valves.length + 1];
            //将原来的数组给拷贝进去
            System.arraycopy(valves, 0, results, 0, valves.length);
            results[valves.length]=valve;
            valves = results;
        }
    }

    public Valve[] getValves() {
        return new Valve[0];
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        // Invoke the first Valve in this pipeline for this request
        (new SimplePipelineValveContext()).invokeNext(request, response);
    }

    public void removeValve(Valve valve) {

    }

    public void addLifecycleListener(LifecycleListener listener) {

    }

    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    public void removeLifecycleListener(LifecycleListener listener) {

    }

    public void start() throws LifecycleException {
        System.out.println("simplepipeline is started");
    }

    public void stop() throws LifecycleException {

    }

    // 定义valve上下文，为了能够处理valve数组，这里使用了责任链模式，不过这个设计模式我还没看过
    protected class SimplePipelineValveContext implements ValveContext {
        protected int stage = 0; // 使用subscript和stage来标识被唤醒的阀门

        public String getInfo() {
            return null;
        }

        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            int subscript = stage;
            stage = stage + 1;
            // Invoke the requested Valve for the current request thread
            if (subscript < valves.length) {
                valves[subscript].invoke(request, response, this);
            } else if ((subscript == valves.length) && (basic != null)) {
                basic.invoke(request, response, this);
            } else {
                throw new ServletException("No valve");
            }
        }
    }
}
