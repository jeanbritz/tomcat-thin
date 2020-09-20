package com.britzj;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TomcatPaths {

  private TomcatPaths() {
    // Forbid instance
  }

  private static final String USER_DIR = System.getProperty("user.dir");

  public static Path getBasePath() {
    return Paths.get(USER_DIR);
  }

  public static Path getWebAppsPath() {
    return Paths.get(USER_DIR, "webapps");
  }

  public static Path getConfPath() {
    return Paths.get(USER_DIR, "conf");
  }

}
