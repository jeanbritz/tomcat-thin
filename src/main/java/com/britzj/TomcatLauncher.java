package com.britzj;

import com.britzj.config.ConfigProcessor;
import com.britzj.config.TomcatConfig;
import com.britzj.config.WebApp;
import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.Valve;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.mbeans.GlobalResourcesLifecycleListener;
import org.apache.catalina.security.SecurityListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.VersionLoggerListener;
import org.apache.catalina.valves.ErrorReportValve;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.util.Map;

public class TomcatLauncher {

    private static final Log log = LogFactory.getLog(TomcatLauncher.class.getName());

    public static void main( String[] args ) {
        log.info( "Starting Tomcat Server" );

        if(!preChecks()) {
            System.exit(1);
        }

        ConfigProcessor configProcessor = ConfigProcessor.getInstance();

        TomcatConfig config = configProcessor.load();
        Tomcat tomcat = new Tomcat();
        tomcat.setHostname(config.getServerConfig().getHostname());
        tomcat.getHost().getPipeline().addValve(getErrorReportValve());

        // Very important
        tomcat.setBaseDir(TomcatPaths.getBasePath().toString());
        tomcat.setAddDefaultWebXmlToWebapp(false);

        for (Map.Entry<String, WebApp> apps : config.getWebApps().getInstances().entrySet()) {
            WebApp webApp = apps.getValue();
            log.info(String.format("Registering %s web application at %s", apps.getKey(), webApp.getContext()));
            StandardContext standardContext = (StandardContext) tomcat.addWebapp(webApp.getContext(),
                    TomcatPaths.getWebAppsPath().resolve(webApp.getWarFile()).toString());
            standardContext.setDefaultContextXml(TomcatFiles.getGlobalContextXmlFile().getAbsolutePath());
            standardContext.setDefaultWebXml(TomcatFiles.getGlobalWebXmlFile().getAbsolutePath());
            addDefaultListeners(standardContext);
        }

        try {
            Server server = tomcat.getServer();
            tomcat.start();
            server.await();
        } catch (Exception e) {
            log.error("Exception occurred", e);
        }

    }

    private static boolean preChecks() {
        if(!TomcatFiles.getGlobalWebXmlFile().exists()) {
            log.error("Global 'web.xml' could not be located at " + TomcatPaths.getConfPath());
            return false;
        }
        if(!TomcatFiles.getTomcatYmlFile().exists()) {
            log.error("'tomcat.yml' could not be located at " + TomcatPaths.getConfPath());
            return false;
        }
        return true;
    }

    private static void addDefaultListeners(Context context) {
        context.addLifecycleListener(new VersionLoggerListener());
        context.addLifecycleListener(new SecurityListener());
        context.addLifecycleListener(new JreMemoryLeakPreventionListener());
        context.addLifecycleListener(new GlobalResourcesLifecycleListener());
        context.addLifecycleListener(new ThreadLocalLeakPreventionListener());
    }

    private static Valve getErrorReportValve() {
        ErrorReportValve valve = new ErrorReportValve();
        valve.setShowReport(false);
        valve.setShowServerInfo(false);
        return valve;
    }
}
