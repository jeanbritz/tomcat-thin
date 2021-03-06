package com.britzj;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Common paths used with Tomcat.
 */
public class TomcatPaths {

  private TomcatPaths() {
    // Forbid instance
  }

  private static String USER_DIR = System.getProperty("catalina.home");

  static {
    if(USER_DIR == null) {
      USER_DIR = System.getProperty("user.dir");
    }
  }


  public static Path getBasePath() {
    return Paths.get(USER_DIR);
  }

  /**
   *
   * @return
   */
  public static Path getWebAppsPath() {
    return Paths.get(USER_DIR, "webapps");
  }


  /**
   *
   * @return
   */
  public static Path getConfPath() {
    return Paths.get(USER_DIR, "conf");
  }

}
