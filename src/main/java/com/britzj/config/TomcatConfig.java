package com.britzj.config;

public class TomcatConfig {

  public Server server;

  public WebApps webapps;

  public Server getServerConfig() {
    return server;
  }

  public void setServerConfig(Server serverConfig) {
    this.server = serverConfig;
  }

  public WebApps getWebApps() {
    return webapps;
  }

  public void setWebApps(WebApps webApps) {
    this.webapps = webApps;
  }
}
