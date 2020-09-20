package com.britzj;

import java.io.File;

public class TomcatFiles {

  private TomcatFiles() {
    // Forbid instance
  }

  private static final String TOMCAT_YML = "tomcat.yml";
  private static final String WEB_XML = "web.xml";

  public static File getGlobalWebXmlFile() {
    return TomcatPaths.getConfPath().resolve(WEB_XML).toFile();
  }

  public static File getTomcatYmlFile() {
    return TomcatPaths.getConfPath().resolve(TOMCAT_YML).toFile();
  }

}
