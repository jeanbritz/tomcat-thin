package com.britzj;

import java.io.File;

/**
 * Common files used within Tomcat
 */
public class TomcatFiles {

  private TomcatFiles() {
    // Forbid instance
  }

  private static final String TOMCAT_YML = "tomcat-thin.yml";
  private static final String CONTEXT_XML = "context.xml";
  private static final String WEB_XML = "web.xml";

  /**
   *
   * @return
   */
  public static File getGlobalContextXmlFile() {
    return TomcatPaths.getConfPath().resolve(CONTEXT_XML).toFile();
  }

  /**
   *
   * @return
   */
  public static File getGlobalWebXmlFile() {
    return TomcatPaths.getConfPath().resolve(WEB_XML).toFile();
  }

  /**
   *
   * @return
   */
  public static File getTomcatYmlFile() {
    return TomcatPaths.getConfPath().resolve(TOMCAT_YML).toFile();
  }

}
