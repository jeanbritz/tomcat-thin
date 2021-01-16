package com.britzj.config;

import de.stklcode.jvault.connector.VaultConnector;
import de.stklcode.jvault.connector.builder.VaultConnectorBuilder;
import de.stklcode.jvault.connector.exception.VaultConnectorException;
import de.stklcode.jvault.connector.model.response.AuthResponse;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.Tag;

public class VaultPropertyConstructor extends Constructor {

  private VaultConnector vault;
  private HashicorpVaultConfig config;

  public VaultPropertyConstructor(HashicorpVaultConfig config) {
    super();
    yamlConstructors.put(new Tag(Tag.PREFIX + "vault"), new VaultSecretConstruct());

    this.vault = VaultConnectorBuilder.http()
            .withHost(config.getHost())
            .withPort(config.getPort())
            .withoutTLS()
            .build();
    this.config = config;
  }

  class VaultSecretConstruct implements Construct {

    @Override
    public Object construct(Node nnode) {
      if(nnode instanceof ScalarNode) {
        try {
          ScalarNode scalarNode = (ScalarNode) nnode;
          AuthResponse authResponse = vault.authAppRole("df5ddab4-967f-78e6-c622-c0aae1c6a491","25ee2d17-edec-0d47" +
                  "-db3d" +
                  "-966ba0d68f7a" );
          if(!"secret".equals(config.getMount())) {
            return vault.readSecretData(config.getMount(), config.getPath()).get(scalarNode.getValue());
          } else {
            return vault.readSecret(config.getPath()).get(scalarNode.getValue());
          }
          } catch (VaultConnectorException e) {
          e.printStackTrace();
        }

      }
        return null;

    }

    @Override
    public void construct2ndStep(Node node, Object object) {
      System.out.println(node.getTag());
    }
  }
}
