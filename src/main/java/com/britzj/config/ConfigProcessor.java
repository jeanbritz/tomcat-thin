package com.britzj.config;


import com.britzj.TomcatFiles;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigProcessor {

  private static final Log log = LogFactory.getLog(ConfigProcessor.class.getName());
  private static final ConfigProcessor instance = new ConfigProcessor();

  public static ConfigProcessor getInstance() {
    return instance;
  }

  public TomcatConfig load() {
    File yamlFile  = TomcatFiles.getTomcatYmlFile();
//    Constructor constructor = new Constructor(Server.class);
//    TypeDescription customTypeDescription = new TypeDescription(Server.class);
//    customTypeDescription.addPropertyParameters("server", Server.class);
//    constructor.addTypeDescription(customTypeDescription);
    Yaml yaml = new Yaml();
    try(InputStream is = Files.newInputStream(yamlFile.toPath())) {
      return yaml.loadAs(is, TomcatConfig.class);
    } catch (IOException e) {
      log.error("Failed to load 'tomcat.yml'");
    }
    return null;
  }
}
