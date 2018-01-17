
# Pré-Requisitos

* Maven (Testado na versão 3.5.2)
* Wildfly 11.0.0 Final: Utilizado o Wildfly com Teiid (não obrigatório) [http://teiid.jboss.org/downloads/](http://teiid.jboss.org/downloads/) 
* SGBD Relacional com datasource instalado. Testado em PostgreSQL 10.1.3 rodando sobre Windows: [https://www.postgresql.org/download/](https://www.postgresql.org/download/)
* Eclipse Oxygen 
* DataSource JDBC configurado no Wildfly conectando com o SGBD Relacional. 


*- Ex no PostgreSQL:

-- Baixar driver JDBC mais recente e copiá-lo para a pasta deployments do Wildfly. [https://jdbc.postgresql.org/download.html](https://jdbc.postgresql.org/download.html) 

-- Configure a conexão com o SGBD no Wildfly. Exemplo de datasource no standalone.xml: 

                <datasource jta="true" jndi-name="java:/TwitterLikeDS" pool-name="TwitterLikeDS" enabled="true" use-ccm="true">
                    <connection-url>jdbc:postgresql://localhost:5432/twitter_like</connection-url>
                    <driver-class>org.postgresql.Driver</driver-class>
                    <driver>postgresql-42.1.4.jar</driver>
                    <pool>
                        <min-pool-size>5</min-pool-size>
                        <initial-pool-size>5</initial-pool-size>
                        <max-pool-size>10</max-pool-size>
                        <flush-strategy>IdleConnections</flush-strategy>
                    </pool>
                    <security>
                        <user-name>twitter_like</user-name>
                        <password>123456</password>
                    </security>
                    <validation>
                        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
                        <background-validation>true</background-validation>
                        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
                    </validation>
                </datasource>



# Liquibase

Criar um arquivo ~/.m2/settings.xml com o seguinte conteúdo (substituindo os dados de conexão conforme necessário):

  <?xml version="1.0" encoding="UTF-8"?>
  <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                        https://maven.apache.org/xsd/settings-1.0.0.xsd">
    <localRepository>${user.home}/.m2/repository</localRepository>
    <interactiveMode>true</interactiveMode>
    <usePluginRegistry>false</usePluginRegistry>
    <offline>false</offline>
    
    <profiles>
      <profile>
        <id>default</id>
        
        <activation>
          <activeByDefault>true</activeByDefault>
        </activation>
        <properties>
            
          <!-- Base de dados de exemplo -->
          <twitterLike.jdbc.driver>org.postgresql.Driver</twitterLike.jdbc.driver>
          <twitterLike.jdbc.url>jdbc:postgresql://localhost:5432/twitter_like</twitterLike.jdbc.url>
          <twitterLike.jdbc.username>twitter_like</twitterLike.jdbc.username>
          <twitterLike.jdbc.password>123456</twitterLike.jdbc.password>
          
        </properties>
      </profile>
    </profiles>
  </settings>
  
Em seguida, execute mvn liquibase:update
  
# OAuth

Acrescente ao standalone.xml (após a seção extensions): 
  
    <system-properties>
        <property name="login.oauth2.client_secret" value="XXX"/>
        <property name="login.oauth2.client_id" value="XXX"/>
        <property name="login.oauth2.redirect_uri" value="http://localhost:8080/twitterLike/"/>
        <property name="login.oauth2.access_token_uri" value="https://graph.facebook.com/v2.9/oauth/access_token"/>
        <property name="login.oauth2.search_user_from_token_uri" value="https://graph.facebook.com/me"/>
    </system-properties>
  
# Versionamento de API

Utiliza-se versionamento por URL, ex: 

[http://localhost:8080/twitterLike/](http://localhost:8080/twitterLike/) é a versão mais atual.
[http://localhost:8080/twitterLike-0.0.1-SNAPSHOT/](http://localhost:8080/twitterLike-0.0.1-SNAPSHOT/) é a versão 0.0.1-SNAPSHOT (não final).
[http://localhost:8080/twitterLike-1.0.0/](http://localhost:8080/twitterLike-1.0.0/) é a versão 1.0.0 final.  

# Melhorias futuras (Infelizmente não deu tempo)
* Utilizar ElasticSearch para pesquisas de texto: [https://www.elastic.co/guide/en/elasticsearch/reference/current/windows.html](https://www.elastic.co/guide/en/elasticsearch/reference/current/windows.html)
* Adicionar HATEOAS nas mensagens 
* Criar jobs no Jenkins para compilar e implantar (necessita servidor)
* Adicionar testes automatizados com o Arquillian 
