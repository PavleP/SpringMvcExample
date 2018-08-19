package com.learn.config;

import java.io.IOException;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * Set the spring application context according to the environment property set as a system property.
 */
@Component
public class EnvironmentTokenBasedApplicationContextInitializer implements
    ApplicationContextInitializer<ConfigurableWebApplicationContext> {

  private static final String ENVIRONMENT_TOKEN_NAME = "ENVIRONMENT_TOKEN";
  
  private static final String ACTIVE_PROFILES_SUFFIX = ".active.profiles";

  private static final String PROPERTIES = "classpath:environment.setup.properties";

  private static final String[] EMPTY_ARRAY = new String[0];

  @Override
  public void initialize(ConfigurableWebApplicationContext applicationContext) {
    ResourcePropertySource propertySource;
    try {
      propertySource = new ResourcePropertySource(PROPERTIES);
    } catch (IOException e) {
      throw new IllegalArgumentException("could not load " + PROPERTIES, e);
    }

    String environmentToken = getEnvironmentToken();

    String[] activeProfiles = readActiveProfiles(environmentToken, propertySource);
    ConfigurableEnvironment environment = applicationContext.getEnvironment();

    environment.setActiveProfiles(activeProfiles);
  }

  private String getEnvironmentToken() {
    String environmentToken = System.getenv(ENVIRONMENT_TOKEN_NAME); // from System variables

    if(StringUtils.isEmpty(environmentToken)) {
      environmentToken = System.getProperty(ENVIRONMENT_TOKEN_NAME); // from VM options
    }

    if(StringUtils.isEmpty(environmentToken)) {
      throw new IllegalStateException("Neither system variable not system property is found for " + ENVIRONMENT_TOKEN_NAME);
    }

    return environmentToken;
  }

  private String[] readActiveProfiles(String environmentToken, ResourcePropertySource propertySource) {

    String activeProfiles = (String) propertySource.getProperty(environmentToken + ACTIVE_PROFILES_SUFFIX);
    if (StringUtils.isEmpty(activeProfiles)) {
      return EMPTY_ARRAY;
    }

    return activeProfiles.split(",");
  }

}
