package com.britzj.config;

import java.util.Map;

public class ConnectorConfig {

  public String protocol;
  public int port = -1;
  public int redirectPort = -1;
  public boolean secure = false;
  public Map<String, String> properties;

  public String getProtocol() {
    return protocol;
  }

  public int getPort() {
    return port;
  }

  public int getRedirectPort() {
    return this.redirectPort;
  }

  public boolean isSecure() {
    return this.secure;
  }

  public Map<String, String> getProperties() {
    return this.properties;
  }
}
