package com.britzj.config;

import java.util.Map;

public class WebApps {

  public Map<String, WebApp> instances;

  public Map<String, WebApp> getInstances() {
    return instances;
  }

  public void setInstances(Map<String, WebApp> instancesMap) {
    this.instances = instancesMap;
  }
}
