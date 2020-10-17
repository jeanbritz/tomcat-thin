package com.britzj.config;

import java.util.Map;

public class TomcatConfig {

  public Server server;

  public WebApps webapps;

  public Map<String, ConnectorConfig> connectors;

  public Server getServerConfig() {
    return server;
  }

  public WebApps getWebApps() {
    return webapps;
  }

  public Map<String, ConnectorConfig> getConnectors() {
    return connectors;
  }

}
