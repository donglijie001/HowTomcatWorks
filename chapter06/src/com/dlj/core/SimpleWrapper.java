package com.dlj.core;

import org.apache.catalina.*;
import org.apache.catalina.util.LifecycleSupport;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/29/21 4:51 PM
 */
public class SimpleWrapper implements Wrapper, Pipeline ,Lifecycle{
    private Servlet instance = null;
    private String servletClass;
    private Loader loader;
    private String name;
    private SimplePipeline pipeline = new SimplePipeline(this);
    protected boolean started = false;
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);


    public SimpleWrapper() {
        pipeline.setBasic(new SimpleWrapperValve());
    }

    // 该包装器的父容器
    protected Container parent = null;

    public Valve getBasic() {
        return null;
    }

    public void setBasic(Valve valve) {

    }

    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    public Valve[] getValves() {
        return new Valve[0];
    }

    public void removeValve(Valve valve) {

    }

    public String getInfo() {
        return null;
    }

    public Loader getLoader() {
        if (loader != null)
            return loader;
        if (parent != null) {
            return parent.getLoader();
        }
        return null;
    }

    public void setLoader(Loader loader) {
        this.loader=loader;
    }

    public Logger getLogger() {
        return null;
    }

    public void setLogger(Logger logger) {

    }

    public Manager getManager() {
        return null;
    }

    public void setManager(Manager manager) {

    }

    public Cluster getCluster() {
        return null;
    }

    public void setCluster(Cluster cluster) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public Container getParent() {
        return parent;
    }

    public void setParent(Container container) {
        parent=container;
    }

    public ClassLoader getParentClassLoader() {
        return null;
    }

    public void setParentClassLoader(ClassLoader parent) {

    }

    public Realm getRealm() {
        return null;
    }

    public void setRealm(Realm realm) {

    }

    public DirContext getResources() {
        return null;
    }

    public void setResources(DirContext resources) {

    }

    public void addChild(Container child) {

    }

    public void addContainerListener(ContainerListener listener) {

    }

    public void addMapper(Mapper mapper) {

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    public Container findChild(String name) {
        return null;
    }

    public Container[] findChildren() {
        return new Container[0];
    }

    public ContainerListener[] findContainerListeners() {
        return new ContainerListener[0];
    }

    public Mapper findMapper(String protocol) {
        return null;
    }

    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request, response);
    }

    public Container map(Request request, boolean update) {
        return null;
    }

    public void removeChild(Container child) {

    }

    public void removeContainerListener(ContainerListener listener) {

    }

    public void removeMapper(Mapper mapper) {

    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    public long getAvailable() {
        return 0;
    }

    public void setAvailable(long available) {

    }

    public String getJspFile() {
        return null;
    }

    public void setJspFile(String jspFile) {

    }

    public int getLoadOnStartup() {
        return 0;
    }

    public void setLoadOnStartup(int value) {

    }

    public String getRunAs() {
        return null;
    }

    public void setRunAs(String runAs) {

    }

    public String getServletClass() {
        return this.servletClass;
    }

    public void setServletClass(String servletClass) {
        this.servletClass=servletClass;
    }

    public boolean isUnavailable() {
        return false;
    }

    public void addInitParameter(String name, String value) {

    }

    public void addInstanceListener(InstanceListener listener) {

    }

    public void addSecurityReference(String name, String link) {

    }

    public Servlet allocate() throws ServletException {
        // 定位servlet
        if (instance == null) {
            try {
                instance = loadServlet();
            }catch (ServletException e){
                throw e;
            }catch (Throwable e){
                throw new ServletException("Cannot allocate a servlet instance");
            }
        }
        return instance;
    }

    private Servlet loadServlet() throws ServletException {
        if (instance != null) {
            return instance;
        }
        Servlet servlet = null;
        String actualClass = servletClass;
        if (actualClass == null) {
            throw new ServletException("servlet class has not been specified");
        }
        // 获取类加载器
        Loader loader = getLoader();
        if (loader == null) {
            throw new ServletException("No loader.");
        }
        ClassLoader classLoader = loader.getClassLoader();
        // 加载 servlet class
        Class classClass = null;
        try {
            if (classLoader != null) {
                classClass = classLoader.loadClass(actualClass);
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Servlet class not found");
        }
        // 初始化servlet 实例
        try {
            servlet = (Servlet) classClass.newInstance();
        } catch (Throwable e) {
            throw new ServletException("Failed to instantiate servlet");
        }
        // 初始化servelt
        try {
            servlet.init(null);
        } catch (Throwable e) {
            throw new ServletException("Failed to initialize servlet");
        }
        return servlet;


    }

    public void deallocate(Servlet servlet) throws ServletException {

    }

    public String findInitParameter(String name) {
        return null;
    }

    public String[] findInitParameters() {
        return new String[0];
    }

    public String findSecurityReference(String name) {
        return null;
    }

    public String[] findSecurityReferences() {
        return new String[0];
    }

    public void load() throws ServletException {
        instance=loadServlet();
    }

    public void removeInitParameter(String name) {

    }

    public void removeInstanceListener(InstanceListener listener) {

    }

    public void removeSecurityReference(String name) {

    }

    public void unavailable(UnavailableException unavailable) {

    }

    public void unload() throws ServletException {

    }
    // lifecycl方法
    public void addLifecycleListener(LifecycleListener listener) {

    }

    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    public void removeLifecycleListener(LifecycleListener listener) {

    }

    public void start() throws LifecycleException {
        System.out.println("Starting Wrapper " + name);
        if (started)
            throw new LifecycleException("Wrapper already started");

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
        started = true;

        // Start our subordinate components, if any
        if ((loader != null) && (loader instanceof Lifecycle))
            ((Lifecycle) loader).start();

        // Start the Valves in our pipeline (including the basic), if any
        if (pipeline instanceof Lifecycle)
            ((Lifecycle) pipeline).start();

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
    }

    public void stop() throws LifecycleException {
        System.out.println("Stopping wrapper " + name);
        // Shut down our servlet instance (if it has been initialized)
        try {
            instance.destroy();
        }
        catch (Throwable t) {
        }
        instance = null;
        if (!started)
            throw new LifecycleException("Wrapper " + name + " not started");
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;

        // Stop the Valves in our pipeline (including the basic), if any
        if (pipeline instanceof Lifecycle) {
            ((Lifecycle) pipeline).stop();
        }

        // Stop our subordinate components, if any
        if ((loader != null) && (loader instanceof Lifecycle)) {
            ((Lifecycle) loader).stop();
        }

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
    }
}
