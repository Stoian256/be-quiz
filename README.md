# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.example.be-quiz' is invalid and this project uses 'com.example.bequiz' instead.

# Quiz App
The Interview Quiz App is a user-friendly and intuitive platform tailored specifically for conducting interview assessments.
This application streamlines the interview process by providing a structured quiz format, enabling interviewers to evaluate candidates' knowledge, problem-solving abilities, and relevant skills in a systematic manner.


### Configuration

1. Auth0 Configuration:
   - Obtain Auth0 credentials (client ID, client secret, etc.) and configure them in the project. Refer to Auth0 documentation for details.

2. Database Configuration:
   - Ensure PostgreSQL is installed. Configure database settings in the application.properties or application.yml file.


### Prerequisites

- Java JDK (version 17.0.9)
- Gradle (version 9.0)
- Your favorite IDE (IntelliJ)
- Docker (version 3.0)
- Swagger -> http://localhost:8080/swagger-ui/index.html

### Run App from CommandLine
1. Install JDK 17.0.9(https://www.oracle.com/java/technologies/downloads/#java17)
2. Set JAVA_HOME Environment Variable for Windows
3. Execute ./gradlew bootRun

### Installing

1. Clone the repository:

   ```bash
   git clone https://Ddroidd@dev.azure.com/Ddroidd/Bootcamp%20Fall%202023/_git/be-quiz

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web.security)
* [Okta Spring Boot documentation](https://github.com/okta/okta-spring-boot#readme)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Liquibase Migration](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.liquibase)
* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web.security.oauth2.server)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Okta-Hosted Login Page Example](https://github.com/okta/samples-java-spring/tree/master/okta-hosted-login)
* [Custom Login Page Example](https://github.com/okta/samples-java-spring/tree/master/custom-login)
* [Okta Spring Security Resource Server Example](https://github.com/okta/samples-java-spring/tree/master/resource-server)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## OAuth 2.0 and OIDC with Okta

If you don't have a free Okta developer account, you can create one with [the Okta CLI](https://cli.okta.com):

```bash
$ okta register
```

Then, register your Spring Boot app on Okta using:

```bash
$ okta apps create
```

Select **Web** > **Okta Spring Boot Starter** and accept the default redirect URIs.

