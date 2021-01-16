package com.britzj;

import java.io.File;

/**
 * Common files used within Tomcat
 */
public class ConfigFiles {

  private ConfigFiles() {
    // Forbid instance
  }

  private static final String TOMCAT_YML = "tomcat-thin.yml";
  private static final String CONTEXT_XML = "context.xml";
  private static final String WEB_XML = "web.xml";
  private static final String VAULT_XML = "hashicorp-vault.yml";


  /**
   *
   * @return
   */
  public static File getGlobalContextXmlFile() {
    return ConfigPaths.getConfPath().resolve(CONTEXT_XML).toFile();
  }

  /**
   *
   * @return
   */
  public static File getGlobalWebXmlFile() {
    return ConfigPaths.getConfPath().resolve(WEB_XML).toFile();
  }

  /**
   *
   * @return
   */
  public static File getTomcatYmlFile() {
    return ConfigPaths.getConfPath().resolve(TOMCAT_YML).toFile();
  }

  public static File getHashicorpVaultYmlFile() {
    return ConfigPaths.getConfPath().resolve(VAULT_XML).toFile();
  }

}
