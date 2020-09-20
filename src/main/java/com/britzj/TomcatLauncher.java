package com.britzj;

import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.mbeans.GlobalResourcesLifecycleListener;
import org.apache.catalina.security.SecurityListener;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.VersionLoggerListener;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class TomcatLauncher {

    private static final Log log = LogFactory.getLog(TomcatLauncher.class.getName());

    public static void main( String[] args ) {
        log.info( "Starting Tomcat Server" );

        if(preChecks()) {
            Tomcat tomcat = new Tomcat();

            // Very important
            tomcat.setBaseDir(TomcatPaths.getBasePath().toString());
            tomcat.setAddDefaultWebXmlToWebapp(false);

            try {
                Server server = tomcat.getServer();
                tomcat.start();
                server.await();
            } catch (Exception e) {
                log.error("Exception occurred", e);
            }
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
}
