# Thin version of Tomcat

The aim with this project is to create a minimalist or 'thin' version of Tomcat, which can be package with a WAR file or even Docker container.

## TODO list
- Add Docker File
- Implement HTTPS Connector
- Sort out logging config (maybe use syslog)
- Investigate other security aspects of Tomcat
- Investigate the possibility to integrate with secret management software (e.g. Docker secrets or HashiCorp Vault)
