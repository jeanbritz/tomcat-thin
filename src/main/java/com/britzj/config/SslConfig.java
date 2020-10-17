package com.britzj.config;

public class SslConfig {

  private boolean enabled = false;
  private String protocol = "TLSv1.2";

  public boolean isEnabled() {
    return enabled;
  }

  public String getProtocol() {
    return protocol;
  }

}
