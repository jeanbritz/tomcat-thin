package com.britzj.config;


import com.britzj.TomcatFiles;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Processor utility class for YAML file.
 */
public class ConfigProcessor {

  private static final Log log = LogFactory.getLog(ConfigProcessor.class.getName());
  private static final ConfigProcessor instance = new ConfigProcessor();

  public static ConfigProcessor getInstance() {
    return instance;
  }

  /**
   * Loads the config in 'tomcat.yml' into designated POJO objects
   * @return Instance of {@link TomcatConfig} with data of 'tomcal.yml' populated
   */
  public TomcatConfig load() {
    File yamlFile = TomcatFiles.getTomcatYmlFile();
    Yaml yaml = new Yaml();
    try (InputStream is = Files.newInputStream(yamlFile.toPath())) {
      return yaml.loadAs(is, TomcatConfig.class);
    } catch (IOException e) {
      log.error("Failed to load 'tomcat.yml'");
    }
    return null;
  }
}
