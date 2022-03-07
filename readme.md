Business Application Initial Content
====================================

Basic example of KIE Server SpringBoot with KeyCloak integration (SSO).

Prerequisites

KIE Server with KeyCloak
========================

KIE Server SpringBoot sample application that uses KeyCloak and Spring Security for securing access to KIE Server resources.

This sample requires KeyCloak to be installed and configured with following defaults:
- keycloak.auth-server-url=http://localhost:8100/auth
- keycloak.realm=master
- keycloak.resource=springboot-app


How to configure it
-------------------

- Download and install KeyCloak. 
- Use default master realm or create new one
- Create client named springboot-app and set its AccessType to public
- Set Valid redirect URI and Web Origin according to your local setup - with default setup they should be set to
	- Valid Redirect URIs: http://localhost:8090/*
	- Web Origins: http://localhost:8090
- Create realm roles that are used in the example (HR and PM)
- Create user named john and password john1 and add HR and/or PM role to that user

Step 1: Checkout and build (mvn clean install) the below project.
https://github.com/rmuppane/work-flow

Step 2: Checkout and build (mvn clean install).
https://github.com/rmuppane/sb-kie-server-sso-service

When we compare with the https://github.com/rmuppane/sb-kie-server-service following changes were added 


pom.xml
-------

Dependencies

	<dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-spring-boot-starter</artifactId>
			<exclusions>
				<exclusion>
					<artifactId>commons-logging</artifactId>
					<groupId>commons-logging</groupId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-common</artifactId>
			<version>${version.keycloak}</version>
		</dependency>

		<dependency>
			<groupId>org.keycloak</groupId>
			<artifactId>keycloak-admin-client</artifactId>
			<version>${version.keycloak}</version>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>commons-logging</groupId>
					<artifactId>commons-logging</artifactId>
				</exclusion>
				<exclusion>
					<groupId>jakarta.ws.rs</groupId>
					<artifactId>jakarta.ws.rs-api</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.jboss.resteasy</groupId>
					<artifactId>resteasy-multipart-provider</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.jboss.resteasy</groupId>
					<artifactId>resteasy-jaxb-provider</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.kie.server</groupId>
			<artifactId>kie-server-client</artifactId>
			<version>${version.org.kie}</version>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>jakarta.ws.rs</groupId>
					<artifactId>jakarta.ws.rs-api</artifactId>
				</exclusion>
				<exclusion>
					<groupId>org.slf4j</groupId>
					<artifactId>jcl-over-slf4j</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		
Dependency Management

		<dependencyManagement>
			<dependencies>
				<dependency>
					<groupId>org.keycloak.bom</groupId>
					<artifactId>keycloak-adapter-bom</artifactId>
					<version>${version.keycloak}</version>
					<type>pom</type>
					<scope>import</scope>
				</dependency>
			</dependencies>
		</dependencyManagement>
	


application.propeerties
-----------------------

All keycloak configuration is present in src/main/resources/application.properties file.

# keycloak security setup
keycloak.auth-server-url=http://localhost:8080/auth
keycloak.realm=master
keycloak.resource=springboot-app
keycloak.public-client=true
keycloak.principal-attribute=preferred_username
keycloak.enable-basic-auth=true

# role for restricted tag (VariableGuardProcessEventListener)
kie.restricted-role=President


Java classes
------------

KeycloakIdentityProvider.java []
KeycloakVariableGuardProcessEventListener.java []
DefaultWebSecurityConfig.java []


Step 3: java -jar ./target/sb-kie-server-service-1.0-SNAPSHOT.jar or mvn clean spring-boot:run