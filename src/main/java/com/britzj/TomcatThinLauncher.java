package com.britzj;

import com.britzj.config.ConfigProcessor;
import com.britzj.config.ConnectorConfig;
import com.britzj.config.TomcatConfig;
import com.britzj.config.WebApp;
import org.apache.catalina.Context;
import org.apache.catalina.Server;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Connector;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main class
 * Launches the embedded version of Tomcat
 */
public class TomcatThinLauncher {

  private static final Log log = LogFactory.getLog(TomcatThinLauncher.class.getName());

  public static void main(String[] args) {
    log.info("Starting Tomcat Server");

    if (!preChecks()) {
      System.exit(1);
    }

    ConfigProcessor configProcessor = ConfigProcessor.getInstance();

    TomcatConfig config = configProcessor.load();
    Tomcat tomcat = new Tomcat();

    tomcat.setBaseDir(TomcatPaths.getBasePath().toString());
    tomcat.setHostname(config.getServerConfig().getHostname());
    tomcat.setPort(config.getServerConfig().getPort());
    tomcat.getHost().getPipeline().addValve(createErrorReportValve());

    // Creating Connectors
    List<Connector> connectors = createConnectors(config.getConnectors());
    connectors.forEach(tomcat::setConnector);

    tomcat.setAddDefaultWebXmlToWebapp(false);

    for (Map.Entry<String, WebApp> apps : config.getWebApps().getInstances().entrySet()) {
      WebApp webApp = apps.getValue();
      log.info(String.format("Registering %s web application at %s", apps.getKey(), webApp.getContext()));
      StandardContext standardContext = (StandardContext) tomcat.addWebapp("/" + webApp.getContext(),
              TomcatPaths.getWebAppsPath().resolve(webApp.getWarFile()).toString());
      standardContext.setDefaultContextXml(TomcatFiles.getGlobalContextXmlFile().getAbsolutePath());
      standardContext.setDefaultWebXml(TomcatFiles.getGlobalWebXmlFile().getAbsolutePath());
      addDefaultListeners(standardContext);
    }

    try {
      Server server = tomcat.getServer();
      server.setPort(-1); // Disable shutdown port
      tomcat.start();
      log.info("Tomcat Started!");
      server.await();
    } catch (Exception e) {
      log.error("Exception occurred", e);
    }

  }

  /**
   * @return
   */
  private static boolean preChecks() {
    if (!TomcatFiles.getGlobalWebXmlFile().exists()) {
      log.error("Global 'web.xml' could not be located at " + TomcatPaths.getConfPath());
      return false;
    }
    if (!TomcatFiles.getTomcatYmlFile().exists()) {
      log.error("'tomcat.yml' could not be located at " + TomcatPaths.getConfPath());
      return false;
    }
    return true;
  }

  /**
   * @param context
   */
  private static void addDefaultListeners(Context context) {
    context.addLifecycleListener(new VersionLoggerListener());
    context.addLifecycleListener(new SecurityListener());
    context.addLifecycleListener(new JreMemoryLeakPreventionListener());
    context.addLifecycleListener(new GlobalResourcesLifecycleListener());
    context.addLifecycleListener(new ThreadLocalLeakPreventionListener());
  }

  /**
   * @return
   */
  private static Valve createErrorReportValve() {
    ErrorReportValve valve = new ErrorReportValve();
    valve.setShowReport(false);
    valve.setShowServerInfo(false);
    return valve;
  }

  /**
   * Create Connector objects from the given config
   *
   * @param config Config
   * @return List of created Connectors
   */
  private static List<Connector> createConnectors(Map<String, ConnectorConfig> config) {
    List<Connector> connectors = new ArrayList<>();
    for (Map.Entry<String, ConnectorConfig> entry : config.entrySet()) {
      log.info("Creating connector [" + entry.getKey() + "]");
      ConnectorConfig connectorConfig = entry.getValue();
      Connector connector = new Connector(connectorConfig.getProtocol());
      connector.setPort(connectorConfig.getPort());
      connector.setRedirectPort(connectorConfig.getRedirectPort());
      connector.setSecure(connectorConfig.isSecure());
      Map<String, String> properties = connectorConfig.getProperties();
      if(properties != null) {
        for(Map.Entry<String, String> property : properties.entrySet()) {
          log.debug(String.format("Setting property [%s] for connector [%s] to [%s]", property.getKey(), entry.getKey(),
                  property.getValue()));
          connector.setProperty(property.getKey(), property.getValue());
        }
      }
      connectors.add(connector);
    }
    return connectors;
  }
}
