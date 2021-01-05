# Thin version of Tomcat

The aim with this project is to create a minimalist or 'thin' version of Tomcat, which can be package with a WAR file or even Docker container.

## TODO list
- Sort out logging config (maybe use syslog)
- Investigate other security aspects of Tomcat
- Investigate the possibility to integrate with secret management software (e.g. Docker secrets or HashiCorp Vault)

## TLS Config
Currently, all TLS/SSL Config is done in `tomcat-thin.yml`.

See example below:
```yaml
server:
  hostname: localhost
  
connectors:
  http:
    protocol: HTTP/1.1
    port: 8080
    redirectPort: 8443

  https:
    protocol: HTTP/1.1
    port: 8443
    secure: true
    properties:
      keyAlias: "tomcat"
      keystorePass: "tomcat"
      keystoreType: "JKS"
      keystoreFile: "selfsigned.jks"
      sslProtocol: "TLSv1.2"
      SSLEnabled: true

```

To generate your own self-signed certificate you can use `keytool` as follows:
```shell
keytool -genkey -keyalg RSA -alias tomcat -keystore selfsigned.jks -validity 120 -keysize 2048
```