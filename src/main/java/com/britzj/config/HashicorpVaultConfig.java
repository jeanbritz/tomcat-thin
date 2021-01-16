package com.britzj.config;

public class HashicorpVaultConfig {

  public String host;
  public int port;
  public String mount;
  public String path;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getMount() {
    return mount;
  }

  public void setMount(String mount) {
    this.mount = mount;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
