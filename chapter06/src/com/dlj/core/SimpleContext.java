package com.dlj.core;

import org.apache.catalina.*;
import org.apache.catalina.deploy.*;
import org.apache.catalina.util.CharsetMapper;
import org.apache.catalina.util.LifecycleSupport;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/30/21 11:36 AM
 */
public class SimpleContext implements Context, Pipeline, Lifecycle {
    protected HashMap children = new HashMap();
    protected Loader loader = null;
    protected SimplePipeline pipeline = new SimplePipeline(this);
    protected HashMap servletMappings = new HashMap();
    protected Mapper mapper = null;
    protected HashMap mappers = new HashMap();
    private Container parent = null;

    private Logger logger=null;

    protected boolean started = false;
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);
    public SimpleContext() {
        pipeline.setBasic(new SimpleContextValve());
    }

    public String getInfo() {
        return null;
    }

    public Loader getLoader() {
        if (loader != null)
            return (loader);
        if (parent != null)
            return (parent.getLoader());
        return (null);
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger=logger;
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
        return null;
    }

    public void setName(String name) {

    }

    public Container getParent() {
        return null;
    }

    public void setParent(Container container) {

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
        child.setParent((Container) this);
        children.put(child.getName(), child);
    }

    public void addContainerListener(ContainerListener listener) {

    }

    public void addMapper(Mapper mapper) {
// this method is adopted from addMapper in ContainerBase
        // the first mapper added becomes the default mapper
        mapper.setContainer((Container) this);      // May throw IAE
        this.mapper = mapper;
        synchronized (mappers) {
            if (mappers.get(mapper.getProtocol()) != null)
                throw new IllegalArgumentException("addMapper:  Protocol '" +
                        mapper.getProtocol() + "' is not unique");
            mapper.setContainer((Container) this);      // May throw IAE
            mappers.put(mapper.getProtocol(), mapper);
            if (mappers.size() == 1)
                this.mapper = mapper;
            else
                this.mapper = null;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    public Container findChild(String name) {
        if (name == null)
            return (null);
        synchronized (children) {       // Required by post-start changes
            return ((Container) children.get(name));
        }
    }

    public Container[] findChildren() {
        return new Container[0];
    }

    public ContainerListener[] findContainerListeners() {
        return new ContainerListener[0];
    }

    public Mapper findMapper(String protocol) {
        // the default mapper will always be returned, if any,
        // regardless the value of protocol
        if (mapper != null)
            return (mapper);
        else
            synchronized (mappers) {
                return ((Mapper) mappers.get(protocol));
            }
    }

    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        System.out.println("context invoke");
        pipeline.invoke(request, response);
    }

    public Container map(Request request, boolean update) {
        //this method is taken from the map method in org.apache.cataline.core.ContainerBase
        //the findMapper method always returns the default mapper, if any, regardless the
        //request's protocol
        Mapper mapper = findMapper(request.getRequest().getProtocol());
        if (mapper == null)
            return (null);

        // Use this Mapper to perform this mapping
        return (mapper.map(request, update));
    }

    public void removeChild(Container child) {

    }

    public void removeContainerListener(ContainerListener listener) {

    }

    public void removeMapper(Mapper mapper) {

    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }

    public Object[] getApplicationListeners() {
        return new Object[0];
    }

    public void setApplicationListeners(Object[] listeners) {

    }

    public boolean getAvailable() {
        return false;
    }

    public void setAvailable(boolean available) {

    }

    public CharsetMapper getCharsetMapper() {
        return null;
    }

    public void setCharsetMapper(CharsetMapper mapper) {

    }

    public boolean getConfigured() {
        return false;
    }

    public void setConfigured(boolean configured) {

    }

    public boolean getCookies() {
        return false;
    }

    public void setCookies(boolean cookies) {

    }

    public boolean getCrossContext() {
        return false;
    }

    public void setCrossContext(boolean crossContext) {

    }

    public String getDisplayName() {
        return null;
    }

    public void setDisplayName(String displayName) {

    }

    public boolean getDistributable() {
        return false;
    }

    public void setDistributable(boolean distributable) {

    }

    public String getDocBase() {
        return null;
    }

    public void setDocBase(String docBase) {

    }

    public LoginConfig getLoginConfig() {
        return null;
    }

    public void setLoginConfig(LoginConfig config) {

    }

    public NamingResources getNamingResources() {
        return null;
    }

    public void setNamingResources(NamingResources namingResources) {

    }

    public String getPath() {
        return null;
    }

    public void setPath(String path) {

    }

    public String getPublicId() {
        return null;
    }

    public void setPublicId(String publicId) {

    }

    public boolean getReloadable() {
        return false;
    }

    public void setReloadable(boolean reloadable) {

    }

    public boolean getOverride() {
        return false;
    }

    public void setOverride(boolean override) {

    }

    public boolean getPrivileged() {
        return false;
    }

    public void setPrivileged(boolean privileged) {

    }

    public ServletContext getServletContext() {
        return null;
    }

    public int getSessionTimeout() {
        return 0;
    }

    public void setSessionTimeout(int timeout) {

    }

    public String getWrapperClass() {
        return null;
    }

    public void setWrapperClass(String wrapperClass) {

    }

    public void addApplicationListener(String listener) {

    }

    public void addApplicationParameter(ApplicationParameter parameter) {

    }

    public void addConstraint(SecurityConstraint constraint) {

    }

    public void addEjb(ContextEjb ejb) {

    }

    public void addEnvironment(ContextEnvironment environment) {

    }

    public void addErrorPage(ErrorPage errorPage) {

    }

    public void addFilterDef(FilterDef filterDef) {

    }

    public void addFilterMap(FilterMap filterMap) {

    }

    public void addInstanceListener(String listener) {

    }

    public void addLocalEjb(ContextLocalEjb ejb) {

    }

    public void addMimeMapping(String extension, String mimeType) {

    }

    public void addParameter(String name, String value) {

    }

    public void addResource(ContextResource resource) {

    }

    public void addResourceEnvRef(String name, String type) {

    }

    public void addResourceLink(ContextResourceLink resourceLink) {

    }

    public void addRoleMapping(String role, String link) {

    }

    public void addSecurityRole(String role) {

    }

    public void addServletMapping(String pattern, String name) {
        synchronized (servletMappings) {
            servletMappings.put(pattern, name);
        }
    }

    public void addTaglib(String uri, String location) {

    }

    public void addWelcomeFile(String name) {

    }

    public void addWrapperLifecycle(String listener) {

    }

    public void addWrapperListener(String listener) {

    }

    public Wrapper createWrapper() {
        return null;
    }

    public String[] findApplicationListeners() {
        return new String[0];
    }

    public ApplicationParameter[] findApplicationParameters() {
        return new ApplicationParameter[0];
    }

    public SecurityConstraint[] findConstraints() {
        return new SecurityConstraint[0];
    }

    public ContextEjb findEjb(String name) {
        return null;
    }

    public ContextEjb[] findEjbs() {
        return new ContextEjb[0];
    }

    public ContextEnvironment findEnvironment(String name) {
        return null;
    }

    public ContextEnvironment[] findEnvironments() {
        return new ContextEnvironment[0];
    }

    public ErrorPage findErrorPage(int errorCode) {
        return null;
    }

    public ErrorPage findErrorPage(String exceptionType) {
        return null;
    }

    public ErrorPage[] findErrorPages() {
        return new ErrorPage[0];
    }

    public FilterDef findFilterDef(String filterName) {
        return null;
    }

    public FilterDef[] findFilterDefs() {
        return new FilterDef[0];
    }

    public FilterMap[] findFilterMaps() {
        return new FilterMap[0];
    }

    public String[] findInstanceListeners() {
        return new String[0];
    }

    public ContextLocalEjb findLocalEjb(String name) {
        return null;
    }

    public ContextLocalEjb[] findLocalEjbs() {
        return new ContextLocalEjb[0];
    }

    public String findMimeMapping(String extension) {
        return null;
    }

    public String[] findMimeMappings() {
        return new String[0];
    }

    public String findParameter(String name) {
        return null;
    }

    public String[] findParameters() {
        return new String[0];
    }

    public ContextResource findResource(String name) {
        return null;
    }

    public String findResourceEnvRef(String name) {
        return null;
    }

    public String[] findResourceEnvRefs() {
        return new String[0];
    }

    public ContextResourceLink findResourceLink(String name) {
        return null;
    }

    public ContextResourceLink[] findResourceLinks() {
        return new ContextResourceLink[0];
    }

    public ContextResource[] findResources() {
        return new ContextResource[0];
    }

    public String findRoleMapping(String role) {
        return null;
    }

    public boolean findSecurityRole(String role) {
        return false;
    }

    public String[] findSecurityRoles() {
        return new String[0];
    }

    public String findServletMapping(String pattern) {
        synchronized (servletMappings) {
            return ((String) servletMappings.get(pattern));
        }
    }

    public String[] findServletMappings() {
        return new String[0];
    }

    public String findStatusPage(int status) {
        return null;
    }

    public int[] findStatusPages() {
        return new int[0];
    }

    public String findTaglib(String uri) {
        return null;
    }

    public String[] findTaglibs() {
        return new String[0];
    }

    public boolean findWelcomeFile(String name) {
        return false;
    }

    public String[] findWelcomeFiles() {
        return new String[0];
    }

    public String[] findWrapperLifecycles() {
        return new String[0];
    }

    public String[] findWrapperListeners() {
        return new String[0];
    }

    public void reload() {

    }

    public void removeApplicationListener(String listener) {

    }

    public void removeApplicationParameter(String name) {

    }

    public void removeConstraint(SecurityConstraint constraint) {

    }

    public void removeEjb(String name) {

    }

    public void removeEnvironment(String name) {

    }

    public void removeErrorPage(ErrorPage errorPage) {

    }

    public void removeFilterDef(FilterDef filterDef) {

    }

    public void removeFilterMap(FilterMap filterMap) {

    }

    public void removeInstanceListener(String listener) {

    }

    public void removeLocalEjb(String name) {

    }

    public void removeMimeMapping(String extension) {

    }

    public void removeParameter(String name) {

    }

    public void removeResource(String name) {

    }

    public void removeResourceEnvRef(String name) {

    }

    public void removeResourceLink(String name) {

    }

    public void removeRoleMapping(String role) {

    }

    public void removeSecurityRole(String role) {

    }

    public void removeServletMapping(String pattern) {

    }

    public void removeTaglib(String uri) {

    }

    public void removeWelcomeFile(String name) {

    }

    public void removeWrapperLifecycle(String listener) {

    }

    public void removeWrapperListener(String listener) {

    }

    public Valve getBasic() {
        return pipeline.getBasic();
    }

    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    public Valve[] getValves() {
        return pipeline.getValves();
    }

    public void removeValve(Valve valve) {
        pipeline.removeValve(valve);
    }

    // lifecycle 接口方法，添加监听事件
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycle.addLifecycleListener(listener);
    }

    public LifecycleListener[] findLifecycleListeners() {
        return new LifecycleListener[0];
    }

    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycle.removeLifecycleListener(listener);
    }

    public synchronized void start() throws LifecycleException {
        log("starting Context");
        if (started)
            throw new LifecycleException("SimpleContext has already started");

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
        started = true;
        try {
            // Start our subordinate components, if any
            if ((loader != null) && (loader instanceof Lifecycle))
                ((Lifecycle) loader).start();

            // Start our child containers, if any
            Container children[] = findChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i] instanceof Lifecycle)
                    ((Lifecycle) children[i]).start();
            }

            // Start the Valves in our pipeline (including the basic),
            // if any
            if (pipeline instanceof Lifecycle)
                ((Lifecycle) pipeline).start();
            // Notify our interested LifecycleListeners
            lifecycle.fireLifecycleEvent(START_EVENT, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
    }

    public void stop() throws LifecycleException {
        if (!started)
            throw new LifecycleException("SimpleContext has not been started");
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;
        try {
            // Stop the Valves in our pipeline (including the basic), if any
            if (pipeline instanceof Lifecycle) {
                ((Lifecycle) pipeline).stop();
            }

            // Stop our child containers, if any
            Container children[] = findChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i] instanceof Lifecycle)
                    ((Lifecycle) children[i]).stop();
            }
            if ((loader != null) && (loader instanceof Lifecycle)) {
                ((Lifecycle) loader).stop();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
        log(" Context started!");
    }
    private void log(String message) {
        Logger logger = this.getLogger();
        if (logger!=null)
            logger.log(message);
    }
}
