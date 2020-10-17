package com.britzj.config;

public class Server {

  public String hostname = "localhost";

  public int port = -1;

  public SslConfig ssl;

  public String getHostname() {
    return hostname;
  }

  public int getPort() {
    return port;
  }

}
